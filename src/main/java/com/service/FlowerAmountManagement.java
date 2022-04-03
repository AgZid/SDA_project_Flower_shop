package com.service;

import com.model.Flower;
import com.model.FlowersForOrdering;
import com.repository.Repository;

public class FlowerAmountManagement {

    Repository repository = new Repository();

    FlowersForOrdering flowersForOrdering;

    FlowerAmountManagement(FlowersForOrdering flowersForOrdering) {
        this.flowersForOrdering = flowersForOrdering;
    }

    public void reduceFlowerAmount() {
        Flower flowerToReduceAmount = repository
                .findById(Flower.class, flowersForOrdering.getFlower().getId(), "Flower");
        flowerToReduceAmount.setAmount(flowerToReduceAmount.getAmount() - flowersForOrdering.getQuantity());
        repository.createOrUpdateRecord(flowerToReduceAmount);
    }

    public void restoreFlowerAmount(Integer orderedAmount) {
        Flower flowerToRestoreAmount = repository
                .findById(Flower.class, flowersForOrdering.getFlower().getId(), "Flower");
        flowerToRestoreAmount.setAmount(flowerToRestoreAmount.getAmount() + flowersForOrdering.getQuantity());
        repository.createOrUpdateRecord(flowerToRestoreAmount);
    }
}
