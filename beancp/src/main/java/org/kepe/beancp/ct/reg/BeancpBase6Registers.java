package org.kepe.beancp.ct.reg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.config.BeancpOOConverter;
import org.kepe.beancp.ct.BeancpConvertProvider;
import org.kepe.beancp.ct.convert.BeancpConvertMapper;
import org.kepe.beancp.ct.converter.BeancpConverterInfo;
import org.kepe.beancp.ct.invocation.BeancpInvocationIO;
import org.kepe.beancp.ct.invocation.BeancpInvocationImp;
import org.kepe.beancp.ct.invocation.BeancpInvocationJO;
import org.kepe.beancp.ct.invocation.BeancpInvocationOI;
import org.kepe.beancp.ct.invocation.BeancpInvocationOJ;
import org.kepe.beancp.ct.invocation.BeancpInvocationOO;
import org.kepe.beancp.ct.invocation.BeancpInvocationOZ;
import org.kepe.beancp.ct.invocation.BeancpInvocationZO;
import org.kepe.beancp.ct.itf.BeancpConverter;
import org.kepe.beancp.ct.itf.BeancpCustomConverter;
import org.kepe.beancp.ct.reg.converter.BeancpDirectCustomConverter;
import org.kepe.beancp.exception.BeancpException;
import org.kepe.beancp.info.BeancpInfo;
import org.kepe.beancp.tool.BeancpInfoMatcherTool;
import org.kepe.beancp.tool.BeancpTool;
import org.kepe.beancp.tool.TimeTool;

public class BeancpBase6Registers  implements BeancpRegister{
	public static void registers() {
		
		BeancpCustomConverter converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 6;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return ((Enum)fromObj).ordinal();
			};
			@Override
			public int convert(BeancpInvocationOI invocation, BeancpContext context, Object fromObj, int toObj) {
				return ((Enum)fromObj).ordinal();
			}
			
		};
		registerEnum2Eq(Integer.class, converter1, PRIORITY8);
		registerEnum2Eq(int.class, converter1, PRIORITY8);
		
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 6;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				Enum[] enums=((BeancpInvocationImp)invocation).getToInfo().getEnumConstants();
				return enums[(Integer)fromObj];
			};
			@Override
			public Object convert(BeancpInvocationIO invocation, BeancpContext context, int fromObj, Object toObj) {
				Enum[] enums=((BeancpInvocationImp)invocation).getToInfo().getEnumConstants();
				return enums[fromObj];
			}
			
		};
		registerEq2Enum(Integer.class, converter1, PRIORITY8);
		registerEq2Enum(int.class, converter1, PRIORITY8);
		
		
		registerEnum2Eq(String.class, BeancpTool.create(5, (invocation,context,fromObj,toObj)->{
			if(fromObj==null) {
				return toObj;
			}
			return ((Enum)fromObj).toString();
		}), PRIORITY8);
		registerEq2Enum(String.class, BeancpTool.create(5, (invocation,context,fromObj,toObj)->{
			Enum[] enums=((BeancpInvocationImp)invocation).getToInfo().getEnumConstants();
			for(Enum e:enums) {
				if(Objects.equals(e.toString(), fromObj)) {
					return e;
				}
			}
			return null;
		}), PRIORITY8);
		
		
	}
	private static void registerEnum2Eq(Type toType, BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createEnumMatcher(), BeancpInfoMatcherTool.createEqualMatcher(BeancpInfo.of(toType)), converter, priority));
    }
	private static void registerEq2Enum(Type fromType, BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createEqualMatcher(BeancpInfo.of(fromType)),BeancpInfoMatcherTool.createEnumMatcher(),  converter, priority));
    }
	private static void register(Type fromType, Type toType, BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(toType)), converter, priority));
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
