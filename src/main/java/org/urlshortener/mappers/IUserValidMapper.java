package org.urlshortener.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;
import org.urlshortener.dto.UserValid;
import org.urlshortener.entities.User;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IUserValidMapper {


    @Mapping(target = "mail", source = "email")
    @Mapping(target = "password", expression = "java(" +
            "new org.urlshortener.manager.HashManagerImp()" +
            ".getHashFrom(userValid.getPassword(),userValid.getEmail())" +
            ")")
    User toUser(UserValid userValid);

}
