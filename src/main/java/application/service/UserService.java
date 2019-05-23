package application.service;

import application.domain.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
