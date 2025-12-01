package com.infinito.KYC.service.interfac;

import com.infinito.KYC.dto.Response;
import com.infinito.KYC.entity.Branch;
import com.infinito.KYC.entity.User;

public interface IBranchService {

    Response add(Branch branch);
    Response getAllBranch();
    Response getBranchById(Long id);
    Response updateBranch(Long id, Branch upDatedBranch);

}
