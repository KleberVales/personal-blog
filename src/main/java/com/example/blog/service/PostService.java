package com.example.blog.service;

import com.example.blog.domain.Post;
import com.example.blog.domain.User;
import com.example.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {

   private PostRepository repo;

   public PostService(PostRepository repo) {
       this.repo = repo;
   }

    //=======================================================================
    //          create post
    //=======================================================================

    public Post createPost(String title, String text, User user){

       Post post = new Post();

        post.setTitle(title);
        post.setText(text);
        post.setUser(user);

        return repo.save(post);

    }
}
