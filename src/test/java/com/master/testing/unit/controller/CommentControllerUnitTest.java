package com.master.testing.unit.controller;

import com.master.testing.unit.util.TestUtility;
import org.junit.jupiter.api.*;
import org.master.testing.entity.Comment;
import org.master.testing.entity.Role;
import org.master.testing.entity.Topic;
import org.master.testing.entity.User;
import org.master.testing.security.UserCredentials;
import org.master.testing.service.CommentService;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommentControllerUnitTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentControllerUnitTest.class);

    private static final String VERSION = "/api/v1";
    private static final String RESOURCES = "/comments";
    private static final String PARAMETER = "/{id}";

    @MockBean
    private CommentService commentService;

    @Autowired
    private MockMvc mockMvc;

    private Comment comment;

    private String token;

    @BeforeAll
    void getAuthentication() {
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setEmail("admin@example.com");
        userCredentials.setPassword("Qwerty123456");

        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(VERSION.concat("/auth"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtility.objectToJson(userCredentials)))
                    .andReturn();

            token = mvcResult.getResponse().getHeader(HttpHeaders.AUTHORIZATION);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @BeforeEach
    void initData() {
        comment = new Comment();
        comment.setContent("Lorem ipsum content");
        comment.setTopic(new Topic("Title", "Description", null));
        comment.setUser(new User("admin@example.com", "Qwerty1234", "Admin Admin", new Role("ROLE_ADMIN")));
    }

    @Test
    @DisplayName("Return OK when comment exist")
    void getCommentOK() {
        Mockito.doReturn(Optional.of(comment)).when(commentService).getForUser(1);

        try {
            mockMvc.perform(MockMvcRequestBuilders.get(VERSION.concat(RESOURCES).concat(PARAMETER), 1)
                            .header(HttpHeaders.AUTHORIZATION, token))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("Return NOT_FOUND when comment does not exist")
    void getCommentNotFound() {
        Mockito.doReturn(Optional.empty()).when(commentService).getForUser(188);

        try {
            mockMvc.perform(MockMvcRequestBuilders.get(VERSION.concat(RESOURCES).concat(PARAMETER), 188)
                            .header(HttpHeaders.AUTHORIZATION, token))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("Return CREATED when comment is successfully created")
    void createCommentOK() {
        comment.setId(18);
        Mockito.doReturn(Optional.of(comment)).when(commentService).create(Mockito.any());

        try {
            mockMvc.perform(MockMvcRequestBuilders.post(VERSION.concat(RESOURCES))
                            .header(HttpHeaders.AUTHORIZATION, token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtility.objectToJson(comment)))
                    .andExpect(MockMvcResultMatchers.status().isCreated());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("Return BAD_REQUEST when comment is successfully created")
    void createCommentBadRequest() {
        comment.setId(20);
        comment.setContent(null);
        Mockito.doReturn(Optional.of(comment)).when(commentService).create(Mockito.any());

        try {
            mockMvc.perform(MockMvcRequestBuilders.post(VERSION.concat(RESOURCES))
                            .header(HttpHeaders.AUTHORIZATION, token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtility.objectToJson(comment)))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("Return OK when comment is successfully updated")
    void updateCommentOk() {
        comment.setContent("Lorem ipsum UPDATED");
        comment.setId(18);

        Mockito.doReturn(Optional.of(comment)).when(commentService).get(18);
        Mockito.doReturn(Optional.of(comment)).when(commentService).update(Mockito.any());

        try {
            mockMvc.perform(MockMvcRequestBuilders.put(VERSION.concat(RESOURCES).concat(PARAMETER), 18)
                            .header(HttpHeaders.AUTHORIZATION, token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtility.objectToJson(comment)))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("Return NOT_FOUND on update comment that not exist")
    void updateCommentNotFound() {
        Mockito.doReturn(Optional.empty()).when(commentService).get(123);

        try {
            mockMvc.perform(MockMvcRequestBuilders.put(VERSION.concat(RESOURCES).concat(PARAMETER), 123)
                            .header(HttpHeaders.AUTHORIZATION, token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtility.objectToJson(comment)))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("Return OK when comment is deleted successfully")
    void deleteCommentOk() {
        Mockito.doReturn(Optional.of(comment)).when(commentService).get(1);

        try {
            mockMvc.perform(MockMvcRequestBuilders.delete(VERSION.concat(RESOURCES).concat(PARAMETER), 1)
                            .header(HttpHeaders.AUTHORIZATION, token))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("Return NOT_FOUND on delete comment that not exist")
    void deleteCommentNotFound() {
        Mockito.doReturn(Optional.empty()).when(commentService).get(1);

        try {
            mockMvc.perform(MockMvcRequestBuilders.delete(VERSION.concat(RESOURCES).concat(PARAMETER), 1)
                            .header(HttpHeaders.AUTHORIZATION, token))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
