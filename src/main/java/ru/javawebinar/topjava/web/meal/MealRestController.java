package ru.javawebinar.topjava.web.meal;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@RestController
@RequestMapping(MealRestController.MEALS_URL)
public class MealRestController extends AbstractMealController{
    static final String MEALS_URL = "/rest/meals";
    static final String MEAL_FORM_URL = "rest/mealForm";


    @Override
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);


    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Meal meal, @PathVariable("id") int id) {
        super.update(meal, id);
    }


    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createMealForm() {
        Meal mealToCreate =  new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), "", 1000);

        return ResponseEntity.created(URI.create(MEALS_URL + "/create")).body(mealToCreate);
    }

    @PostMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> updateMealForm(@RequestBody Meal meal) {
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("rest/update" + "/{id}")
                .buildAndExpand(meal.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(meal);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> updateOrCreate(@RequestBody Meal meal) {
        if(meal.getId() == null){
            super.create(meal);
        }
        else{
            super.update(meal, meal.getId());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(MEALS_URL));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @PostMapping(value = "/filter/{startDate}/{endDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MealWithExceed>> getBetween(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @PathVariable("startDate")   LocalDateTime startDate,
                                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @PathVariable("endDate") LocalDateTime endDate){


        URI uriOfNewResource = URI.create(MEALS_URL);
        return ResponseEntity.created(uriOfNewResource)
                .body(super.getBetween(startDate.toLocalDate(), startDate.toLocalTime(), endDate.toLocalDate(), endDate.toLocalTime()));

    }
}