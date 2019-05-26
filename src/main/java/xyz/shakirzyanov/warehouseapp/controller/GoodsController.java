package xyz.shakirzyanov.warehouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.shakirzyanov.warehouseapp.dto.GoodsDto;
import xyz.shakirzyanov.warehouseapp.model.Goods;
import xyz.shakirzyanov.warehouseapp.model.enums.GoodsUnit;
import xyz.shakirzyanov.warehouseapp.service.GoodsService;

import javax.validation.constraints.Min;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/units")
    public ResponseEntity<List<GoodsUnit>> getUnits() {
        var units = Arrays.asList(GoodsUnit.values());
        return ResponseEntity.ok(units);
    }

    @PostMapping
    public ResponseEntity<Goods> createGoods(@RequestBody GoodsDto goodsDto) {
        var goods = goodsService.createNew(goodsDto);
        return ResponseEntity.ok(goods);
    }

    @GetMapping()
    public ResponseEntity<Page<Goods>> getGoodsList(@Min(1) @RequestParam(defaultValue = "1") int pageNumber,
                                                    @Min(1) @RequestParam(defaultValue = "20") int perPage) {
        PageRequest pageable = PageRequest.of(pageNumber - 1, perPage);
        var goodsPage = goodsService.getList(pageable);
        return ResponseEntity.ok(goodsPage);
    }
}
