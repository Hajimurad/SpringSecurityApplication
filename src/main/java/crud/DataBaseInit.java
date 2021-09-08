package crud;

import crud.entity.Role;
import crud.entity.User;
import crud.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataBaseInit implements InitializingBean {

    private final UserService userService;

    @Autowired
    public DataBaseInit(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void afterPropertiesSet() {
        Role role1 = new Role("ADMIN");
        Role role2 = new Role("USER");
        Set<Role> rolesSet1 = new HashSet<>();
        rolesSet1.add(role1);
        rolesSet1.add(role2);

        Set<Role> rolesSet2 = new HashSet<>();
        rolesSet2.add(role2);

        //Логин:e Пароль:password
        userService.saveUser(new User("$2a$12$PJ7W4DrDJOOpYsFaGAzdqOY24e2ysfIksxeuA6O8o4OQRN50OEO52", "name1", "surname1",
               "e", 21, rolesSet1));

        //Логин:e2@.com Пароль: qwerty
        userService.saveUser(new User("$2a$12$PJ7W4DrDJOOpYsFaGAzdqOY24e2ysfIksxeuA6O8o4OQRN50OEO52", "name2", "surname2",
                "e2@.com", 26, rolesSet2));

        System.out.println("USERS_SAVED");
    }
}
