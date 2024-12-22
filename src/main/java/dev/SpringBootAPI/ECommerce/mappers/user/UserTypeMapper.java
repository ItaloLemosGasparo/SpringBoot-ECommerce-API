package dev.SpringBootAPI.ECommerce.mappers.user;

import dev.SpringBootAPI.ECommerce.dtos.user.UserTypeDTO;
import dev.SpringBootAPI.ECommerce.models.user.UserType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserTypeMapper {
    UserTypeMapper INSTANCE = Mappers.getMapper(UserTypeMapper.class);

    UserTypeDTO toDto(UserType userType);

    UserType toEntity(UserTypeDTO userTypeDTO);
}
