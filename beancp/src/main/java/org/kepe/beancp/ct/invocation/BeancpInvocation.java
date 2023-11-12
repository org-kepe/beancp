package org.kepe.beancp.ct.invocation;

import java.lang.reflect.Type;

import org.kepe.beancp.config.BeancpFeature;

public interface BeancpInvocation {
	
	BeancpFeature getFeature();
	
	Class<?> getFromClass();
	Type getFromType();
	
	Class<?> getToClass();
	Type getToType();
}
