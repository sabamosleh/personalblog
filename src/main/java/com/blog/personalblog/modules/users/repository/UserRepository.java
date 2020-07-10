package com.blog.personalblog.modules.users.repository;

import com.blog.personalblog.modules.users.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<Users,Long> {


    Users findByEmail(String email);


    //to do a costume query add @Query to method

    @Query(nativeQuery = true , value = "select  * from Users u where u.email= :email")
    Users findMyEmail(@Param("email") String myEmail);


    @Query(value = "select u from Users u where u.name like concat( '%',:#{#users.name},'%')" +
            " and u.email like concat('%',:#{#users.email},'%') ")
    List<Users> searchUsers(@Param("users")Users users);
}
