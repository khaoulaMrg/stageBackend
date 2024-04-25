package com.Aswat.Controllers;

import com.Aswat.Dtos.AuthenticationRequest;
import com.Aswat.Dtos.AuthenticationResponse;
import com.Aswat.Dtos.SignupRequest;
import com.Aswat.Dtos.UserDto;
import com.Aswat.entity.User;

import com.Aswat.reposistories.UserRepo;
import com.Aswat.services.jwt.AuthoSrv;
import com.Aswat.services.jwt.jwt.UserDetailsSrvImp;
import com.Aswat.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Aswat.services.jwt.AuthoSrv;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/autho")
public class AuthoController {

    private final AuthoSrv authoSrv;
    private final AuthenticationManager authenticationManager;

    private final UserDetailsSrvImp userDetailsService;
    private final JwtUtil jwtUtil;

    private final UserRepo userRepo;


    public AuthoController(AuthoSrv authoSrv, AuthenticationManager authenticationManager, UserDetailsSrvImp userDetailsSrv, UserDetailsSrvImp userDetailsService, JwtUtil jwtUtil, UserRepo userRepo) {
        this.authoSrv = authoSrv;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {
        UserDto createdUserDto = authoSrv.createUser(signupRequest);
        if (createdUserDto == null) {
            return new ResponseEntity<>("User not created", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws IOException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password");
        } catch (DisabledException disabledException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not active");
            return null;
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        Optional<User> optionalUser = userRepo.findFirstByEmail(userDetails.getUsername());
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
            authenticationResponse.setUserId(optionalUser.get().getId());
        }
        return authenticationResponse;
    }
}