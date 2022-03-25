package org.master.testing.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * Class used to represent Role component as part of the functionality
 *
 * @implNote name - role name with maximum character limit of 25 characters
 */
@Entity
@Table(name = "roles")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role extends Base {

    @Size(max = 25)
    private String name;
}
