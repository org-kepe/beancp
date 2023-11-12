package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationCF extends BeancpInvocation {
	float proceed(BeancpContext context,char fromObj, float toObj);
	Class<Character> getFromClass();
	Class<Float> getToClass();
}