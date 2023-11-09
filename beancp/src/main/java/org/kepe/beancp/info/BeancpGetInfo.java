package org.kepe.beancp.info;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.tool.BeancpBeanTool;

public class BeancpGetInfo {
	protected Member omember;
	private BeancpName name;
	private Field field;
	private Method method;
	private BeancpInfo parentInfo;
	private BeancpInfo info;
	private int access;
	private boolean isSameType;
	private boolean isMode;
	private boolean isField;
	private String[] possNames;
	
	public BeancpGetInfo(String name,BeancpInfo parentInfo,Field field) {
		this.name=BeancpName.of(name);
		this.parentInfo=parentInfo;
		this.field=field;
		this.access=Math.max(BeancpBeanTool.getAccess(field), BeancpBeanTool.getAccess(field.getDeclaringClass()));
		this.access=Math.max(this.access, BeancpBeanTool.getAccess(field.getType()));
		this.isField=true;
		this.possNames=new String[] {this.name.name};
		init();
	}
	public BeancpGetInfo(String name,BeancpInfo parentInfo,Method method) {
		this.name=BeancpName.of(name);
		this.parentInfo=parentInfo;
		this.method=method;
		this.access=Math.max(BeancpBeanTool.getAccess(method), BeancpBeanTool.getAccess(method.getDeclaringClass()));
		this.access=Math.max(this.access, BeancpBeanTool.getAccess(method.getReturnType()));
		this.isMode=method.getName().startsWith("is");
		this.possNames=new String[] {this.name.name};
		init();
	}
	public void setPossName(String name) {
		if(!this.name.name.equals(name)) {
			this.possNames=new String[] {this.name.name,name};
		}
	}
	public String[] getPossNames() {
		return possNames;
	}
	public String getFieldName() {
		if(this.isField) {
			if(this.field!=null) {
				return this.field.getName();
			}else {
				return this.omember.getName();
			}
		}
		return null;
	}
	
	public int getAccess() {
		return this.access;
	}
	public boolean isSameType() {
		return isSameType;
	}
	public BeancpName getName() {
		return name;
	}
	public boolean needProxy() {
		return this.access>0&&this.omember==null;
	}
	public boolean isUseful(BeancpFeature feature) {
		if(feature.is(BeancpFeature.ACCESS_PRIVATE)) {
			return this.isUseful();
		}else if(feature.is(BeancpFeature.ACCESS_PROTECTED)) {
			if(this.access==2) {
				return false;
			}
			return this.isUseful();
		}
		return this.access==0;
	}
	public boolean isUseful() {
		return this.omember!=null||this.access==0;
	}
	private void init() {
	}
	public void updateMethod(Method method) {
		this.omember=this.method!=null?this.method:this.field;
		this.method=method;
		this.field=null;
		this.init();
	}
	public BeancpInfo getParentInfo(){
		return parentInfo;
	}
	public BeancpInfo getInfo() {
		if(this.info!=null) {
			return this.info;
		}
		if(this.field!=null) {
			this.info=BeancpInfo.of(BeancpBeanTool.getFieldType(parentInfo.getFtype(), parentInfo.getFClass(), field, this.field.getGenericType()) );
			this.isSameType=this.field.getGenericType()==this.info.getBType();
		}else {
			this.info=BeancpInfo.of(BeancpBeanTool.getFieldType(parentInfo.getFtype(), parentInfo.getFClass(), method, this.method.getGenericReturnType()) );
			this.isSameType=this.method.getGenericReturnType()==this.info.getBType();
		}
		return this.info;
	}
	public Field getField() {
		return this.field;
	}
	public Method getMethod() {
		return this.method;
	}
	public boolean isMode() {
		return isMode;
	}
	public boolean isField() {
		return isField;
	}
}
