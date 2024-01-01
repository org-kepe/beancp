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

public class BeancpBaseRegisters {
	public static void registers() {
		BeancpBase1Registers.registers();
		BeancpBase2Registers.registers();
		BeancpBase3Registers.registers();
		BeancpBase4Registers.registers();
		BeancpBase5Registers.registers();
		BeancpBase6Registers.registers();
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
