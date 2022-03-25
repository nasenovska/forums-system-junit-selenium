package org.master.testing.service.impl;

import org.master.testing.entity.Base;
import org.master.testing.repository.BaseRepository;
import org.master.testing.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

/**
 * Basic extendable business logic.
 *
 * @param <T> is subclass of {@link Base}
 */
public class BaseServiceImpl<T extends Base> implements BaseService<T> {

    private BaseRepository<T> repository;

    BaseServiceImpl(BaseRepository<T> repository) {
        this.repository = repository;
    }

    @Override
    public Optional<T> get(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<T> get() {
        return repository.findAll();
    }

    @Override
    public List<T> get(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public Optional<Page<T>> get(Pageable pageable) {
        return Optional.of(repository.findAll(pageable));
    }

    @Override
    public Optional<T> create(T t) {
        return Optional.of(repository.save(t));
    }

    @Override
    public Optional<T> update(T t) {
        return Optional.of(repository.save(t));
    }

    @Override
    public void delete(T t) {
        repository.delete(t);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
