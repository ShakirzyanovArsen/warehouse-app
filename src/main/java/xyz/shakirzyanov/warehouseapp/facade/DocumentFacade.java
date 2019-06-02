package xyz.shakirzyanov.warehouseapp.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import xyz.shakirzyanov.warehouseapp.dto.CreateDocumentDto;
import xyz.shakirzyanov.warehouseapp.dto.GoodsCount;
import xyz.shakirzyanov.warehouseapp.exception.BadRequestException;
import xyz.shakirzyanov.warehouseapp.model.Client;
import xyz.shakirzyanov.warehouseapp.model.Document;
import xyz.shakirzyanov.warehouseapp.model.Goods;
import xyz.shakirzyanov.warehouseapp.model.User;
import xyz.shakirzyanov.warehouseapp.service.ClientService;
import xyz.shakirzyanov.warehouseapp.service.GoodsService;
import xyz.shakirzyanov.warehouseapp.service.WarehouseService;
import xyz.shakirzyanov.warehouseapp.xlsx.ExcelFiller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentFacade {

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private GoodsService goodsService;

    private final String DOCUMENTS_DIRECTORY = "documents/";

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
        if(warehouse == null) {
            throw new BadRequestException("Warehouse not found");
        }
        document.setWarehouseUuid(warehouse.getUuid());

        var client = clientService.getByUuid(documentDto.getClientUuid());
        if(client == null) {
            throw new BadRequestException("Client not found");
        }
        document.setClientUuid(client.getUuid());

        List<Goods> goodsList = new ArrayList<>();
        for (GoodsCount goodsCount : documentDto.getGoodsCounts()) {
            var goods = goodsService.getByUuid(goodsCount.getGoodsUuid());
            if(goods.getCount() < goodsCount.getCount()) {
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

        try {
            var file = new File(DOCUMENTS_DIRECTORY + document.getUuid() + ".xlsx");
            file.createNewFile();
            var os = new FileOutputStream(file);
            excelFiller.writeToStream(os);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Excel error");
        }
        return document.getUuid();
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
