package com.example.transactions.domain.model.common.repository;

public interface BaseRepository<T> {

  T create(T entity);

}
