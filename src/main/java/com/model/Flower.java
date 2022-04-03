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
    private Integer id;
    private String name;
    private Double price;
    private Double length;
    private String color;
    private String intakeDate;
    private String  expires;
    private Integer amount;
//     private Integer suppler_id;

    @ToString.Exclude
    @OneToMany(mappedBy = "flower")
    private List<FlowersForOrdering> flowersForOrdering;

}