package org.kepe.beancp.test.cases;

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
	}
	@Test
	public void test2() {
		DemoUser user=new DemoUser();
		BeancpUtil.setProperty(user, "id", "a");
		BeancpUtil.setProperty(user, "live", true);
		
		Assert.assertEquals(user.getId(), "a");
		Assert.assertTrue(user.isLive());
	}
	@Test
	public void test3() {
		DemoUser user=new DemoUser();
		user.setId("a1");
		user.setAge(1);
		user.setLive(true);
		user.setSalary(100.01);
		user.setFirstName("Elon");
		user.setLastName("Musk");
		String id=BeancpUtil.getProperty(user,"id",String.class);
		String age=BeancpUtil.getProperty(user,"age",String.class);
		
		Assert.assertEquals(id, user.getId());
		Assert.assertEquals(age, "1");
	}
	
	
	
}
