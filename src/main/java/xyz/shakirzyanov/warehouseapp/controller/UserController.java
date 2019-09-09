package xyz.shakirzyanov.warehouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.shakirzyanov.warehouseapp.dto.AddPublicKeyDto;
import xyz.shakirzyanov.warehouseapp.model.User;
import xyz.shakirzyanov.warehouseapp.service.CryptoService;
import xyz.shakirzyanov.warehouseapp.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CryptoService cryptoService;

    @PostMapping("/public_key")
    public ResponseEntity addPublicKey(@RequestBody AddPublicKeyDto publicKeyDto) {
        if(!cryptoService.validatePublicKey(publicKeyDto.getPublicKey())) {
            return ResponseEntity.badRequest().body("invalid public key");
        }
        User user = userService.addPublicKey(publicKeyDto.getPublicKey());
        return ResponseEntity.ok(user.getPublicKeys());
    }
}
