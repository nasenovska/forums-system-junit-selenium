package org.master.testing.repository;

import org.master.testing.entity.Comment;
import org.springframework.data.domain.Page;

/**
 * Comment repository - a central place (as a storage) in which an aggregation of {@link Comment} is kept and maintained.
 */
public interface CommentRepository extends BaseRepository<Comment> {
    Page<Comment> findAllByUserId(Integer user);
}
