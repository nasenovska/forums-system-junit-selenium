package org.master.testing.service.impl;

import org.master.testing.entity.Comment;
import org.master.testing.repository.CommentRepository;
import org.master.testing.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * Business logic and data processing of {@link Comment} entity.
 */
@Service
public class CommentServiceImpl
        extends BaseServiceImpl<Comment>
        implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        super(commentRepository);
        this.commentRepository = commentRepository;
    }

    @Override
    public Page<Comment> getForUser(Integer userId) {
        return commentRepository.findAllByUserId(userId);
    }
}