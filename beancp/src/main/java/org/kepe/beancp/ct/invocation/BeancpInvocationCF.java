package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationCF extends BeancpInvocation<Character,Float> {
	float proceed(BeancpContext context,char fromObj, float toObj);
}