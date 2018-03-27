package ru.javawebinar.topjava.service;

import org.hsqldb.lib.StopWatch;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.LocalDateTime.of;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    private StopWatch stopWatch = new StopWatch();
    private static Map<String, Long> test_time_map = new HashMap<>();

    private static final Logger log = LoggerFactory.getLogger(MealServiceTest.class);

    static {
        SLF4JBridgeHandler.install();
    }

    @AfterClass
    public static void afterClass(){
        for(Map.Entry<String, Long> entry : test_time_map.entrySet()){
            System.out.println("Test " + entry.getKey() +" worked " + entry.getValue() + " ms.");
        }
    }


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TestRule testWatcher = new TestWatcher() {
        @Override
        protected void succeeded(Description description) {
        }
        @Override
        protected void failed(Throwable e, Description description) {
            log.info("Test " + description.getMethodName() + " failed on " + e.getStackTrace()[0]);
            //logs as failed tests with expected exception, which are passed. Debugger marks this tests as passed
        }
        @Override
        protected void starting(Description description){
            stopWatch.start();
            super.starting(description);
        }

        @Override
        protected void finished(Description description) {
            super.finished(description);
            long elapsedTime = stopWatch.elapsedTime();
            test_time_map.put(description.getDisplayName(), elapsedTime);
            log.info("Test time " + elapsedTime + " ms");
            stopWatch.stop();
        }

    };

        @Autowired
        private MealService service;

        @Test
        public void delete() throws Exception {
            service.delete(MEAL1_ID, USER_ID);
            assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4,  MEAL3, MEAL2);

        }

        @Test
        public void deleteNotFound() throws Exception {
            thrown.expect(NotFoundException.class);
            service.delete(MEAL1_ID, ADMIN_ID);
        }

        @Test
        public void save() throws Exception {
            Meal created = getCreated();
            service.create(created, USER_ID);
            assertMatch(service.getAll(USER_ID), created, MEAL6, MEAL5, MEAL4,  MEAL3, MEAL2, MEAL1);
        }

        @Test
        public void get() throws Exception {
            Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
            assertMatch(actual, ADMIN_MEAL1);
        }

        @Test
        public void getNotFound() throws Exception {
            thrown.expect(NotFoundException.class);
            service.get(MEAL1_ID, ADMIN_ID);
        }

        @Test
        public void update() throws Exception {
            Meal updated = getUpdated();
            service.update(updated, USER_ID);
            assertMatch(service.get(MEAL1_ID, USER_ID), updated);
        }

        @Test
        public void updateNotFound() throws Exception {
            thrown.expect(NotFoundException.class);
            service.update(MEAL1, ADMIN_ID);
        }

        @Test
        public void getAll() throws Exception {
            assertMatch(service.getAll(USER_ID),  MEALS);
        }

        @Test
        public void getBetween() throws Exception {
            assertMatch(service.getBetweenDates(
                    LocalDate.of(2015, Month.MAY, 30),
                    LocalDate.of(2015, Month.MAY, 30), USER_ID), MEAL3, MEAL2, MEAL1);
        }

        @Test
        public void createSameDateOneUser(){
            thrown.expect(PersistenceException.class);
            Meal meal = new Meal(of(2015, Month.MAY, 30, 10, 0), "Дублированная дата завтрак", 1000);
            service.create(meal, USER.getId());
            List<Meal> meals = service.getAll(USER.getId());
        }

    @Test
    public void createSameDateDifferentUsers(){
        Meal meal = new Meal(of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
        service.create(meal, ADMIN.getId());
        List<Meal> meals = service.getAll(USER.getId());
    }

    }