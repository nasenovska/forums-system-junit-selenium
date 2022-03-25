package org.master.testing.controller;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.master.testing.entity.Comment;
import org.master.testing.exception.ErrorResponse;
import org.master.testing.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Api endpoints for using the commenting functionality
 *
 * @version 1.0
 * @implNote {@link Comment}
 * @apiNote /api/v1/comments
 */
@Validated
@RestController
@RequestMapping(value = "/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Comment is retrieved for user by user id.")
    })
    @GetMapping(value = "/{userId}")
    public HttpEntity<?> getByUserId(@PathVariable("userId") Integer id) {
        return ResponseEntity.ok(commentService.getForUser(id));
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Comment is created successfully."),
            @ApiResponse(code = 400, message = "Comment object is incorrect of incomplete.")
    })
    @PostMapping
    public HttpEntity<?> create(@Valid @RequestBody Comment comment) {
        Optional<Comment> optional = commentService.create(comment);

        return optional.map(r -> ResponseEntity.ok(r))
                .orElseGet(() -> new ResponseEntity(new ErrorResponse("Object is incorrect."), HttpStatus.BAD_REQUEST));
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Comment is updated successfully."),
            @ApiResponse(code = 400, message = "Comment object is incorrect of incomplete."),
            @ApiResponse(code = 404, message = "Comment with this id is not found.")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public HttpEntity<?> update(@PathVariable("id") Integer id, @Valid @RequestBody Comment comment) {
        Optional<Comment> optional = commentService.get(id);
        if (!optional.isPresent()) {
            return new ResponseEntity(new ErrorResponse("Resource is not found."), HttpStatus.NOT_FOUND);
        }

        optional = commentService.update(comment);

        return optional.map(r -> ResponseEntity.ok(r))
                .orElseGet(() -> new ResponseEntity(new ErrorResponse("Object is incorrect."), HttpStatus.BAD_REQUEST));
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Comment is updated successfully."),
            @ApiResponse(code = 404, message = "Comment with this id is not found.")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public HttpEntity<?> delete(@PathVariable("id") Integer id) {
        Optional<Comment> optional = commentService.get(id);
        if (!optional.isPresent()) {
            return new ResponseEntity(new ErrorResponse("Resource is not found."), HttpStatus.NOT_FOUND);
        }

        commentService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
