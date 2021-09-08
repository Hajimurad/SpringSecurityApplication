package crud.dao;

import crud.entity.Role;
import crud.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{

    public UserDaoImpl() {
    }

    @PersistenceContext
    EntityManager entityManager;

    private User user;

    @Override
    public User getById(Long id) {

        user = entityManager.find(User.class, id);
        if (user == null) {
            throw new NotFoundException(id);
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public List<User> getByName(String firstName, String lastName) {
        List<User> user =  entityManager.createQuery(
                        "SELECT u FROM User u WHERE u.firstName LIKE :firstname AND u.lastName LIKE :lastname", User.class)
                .setParameter("firstname", firstName)
                .setParameter("lastname", lastName)
                .getResultList();

        if (user == null) {
            throw new NotFoundException(firstName, lastName);
        }
        return user;
    }

    @Override
    public User getByEmail(String email) {
        return entityManager.createQuery(
                        "SELECT u FROM User u WHERE u.email LIKE :email", User.class)
                .setParameter("email", email).getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public boolean saveUser(User user) {
        User userPre = entityManager.merge(user);
        entityManager.persist(userPre);
        return true;
    }

    @Override
    public boolean updateUser(Long id, User user) {
        entityManager.merge(user);
           return true;
    }

    @Override
    public boolean removeUser(Long id) {
        entityManager.remove(getById(id));
           return true;
    }
}
