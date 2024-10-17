package org.urlshortener.services;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${app.default.urlPath}")
    private String URL_PATTERN;

    // Таймер на проверку протухших ссылок
    @Scheduled(cron = "* * 0 * * *")
    public void scheduleTaskUsingCronExpression() {
           var ls = db.getAllExpiredLinks();
           if (ls.isEmpty()){
               return;
           }
        ls.forEach((x) -> emailServ.expiredUrl(x.a, URL_PATTERN + x.b, x.c));
           db.deleteExpiredLinks();
    }
}
