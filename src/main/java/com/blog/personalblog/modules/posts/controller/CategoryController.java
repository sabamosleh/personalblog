package com.blog.personalblog.modules.posts.controller;

import com.blog.personalblog.modules.posts.model.Category;
import com.blog.personalblog.modules.posts.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {


    private CategoryService categoryService;


    @Autowired
    public CategoryController(CategoryService categoryService) {

        this.categoryService = categoryService;

    }


    @RequestMapping(value = {"/saveCategory"}, method = RequestMethod.POST)
    public @ResponseBody
    Category saveCategory(@RequestBody Category category) {

        return categoryService.saveCategory(category);

    }


    @RequestMapping(value = {"/rest/categories"}, method = RequestMethod.GET)
    public @ResponseBody
    List<Category> findAllCategories() {

        return categoryService.findAllCategories();

    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showCategories(Model modle) {

        modle.addAttribute("categories", categoryService.findAllCategories());

        return "categories/categories";
    }


    @RequestMapping(value = "/registerCategory", method = RequestMethod.GET)
    public String showRegisterCategoryPage(Model model) {
//        System.out.println("\n\n\n\n" + " in showRegisterCategoryPage" + "\n\n\n\n");
        model.addAttribute("category", new Category());
        return "categories/registerCategories";
    }



    @RequestMapping(value = "/registerCategory", method = RequestMethod.POST)
    public String register(@ModelAttribute @Valid Category category, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return "categories/registerCategories";
        }

        categoryService.saveCategory(category);
        return "redirect:/categories";
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteBiId(id);
        return "redirect:/categories";

    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id,Model model) {
        model.addAttribute("category",categoryService.findById(id));

        return "categories/registerCategories";

    }
}