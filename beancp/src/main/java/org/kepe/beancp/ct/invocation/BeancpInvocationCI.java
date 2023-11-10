package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationCI extends BeancpInvocation<Character,Integer> {
	int proceed(BeancpContext context,char fromObj, int toObj);
}