package xyz.shakirzyanov.warehouseapp.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import xyz.shakirzyanov.warehouseapp.dto.CreateDocumentDto;
import xyz.shakirzyanov.warehouseapp.dto.FileSig;
import xyz.shakirzyanov.warehouseapp.dto.GoodsCount;
import xyz.shakirzyanov.warehouseapp.exception.BadRequestException;
import xyz.shakirzyanov.warehouseapp.model.Client;
import xyz.shakirzyanov.warehouseapp.model.Document;
import xyz.shakirzyanov.warehouseapp.model.Goods;
import xyz.shakirzyanov.warehouseapp.model.User;
import xyz.shakirzyanov.warehouseapp.repository.DocumentRepository;
import xyz.shakirzyanov.warehouseapp.service.ClientService;
import xyz.shakirzyanov.warehouseapp.service.CryptoService;
import xyz.shakirzyanov.warehouseapp.service.GoodsService;
import xyz.shakirzyanov.warehouseapp.service.WarehouseService;
import xyz.shakirzyanov.warehouseapp.xlsx.ExcelFiller;

import java.io.*;
import java.security.MessageDigest;
import java.util.*;

@Service
public class DocumentFacade {

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private CryptoService cryptoService;

    private final String DOCUMENTS_DIRECTORY = "documents/";
    private Logger logger = LoggerFactory.getLogger(DocumentFacade.class);

    /**
     * @param documentDto
     * @return uuid of document
     */
    public String createDocument(CreateDocumentDto documentDto) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var document = new Document();
        document.setUuid(UUID.randomUUID().toString());
        document.setUserUuid(user.getUuid());

        var warehouse = warehouseService.getByUuid(documentDto.getWarehouseUuid());
        if (warehouse == null) {
            throw new BadRequestException("Warehouse not found");
        }
        document.setWarehouseUuid(warehouse.getUuid());

        var client = clientService.getByUuid(documentDto.getClientUuid());
        if (client == null) {
            throw new BadRequestException("Client not found");
        }
        document.setClientUuid(client.getUuid());

        List<Goods> goodsList = new ArrayList<>();
        for (GoodsCount goodsCount : documentDto.getGoodsCounts()) {
            var goods = goodsService.getByUuid(goodsCount.getGoodsUuid());
            if (goods.getCount() < goodsCount.getCount()) {
                throw new BadRequestException("Goods count is greater than in DB");
            }
            goodsList.add(goods);
        }
        var goodsUuids = new ArrayList<String>();
        var goodsCounts = new ArrayList<Double>();
        for (int i = 0; i < goodsList.size(); i++) {
            goodsUuids.add(documentDto.getGoodsCounts().get(i).getGoodsUuid());
            goodsCounts.add(documentDto.getGoodsCounts().get(i).getCount());
        }
        document.setGoods(goodsUuids);
        document.setCounts(goodsCounts);
        document.setDocumentType(documentDto.getDocumentType());
        document.setEnded(0);
        document.setCreatedAt(new Date());

        ExcelFiller excelFiller = createExcelFiller();
        excelFiller.fillCreatedAt(document.getCreatedAt());
        excelFiller.fillDocumentType(document.getDocumentType());
        excelFiller.fillWarehouse(warehouse);
        excelFiller.fillClient(client);
        excelFiller.fillGoodsAndCounts(goodsList, goodsCounts);
        String documentName = DOCUMENTS_DIRECTORY + document.getUuid() + ".xlsx";
        File file;
        try {
            file = new File(documentName);
            file.createNewFile();
            var os = new FileOutputStream(file);
            excelFiller.writeToStream(os);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Excel error");
        }
        document.setFileName(file.getAbsolutePath());
        try {
            document.setFileHash(getFileHash(file));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hash error");
        }
        documentRepository.save(document);
        return document.getUuid();
    }

    public boolean addFileSig(FileSig fileSig) {
        Document document = documentRepository.findByUUID(fileSig.getUuid());
        if(document == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found");
        }
        File file = new File(document.getFileName());
        byte[] sigBytes = Base64.getDecoder().decode(fileSig.getSigBase64());
        boolean verified = cryptoService.validateDocument(file, sigBytes);
        if(verified) {
            String newFileName = getFileNameWithNewExtension(file, ".sig");
            File sigFile = new File(newFileName);
            try {
                sigFile.createNewFile();
                FileOutputStream fos = new FileOutputStream(sigFile);
                fos.write(sigBytes);
            } catch (Exception e){
                logger.warn("Error while file write", e);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error");
            }
            document.setEnded(1);
            document.setCreatedAt(new Date());
            document.setSigFileName(sigFile.getAbsolutePath());
            documentRepository.save(document);
        }
        return verified;
    }

    public InputStream getDocumentInputStream(String uuid) throws FileNotFoundException {
        return new FileInputStream(new File(DOCUMENTS_DIRECTORY + uuid + ".xlsx"));
    }

    private String getFileNameWithNewExtension(File f, String newExtension) {
        int i = f.getName().lastIndexOf('.');
        String name = f.getName().substring(0,i);
        return f.getParent() + "/" + name + newExtension;
    }

    private String getFileHash(File file) throws Exception{
        byte[] buffer= new byte[8192];
        int count;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        while ((count = bis.read(buffer)) > 0) {
            digest.update(buffer, 0, count);
        }
        bis.close();

        byte[] hash = digest.digest();
        return Base64.getEncoder().encodeToString(hash);
    }

    private ExcelFiller createExcelFiller() {
        ExcelFiller excelFiller;
        try {
            InputStream templateStream = new ClassPathResource("template/transaction.xlsx").getInputStream();
            excelFiller = new ExcelFiller(templateStream);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cant create file");
        }
        return excelFiller;
    }
}
