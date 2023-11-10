package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationCO<R> extends BeancpInvocation<Character,R> {
	R proceed(BeancpContext context,char fromObj, R toObj);
}