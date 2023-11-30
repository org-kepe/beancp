package org.kepe.beancp.config;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpContext {
	
	/**
	 * Disallow the assignment of certain attributes in a class
	 * @param clazz the class
	 * @param key disallow attributes
	 * @return this
	 */
	public BeancpContext disallowKey(Class<?> clazz,String... key);
	
	/**
	 * Only the assignment of certain attributes in the class
	 * @param clazz the class
	 * @param key allow attributes
	 * @return this
	 */
	public BeancpContext allowOnly(Class<?> clazz,String... key);
	
	/**
	 * add a value filter when set value for attribute
	 * @param clazz the class
	 * @param key attribute
	 * @param valueFilter filter
	 * @return
	 */
	public BeancpContext addValueFilter(Class<?> clazz,String key,BeancpValueFilter valueFilter);
	
	/**
	 * Provide filter to filter certain values
	 * @param clazz the class
	 * @param allowFilter filter
	 * @return
	 */
	public BeancpContext addAllowFilter(Class<?> clazz,BeancpAllowFilter allowFilter);
	/**
	 * set exception filter
	 * @param exceptionFilter filter
	 * @return
	 */
	public BeancpContext setExceptionFilter(BeancpExceptionFilter exceptionFilter) ;
	
}
