package org.kepe.beancp.test.cases;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.kepe.beancp.BeancpUtil;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.test.DemoConsumer;
import org.kepe.beancp.test.DemoUser;


public class DemoCase2 {
	
	@Test
	public void test1() {
		DemoUser user=new DemoUser();
		user.setId("a1");
		user.setAge(1);
		user.setLive(true);
		user.setSalary(100.01);
		DemoConsumer consumer=BeancpUtil.copy(user, DemoConsumer.class,BeancpFeature.SETVALUE_WHENNOTNULL);
		
		Assert.assertEquals(user.getId(), consumer.getId());
		Assert.assertEquals(user.getSalary(), consumer.getSalary().doubleValue(),0.001);
	}
	
	
}
