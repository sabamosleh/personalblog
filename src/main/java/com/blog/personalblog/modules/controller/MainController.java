package com.blog.personalblog.modules.controller;


import com.blog.personalblog.modules.posts.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {


    private PostsService postsService;

    @Autowired
    public MainController(PostsService postsService){

        this.postsService=postsService;

    }

    @RequestMapping("")
    public String showMainPage(Model model){

        model.addAttribute("posts",postsService.findAllPosts());

        return "index";

    }

    @RequestMapping(value = "/login")
    public String showLoginPage(){

        return "login";

    }



    @RequestMapping(value = "/users")
    public String users(){
        return "users/users";
    }








}
