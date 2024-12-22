package dev.SpringBootAPI.ECommerce.repositories.user;

import dev.SpringBootAPI.ECommerce.models.user.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findAllByUser_Id(UUID userId);
}
