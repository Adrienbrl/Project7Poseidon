package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "Trade")
@NoArgsConstructor
@Getter
@Setter
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TradeId")
    private Integer tradeId;

    @NotBlank
    @Size(max = 30)
    @Column(name = "account", length = 30)
    private String account;

    @NotBlank
    @Size(max = 30)
    @Column(name = "type", length = 30)
    private String type;

    @Column(name = "buyQuantity")
    private Double buyQuantity;

    @Column(name = "sellQuantity")
    private Double sellQuantity;

    @Column(name = "buyPrice")
    private Double buyPrice;

    @Column(name = "sellPrice")
    private Double sellPrice;

    @Column(name = "tradeDate")
    private LocalDateTime tradeDate;

    @Size(max = 125)
    @Column(name = "security", length = 125)
    private String security;

    @Size(max = 10)
    @Column(name = "status", length = 10)
    private String status;

    @Size(max = 125)
    @Column(name = "trader", length = 125)
    private String trader;

    @Size(max = 125)
    @Column(name = "benchmark", length = 125)
    private String benchmark;

    @Size(max = 125)
    @Column(name = "book", length = 125)
    private String book;

    @Size(max = 125)
    @Column(name = "creationName", length = 125)
    private String creationName;

    @CreationTimestamp
    @Column(name = "creationDate")
    private LocalDateTime creationDate;

    @Size(max = 125)
    @Column(name = "revisionName", length = 125)
    private String revisionName;

    @UpdateTimestamp
    @Column(name = "revisionDate")
    private LocalDateTime revisionDate;

    @Size(max = 125)
    @Column(name = "dealName", length = 125)
    private String dealName;

    @Size(max = 125)
    @Column(name = "dealType", length = 125)
    private String dealType;

    @Size(max = 125)
    @Column(name = "sourceListId", length = 125)
    private String sourceListId;

    @Size(max = 125)
    @Column(name = "side", length = 125)
    private String side;

}
