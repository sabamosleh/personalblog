package com.blog.personalblog.modules.posts.service;


import com.blog.personalblog.modules.posts.model.Category;
import com.blog.personalblog.modules.posts.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;


    @Autowired
    public CategoryService(CategoryRepository categoryRepository){

        this.categoryRepository=categoryRepository;

    }

    @Transactional
    public Category saveCategory(Category category){

        return categoryRepository.save(category);

    }

    public List<Category> findAllCategories(){

        return categoryRepository.findAll();

    }


    public Category findById(Long id) {
//        System.out.println("\n\n\nid in find by id is:"+id);
        return categoryRepository.getOne(id);

    }

    public void deleteBiId(Long id) {

        categoryRepository.deleteById(id);

    }
}
