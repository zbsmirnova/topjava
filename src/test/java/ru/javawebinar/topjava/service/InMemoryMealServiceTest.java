package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring-test.xml",
        "classpath:spring/spring-db.xml"
})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@RunWith(SpringRunner.class)
public class InMemoryMealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(ADMIN_MEAL1_ID, ADMIN_ID);
        assertMatch(ADMIN_MEAL_1, meal);
    }
    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1, 1);
    }

    @Test
    public void delete() {
        service.delete(MEAL1_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4,  MEAL3, MEAL2);}

    @Test(expected = NotFoundException.class)
    public void deleteAnothersMeal() throws Exception {
        service.delete(MEAL1_ID, ADMIN_ID);
    }
    @Test
    public void getBetweenDates() {
    }

    @Test
    public void getBetweenDateTimes() {
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(USER_ID);
        assertMatch(meals, MEAL6, MEAL5, MEAL4,  MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void getAllNotFound() {
        List<Meal> meals = service.getAll(1);
        assertThat(meals.isEmpty());
    }

    @Test
    public void update() {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("updated");
        updated.setCalories(333);
        updated.setDateTime(LocalDateTime.now());
        assertMatch(service.get(MEAL1_ID,USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateAnothersMeal() {
        Meal updated = new Meal(MEAL1);
        updated.setId(ADMIN_MEAL1_ID);
        updated.setDescription("updated");
        updated.setCalories(333);
        updated.setDateTime(LocalDateTime.now());
        assertMatch(service.get(ADMIN_MEAL1_ID,USER_ID), updated);
    }

    @Test
    public void create() throws Exception{
        Meal newMeal = new Meal(LocalDateTime.of(2016, Month.MAY, 30, 10, 0), "Завтрак", 555);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(USER_ID), newMeal, MEAL6, MEAL5, MEAL4,  MEAL3, MEAL2, MEAL1);
    }

}