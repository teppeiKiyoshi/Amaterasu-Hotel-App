package com.personalproj.amaterasuhotel.service;

import com.personalproj.amaterasuhotel.model.RoleModel;
import com.personalproj.amaterasuhotel.model.UserModel;

import java.util.List;

public interface RoleService {

    List<RoleModel> getRoles();
    RoleModel createRole(RoleModel role);
    void deleteRole(Long roleId);
    RoleModel findByRoleName(String roleName);
    UserModel removeUserFromRole(Long userId, Long roleId);
    UserModel assignRoleToUser(Long userId, Long roleId);
    RoleModel removeAllUsersFromRole(Long roleId);
}
