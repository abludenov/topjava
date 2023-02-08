package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repo.InMemoryMealRepo;
import ru.javawebinar.topjava.repo.MealRepo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String SAVE_OR_UPDATE = "/meal.jsp";
    private static final String LIST = "/meals.jsp";
    private MealRepo repo;

    @Override
    public void init() {
        repo = new InMemoryMealRepo();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward;
        String action = request.getParameter("action");
        switch (action == null ? "list" : action) {
            case "save":
                request.setAttribute("action", action);
                request.setAttribute("meal", new Meal(LocalDateTime.now(), "Введите еду", 0));
                forward = SAVE_OR_UPDATE;
                log.debug("Redirect to: {}", forward);
                break;
            case "edit":
                request.setAttribute("action", action);
                forward = SAVE_OR_UPDATE;
                log.debug("Redirect to: {}", forward);
                int id = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("meal", repo.get(id));
                log.debug("Meal with id: {}, was get", id);
                break;
            case "delete":
                request.getParameter("id");
                int idForDelete = Integer.parseInt(request.getParameter("id"));
                repo.delete(idForDelete);
                log.debug("Meal with id: {}, was deleted", idForDelete);
                response.sendRedirect("meals");
                return;
            case "list":
            default:
                forward = LIST;
                request.setAttribute("meals", getAllMealTo());
                log.debug("Show all meals");
        }

        request.getRequestDispatcher(forward).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        LocalDateTime localDateTime = DateTimeUtil.dateTimeParse(request.getParameter("datetime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        String stringId = request.getParameter("id");
        if (stringId.isEmpty()) {
            repo.saveOrUpdate(new Meal(localDateTime, description, calories));
        } else {
            repo.saveOrUpdate(new Meal(Integer.parseInt(stringId), localDateTime, description, calories));
        }
        log.debug("Meal save/update");

        request.setAttribute("meals", getAllMealTo());
        response.sendRedirect("meals");
    }

    private List<MealTo> getAllMealTo() {
        return MealsUtil.filteredByStreams(repo.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY);
    }
}