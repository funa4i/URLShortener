package org.urlshortener.Db.Dao;

import java.util.List;

public interface UrlDao {
    String getLastUrl();

    List<Object[]> getMailOfExpiredLinks();

    void  deleteExpiredLinks();
}
