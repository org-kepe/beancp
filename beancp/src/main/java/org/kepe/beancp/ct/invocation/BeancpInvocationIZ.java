package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationIZ extends BeancpInvocation<Integer,Boolean> {
	boolean proceed(BeancpContext context,int fromObj, boolean toObj);
}