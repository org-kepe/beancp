package org.kepe.beancp.test.cases;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.kepe.beancp.BeancpUtil;
import org.kepe.beancp.test.DemoConsumer;
import org.kepe.beancp.test.DemoUser;


public class DemoCase4 {
	
	@Test
	public void test1() {
		DemoUser user=new DemoUser();
		user.setId("a1");
		user.setAge(1);
		user.setLive(true);
		user.setSalary(100.01);
		user.setFirstName("Elon");
		user.setLastName("Musk");
		DemoUser user2=BeancpUtil.clone(user);
		
		Assert.assertEquals(user.getId(), user2.getId());
		Assert.assertTrue(user2.isLive());
		Assert.assertFalse(user==user2);
	}
	@Test
	public void test2() {
		DemoUser user=new DemoUser();
		user.setUserName("a1");
		user.setAge(1);
		user.setLive(true);
		user.setSalary(100.01);
		user.setFirstName("Elon");
		user.setLastName("Musk");
		String userName=BeancpUtil.getProperty(user,"userName",String.class);
		String userName1=BeancpUtil.getProperty(user,"user_name",String.class);
		String age=BeancpUtil.getProperty(user,"age",String.class);
		
		Assert.assertEquals(userName, user.getUserName());
		Assert.assertEquals(userName1, user.getUserName());
		Assert.assertEquals(age, "1");
		
	}
	@Test
	public void test3() {
		DemoUser user=new DemoUser();
		BeancpUtil.setProperty(user, "id", "a");
		BeancpUtil.setProperty(user, "user_name", "b");
		BeancpUtil.setProperty(user, "live", true);
		
		Assert.assertEquals(user.getId(), "a");
		Assert.assertEquals(user.getUserName(), "b");
		Assert.assertTrue(user.isLive());
	}
	
	
	@Test
	public void test4() {
		Map<String,Object> map=new HashMap<>();
		map.put("userName", "a1");
		map.put("age", 1);
		String userName=BeancpUtil.getProperty(map,"userName",String.class);
		BigDecimal age=BeancpUtil.getProperty(map,"age",BigDecimal.class);
		
		Assert.assertEquals(userName, map.get("userName"));
		Assert.assertEquals(age.intValue(), 1);
		
	}
	@Test
	public void test5() {
		Map<String,Object> map=new HashMap<>();
		map.put("userName", "a1");
		map.put("age", 1);
		BeancpUtil.setProperty(map, "id", "a");
		BeancpUtil.setProperty(map, "userName", "b");
		BeancpUtil.setProperty(map, "age", 2);
		
		Assert.assertEquals(map.get("id"), "a");
		Assert.assertEquals(map.get("userName"), "b");
		Assert.assertEquals((int)map.get("age"),2);
	}
	
	
	
}
