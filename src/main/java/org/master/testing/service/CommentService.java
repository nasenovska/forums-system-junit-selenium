package org.master.testing.service;

import org.master.testing.entity.Comment;
import org.springframework.data.domain.Page;

/**
 * Service providing business logic for {@link Comment} entity ready to be implemented.
 */
public interface CommentService extends BaseService<Comment> {

    /**
     * Used to retrieve {@link Comment}s for particular user.
     *
     * @param userId - the id of the user whose comments should be returned
     * @return paginated comments
     */
    Page<Comment> getForUser(Integer userId);
}
