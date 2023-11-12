package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationIJ extends BeancpInvocation {
	long proceed(BeancpContext context,int fromObj, long toObj);
	Class<Integer> getFromClass();
	Class<Long> getToClass();
}