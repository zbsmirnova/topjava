package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDAO {

    public void addMeal(Meal meal);
    public void deleteMealById(int id);
    public void updateMeal(int id, Meal newMeal);
    public List<Meal> getAllMeals();
    public Meal getMealById(int mealId);
}
