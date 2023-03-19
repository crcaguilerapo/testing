package org.example.businessLogic;

import org.example.businessLogic.dto.Message;

public class OrderService {

    private String product;
    private int stock;
    private boolean filled;

    private IMailService mailService;

    public OrderService(String product, Integer stock) {
        this.product = product;
        this.stock = stock;
    }

    public void fill(WarehouseService warehouseService) {
        if (warehouseService.hasInventory(product, stock)) {
            warehouseService.remove(product, stock);
            filled = true;
        } else {
            filled = false;
            mailService.send(new Message("test@test.com", "test1@test1.com", "Error"));
        }
    }

    public boolean isFilled() {
        return filled;
    }

    public void setMailer(IMailService mailService) {
        this.mailService = mailService;
    }
}
