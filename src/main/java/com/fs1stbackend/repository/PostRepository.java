package com.fs1stbackend.repository;

import com.fs1stbackend.model.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class PostRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Post save(Post post) {
        em.persist(post);
        return post;
    }

    @Transactional
    public Post update(Post post) {
        return em.merge(post);
    }

    @Transactional
    public void deleteById(Long id) {
        Post post = em.find(Post.class, id);
        if (post != null) {
            em.remove(post);
        }
    }

    public Post findById(Long id) {
        return em.find(Post.class, id);
    }

    public List<Post> findAll() {
        return em.createQuery("SELECT p FROM Post p", Post.class).getResultList();
    }
}
