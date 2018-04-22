package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealTestData.MEAL2;
import static ru.javawebinar.topjava.MealTestData.MEAL3;
import static ru.javawebinar.topjava.UserTestData.*;

public class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.MEALS_URL + '/';
    @Autowired
    MealService mealService;

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andExpect(status().isNoContent());
        assertMatch(mealService.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test
    public void testCreateMeal() throws Exception {
        Meal toCreate = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), "", 1000);

        ResultActions action = mockMvc.perform(post(REST_URL + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(toCreate))) //????
                .andExpect(status().isCreated())
                .andDo(print());


        Meal returned = TestUtil.readFromJson(action, Meal.class);

        assertMatch(returned, toCreate);
    }

    @Test
    public void testUpdate()throws Exception {
        ResultActions action = mockMvc.perform(post(REST_URL + "update/" + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(MEAL1)))//???????
                .andExpect(status().isCreated())
                .andDo(print());

        Meal returned = TestUtil.readFromJson(action, Meal.class);

        assertMatch(returned, MEAL1);
    }

    @Test
    public void testUpdateOrCreate() throws Exception {
        Meal updated = getUpdated();
        mockMvc.perform(put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isMovedPermanently())
                .andDo(print());
        assertMatch(mealService.get(MEAL1_ID, USER_ID), updated);

    }
    @Test
    public void testGetBetween() throws Exception {
        TestUtil.print(mockMvc.perform(post(REST_URL + "filter/2015-05-30T00:00:00/2015-05-30T00:00:00" ))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)));

        assertMatch(mealService.getBetweenDates(
                LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), USER_ID), MEAL3, MEAL2, MEAL1);

    }



}
