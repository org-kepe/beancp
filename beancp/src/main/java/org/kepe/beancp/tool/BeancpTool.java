package org.kepe.beancp.tool;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.config.BeancpOOConverter;
import org.kepe.beancp.config.BeancpPropertyGetAndSet;
import org.kepe.beancp.config.BeancpTypeConverter;
import org.kepe.beancp.config.BeancpTypeMatcher;
import org.kepe.beancp.config.BeancpTypeRelMatcher;
import org.kepe.beancp.ct.BeancpConvertProvider;
import org.kepe.beancp.ct.convert.BeancpConvertMapper;
import org.kepe.beancp.ct.converter.BeancpConverterInfo;
import org.kepe.beancp.ct.converter.BeancpMapperPropertyInfo;
import org.kepe.beancp.ct.invocation.BeancpInvocationOO;
import org.kepe.beancp.ct.itf.BeancpConvertByObject;
import org.kepe.beancp.ct.itf.BeancpConverter;
import org.kepe.beancp.ct.itf.BeancpCustomConverter;
import org.kepe.beancp.ct.reg.BeancpRegisters;
import org.kepe.beancp.exception.BeancpException;
import org.kepe.beancp.info.BeancpInfo;
/**
 * Hello world!
 *
 */
public class BeancpTool {
	private volatile static BeancpFeature DEFAULT_FEATURE=BeancpFeature.DEFAULT_FEATURE;
    static {
    	BeancpRegisters.registers();
//        register(BeancpInfo.of(String.class), BeancpInfo.of(int.class), new BeancpSimpleCustomConverter() {
//
//            @Override
//            public Object convert(BeancpFeature flag,BeancpContext context,Object fromObj, Class fromClass, Object toObj, Class toClass) {
//                if (StringTool.isEmpty(fromObj)) {
//                    return 0;
//                }
//                return Integer.parseInt((String) fromObj);
//            }
//            @Override
//            public int distance(BeancpFeature flag,Class fromClass,Class toClass){
//                return 100;
//            }
//        },0);
//        register(BeancpInfo.of(Integer.class), BeancpInfo.of(AtomicInteger.class), new BeancpASMConverter() {
//
//            @Override
//            public int distance(BeancpFeature flag,Class fromClass,Class toClass){
//                return 100;
//            }
//
//			@Override
//			public void convert2New(MethodASMContext asmContext,BeancpFeature flag,Type fromType,Class fromClass,Type toType,Class toClass) {
//				MethodVisitor methodVisitor=asmContext.getMethodVisitor();
//				Label label0 = new Label();
//				methodVisitor.visitLabel(label0);
//				methodVisitor.visitLineNumber(asmContext.getNextLine(), label0);
//				methodVisitor.visitTypeInsn(NEW, "java/util/concurrent/atomic/AtomicInteger");
//				methodVisitor.visitInsn(DUP);
//				methodVisitor.visitVarInsn(ALOAD, 2);
//				methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
//				methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
//				methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/util/concurrent/atomic/AtomicInteger", "<init>", "(I)V", false);
//				methodVisitor.visitInsn(ARETURN);
//				
//			
//			}
//
//			@Override
//			public void convert2Object(MethodASMContext asmContext,BeancpFeature flag,Type fromType,Class fromClass,Type toType,Class toClass) {
//				MethodVisitor methodVisitor=asmContext.getMethodVisitor();
//
//				Label label0 = new Label();
//				methodVisitor.visitLabel(label0);
//				methodVisitor.visitLineNumber(asmContext.getNextLine(), label0);
//				methodVisitor.visitInsn(ACONST_NULL);
//				methodVisitor.visitInsn(ARETURN);
//			}
//
//			@Override
//			public void createField(MethodASMContext asmContext, BeancpFeature flag, Type fromType, Class fromClass,
//					Type toType, Class toClass) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void init(MethodASMContext asmContext, BeancpFeature flag, Type fromType, Class fromClass,
//					Type toType, Class toClass) {
//				// TODO Auto-generated method stub
//				
//			}
//        },0);
    }
    
    public static BeancpFeature getDefaultFeature() {
    	return DEFAULT_FEATURE;
    }
    public static void configAdd(BeancpFeature feature) {
    	DEFAULT_FEATURE=DEFAULT_FEATURE.add(feature);
    }
    public static void configRemove(BeancpFeature feature) {
    	DEFAULT_FEATURE=DEFAULT_FEATURE.add(feature);
    }

    public static Object convert(BeancpFeature flag,BeancpContext context,Type fromType, Class fromClass, Object fromObj, Type toType, Class toClass,
            Object toObj) {
        BeancpConvertProvider bcp = getConvertProvider(flag,fromType, fromClass, fromObj, toType, toClass, toObj);
        return ((BeancpConvertByObject)bcp).convertByObject(context,fromObj, toObj);
    }
    
    public static BeancpConvertProvider getConvertProvider(BeancpFeature flag,Type fromType, Class fromClass, Object fromObj, Type toType,
            Class toClass, Object toObj) {
		if(flag==null){flag=DEFAULT_FEATURE;}
        BeancpInfo fromInfo = BeancpInfo.of(fromType, fromClass, fromObj);
        BeancpInfo toInfo = BeancpInfo.of(toType, toClass, toObj);
        BeancpConvertProvider provider=BeancpConvertProvider.of(flag, fromInfo, toInfo);
        return provider;
    }
	public static Object getConvertProvider(BeancpFeature flag,BeancpInfo fromInfo,BeancpInfo toInfo) {
		return BeancpConvertProvider.of(flag, fromInfo, toInfo);
    }
	

    private static void register(BeancpInfo fromInfo, BeancpInfo toInfo, BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createExtendsMatcher(fromInfo), BeancpInfoMatcherTool.createExtendsMatcher(toInfo), converter, priority));
    }
    
    public static <T,R> void registerTypeConversion(Type fromType,Type toType,BeancpTypeConverter<T,R> converter,int priority) {
		if(fromType!=null&&toType!=null&&converter!=null) {
	        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createEqualMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createEqualMatcher(BeancpInfo.of(toType)), converter, priority));
		}
	}
    
    public static <T,R> void registerTypeConversion(Type fromType,Type toType,int distance, BeancpOOConverter<T,R> oconverter,int priority) {
		if(fromType!=null&&toType!=null&&oconverter!=null) {
	        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createEqualMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createEqualMatcher(BeancpInfo.of(toType)), create(distance, oconverter), priority));
		}
	}
    public static <T,R> void registerTypeConversion(BeancpTypeMatcher fromTypeMatcher,BeancpTypeMatcher toTypeMatcher,BeancpTypeConverter<T,R> converter,int priority) {
		if(fromTypeMatcher!=null&&toTypeMatcher!=null&&converter!=null) {
	        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createMatcher(fromTypeMatcher), BeancpInfoMatcherTool.createMatcher(toTypeMatcher), converter, priority));
		}
	}
    
    public static <T,R> void registerTypeConversion(BeancpTypeMatcher fromTypeMatcher,BeancpTypeMatcher toTypeMatcher,int distance, BeancpOOConverter<T,R> oconverter,int priority) {
		if(fromTypeMatcher!=null&&toTypeMatcher!=null&&oconverter!=null) {
	        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createMatcher(fromTypeMatcher), BeancpInfoMatcherTool.createMatcher(toTypeMatcher), create(distance, oconverter), priority));
		}
	}
    public static <T,R> void registerTypeConversion(BeancpTypeMatcher fromTypeMatcher,BeancpTypeMatcher toTypeMatcher,BeancpTypeRelMatcher typeRelMatcher, BeancpTypeConverter<T,R> converter,int priority) {
		if(fromTypeMatcher!=null&&toTypeMatcher!=null&&typeRelMatcher!=null&&converter!=null) {
	        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createMatcher(fromTypeMatcher), BeancpInfoMatcherTool.createMatcher(toTypeMatcher),BeancpInfoMatcherTool.createRelMatcher(typeRelMatcher) , converter, priority));
		}
	}
    
    public static <T,R> void registerTypeConversion(BeancpTypeMatcher fromTypeMatcher,BeancpTypeMatcher toTypeMatcher,BeancpTypeRelMatcher typeRelMatcher,int distance, BeancpOOConverter<T,R> oconverter,int priority) {
		if(fromTypeMatcher!=null&&toTypeMatcher!=null&&typeRelMatcher!=null&&oconverter!=null) {
	        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createMatcher(fromTypeMatcher), BeancpInfoMatcherTool.createMatcher(toTypeMatcher),BeancpInfoMatcherTool.createRelMatcher(typeRelMatcher), create(distance, oconverter), priority));
		}
	}
	
	public static boolean isUsefulField(Field field) {
		int mod=field.getModifiers();
		if(!Modifier.isPrivate(mod)&&!Modifier.isAbstract(mod)&&!Modifier.isInterface(mod)&&!Modifier.isStatic(mod)&&!Modifier.isTransient(mod)) {
			return true;
		}
		return false;
	}
	
	public static boolean isUsefulMethod(Method method) {
		int mod=method.getModifiers();
		if(!Modifier.isPrivate(mod)&&!Modifier.isAbstract(mod)&&!Modifier.isInterface(mod)&&!Modifier.isStatic(mod)&&!Modifier.isTransient(mod)) {
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public static  <T> T clone(T obj,Type type,BeancpFeature feature,BeancpContext context) {
		if(obj==null) {
			return null;
		}
		if(feature==null) {
			feature=BeancpTool.DEFAULT_FEATURE;
		}
		if(BeancpFeature.DEFAULT_FEATURE.equals(feature)) {
			return (T) BeancpInfo.of(type, null, obj).getDefaultMapper().clone(context, obj); 
		}
		return (T)BeancpConvertMapper.of(BeancpInfo.of(type, null, obj), feature).clone(context, obj);
	}
	
	public static void setProperty(Type type,Object obj,String key,Object value,BeancpFeature feature,BeancpContext context) {
		if(feature==null) {
			feature=BeancpTool.DEFAULT_FEATURE;
		}
		if(BeancpFeature.DEFAULT_FEATURE.equals(feature)) {
			BeancpInfo.of(type, null, obj).getDefaultMapper().put(obj, key, value, null, context);
		}else {
			BeancpConvertMapper.of(BeancpInfo.of(type, null, obj), feature).put(obj, key, value, null, context);
		}
	}
	public static Object getProperty(Type type,Object obj,String key,Type valueType,BeancpFeature feature,BeancpContext context) {
		if(feature==null) {
			feature=BeancpTool.DEFAULT_FEATURE;
		}
		try {
			if(BeancpFeature.DEFAULT_FEATURE.equals(feature)) {
				return BeancpInfo.of(type, null, obj).getDefaultMapper().get(obj, key, null, BeancpInfo.of(valueType), context);
			}else {
				return BeancpConvertMapper.of(BeancpInfo.of(type, null, obj), feature).get(obj, key, null, BeancpInfo.of(valueType), context);
			}
		} catch (BeancpException e) {
			throw e;
		} catch (Exception e) {
			throw new BeancpException(e);
		}
	}
	
	public static BeancpCustomConverter create(int dinstance,BeancpOOConverter ooConverter) {
    	return new BeancpCustomConverter() {
    		@Override
    		public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
    			return dinstance;
    		}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj, Object toObj) {
				return ooConverter.convert(invocation, context, fromObj, toObj);
			}
    		
    	};
    }
	public static void registerPropertyGetAndSet(Type type,BeancpPropertyGetAndSet pgas) {
		registerPropertyGetAndSet(type,pgas,100);
	}
	
	public static void registerPropertyGetAndSet(Type type,BeancpPropertyGetAndSet pgas,int priority) {
		BeancpConvertMapper.register(BeancpMapperPropertyInfo.of(BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(type)), pgas, priority));
	}
	
}
