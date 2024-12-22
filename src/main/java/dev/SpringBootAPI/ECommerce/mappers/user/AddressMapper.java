package dev.SpringBootAPI.ECommerce.mappers.user;

import dev.SpringBootAPI.ECommerce.dtos.user.AddressDTO;
import dev.SpringBootAPI.ECommerce.models.user.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    @Mapping(source = "user.id", target = "userId")
    AddressDTO toDto(Address address);

    @Mapping(source = "userId", target = "user.id")
    Address toEntity(AddressDTO addressDTO);
}
