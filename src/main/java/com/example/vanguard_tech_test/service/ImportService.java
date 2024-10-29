package com.example.vanguard_tech_test.service;

import com.example.vanguard_tech_test.model.GameSale;
import com.example.vanguard_tech_test.model.ImportLog;
import com.example.vanguard_tech_test.repository.ImportLogRepository;
import jakarta.persistence.Table;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.multipart.MultipartFile;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.vanguard_tech_test.constant.Constant.*;

@Service
public class ImportService {
    private final JdbcTemplate jdbcTemplate;
    private final ImportLogRepository importLogRepository;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private Integer batchSize;

    public ImportService(JdbcTemplate jdbcTemplate, ImportLogRepository importLogRepository, PlatformTransactionManager platformTransactionManager)
    {
        this.jdbcTemplate = jdbcTemplate;
        this.importLogRepository = importLogRepository;
    }

    public void importCSV(MultipartFile file) throws Exception {
        CSVReader csvReader = null;

        try {
            csvReader = new CSVReader(new InputStreamReader(file.getInputStream()));
            List<String[]> rows = csvReader.readAll();
            List<GameSale> gameSaleList = new ArrayList<>();
            boolean firstRow = true;

            for (String[] row : rows) {
                if (firstRow) {
                    firstRow = false; // Skip the first row
                    continue;
                }

                GameSale gs = new GameSale();
                gs.setId(Long.parseLong(row[0]));
                gs.setGameNo(Integer.parseInt(row[1]));
                gs.setGameName(row[2]);
                gs.setGameCode(row[3]);
                gs.setType(Integer.parseInt(row[4]));
                gs.setCostPrice(new BigDecimal(row[5]));
                gs.setTax(new BigDecimal(row[6]));
                gs.setSalePrice(new BigDecimal(row[7]));
                gs.setDateOfSale(Timestamp.valueOf(row[8]));

                gameSaleList.add(gs);
            }

            ImportLog log = new ImportLog();
            log.setName(file.getOriginalFilename());
            log.setTotalRecords(gameSaleList.size());
            log.setStatus(IN_PROGRESS);
            log.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            log.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            ImportLog savedLogged = importLogRepository.save(log); // Save log entry
            bulkInsert(gameSaleList, savedLogged);

        } catch(IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("An error has occurred.");
        } finally {
            if(csvReader != null) {
                try{
                    csvReader.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void bulkInsert(List<GameSale> gameSaleList, ImportLog savedLogged) throws Exception
    {
        ExecutorService executor = Executors.newFixedThreadPool(NO_OF_THREAD); // Create a thread pool
        try
        {
            int totalRecords = gameSaleList.size();
            List<Future<?>> futures = new ArrayList<>(); // List to hold Future objects

            for (int i = 0; i < totalRecords; i += batchSize) {
                int endIndex = Math.min(i + batchSize, totalRecords);
                List<GameSale> batchList = gameSaleList.subList(i, endIndex);

                Future<?> future = executor.submit(() -> {
                    batchInsertion(batchList);
                });
                futures.add(future);
            }

            for (Future<?> future : futures) {
                future.get(30, TimeUnit.SECONDS); // Wait for up to 30 seconds
            }

            savedLogged.setStatus(COMPLETED);
            savedLogged.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            importLogRepository.save(savedLogged);
        }
        catch(Exception e)
        {
            savedLogged.setStatus(FAILURE);
            savedLogged.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            importLogRepository.save(savedLogged);
            throw e;
        }
        finally{
            executor.shutdown(); // Shutdown the executor
        }
    }

    private void batchInsertion(List<GameSale> batchList)
    {
        String sql = String.format(
            "INSERT INTO %s (game_no, game_name, game_code, type, cost_price, tax, sale_price, date_of_sale) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
            GameSale.class.getAnnotation(Table.class).name()
        );

        jdbcTemplate.batchUpdate(sql, batchList, batchList.size(), (ps, gameSale) -> {
            ps.setInt(1, gameSale.getGameNo());
            ps.setString(2, gameSale.getGameName());
            ps.setString(3, gameSale.getGameCode());
            ps.setInt(4, gameSale.getType());
            ps.setBigDecimal(5, gameSale.getCostPrice());
            ps.setBigDecimal(6, gameSale.getTax());
            ps.setBigDecimal(7, gameSale.getSalePrice());
            ps.setTimestamp(8, gameSale.getDateOfSale());
        });
    }
}
