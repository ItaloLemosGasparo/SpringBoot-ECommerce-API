package dev.SpringBootAPI.ECommerce.services.user;

import dev.SpringBootAPI.ECommerce.dtos.user.UserDTO;
import dev.SpringBootAPI.ECommerce.mappers.user.UserMapper;
import dev.SpringBootAPI.ECommerce.models.user.Password;
import dev.SpringBootAPI.ECommerce.models.user.User;
import dev.SpringBootAPI.ECommerce.repositories.user.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EntityManager entityManager;

    //Create
    @Transactional
    public UserDTO createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toDto(userRepository.save(user));
    }
    //

    //Read
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    public Optional<UserDTO> findById(UUID uuid) {
        return userRepository.findById(uuid).map(userMapper::toDto);
    }
    //

    //Update
    @Transactional
    public UserDTO updateUser(UUID id, UserDTO updatedUserDTO) {
        User existingUser = userRepository.findById(id).get();

        if (updatedUserDTO.getName() != null)
            existingUser.setName(updatedUserDTO.getName());

        if (updatedUserDTO.getEmail() != null)
            existingUser.setEmail(updatedUserDTO.getEmail());

        if (updatedUserDTO.getBirthDate() != null)
            existingUser.setBirthDate(updatedUserDTO.getBirthDate());

        entityManager.merge(existingUser);

        return userMapper.toDto(userRepository.save(existingUser));
    }

    public void updateUserPassword(UUID id, Password password) {
        User user = userRepository.findById(id).get();
        user.setPassword(passwordEncoder.encode(password.getPassword()));
    }

    public void inactiveActiveUser(UUID id) {
        User user = userRepository.findById(id).get();
        user.setActive(!user.getActive());
        userRepository.save(user);
    }
    //

    //Delete
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
    //
}