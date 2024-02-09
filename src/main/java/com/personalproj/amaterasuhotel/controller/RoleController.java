package com.personalproj.amaterasuhotel.controller;

import com.personalproj.amaterasuhotel.exception.RoleAlreadyExistsException;
import com.personalproj.amaterasuhotel.model.RoleModel;
import com.personalproj.amaterasuhotel.model.UserModel;
import com.personalproj.amaterasuhotel.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/amaterasu/v1/api/role")
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/all-roles")
    public ResponseEntity<List<RoleModel>> getAllRoles() {
        return new ResponseEntity<>(roleService.getRoles(), HttpStatus.FOUND);
    }

    @PostMapping("/create-role")
    public ResponseEntity<String> createRole(@RequestBody RoleModel role) {
        try{
            roleService.createRole(role);
            return ResponseEntity.status(HttpStatus.CREATED).body("Role successfully created");
        } catch (RoleAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{roleId}")
    public void deleteRole(@PathVariable("roleId") Long roleId){
        roleService.deleteRole(roleId);
    }

    @PostMapping("/remove-all-users-from-role/{roleId}")
    public RoleModel removeAllUsersFromRole(@PathVariable("roleId") Long roleId) {
        return roleService.removeAllUsersFromRole(roleId);
    }

    @PostMapping("/remove-user-from-role")
    public UserModel removeUserFromRole(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId){
        return  roleService.removeUserFromRole(userId, roleId);
    }

    @PostMapping("/assign-role-to-user")
    public UserModel assignRoleToUser(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) {
        return roleService.assignRoleToUser(userId, roleId);
    }

}
