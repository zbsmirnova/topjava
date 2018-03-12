package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.util.List;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;


@Controller
public class MealRestController{
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    @Autowired
    private MealRestController(MealService service) {
        this.service = service;
    }

    public List<Meal> getAll(){
        return service.getAll(AuthorizedUser.id(), LocalDateTime.MIN, LocalDateTime.MAX, LocalDateTime.class);
    }
    public List<Meal> getAll(String startDate, String endDate, String startTime, String endTime){

        LocalTime localStartTime = startTime ==  null ?  LocalTime.MIN : startTime.equals("") ? LocalTime.MIN : LocalTime.parse(startTime);
        LocalTime localEndTime = endTime ==  null ?  LocalTime.MAX : endTime.equals("") ? LocalTime.MAX : LocalTime.parse(endTime);
        LocalDate localStartDate = startDate == null ?  LocalDate.MIN : startDate.equals("") ? LocalDate.MIN : LocalDate.parse(startDate);
        LocalDate localEndDate = endDate == null ?  LocalDate.MAX : endDate.equals("") ? LocalDate.MAX : LocalDate.parse(endDate);

        Temporal startTemporal;
        Temporal endTemporal;
        Class <? extends Comparable> type;

        if(startDate == null && endDate == null || startDate.equals("") && endDate.equals("")){
            startTemporal = localStartTime;
            endTemporal = localEndTime;
            type = LocalTime.class;
        }
        else if(startTime == null && endTime == null || startTime.equals("")&& endTime.equals("")){
            startTemporal = localStartDate;
            endTemporal = localEndDate;
            type = LocalDate.class;
        }
        else{
            startTemporal = LocalDateTime.of(localStartDate, localStartTime);
            endTemporal = LocalDateTime.of(localEndDate, localEndTime);
            type = LocalDateTime.class;
        }

        return service.getAll(AuthorizedUser.id(), startTemporal, endTemporal, type);
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, AuthorizedUser.id());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, AuthorizedUser.id());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, AuthorizedUser.id());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal, AuthorizedUser.id());
    }

}