package com.example.vanguard_tech_test.requests;

import java.math.BigDecimal;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import static com.example.vanguard_tech_test.constant.Constant.DATE_FORMAT;


@Getter
@Setter
public class GameSaleRequest {
    @Min(value = 1, message = "Page number must be greater than 0")
    private int pageNo;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Invalid date format. Please use '" + DATE_FORMAT + "'")
    private String fromDate;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Invalid date format. Please use '" + DATE_FORMAT + "'")
    private String toDate;
    private BigDecimal minSalePrice;
    private BigDecimal maxSalePrice;

}
