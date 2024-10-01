package org.urlshortener;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.urlshortener.Db.Dao.URLDao;
import org.urlshortener.Db.Repository.UrlsRep;
import org.urlshortener.services.ShortUrlManagerImpRandom;
import org.urlshortener.services.ShortUrlManagerImpSequence;

@Configuration
public class BeanConfiguration {



    @Bean(name = "urlManager")
    public ShortUrlManagerImpSequence getShortUrlManagerImpSequence(URLDao dao){
        return new ShortUrlManagerImpSequence(new StringBuilder(dao.getLastUrl()));
    }


    @Bean
    @Primary
    public ShortUrlManagerImpRandom getShortUrlManagerImpRandom(){
        return new ShortUrlManagerImpRandom();
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public StringBuilder getStringBuilder(){
        return new StringBuilder();
    }
}
