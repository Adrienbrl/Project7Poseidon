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
@Table(name = "bid_list")
@NoArgsConstructor
@Getter
@Setter
public class BidList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid_list_id")
    private Integer bidListId;

    @Column(name = "username")
    private String username;

    @NotBlank(message = "Account is mandatory")
    @Size(max = 30)
    @Column(name = "account", nullable = false, length = 30)
    private String account;

    @NotBlank(message = "Type is mandatory")
    @Size(max = 30)
    @Column(name = "type", nullable = false, length = 30)
    private String type;

    @Column(name = "bid_quantity")
    private Double bidQuantity;

    @Column(name = "ask_quantity")
    private Double askQuantity;

    @Column(name = "bid")
    private Double bid;

    @Column(name = "ask")
    private Double ask;

    @Size(max = 125)
    @Column(name = "benchmark", length = 125)
    private String benchmark;

    @Column(name = "bid_list_date")
    private LocalDateTime bidListDate;

    @Size(max = 125)
    @Column(name = "commentary", length = 125)
    private String commentary;

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
    @Column(name = "book", length = 125)
    private String book;

    @Size(max = 125)
    @Column(name = "creation_name", length = 125)
    private String creationName;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private LocalDateTime creationDate;

    @Size(max = 125)
    @Column(name = "revision_name", length = 125)
    private String revisionName;

    @UpdateTimestamp
    @Column(name = "revision_date")
    private LocalDateTime revisionDate;

    @Size(max = 125)
    @Column(name = "deal_name", length = 125)
    private String dealName;

    @Size(max = 125)
    @Column(name = "deal_type", length = 125)
    private String dealType;

    @Size(max = 125)
    @Column(name = "source_list_id", length = 125)
    private String sourceListId;

    @Size(max = 125)
    @Column(name = "side", length = 125)
    private String side;
}
