package crud.service;

import crud.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    Role getByRole(String role);
    Role getRoleById(Long id);

    boolean saveRole(Role role);
    boolean removeRole(Long id);
}
