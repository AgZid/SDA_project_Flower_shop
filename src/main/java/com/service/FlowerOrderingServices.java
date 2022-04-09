package com.service;

import com.enumerators.OrderStatus;
import com.model.Flower;
import com.model.FlowersOrder;
import com.model.OrderedEntry;
import com.repository.FlowerRepository;
import com.repository.FlowersOrderRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class FlowerOrderingServices {

    FlowerRepository flowerRepository = new FlowerRepository();
    FlowersOrderRepository orderRepository = new FlowersOrderRepository();

    public void reduceFlowerAmount(OrderedEntry orderedEntry) {
        Flower flowerToReduceAmount = flowerRepository.findById(orderedEntry.getFlower().getId());
        flowerToReduceAmount.setAmount(flowerToReduceAmount.getAmount() - orderedEntry.getQuantity());
        flowerRepository.createOrUpdate(flowerToReduceAmount);
    }

    public void restoreFlowerAmount(OrderedEntry orderedEntry) {
        Flower flowerToRestoreAmount = flowerRepository.findById(orderedEntry.getFlower().getId());
        flowerToRestoreAmount.setAmount(flowerToRestoreAmount.getAmount() + orderedEntry.getQuantity());
        flowerRepository.createOrUpdate(flowerToRestoreAmount);
    }

    public void cancelOrder(Integer orderId) {
        FlowersOrder flowersOrder = orderRepository.findById(orderId);
        flowersOrder.getOrderedEntries().stream().forEach(orderedEntry -> restoreFlowerAmount(orderedEntry));

        flowersOrder.setOrderStatus(OrderStatus.CANCELED);
        orderRepository.createOrUpdate(flowersOrder);
    }

//    public void removeOrder(Integer orderId) {
//        FlowersOrder flowersOrder = orderRepository.findById(orderId);
//
////        flowersOrder.getCustomer().getOrders().remove(flowersOrder);
//
//        if (flowersOrder.getOrderStatus() == OrderStatus.ORDERED) {
//            flowersOrder.getOrderedEntries().stream()
//                    .forEach(orderedEntry -> restoreFlowerAmount(orderedEntry));
//        }
//        orderRepository.deleteRecord(flowersOrder);
//    }

}
