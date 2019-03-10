package com.lazycece.admin.server.mysql.mapper.provider;

/**
 * @author lazycece
 */
public class CommonSqlBuilder {

    /**
     * build limit-sql
     *
     * @param page  page
     * @param limit limit
     * @return ""
     */
    public static String limitSqlBuilder(Integer page, Integer limit) {
        String limitSql = "";
        if (page != null && limit != null) {
            limitSql += new StringBuilder().append(" limit ").append((page - 1) * limit).append(",").append(limit);
        }
        return limitSql;
    }
}
