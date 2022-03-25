package org.master.testing.service.impl;

import org.master.testing.entity.Topic;
import org.master.testing.repository.TopicRepository;
import org.master.testing.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Business logic and data processing of {@link Topic} entity.
 */
@Service
public class TopicServiceImpl
        extends BaseServiceImpl<Topic>
        implements TopicService {

    @Autowired
    public TopicServiceImpl(TopicRepository topicRepository) {
        super(topicRepository);
    }
}
