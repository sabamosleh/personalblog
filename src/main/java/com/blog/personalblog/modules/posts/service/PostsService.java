package com.blog.personalblog.modules.posts.service;


import com.blog.personalblog.modules.posts.model.Posts;
import com.blog.personalblog.modules.posts.repository.PostRepository;
import com.blog.personalblog.modules.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PostsService {


    private PostRepository postRepository;
    private UserService userService;


    @Autowired
    public PostsService(PostRepository postRepository,UserService userService){

        this.postRepository=postRepository;
        this.userService=userService;

    }


    public List<Posts> getPostsByTitle(String title){

        return postRepository.findByTitle(title);

    }

    public List<Posts> findAllPosts(){

        return postRepository.findAll();

    }

    public Page<Posts> findAllPosts(Pageable pageable){
        return postRepository.findAll(pageable);
    }


    @Transactional
    public Posts savePost(Posts posts){

        String path=null;

//        if(!posts.getFile().isEmpty()) {

            try {

                path = ResourceUtils.getFile("classpath:static/img").getAbsolutePath();
                byte[] bytes = posts.getImageFile().getBytes();
                Files.write(Paths.get(path + File.separator + posts.getImageFile().getOriginalFilename()), bytes);


            } catch (FileNotFoundException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();
            }
//        }
        posts.setCover(posts.getImageFile().getOriginalFilename());

        return this.postRepository.save(posts);

    }

    public Posts findById(Long id){
        return this.postRepository.getOne(id);
    }


    public void deleteById(Long id) {

        postRepository.deleteById(id);

    }

    public List<Posts> searchPost(Posts post) {
        return postRepository.findByTitleContaining(post.getTitle());
    }

    public List<Posts> findBySearch(Posts post) {
//        System.out.println("\n\n\n post category is: "+post.getCategories()+"\n\n\n");

        return postRepository.findBySearch(post,post.getCategories()!=null ? (long)post.getCategories().size() : 0);
    }

    public List<Posts> findPostByUser(Long id) {
        System.out.println("\n\n\n\n test athurizaiton....");
        System.out.println("\n\n\n\n"+userService.findById(id));
        return postRepository.findPostsByUser(userService.findById(id));

    }
}
