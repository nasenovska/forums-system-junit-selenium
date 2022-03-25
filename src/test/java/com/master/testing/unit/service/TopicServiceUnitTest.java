package com.master.testing.unit.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.master.testing.entity.Topic;
import org.master.testing.repository.TopicRepository;
import org.master.testing.service.TopicService;
import org.master.testing.service.impl.TopicServiceImpl;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TopicServiceUnitTest {

    private TopicService topicService;

    @MockBean
    private TopicRepository topicRepository;

    @BeforeEach
    void init() {
        topicService = new TopicServiceImpl(topicRepository);
    }

    @Test
    @DisplayName("Get topic by id")
    void getOne() {
        Mockito.doReturn(Optional.of(getData())).when(topicRepository).findById(1);

        Optional<Topic> optional = topicService.get(1);
        optional.ifPresent(c -> Assertions.assertEquals(getData().getId(), c.getId()));
    }

    @Test
    @DisplayName("Create topic")
    void create() {
        Topic topic = getData();
        Mockito.doReturn(topic).when(topicRepository).save(topic);

        Optional<Topic> optional = topicService.create(topic);
        optional.ifPresent(c -> Assertions.assertEquals(topic.getId(), c.getId()));
    }

    @Test
    @DisplayName("Update topic")
    void update() {
        Topic topic = getData();
        Mockito.doReturn(topic).when(topicRepository).save(topic);
        Optional<Topic> optional = topicService.create(topic);

        topic.setTitle("Lorem ipsum updated");
        topic.setDescription("Lorem ipsum updated");

        Mockito.doReturn(topic).when(topicRepository).save(topic);

        Optional<Topic> updated = topicService.update(topic);

        optional.ifPresent(c -> Assertions.assertEquals(topic.getId(), c.getId()));
        updated.ifPresent(c -> Assertions.assertEquals(topic.getTitle(), c.getTitle()));
        updated.ifPresent(c -> Assertions.assertEquals(topic.getDescription(), c.getDescription()));
    }

    @Test
    @DisplayName("Delete course")
    void delete() {
        Mockito.doNothing().when(topicRepository).deleteById(1);

        topicService.delete(1);

        Mockito.verify(topicRepository, times(1)).deleteById(1);
    }

    Topic getData() {
        Topic topic = new Topic();
        topic.setId(1);
        topic.setTitle("Lorem ipsum");
        topic.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");

        return topic;
    }
}
