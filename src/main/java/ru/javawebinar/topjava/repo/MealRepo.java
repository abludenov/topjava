package ru.javawebinar.topjava.repo;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepo {

    Meal get(Integer id);

    List<Meal> getAll();

    void save(Meal meal);

    void delete(Integer id);
}
