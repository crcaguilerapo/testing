package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.example.businessLogic.stub.MailServiceStub;
import org.example.businessLogic.OrderService;
import org.example.businessLogic.WarehouseService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

// System Under Test -> OrderService (SUT)
// Collaborators ->  WarehouseService and MailService
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderServiceIntegrationTest {
    private String TALISKER = "Talisker";
    private String HIGHLAND_PARK = "Highland Park";
    private WarehouseService warehouseService = new WarehouseService();

    @BeforeAll
    public void setUp() {
        warehouseService.add(TALISKER, 50);
        warehouseService.add(HIGHLAND_PARK, 25);
    }

    @Test
    public void testOrderIsFilledIfEnoughInWarehouse() {
        //setup - data
        OrderService orderService = new OrderService(TALISKER, 50);

        //exercise
        orderService.fill(warehouseService);

        //verify
        assertTrue(orderService.isFilled());
        assertEquals(0, warehouseService.getInventory(TALISKER));

        //teardown
    }

    @Test
    public void testOrderDoesNotRemoveIfNotEnough() {
        //setup - data
        OrderService orderService = new OrderService(TALISKER, 51);
        MailServiceStub mailer = new MailServiceStub();
        orderService.setMailer(mailer);

        //exercise
        orderService.fill(warehouseService);

        //verify
        assertFalse(orderService.isFilled());
        assertEquals(50, warehouseService.getInventory(TALISKER));
        assertEquals(1, mailer.numberSent());
        //teardown
    }
}