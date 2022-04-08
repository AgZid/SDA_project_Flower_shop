package com.model;

import lombok.*;

import javax.persistence.*;
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
    private String color;
    private Integer amount;

    @ToString.Exclude
    @OneToMany(mappedBy = "flower")
    private List<OrderedEntry> orderedEntry;

}