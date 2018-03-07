package ru.javawebinar.topjava.dao1;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    Meal add(Meal meal);
    void deleteById(int id);
    Meal update(int id, Meal newMeal);
    List<Meal> getAll();
    Meal getById(int mealId);
}
