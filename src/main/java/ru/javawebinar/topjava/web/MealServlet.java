package ru.javawebinar.topjava.web;


import ru.javawebinar.topjava.dao1.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.dao1.DataBaseMap;
import ru.javawebinar.topjava.util.MealsUtil;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private final static String INSERT_OR_EDIT = "/meal.jsp";
    private final static String LIST_MEALS = "/meals.jsp";
    private MealDao mealDao;
    private static final org.slf4j.Logger log = getLogger(UserServlet.class);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public MealServlet() throws ServletException{
        init();
        mealDao = new DataBaseMap();}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action")==null?"toMeals":request.getParameter("action");
        String forward = "";

        request.setAttribute("localDateTimeFormat", formatter);

        if(!action.equalsIgnoreCase("delete")){
        switch (action) {
            case ("toMeals"):
                log.debug("redirect to meals");
                List<MealWithExceed> mealWithExceedList = MealsUtil.getFilteredWithExceeded(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
                request.setAttribute("mealList", mealWithExceedList);
                forward = LIST_MEALS;
                break;

            case ("edit"):
                forward = INSERT_OR_EDIT;
                int mealId = Integer.parseInt(request.getParameter("id"));
                log.debug("action = edit, meal.id = " + mealId);
                Meal meal = mealDao.getById(mealId);
                request.setAttribute("meal", meal);
                break;
            case ("listMeal"):
                log.debug("action = listMeal, redirect to meals.jsp");
                forward = LIST_MEALS;
                request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
                break;
            case ("insert"):
                log.debug("action = insert, redirect to meal.jsp");
                forward = INSERT_OR_EDIT;
                break;
        }
        request.getRequestDispatcher(forward).forward(request, response);

        }
        else{
            int mealId = Integer.parseInt(request.getParameter("id"));
            log.debug("action = delete, meal.id = " + mealId);
            mealDao.deleteById(mealId);
            request.getSession().setAttribute("mealList", MealsUtil.getFilteredWithExceeded(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
            response.sendRedirect("meals");
        }


    }




    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String dateStr = request.getParameter("date");
        LocalDateTime dateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime.of(2018, Month.MAY, 30,10,33);
        Meal meal = new Meal(dateTime, request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
        String id = request.getParameter("id");
        if(id == null || id.isEmpty())
        {
            mealDao.add(meal);
            log.debug("adding : " + meal);
        }
        else
        {
            int mealId = Integer.parseInt(id);
            meal.setId(mealId);
            mealDao.update(mealId, meal);
            log.debug("updating :" + meal);
        }

        request.setAttribute("localDateTimeFormat", formatter);
        request.setAttribute("mealList", MealsUtil.getFilteredWithExceeded(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
        request.getRequestDispatcher(LIST_MEALS).forward(request, response);

    }
}

