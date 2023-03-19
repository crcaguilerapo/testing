package org.example.businessLogic;

import java.util.HashMap;

public class WarehouseService {

    private HashMap<String, Integer> inventory = new HashMap<String, Integer>();

    public void add(String product, Integer stock) {
        inventory.put(product, stock);
    }

    public int getInventory(String product) {
        return inventory.get(product).intValue();
    }

    public boolean hasInventory(String product, Integer quantity) {
        return inventory.get(product) - quantity >= 0;
    }

    public void remove(String product, Integer quantity) {
        inventory.put(product, inventory.get(product) - quantity);
    }
}
