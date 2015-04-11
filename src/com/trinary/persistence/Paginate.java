package com.trinary.persistence;

import java.util.List;

public interface Paginate<T> {
	public abstract List<T> getOrdered(List<OrderPair> orderBy);
	public abstract List<T> getPaginated(Integer page, Integer pageSize);
	List<T> getPaginatedAndOrdered(Integer page, Integer pageSize, List<OrderPair> orderBy);
}