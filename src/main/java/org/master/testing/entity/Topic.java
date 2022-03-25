package org.master.testing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Class used to represent Topic component as part of the functionality
 *
 * @implNote title is with limit of 50 characters, description is with limit of 100 characters, with possibility to add subtopics by {@link Topic} (self)
 */
@Entity
@Table(name = "topics")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Topic extends Base {

    @Size(max = 50)
    private String title;

    @Size(max = 100)
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Topic> subtopics;
}
