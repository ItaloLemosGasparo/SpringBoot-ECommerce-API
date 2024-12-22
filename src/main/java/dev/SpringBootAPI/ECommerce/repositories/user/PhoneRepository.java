package dev.SpringBootAPI.ECommerce.repositories.user;

import dev.SpringBootAPI.ECommerce.models.user.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
    List<Phone> findAllByUserId(UUID id);
}
