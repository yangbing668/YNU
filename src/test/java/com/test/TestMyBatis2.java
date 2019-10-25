package com.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ynu.ssm.pojo.User;
import com.ynu.ssm.service.UserService;
/**
 * 测试spring整合mybatis 普通方式
 * @author
 * @Date
 */
public class TestMyBatis2 {
	
	private ApplicationContext ac = null;
	
	private UserService userService = null;

	@Before
	public void before() {
		ac = new ClassPathXmlApplicationContext("classpath:spring-mybatis.xml");
		userService = (UserService) ac.getBean("userService");
	}
	/**
	 * 测试查询
	 */
	@Test
	public void test1() {
		User user = userService.getUserById(2);
		System.out.println(user.toString());
	}
	/**
	 * 测试添加
	 */
	@Test
	public void test2() {		
		User user = new User();
		user.setUserName("杜甫");
		user.setPassword("234234");
		user.setAge(23);
		int count = userService.insert(user);
		System.out.println("插入"+count+"条数据成功");
	}
}
