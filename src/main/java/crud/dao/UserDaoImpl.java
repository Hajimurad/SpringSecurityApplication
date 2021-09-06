package crud.dao;

import crud.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    UserDao userDao;

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
                .getResultList(); // возможно нужен null

        if (user == null) {
            throw new NotFoundException(firstName, lastName);
        }
        return user;
    }


    @Override
    public User getByEmail(String email) {

        user = entityManager.createQuery(
                        "SELECT u FROM User u WHERE u.email LIKE :email", User.class)
                .setParameter("email", email)
                .getResultList().stream().findFirst().orElse(null);

        if (user == null) {
            throw new NotFoundException(email);
        }
        return user;
    }

    @Override
    public User getByPhone(String phone) {
         user = entityManager.createQuery(
                        "SELECT u FROM User u WHERE u.phone LIKE :phone", User.class)
                .setParameter("phone", phone)
                 .getResultList().stream().findFirst().orElse(null); // возможно нужен null

        if (user == null) {
            throw new NotFoundException(phone);
        }
        return user;
    }

    @Override
    public User getByUsername(String username) {
        return entityManager.createQuery(
                        "SELECT u FROM User u WHERE u.username LIKE :username", User.class)
                .setParameter("username", username)
                .getResultList().stream().findFirst().orElse(null); // возможно нужен null
    }

    @Override
    public boolean saveUser(User user) {
        User userFromDB = userDao.getByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }
        // Если использовать persist
        // https://stackoverflow.com/questions/6378526/org-hibernate-persistentobjectexception-detached-entity-passed-to-persist
        entityManager.merge(user);
        return true;
    }

    @Override
    public boolean updateUser(Long id, User user) {
        User userInDB = userDao.getById(user.getId());

        if(userInDB != null) {
            entityManager.merge(user);
            return true;
        } else
            return false;
    }

    @Override
    public boolean removeUser(Long id) {
        //User userInDB = userDao.getById(id);
        entityManager.remove(getById(id));

//        if(userInDB != null) {
//            entityManager.createQuery("DELETE FROM User u WHERE u.id=:id")
//                    .setParameter("id", id)
//                    .executeUpdate();
//            return true;
//        } else
//            return false

           return true;

    }
}
