package org.kepe.beancp.test.cases;

import org.junit.Assert;
import org.junit.Test;
import org.kepe.beancp.BeancpUtil;
import org.kepe.beancp.test.DemoConsumer;
import org.kepe.beancp.test.DemoUser;


public class DemoCase5 {
	
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
		
		Assert.assertEquals(consumer.getId(), user.getId());
		
		BeancpUtil.registerTypeConversion(DemoUser.class, DemoConsumer.class, (invocation,context,fromObj,toObj)->{
			DemoUser user1=(DemoUser)fromObj;
			DemoConsumer consumer1=(DemoConsumer)toObj;
			if(consumer1==null) {
				consumer1=new DemoConsumer();
			}
			consumer1.setFamilyName(user1.getLastName());
			return consumer1;
		}, 1);
		
		consumer=BeancpUtil.copy(user, DemoConsumer.class);
		
		Assert.assertNotEquals(consumer.getId(), user.getId());
		Assert.assertEquals(consumer.getFamilyName(), user.getLastName());
		Assert.assertNull(consumer.getId());
	}
	
	
	
	
}
