package ru.javawebinar.topjava.repo;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepo {

    Meal get(long id);

    List<Meal> getAll();

    void update(Meal meal);

    void delete(long id);
}
