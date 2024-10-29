package com.example.vanguard_tech_test.repository;


import com.example.vanguard_tech_test.interfaces.GameSaleProjection;
import com.example.vanguard_tech_test.model.GameSale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface GameRepository extends JpaRepository<GameSale, Long> {

    @Query(value = "SELECT * FROM game_sales g WHERE (:startDate IS NULL OR DATE(g.date_of_sale) >= :startDate) " +
            "AND (:endDate IS NULL OR DATE(g.date_of_sale) <= :endDate) " +
            "AND (:minSalePrice IS NULL OR g.sale_price >= :minSalePrice) " +
            "AND (:maxSalePrice IS NULL OR g.sale_price <= :maxSalePrice) ", nativeQuery = true)
    Page<GameSaleProjection> findAllGameSalesProjectedBy(LocalDate startDate,
                                                LocalDate endDate,
                                                BigDecimal minSalePrice,
                                                BigDecimal maxSalePrice, Pageable pageable);


    @Query(value = "SELECT * FROM game_sales g WHERE (:startDate IS NULL OR DATE(g.date_of_sale) >= :startDate) " +
            "AND (:endDate IS NULL OR DATE(g.date_of_sale) <= :endDate) " +
            "AND (:gameNo IS NULL OR g.game_no = :gameNo) ", nativeQuery = true)
    List<GameSaleProjection> findAllTotalSalesProjectedBy(LocalDate startDate,
                                                          LocalDate endDate, Integer gameNo);
}
