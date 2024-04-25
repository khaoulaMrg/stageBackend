package com.Aswat.services.jwt.jwt;

import com.Aswat.reposistories.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.Aswat.entity.User;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailsSrvImp implements UserDetailsService {
    private  final UserRepo userRepo;



    public UserDetailsSrvImp(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Optional<User> optionalUser=userRepo.findFirstByEmail(email);
        if(optionalUser.isEmpty()) throw new UsernameNotFoundException("User not found",null);
        return  new org.springframework.security.core.userdetails.User(optionalUser.get().getEmail(),optionalUser.get().getPassword(),new ArrayList<>());

    }


}