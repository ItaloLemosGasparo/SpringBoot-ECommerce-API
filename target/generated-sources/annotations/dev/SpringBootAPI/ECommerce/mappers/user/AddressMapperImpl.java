package dev.SpringBootAPI.ECommerce.mappers.user;

import dev.SpringBootAPI.ECommerce.dtos.user.AddressDTO;
import dev.SpringBootAPI.ECommerce.models.user.Address;
import dev.SpringBootAPI.ECommerce.models.user.User;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-28T21:47:31-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class AddressMapperImpl implements AddressMapper {

    @Override
    public AddressDTO toDto(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setUserId( addressUserId( address ) );
        addressDTO.setId( address.getId() );
        addressDTO.setZipCode( address.getZipCode() );
        addressDTO.setStreet( address.getStreet() );
        addressDTO.setComplement( address.getComplement() );
        addressDTO.setNumber( address.getNumber() );
        addressDTO.setNeighborhood( address.getNeighborhood() );
        addressDTO.setCity( address.getCity() );
        addressDTO.setState( address.getState() );

        return addressDTO;
    }

    @Override
    public Address toEntity(AddressDTO addressDTO) {
        if ( addressDTO == null ) {
            return null;
        }

        Address address = new Address();

        address.setUser( addressDTOToUser( addressDTO ) );
        address.setId( addressDTO.getId() );
        address.setZipCode( addressDTO.getZipCode() );
        address.setStreet( addressDTO.getStreet() );
        address.setComplement( addressDTO.getComplement() );
        address.setNumber( addressDTO.getNumber() );
        address.setNeighborhood( addressDTO.getNeighborhood() );
        address.setCity( addressDTO.getCity() );
        address.setState( addressDTO.getState() );

        return address;
    }

    private UUID addressUserId(Address address) {
        if ( address == null ) {
            return null;
        }
        User user = address.getUser();
        if ( user == null ) {
            return null;
        }
        UUID id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected User addressDTOToUser(AddressDTO addressDTO) {
        if ( addressDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( addressDTO.getUserId() );

        return user;
    }
}
