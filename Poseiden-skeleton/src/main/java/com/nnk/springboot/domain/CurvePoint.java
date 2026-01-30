package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "curve_point")
@NoArgsConstructor
@Getter
@Setter
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @NotNull(message = "Curve Id is mandatory")
    @Min(value = 1, message = "Curve Id must be greater than 0")
    @Column(name = "curve_id")
    private Integer curveId;

    @Column(name = "as_of_date")
    private LocalDateTime asOfDate;

    @Column(name = "term")
    private Double term;

    @Column(name = "curve_value")
    private Double value;

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
}
