package org.urlshortener.Db.Dao;

import org.antlr.v4.runtime.misc.Triple;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface UrlDao {
    String getLastUrl();

    List<Object[]> getMailOfExpiredLinks();

    void  deleteExpiredLinks();
}
