package org.kepe.beancp.ct.reg;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.config.BeancpOOConverter;
import org.kepe.beancp.ct.BeancpConvertProvider;
import org.kepe.beancp.ct.converter.BeancpConverterInfo;
import org.kepe.beancp.ct.invocation.BeancpInvocationIO;
import org.kepe.beancp.ct.invocation.BeancpInvocationJO;
import org.kepe.beancp.ct.invocation.BeancpInvocationOI;
import org.kepe.beancp.ct.invocation.BeancpInvocationOJ;
import org.kepe.beancp.ct.invocation.BeancpInvocationOO;
import org.kepe.beancp.ct.invocation.BeancpInvocationOZ;
import org.kepe.beancp.ct.invocation.BeancpInvocationZO;
import org.kepe.beancp.ct.itf.BeancpConverter;
import org.kepe.beancp.ct.itf.BeancpCustomConverter;
import org.kepe.beancp.ct.reg.converter.BeancpDirectCustomConverter;
import org.kepe.beancp.info.BeancpInfo;
import org.kepe.beancp.tool.BeancpInfoMatcherTool;
import org.kepe.beancp.tool.BeancpTool;

public class BeancpBase1Registers  implements BeancpRegister{
	public static void registers() {
		BeancpOOConverter defaultOOConverter=(invocation, context, fromObj, toObj) -> null;
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

		register(int.class,float.class,BeancpTool.create(1, defaultOOConverter),PRIORITY8);
		register(int.class,boolean.class,BeancpTool.create(1, defaultOOConverter),PRIORITY8);
		register(int.class,char.class,BeancpTool.create(1, defaultOOConverter),PRIORITY8);
		register(int.class,double.class,BeancpTool.create(1, defaultOOConverter),PRIORITY8);
		register(int.class,short.class,BeancpTool.create(1, defaultOOConverter),PRIORITY8);
		register(int.class,byte.class,BeancpTool.create(10, defaultOOConverter),PRIORITY8);
		register(int.class,long.class,BeancpTool.create(1, defaultOOConverter),PRIORITY8);
		register(float.class,int.class,BeancpTool.create(1, defaultOOConverter),PRIORITY8);
		register(float.class,boolean.class,BeancpTool.create(200, defaultOOConverter),PRIORITY8);
		register(float.class,char.class,BeancpTool.create(200, defaultOOConverter),PRIORITY8);
		register(float.class,double.class,BeancpTool.create(1, defaultOOConverter),PRIORITY8);
		register(float.class,short.class,BeancpTool.create(1, defaultOOConverter),PRIORITY8);
		register(float.class,byte.class,BeancpTool.create(200, defaultOOConverter),PRIORITY8);
		register(float.class,long.class,BeancpTool.create(1, defaultOOConverter),PRIORITY8);
		register(boolean.class,int.class,BeancpTool.create(1, defaultOOConverter),PRIORITY8);
		register(boolean.class,float.class,BeancpTool.create(200, defaultOOConverter),PRIORITY8);
		register(boolean.class,char.class,BeancpTool.create(2, defaultOOConverter),PRIORITY8);
		register(boolean.class,double.class,BeancpTool.create(200, defaultOOConverter),PRIORITY8);
		register(boolean.class,short.class,BeancpTool.create(200, defaultOOConverter),PRIORITY8);
		register(boolean.class,byte.class,BeancpTool.create(200, defaultOOConverter),PRIORITY8);
		register(boolean.class,long.class,BeancpTool.create(150, defaultOOConverter),PRIORITY8);
		register(char.class,int.class,BeancpTool.create(1, defaultOOConverter),PRIORITY8);
		register(char.class,float.class,BeancpTool.create(200, defaultOOConverter),PRIORITY8);
		register(char.class,boolean.class,BeancpTool.create(2, defaultOOConverter),PRIORITY8);
		register(char.class,double.class,BeancpTool.create(200, defaultOOConverter),PRIORITY8);
		register(char.class,short.class,BeancpTool.create(200, defaultOOConverter),PRIORITY8);
		register(char.class,byte.class,BeancpTool.create(200, defaultOOConverter),PRIORITY8);
		register(char.class,long.class,BeancpTool.create(150, defaultOOConverter),PRIORITY8);
		register(double.class,int.class,BeancpTool.create(1, defaultOOConverter),PRIORITY8);
		register(double.class,float.class,BeancpTool.create(1, defaultOOConverter),PRIORITY8);
		register(double.class,boolean.class,BeancpTool.create(200, defaultOOConverter),PRIORITY8);
		register(double.class,char.class,BeancpTool.create(200, defaultOOConverter),PRIORITY8);
		register(double.class,short.class,BeancpTool.create(1, defaultOOConverter),PRIORITY8);
		register(double.class,byte.class,BeancpTool.create(200, defaultOOConverter),PRIORITY8);
		register(double.class,long.class,BeancpTool.create(1, defaultOOConverter),PRIORITY8);
		register(short.class,int.class,BeancpTool.create(1, defaultOOConverter),PRIORITY8);
		register(short.class,float.class,BeancpTool.create(1, defaultOOConverter),PRIORITY8);
		register(short.class,boolean.class,BeancpTool.create(150, defaultOOConverter),PRIORITY8);
		register(short.class,char.class,BeancpTool.create(200, defaultOOConverter),PRIORITY8);
		register(short.class,double.class,BeancpTool.create(1, defaultOOConverter),PRIORITY8);
		register(short.class,byte.class,BeancpTool.create(200, defaultOOConverter),PRIORITY8);
		register(short.class,long.class,BeancpTool.create(1, defaultOOConverter),PRIORITY8);
		register(byte.class,int.class,BeancpTool.create(150, defaultOOConverter),PRIORITY8);
		register(byte.class,float.class,BeancpTool.create(200, defaultOOConverter),PRIORITY8);
		register(byte.class,boolean.class,BeancpTool.create(200, defaultOOConverter),PRIORITY8);
		register(byte.class,char.class,BeancpTool.create(200, defaultOOConverter),PRIORITY8);
		register(byte.class,double.class,BeancpTool.create(200, defaultOOConverter),PRIORITY8);
		register(byte.class,short.class,BeancpTool.create(200, defaultOOConverter),PRIORITY8);
		register(byte.class,long.class,BeancpTool.create(200, defaultOOConverter),PRIORITY8);
		register(long.class,int.class,BeancpTool.create(1, defaultOOConverter),PRIORITY8);
		register(long.class,float.class,BeancpTool.create(1, defaultOOConverter),PRIORITY8);
		register(long.class,boolean.class,BeancpTool.create(150, defaultOOConverter),PRIORITY8);
		register(long.class,char.class,BeancpTool.create(200, defaultOOConverter),PRIORITY8);
		register(long.class,double.class,BeancpTool.create(1, defaultOOConverter),PRIORITY8);
		register(long.class,short.class,BeancpTool.create(1, defaultOOConverter),PRIORITY8);
		register(long.class,byte.class,BeancpTool.create(200, defaultOOConverter),PRIORITY8);
		
		
		BeancpCustomConverter prim2ObjectConverter=new BeancpDirectCustomConverter() {
			public int distance(BeancpFeature flag, Class fromClass, Class toClass) {
				return 1;
			};
		};
		registerEq2Super(int.class,Integer.class, prim2ObjectConverter, PRIORITY7);
		registerEq2Super(char.class,Character.class, prim2ObjectConverter, PRIORITY7);
		registerEq2Super(long.class,Long.class, prim2ObjectConverter, PRIORITY7);
		registerEq2Super(float.class,Float.class, prim2ObjectConverter, PRIORITY7);
		registerEq2Super(boolean.class,Boolean.class, prim2ObjectConverter, PRIORITY7);
		registerEq2Super(short.class,Short.class, prim2ObjectConverter, PRIORITY7);
		registerEq2Super(double.class,Double.class, prim2ObjectConverter, PRIORITY7);
		registerEq2Super(byte.class,Byte.class, prim2ObjectConverter, PRIORITY7);
		
		
		
		
		
		
		//register(boolean.class,Boolean.class,defaultConverter,100);
	}
	
	private static void register(Type fromType, Type toType, BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(toType)), converter, priority));
    }
	private static void registerEq(Type fromType, Type toType, BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createEqualMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createEqualMatcher(BeancpInfo.of(toType)), converter, priority));
    }
	private static void registerEq2Super(Type fromType, Type toType, BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createEqualMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createSuperMatcher(BeancpInfo.of(toType)), converter, priority));
    }
	private static void register2EqType(Type fromType, Type toType, BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createEqualMatcher(BeancpInfo.of(toType)), converter, priority));
    }
}
