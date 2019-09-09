package xyz.shakirzyanov.warehouseapp.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import xyz.shakirzyanov.warehouseapp.model.User;
import xyz.shakirzyanov.warehouseapp.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public User addPublicKey(String publicKey) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) auth.getPrincipal();
        List<String> keysList = Lists.newArrayList(user.getPublicKeys());
        for (String key: keysList) {
            if(key.equals(publicKey)) {
                return user;
            }
        }
        keysList.add(publicKey);
        String[] keys = new String[keysList.size()];
        keysList.toArray(keys);
        user.setPublicKeys(keys);
        userRepository.save(user);
        return user;
    }
}
