package xyz.shakirzyanov.warehouseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xyz.shakirzyanov.warehouseapp.dto.GoodsDto;
import xyz.shakirzyanov.warehouseapp.mapper.GoodsMapper;
import xyz.shakirzyanov.warehouseapp.model.Goods;
import xyz.shakirzyanov.warehouseapp.repository.GoodsRepository;

import java.util.Date;
import java.util.UUID;

@Service
public class GoodsService {
    @Autowired
    private GoodsMapper mapper;

    @Autowired
    private GoodsRepository goodsRepository;

    public Goods createNew(GoodsDto dto) {
        var goods = mapper.toModel(dto);
        goods.setUuid(UUID.randomUUID().toString());
        goods.setCreatedAt(new Date());
        goodsRepository.save(goods);
        return goods;
    }

    public Page<Goods> getList(Pageable page) {
        return goodsRepository.findGoodsByPage(page);
    }

    public Goods getByUuid(String uuid) {
        return goodsRepository.findByUuid(uuid);
    }
}
