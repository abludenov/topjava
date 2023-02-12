package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        if(meal.getUserId() == userId) {
            return meal;
        } else {
            throw new NotFoundException("incorrect meal id for user id");
        }
    }

    public Meal save(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    public Meal update(Meal meal, int userId) {
        if(meal.getUserId() == userId) {
            return repository.save(meal, userId);
        } else {
            throw new NotFoundException("can't update this meal");
        }
    }

    public void delete(int id, int userId) {
        Meal meal = repository.get(id);
        if(meal.getUserId() == userId) {
            repository.delete(id);
        } else {
            throw new NotFoundException("can't delete this meal");
        }
    }

    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }
}