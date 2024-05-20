package crio.aman.service.impl;

import crio.aman.entity.Badges;
import crio.aman.entity.User;
import crio.aman.exception.UserNotFoundException;
import crio.aman.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        List<Badges> badgesList = new ArrayList<>();
        badgesList.add(Badges.valueOf("NINJA"));
        badgesList.add(Badges.valueOf("MASTER"));
        user = User.builder().id("1").userName("Aman").score(56).badges(badgesList).build();
    }

    @AfterEach
    void tearDown() {
        user = null;
    }

    @Test
    void getAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();
        assertEquals(1, result.size());
        assertEquals("Aman", result.get(0).getUserName());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        User result = userService.getUserById("1");
        assertNotNull(result);
        assertEquals("Aman", result.getUserName());

        verify(userRepository, times(1)).findById("1");
    }

    @Test
    void createUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        User result = userService.createUser(user);
        assertNotNull(result);
        assertEquals(user, result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUserById() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User score = userService.updateUserById("1",54);
        assertNotNull(score);
        assertEquals(54, score.getScore());

        verify(userRepository, times(1)).findById("1");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void deleteUserById() {
        // Case 1: User exists and is deleted successfully
        when(userRepository.existsById("1")).thenReturn(true);
        doNothing().when(userRepository).deleteById("1");

        String result = userService.deleteUserById("1");
        assertEquals("User deleted successfully!", result);

        verify(userRepository, times(1)).existsById("1");
        verify(userRepository, times(1)).deleteById("1");

        // Case 2: User does not exist and exception is thrown
        when(userRepository.existsById("2")).thenReturn(false);

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUserById("2");
        });

        assertEquals("User not found with ID: 2", exception.getMessage());

        verify(userRepository, times(1)).existsById("2");
        verify(userRepository, times(0)).deleteById("2");
    }

    @Test
    void testUpdateUserById() {
        // This seems redundant since updateUserById already covers this.
        // Assuming it's for different validation or conditions:
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.updateUserById("1", 54);
        assertNotNull(result);
        assertEquals(54, result.getScore());

        verify(userRepository, times(1)).findById("1");
        verify(userRepository, times(1)).save(any(User.class));
    }
}