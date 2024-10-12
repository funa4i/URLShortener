package org.urlshortener.Mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import org.urlshortener.Dto.UrlTransfer;
import org.urlshortener.Entities.Url;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUrlTransferMapper {

    @Mapping(target = "fullUrl", source = "url")
    @Mapping(target ="userMail", expression = "java(" +
            "new org.urlshortener.Entities.User(urlTransfer.getMail()))")
    Url toUrl(UrlTransfer urlTransfer);


    @Mapping(target = "url", expression = "java(pattern + url.getShortUrl())")
    @Mapping(target = "mail", expression= "java(url.getUserMail().getMail())")
    UrlTransfer toUrlTransfer(Url url, String pattern);
}
