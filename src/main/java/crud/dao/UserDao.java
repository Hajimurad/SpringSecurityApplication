package crud.dao;

import crud.entity.User;

import java.util.List;

public interface UserDao {

    List<User> getAllUsers();
    List<User> getByName(String firstName, String lastName);

    User getById(Long id);
    User getByEmail(String email);

    boolean saveUser(User user);
    boolean removeUser(Long id);
    boolean updateUser(Long id, User user);

}
