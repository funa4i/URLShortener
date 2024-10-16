package org.urlshortener.Conf;

import org.springframework.context.annotation.*;

import org.urlshortener.Db.Dao.UrlDao;
import org.urlshortener.Manager.ShortUrlManagerImpRandom;
import org.urlshortener.Manager.ShortUrlManagerImpSequence;

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
