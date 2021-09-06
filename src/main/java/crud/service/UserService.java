package crud.service;

import crud.entity.User;

import java.util.List;

public interface UserService  {

    //@Secured({"ROLE_ADMIN"})
    User getById(Long id);
    //@Secured ({"ROLE_ADMIN"})
    List<User> getAllUsers();
    //@Secured ({"ROLE_ADMIN"})
    List<User> getByName(String firstName, String lastName);
    //@Secured ({"ROLE_ADMIN"})
    User getByEmail(String email);
    //@Secured ({"ROLE_ADMIN"})
    User getByPhone(String phone);
    //@Secured ({"ROLE_ADMIN"})
    User getByUsername(String username);
    //@Secured ({"ROLE_ADMIN"})
    boolean saveUser(User user);
    //@Secured ({"ROLE_ADMIN", "ROLE_USER"})
    boolean removeUser(Long id);
    //@Secured ({"ROLE_ADMIN", "ROLE_USER"})
    boolean updateUser(long id, User user);
}
