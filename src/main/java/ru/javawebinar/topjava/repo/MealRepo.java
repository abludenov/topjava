package ru.javawebinar.topjava.repo;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepo {

    Meal get(int id);

    List<Meal> getAll();

    Meal saveOrUpdate(Meal meal);

    void delete(int id);
}
