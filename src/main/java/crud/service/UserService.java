package crud.service;

import crud.entity.User;

import java.util.List;

public interface UserService  {

    User getById(Long id);
    List<User> getAllUsers();
    List<User> getByName(String firstName, String lastName);
    User getByEmail(String email);
    boolean saveUser(User user);
    boolean removeUser(Long id);
    boolean updateUser(Long id, User user);
}
