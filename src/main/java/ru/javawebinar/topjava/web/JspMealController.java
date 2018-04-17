package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;


import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;
import static ru.javawebinar.topjava.util.MealsUtil.getWithExceeded;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;



@Controller
public class JspMealController extends MealRestController{

    @Autowired
    MealService mealService;

    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping("/meals")
    public String meals(HttpServletRequest request) {
        request.setAttribute("meals", getWithExceeded(mealService.getAll(AuthorizedUser.id()), DEFAULT_CALORIES_PER_DAY));
        return "meals";
    }

    @RequestMapping(value = "/meals/delete", params = "id", method = GET)
    public String deleteMeal(@RequestParam("id") int id) {
        mealService.delete(id, AuthorizedUser.id());
        return ("redirect:/meals");
    }

    @RequestMapping(value = "/meals/update", params = "id", method = GET)
    public String update(@RequestParam("id") int id, HttpServletRequest request) {
        final Meal meal = mealService.get(id, AuthorizedUser.id());
        request.setAttribute("meal", meal);
        return "mealForm";
    }

    @RequestMapping(params = {"save"}, method = POST)
    public String save(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (id.equals("")) mealService.create(meal, AuthorizedUser.id());
        else{
            meal.setId(Integer.parseInt(id));
            mealService.update(meal, AuthorizedUser.id());
        }
        return "redirect:/meals";
    }

    @RequestMapping(params = {"filter"}, method = POST)
    public String filter(HttpServletRequest request) throws UnsupportedEncodingException {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        request.setAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    @RequestMapping(value = "/meals/create", method = GET)
    public String create(Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }


}


