package org.master.testing.repository;

import org.master.testing.entity.Base;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Base repository, providing CRUD functionality, extending from JPA.
 *
 * @param <T> is subclass of {@link Base}
 */
public interface BaseRepository<T extends Base> extends JpaRepository<T, Integer> {
}

