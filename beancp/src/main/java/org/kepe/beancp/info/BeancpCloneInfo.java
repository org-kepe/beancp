package org.kepe.beancp.info;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.tool.BeancpBeanTool;

public class BeancpCloneInfo {
	protected Method omethod;
	private Method method;
	private BeancpInfo parentInfo;
	private int access;
	private LinkedHashMap<String,BeancpInfo> params=null;//new LinkedHashMap<>();
	
	public BeancpCloneInfo(BeancpInfo parentInfo,Method method) {
		this.parentInfo=parentInfo;
		this.method=method;
		this.access=BeancpBeanTool.getAccess(method);
		
	}
	public int getAccess() {
		return this.access;
	}
	
	public void updateMethod(Method method) {
		this.omethod=this.method;
		this.method=method;
	}
	public Method getMethod() {
		return this.method;
	}
	public boolean needProxy() {
		if(omethod==null) {
			return this.access>0;
		}
		return false;
	}
	public boolean isUseful(BeancpFeature feature,BeancpContext context) {
		if(feature.is(BeancpFeature.ACCESS_PRIVATE)) {
			return this.isUseful(context);
		}else if(feature.is(BeancpFeature.ACCESS_PROTECTED)) {
			if(this.access==2) {
				return false;
			}
			return this.isUseful(context);
		}
		return this.isUseful(context);
	}
	private boolean isUseful(BeancpContext context) {
		return this.isUseful();
	}
	public boolean isUseful() {
		return !needProxy();
	}
	public boolean isProxyMode() {
		return this.omethod!=null;
	}
	
}
