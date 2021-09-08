package crud.service;

import crud.dao.UserDao;
import crud.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao dao) {
        this.userDao = dao;
    }

    @Override
    public User getById(Long id) {
        return userDao.getById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public List<User> getByName(String firstName, String lastName) {
        return userDao.getByName(firstName, lastName);
    }

    @Override
    public User getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    @Override
    public boolean saveUser(User user) {
        return userDao.saveUser(user);
    }

    @Override
    public boolean removeUser(Long id) {
        return userDao.removeUser(id);
    }

    @Override
    public boolean updateUser(Long id, User user) {
        return userDao.updateUser(id, user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            return userDao.getByEmail(email);
    }
}
