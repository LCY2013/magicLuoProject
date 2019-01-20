package com.lcydream.project.framework.service.impl;

import com.lcydream.project.framework.mybatis.BaseMapper;
import com.lcydream.project.framework.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * IService 实现类（ 泛型：M 是 mapper 对象， T 是实体 ， I 是主键泛型 ）
 * </p>
 * 
 * @author magicLuo
 * @Date 2018-08-20
 */
public class ServiceImpl<M extends BaseMapper<T, I>, T, I> implements IService<T, I> {

	@Autowired
	protected M baseMapper;

	/**
	 * 判断数据库操作是否成功
	 * 
	 * @param result
	 *            数据库操作返回影响条数
	 * @return boolean
	 */
	protected boolean retBool(int result) {
		return (result >= 1) ? true : false;
	}

	@Override
	public boolean insert(T entity) {
		return retBool(baseMapper.insert(entity));
	}
	@Override
	public boolean insertSelective(T entity) {
		return retBool(baseMapper.insertSelective(entity));
	}
	@Override
	public boolean insertBatch(List<T> entityList) {
		return retBool(baseMapper.insertBatch(entityList));
	}
	@Override
	public boolean deleteById(I id) {
		return retBool(baseMapper.deleteById(id));
	}
	@Override
	public boolean deleteByMap(Map<String, Object> columnMap) {
		return retBool(baseMapper.deleteByMap(columnMap));
	}
	@Override
	public boolean deleteSelective(T entity) {
		return retBool(baseMapper.deleteSelective(entity));
	}
	@Override
	public boolean deleteBatchIds(List<I> idList) {
		return retBool(baseMapper.deleteBatchIds(idList));
	}
	@Override
	public boolean updateById(T entity) {
		return retBool(baseMapper.updateById(entity));
	}
	@Override
	public boolean updateSelectiveById(T entity) {
		return retBool(baseMapper.updateSelectiveById(entity));
	}
	@Override
	public boolean updateBatchById(List<T> entityList) {
		return retBool(baseMapper.updateBatchById(entityList));
	}
	@Override
	public T selectById(I id) {
		return baseMapper.selectById(id);
	}
	@Override
	public List<T> selectBatchIds(List<I> idList) {
		return baseMapper.selectBatchIds(idList);
	}
	@Override
	public List<T> selectByMap(Map<String, Object> columnMap) {
		return baseMapper.selectByMap(columnMap);
	}
	@Override
	public T selectOne(T entity) {
		return baseMapper.selectOne(entity);
	}
	@Override
	public int selectCount(T entity) {
		return baseMapper.selectCount(entity);
	}

	@Override
	public int deleteByPrimaryKey(I id) {
		return baseMapper.deleteByPrimaryKey(id);
	}

	@Override
	public T selectByPrimaryKey(I id) {
		return baseMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(T entity) {
		return baseMapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public int updateByPrimaryKey(T entity) {
		return baseMapper.updateByPrimaryKey(entity);
	}
}
