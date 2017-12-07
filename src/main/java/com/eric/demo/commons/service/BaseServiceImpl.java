package com.eric.demo.commons.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;*/
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.eric.demo.commons.dao.BaseDao;
import com.eric.demo.commons.domain.AbstractCriteria;
import com.eric.demo.commons.domain.AbstractEntity;
import com.eric.demo.commons.util.IdGen;

@Transactional
public abstract class BaseServiceImpl<T extends AbstractEntity, E extends AbstractCriteria>
        implements BaseService<T, E> {

    protected abstract BaseDao<T, E, String> getDao();

    protected Class<T> entityClazz;

    protected Class<E> criteriaClazz;

    @SuppressWarnings("unchecked")
    public BaseServiceImpl() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        entityClazz = (Class<T>) params[0];
        criteriaClazz = (Class<E>) params[1];
    }

    @Override
    public T create(T entity) {
        Assert.notNull(entity);
        String id = entity.getId() == null ? IdGen.uuid() : entity.getId()
                .trim();
        entity.setId(id);
        getDao().insertSelective(entity);
        return entity;
    }

    @Override
    public List<T> create(List<T> entities) {
        Assert.notEmpty(entities);
        List<T> list = new ArrayList<T>();
        for (T t : entities) {
            list.add(create(t));
        }
        return list;
    }

    @Override
    public T update(T entity) {
        Assert.notNull(entity);
        T existing = getDao().selectByPrimaryKey(entity.getId());
        BeanUtils.copyProperties(entity, existing);
        getDao().updateByPrimaryKeySelective(existing);
        return findOne(entity.getId());
    }

    @Override
    public List<T> update(List<T> entities) {
        Assert.notEmpty(entities);
        List<T> list = new ArrayList<T>();
        for (T t : entities) {
            list.add(update(t));
        }
        return list;
    }

    @Override
    public T save(T entity) {
        Assert.notNull(entity);
        if (entity.getId() == null) {
            create(entity);
        } else {
            update(entity);
        }
        return findOne(entity.getId());
    }

    @Override
    public List<T> save(List<T> entities) {
        Assert.notEmpty(entities);
        List<T> list = new ArrayList<T>();
        for (T t : entities) {
            list.add(save(t));
        }
        return list;
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public T findOne(String id) {
        Assert.notNull(id);
        return getDao().selectByPrimaryKey(id);
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public boolean exists(String id) {
        Assert.notNull(id);
        return findOne(id) != null;
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public long count() {
        return getDao().countByExample(null);
    }

    @Override
    public void delete(String id) {
        Assert.notNull(id);
        getDao().deleteByPrimaryKey(id);
    }

    @Override
    public void delete(T entity) {
        Assert.notNull(entity);
        getDao().deleteByPrimaryKey(entity.getId());
    }

    @Override
    public void delete(List<T> entities) {
        Assert.notEmpty(entities);
        for (T t : entities) {
            delete(t);
        }
    }

    @Override
    public void deleteAll() {
        delete(findAll());
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public List<T> findAll() {
        return getDao().selectByExample(null);
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public List<T> search(E criteria) {
        Assert.notNull(criteria);
        return getDao().selectByExample(criteria);
    }

    @Override
    public int logicDeleteById(String id) {
        Assert.notNull(id);
        return getDao().logicDeleteById(id);
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public List<T> findList(Map<String, Object> paramMap) {
        return getDao().findList(paramMap);
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public List<T> findByVo(T entity) {
        return getDao().findByVo(entity);
    }

}
