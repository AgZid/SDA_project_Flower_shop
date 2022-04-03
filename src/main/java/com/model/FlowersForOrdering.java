package com.model;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class FlowersForOrdering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer quantity;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "flowerId")
    private Flower flower;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "flowersOrderId")
    private FlowersOrder flowersOrder;
}
