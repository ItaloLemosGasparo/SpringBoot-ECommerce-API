package dev.SpringBootAPI.ECommerce.services.user;

import dev.SpringBootAPI.ECommerce.dtos.user.PhoneDTO;
import dev.SpringBootAPI.ECommerce.mappers.user.PhoneMapper;
import dev.SpringBootAPI.ECommerce.models.user.Phone;
import dev.SpringBootAPI.ECommerce.repositories.user.PhoneRepository;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PhoneService {
    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private PhoneMapper phoneMapper;

    //Create
    public PhoneDTO createPhone(@Valid Phone phone) {
        return phoneMapper.toDTO(phoneRepository.save(phone));
    }
    //

    //Read
    public List<PhoneDTO> findAllByUserId(UUID id) {
        return phoneRepository.findAllByUserId(id).stream().map(phoneMapper::toDTO).collect(Collectors.toList());
    }

    public Optional<PhoneDTO> findById(Long id) {
        return phoneRepository.findById(id).map(phoneMapper::toDTO);
    }
    //

    //Update
    public PhoneDTO updatePhone(Long phoneId, PhoneDTO updatePhoneDTO) {
        Phone existingPhone = phoneRepository.findById(phoneId).get();

        if (updatePhoneDTO.getDdd() != null)
            existingPhone.setDdd(updatePhoneDTO.getDdd());

        if (updatePhoneDTO.getNumber() != null)
            existingPhone.setNumber(updatePhoneDTO.getNumber());

        return phoneMapper.toDTO(phoneRepository.save(existingPhone));
    }

    public PhoneDTO confirmPhone(Long phoneId) {
        Phone existingPhone = phoneRepository.findById(phoneId).get();

        existingPhone.setConfirmed(true);

        return phoneMapper.toDTO(phoneRepository.save(existingPhone));
    }
    //

    //Delete
    public void deletePhone(Long id) {
        phoneRepository.deleteById(id);
    }
    //
}
