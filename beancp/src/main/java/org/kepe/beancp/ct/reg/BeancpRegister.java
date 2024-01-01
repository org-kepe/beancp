package org.kepe.beancp.ct.reg;

import java.lang.reflect.Type;

import org.kepe.beancp.config.BeancpCompare;
import org.kepe.beancp.ct.BeancpCompareProvider;
import org.kepe.beancp.ct.BeancpConvertProvider;
import org.kepe.beancp.ct.converter.BeancpConverterCompareInfo;
import org.kepe.beancp.ct.converter.BeancpConverterInfo;
import org.kepe.beancp.ct.itf.BeancpConverter;
import org.kepe.beancp.ct.itf.BeancpConverterCreator;
import org.kepe.beancp.info.BeancpInfo;
import org.kepe.beancp.tool.BeancpInfoMatcherTool;

public class BeancpRegister {
	public static final int PRIORITY10=-1;
	public static final int PRIORITY9=-2;
	public static final int PRIORITY8=-3;
	public static final int PRIORITY7=-4;
	public static final int PRIORITY6=-5;
	public static final int PRIORITY5=-6;
	public static final int PRIORITY4=-7;
	public static final int PRIORITY3=-8;
	public static final int PRIORITY2=-9;
	public static final int PRIORITY1=-10;
	static void cregister(Type fromType, Type toType, BeancpCompare compare,int priority) {
		BeancpCompareProvider.register(BeancpConverterCompareInfo.of(BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(toType)), compare, priority));
    }
	static void cregisterExtends2Any(Type fromType, BeancpCompare compare,int priority) {
		BeancpCompareProvider.register(BeancpConverterCompareInfo.of(BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createAnyMatcher(), compare, priority));
    }
	
	static void cregisterArray2Extends(Type toType, BeancpCompare compare,int priority) {
		BeancpCompareProvider.register(BeancpConverterCompareInfo.of(BeancpInfoMatcherTool.createArrayMatcher(), BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(toType)), compare, priority));
    }
	static void cregisterArray2Array( BeancpCompare compare,int priority) {
		BeancpCompareProvider.register(BeancpConverterCompareInfo.of(BeancpInfoMatcherTool.createArrayMatcher(), BeancpInfoMatcherTool.createArrayMatcher(), compare, priority));
    }
}
