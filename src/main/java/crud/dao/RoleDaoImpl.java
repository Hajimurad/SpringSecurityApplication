package crud.dao;

import crud.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    RoleDao roleDao;

    private Role role;

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("FROM Role", Role.class).getResultList();
    }

    @Override
    public boolean removeRole(Long id) {
        Role roleInDB = roleDao.getRoleById(role.getId());

        if(roleInDB != null) {
            entityManager.createQuery("DELETE FROM Role u WHERE u.id=:id")
                    .setParameter("id", id)
                    .executeUpdate();
            return true;
        } else
            return false;
    }

    @Override
    public Role getByRole(String role) {
        return entityManager.createQuery(
                        "SELECT u FROM Role u WHERE u.role LIKE :role", Role.class)
                .setParameter("role", role)
                .getResultList().stream().findFirst().orElse(null); // возможно нужен null
    }

    @Override
    public Role getRoleById(Long id) {
         role = entityManager.find(Role.class, id);
        if (role == null) {
            throw new NotFoundException(id);
        }
        return role;
    }

    @Override
    public boolean saveRole(Role role) {
        Role roleFromDB = roleDao.getByRole(role.getRole());

        if (roleFromDB != null) {
            return false;
        }
        // Править потом. Если использовать persist
        // https://stackoverflow.com/questions/6378526/org-hibernate-persistentobjectexception-detached-entity-passed-to-persist
        entityManager.merge(role);

        return true;
    }
}
