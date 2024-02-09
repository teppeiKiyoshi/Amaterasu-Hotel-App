package com.personalproj.amaterasuhotel.service.impl;

import com.personalproj.amaterasuhotel.exception.RoleAlreadyExistsException;
import com.personalproj.amaterasuhotel.exception.UserAlreadyExistsException;
import com.personalproj.amaterasuhotel.model.RoleModel;
import com.personalproj.amaterasuhotel.model.UserModel;
import com.personalproj.amaterasuhotel.repository.RoleRepository;
import com.personalproj.amaterasuhotel.repository.UserRepository;
import com.personalproj.amaterasuhotel.service.RoleService;
import com.personalproj.amaterasuhotel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public List<RoleModel> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public RoleModel createRole(RoleModel role) {
        String roleName = "ROLE_" + role.getRoleName().toUpperCase();
        RoleModel newRole = new RoleModel(roleName);
        if(roleRepository.existsByRoleName(roleName)){
            throw new RoleAlreadyExistsException(newRole.getRoleName() + " already exists.");
        }
        return roleRepository.save(newRole);
    }

    @Override
    public void deleteRole(Long roleId) {
        this.removeAllUsersFromRole(roleId);
        roleRepository.deleteById(roleId);
    }

    @Override
    public RoleModel findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName).get();
    }

    @Override
    public UserModel removeUserFromRole(Long userId, Long roleId) {
        Optional<UserModel> user = userRepository.findById(userId);
        Optional<RoleModel> role = roleRepository.findById(roleId);
        if(role.isPresent() && role.get().getUsers().contains(user.get())){
            role.get().removeUserFromRole(user.get());
            roleRepository.save(role.get());
            return user.get();
        }
        throw new UsernameNotFoundException("User not found");
    }

    @Override
    public UserModel assignRoleToUser(Long userId, Long roleId) {
        Optional<UserModel> user = userRepository.findById(userId);
        Optional<RoleModel> role = roleRepository.findById(roleId);
        if(user.isPresent() && user.get().getRoles().contains(role.get())){
            throw new UserAlreadyExistsException(user.get().getFirstName() + " already assigned to the " + role.get().getName() + " role.");
        }
        if(role.isPresent()){
            role.get().assignRoleToUser(user.get());
            roleRepository.save(role.get());
        }
        return user.get();
    }

    @Override
    public RoleModel removeAllUsersFromRole(Long roleId) {
        Optional<RoleModel> role = roleRepository.findById(roleId);
        role.get().removeAllUsersFromRole();
        return roleRepository.save(role.get());
    }
}
