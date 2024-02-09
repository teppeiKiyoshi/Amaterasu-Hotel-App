package com.personalproj.amaterasuhotel.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class RoleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    private String roleName;
    @ManyToMany(mappedBy = "roles")
    private Collection<UserModel> users = new HashSet<>();

    public RoleModel(String roleName) {
        this.roleName = roleName;
    }

    public void assignRoleToUser(UserModel user){
        user.getRoles().add(this);
        this.getUsers().add(user);
    }

    public void removeUserFromRole(UserModel user){
        user.getRoles().remove(this);
        this.getUsers().remove(user);
    }

    public void removeAllUsersFromRole(){
        if(this.getUsers() != null) {
            List<UserModel> roleUsers = this.getUsers().stream().toList();
            roleUsers.forEach(this :: removeUserFromRole);
        }
    }

    public String getName(){
        return roleName != null ? roleName : "";
    }
}
