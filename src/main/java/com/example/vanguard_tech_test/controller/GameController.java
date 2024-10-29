package com.example.vanguard_tech_test.controller;

import com.example.vanguard_tech_test.DTO.TotalSalesDTO;
import com.example.vanguard_tech_test.requests.GameSaleRequest;
import com.example.vanguard_tech_test.requests.GameTotalSalesRequest;
import com.example.vanguard_tech_test.interfaces.GameSaleProjection;
import com.example.vanguard_tech_test.response.Response;
import com.example.vanguard_tech_test.response.ResponseFields;
import com.example.vanguard_tech_test.service.GameService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import static com.example.vanguard_tech_test.constant.Constant.DATE_FORMAT;

@RestController
@RequestMapping("/api")
public class GameController
{
    private final GameService gameService;

    public GameController(GameService gameService)
    {
        this.gameService = gameService;
    }

    @GetMapping("/getTotalSales")
    public ResponseEntity<ResponseFields> getTotalSales(@Valid GameTotalSalesRequest gameTotalSaleRequest)
    {
        String startDate = gameTotalSaleRequest.getFromDate();
        String endDate = gameTotalSaleRequest.getToDate();
        Integer gameNo = gameTotalSaleRequest.getGameNo();
        LocalDate from = null;
        LocalDate to = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        if (startDate != null)
            from = LocalDate.parse(startDate, formatter);
        if (endDate != null)
            to = LocalDate.parse(endDate, formatter);

        List<GameSaleProjection> totalSaleList = gameService.getTotalSales(from, to, gameNo);
        BigDecimal totalSalePrice = BigDecimal.ZERO;
        Integer totalCount = totalSaleList.size();

        totalSalePrice = totalSaleList.parallelStream()
                .map(GameSaleProjection::getSalePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        TotalSalesDTO result = new TotalSalesDTO();
        result.setTotalSalePrice(totalSalePrice);
        result.setTotalNoOfGamesSold(totalCount);

        return Response.successResponse(result, "Total sales retrieved successfully", HttpStatus.OK);
    }

    @GetMapping("/getGameSales")
    public ResponseEntity<ResponseFields> getGameSales(@Valid GameSaleRequest gameRequest) {
        String startDate = gameRequest.getFromDate();
        String endDate = gameRequest.getToDate();

        int pageNo = gameRequest.getPageNo();
        BigDecimal minSalePrice = gameRequest.getMinSalePrice();
        BigDecimal maxSalePrice = gameRequest.getMaxSalePrice();

        LocalDate from = null;
        LocalDate to = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        if (startDate != null)
            from = LocalDate.parse(startDate, formatter);
        if (endDate != null)
            to = LocalDate.parse(endDate, formatter);

        Page<GameSaleProjection> gameSaleListPage = gameService.getGameSales(pageNo - 1, from, to, minSalePrice, maxSalePrice);

        return Response.successResponse(gameSaleListPage, "Game sales retrieved successfully", HttpStatus.OK);
    }
}
