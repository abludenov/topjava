package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repo.MealRepo;
import ru.javawebinar.topjava.repo.MealRepoInMemory;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.List;

public class MealServiceImpl implements MealService{

    private final MealRepo mealDao;

    public MealServiceImpl(MealRepo mealDao) {
        super();
        this.mealDao = new MealRepoInMemory();
    }

    @Override
    public Meal get(Integer id) {
        return mealDao.get(id);
    }

    @Override
    public List<MealTo> getAll() {
        return MealsUtil.filteredByStreams(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY);
    }

    @Override
    public void save(Meal meal) {
        mealDao.save(meal);
    }

    @Override
    public void delete(Integer id) {
        mealDao.delete(id);
    }
}
