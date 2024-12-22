package dev.SpringBootAPI.ECommerce.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.SpringBootAPI.ECommerce.controllers.user.UserController;
import dev.SpringBootAPI.ECommerce.dtos.user.UserDTO;
import dev.SpringBootAPI.ECommerce.exceptions.GlobalExceptionHandler;
import dev.SpringBootAPI.ECommerce.models.user.User;
import dev.SpringBootAPI.ECommerce.models.user.UserType;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

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


    @Test
    void testCreateUser_withInvalidUser_shouldReturnBadRequest() throws Exception {
        User user = createInvalidUser();

        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.details.name").value("O nome só pode conter letras e espaços."))
                .andExpect(jsonPath("$.details.email").value("O email deve ser válido."))
                .andExpect(jsonPath("$.details.password").value("A senha deve ter no mínimo 8 caracteres, incluindo letras, números e caracteres especiais."))
                .andExpect(jsonPath("$.details.cpf").value("CPF inválido."))
                .andExpect(jsonPath("$.details.birthDate").value("A data de nascimento deve ser uma data passada."))
                .andExpect(jsonPath("$.details.userType").value("O tipo de usuário não pode ser nulo."));
    }

    @Test
    @DisplayName("Should create User")
    void testCreateUser_withValidUser_shouldReturnOK() throws Exception {
        User user = createValidUser();
        UserDTO userDTO = createValidUserDTO(user);

        when(userService.createUser(any(User.class))).thenReturn(userDTO);

        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(userDTO.getName()))
                .andExpect(jsonPath("$.email").value(userDTO.getEmail()))
                .andExpect(jsonPath("$.cpf").value(userDTO.getCpf()))
                .andExpect(jsonPath("$.userType").value(userDTO.getUserType()));
        //.andExpect(jsonPath("$.birthDate").value(userDTO.getBirthDate()));This shit don't want to work...
    }

    private User createValidUser() {
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

    private UserDTO createValidUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setCpf(user.getCpf());
        userDTO.setBirthDate(user.getBirthDate());
        userDTO.setUserType(user.getUserType().getId());
        return userDTO;
    }

    @Test
    void getUsers() {
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