package dev.SpringBootAPI.ECommerce.services;

import dev.SpringBootAPI.ECommerce.dtos.user.UserDTO;
import dev.SpringBootAPI.ECommerce.mappers.user.UserMapper;
import dev.SpringBootAPI.ECommerce.models.user.User;
import dev.SpringBootAPI.ECommerce.repositories.user.UserRepository;
import dev.SpringBootAPI.ECommerce.services.user.UserService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    EntityManager entityManager;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("12345");
        user.setActive(true);

        userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
    }

    @Test
    void testCreateUser() {
        when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userDTO);

        UserDTO createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals(user.getName(), createdUser.getName());
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testFindAll() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toDto(any(User.class))).thenReturn(userDTO);

        List<UserDTO> users = userService.findAll();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(user.getName(), users.get(0).getName());
    }

    @Test
    void testFindById() {
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));
        when(userMapper.toDto(any(User.class))).thenReturn(userDTO);

        Optional<UserDTO> foundUser = userService.findById(user.getId());

        assertTrue(foundUser.isPresent());
        assertEquals(user.getName(), foundUser.get().getName());
    }

    @Test
    void testUpdateUser() {
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userDTO);

        userDTO.setName("Updated User");

        UserDTO updatedUser = userService.updateUser(user.getId(), userDTO);

        assertNotNull(updatedUser);
        assertEquals("Updated User", updatedUser.getName());

        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).save(any(User.class));
    }


    @Test
    void testInactiveActiveUser() {
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));

        userService.inactiveActiveUser(user.getId());

        assertFalse(user.getActive());
        verify(userRepository, times(1)).save(any(User.class));
    }
}
