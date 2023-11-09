package org.kepe.beancp.info;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.convert.BeancpConvertProviderTool;
import org.kepe.beancp.tool.BeancpBeanTool;
import org.kepe.beancp.tool.vo.Tuple2;

public class BeancpInitInfo {
	protected Constructor oconstructor;
	private Executable constructor;
	private BeancpInfo parentInfo;
	private int access;
	private LinkedHashMap<String,BeancpInfo> params=null;//new LinkedHashMap<>();
	
	public BeancpInitInfo(BeancpInfo parentInfo,Constructor constructor) {
		this.parentInfo=parentInfo;
		this.constructor=constructor;
		this.access=Math.max(BeancpBeanTool.getAccess(constructor), BeancpBeanTool.getAccess(constructor.getDeclaringClass()));
		for(Class clazz: constructor.getParameterTypes()) {
			this.access=Math.max(this.access, BeancpBeanTool.getAccess(clazz));
		}
	}
	public int getAccess() {
		return this.access;
	}
	public LinkedHashMap<String,BeancpInfo> getParams() {
		if(this.params==null) {
			LinkedHashMap<String,BeancpInfo> params=new LinkedHashMap<>();
			for(Parameter parameter:constructor.getParameters()) {
				params.put(parameter.getName(), BeancpInfo.of(BeancpBeanTool.getParamType(parentInfo.getFtype(), parentInfo.getFClass(),this.getConstructor().getDeclaringClass(), parameter, parameter.getParameterizedType())));
			}
			this.params=params;
		}
		return params;
	}
	public void updateMethod(Method method) {
		this.getParams();
		this.oconstructor=(Constructor) this.constructor;
		this.constructor=method;
	}
	public Executable getConstructor() {
		return this.constructor;
	}
	public boolean needProxy() {
		if(constructor instanceof Constructor) {
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
		if(context!=null) {
			for(String key:this.getParams().keySet()) {
				if(!BeancpConvertProviderTool.isAllowSet(context, parentInfo.getFClass(), key)) {
					return false;
				}
			}
		}
		return this.isUseful();
	}
	public boolean isUseful() {
		return !needProxy();
	}
	public boolean isProxyMode() {
		return Method.class.isAssignableFrom(constructor.getClass());
	}
	
	public int order() {
		return constructor.getParameterCount();
	}
	
	public Tuple2<Integer,List<BeancpGetInfo>> fitDistance(BeancpFeature flag,BeancpInfo info) {
		if(this.getParams().isEmpty()) {
			return new Tuple2<>(0,null);
		}
		if(info.fields==null||info.fields.isEmpty()) {
			return new Tuple2<>(-1,null);
		}
		int distance=0;
		List<BeancpGetInfo> list=new ArrayList<>();
		boolean isForceEquals=flag.is(BeancpFeature.SETVALUE_TYPEEQUALS);
		for(Map.Entry<String,BeancpInfo> e:this.params.entrySet()) {
			if(info.fields.containsKey(e.getKey())){
				BeancpFieldInfo fieldInfo=info.fields.get(e.getKey());
				BeancpGetInfo getInfo=null;
				int minNum=Integer.MAX_VALUE;
				if(isForceEquals) {
					for(BeancpGetInfo getInfo1:fieldInfo.getGetterList()) {
						int num=getInfo1.getInfo().distance(flag, e.getValue());
						if(num==0) {
							getInfo=getInfo1;
							minNum=num;
							break;
						}
					}
				}else {
					for(BeancpGetInfo getInfo1:fieldInfo.getGetterList()) {
						int num=getInfo1.getInfo().distance(flag, e.getValue());
						if(num==0) {
							getInfo=getInfo1;
							minNum=num;
							break;
						}else if(num>0&&minNum>num) {
							getInfo=getInfo1;
							minNum=num;
						}
					}
				}
				
				if(getInfo==null) {
					return new Tuple2<>(-1,null);
				}
				list.add(getInfo);
				distance+=minNum;
			}else {
				return new Tuple2<>(-1,null);
			}
		}
		return new Tuple2<>(distance,list);
	}
}
