package com.service;

import com.model.Flower;
import com.model.OrderedEntry;
import com.repository.FlowerRepository;

public class FlowersServices {

    FlowerRepository flowerRepository = new FlowerRepository();

    public void showAllFlowers() {flowerRepository.findAll().forEach(System.out::println);
    }

    public boolean isValidFlowerId(Integer flowerId) {
        return flowerRepository.findById(flowerId) != null;
    }

    public boolean isFlowersQuantityAppropriate(Integer flowerId, Integer quantity) {
        Integer flowersAmountInStock = flowerRepository.findById(flowerId).getAmount();
        if (flowersAmountInStock >= quantity && quantity > 0) {
            return true;
        } else {
            System.out.println("Quantity must be greater then 0 and less then " + flowersAmountInStock);
            return false;
        }
    }

    public void addNewFlower(Flower newFlower) {
        Flower flowerInStock = findFlowerInStock(newFlower);
        if (flowerInStock != null) {
            System.out.println("Flower is already in stock");
        } else {
            flowerRepository.createOrUpdate(newFlower);
        }
    }

    public void removeFlower(Integer flowerId) {
        flowerRepository.deleteRecord(flowerRepository.findById(flowerId));
    }

    public void updateFlowerAmount(Integer flowerId, Integer newFlowerAmount) {
        Flower flowerToUpdate = flowerRepository.findById(flowerId);
        flowerToUpdate.setAmount(newFlowerAmount);
        flowerRepository.createOrUpdate(flowerToUpdate);
    }

    public void restoreFlowerAmount(OrderedEntry orderedEntry) {
        Flower flowerToRestoreAmount = flowerRepository.findById(orderedEntry.getFlower().getId());
        flowerToRestoreAmount.setAmount(flowerToRestoreAmount.getAmount() + orderedEntry.getQuantity());
        flowerRepository.createOrUpdate(flowerToRestoreAmount);
    }

    public Flower findFlowerInStock(Flower newFlower) {
        return flowerRepository.findAll().stream()
                .filter(flower -> flower.getName().equalsIgnoreCase(newFlower.getName())
                        && flower.getColor().equalsIgnoreCase(newFlower.getColor())
                        && flower.getPrice().equals(newFlower.getPrice())).findFirst().orElse(null);
    }

    public void reduceFlowerAmount(OrderedEntry orderedEntry) {
        Flower flowerToReduceAmount = flowerRepository.findById(orderedEntry.getFlower().getId());
        flowerToReduceAmount.setAmount(flowerToReduceAmount.getAmount() - orderedEntry.getQuantity());
        flowerRepository.createOrUpdate(flowerToReduceAmount);
    }

}
