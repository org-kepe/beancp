package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationBS extends BeancpInvocation<Byte,Short> {
	short proceed(BeancpContext context,byte fromObj, short toObj);
}