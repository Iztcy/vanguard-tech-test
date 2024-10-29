package com.example.vanguard_tech_test.requests;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameTotalSalesRequest {

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Invalid date format. Please use 'YYYY-MM-DD'.")
    private String fromDate;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Invalid date format. Please use 'YYYY-MM-DD'.")
    private String toDate;

    private Integer gameNo;
}
