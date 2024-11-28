package org.urlshortener.conf;

import org.springframework.context.annotation.*;

import org.urlshortener.db.dao.UrlDao;
import org.urlshortener.manager.ShortUrlManagerImpRandom;
import org.urlshortener.manager.ShortUrlManagerImpSequence;

@Configuration
public class BeanConfiguration {

    @Lazy
    public ShortUrlManagerImpSequence getShortUrlManagerImpSequence(UrlDao dao){
        return new ShortUrlManagerImpSequence(new StringBuilder(dao.getLastUrl()));
    }

    @Bean
    @Primary
    public ShortUrlManagerImpRandom getShortUrlManagerImpRandom(){
        return new ShortUrlManagerImpRandom();
    }
}
