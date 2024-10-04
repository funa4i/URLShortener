package org.urlshortener.Conf;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;

import org.urlshortener.Db.Dao.URLDao;
import org.urlshortener.Db.Repository.UrlsRep;
import org.urlshortener.services.ShortUrlManagerImpRandom;
import org.urlshortener.services.ShortUrlManagerImpSequence;

@Configuration
public class BeanConfiguration {

    @Lazy
    public ShortUrlManagerImpSequence getShortUrlManagerImpSequence(URLDao dao){
        return new ShortUrlManagerImpSequence(new StringBuilder(dao.getLastUrl()));
    }


    @Bean
    @Primary
    public ShortUrlManagerImpRandom getShortUrlManagerImpRandom(){
        return new ShortUrlManagerImpRandom();
    }

//    @Bean
//    public String getString(){
//        return "";
//    }
}
