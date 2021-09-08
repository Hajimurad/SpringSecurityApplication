package crud.service;

import crud.dao.RoleDao;
import crud.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{
    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Set<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    public Role getByRole(String role) {
        return roleDao.getByRole(role);
    }

    @Override
    public Role getRoleById(Long id) {
        return roleDao.getRoleById(id);
    }

    @Override
    public boolean saveRole(Role role) {
        return roleDao.saveRole(role);
    }

    @Override
    public boolean removeRole(Long id) {
        return roleDao.removeRole(id);
    }
}
