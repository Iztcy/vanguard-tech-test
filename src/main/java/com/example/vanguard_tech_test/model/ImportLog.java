package com.example.vanguard_tech_test.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

@Entity
@Table(name = "import_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImportLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "total_records", nullable = false)
    private int totalRecords;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name ="created_at")
    private Timestamp createdAt;

    @Column(name ="updated_at")
    private Timestamp updatedAt;
}
