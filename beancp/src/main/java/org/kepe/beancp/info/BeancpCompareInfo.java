package org.kepe.beancp.info;

import java.lang.reflect.Method;

import org.kepe.beancp.tool.BeancpBeanTool;

public class BeancpCompareInfo {
	private Method method;
	private BeancpInfo parentInfo;
	private BeancpInfo info;
	
	public static boolean isCompareMethod(Method method) {
		if("compareTo".equals(method.getName())&&method.getReturnType()==int.class&&method.getParameterCount()==1&&BeancpBeanTool.getAccess(method)==0) {
			return true;
		}
		return false;
	}
	public BeancpCompareInfo(BeancpInfo parentInfo,Method method) {
		this.parentInfo=parentInfo;
		this.method=method;
	}
	
	public BeancpInfo getInfo() {
		if(this.info!=null) {
			return this.info;
		}
		this.info=BeancpInfo.of(BeancpBeanTool.getFieldType(parentInfo.getFtype(), parentInfo.getFClass(), method, this.method.getGenericParameterTypes()[0]) );
		return this.info;
	}
	public BeancpInfo getParentInfo(){
		return parentInfo;
	}
	
	public Method getMethod() {
		return this.method;
	}
	
}
