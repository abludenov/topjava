package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealService {

    Meal get(Integer id);

    List<MealTo> getAll();

    void save(Meal meal);

    void delete(Integer id);
}
