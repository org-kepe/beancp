package org.kepe.beancp.config;

import org.kepe.beancp.ct.invocation.BeancpInvocationOO;

public interface BeancpOOConverter<T,R> {
	R convert(BeancpInvocationOO<T, R> invocation, BeancpContext context,T fromObj,R toObj);
}
