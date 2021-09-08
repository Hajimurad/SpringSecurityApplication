package crud.dao;

import crud.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao{

    public RoleDaoImpl() {}

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<Role> getAllRoles() {
        return new HashSet<>(entityManager.createQuery("FROM Role", Role.class).getResultList());
    }

    @Override
    public boolean removeRole(Long id) {
        entityManager.remove(getRoleById(id));
            return false;
    }

    @Override
    public Role getByRole(String role) {
        return entityManager.find(Role.class, role);
    }

    @Override
    public Role getRoleById(Long id) {
         return entityManager.find(Role.class, id);
    }

    @Override
    public boolean saveRole(Role role) {
        Role rolePre = entityManager.merge(role);
        entityManager.persist(rolePre);

        return true;
    }
}
