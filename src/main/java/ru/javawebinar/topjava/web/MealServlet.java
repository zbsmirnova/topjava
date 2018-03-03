package ru.javawebinar.topjava.web;


import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final org.slf4j.Logger log = getLogger(UserServlet.class);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        List<MealWithExceed> mealWithExceedList = MealsUtil.getMealWithExceedList(MealsUtil.initMealList(), 2000);

        System.out.println(mealWithExceedList.get(0).getDateString());
        request.setAttribute("mealList", mealWithExceedList);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);

    }
}

