package com.carDealer.sql;

public class SqlQueries {

    public static final String SELECT_TOTAL_SALES_BY_CUSTOMER =
            "SELECT cs FROM Customer cs " +
            "JOIN cs.cars sales " +
            "JOIN sales.car c " +
            "JOIN c.parts p " +
            "GROUP BY cs.id " +
            "ORDER BY sum(p.price) DESC, cs.cars.size ";
}
