package com.master.testing.unit.controller;

import com.master.testing.unit.util.TestUtility;
import org.junit.jupiter.api.*;
import org.master.testing.entity.Topic;
import org.master.testing.security.UserCredentials;
import org.master.testing.service.TopicService;
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
public class TopicControllerUnitTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(TopicControllerUnitTest.class);

    private static final String VERSION = "/api/v1";
    private static final String RESOURCES = "/topics";
    private static final String PARAMETER = "/{id}";

    @MockBean
    private TopicService topicService;

    @Autowired
    private MockMvc mockMvc;

    private Topic topic;

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
        topic = new Topic();
        topic.setId(1);
        topic.setTitle("Lorem ipsum");
        topic.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
    }

    @Test
    @DisplayName("Return OK when topic exist")
    void getTopicOK() {
        Mockito.doReturn(Optional.of(topic)).when(topicService).get(1);

        try {
            mockMvc.perform(MockMvcRequestBuilders.get(VERSION.concat(RESOURCES).concat(PARAMETER), 1)
                            .header(HttpHeaders.AUTHORIZATION, token))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("Return NOT_FOUND when topic does not exist")
    void getTopicNotFound() {
        Mockito.doReturn(Optional.empty()).when(topicService).get(188);

        try {
            mockMvc.perform(MockMvcRequestBuilders.get(VERSION.concat(RESOURCES).concat(PARAMETER), 188)
                            .header(HttpHeaders.AUTHORIZATION, token))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("Return CREATED when topic is successfully created")
    void createTopicOK() {
        topic.setId(18);
        Mockito.doReturn(Optional.of(topic)).when(topicService).create(Mockito.any());

        try {
            mockMvc.perform(MockMvcRequestBuilders.post(VERSION.concat(RESOURCES))
                            .header(HttpHeaders.AUTHORIZATION, token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtility.objectToJson(topic)))
                    .andExpect(MockMvcResultMatchers.status().isCreated());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("Return OK when topic is successfully updated")
    void updateTopicOk() {
        topic.setDescription("Lorem ipsum UPDATED");
        topic.setId(18);

        Mockito.doReturn(Optional.of(topic)).when(topicService).get(18);
        Mockito.doReturn(Optional.of(topic)).when(topicService).update(Mockito.any());

        try {
            mockMvc.perform(MockMvcRequestBuilders.put(VERSION.concat(RESOURCES).concat(PARAMETER), 18)
                            .header(HttpHeaders.AUTHORIZATION, token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtility.objectToJson(topic)))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("Return NOT_FOUND on update topic that not exist")
    void updateTopicNotFound() {
        Mockito.doReturn(Optional.empty()).when(topicService).get(123);

        try {
            mockMvc.perform(MockMvcRequestBuilders.put(VERSION.concat(RESOURCES).concat(PARAMETER), 123)
                            .header(HttpHeaders.AUTHORIZATION, token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtility.objectToJson(topic)))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("Return OK when topic is deleted successfully")
    void deleteTopicOk() {
        Mockito.doReturn(Optional.of(topic)).when(topicService).get(1);

        try {
            mockMvc.perform(MockMvcRequestBuilders.delete(VERSION.concat(RESOURCES).concat(PARAMETER), 1)
                            .header(HttpHeaders.AUTHORIZATION, token))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("Return NOT_FOUND on delete topic that not exist")
    void deleteTopicNotFound() {
        Mockito.doReturn(Optional.empty()).when(topicService).get(1);

        try {
            mockMvc.perform(MockMvcRequestBuilders.delete(VERSION.concat(RESOURCES).concat(PARAMETER), 1)
                            .header(HttpHeaders.AUTHORIZATION, token))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
