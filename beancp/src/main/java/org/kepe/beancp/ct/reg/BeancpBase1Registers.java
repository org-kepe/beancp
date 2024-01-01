package org.kepe.beancp.ct.reg;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.kepe.beancp.config.BeancpCompare;
import org.kepe.beancp.config.BeancpCompareFlag;
import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.config.BeancpOOConverter;
import org.kepe.beancp.ct.BeancpCompareProvider;
import org.kepe.beancp.ct.BeancpConvertProvider;
import org.kepe.beancp.ct.converter.BeancpConverterCompareInfo;
import org.kepe.beancp.ct.converter.BeancpConverterInfo;
import org.kepe.beancp.ct.invocation.BeancpInvocation;
import org.kepe.beancp.ct.invocation.BeancpInvocationIO;
import org.kepe.beancp.ct.invocation.BeancpInvocationJO;
import org.kepe.beancp.ct.invocation.BeancpInvocationOI;
import org.kepe.beancp.ct.invocation.BeancpInvocationOJ;
import org.kepe.beancp.ct.invocation.BeancpInvocationOO;
import org.kepe.beancp.ct.invocation.BeancpInvocationOZ;
import org.kepe.beancp.ct.invocation.BeancpInvocationZO;
import org.kepe.beancp.ct.itf.BeancpConverter;
import org.kepe.beancp.ct.itf.BeancpCustomConverter;
import org.kepe.beancp.ct.reg.compare.BeancpDefaultCustomCompare;
import org.kepe.beancp.ct.reg.converter.BeancpDirectCustomConverter;
import org.kepe.beancp.info.BeancpCompareInfo;
import org.kepe.beancp.info.BeancpInfo;
import org.kepe.beancp.tool.BeancpInfoMatcherTool;
import org.kepe.beancp.tool.BeancpTool;

public class BeancpBase1Registers  extends BeancpRegister{
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
		
		
		cregister(int.class,int.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(int.class,float.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(int.class,boolean.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(int.class,char.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(int.class,double.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(int.class,short.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(int.class,byte.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(int.class,long.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(float.class,int.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(float.class,float.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(float.class,boolean.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(float.class,char.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(float.class,double.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(float.class,short.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(float.class,byte.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(float.class,long.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(boolean.class,int.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(boolean.class,float.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(boolean.class,boolean.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(boolean.class,char.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(boolean.class,double.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(boolean.class,short.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(boolean.class,byte.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(boolean.class,long.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(char.class,int.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(char.class,float.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(char.class,boolean.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(char.class,char.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(char.class,double.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(char.class,short.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(char.class,byte.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(char.class,long.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(double.class,int.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(double.class,float.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(double.class,boolean.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(double.class,char.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(double.class,double.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(double.class,short.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(double.class,byte.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(double.class,long.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(short.class,int.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(short.class,float.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(short.class,boolean.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(short.class,char.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(short.class,double.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(short.class,short.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(short.class,byte.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(short.class,long.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(byte.class,int.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(byte.class,float.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(byte.class,boolean.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(byte.class,char.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(byte.class,double.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(byte.class,short.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(byte.class,byte.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(byte.class,long.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(long.class,int.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(long.class,float.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(long.class,boolean.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(long.class,char.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(long.class,double.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(long.class,short.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(long.class,byte.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		cregister(long.class,long.class,BeancpDefaultCustomCompare.INSTANCE,PRIORITY8);
		
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
