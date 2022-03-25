package org.master.testing.controller;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.master.testing.entity.Topic;
import org.master.testing.exception.ErrorResponse;
import org.master.testing.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Api endpoints for using the topic functionality
 *
 * @version 1.0
 * @implNote {@link Topic}
 * @apiNote /api/v1/topics
 */
@Validated
@RestController
@RequestMapping(value = "/api/v1/topics")
public class TopicController {

    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Page of topics is retrieved successfully.")
    })
    @GetMapping
    public HttpEntity<?> get(Pageable pageable) {
        Optional<Page<Topic>> optionalPage = topicService.get(pageable);

        return optionalPage.map(r -> ResponseEntity.ok(r.get()))
                .orElseGet(() -> ResponseEntity.ok().build());
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Topic is retrieved by id."),
            @ApiResponse(code = 404, message = "Topic with this id is not found.")
    })
    @GetMapping(value = "/{id}")
    public HttpEntity<?> getById(@PathVariable("id") Integer id) {
        Optional<Topic> optional = topicService.get(id);

        return optional.map(r -> ResponseEntity.ok(r))
                .orElseGet(() -> new ResponseEntity(new ErrorResponse("Resource not found."), HttpStatus.BAD_REQUEST));
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Topic is created successfully."),
            @ApiResponse(code = 400, message = "Topic object is incorrect of incomplete.")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public HttpEntity<?> create(@Valid @RequestBody Topic topic) {
        Optional<Topic> optional = topicService.create(topic);

        return optional.map(r -> ResponseEntity.ok(r))
                .orElseGet(() -> new ResponseEntity(new ErrorResponse("Object is incorrect."), HttpStatus.BAD_REQUEST));
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Topic is updated successfully."),
            @ApiResponse(code = 400, message = "Topic object is incorrect of incomplete."),
            @ApiResponse(code = 404, message = "Topic with this id is not found.")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public HttpEntity<?> update(@PathVariable("id") Integer id, @Valid @RequestBody Topic topic) {
        Optional<Topic> optional = topicService.get(id);
        if (!optional.isPresent()) {
            return new ResponseEntity(new ErrorResponse("Resource is not found."), HttpStatus.NOT_FOUND);
        }

        optional = topicService.update(topic);

        return optional.map(r -> ResponseEntity.ok(r))
                .orElseGet(() -> new ResponseEntity(new ErrorResponse("Object is incorrect."), HttpStatus.BAD_REQUEST));
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Topic is updated successfully."),
            @ApiResponse(code = 404, message = "Topic with this id is not found.")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public HttpEntity<?> delete(@PathVariable("id") Integer id) {
        Optional<Topic> optional = topicService.get(id);
        if (!optional.isPresent()) {
            return new ResponseEntity(new ErrorResponse("Resource is not found."), HttpStatus.NOT_FOUND);
        }

        topicService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
