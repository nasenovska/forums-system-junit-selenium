package org.master.testing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * Class used to represent User component as part of the functionality
 *
 * @implNote valid email with limit of 50 characters, 6-symbolic password, name with limit of 25 characters and assigned {@link Role}
 */
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User extends Base {

    @Column(unique = true)
    @Size(max = 50)
    private String email;

    @Size(min = 6)
    private String password;

    @Size(max = 25)
    private String name;

    @ManyToOne
    private Role role;
}
