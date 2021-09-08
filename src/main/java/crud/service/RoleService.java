package crud.service;

import crud.entity.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {

    Set<Role> getAllRoles();

    Role getByRole(String role);
    Role getRoleById(Long id);

    boolean saveRole(Role role);
    boolean removeRole(Long id);
}
