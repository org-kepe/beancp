package org.kepe.beancp.test.cases;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.kepe.beancp.BeancpUtil;
import org.kepe.beancp.test.DemoConsumer;
import org.kepe.beancp.test.DemoUser;


public class DemoCase1 {
	
	@Test
	public void test1() {
		DemoUser du=new DemoUser();
		du.setId("a1");
		du.setAge(1);
		du.setLive(true);
		du.setSalary(100.01);
		DemoConsumer dc=BeancpUtil.copy(du, DemoConsumer.class);
		
		Assert.assertEquals(du.getId(), dc.getId());
		Assert.assertEquals(du.getSalary(), dc.getSalary().doubleValue(),0.001);
	}
	
	@Test
	public void test2() {
		DemoUser du=new DemoUser();
		du.setId("a1");
		du.setAge(1);
		du.setLive(true);
		du.setSalary(100.01);
		DemoConsumer dc=new DemoConsumer();
		dc.setSalary(new BigDecimal("1"));
		BeancpUtil.copy(du, dc);
		
		Assert.assertEquals(du.getId(), dc.getId());
		Assert.assertEquals(du.getSalary(), dc.getSalary().doubleValue(),0.001);
	}
	
	@Test
	public void test3() {
		BigDecimal num = BeancpUtil.copy("123.4", BigDecimal.class);
		Assert.assertEquals(num.doubleValue(), 123.4,0.001);
	}
	
	@Test
	public void test4() {
		DemoUser du=new DemoUser();
		du.setId("a1");
		du.setAge(1);
		du.setLive(true);
		du.setSalary(100.01);
		List<DemoUser> userList=new ArrayList<>();
		userList.add(du);
		List<DemoConsumer> consumerList=BeancpUtil.copy(userList, BeancpUtil.type(List.class,DemoConsumer.class));
		
		Assert.assertEquals(userList.size(), consumerList.size());
		Assert.assertEquals(du.getSalary(), consumerList.get(0).getSalary().doubleValue(),0.001);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test5() {
		DemoUser user=new DemoUser();
		user.setId("a1");
		user.setAge(1);
		user.setLive(true);
		user.setSalary(100.01);
		Map<String,Object> map = BeancpUtil.copy(user, Map.class);
		
		Assert.assertEquals("a1", map.get("id"));
	}
}
