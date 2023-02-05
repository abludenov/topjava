package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repo.MealRepoInMemory;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.util.DateTimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String SAVE_OR_UPDATE = "/meal.jsp";
    private static final String LIST = "/meals.jsp";
    private final MealService service;

    public MealServlet() {
        super();
        service = new MealServiceImpl(new MealRepoInMemory());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String forward = "";
        String action = request.getParameter("action");
        if (action == null) {
            forward = LIST;
            request.setAttribute("meals", service.getAll());
            log.debug("Show all meals from action: {}", action);
        } else if (action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            service.delete(id);
            log.debug("Meal with id: {}, was deleted", id);
            forward = LIST;
            request.setAttribute("meals", service.getAll());
        } else if (action.equalsIgnoreCase("save")) {
            forward = SAVE_OR_UPDATE;
            log.debug("Redirect to: {}", forward);
        } else if (action.equalsIgnoreCase("edit")) {
            forward = SAVE_OR_UPDATE;
            log.debug("Redirect to: {}", forward);
            int id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("meal", service.get(id));
            log.debug("Meal with id: {}, was get", id);
        } else {
            forward = LIST;
            request.setAttribute("meals", service.getAll());
            log.debug("Show all meals");
        }

        request.getRequestDispatcher(forward).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LocalDateTime localDateTime = DateTimeUtil.dateTimeParser(request.getParameter("datetime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        String stringId = request.getParameter("id");
        int id = (stringId.isEmpty()) ? 0 : Integer.parseInt(stringId);

        Meal meal = new Meal(id, localDateTime, description, calories);
        service.save(meal);
        log.debug("Meal save/update");

        request.setAttribute("meals", service.getAll());
        request.getRequestDispatcher(LIST).forward(request, response);
    }
}