package com.service;

import com.model.Flower;
import com.model.FlowersForOrdering;
import com.repository.FlowerRepository;

public class FlowerAmountManagement {

    FlowerRepository flowerRepository = new FlowerRepository();

    FlowersForOrdering flowersForOrdering;

    FlowerAmountManagement(FlowersForOrdering flowersForOrdering) {
        this.flowersForOrdering = flowersForOrdering;
    }

    public void reduceFlowerAmount() {
        Flower flowerToReduceAmount = flowerRepository.findById(flowersForOrdering.getFlower().getId());
        flowerToReduceAmount.setAmount(flowerToReduceAmount.getAmount() - flowersForOrdering.getQuantity());
        flowerRepository.createAndUpdate(flowerToReduceAmount);
    }

    public void restoreFlowerAmount(Integer orderedAmount) {
        Flower flowerToRestoreAmount = flowerRepository.findById(flowersForOrdering.getFlower().getId());
        flowerToRestoreAmount.setAmount(flowerToRestoreAmount.getAmount() + flowersForOrdering.getQuantity());
        flowerRepository.createAndUpdate(flowerToRestoreAmount);
    }
}
