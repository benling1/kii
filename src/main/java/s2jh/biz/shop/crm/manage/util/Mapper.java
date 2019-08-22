package s2jh.biz.shop.crm.manage.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import s2jh.biz.shop.utils.MyBeanUtils;

/**
 * @ClassName: Mapper <br/>
 * @Description: Mapper Utils <br/>
 * @CreateDate: 2017年5月23日 下午4:55:26 <br/>
 * @author zlp
 * @version V1.0
 */
public class Mapper<V, E> {

	private Class<V> valueClass;
	private Class<E> entityClass;
	
	/**
	 * @deprecated Use Mapper(Class<V> theValue, Class<E> theEntity) instead
	 */
	protected Mapper() {}
	
	protected Mapper(Class<V> theValue, Class<E> theEntity) {
		this.valueClass = theValue;
		this.entityClass = theEntity;
	}
	
	/**
	 * Get an instance of the mapper class.
	 * 
	 * @param valueClass
	 *            Value class. Must have a no argument constructor.
	 * @param entityClass
	 *            Entity class. Must have a no argument constructor.
	 * @return
	 */
	public static <V, E> Mapper<V, E> getInstance(Class<V> valueClass, Class<E> entityClass) {
		Mapper<V, E> m = new Mapper<V, E>(valueClass, entityClass);
		return m;
	}
	
	/**
	 * Map property values from the entity to the value bean for all cases where
	 * the property names are the same.
	 * 
	 * @param entity 
	 *            Entity which the properties are to be copied to value bean.
	 * @return
	 */
	@SuppressWarnings("null")
	public V mapToValue(E entity) {
		V value = null;

		try {
			//value = valueClass.newInstance();
			value = getValueClass().newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException("Error creating new instance of : " + value.getClass().getName(), e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Error creating new instance of : " + value.getClass().getName(), e);
		}

		try {
			MyBeanUtils.copyProperties(value, entity);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Error copying properties of : " + entity.getClass().getName(), e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Error copying properties of : " + entity.getClass().getName(), e);
		}

		return value;
	}

	/**
	 * Map property values from the value bean to the entity for all cases where
	 * the property names are the same.
	 * 
	 * @param value 
	 *            Value bean which the properties are to be copied to entity.
	 * @return
	 */
	@SuppressWarnings("null")
	public E mapToEntity(V value) {
		E entity = null;

		try {
			//entity = entityClass.newInstance();
			entity = getEntityClass().newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException("Error creating new instance of : " + entity.getClass().getName(), e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Error creating new instance of : " + entity.getClass().getName(), e);
		}

		try {
			MyBeanUtils.copyProperties(entity, value);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Error copying properties of : " + value.getClass().getName(), e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Error copying properties of : " + value.getClass().getName(), e);
		}

		return entity;
	}

	/**
	 * Map property values from the list of entities to the list of value beans for all cases where the property names are the
	 * same.
	 * 
	 * @param entityList
	 * @return
	 */
	public List<V> mapToValues(List<E> entityList) {
		if (entityList != null) {
			List<V> valueList = new ArrayList<V>();
			for (E entity : entityList) {
				valueList.add(mapToValue(entity));
			}
			return valueList;
		} else {
			return null;
		}
	}
	
	/**
	 * Map property values from the set of entities to the list of value beans
	 * for all cases where the property names are the same.
	 * 
	 * @param entitySet
	 * @return
	 */
	public List<V> mapToValues(Set<E> entitySet) {
		if (entitySet != null) {
			List<V> valueList = new ArrayList<V>();
			for (E entity : entitySet) {
				valueList.add(mapToValue(entity));
			}
			return valueList;
		} else {
			return null;
		}
	}

	/**
	 * Map property values from the list of value beans to the list of entities
	 * for all cases where the property names are the same.
	 * 
	 * @param valueList
	 * @return
	 */
	public List<E> mapToEntities(List<V> valueList) {
		if (valueList != null) {
			List<E> entityList = new ArrayList<E>();
			for (V value : valueList) {
				entityList.add(mapToEntity(value));
			}
			return entityList;
		} else {
			return null;
		}
	}
	
	/**
	 * @return
	 */
	protected Class<E> getEntityClass() {
		return entityClass;
	};

	/**
	 * @return
	 */
	protected Class<V> getValueClass() {
		return valueClass;
	};
}
