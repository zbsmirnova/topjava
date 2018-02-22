package ru.javawebinar.topjava.util;

import main.java.ru.javawebinar.topjava.model.UserMeal;
import main.java.ru.javawebinar.topjava.model.UserMealWithExceed;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        List<UserMealWithExceed> userMealWithExceedList = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        for (UserMealWithExceed mealWithExceed : userMealWithExceedList) {
            System.out.println(mealWithExceed);
        }

        List<UserMealWithExceed> userMealWithExceedListStream = getFilteredWithExceededStream(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        for (UserMealWithExceed mealWithExceed : userMealWithExceedListStream) {
            System.out.println(mealWithExceed);
        }


//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMeal> userMealBetween = new ArrayList<>();
        Map<LocalDate, Integer> dateCaloriesMap = new HashMap<>();
        for (UserMeal meal : mealList) {
            LocalDate date = meal.getDateTime().toLocalDate();
            if (ru.javawebinar.topjava.util.TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealBetween.add(meal);
            }
            dateCaloriesMap.merge(date, meal.getCalories(), (value, newValue) -> (value + newValue));
        }
        List<UserMealWithExceed> mealWithExceedList = new ArrayList<>();
        for (UserMeal meal : userMealBetween) {
            LocalDate date = meal.getDateTime().toLocalDate();
            boolean exceed = false;
            if (dateCaloriesMap.get(date) > caloriesPerDay) exceed = true;
            mealWithExceedList.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceed));
        }
        return mealWithExceedList;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMeal> userMealBetween = new ArrayList<>();
        Map<LocalDate, Integer> dateCaloriesMap = new HashMap<>();
        userMealBetween = mealList.stream()
                .peek(meal -> dateCaloriesMap.merge(meal.getDate(), meal.getCalories(), (value, newValue) -> (value + newValue)))
                .filter(meal -> ru.javawebinar.topjava.util.TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());

        List<UserMealWithExceed> mealWithExceedList = new ArrayList<>();
        userMealBetween.stream()
                .forEach(meal -> {
                    boolean exceed = false;
                    if (dateCaloriesMap.get(meal.getDate()) > caloriesPerDay) exceed = true;
                    UserMealWithExceed mealWithExceed = new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceed);
                    mealWithExceedList.add(mealWithExceed);
                });
//                .map(mealWithExceedList.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), true));)
//                    boolean exceed = false;
//                    if(dateCaloriesMap.get(meal.getDate()) > caloriesPerDay) exceed = true;
//                    mealWithExceedList.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceed));
//                });
//
//
        return mealWithExceedList;
    }
}
