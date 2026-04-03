package cs.sbs.web.servlet;

import cs.sbs.web.model.DataStore;
import cs.sbs.web.model.Order;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class OrderCreateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/plain; charset=UTF-8");

        String customer = req.getParameter("customer");
        String food = req.getParameter("food");
        String quantityParam = req.getParameter("quantity");

        if (isBlank(customer) || isBlank(food) || isBlank(quantityParam)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Error: missing required parameter(s)");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityParam.trim());
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Error: quantity must be a valid number");
            return;
        }

        if (quantity <= 0) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Error: quantity must be greater than 0");
            return;
        }

        Order order = DataStore.createOrder(customer.trim(), food.trim(), quantity);
        resp.getWriter().println("Order Created: " + order.getId());
        resp.getWriter().println("Order #" + order.getId() + " (Click to view details) /order/" + order.getId());
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
