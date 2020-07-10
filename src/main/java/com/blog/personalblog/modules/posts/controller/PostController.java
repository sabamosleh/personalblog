package com.blog.personalblog.modules.posts.controller;

import com.blog.personalblog.modules.posts.model.Posts;
import com.blog.personalblog.modules.posts.service.CategoryService;
import com.blog.personalblog.modules.posts.service.PostsService;
import com.blog.personalblog.modules.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {



    private PostsService postsService;
    private CategoryService categoryService;
    private UserService userService;




    @Autowired
    public PostController(PostsService postsService,CategoryService categoryService,UserService userService){
        this.userService=userService;
        this.postsService=postsService;
        this.categoryService=categoryService;

    }


    @RequestMapping(value = {"/rest/findPostByTitle"},method = RequestMethod.GET)
    public  @ResponseBody
      List<Posts>  findAllPostsByTitle(String title){
        return postsService.getPostsByTitle(title);
    }

    @RequestMapping(value = "/addPost",method = RequestMethod.POST)
    public Posts savePost(@RequestBody Posts posts){

        return postsService.savePost(posts);
    }

    @RequestMapping(value = "/rest/findAllPosts",method = RequestMethod.GET)
    public @ResponseBody
            List<Posts> findAllPosts(){

        return postsService.findAllPosts();

    }

    @RequestMapping(value = "/showRegisterPosts",method = RequestMethod.GET)
    public String showRegisterPosts(){

        return "posts/registerPosts";
    }

    @RequestMapping(value ={"","/"},method = RequestMethod.GET)
    public String showPosts(Model model, @PageableDefault(size = 5) Pageable pageable){
            model.addAttribute("posts",postsService.findAllPosts(pageable));
        return "posts/posts";
    }


    @RequestMapping(value = {"add"},method=RequestMethod.POST)
    public String add(@ModelAttribute Posts post){
            postsService.savePost(post);
        return "redirect:/index";

    }

    @RequestMapping(value = "/showHomePage")
    public String showHomePage(Model model){
        model.addAttribute("posts",postsService.findAllPosts());
        return "index";
    }

    @RequestMapping(value = "/registerPost",method = RequestMethod.POST)
    public @ResponseBody
    Posts registerPosts(@ModelAttribute Posts posts){
//        posts.setCover(posts.getImageFile().getOriginalFilename());
        return postsService.savePost(posts);

    }



    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(@ModelAttribute Posts post, Principal principal)
    {
//        System.out.println("\n\n\n\n post is: "+post.getCategories()+"\n\n\n\n");
        post.setUser(userService.findByEmail(principal.getName()));
        postsService.savePost(post);

        return "redirect:/posts";
    }

    @RequestMapping(value = "/register",method=RequestMethod.GET)
    public String showRegisterPage(Model model){
        model.addAttribute("post",new Posts());
        model.addAttribute("categories",categoryService.findAllCategories());
        return "posts/registerPosts";
    }

    @RequestMapping(value = "/edit/{id}",method = RequestMethod.GET)
    public String editPost(Model model, @PathVariable("id") Long id){

        model.addAttribute("post",postsService.findById(id));
        return "posts/registerPosts";

    }

    @RequestMapping(value="delete/{id}",method=RequestMethod.GET)
    public String delete(@PathVariable("id") Long id){

        postsService.deleteById(id);
        return "redirect:/posts";
    }


    @RequestMapping (value = "/rest/search",method = RequestMethod.GET)
    public @ResponseBody
    List<Posts> searchPost(@ModelAttribute Posts post){
        return postsService.findBySearch(post);

    }



}
