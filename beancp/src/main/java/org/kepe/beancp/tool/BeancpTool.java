package org.kepe.beancp.tool;

import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ARETURN;
import static org.objectweb.asm.Opcodes.CHECKCAST;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicInteger;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.BeancpConvertProvider;
import org.kepe.beancp.ct.asm.MethodASMContext;
import org.kepe.beancp.ct.converter.BeancpConverterInfo;
import org.kepe.beancp.ct.itf.BeancpASMConverter;
import org.kepe.beancp.ct.itf.BeancpConvertByObject;
import org.kepe.beancp.ct.itf.BeancpConverter;
import org.kepe.beancp.ct.itf.BeancpSimpleCustomConverter;
import org.kepe.beancp.ct.reg.BeancpRegisters;
import org.kepe.beancp.info.BeancpInfo;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
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
    
    public static boolean isAllowField(Field field) {
		int mod=field.getModifiers();
		if(Modifier.isTransient(mod)) {
			return false;
		}
		return true;
	}
	
	public static boolean isUsefulField(Field field) {
		int mod=field.getModifiers();
		if(!Modifier.isPrivate(mod)&&!Modifier.isAbstract(mod)&&!Modifier.isInterface(mod)&&!Modifier.isStatic(mod)&&!Modifier.isTransient(mod)) {
			return true;
		}
		return false;
	}
	public static boolean isAllowMethod(Method method) {
		return true;
	}
	
	public static boolean isUsefulMethod(Method method) {
		int mod=method.getModifiers();
		if(!Modifier.isPrivate(mod)&&!Modifier.isAbstract(mod)&&!Modifier.isInterface(mod)&&!Modifier.isStatic(mod)&&!Modifier.isTransient(mod)) {
			return true;
		}
		return false;
	}
	
}
