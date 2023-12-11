package org.kepe.beancp.ct.reg;

import java.lang.reflect.Type;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.BeancpConvertProvider;
import org.kepe.beancp.ct.convert.BeancpConvertMapper;
import org.kepe.beancp.ct.converter.BeancpConverterInfo;
import org.kepe.beancp.ct.invocation.BeancpInvocationImp;
import org.kepe.beancp.ct.invocation.BeancpInvocationOI;
import org.kepe.beancp.ct.invocation.BeancpInvocationOO;
import org.kepe.beancp.ct.itf.BeancpConverter;
import org.kepe.beancp.ct.itf.BeancpCustomConverter;
import org.kepe.beancp.ct.reg.converter.BeancpDirectCustomConverter;
import org.kepe.beancp.info.BeancpInfo;
import org.kepe.beancp.tool.BeancpInfoMatcherTool;

public class BeancpDirectRegisters implements BeancpRegister {
	public static void registers() {
		registerSame(BeancpDirectCustomConverter.INSTANCE, PRIORITY9);
		register(int.class,Integer.class, BeancpDirectCustomConverter.INSTANCE, PRIORITY8);
		register(char.class,Character.class, BeancpDirectCustomConverter.INSTANCE, PRIORITY8);
		register(long.class,Long.class, BeancpDirectCustomConverter.INSTANCE, PRIORITY8);
		register(float.class,Float.class, BeancpDirectCustomConverter.INSTANCE, PRIORITY8);
		register(boolean.class,Boolean.class, BeancpDirectCustomConverter.INSTANCE, PRIORITY8);
		register(short.class,Short.class, BeancpDirectCustomConverter.INSTANCE, PRIORITY8);
		register(double.class,Double.class, BeancpDirectCustomConverter.INSTANCE, PRIORITY8);
		register(byte.class,Byte.class, BeancpDirectCustomConverter.INSTANCE, PRIORITY8);
		
		register(Integer.class,int.class, BeancpDirectCustomConverter.INSTANCE, PRIORITY8);
		register(Character.class,char.class, BeancpDirectCustomConverter.INSTANCE, PRIORITY8);
		register(Long.class,long.class, BeancpDirectCustomConverter.INSTANCE, PRIORITY8);
		register(Float.class,float.class, BeancpDirectCustomConverter.INSTANCE, PRIORITY8);
		register(Boolean.class,boolean.class, BeancpDirectCustomConverter.INSTANCE, PRIORITY8);
		register(Short.class,short.class, BeancpDirectCustomConverter.INSTANCE, PRIORITY8);
		register(Double.class,double.class, BeancpDirectCustomConverter.INSTANCE, PRIORITY8);
		register(Byte.class,byte.class, BeancpDirectCustomConverter.INSTANCE, PRIORITY8);
		
		registerSuper(new BeancpDirectCustomConverter() {
			@Override
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}
			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj, Object toObj) {
				if(fromObj==null) {
					return toObj;
				}
				if(toObj==null) {
					if(invocation.getFeature().is(BeancpFeature.ALLWAYS_NEW)) {
						return BeancpConvertMapper.of(((BeancpInvocationImp)invocation).getFromInfo(), invocation.getFeature()).clone(context,fromObj);
					}else {
						return fromObj;
					}
				}else {
					return invocation.proceed(context, fromObj, toObj);
				}
			}
		},PRIORITY10);
		registerChild(new BeancpDirectCustomConverter() {
			@Override
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 101;
			}
			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj, Object toObj) {
				return invocation.proceed(context, fromObj, toObj);
			}
		},PRIORITY10);
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
	private static void registerSame( BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createAnyMatcher(), BeancpInfoMatcherTool.createAnyMatcher(),(fromInfo,toInfo)->fromInfo.getId()==toInfo.getId(), converter, priority));
    }
	private static void registerSuper( BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createAnyMatcher(), BeancpInfoMatcherTool.createAnyMatcher(),(fromInfo,toInfo)->fromInfo.getId()!=toInfo.getId()&&fromInfo.instanceOf(toInfo), converter, priority));
    }
	private static void registerChild( BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createAnyMatcher(), BeancpInfoMatcherTool.createAnyMatcher(),(fromInfo,toInfo)->fromInfo.getId()!=toInfo.getId()&&toInfo.instanceOf(fromInfo), converter, priority));
    }
}
