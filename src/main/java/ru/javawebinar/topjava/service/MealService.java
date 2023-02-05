package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealService {

    Meal get(long id);

    List<MealTo> getAll();

    void update(Meal meal);

    void delete(long id);
}
