package com.trinary.persistence;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract class GenericDAO<T> implements BaseDAO<T>, Paginate<T> {
	protected Class<T> type;
	
	protected abstract SessionFactory getSessionFactory();

	/* (non-Javadoc)
	 * @see com.trinary.test.persistence.dao.Paginate#getPaginated(Integer, Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getPaginated(Integer page, Integer pageSize) {
		Integer offset = null;
		if (page == null || pageSize == null) {
			offset = (page - 1) * pageSize;
		}
		
		return getSessionFactory().getCurrentSession()
				.createCriteria(type)
				.setFirstResult(offset)
				.setMaxResults(pageSize)
				.list();
	}
	
	/* (non-Javadoc)
	 * @see com.trinary.test.persistence.dao.Paginate#getPaginated(Integer, Integer)
	 */
	@Override
	public List<T> getOrdered(List<OrderPair> orderBy) {
		return getPaginatedAndOrdered(0, 0, orderBy);
	}
	
	/* (non-Javadoc)
	 * @see com.trinary.test.persistence.dao.Paginate#getPaginated(Integer, Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getPaginatedAndOrdered(Integer page, Integer pageSize, List<OrderPair> orderBy) {
		Integer offset = null;
		if (page != null && pageSize != null) {
			offset = (page - 1) * pageSize;
		}
		
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(type);
		
		if (orderBy != null) {
			for (OrderPair orderSpec : orderBy) {
				if (orderSpec.getDirection() == OrderDirection.ASCENDING) {
					criteria.addOrder(Order.asc(orderSpec.getField()));
				} else {
					criteria.addOrder(Order.desc(orderSpec.getField()));
				}
			}
		}
		
		if (offset != null && pageSize != null) {
			criteria.setFirstResult(offset);
			criteria.setMaxResults(pageSize);
		}
		
		return criteria.list();
	}

	/* (non-Javadoc)
	 * @see com.trinary.test.persistence.dao.BaseDAO#get(long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T get(long id) {
		return (T)getSessionFactory().getCurrentSession().get(type, id);
	}

	/* (non-Javadoc)
	 * @see com.trinary.test.persistence.dao.BaseDAO#getAll()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() {
		return getSessionFactory().getCurrentSession()
				.createCriteria(type)
				.list();
	}
	
	/* (non-Javadoc)
	 * @see com.trinary.test.persistence.dao.BaseDAO#save(java.lang.Object)
	 */
	@Override
	public void save(T object) {
		getSessionFactory().getCurrentSession().save(object);
	}
	
	/* (non-Javadoc)
	 * @see com.trinary.test.persistence.dao.BaseDAO#save(java.lang.Object)
	 */
	@Override
	public void delete(T object) {
		getSessionFactory().getCurrentSession().delete(object);
	}

	/* (non-Javadoc)
	 * @see com.trinary.persistence.BaseDAO#findOne(java.util.List, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T findOne(List<Criterion> criterion, List<Order> sorting) throws Exception {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(type);
		for (Criterion c : criterion) {
			criteria.add(c);
		}
		criteria.setFirstResult(0);
		criteria.setMaxResults(1);
		
		if (sorting != null) {
			for (Order sort : sorting) {
				criteria.addOrder(sort);
			}
		}
		
		List<T> results = criteria.list();
		
		if (results == null || results.isEmpty()) {
			throw new Exception("No results returned");
		} else if (results.size() > 1) {
			throw new Exception("More than one result returned.");
		}
		
		return results.get(0);
	}

	/* (non-Javadoc)
	 * @see com.trinary.persistence.BaseDAO#findFirst(java.util.List, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T findFirst(List<Criterion> criterion, List<Order> sorting) throws Exception {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(type);
		for (Criterion c : criterion) {
			criteria.add(c);
		}
		criteria.setFirstResult(0);
		criteria.setMaxResults(1);
		
		if (sorting != null) {
			for (Order sort : sorting) {
				criteria.addOrder(sort);
			}
		}
		
		List<T> results = criteria.list();
		
		if (results == null || results.isEmpty()) {
			throw new Exception("No results returned");
		}
		
		return results.get(0);
	}

	/* (non-Javadoc)
	 * @see com.trinary.test.persistence.dao.BaseDAO#findAll(java.util.List, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(List<Criterion> criterion, List<OrderPair> sorting, Integer offset,
			Integer maxResults) throws Exception {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(type);
		for (Criterion c : criterion) {
			criteria.add(c);
		}
		if (offset != null) {
			criteria.setFirstResult(offset);
		}
		if (maxResults != null) {
			criteria.setMaxResults(maxResults);
		}
		
		if (sorting != null) {
			for (OrderPair sort : sorting) {
				if (sort.direction == OrderDirection.ASCENDING) {
					criteria.addOrder(Order.asc(sort.getField()));
				} else {
					criteria.addOrder(Order.desc(sort.getField()));
				}
			}
		}
		
		List<T> results = criteria.list();
		
		if (results == null || results.isEmpty()) {
			throw new Exception("No results returned");
		}
		
		return results;
	}
}