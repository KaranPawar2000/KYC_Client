package com.infinito.KYC.service.interfac;

import com.infinito.KYC.dto.Response;
import com.infinito.KYC.entity.Role;

public interface IRoleService {


    Response add(Role role);

    Response getAllRoles();
}
