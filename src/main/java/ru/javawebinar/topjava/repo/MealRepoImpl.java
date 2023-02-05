package ru.javawebinar.topjava.repo;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MealRepoImpl implements MealRepo {

    AtomicLong id = new AtomicLong(8);
    Map<Long, Meal> meals;

    {
        meals = new ConcurrentHashMap<>();
        meals.put(1L, new Meal(1L, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.put(2L, new Meal(2L, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.put(3L, new Meal(3L, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.put(4L, new Meal(4L, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.put(5L, new Meal(5L, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.put(6L, new Meal(6L, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.put(7L, new Meal(7L, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public Meal get(long id) {
        return meals.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public void update(Meal meal) {
        if (meal.getId() == 0) {
            meal.setId(id.getAndIncrement());
        }
        meals.put(meal.getId(), meal);
    }

    @Override
    public void delete(long id) {
        meals.remove(id);
    }
}