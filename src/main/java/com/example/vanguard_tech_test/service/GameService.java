package com.example.vanguard_tech_test.service;

import com.example.vanguard_tech_test.interfaces.GameSaleProjection;
import com.example.vanguard_tech_test.repository.GameRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.example.vanguard_tech_test.constant.Constant.NO_OF_ITEMS;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository){
        this.gameRepository = gameRepository;
    }

    public List<GameSaleProjection> getTotalSales(LocalDate startDate, LocalDate endDate, Integer gameNo){
        return gameRepository.findAllTotalSalesProjectedBy(startDate, endDate, gameNo);
    }

    public Page<GameSaleProjection> getGameSales(int pageNo, LocalDate startDate, LocalDate endDate,
                                                BigDecimal minSalePrice, BigDecimal maxSalePrice){
        Pageable pageable = PageRequest.of(pageNo, NO_OF_ITEMS);
        return gameRepository.findAllGameSalesProjectedBy(startDate, endDate, minSalePrice, maxSalePrice, pageable);
    }
}
