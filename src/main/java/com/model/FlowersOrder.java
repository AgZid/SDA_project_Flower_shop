package com.model;

import com.enumerators.OrderStatus;
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
    private Integer id;

    private LocalDate orderDate;
    private LocalDate deliveryDay;
    private OrderStatus orderStatus;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "flowersOrder", orphanRemoval = true)
    private List<FlowersForOrdering> flowersForOrderings;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;
}
