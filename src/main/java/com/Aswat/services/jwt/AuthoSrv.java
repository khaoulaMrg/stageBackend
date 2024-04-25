package com.Aswat.services.jwt;

import com.Aswat.Dtos.SignupRequest;
import com.Aswat.Dtos.UserDto;

public interface AuthoSrv {
    UserDto createUser(SignupRequest signupRequest);
}
