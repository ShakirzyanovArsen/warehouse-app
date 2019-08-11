package xyz.shakirzyanov.warehouseapp.mapper;

import org.mapstruct.Mapper;
import xyz.shakirzyanov.warehouseapp.dto.WarehouseDto;
import xyz.shakirzyanov.warehouseapp.model.Warehouse;

@Mapper
public interface WarehouseMapper {
    WarehouseDto toDto(Warehouse warehouseDto);
    Warehouse toModel(WarehouseDto warehouse);
}
