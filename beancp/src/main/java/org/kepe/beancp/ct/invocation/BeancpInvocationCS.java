package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationCS extends BeancpInvocation<Character,Short> {
	short proceed(BeancpContext context,char fromObj, short toObj);
}