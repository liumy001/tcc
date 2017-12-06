package com.eric.demo.commons.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/*import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
*/
public interface BaseService<T extends Serializable, E> {
	T create(T entity);

	List<T> create(List<T> enetities);

	T update(T entity);

	List<T> update(List<T> entities);

	T save(T entity);

	List<T> save(List<T> entities);

	T findOne(String id);

	boolean exists(String id);

	long count();

	void delete(String id);

	void delete(T entity);

	void delete(List<T> entities);

	void deleteAll();

	List<T> findAll();
	
	List<T> search(E criteria);

	int logicDeleteById(String id);

	List<T> findList(Map<String, Object> paramMap);

	List<T> findByVo(T entity);
}
