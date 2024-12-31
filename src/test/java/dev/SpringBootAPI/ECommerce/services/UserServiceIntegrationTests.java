package dev.SpringBootAPI.ECommerce.services;

import dev.SpringBootAPI.ECommerce.dtos.user.UserDTO;
import dev.SpringBootAPI.ECommerce.mappers.user.UserMapper;
import dev.SpringBootAPI.ECommerce.models.user.User;
import dev.SpringBootAPI.ECommerce.models.user.UserType;
import dev.SpringBootAPI.ECommerce.repositories.user.UserRepository;
import dev.SpringBootAPI.ECommerce.repositories.user.UserTypeRepository;
import dev.SpringBootAPI.ECommerce.services.user.UserService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceIntegrationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EntityManager entityManager;

    private UserType userType;
    private User user1;
    private User user2;
    private User user3;
    private User user4;

    private static User createValidUser(String name, String cpf, boolean active, UserType userType) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName(name);
        user.setEmail(name.toLowerCase().replace(" ", "") + "@teste.com");
        user.setPassword("100%Testpass");
        user.setCpf(cpf);
        user.setBirthDate(LocalDate.parse("2001-12-03"));
        user.setUserType(userType);
        return user;
    }

    private static UserDTO createUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setBirthDate(user.getBirthDate());
        userDTO.setUserType(user.getUserType().getId());
        return userDTO;
    }

    @BeforeAll
    void beforeAll() {
        userRepository.deleteAll();
        userTypeRepository.deleteAll();

        userType = userTypeRepository.save(new UserType(1, "testUser", "TestUserDescription"));
        user1 = userRepository.save(createValidUser("Test User one", "64448375021", true, userType));
        user2 = userRepository.save(createValidUser("Test User two", "01787546098", true, userType));
        user3 = userRepository.save(createValidUser("Test User three", "37246748059", false, userType));
        user4 = userRepository.save(createValidUser("Test User four", "86564425031", false, userType));
    }

    @Test
    void createUserTest() {
        User newUser = createValidUser("createUserTest", "83708199073", true, userType);

        UserDTO createdUserDTO = userService.createUser(newUser);

        assertNotNull(createdUserDTO);
        assertEquals(newUser.getName(), createdUserDTO.getName());
        assertEquals(newUser.getEmail(), createdUserDTO.getEmail());
        assertEquals(newUser.getBirthDate(), createdUserDTO.getBirthDate());
        assertEquals(newUser.getUserType().getId(), createdUserDTO.getUserType());

        Optional<User> savedUser = userRepository.findByEmail(newUser.getEmail());
        assertTrue(savedUser.isPresent());
        assertTrue(passwordEncoder.matches("100%Testpass", savedUser.get().getPassword()));
        assertEquals(newUser.getCpf(), savedUser.get().getCpf());

        assertNotNull(savedUser.get().getCreatedAt());
        assertTrue(savedUser.get().getCreatedAt().isBefore(LocalDate.now()) || savedUser.get().getCreatedAt().equals(LocalDate.now()));
    }

    @Test
    void createUserWithInvalidEmailTest() {
        User invalidUser = createValidUser("Invalid Email", "61157275028", true, userType);
        invalidUser.setEmail("invalid-email");

        assertThrows(org.springframework.transaction.TransactionSystemException.class, () -> {
            userService.createUser(invalidUser);
        });
    }

    @Test
    void createUserWithInvalidCpfTest() {
        User invalidUser = createValidUser("Invalid CPF", "77714330066", true, userType);
        invalidUser.setCpf("12345678900");

        assertThrows(org.springframework.transaction.TransactionSystemException.class, () -> {
            userService.createUser(invalidUser);
        });
    }

    @Test
    void createUserWithBlankPasswordTest() {
        User invalidUser = createValidUser("Blank Password", "15036758072", true, userType);
        invalidUser.setPassword(null);

        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            userService.createUser(invalidUser);
        });
    }
}
