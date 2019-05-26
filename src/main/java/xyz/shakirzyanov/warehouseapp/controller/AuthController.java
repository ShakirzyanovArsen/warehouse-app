package xyz.shakirzyanov.warehouseapp.controller;

import io.swagger.models.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import xyz.shakirzyanov.warehouseapp.dto.AuthenticationRequest;
import xyz.shakirzyanov.warehouseapp.model.User;
import xyz.shakirzyanov.warehouseapp.repository.UserRepository;
import xyz.shakirzyanov.warehouseapp.security.JwtTokenProvider;
import xyz.shakirzyanov.warehouseapp.security.UserAlreadyExists;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody AuthenticationRequest data) {

        try {
            var username = data.getUsername();
            var password = data.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            var user = this.userRepository.findByLogin(username);
            if (user == null) {
                throw new UsernameNotFoundException("Username " + username + "not found");
            }
            var token = jwtTokenProvider.createToken(username, user.getRoles());

            return successResponse(username, token);
        } catch (AuthenticationException e) {
            logger.info("exception while signin {}", e);
            return errorResponse("Can't signin");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody AuthenticationRequest data) {
        var username = data.getUsername();
        var encodedPasswprd = encoder.encode(data.getPassword());
        try {
            var user = userRepository.findByLogin(username);
            if(user != null) {
                throw new UserAlreadyExists("User already exists");
            }
            user = new User();
            user.setUuid(UUID.randomUUID().toString());
            user.setLogin(username);
            user.setPwd(encodedPasswprd);
            user.setPublicKeys(new String[]{});
            userRepository.save(user);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            var token = jwtTokenProvider.createToken(username, user.getRoles());
            return successResponse(username, token);
        } catch (AuthenticationException e) {
            logger.info("exception while signup {}", e);
            return errorResponse("Can't signup");
        }
    }

    private ResponseEntity errorResponse(String error) {
        Map<Object, Object> model = new HashMap<>();
        model.put("error", error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(model);
    }
    private ResponseEntity successResponse(String username, String token) {
        Map<Object, Object> model = new HashMap<>();
        model.put("username", username);
        model.put("token", token);
        return ok(model);
    }
}
