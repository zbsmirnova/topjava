package ru.javawebinar.topjava.repository.mock.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;


import java.time.temporal.Temporal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer,Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);


    {
        MealsUtil.MEALS.forEach(this::init);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if( meal.getUserId() != userId) return null;
        log.info("save meal " + meal);
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
        log.info("delete meal id = " + id);
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        if(repository.get(id).getUserId() != userId) return null;
        log.info("get meal id = " + id);
        return repository.get(id);
    }

    @Override
    public List<Meal> getAll(int userId, Temporal startTime, Temporal endTime, Class <? extends Comparable> type) {
        log.info("getAll meals, userId = " + userId);
        return repository.values().stream()
                .filter(meal -> (meal.getUserId() == userId))
                .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime(), startTime, endTime, type))
                .sorted(Comparator.comparing(Meal::getDate).thenComparing(Meal::getTime)).collect(Collectors.toList());
    }

    private void init(Meal meal){
        meal.setId(counter.incrementAndGet());
        repository.put(meal.getId(), meal);
    }
}

