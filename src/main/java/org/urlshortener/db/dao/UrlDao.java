package org.urlshortener.db.dao;

import java.util.List;

public interface UrlDao {
    String getLastUrl();

    List<Object[]> getMailOfExpiredLinks();

    void  deleteExpiredLinks();
}
