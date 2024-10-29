package com.example.vanguard_tech_test.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "game_sales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameSale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="game_no")
    private Integer gameNo;

    @Column(name ="game_name")
    private String gameName;

    @Column(name ="game_code")
    private String gameCode;

    @Column(name ="type")
    private Integer type;

    @Column(name ="cost_price")
    private BigDecimal costPrice;

    @Column(name ="tax")
    private BigDecimal  tax;

    @Column(name ="sale_price")
    private BigDecimal salePrice;

    @Column(name ="date_of_sale")
    private Timestamp dateOfSale;

    @Column(name ="created_at")
    private Timestamp createdAt;

    @Column(name ="updated_at")
    private Timestamp updatedAt;
}
