package com.ccc.common.base;

import com.jfinal.plugin.activerecord.Model;

public interface BaseService {

	public <T extends Model<T>> T getTByTId(T t, Integer id);

	public <T extends Model<T>> boolean delete(T t);

	public <T extends Model<T>> boolean save(T t);

	public <T extends Model<T>> boolean update(T t);

}
