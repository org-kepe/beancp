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

public class BeancpBase2Registers  implements BeancpRegister{
	public static void registers() {
		
		BeancpCustomConverter converter1=new BeancpCustomConverter<Boolean,AtomicBoolean>() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 1;
			}

			@Override
			public AtomicBoolean convert(BeancpInvocationOO<Boolean, AtomicBoolean> invocation, BeancpContext context, Boolean fromObj,
					AtomicBoolean toObj) {
				if(toObj!=null) {
					toObj.set(fromObj);
					return toObj;
				}
				return new AtomicBoolean(fromObj);
			};
			@Override
			public AtomicBoolean convert(BeancpInvocationZO<AtomicBoolean> invocation, BeancpContext context, boolean fromObj, AtomicBoolean toObj) {
				if(toObj!=null) {
					toObj.set(fromObj);
					return toObj;
				}
				return new AtomicBoolean(fromObj);
			};
			
			
		};
		registerEq(boolean.class,AtomicBoolean.class, converter1, PRIORITY8);
		registerEq(Boolean.class,AtomicBoolean.class, converter1, PRIORITY8);
		
		converter1=new BeancpCustomConverter<AtomicBoolean,Boolean>() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 1;
			}

			@Override
			public Boolean convert(BeancpInvocationOO<AtomicBoolean,Boolean> invocation, BeancpContext context, AtomicBoolean fromObj,
					Boolean toObj) {
				return fromObj.get();
			};
			@Override
			public boolean convert(BeancpInvocationOZ<AtomicBoolean> invocation, BeancpContext context, AtomicBoolean fromObj, boolean toObj) {
				if(fromObj==null) {
					return toObj;
				}
				return fromObj.get();
			};
			
			
		};
		registerEq(AtomicBoolean.class,boolean.class, converter1, PRIORITY8);
		registerEq(AtomicBoolean.class,Boolean.class, converter1, PRIORITY8);
		
		converter1=new BeancpCustomConverter<Integer,AtomicInteger>() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 1;
			}

			@Override
			public AtomicInteger convert(BeancpInvocationOO<Integer, AtomicInteger> invocation, BeancpContext context, Integer fromObj,
					AtomicInteger toObj) {
				if(toObj!=null) {
					toObj.set(fromObj);
					return toObj;
				}
				return new AtomicInteger(fromObj);
			};
			@Override
			public AtomicInteger convert(BeancpInvocationIO<AtomicInteger> invocation, BeancpContext context, int fromObj, AtomicInteger toObj) {
				if(toObj!=null) {
					toObj.set(fromObj);
					return toObj;
				}
				return new AtomicInteger(fromObj);
			};
			
			
		};
		registerEq(int.class,AtomicInteger.class, converter1, PRIORITY8);
		registerEq(Integer.class,AtomicInteger.class, converter1, PRIORITY8);
		
		converter1=new BeancpCustomConverter<AtomicInteger,Integer>() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 1;
			}

			@Override
			public Integer convert(BeancpInvocationOO<AtomicInteger,Integer> invocation, BeancpContext context, AtomicInteger fromObj,
					Integer toObj) {
				return fromObj.get();
			};
			@Override
			public int convert(BeancpInvocationOI<AtomicInteger> invocation, BeancpContext context, AtomicInteger fromObj, int toObj) {
				if(fromObj==null) {
					return toObj;
				}
				return fromObj.get();
			};
			
			
		};
		registerEq(AtomicInteger.class,int.class, converter1, PRIORITY8);
		registerEq(AtomicInteger.class,Integer.class, converter1, PRIORITY8);
		
		
		converter1=new BeancpCustomConverter<Long,AtomicLong>() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 1;
			}

			@Override
			public AtomicLong convert(BeancpInvocationOO<Long, AtomicLong> invocation, BeancpContext context, Long fromObj,
					AtomicLong toObj) {
				if(toObj!=null) {
					toObj.set(fromObj);
					return toObj;
				}
				return new AtomicLong(fromObj);
			};
			@Override
			public AtomicLong convert(BeancpInvocationJO<AtomicLong> invocation, BeancpContext context, long fromObj, AtomicLong toObj) {
				if(toObj!=null) {
					toObj.set(fromObj);
					return toObj;
				}
				return new AtomicLong(fromObj);
			};
			
			
		};
		registerEq(long.class,AtomicLong.class, converter1, PRIORITY8);
		registerEq(Long.class,AtomicLong.class, converter1, PRIORITY8);
		
		converter1=new BeancpCustomConverter<AtomicLong,Long>() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 1;
			}

			@Override
			public Long convert(BeancpInvocationOO<AtomicLong,Long> invocation, BeancpContext context, AtomicLong fromObj,
					Long toObj) {
				return fromObj.get();
			};
			@Override
			public long convert(BeancpInvocationOJ<AtomicLong> invocation, BeancpContext context, AtomicLong fromObj, long toObj) {
				if(fromObj==null) {
					return toObj;
				}
				return fromObj.get();
			};
			
			
		};
		registerEq(AtomicLong.class,long.class, converter1, PRIORITY8);
		registerEq(AtomicLong.class,Long.class, converter1, PRIORITY8);
		
		
		//AtomicBoolean AtomicInteger  AtomicLong
		
		
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
