package org.urlshortener.Mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;
import org.urlshortener.Dto.UserValid;
import org.urlshortener.Entities.User;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IUserValidMapper {


    @Mapping(target = "mail", source = "email")
    @Mapping(target = "password", expression = "java(" +
            "new org.urlshortener.Manager.HashManagerImp()" +
            ".getHashFrom(userValid.getPassword(),userValid.getEmail())" +
            ")")
    User toUser(UserValid userValid);

}
