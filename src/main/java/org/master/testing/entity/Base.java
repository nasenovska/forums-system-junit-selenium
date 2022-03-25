package org.master.testing.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Base class containing the common fields for all the entities
 *
 * @implNote id - the common field
 */
@MappedSuperclass
@Data
public class Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}
