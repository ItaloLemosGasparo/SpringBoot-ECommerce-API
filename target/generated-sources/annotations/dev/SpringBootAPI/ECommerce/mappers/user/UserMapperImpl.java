package dev.SpringBootAPI.ECommerce.mappers.user;

import dev.SpringBootAPI.ECommerce.dtos.user.UserDTO;
import dev.SpringBootAPI.ECommerce.models.user.User;
import dev.SpringBootAPI.ECommerce.models.user.UserType;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-22T17:52:13-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setUserType( userUserTypeId( user ) );
        userDTO.setId( user.getId() );
        userDTO.setName( user.getName() );
        userDTO.setEmail( user.getEmail() );
        userDTO.setBirthDate( user.getBirthDate() );
        userDTO.setActive( user.getActive() );
        userDTO.setCreatedAt( user.getCreatedAt() );
        userDTO.setUpdatedAt( user.getUpdatedAt() );

        userDTO.setUrl( "http://localhost:8080/api/user/" + user.getId() );

        return userDTO;
    }

    @Override
    public User toEntity(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setUserType( userDTOToUserType( userDTO ) );
        user.setId( userDTO.getId() );
        user.setName( userDTO.getName() );
        user.setEmail( userDTO.getEmail() );
        user.setBirthDate( userDTO.getBirthDate() );
        user.setActive( userDTO.getActive() );
        user.setCreatedAt( userDTO.getCreatedAt() );
        user.setUpdatedAt( userDTO.getUpdatedAt() );

        return user;
    }

    private Integer userUserTypeId(User user) {
        if ( user == null ) {
            return null;
        }
        UserType userType = user.getUserType();
        if ( userType == null ) {
            return null;
        }
        Integer id = userType.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserType userDTOToUserType(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        UserType userType = new UserType();

        userType.setId( userDTO.getUserType() );

        return userType;
    }
}
