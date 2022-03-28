package com.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer flowerId;
    private String name;
    private Integer flowerPrice;
    private double length;
    private String color;
    private LocalDate intakeDate;
    private LocalDate expires;
    private Integer amount;
//     private Integer suppler_id;

    @ToString.Exclude
    @OneToMany(mappedBy = "flower")
    private List<FlowersForOrdering> flowersForOrdering;

}