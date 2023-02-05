package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repo.MealRepoImpl;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
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
        service = new MealServiceImpl(new MealRepoImpl());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");
        if (action == null) {
            forward = LIST;
            request.setAttribute("meals", service.getAll());
        } else if (action.equalsIgnoreCase("delete")) {
            long id = Long.parseLong(request.getParameter("id"));
            service.delete(id);
            log.debug("Meal with id: {}, was deleted", id);
            forward = LIST;
            request.setAttribute("meals", service.getAll());
        } else if (action.equalsIgnoreCase("save")) {
            forward = SAVE_OR_UPDATE;
        } else if (action.equalsIgnoreCase("edit")) {
            forward = SAVE_OR_UPDATE;
            long id = Long.parseLong(request.getParameter("id"));
            request.setAttribute("meal", service.get(id));
            log.debug("Meal with id: {}, was get", id);
        } else if (action.equalsIgnoreCase("list")) {
            forward = LIST;
            request.setAttribute("meals", service.getAll());
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LocalDateTime localDateTime = MealsUtil.format(request.getParameter("datetime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        String stringId = request.getParameter("id");
        long id = (stringId.isEmpty()) ? 0 : Long.parseLong(stringId);

        Meal meal = new Meal(id, localDateTime, description, calories);
        service.update(meal);
        if(id == 0) {
            log.debug("Meal was created");
        } else {
            log.debug("Meal with id: {}, was updated", meal.getId());
        }

        request.setAttribute("meals", service.getAll());

        RequestDispatcher view = request.getRequestDispatcher(LIST);
        view.forward(request, response);
    }
}