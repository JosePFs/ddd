package com.example.transactions.domain.model.common.transformer;

public abstract class Transformer<T, D> {

  public abstract D transform(T transactions);
}
