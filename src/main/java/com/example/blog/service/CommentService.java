package com.example.blog.service;

import com.example.blog.domain.comment.Comment;
import com.example.blog.domain.post.Post;
import com.example.blog.domain.user.User;
import com.example.blog.dto.comment.CommentCreateRequest;
import com.example.blog.dto.comment.CommentResponse;
import com.example.blog.dto.comment.CommentUpdateRequest;
import com.example.blog.exception.BadRequestException;
import com.example.blog.exception.ForbiddenException;
import com.example.blog.exception.NotFoundException;
import com.example.blog.repository.CommentRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository,
                          PostRepository postRepository,
                          UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public CommentResponse create(CommentCreateRequest req, Authentication auth) {
        if (req == null || req.postId() == null || req.content() == null || req.content().isBlank()) {
            throw new BadRequestException("PostId e conteúdo são obrigatórios.");
        }

        Post post = postRepository.findById(req.postId())
                .orElseThrow(() -> new NotFoundException("Post não encontrado."));

        User user = findCurrentUser(auth);

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setAuthor(user);
        comment.setContent(req.content().trim());

        Comment saved = commentRepository.save(comment);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public CommentResponse findById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comentário não encontrado."));
        return toResponse(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> listByPost(Long postId) {
        return commentRepository.findByPost_Id(postId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public CommentResponse update(Long id, CommentUpdateRequest req, Authentication auth) {
        if (req == null || req.content() == null || req.content().isBlank()) {
            throw new BadRequestException("Conteúdo é obrigatório.");
        }

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comentário não encontrado."));

        if (!canModify(auth, comment)) {
            throw new ForbiddenException("Você não tem permissão para editar este comentário.");
        }

        comment.setContent(req.content().trim());
        Comment saved = commentRepository.save(comment);
        return toResponse(saved);
    }

    @Transactional
    public void delete(Long id, Authentication auth) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comentário não encontrado."));

        if (!canModify(auth, comment)) {
            throw new ForbiddenException("Você não tem permissão para deletar este comentário.");
        }

        commentRepository.delete(comment);
    }

    private boolean canModify(Authentication auth, Comment comment) {
        // ADMIN pode tudo
        if (hasRole(auth, "ADMIN")) return true;
        // Autor pode editar/deletar
        User current = findCurrentUser(auth);
        return comment.getAuthor().getId().equals(current.getId());
    }

    private boolean hasRole(Authentication auth, String role) {
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }

    private User findCurrentUser(Authentication auth) {
        String username = auth.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Usuário autenticado não encontrado."));
    }

    private CommentResponse toResponse(Comment c) {
        DateTimeFormatter fmt = DateTimeFormatter.ISO_INSTANT;
        return new CommentResponse(
                c.getId(),
                c.getPost().getId(),
                c.getAuthor().getId(),
                c.getAuthor().getUsername(),
                c.getContent(),
                fmt.format(c.getCreatedAt()),
                fmt.format(c.getUpdatedAt())
        );
    }
}

