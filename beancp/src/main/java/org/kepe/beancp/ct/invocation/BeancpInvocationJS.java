package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationJS extends BeancpInvocation<Long,Short> {
	short proceed(BeancpContext context,long fromObj, short toObj);
}