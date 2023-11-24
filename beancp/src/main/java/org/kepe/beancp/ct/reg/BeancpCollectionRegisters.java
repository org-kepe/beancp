package org.kepe.beancp.ct.reg;

import java.lang.reflect.Array;
import java.lang.reflect.Type;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.BeancpConvertProvider;
import org.kepe.beancp.ct.converter.BeancpConverterInfo;
import org.kepe.beancp.ct.invocation.BeancpInvocationImp;
import org.kepe.beancp.ct.invocation.BeancpInvocationOO;
import org.kepe.beancp.ct.itf.BeancpConverter;
import org.kepe.beancp.ct.itf.BeancpCustomConverter;
import org.kepe.beancp.info.BeancpInfo;
import org.kepe.beancp.tool.BeancpInfoMatcherTool;
import org.kepe.beancp.tool.BeancpTool;

public class BeancpCollectionRegisters implements BeancpRegister{
	public static void registers() {
		
		
		registerArray2Array(new BeancpCustomConverter() {
			@Override
			public int distance(BeancpFeature feature, Type fromType, Class fromClass, Type toType, Class toClass) {
				BeancpInfo fromInfo=BeancpInfo.of(fromType, fromClass);
				BeancpInfo toInfo=BeancpInfo.of(toType, toClass);
				return fromInfo.getComponentType().distance(feature, toInfo.getComponentType());
			}
			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj, Object toObj) {
				if(toObj==null) {
					BeancpInfo fromInfo=((BeancpInvocationImp)invocation).getToInfo();
					BeancpInfo toInfo=((BeancpInvocationImp)invocation).getToInfo();
					int len=Array.getLength(fromObj);
					toObj=Array.newInstance(toInfo.getComponentType().getBClass(), len);
					for(int i=0;i<len;i++) {
						switch(fromInfo.stage) {
						case 0:
							switch(toInfo.stage) {
							case 0:
								Array.set(toObj, i, toInfo);
								break;
							}
							break;
						}
					}
					return toObj;
				}
				return ((String)fromObj).toCharArray();
			}
			
		}, PRIORITY8);
		
		//array List Set Map
		
		
	}
	
	private static void register(Type fromType, Type toType, BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(toType)), converter, priority));
    }
	private static void registerArray2Array( BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createArrayMatcher(), BeancpInfoMatcherTool.createArrayMatcher(), converter, priority));
    }
	private static void registerEq(Type fromType, Type toType, BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createEqualMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createEqualMatcher(BeancpInfo.of(toType)), converter, priority));
    }
	private static void registerEq2Extends(Type fromType, Type toType, BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createEqualMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(toType)), converter, priority));
    }
	private static void registerEq2Super(Type fromType, Type toType, BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createEqualMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createSuperMatcher(BeancpInfo.of(toType)), converter, priority));
    }
	private static void register2EqType(Type fromType, Type toType, BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createEqualMatcher(BeancpInfo.of(toType)), converter, priority));
    }
	
}
