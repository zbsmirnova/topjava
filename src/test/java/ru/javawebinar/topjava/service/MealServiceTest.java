package ru.javawebinar.topjava.service;

import org.junit.AssumptionViolatedException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.rules.Timeout;
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
import ru.javawebinar.topjava.web.meal.MealRestController;

import java.beans.Statement;
import java.rmi.UnexpectedException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    private long startTime;
    private long endTime;
    private boolean passed;

    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    static {
        SLF4JBridgeHandler.install();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TestRule testWatcher = new TestWatcher() {
        @Override
        protected void succeeded(Description description) {
            passed = true;
        }
        @Override
        protected void failed(Throwable e, Description description) {
            log.info("Test " + description.getClassName() + " failed on " + e.getStackTrace()[0]);
            //logs as failed tests with expected exception, which are passed. Debugger marks this tests as passed
        }
        @Override
        protected void starting(Description description){
            startTime = System.currentTimeMillis();
            super.starting(description);
        }

        @Override
        protected void finished(Description description) {
            endTime = System.currentTimeMillis();
            super.finished(description);
            if(passed) log.info("Test " + description.getClassName() + " passed in "  + (endTime - startTime) + " ms");
        }
    };

        @Autowired
        private MealService service;

        @Test
        public void delete() throws Exception {
            service.delete(MEAL1_ID, USER_ID);
            assertMatch(service.getAll(USER_ID), MEAL2, MEAL3, MEAL4, MEAL5, MEAL6);

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
            assertMatch(service.getAll(USER_ID), MEAL1, MEAL2, MEAL3, MEAL4, MEAL5, MEAL6, created);
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
            assertMatch(service.getAll(USER_ID), MEAL1, MEAL2, MEAL3, MEAL4, MEAL5, MEAL6);
        }

        @Test
        public void getBetween() throws Exception {
            assertMatch(service.getBetweenDates(
                    LocalDate.of(2015, Month.MAY, 30),
                    LocalDate.of(2015, Month.MAY, 30), USER_ID), MEAL1, MEAL2, MEAL3);
        }

    }