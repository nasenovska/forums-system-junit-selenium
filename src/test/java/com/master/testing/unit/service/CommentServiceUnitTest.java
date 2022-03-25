package com.master.testing.unit.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.master.testing.entity.Comment;
import org.master.testing.entity.Role;
import org.master.testing.entity.Topic;
import org.master.testing.entity.User;
import org.master.testing.repository.CommentRepository;
import org.master.testing.service.CommentService;
import org.master.testing.service.impl.CommentServiceImpl;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CommentServiceUnitTest {

    private CommentService commentService;

    @MockBean
    private CommentRepository commentRepository;

    @BeforeEach
    void init() {
        commentService = new CommentServiceImpl(commentRepository);
    }

    @Test
    @DisplayName("Get comment by id")
    void getOne() {
        Mockito.doReturn(Optional.of(getData())).when(commentRepository).findById(1);

        Optional<Comment> optional = commentService.get(1);
        optional.ifPresent(c -> Assertions.assertEquals(getData().getId(), c.getId()));
    }

    @Test
    @DisplayName("Create comment")
    void create() {
        Comment comment = getData();
        Mockito.doReturn(comment).when(commentRepository).save(comment);

        Optional<Comment> optional = commentService.create(comment);
        optional.ifPresent(c -> Assertions.assertEquals(comment.getId(), c.getId()));
    }

    @Test
    @DisplayName("Update comment")
    void update() {
        Comment comment = getData();
        Mockito.doReturn(comment).when(commentRepository).save(comment);
        Optional<Comment> optional = commentService.create(comment);

        comment.setContent("Lorem ipsum updated");

        Mockito.doReturn(comment).when(commentRepository).save(comment);

        Optional<Comment> updated = commentService.update(comment);

        optional.ifPresent(c -> Assertions.assertEquals(comment.getId(), c.getId()));
        updated.ifPresent(c -> Assertions.assertEquals(comment.getContent(), c.getContent()));
        updated.ifPresent(c -> Assertions.assertEquals(comment.getTopic(), c.getTopic()));
        updated.ifPresent(c -> Assertions.assertEquals(comment.getUser(), c.getUser()));
    }

    @Test
    @DisplayName("Delete comment")
    void delete() {
        Mockito.doNothing().when(commentRepository).deleteById(1);

        commentService.delete(1);

        Mockito.verify(commentRepository, times(1)).deleteById(1);
    }

    Comment getData() {
        Comment comment = new Comment();
        comment.setId(1);
        comment.setContent("Lorem ipsum content");
        comment.setTopic(new Topic("Title", "Description", null));
        comment.setUser(new User("admin@example.com", "Qwerty1234", "Admin Admin", new Role("ROLE_ADMIN")));

        return comment;
    }
}
