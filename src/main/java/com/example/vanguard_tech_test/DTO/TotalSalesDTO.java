package com.example.vanguard_tech_test.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class TotalSalesDTO
{
    private BigDecimal totalSalePrice = BigDecimal.ZERO;
    private Integer totalNoOfGamesSold = 0;

}
