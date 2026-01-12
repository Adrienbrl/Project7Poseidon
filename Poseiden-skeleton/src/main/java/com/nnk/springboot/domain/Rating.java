package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rating")
@NoArgsConstructor
@Getter
@Setter
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Size(max = 125)
    @Column(name = "moodys_rating", length = 125)
    private String moodysRating;

    @Size(max = 125)
    @Column(name = "sand_prating", length = 125)
    private String sandPrating;

    @Size(max = 125)
    @Column(name = "fitch_rating", length = 125)
    private String fitchRating;

    @Column(name = "order_number")
    private Integer orderNumber;
}
