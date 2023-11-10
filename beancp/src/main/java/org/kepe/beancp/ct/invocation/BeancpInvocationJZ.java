package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationJZ extends BeancpInvocation<Long,Boolean> {
	boolean proceed(BeancpContext context,long fromObj, boolean toObj);
}