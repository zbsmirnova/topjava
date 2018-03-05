package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.DataBaseMap;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MealDAOImpl implements MealDAO{

        private DataBaseMap dataBaseMap = DataBaseMap.getInstance();

        public void addMeal(Meal meal) {
               dataBaseMap.addMeal(meal);
        }

        public void deleteMealById(int id) {
               dataBaseMap.deleteMealbyID(id);
        }

        public void updateMeal(int id, Meal newMeal) {
                dataBaseMap.updateMeal(id, newMeal);
        }

        public List<Meal> getAllMeals() {
            return dataBaseMap.getAllMeals();
        }

        public Meal getMealById(int mealId) {
            return dataBaseMap.getMealById(mealId);
        }

}
