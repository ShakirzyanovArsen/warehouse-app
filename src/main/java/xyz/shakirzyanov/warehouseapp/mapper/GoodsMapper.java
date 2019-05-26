package xyz.shakirzyanov.warehouseapp.mapper;

import org.mapstruct.Mapper;
import xyz.shakirzyanov.warehouseapp.dto.GoodsDto;
import xyz.shakirzyanov.warehouseapp.model.Goods;

@Mapper
public interface GoodsMapper {
    GoodsDto toDto(Goods goods);
    Goods toModel(GoodsDto dto);
}
