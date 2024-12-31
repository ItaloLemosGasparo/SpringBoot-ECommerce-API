package dev.SpringBootAPI.ECommerce.mappers.user;

import dev.SpringBootAPI.ECommerce.dtos.user.PhoneDTO;
import dev.SpringBootAPI.ECommerce.models.user.Phone;
import dev.SpringBootAPI.ECommerce.models.user.User;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-30T20:58:06-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class PhoneMapperImpl implements PhoneMapper {

    @Override
    public PhoneDTO toDTO(Phone phone) {
        if ( phone == null ) {
            return null;
        }

        PhoneDTO phoneDTO = new PhoneDTO();

        phoneDTO.setUserId( phoneUserId( phone ) );
        phoneDTO.setId( phone.getId() );
        phoneDTO.setDdd( phone.getDdd() );
        phoneDTO.setNumber( phone.getNumber() );
        phoneDTO.setConfirmed( phone.isConfirmed() );

        return phoneDTO;
    }

    @Override
    public Phone toEntity(PhoneDTO phoneDTO) {
        if ( phoneDTO == null ) {
            return null;
        }

        Phone phone = new Phone();

        phone.setUser( phoneDTOToUser( phoneDTO ) );
        phone.setId( phoneDTO.getId() );
        phone.setDdd( phoneDTO.getDdd() );
        phone.setNumber( phoneDTO.getNumber() );
        phone.setConfirmed( phoneDTO.isConfirmed() );

        return phone;
    }

    private UUID phoneUserId(Phone phone) {
        if ( phone == null ) {
            return null;
        }
        User user = phone.getUser();
        if ( user == null ) {
            return null;
        }
        UUID id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected User phoneDTOToUser(PhoneDTO phoneDTO) {
        if ( phoneDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( phoneDTO.getUserId() );

        return user;
    }
}
