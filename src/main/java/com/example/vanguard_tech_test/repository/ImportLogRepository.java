package com.example.vanguard_tech_test.repository;


import com.example.vanguard_tech_test.interfaces.GameSaleProjection;
import com.example.vanguard_tech_test.model.GameSale;
import com.example.vanguard_tech_test.model.ImportLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ImportLogRepository extends JpaRepository<ImportLog, Long> {

    long countByName(String name);
}
