package ru.javawebinar.topjava.util;

import main.java.ru.javawebinar.topjava.model.UserMeal;
import main.java.ru.javawebinar.topjava.model.UserMealWithExceed;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

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

    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dateCaloriesMap = new HashMap<>();
        for (UserMeal meal : mealList) {
            LocalDate date = meal.getDateTime().toLocalDate();
            dateCaloriesMap.merge(date, meal.getCalories(), Integer::sum);
        }
        List<UserMealWithExceed> mealWithExceedList = new ArrayList<>();
        for (UserMeal meal : mealList) {
            LocalDate date = meal.getDateTime().toLocalDate();
            if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)){
                boolean exceed = dateCaloriesMap.get(date) > caloriesPerDay;
                mealWithExceedList.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceed));
        }}
        return mealWithExceedList;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMeal> userMealBetween;
        Map<LocalDate, Integer> dateCaloriesMap = new HashMap<>();
        userMealBetween = mealList.stream()
                .peek(meal -> dateCaloriesMap.merge(meal.getDate(), meal.getCalories(), (value, newValue) -> (value + newValue)))
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());

        return userMealBetween.stream()
                .map(meal -> {
                    boolean exceed = dateCaloriesMap.get(meal.getDate()) > caloriesPerDay;
                    return new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceed);})
                .collect(Collectors.toList());
    }
}
