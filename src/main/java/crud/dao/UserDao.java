package crud.dao;

import crud.entity.User;

import java.util.List;

public interface UserDao {

    List<User> getAllUsers();
    List<User> getByName(String firstName, String lastName);

    User getByUsername(String username);
    User getById(Long id);
    User getByEmail(String email);
    User getByPhone(String phone);

    boolean saveUser(User user);
    boolean removeUser(Long id);
    boolean updateUser(Long id, User user);

}
