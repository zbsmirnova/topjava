package ru.javawebinar.topjava.to;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class MealTo extends BaseTo{
    private static final long serialVersionUID = 1L;

    @NotNull
    @DateTimeFormat
    private LocalDateTime dateTime;

    @NotBlank
    @Size(min = 2, max = 120, message = "description must be between 2 and 120 characters")
    private String description;

    @NotNull
    @Range(min = 10, max = 5000, message = "range must be between 10 and 5000")
    private int calories;


    public MealTo(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public MealTo(){}

    public void setDateTime(LocalDateTime localDateTime){ this.dateTime = localDateTime;}

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }


    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
