package org.master.testing.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Class used to represent Comment component as part of the functionality
 *
 * @implNote comment with maximum content of 100 characters, associated with {@link User} and {@link Topic}
 */
@Entity
@Table(name = "comments")
@EqualsAndHashCode(callSuper = true)
@Data
public class Comment extends Base {
    @Size(max = 100)
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private User user;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Topic topic;
}
