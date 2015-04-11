package com.trinary.persistence;


import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public interface BaseDAO<T> {
	T get(long id);
	List<T> getAll();
	T findOne(List<Criterion> criterion, List<Order> sorting) throws Exception;
	T findFirst(List<Criterion> criterion, List<Order> sorting) throws Exception;
	List<T> findAll(List<Criterion> criterion, List<OrderPair> sorting, Integer offset,
			Integer maxResults) throws Exception;
	void save(T object);
	void delete(T object);
}