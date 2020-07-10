package com.blog.personalblog.modules.posts.repository;

import com.blog.personalblog.modules.posts.model.Category;
import com.blog.personalblog.modules.posts.model.Posts;
import com.blog.personalblog.modules.users.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository  extends JpaRepository<Posts,Long> {

     List<Posts> findByTitle(String title);
     List<Posts> findAll();
     List<Posts> findByTitleContaining(String title);


//     @Query("select p from Posts p where :#{#post.title} is null or p.title like concat('%',:#{#post.title},'%')")
     @Query("select p from Posts p join p.categories pc where (:#{#post.title} is null or p.title like concat('%',:#{#post.title},'%'))" +
            "and (coalesce(:#{#post.categories},null) is null " +
            "or pc in (:#{#post.categories})) group by p.id having count(p.id) >= :num ")
     List<Posts> findBySearch(@Param("post")Posts post,@Param("num") Long num);

//    List<Posts> findPostByUser(Long id);
    
     List<Posts> findPostsByUser(Users user);

//     @Query("select p from Posts p join p.categories pc where pc in ('81')")
//     List<Posts> findBy_Categories(List<Category> category);

}
