package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repo.MealRepo;
import ru.javawebinar.topjava.repo.MealRepoInMemory;
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
    public void init() throws ServletException {
        super.init();
        repo = new MealRepoInMemory();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String forward = "";
        String action = request.getParameter("action");
        switch (action == null ? "list" : action) {
            case "save":
                request.setAttribute("action", action);
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
                int idDel = Integer.parseInt(request.getParameter("id"));
                repo.delete(idDel);
                log.debug("Meal with id: {}, was deleted", idDel);
                forward = LIST;
                request.setAttribute("meals", getAllMealTo());
                break;
            case "list":
            default:
                forward = LIST;
                request.setAttribute("meals", getAllMealTo());
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
        repo.save(meal);
        log.debug("Meal save/update");

        request.setAttribute("meals", getAllMealTo());
        request.getRequestDispatcher(LIST).forward(request, response);
    }

    private List<MealTo> getAllMealTo() {
        return MealsUtil.filteredByStreams(repo.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY);
    }
}