package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationCC extends BeancpInvocation<Character,Character> {
	char proceed(BeancpContext context,char fromObj, char toObj);
}