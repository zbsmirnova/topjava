package ru.javawebinar.topjava.web.user;

import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.List;

@RestController
@RequestMapping("/ajax/admin/users")
public class AdminAjaxController extends AbstractUserController {

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }


    @PostMapping("/{id}")
    @Transactional
    public void enableDisable(@PathVariable("id") int id, @RequestParam("enabled") boolean enabled) {
//        boolean enabledBool = false;
//        if(enabled.equals("true")) enabledBool = true;
        User user = super.get(id);
        user.setEnabled(!enabled);
        super.update(user, user.getId());
    }

    @PostMapping
    public void createOrUpdate(@RequestParam("id") Integer id,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password

    ) {

        User user = new User(id, name, email, password, Role.ROLE_USER);
        if (user.isNew()) {
            super.create(user);
        }
    }
}
