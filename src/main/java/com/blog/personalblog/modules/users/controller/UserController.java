package com.blog.personalblog.modules.users.controller;

import com.blog.personalblog.modules.posts.model.Posts;
import com.blog.personalblog.modules.posts.service.PostsService;
import com.blog.personalblog.modules.users.model.Users;
import com.blog.personalblog.modules.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/users")
public class UserController {


    private UserService userService;
    private PostsService postsService;

    @Autowired
    public UserController(UserService userService, PostsService postsService) {
        this.userService = userService;
        this.postsService=postsService;
    }

    @RequestMapping("/hi")
    public String home() {
        return "welcome to saba's server";
    }

    @RequestMapping(value = {"/rest/allUsers"}, method = RequestMethod.GET)
    public @ResponseBody
    List<Users> getUsers() {

        return this.userService.getAllUsers();

    }

    @RequestMapping(value = {"/rest/register"}, method = RequestMethod.POST)
    public @ResponseBody
    Users registerUser(@RequestBody Users user) {

        return userService.registerUser(user);

    }

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String getUsers(Model model) {
//        System.out.println("\n\n\n"+userService.getAllUsers());
        model.addAttribute("users", userService.getAllUsers());
        return "users/users";
    }

//    @RequestMapping(value={"/users/edit/{id}"},method = RequestMethod.GET)
//    public String editUser(){
//
//    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    public String register(@ModelAttribute(name = "user") @Valid Users user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
//            System.out.println("binding error"+bindingResult.getFieldError());
            return "users/registerUser";
        }

//        System.out.println("\n\n\n"+user.getUserID()+"\n\n\n");
        userService.save(user);
        return "redirect:/users";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("user", new Users());
        return "users/registerUser";

    }


    @RequestMapping(value = {"/edit/{id}"}, method = RequestMethod.GET)
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.findById(id));
        return "users/registerUser";
    }


    @RequestMapping(value = "/postsByUser/{id}", method = RequestMethod.GET)
    public String findPostsByUserID(@PathVariable("id") Long id,Model model) {
        model.addAttribute("posts",postsService.findPostByUser(id));
        return "index";

    }

    @RequestMapping(value = "/rest/postsByUser/{id}", method = RequestMethod.GET)
    public @ResponseBody
    List<Posts>findPostsByUserID_v2(@PathVariable("id") Long id){
        return postsService.findPostByUser(id);


    }

//    @RequestMapping(value = "/rest/search",method = RequestMethod.GET)
//    public @ResponseBody
//    List<Users> searchedUsers(@ModelAttribute Users users){
//        return userService.searchUser(users);
//    }

    @RequestMapping(value="/search",method = RequestMethod.GET)
     public String searchedUsers(@ModelAttribute Users users,Model model) {
        model.addAttribute("users",userService.searchUser(users));
        return "users/users";
    }

    @RequestMapping(value = "/profiles",method = RequestMethod.GET)
    public String showProfiles(Model model){
//        System.out.println("\n\n\n\n"+userService.findAllUsers().size()+"\n\n\n");
        model.addAttribute("users",userService.findAllUsers());
        return "users/Profiles";

    }
}