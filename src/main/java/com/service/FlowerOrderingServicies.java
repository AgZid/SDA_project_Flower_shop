package com.service;

import com.model.Flower;
import com.model.OrderedEntry;
import com.repository.FlowerRepository;

public class FlowerOrderingServicies {

    FlowerRepository flowerRepository = new FlowerRepository();

    OrderedEntry orderedEntry;

    FlowerOrderingServicies(OrderedEntry orderedEntry) {
        this.orderedEntry = orderedEntry;
    }

    public void reduceFlowerAmount() {
        Flower flowerToReduceAmount = flowerRepository.findById(orderedEntry.getFlower().getId());
        flowerToReduceAmount.setAmount(flowerToReduceAmount.getAmount() - orderedEntry.getQuantity());
        flowerRepository.createAndUpdate(flowerToReduceAmount);
    }

    public void restoreFlowerAmount(Integer orderedAmount) {
        Flower flowerToRestoreAmount = flowerRepository.findById(orderedEntry.getFlower().getId());
        flowerToRestoreAmount.setAmount(flowerToRestoreAmount.getAmount() + orderedEntry.getQuantity());
        flowerRepository.createAndUpdate(flowerToRestoreAmount);
    }
}
