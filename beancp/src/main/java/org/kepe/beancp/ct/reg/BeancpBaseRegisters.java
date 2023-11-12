package org.kepe.beancp.ct.reg;

import java.lang.reflect.Type;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.BeancpConvertProvider;
import org.kepe.beancp.ct.converter.BeancpConverterInfo;
import org.kepe.beancp.ct.invocation.BeancpInvocationOI;
import org.kepe.beancp.ct.invocation.BeancpInvocationOO;
import org.kepe.beancp.ct.itf.BeancpConverter;
import org.kepe.beancp.ct.itf.BeancpCustomConverter;
import org.kepe.beancp.ct.reg.converter.BeancpDirectCustomConverter;
import org.kepe.beancp.info.BeancpInfo;
import org.kepe.beancp.tool.BeancpInfoMatcherTool;

public class BeancpBaseRegisters {
	public static void registers() {
		BeancpCustomConverter<Object,Object> defaultConverter=(invocation, context, fromObj, toObj) -> null;
		BeancpCustomConverter<Object,Object> defaultConverter0=new BeancpCustomConverter<Object, Object>() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 0;
			}

			@Override
			public Object convert(BeancpInvocationOO<Object, Object> invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return null;
			};
			
		};

		register(int.class,int.class,defaultConverter0,0);
		register(int.class,float.class,defaultConverter,0);
		register(int.class,boolean.class,defaultConverter,0);
		register(int.class,char.class,defaultConverter,0);
		register(int.class,double.class,defaultConverter,0);
		register(int.class,short.class,defaultConverter,0);
		register(int.class,byte.class,defaultConverter,0);
		register(int.class,long.class,defaultConverter,0);
		register(float.class,int.class,defaultConverter,0);
		register(float.class,float.class,defaultConverter0,0);
		register(float.class,boolean.class,defaultConverter,0);
		register(float.class,char.class,defaultConverter,0);
		register(float.class,double.class,defaultConverter,0);
		register(float.class,short.class,defaultConverter,0);
		register(float.class,byte.class,defaultConverter,0);
		register(float.class,long.class,defaultConverter,0);
		register(boolean.class,int.class,defaultConverter,0);
		register(boolean.class,float.class,defaultConverter,0);
		register(boolean.class,boolean.class,defaultConverter0,0);
		register(boolean.class,char.class,defaultConverter,0);
		register(boolean.class,double.class,defaultConverter,0);
		register(boolean.class,short.class,defaultConverter,0);
		register(boolean.class,byte.class,defaultConverter,0);
		register(boolean.class,long.class,defaultConverter,0);
		register(char.class,int.class,defaultConverter,0);
		register(char.class,float.class,defaultConverter,0);
		register(char.class,boolean.class,defaultConverter,0);
		register(char.class,char.class,defaultConverter0,0);
		register(char.class,double.class,defaultConverter,0);
		register(char.class,short.class,defaultConverter,0);
		register(char.class,byte.class,defaultConverter,0);
		register(char.class,long.class,defaultConverter,0);
		register(double.class,int.class,defaultConverter,0);
		register(double.class,float.class,defaultConverter,0);
		register(double.class,boolean.class,defaultConverter,0);
		register(double.class,char.class,defaultConverter,0);
		register(double.class,double.class,defaultConverter0,0);
		register(double.class,short.class,defaultConverter,0);
		register(double.class,byte.class,defaultConverter,0);
		register(double.class,long.class,defaultConverter,0);
		register(short.class,int.class,defaultConverter,0);
		register(short.class,float.class,defaultConverter,0);
		register(short.class,boolean.class,defaultConverter,0);
		register(short.class,char.class,defaultConverter,0);
		register(short.class,double.class,defaultConverter,0);
		register(short.class,short.class,defaultConverter0,0);
		register(short.class,byte.class,defaultConverter,0);
		register(short.class,long.class,defaultConverter,0);
		register(byte.class,int.class,defaultConverter,0);
		register(byte.class,float.class,defaultConverter,0);
		register(byte.class,boolean.class,defaultConverter,0);
		register(byte.class,char.class,defaultConverter,0);
		register(byte.class,double.class,defaultConverter,0);
		register(byte.class,short.class,defaultConverter,0);
		register(byte.class,byte.class,defaultConverter0,0);
		register(byte.class,long.class,defaultConverter,0);
		register(long.class,int.class,defaultConverter,0);
		register(long.class,float.class,defaultConverter,0);
		register(long.class,boolean.class,defaultConverter,0);
		register(long.class,char.class,defaultConverter,0);
		register(long.class,double.class,defaultConverter,0);
		register(long.class,short.class,defaultConverter,0);
		register(long.class,byte.class,defaultConverter,0);
		register(long.class,long.class,defaultConverter0,0);
		
		register2Object(Object.class, new BeancpCustomConverter() {
			public int distance(BeancpFeature flag, Class fromClass, Class toClass) {
				if(fromClass==Object.class) {
					return 0;
				}
				return 1000;
			};
			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj, Object toObj) {
				return fromObj;
			}
			
		}, 100);
		BeancpCustomConverter prim2ObjectConverter=new BeancpDirectCustomConverter() {
			public int distance(BeancpFeature flag, Class fromClass, Class toClass) {
				return 1;
			};
		};
		register2Object(int.class, prim2ObjectConverter, 1);
		register2Object(char.class, prim2ObjectConverter, 1);
		register2Object(long.class, prim2ObjectConverter, 1);
		register2Object(float.class, prim2ObjectConverter, 1);
		register2Object(boolean.class, prim2ObjectConverter, 1);
		register2Object(short.class, prim2ObjectConverter, 1);
		register2Object(double.class, prim2ObjectConverter, 1);
		register2Object(byte.class, prim2ObjectConverter, 1);
		
		register(int.class,Integer.class, BeancpDirectCustomConverter.INSTANCE, 1);
		register(char.class,Character.class, BeancpDirectCustomConverter.INSTANCE, 1);
		register(long.class,Long.class, BeancpDirectCustomConverter.INSTANCE, 1);
		register(float.class,Float.class, BeancpDirectCustomConverter.INSTANCE, 1);
		register(boolean.class,Boolean.class, BeancpDirectCustomConverter.INSTANCE, 1);
		register(short.class,Short.class, BeancpDirectCustomConverter.INSTANCE, 1);
		register(double.class,Double.class, BeancpDirectCustomConverter.INSTANCE, 1);
		register(byte.class,Byte.class, BeancpDirectCustomConverter.INSTANCE, 1);
		
		register(String.class,int.class, new BeancpCustomConverter<String, Object>() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 0;
			}

			@Override
			public Object convert(BeancpInvocationOO<String, Object> invocation, BeancpContext context, String fromObj,
					Object toObj) {
				return null;
			};
			public int convert(BeancpInvocationOI<String> invocation, BeancpContext context, String fromObj, int toObj) {
				if(fromObj==null||"".equals(fromObj)) {
					return 0;
				}
				return Integer.parseInt(fromObj);
			};
			
			
		},1);
		//register(boolean.class,Boolean.class,defaultConverter,100);
	}
	
	private static void register(Type fromType, Type toType, BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(toType)), converter, priority));
    }
	private static void register2Object(Type fromType, BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createEqualMatcher(BeancpInfo.OBJECT_INFO), converter, priority));
    }
	private static void register2EqType(Type fromType, Type toType, BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createEqualMatcher(BeancpInfo.of(toType)), converter, priority));
    }
}
