package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationCD extends BeancpInvocation<Character,Double> {
	double proceed(BeancpContext context,char fromObj, double toObj);
}