package crio.aman.service.impl;

import crio.aman.entity.Badges;
import crio.aman.entity.User;
import crio.aman.exception.UserNotFoundException;
import crio.aman.repository.UserRepository;
import crio.aman.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UserNotFoundException(" User not found with ID: " + userId);
        }
    }

    @Override
    public User createUser(User user) {
        int score = user.getScore();
        if(score>100 || score<0)
        {
            throw new UserNotFoundException(" Invalid score entered");
        }
        // check it does not contains duplicate badges
        List<Badges> badgesList = new ArrayList<>();
        badgesList.addAll(user.getBadges());
        Map<Badges,Integer> hm = new HashMap<>();
        int count_badges = 0;
        for(Badges i: badgesList)
        {
            if(hm.containsKey(i))
            {
                throw new UserNotFoundException("Duplicate Badges not allowed !");
            }
            else{
                hm.put(i,1);
            }
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUserById(String userId, Integer score) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if(score>100 || score<0)
            {
                throw new UserNotFoundException(" Invalid score entered");
            }
            else {
                user.setScore(score);
            }
            return userRepository.save(user);
        } else {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
    }

    @Override
    public String deleteUserById(String userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return "User deleted successfully!";
        } else {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
    }
}
