package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationJJ extends BeancpInvocation<Long,Long> {
	long proceed(BeancpContext context,long fromObj, long toObj);
}