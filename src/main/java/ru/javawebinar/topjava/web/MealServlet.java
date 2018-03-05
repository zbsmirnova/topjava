package ru.javawebinar.topjava.web;


import ru.javawebinar.topjava.DAO.MealDAO;
import ru.javawebinar.topjava.DAO.MealDAOImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.DateFormatter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static String INSERT_OR_EDIT = "/meal.jsp";
    private static String LIST_MEALS = "/meals.jsp";
    private MealDAO mealDAO;
    private static final org.slf4j.Logger log = getLogger(UserServlet.class);

    public MealServlet(){
        super();
        mealDAO = new MealDAOImpl();}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        String forward = "";

        request.setAttribute("localDateTimeFormat", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        if(action == null) {
            log.debug("redirect to meals");
            List<MealWithExceed> mealWithExceedList = MealsUtil.getFilteredWithExceeded(mealDAO.getAllMeals(), LocalTime.MIN, LocalTime.MAX, 2000);
            request.setAttribute("mealList", mealWithExceedList);
            forward = "/meals.jsp";
        }

        else if (action.equalsIgnoreCase("delete")){
            int mealId = Integer.parseInt(request.getParameter("id"));
            mealDAO.deleteMealById(mealId);
            forward = "meals.jsp";
            request.setAttribute("mealList", MealsUtil.getFilteredWithExceeded(mealDAO.getAllMeals(), LocalTime.MIN, LocalTime.MAX, 2000));
        } else if (action.equalsIgnoreCase("edit")){
            forward = INSERT_OR_EDIT;
            int mealId = Integer.parseInt(request.getParameter("id"));
            Meal meal = mealDAO.getMealById(mealId);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("listMeal")){
            forward = LIST_MEALS;
            request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(mealDAO.getAllMeals(), LocalTime.MIN, LocalTime.MAX, 2000));
        } else if(action.equalsIgnoreCase("insert")){
            forward = INSERT_OR_EDIT;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }




    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dateStr = request.getParameter("date");
        LocalDateTime dateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime.of(2018, Month.MAY, 30,10,33);
        Meal meal = new Meal(dateTime, request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
        String id = request.getParameter("id");
        if(id == null || id.isEmpty())
        {
            mealDAO.addMeal(meal);
        }
        else
        {
            int mealId = Integer.parseInt(id);
            meal.setMealId(mealId);
            mealDAO.updateMeal(mealId, meal);
        }
        RequestDispatcher view = request.getRequestDispatcher(LIST_MEALS);
        request.setAttribute("localDateTimeFormat", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        request.setAttribute("mealList", MealsUtil.getFilteredWithExceeded(mealDAO.getAllMeals(), LocalTime.MIN, LocalTime.MAX, 2000));
        view.forward(request, response);
    }
}

