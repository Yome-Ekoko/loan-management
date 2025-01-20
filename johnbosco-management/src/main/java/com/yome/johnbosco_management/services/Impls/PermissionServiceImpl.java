package com.yome.johnbosco_management.services.Impls;

import com.yome.johnbosco_management.enums.RoleType;
import com.yome.johnbosco_management.exceptions.PermissionDeniedException;
import com.yome.johnbosco_management.models.Role;
import com.yome.johnbosco_management.models.Users;
import com.yome.johnbosco_management.services.PermissionService;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {


    @Override
    public Users checkLoggedInUser() {
        // Mock implementation of logged-in user with ADMIN role
        Role adminRole = new Role( RoleType.ADMIN);
        Users mockUser = new Users();
        mockUser.setUsername("adminUser");
        mockUser.setRole(new Role( RoleType.ADMIN));



        return mockUser;
    }

    @Override
    public boolean checkUserRole(Users user, RoleType roleType) {
        if (user == null || user.getRole() == null || user.getRole().getRole() == null) {
            return false;
        }

        boolean hasRole = user.getRole().getRole() == roleType;
        return hasRole;
    }


    @Override
    public boolean canApproveOrRejectLoan(Users user) {
        if (checkUserRole(user, RoleType.ADMIN)) {
            return true;
        }

        throw new PermissionDeniedException("Access denied: Only admins can approve or reject loans.");
    }
}
