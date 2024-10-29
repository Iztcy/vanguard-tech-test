package com.example.vanguard_tech_test.interfaces;

import java.math.BigDecimal;
import java.sql.Timestamp;

public interface GameSaleProjection {
    Long getId();
    Integer getGameNo();
    String getGameName();
    String getGameCode();
    Integer getType();
    BigDecimal getCostPrice();
    BigDecimal getTax();
    BigDecimal getSalePrice();
    Timestamp getDateOfSale();
}
