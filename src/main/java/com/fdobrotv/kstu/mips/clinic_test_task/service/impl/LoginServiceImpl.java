package com.fdobrotv.kstu.mips.clinic_test_task.service.impl;

import com.fdobrotv.kstu.mips.clinic_test_task.dto.LoginDTO;
import com.fdobrotv.kstu.mips.clinic_test_task.dto.UserDTO;
import com.fdobrotv.kstu.mips.clinic_test_task.exception.AccessDeniedException;
import com.fdobrotv.kstu.mips.clinic_test_task.mapper.UserDTOMapper;
import com.fdobrotv.kstu.mips.clinic_test_task.model.UserEntity;
import com.fdobrotv.kstu.mips.clinic_test_task.repository.UserRepository;
import com.fdobrotv.kstu.mips.clinic_test_task.service.LoginService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;

    public LoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDTO login(LoginDTO loginDTO) throws AccessDeniedException {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(loginDTO.getEmail());
        UserEntity userEntity = userEntityOptional.orElseThrow(AccessDeniedException::new);
        return UserDTOMapper.toDTO(userEntity);
    }
}
