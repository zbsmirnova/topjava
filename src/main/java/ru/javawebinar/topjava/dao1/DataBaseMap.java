package ru.javawebinar.topjava.dao1;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DataBaseMap implements MealDao {

    private static Map<Integer, Meal> dbMap = new ConcurrentHashMap<>();

    private static AtomicInteger id = new AtomicInteger(0);

    public DataBaseMap(){
        init();
    }

    public Meal add(Meal meal){
        int mealId = id.incrementAndGet();
        meal.setId(mealId);
        return dbMap.put(mealId, meal);
    }

    public void deleteById (int id){
        dbMap.remove(id);
    }

    public Meal update(int id, Meal newMeal){
        return dbMap.put(id, newMeal);
    }

    public Meal getById(int id){
        return dbMap.get(id);
    }

    public List<Meal> getAll(){
        return new ArrayList<>(dbMap.values());
    }

    private void init(){

                add(new Meal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500));
                add(new Meal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000));
                add(new Meal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500));
                add(new Meal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000));
                add(new Meal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500));
                add(new Meal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510));

    }
}
