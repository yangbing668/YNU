package com.ynu.ssm.service;

import com.ynu.ssm.pojo.User;
/**
 * Service层接口
 * @author 
 * @Date
 */
public interface UserService {
	//根据id查找
	public User getUserById(Integer userid);
	
	//添加一条数据
	public int insert(User user);
}
