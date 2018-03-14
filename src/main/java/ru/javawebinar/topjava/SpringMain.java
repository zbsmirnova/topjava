package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

public class SpringMain {

    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            try{
            Meal meal = new Meal(5, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "UpdateSomebodyElseMeal", 500, null);
            mealRestController.update(meal, 5);}
            catch (NotFoundException e){
                System.out.println(e.getMessage());}
                try{
            Meal meal1 = new Meal(7, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "UpdateSomebodyElseMeal", 500, 5);
            mealRestController.update(meal1, 7);}
            catch (NotFoundException e){
                    System.out.println(e.getMessage());}
            try{
                Meal meal3 = new Meal(4, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "UpdateSomebodyElseMeal", 500, 2);
                mealRestController.update(meal3, 4);}
            catch (NotFoundException e){
            System.out.println(e.getMessage());}
        }

    }
}
