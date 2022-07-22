package dto;

import java.util.List;

public class OrderList {
    public OrderList(List<Orders> orders) {
        this.orders = orders;
    }

    public static List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    private static List<Orders> orders;
}
