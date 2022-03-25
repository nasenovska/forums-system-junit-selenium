package org.master.testing.service;

import org.master.testing.entity.Base;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

/**
 * Service providing the common business logic ready to be implemented.
 *
 * @param <T> is subclass of {@link Base}
 */
public interface BaseService<T> {
    Optional<T> get(Integer id);

    List<T> get();

    List<T> get(Sort sort);

    Optional<Page<T>> get(Pageable pageable);

    Optional<T> create(T t);

    Optional<T> update(T t);

    void delete(T t);

    void delete(Integer id);
}
