package xyz.shakirzyanov.warehouseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import xyz.shakirzyanov.warehouseapp.dto.WarehouseDto;
import xyz.shakirzyanov.warehouseapp.mapper.WarehouseMapper;
import xyz.shakirzyanov.warehouseapp.model.Warehouse;
import xyz.shakirzyanov.warehouseapp.repository.WarehouseRepository;

import java.util.Date;
import java.util.UUID;

@Service
public class WarehouseService {
    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private WarehouseMapper mapper;

    public Warehouse createNew(WarehouseDto warehouseDto) {
        var warehouse = mapper.toModel(warehouseDto);
        warehouse.setUuid(UUID.randomUUID().toString());
        warehouse.setCreatedAt(new Date());
        warehouseRepository.save(warehouse);
        return warehouse;
    }

    public Warehouse getByUuid(String uuid) {
        var warehouse = warehouseRepository.findByUuid(uuid);
        if(warehouse == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "warehouse not found");
        }
        return warehouse;
    }

    public Page<Warehouse> getList(Pageable pageable) {
        return warehouseRepository.findWarehousesByPage(pageable);
    }
}
