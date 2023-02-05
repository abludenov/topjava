package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repo.MealRepo;
import ru.javawebinar.topjava.repo.MealRepoImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.List;

public class MealServiceImpl implements MealService{

    private final MealRepo mealDao;

    public MealServiceImpl(MealRepo mealDao) {
        super();
        this.mealDao = new MealRepoImpl();
    }

    @Override
    public Meal get(long id) {
        return mealDao.get(id);
    }

    @Override
    public List<MealTo> getAll() {
        return MealsUtil.filteredByStreams(mealDao.getAll(), LocalTime.of(0, 0), LocalTime.of(23,59, 59), MealsUtil.caloriesPerDay);
    }

    @Override
    public void update(Meal meal) {
        mealDao.update(meal);
    }

    @Override
    public void delete(long id) {
        mealDao.delete(id);
    }
}
