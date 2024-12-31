package dev.SpringBootAPI.ECommerce.services;

import dev.SpringBootAPI.ECommerce.dtos.user.UserDTO;
import dev.SpringBootAPI.ECommerce.mappers.user.UserMapper;
import dev.SpringBootAPI.ECommerce.models.user.Password;
import dev.SpringBootAPI.ECommerce.models.user.User;
import dev.SpringBootAPI.ECommerce.models.user.UserType;
import dev.SpringBootAPI.ECommerce.repositories.user.UserRepository;
import dev.SpringBootAPI.ECommerce.services.user.UserService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceUnitTests {

    @InjectMocks
    private static UserService userService;

    @Mock
    private static UserRepository userRepository;

    @Mock
    private static UserMapper userMapper;

    @Mock
    private static PasswordEncoder passwordEncoder;

    @Mock
    private static EntityManager entityManager;

    private static User createValidUser(boolean active, UUID uuid) {
        User user = new User();
        user.setId(uuid);
        user.setName("Test User");
        user.setEmail("testuser@teste.com");
        user.setPassword("100%Testpass");
        user.setCpf("89403234083");
        user.setBirthDate(LocalDate.parse("2001-12-03"));
        user.setUserType(new UserType(1));
        user.setActive(active);
        return user;
    }

    private static User createInvalidUser() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName("123");
        user.setEmail("maria.com");
        user.setPassword("123!");
        user.setCpf("89434083");
        user.setBirthDate(LocalDate.parse("3000-01-01"));
        user.setUserType(null);
        return user;
    }

    private static UserDTO createUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setBirthDate(user.getBirthDate());
        userDTO.setUserType(user.getUserType().getId());
        userDTO.setActive(user.getActive());
        return userDTO;
    }

    @Test
    void createUserTest() {
        User user = createValidUser(true, UUID.randomUUID());
        UserDTO userDTO = createUserDTO(user);

        when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userDTO);

        UserDTO createdUserDTO = userService.createUser(user);

        assertNotNull(createdUserDTO);
        assertEquals(user.getName(), createdUserDTO.getName());

        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(any(User.class));
        verify(userMapper, times(1)).toDto(any(User.class));
    }

    @Test
    void findAllTest() {
        User user = createValidUser(true, UUID.randomUUID());
        UserDTO userDTO = createUserDTO(user);

        when(userRepository.findAll()).thenReturn(List.of(user, user, user));
        when(userMapper.toDto(any(User.class))).thenReturn(userDTO);

        List<UserDTO> userDTOList = userService.findAll();

        assertNotNull(userDTOList);
        assertEquals(3, userDTOList.size());
        assertEquals(userDTO, userDTOList.get(0));
        assertEquals(userDTO, userDTOList.get(1));
        assertEquals(userDTO, userDTOList.get(2));

        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(3)).toDto(any(User.class));
    }

    @Test
    void findByIdTest() {
        User user = createValidUser(true, UUID.randomUUID());
        UserDTO userDTO = createUserDTO(user);

        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));
        when(userMapper.toDto(any(User.class))).thenReturn(userDTO);

        Optional<UserDTO> foundUser = userService.findById(user.getId());

        assertTrue(foundUser.isPresent());
        assertEquals(userDTO, foundUser.get());

        verify(userRepository, times(1)).findById(any(UUID.class));
        verify(userMapper, times(1)).toDto(any(User.class));
    }

    @Test
    void updateUserTest() {
        User spyUser = spy(createValidUser(true, UUID.randomUUID()));

        User updatedUser = createValidUser(true, spyUser.getId());
        updatedUser.setName("Updated Name");
        updatedUser.setEmail("updatedEmail@Email.com");
        updatedUser.setBirthDate(LocalDate.parse("2001-01-01"));
        UserDTO updatedUserDTO = createUserDTO(updatedUser);

        //Mocking
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(spyUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        when(userMapper.toDto(any(User.class))).thenReturn(updatedUserDTO);

        //Executing
        UserDTO userDTO = userService.updateUser(updatedUserDTO);

        assertNotNull(userDTO);

        verify(userRepository, times(1)).findById(spyUser.getId());
        verify(userRepository, times(1)).save(any(User.class));
        verify(userMapper, times(1)).toDto(any(User.class));
        verify(entityManager, times(1)).merge(any(User.class));
        verify(spyUser, times(1)).setName("Updated Name");
        verify(spyUser, times(1)).setEmail("updatedEmail@Email.com");
        verify(spyUser, times(1)).setBirthDate(LocalDate.parse("2001-01-01"));
    }

    @Test
    void updateUserPasswordTest() {
        Password spyPassword = new Password("100%Testpass");

        User spyUser = spy(createValidUser(true, UUID.randomUUID()));
        User updatedUser = createValidUser(false, spyUser.getId());
        updatedUser.setPassword("EncodedPassword");

        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(spyUser));
        when(passwordEncoder.encode(any(String.class))).thenReturn("EncodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        userService.updateUserPassword(spyUser.getId(), spyPassword);

        assertEquals("EncodedPassword", spyUser.getPassword());

        verify(userRepository, times(1)).findById(spyUser.getId());
        verify(passwordEncoder, times(1)).encode("100%Testpass");
        verify(userRepository, times(1)).save(spyUser);
        verify(spyUser, times(1)).setPassword("EncodedPassword");
    }

    @Test
    void inactiveUserTest() {
        User spyUser = spy(createValidUser(true, UUID.randomUUID()));
        User updatedUser = createValidUser(false, spyUser.getId());

        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(spyUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        userService.inactiveActiveUser(spyUser.getId());

        assertFalse(spyUser.getActive());

        verify(userRepository, times(1)).findById(spyUser.getId());
        verify(userRepository, times(1)).save(spyUser);
        verify(spyUser, times(1)).setActive(false);
    }

    @Test
    void ActiveUserTest() {
        User spyUser = spy(createValidUser(false, UUID.randomUUID()));
        User updatedUser = createValidUser(true, spyUser.getId());

        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(spyUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        userService.inactiveActiveUser(spyUser.getId());

        assertTrue(spyUser.getActive());

        verify(userRepository, times(1)).findById(spyUser.getId());
        verify(userRepository, times(1)).save(spyUser);
        verify(spyUser, times(1)).setActive(true);
    }
}