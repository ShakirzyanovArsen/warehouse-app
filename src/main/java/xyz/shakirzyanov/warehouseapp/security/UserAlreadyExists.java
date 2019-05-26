package xyz.shakirzyanov.warehouseapp.security;


import org.springframework.security.core.AuthenticationException;

public class UserAlreadyExists extends AuthenticationException {
    public UserAlreadyExists(String explanation) {
        super(explanation);
    }
}
