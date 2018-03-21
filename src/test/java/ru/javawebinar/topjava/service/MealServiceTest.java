package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@RunWith(SpringRunner.class)
public class MealServiceTest {
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
    public void getAnothersMeal() throws Exception {
        service.get(ADMIN_MEAL1_ID, USER_ID);
    }

    @Test
    public void delete() {
        service.delete(MEAL1_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }


    @Test(expected = NotFoundException.class)
    public void deleteAnothersMeal() throws Exception {
        service.delete(MEAL2_ID, ADMIN_ID);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> mealsBetween = service.getBetweenDateTimes(LocalDateTime.of(
                2015, Month.MAY, 30, 10, 0),
                LocalDateTime.of(2015, Month.MAY, 30, 20, 0), USER_ID );
        assertMatch(mealsBetween, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void getBetweenDateTimesNotFound() {
        List<Meal> mealsBetween = service.getBetweenDateTimes(LocalDateTime.of(
                2018, Month.MAY, 30, 10, 0),
                LocalDateTime.of(2018, Month.MAY, 30, 20, 0), USER_ID );
        assertThat(mealsBetween.isEmpty());
    }

    @Test
    public void getBetweenDates() {
        List<Meal> mealsBetween = service.getBetweenDates(LocalDate.of(
                2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), USER_ID );
        assertMatch(mealsBetween, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void getBetweenDatesNotFound() {
        List<Meal> mealsBetween = service.getBetweenDates(LocalDate.of(
                2015, Month.MAY, 05),
                LocalDate.of(2015, Month.MAY, 06), USER_ID );
        assertThat(mealsBetween.isEmpty());
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
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
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL1_ID, USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateAnothersMeal() {
        Meal updated = new Meal(MEAL1);
        updated.setId(ADMIN_MEAL1_ID);
        updated.setDescription("updated");
        updated.setCalories(333);
        updated.setDateTime(LocalDateTime.now());
        assertMatch(service.get(ADMIN_MEAL1_ID, USER_ID), updated);
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.of(2016, Month.MAY, 30, 10, 0), "Завтрак", 555);
        service.create(newMeal, USER_ID);
        assertMatch(service.getAll(USER_ID), newMeal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test(expected = DuplicateKeyException.class)
    public void createDuplicateDateTime(){
        Meal newMeal = new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Duplicate", 111);
        service.create(newMeal, USER_ID);
    }


}
