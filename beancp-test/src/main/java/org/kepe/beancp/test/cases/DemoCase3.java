package org.kepe.beancp.test.cases;

import org.junit.Assert;
import org.junit.Test;
import org.kepe.beancp.BeancpUtil;
import org.kepe.beancp.test.DemoConsumer;
import org.kepe.beancp.test.DemoUser;


public class DemoCase3 {
	
	@Test
	public void test1() {
		DemoUser user=new DemoUser();
		user.setId("a1");
		user.setAge(1);
		user.setLive(true);
		user.setSalary(100.01);
		user.setFirstName("Elon");
		user.setLastName("Musk");
		DemoConsumer consumer=BeancpUtil.copy(user, DemoConsumer.class);
		
		Assert.assertEquals(user.getId(), consumer.getId());
		Assert.assertNull(consumer.getFirstName());
		Assert.assertEquals(user.getLastName(), consumer.getFamilyName());
	}
	
	
	
	
}
