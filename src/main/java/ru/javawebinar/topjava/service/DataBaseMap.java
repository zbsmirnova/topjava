package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DataBaseMap {
    private static DataBaseMap dataBaseMap;

    private static Map<Integer, Meal> dbMap = new ConcurrentHashMap<>();

    private static AtomicInteger id = new AtomicInteger(0);;

    private DataBaseMap() { }

    public static DataBaseMap getInstance(){
        if(dataBaseMap == null){
            DataBaseMap dataBaseMap = new DataBaseMap();
            dataBaseMap.init();
            return dataBaseMap ; //maybe add Double Checked Locking & volatile
        }
        else return dataBaseMap;
    }

    public Map getDbMap() {
        return dbMap;
    }

    public void addMeal(Meal meal){
        int mealId = id.incrementAndGet();
        meal.setMealId(mealId);
        dbMap.put(mealId, meal);

    }

    public void deleteMealbyID(int id){
        dbMap.remove(id);
    }

    public void updateMeal(int id, Meal newMeal){
        dbMap.put(id, newMeal);
    }

    public int getId(Meal meal){
        int id = -1;
        for(Map.Entry entry : dbMap.entrySet()){
            if(entry.getValue().equals(meal)) id = (int) entry.getKey();
        }
        return id;
    }
    public Meal getMealById(int id){
        return dbMap.get(id);
    }

    public List<Meal> getAllMeals(){
        return new ArrayList<>(dbMap.values());
    }

    public void init(){

                addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500));
                addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000));
                addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500));
                addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000));
                addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500));
                addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510));

    }
}
