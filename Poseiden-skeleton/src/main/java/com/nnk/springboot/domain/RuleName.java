package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "RuleName")
@NoArgsConstructor
@Getter
@Setter
public class RuleName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Size(max = 125)
    @Column(name = "name", length = 125)
    private String name;

    @Size(max = 125)
    @Column(name = "description", length = 125)
    private String description;

    @Size(max = 125)
    @Column(name = "json", length = 125)
    private String json;

    @Size(max = 512)
    @Column(name = "template", length = 512)
    private String template;

    @Size(max = 125)
    @Column(name = "sqlStr", length = 125)
    private String sqlStr;

    @Size(max = 125)
    @Column(name = "sqlPart", length = 125)
    private String sqlPart;
}
