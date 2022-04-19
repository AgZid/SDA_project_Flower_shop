package com.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.criteria.Order;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fullName;
    private String email;
    private String phoneNumber;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FlowersOrder> orders = new ArrayList<>();
//    public List<FlowersOrder> getOrders() { return orders;}
//    public void setOrders(List<FlowersOrder> orders) {this.orders = orders;}

    public void addOrder(FlowersOrder order) {
        if (orders == null) {
            orders = new ArrayList<>();
        }

        orders.add(order);
    }

}
