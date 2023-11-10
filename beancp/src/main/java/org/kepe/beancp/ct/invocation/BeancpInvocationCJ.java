package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationCJ extends BeancpInvocation<Character,Long> {
	long proceed(BeancpContext context,char fromObj, long toObj);
}