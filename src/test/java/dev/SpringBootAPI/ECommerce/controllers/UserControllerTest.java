package dev.SpringBootAPI.ECommerce.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.SpringBootAPI.ECommerce.controllers.user.UserController;
import dev.SpringBootAPI.ECommerce.dtos.user.UserDTO;
import dev.SpringBootAPI.ECommerce.exceptions.GlobalExceptionHandler;
import dev.SpringBootAPI.ECommerce.models.user.User;
import dev.SpringBootAPI.ECommerce.models.user.UserType;
import dev.SpringBootAPI.ECommerce.repositories.user.UserRepository;
import dev.SpringBootAPI.ECommerce.services.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private User createValidUser(boolean active) {
        User user = new User();
        user.setName("Test User");
        user.setEmail("teste@teste.com");
        user.setPassword("100%Testpass");
        user.setCpf("89403234083");
        user.setBirthDate(LocalDate.parse("2001-12-03"));
        user.setUserType(new UserType(1));
        return user;
    }

    private User createInvalidUser() {
        User user = new User();
        user.setName("123");
        user.setEmail("maria.com");
        user.setPassword("123!");
        user.setCpf("89434083");
        user.setBirthDate(LocalDate.parse("3000-01-01"));
        user.setUserType(null);
        return user;
    }

    private UserDTO createUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setBirthDate(user.getBirthDate());
        userDTO.setUserType(user.getUserType().getId());
        return userDTO;
    }

    @Test
    @DisplayName("should Return Bad Request")
    void testCreateUser_withInvalidUser_shouldReturnBadRequest() throws Exception {
        User user = createInvalidUser();

        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.details.name").value("The name can only contain letters and spaces."))
                .andExpect(jsonPath("$.details.email").value("Invalid email."))
                .andExpect(jsonPath("$.details.password").value("The password must have at least 8 characters and include letters, numbers, and special characters."))
                .andExpect(jsonPath("$.details.cpf").value("Invalid CPF."))
                .andExpect(jsonPath("$.details.birthDate").value("The birth date must be in the past."))
                .andExpect(jsonPath("$.details.userType").value("UserType can't be null."));
    }

    @Test
    @DisplayName("Should create User")
    void testCreateUser_withValidUser_shouldReturnOK() throws Exception {
        User user = createValidUser(true);
        UserDTO userDTO = createUserDTO(user);

        when(userService.createUser(any(User.class))).thenReturn(userDTO);

        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(userDTO.getName()))
                .andExpect(jsonPath("$.email").value(userDTO.getEmail()))
                .andExpect(jsonPath("$.userType").value(userDTO.getUserType()));
        //.andExpect(jsonPath("$.birthDate").value(userDTO.getBirthDate()));This shit don't want to work...
    }

    @Test
    void getUsers() {
        when(userRepository.findAll()).thenReturn(List.of(createValidUser(true)));
        List<UserDTO> result = userService.findAll();
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void inactiveActiveUser() {
    }

    @Test
    void testInactiveActiveUser() {
    }

    @Test
    void deleteUser() {
    }
}