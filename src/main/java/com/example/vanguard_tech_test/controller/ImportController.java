package com.example.vanguard_tech_test.controller;

import com.example.vanguard_tech_test.exceptions.ImportFileExistException;
import com.example.vanguard_tech_test.repository.ImportLogRepository;
import com.example.vanguard_tech_test.response.Response;
import com.example.vanguard_tech_test.response.ResponseFields;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.vanguard_tech_test.service.ImportService;

@RestController
@RequestMapping("/api")
public class ImportController {
    private final ImportService importService;
    private final ImportLogRepository importLogRepository;

    public ImportController(ImportService importService, ImportLogRepository importLogRepository)
    {
        this.importService = importService;
        this.importLogRepository = importLogRepository;
    }

    @PostMapping("/import")
    public ResponseEntity<ResponseFields> importCSV(@RequestParam("file")MultipartFile file) throws Exception {

        if(!isCSV(file))
            throw new IllegalArgumentException("File format is not csv");

        if(importLogRepository.countByName(file.getOriginalFilename()) > 0)
            throw new ImportFileExistException("File has already been imported");

        importService.importCSV(file);

        return Response.successResponse(null, "CSV import successfully", HttpStatus.OK);
    }

    private boolean isCSV(MultipartFile file) {
        return file.getContentType() != null && file.getContentType().equals("text/csv");
    }
}
