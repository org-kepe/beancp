package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationIJ extends BeancpInvocation<Integer,Long> {
	long proceed(BeancpContext context,int fromObj, long toObj);
}