package com.ynu.ssm.dao;

import com.ynu.ssm.pojo.User;
/**
 * User类Dao层接口
 * 
 * 之前我们会在dao层自己手动实现dao层然后自动注入SqlSessionTemplate 实例
 * 来调用具体的方法 比如 insert("","")  selectOne("","") 等方法 
 * 其中第一个参数就是映射文件的地址： namespace+id  而第二个参数就是传递的条件这样mybatis 
 * 就会按照我们传递的这两个参数找到具体的映射文件进行解析查询。
 * 而这里使用动态代理就省去了我们实现dao接口的这一步骤，而是由spring提我们实现了
 * @author
 * @Date
 */
public interface UserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}