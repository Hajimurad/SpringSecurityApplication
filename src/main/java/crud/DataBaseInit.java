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

        Date data1 = new Date(12/12/2001);
        Date data2 = new Date(12/12/2000);

        //Логин:un1 Пароль:password
        userService.saveUser(new User("un1", "$2a$12$PJ7W4DrDJOOpYsFaGAzdqOY24e2ysfIksxeuA6O8o4OQRN50OEO52", "pass1", "fn1", "s1",
               "e1@.com", "1234567898", data1, rolesSet1));

        //Логин:un2 Пароль: qwerty
        userService.saveUser(new User("un2", "$2a$12$dHVTvD81JUgjaCPLzLFInexX4UBVHqygTC0WSAdd5civVrzMeR92G", "pass2", "fn2", "s2",
                "e2@.com", "1234567890", data2, rolesSet2));

        System.out.println("USERS_SAVED");
    }
}
