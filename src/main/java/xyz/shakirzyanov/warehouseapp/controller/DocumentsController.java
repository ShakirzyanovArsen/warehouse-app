package xyz.shakirzyanov.warehouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.shakirzyanov.warehouseapp.dto.CreateDocumentDto;
import xyz.shakirzyanov.warehouseapp.facade.DocumentFacade;

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
}
