package cs.sbs.web.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class DataStore {

	private static final List<MenuItem> MENU = new ArrayList<>();
	private static final List<Order> ORDERS = new ArrayList<>();
	private static int nextOrderId = 1000;

	static {
		MENU.add(new MenuItem("Fried Rice", 8));
		MENU.add(new MenuItem("Fried Noodles", 9));
		MENU.add(new MenuItem("Burger", 10));
	}

	private DataStore() {
	}

	public static List<MenuItem> getAllMenuItems() {
		return Collections.unmodifiableList(MENU);
	}

	public static List<MenuItem> searchMenuByName(String keyword) {
		if (keyword == null) {
			return getAllMenuItems();
		}

		String needle = keyword.trim().toLowerCase();
		if (needle.isEmpty()) {
			return getAllMenuItems();
		}

		List<MenuItem> result = new ArrayList<>();
		for (MenuItem item : MENU) {
			if (item.getName().toLowerCase().contains(needle)) {
				result.add(item);
			}
		}
		return result;
	}

	public static synchronized Order createOrder(String customer, String food, int quantity) {
		Order order = new Order(nextOrderId++, customer, food, quantity);
		ORDERS.add(order);
		return order;
	}

	public static synchronized Order findOrderById(int id) {
		for (Order order : ORDERS) {
			if (order.getId() == id) {
				return order;
			}
		}
		return null;
	}
}
