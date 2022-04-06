package com.model;

import com.enumerators.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private LocalDateTime orderDate;
    private LocalDate deliveryDay;
    private String deliveryAddress;
    private OrderStatus orderStatus;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "flowersOrder")
    private List<OrderedEntry> orderedFlowersQuantities;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;
}
