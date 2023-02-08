package ru.javawebinar.topjava.repo;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepo implements MealRepo {

    private AtomicInteger id = new AtomicInteger(1);
    private Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    {
        Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        ).forEach(this::saveOrUpdate);
    }

    @Override
    public Meal get(int id) {
        return meals.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public Meal saveOrUpdate(Meal meal) {
        Meal oldMeal = get(meal.getId());
        if (oldMeal == null) {
            int newId = id.getAndIncrement();
            meal.setId(newId);
            return meals.put(newId, meal);
        } else {
            return meals.replace(meal.getId(), meal);
        }
    }

    @Override
    public void delete(int id) {
        meals.remove(id);
    }

    protected int searchKey(int id) {
        return meals.keySet().stream().findFirst().orElse(-1);
    }
}