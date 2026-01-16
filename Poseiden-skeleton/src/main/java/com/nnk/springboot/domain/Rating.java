package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "Moody's Rating is mandatory")
    @Size(max = 125, message = "Moody's Rating must be <= 125 characters")
    @Column(name = "moodys_rating", length = 125)
    private String moodysRating;

    @NotBlank(message = "Sand Prating is mandatory")
    @Size(max = 125)
    @Column(name = "sand_prating", length = 125)
    private String sandPrating;

    @NotBlank(message = "Fitch Rating is mandatory")
    @Size(max = 125)
    @Column(name = "fitch_rating", length = 125)
    private String fitchRating;

    @NotNull(message = "Order number is mandatory")
    @Min(value = 1, message = "Order number must be >= 1")
    @Column(name = "order_number")
    private Integer orderNumber;
}
