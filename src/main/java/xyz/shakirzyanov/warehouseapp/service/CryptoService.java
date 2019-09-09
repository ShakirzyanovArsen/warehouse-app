package xyz.shakirzyanov.warehouseapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import xyz.shakirzyanov.warehouseapp.model.User;
import xyz.shakirzyanov.warehouseapp.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class CryptoService {
    private Logger log = LoggerFactory.getLogger(CryptoService.class);
    private static final String ALGORITHM = "RSA";
    public static final String SIGN_VERIFY_ALG = "SHA256withRSA";

    @Autowired
    private UserRepository userRepository;

    public boolean validatePublicKey(String base64PublicKey) {
        try {
            byte[] publicKey = Base64.getDecoder().decode(base64PublicKey);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            keyFactory.generatePublic(new X509EncodedKeySpec(publicKey));
        } catch (Exception e) {
            log.info("error occurs while public key validation", e);
            return false;
        }
        return true;
    }

    public boolean validateDocument(File file, byte[] sig) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        String[] publicKeys = user.getPublicKeys();
        byte[] payload;
        try {
            payload = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "file dos not exists");
        }
        for (String publicKey : publicKeys) {
            byte[] key = Base64.getDecoder().decode(publicKey);
            PublicKey pKey;
            Signature signature;
            try {

                KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
                pKey = keyFactory.generatePublic(new X509EncodedKeySpec(key));
                signature = Signature.getInstance(SIGN_VERIFY_ALG);
                signature.initVerify(pKey);
                signature.update(payload);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while key creation");
            }

            try {
                return signature.verify(sig);
            } catch (SignatureException e) {
                return false;
            }
        }
        return false;
    }
}
