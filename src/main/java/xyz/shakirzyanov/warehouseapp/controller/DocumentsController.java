package xyz.shakirzyanov.warehouseapp.controller;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.shakirzyanov.warehouseapp.dto.CreateDocumentDto;
import xyz.shakirzyanov.warehouseapp.dto.FileSig;
import xyz.shakirzyanov.warehouseapp.facade.DocumentFacade;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/documents")
public class DocumentsController {

    @Autowired
    private DocumentFacade documentFacade;

    @PostMapping()
    public ResponseEntity<Map> prepareDocument(@RequestBody CreateDocumentDto documentDto) {
        var map = new HashMap<String, String>();
        var documentUuid = documentFacade.createDocument(documentDto);
        map.put("documentUuid", documentUuid);
        return ResponseEntity.ok(map);
    }
    @GetMapping(value = "/{uuid}")
    public void getDocumentByUuid(@PathVariable("uuid") String uuid, HttpServletResponse response) {
        try {
            InputStream is = documentFacade.getDocumentInputStream(uuid);
            IOUtils.copy(is, response.getOutputStream());
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.addHeader("Content-disposition", "attachment;filename=Document.xlsx");
            response.flushBuffer();
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @PostMapping("/validate")
    public ResponseEntity addDocumentSig(@RequestBody  FileSig fileSig) {
        boolean verified = documentFacade.addFileSig(fileSig);
        if(verified) {
            return ResponseEntity.ok(verified);
        } else {
            return ResponseEntity.badRequest().body(verified);
        }
    }
}
