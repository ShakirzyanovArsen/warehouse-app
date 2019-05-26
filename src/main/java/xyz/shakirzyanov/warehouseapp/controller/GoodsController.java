package xyz.shakirzyanov.warehouseapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.shakirzyanov.warehouseapp.model.enums.GoodsUnit;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @GetMapping("/units")
    public ResponseEntity<List<GoodsUnit>> getUnits() {
        var units = Arrays.asList(GoodsUnit.values());
        return ResponseEntity.ok(units);
    }
}
