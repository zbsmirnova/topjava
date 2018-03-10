package ru.javawebinar.topjava.repository.mock.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer,Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);


    {
        MealsUtil.MEALS.forEach(this::init);
    }

    @Override
    public Meal save(Meal meal, int userId) {

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(userId, (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        if( repository.get(id).getUserId() != userId) return false;
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        if(repository.get(id).getUserId() != userId) return null;
        return repository.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {

        return repository.values().stream().filter(meal -> (meal.getUserId() == userId)).sorted(Comparator.comparing(Meal::getDate).thenComparing(Meal::getTime)).collect(Collectors.toList());
    }

    private void init(Meal meal){
        meal.setId(counter.incrementAndGet());
        repository.put(meal.getId(), meal);
    }
}

