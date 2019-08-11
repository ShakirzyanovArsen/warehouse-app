package xyz.shakirzyanov.warehouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.shakirzyanov.warehouseapp.dto.WarehouseDto;
import xyz.shakirzyanov.warehouseapp.model.Warehouse;
import xyz.shakirzyanov.warehouseapp.service.WarehouseService;

import javax.validation.constraints.Min;


@RestController
@RequestMapping("/warehouse")
@Validated
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping
    public ResponseEntity<Page<Warehouse>> getList(@Min(1) @RequestParam(defaultValue = "1") int pageNumber,
                                                    @Min(1) @RequestParam(defaultValue = "20") int perPage) {
        PageRequest pageable = PageRequest.of(pageNumber - 1, perPage);
        return ResponseEntity.ok(warehouseService.getList(pageable));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Warehouse> getWarehouse(@PathVariable String uuid) {
        return ResponseEntity.ok(warehouseService.getByUuid(uuid));
    }

    @PostMapping
    public ResponseEntity<Warehouse> createWarehouse(@RequestBody WarehouseDto warehouseDto) {
        return ResponseEntity.ok(warehouseService.createNew(warehouseDto));
    }
}
