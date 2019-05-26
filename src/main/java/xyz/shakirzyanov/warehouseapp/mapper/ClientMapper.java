package xyz.shakirzyanov.warehouseapp.mapper;

import org.mapstruct.Mapper;
import xyz.shakirzyanov.warehouseapp.dto.ClientDto;
import xyz.shakirzyanov.warehouseapp.model.Client;

@Mapper
public interface ClientMapper {
    Client toModel(ClientDto clientDto);
    ClientDto toDto(Client client);
}
