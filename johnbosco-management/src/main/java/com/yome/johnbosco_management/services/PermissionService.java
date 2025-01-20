package com.yome.johnbosco_management.services;

import com.yome.johnbosco_management.enums.RoleType;
import com.yome.johnbosco_management.models.Users;

public interface PermissionService {
    Users checkLoggedInUser();
    boolean checkUserRole(Users user, RoleType roleType);
    boolean canApproveOrRejectLoan(Users user);

}
