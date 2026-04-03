package cs.sbs.web.servlet;

import cs.sbs.web.model.DataStore;
import cs.sbs.web.model.MenuItem;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class MenuListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/plain; charset=UTF-8");

        String name = req.getParameter("name");
        List<MenuItem> items = DataStore.searchMenuByName(name);

        StringBuilder out = new StringBuilder();
        out.append("Menu List:\n\n");

        if (items.isEmpty()) {
            out.append("No menu found for keyword: ").append(name == null ? "" : name);
            resp.getWriter().println(out);
            return;
        }

        int index = 1;
        for (MenuItem item : items) {
            out.append(index++)
                    .append(". ")
                    .append(item.getName())
                    .append(" - $")
                    .append(item.getPrice())
                    .append("\n");
        }

        resp.getWriter().println(out);
    }
}
