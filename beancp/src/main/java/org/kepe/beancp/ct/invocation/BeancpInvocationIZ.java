package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationIZ extends BeancpInvocation {
	boolean proceed(BeancpContext context,int fromObj, boolean toObj);
	Class<Integer> getFromClass();
	Class<Boolean> getToClass();
}