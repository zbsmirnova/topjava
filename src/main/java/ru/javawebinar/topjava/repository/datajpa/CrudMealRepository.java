package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Override
    List<Meal> findAll();

    @Transactional
    @Modifying
    @Query("DELETE from Meal m WHERE m.id=?1 AND m.user.id=?2")
    int delete(Integer id, Integer userId);

    Meal findByIdAndUserId(int id, int userId);

    List<Meal> findAllByUserIdOrderByDateTimeDesc(Integer userId);

    @Override
    Meal save(Meal meal);

    List<Meal> findByUserIdAndDateTimeBetweenOrderByDateTimeDesc(int userId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT m FROM Meal m JOIN FETCH m.user where m.id=?1 ")
    Meal getMealWithUser(int mealId);




}
