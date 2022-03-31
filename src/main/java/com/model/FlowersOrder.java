package com.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class FlowersOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer flowersOrderId;

    // private boolean status;
    private LocalDate orderDate;
    private LocalDate deliveryDay;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "flowersOrder")
    private List<FlowersForOrdering> flowersForOrderings;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;
}
