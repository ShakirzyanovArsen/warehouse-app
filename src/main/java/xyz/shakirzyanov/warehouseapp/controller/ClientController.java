package xyz.shakirzyanov.warehouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.shakirzyanov.warehouseapp.dto.ClientDto;
import xyz.shakirzyanov.warehouseapp.model.Client;
import xyz.shakirzyanov.warehouseapp.service.ClientService;

import javax.validation.constraints.Min;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<Client> createNewClient(@RequestBody ClientDto dto) {
        var client = clientService.createNew(dto);
        return ResponseEntity.ok(client);
    }
    @GetMapping
    public ResponseEntity<Page<Client>> getList(@Min(1) @RequestParam(defaultValue = "1") int pageNumber,
                                                   @Min(1) @RequestParam(defaultValue = "20") int perPage) {
        PageRequest pageable = PageRequest.of(pageNumber - 1, perPage);
        return ResponseEntity.ok(clientService.getList(pageable));
    }

}
