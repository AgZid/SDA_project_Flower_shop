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
        flowerRepository.createAndUpdate(flowerToReduceAmount);
    }

    public void restoreFlowerAmount(OrderedEntry orderedEntry) {
        Flower flowerToRestoreAmount = flowerRepository.findById(orderedEntry.getFlower().getId());
        flowerToRestoreAmount.setAmount(flowerToRestoreAmount.getAmount() + orderedEntry.getQuantity());
        flowerRepository.createAndUpdate(flowerToRestoreAmount);
    }

    public void cancelOrder(Integer orderId) {
        FlowersOrder flowersOrder = orderRepository.findById(orderId);
        flowersOrder.getOrderedEntries().stream().forEach(orderedEntry -> restoreFlowerAmount(orderedEntry));

        flowersOrder.setOrderStatus(OrderStatus.CANCELED);
        orderRepository.createAndUpdate(flowersOrder);
    }

    public void changeOrderEntity (Integer orderId) {

    }
}
