package com.fdobrotv.kstu.mips.clinic_test_task.service;

import com.fdobrotv.kstu.mips.clinic_test_task.dto.LoginDTO;
import com.fdobrotv.kstu.mips.clinic_test_task.dto.UserDTO;
import com.fdobrotv.kstu.mips.clinic_test_task.exception.AccessDeniedException;

public interface LoginService {
    UserDTO login(LoginDTO loginDTO) throws AccessDeniedException;
}
