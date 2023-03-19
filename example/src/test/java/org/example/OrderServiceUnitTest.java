package org.example;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.businessLogic.IMailService;
import org.example.businessLogic.dto.Message;
import org.example.businessLogic.OrderService;
import org.example.businessLogic.WarehouseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

// System Under Test -> OrderService (SUT)
// Collaborators ->  WarehouseService and MailService
@ExtendWith(MockitoExtension.class)
public class OrderServiceUnitTest {
    private static String TALISKER = "Talisker";

    @Test
    public void testFillingRemovesInventoryIfInStock() {
        //setup - data
        OrderService orderService = new OrderService(TALISKER, 50);
        WarehouseService warehouseServiceMock = mock(WarehouseService.class);

        //setup - expectations
        when(warehouseServiceMock.hasInventory(TALISKER, 50)).thenReturn(true);
        doNothing().when(warehouseServiceMock).remove(anyString(), anyInt());

        //exercise
        orderService.fill(warehouseServiceMock);

        //verify
        assertTrue(orderService.isFilled());

        //teardown
    }

    @Test
    public void testFillingDoesNotRemoveIfNotEnoughInStock() {
        //setup - data
        OrderService orderService = new OrderService(TALISKER, 51);
        WarehouseService warehouseService = mock(WarehouseService.class);
        IMailService mailer = mock(IMailService.class);
        orderService.setMailer(mailer);

        //setup - expectations
        when(warehouseService.hasInventory(anyString(), anyInt())).thenReturn(false);

        //exercise
        orderService.fill(warehouseService);

        //verify
        assertFalse(orderService.isFilled());

        //teardown
    }

    @Test
    public void testOrderSendsMailIfUnfilled() {
        //setup - data
        OrderService orderService = new OrderService(TALISKER, 51);
        WarehouseService warehouseService = mock(WarehouseService.class);
        IMailService mailer = mock(IMailService.class);
        orderService.setMailer(mailer);

        //setup - expectations
        when(warehouseService.hasInventory(anyString(), anyInt())).thenReturn(false);

        //exercise
        orderService.fill(warehouseService);


        //verify
        verify(mailer, times(1)).send(any(Message.class));

        //teardown
    }
}