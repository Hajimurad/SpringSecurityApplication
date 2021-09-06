package crud.dao;

import crud.entity.Role;

import java.util.List;

public interface RoleDao {

    List<Role> getAllRoles();

    Role getByRole(String role);
    Role getRoleById(Long id);

    boolean saveRole(Role role);
    boolean removeRole(Long id);
}
