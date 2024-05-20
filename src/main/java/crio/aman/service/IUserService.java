package crio.aman.service;

import crio.aman.entity.User;

import java.util.List;

public interface IUserService {
    public List<User> getAllUsers();

    User getUserById(String userId);

    User createUser(User user);

    User updateUserById(String userId, Integer score);

    String deleteUserById(String userId);
}
