package org.urlshortener.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.urlshortener.dto.UrlTransfer;
import org.urlshortener.entities.Url;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUrlTransferMapper {

    @Mapping(target = "fullUrl", source = "url")
    @Mapping(target ="user", expression = "java(" +
            "new org.urlshortener.entities.User(urlTransfer.getMail()))")
    Url toUrl(UrlTransfer urlTransfer);


    @Mapping(target = "url", expression = "java(pattern + url.getShortUrl())")
    @Mapping(target = "mail", expression= "java(url.getUser().getMail())")
    UrlTransfer toUrlTransfer(Url url, String pattern);
}
