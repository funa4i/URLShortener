package org.urlshortener.services;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.urlshortener.Db.UrlShortenerDb;


@Service
@EnableScheduling
@AllArgsConstructor
public class TaskSchedulerUrl {

    private final UrlShortenerDb db;

    private final EmailServ emailServ;

    @Scheduled(cron = "* * 0 * * *")
    public void scheduleTaskUsingCronExpression() {
           var ls = db.getAllExpiredLinks();
           if (ls.isEmpty()){
               return;
           }
           ls.forEach((x) -> emailServ.expiredUrl(x.a, x.b, x.c));
           db.deleteExpiredLinks();
    }
}
