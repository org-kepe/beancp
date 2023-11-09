package org.kepe.beancp.config;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpContext {
	
	public BeancpContext disallowKey(Class<?> clazz,String... key);
	public BeancpContext allowOnly(Class<?> clazz,String... key);
	public BeancpContext addValueFilter(Class<?> clazz,String key,BeancpValueFilter valueFilter);
	//public BeancpContext addValueFilter(Class<?> clazz, BeancpValueFilter valueFilter);

	public BeancpContext addAllowFilter(Class<?> clazz,BeancpAllowFilter allowFilter);
	public BeancpContext setExceptionFilter(BeancpExceptionFilter exceptionFilter) ;
	
}
