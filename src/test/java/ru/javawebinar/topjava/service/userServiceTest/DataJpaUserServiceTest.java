package ru.javawebinar.topjava.service.userServiceTest;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;


import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Test
    public void getUserWithMeals(){
        User user = service.getUserWithMeals(USER_ID);
        MealTestData.assertMatch(user.getMeals(), MealTestData.MEALS);
    }

}
