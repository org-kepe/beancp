package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationBC extends BeancpInvocation<Byte,Character> {
	char proceed(BeancpContext context,byte fromObj, char toObj);
}