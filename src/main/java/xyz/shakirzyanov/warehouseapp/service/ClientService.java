package xyz.shakirzyanov.warehouseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xyz.shakirzyanov.warehouseapp.dto.ClientDto;
import xyz.shakirzyanov.warehouseapp.mapper.ClientMapper;
import xyz.shakirzyanov.warehouseapp.model.Client;
import xyz.shakirzyanov.warehouseapp.model.Warehouse;
import xyz.shakirzyanov.warehouseapp.repository.ClientRepository;

import java.util.Date;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private ClientMapper mapper;
    @Autowired
    private ClientRepository clientRepository;

    public Client createNew(ClientDto dto) {
        var client = mapper.toModel(dto);
        client.setUuid(UUID.randomUUID().toString());
        client.setCreatedAt(new Date());
        clientRepository.save(client);
        return client;
    }

    public Page<Client> getList(Pageable pageable) {
        return clientRepository.findClientsByPage(pageable);
    }
}
