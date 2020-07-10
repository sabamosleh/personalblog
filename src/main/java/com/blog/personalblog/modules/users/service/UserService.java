package com.blog.personalblog.modules.users.service;

import com.blog.personalblog.modules.users.model.Users;
import com.blog.personalblog.modules.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){

        this.userRepository=userRepository;

    }

    public Users registerUser(Users users){

        return this.userRepository.save(users);

    }

    public List<Users> getAllUsers(){

      return this.userRepository.findAll();

    }

    @Transactional
    public Users save(@Valid Users user) {


        String path = null;

        try {
            path = ResourceUtils.getFile("classpath:static/img").getAbsolutePath();
            byte[] bytes = user.getFile().getBytes();
            Files.write(Paths.get(path + File.separator+user.getFile().getOriginalFilename()),bytes);

        } catch (FileNotFoundException e) {
            e.printStackTrace();


        } catch (IOException e){

            e.printStackTrace();
        }
        user.setCover(user.getFile().getOriginalFilename());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        return userRepository.save(user);

    }


    public Users findById(Long id){

        return userRepository.getOne(id);

    }


    public Users findByEmail(String name) {
        return userRepository.findByEmail(name);
    }

    public List<Users> searchUser(Users users) {

        return userRepository.searchUsers(users);

    }

    public List<Users> findAllUsers() {
        return userRepository.findAll();
    }
}

