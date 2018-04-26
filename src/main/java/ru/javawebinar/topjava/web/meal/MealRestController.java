package ru.javawebinar.topjava.web.meal;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.converter.DateConverterFactory;
import ru.javawebinar.topjava.util.converter.TimeConverterFactory;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@RestController
@RequestMapping(value = MealRestController.MEALS_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController{
    static final String MEALS_URL = "/rest/meals";

//    @Autowired
//    DateConverterFactory dateConverterFactory;
//
//    @Autowired
//    TimeConverterFactory timeConverterFactory;

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

    @Override
    @GetMapping(value = "/{id}")
    public Meal get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @GetMapping
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> updateMealForm(@RequestBody Meal meal) {
        Meal created = super.create(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("rest/update" + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PostMapping(value = "/filter/{startDate}/{endDate}")
    public ResponseEntity<List<MealWithExceed>> getBetween( @PathVariable("startDate")   String start,
                                                            @PathVariable("endDate") String end){

        DateConverterFactory dateConverterFactory = new DateConverterFactory();
        TimeConverterFactory timeConverterFactory = new TimeConverterFactory();

        URI uriOfNewResource = URI.create(MEALS_URL);
        LocalDate startDate = dateConverterFactory.getConverter(LocalDate.class).convert(start);
        LocalTime startTime = timeConverterFactory.getConverter(LocalTime.class).convert(start);
        LocalDate endDate = dateConverterFactory.getConverter(LocalDate.class).convert(end);
        LocalTime endTime = timeConverterFactory.getConverter(LocalTime.class).convert(end);

        List<MealWithExceed> meals = super.getBetween(startDate,startTime,
                endDate, endTime);
        return ResponseEntity.created(uriOfNewResource)
                .body(super.getBetween(startDate,startTime,
                        endDate, endTime));


    }
//
//    @PostMapping(value = "/filter/{startDate}/{endDate}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<MealWithExceed>> getBetween(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @PathVariable("startDate")   LocalDateTime startDate,
//                                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @PathVariable("endDate") LocalDateTime endDate){
//
//
//        URI uriOfNewResource = URI.create(MEALS_URL);
//        return ResponseEntity.created(uriOfNewResource)
//                .body(super.getBetween(startDate.toLocalDate(), startDate.toLocalTime(), endDate.toLocalDate(), endDate.toLocalTime()));
//
//    }
}