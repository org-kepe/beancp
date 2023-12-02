package org.kepe.beancp.ct.asm;

import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.CHECKCAST;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.NEW;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.convert.BeancpConvertASMProvider;
import org.kepe.beancp.ct.convert.BeancpConvertMapper;
import org.kepe.beancp.ct.convert.BeancpConvertProviderTool;
import org.kepe.beancp.ct.converter.BeancpConverterInfo;
import org.kepe.beancp.ct.itf.BeancpASMConverter;
import org.kepe.beancp.exception.BeancpException;
import org.kepe.beancp.info.BeancpCloneInfo;
import org.kepe.beancp.info.BeancpFieldInfo;
import org.kepe.beancp.info.BeancpGetInfo;
import org.kepe.beancp.info.BeancpInfo;
import org.kepe.beancp.info.BeancpInitInfo;
import org.kepe.beancp.info.BeancpName;
import org.kepe.beancp.info.BeancpSetInfo;
import org.kepe.beancp.tool.BeancpBeanTool;
import org.kepe.beancp.tool.vo.Tuple2;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.RecordComponentVisitor;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.CheckClassAdapter;
import org.objectweb.asm.util.Textifier;
import org.kepe.beancp.ct.BeancpConvertProvider;
import org.kepe.beancp.ct.asm.BeancpInfoASMTool;
import org.kepe.beancp.ct.asm.MethodASMContext;


/**
 * Hello world!
 *
 */
public class BeancpInfoASMTool implements Opcodes 
{
	private static final AtomicInteger INDEX=new AtomicInteger(0);
	private static boolean checkClass=true;
	private static final String CLASS_NAME_PREFIX="BeancpConvertGenProvider";
	private static final String CLASS_NAME_PREFIX_MAPPER="BeancpConvertGenMapper";
	private static final BeancpDynamicClassLoader CLASS_LOADER=new BeancpDynamicClassLoader(BeancpDynamicClassLoader.getInstance());
	private static Map<Class, String> descMapping = new HashMap<>();
	private static Map<Class, String> typeMapping = new HashMap<>();
	private static AtomicReference<char[]> descCacheRef = new AtomicReference<>();
	static {
		descMapping.put(int.class, "I");
	    descMapping.put(void.class, "V");
	    descMapping.put(boolean.class, "Z");
	    descMapping.put(char.class, "C");
	    descMapping.put(byte.class, "B");
	    descMapping.put(short.class, "S");
	    descMapping.put(float.class, "F");
	    descMapping.put(long.class, "J");
	    descMapping.put(double.class, "D");

	    typeMapping.put(int.class, "I");
	    typeMapping.put(void.class, "V");
	    typeMapping.put(boolean.class, "Z");
	    typeMapping.put(char.class, "C");
	    typeMapping.put(byte.class, "B");
	    typeMapping.put(short.class, "S");
	    typeMapping.put(float.class, "F");
	    typeMapping.put(long.class, "J");
	    typeMapping.put(double.class, "D");
	}
	
	public static BeancpConvertASMProvider generateASMProvider(BeancpConvertProvider provider,BeancpConverterInfo info,BeancpFeature flag,BeancpInfo fromInfo,BeancpInfo toInfo){
    	BeancpASMConverter asmConverter=(BeancpASMConverter) info.getConverter();
    	ClassVisitor classWriter;
    	if(checkClass) {
        	classWriter = new CheckClassAdapter( new ClassWriter(ClassWriter.COMPUTE_MAXS));
    	}else {
        	classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
    	}
		FieldVisitor fieldVisitor;
		RecordComponentVisitor recordComponentVisitor;
		MethodVisitor methodVisitor;
		AnnotationVisitor annotationVisitor0;
		int classIndex=INDEX.incrementAndGet();
		String classpath=BeancpConvertASMProvider.class.getPackage().getName().replace('.', '/')+"/"+CLASS_NAME_PREFIX+classIndex;
		classWriter.visit(V1_8, ACC_PUBLIC | ACC_SUPER, classpath, null, getClassName(BeancpConvertASMProvider.class), null);

		classWriter.visitSource(CLASS_NAME_PREFIX+classIndex+".java", null);
		MethodASMContext asmContext=new MethodASMContext(classpath,classWriter, null, 1);
		
		asmConverter.createField(asmContext, flag, fromInfo.getBType(),fromInfo.getBClass(),toInfo.getBType(),toInfo.getBClass());
		for(int i=0;i<20;i++) {
			asmContext.getNextLine();
		}
		{
			methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "(Lorg/kepe/beancp/ct/BeancpConvertProvider;Lorg/kepe/beancp/config/BeancpFeature;"+BeancpInfoASMTool.desc(BeancpConverterInfo.class)+"Lorg/kepe/beancp/info/BeancpInfo;Lorg/kepe/beancp/info/BeancpInfo;)V", null, null);
			methodVisitor.visitParameter("parent", 0);
			methodVisitor.visitParameter("feature", 0);
			methodVisitor.visitParameter("info", 0);
			methodVisitor.visitParameter("fromInfo", 0);
			methodVisitor.visitParameter("toInfo", 0);
			methodVisitor.visitCode();
			Label label0 = new Label();
			methodVisitor.visitLabel(label0);
			methodVisitor.visitLineNumber(asmContext.getNextLine(), label0);
			methodVisitor.visitVarInsn(ALOAD, 0);
			methodVisitor.visitVarInsn(ALOAD, 1);
			methodVisitor.visitVarInsn(ALOAD, 2);
			methodVisitor.visitVarInsn(ALOAD, 3);
			methodVisitor.visitVarInsn(ALOAD, 4);
			methodVisitor.visitVarInsn(ALOAD, 5);
			methodVisitor.visitMethodInsn(INVOKESPECIAL, BeancpInfoASMTool.getClassName(BeancpConvertASMProvider.class), "<init>", "(Lorg/kepe/beancp/ct/BeancpConvertProvider;Lorg/kepe/beancp/config/BeancpFeature;"+BeancpInfoASMTool.desc(BeancpConverterInfo.class)+"Lorg/kepe/beancp/info/BeancpInfo;Lorg/kepe/beancp/info/BeancpInfo;)V", false);
			asmContext.setMethodVisitor(methodVisitor);
			asmConverter.init(asmContext, flag, fromInfo.getBType(),fromInfo.getBClass(),toInfo.getBType(),toInfo.getBClass());
			
			Label label3 = new Label();
			methodVisitor.visitLabel(label3);
			methodVisitor.visitLineNumber(asmContext.getNextLine(), label3);
			methodVisitor.visitInsn(RETURN);








			methodVisitor.visitMaxs(5, 5);
			methodVisitor.visitEnd();
		}
		
		{
			methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "convert0", "(Lorg/kepe/beancp/config/BeancpContext;Ljava/lang/Object;)Ljava/lang/Object;", "<T:Ljava/lang/Object;R:Ljava/lang/Object;>(Lorg/kepe/beancp/config/BeancpContext;TT;)TR;", null);
			methodVisitor.visitParameter("context", 0);
			methodVisitor.visitParameter("fromObj", 0);
			methodVisitor.visitCode();
			asmContext.setMethodVisitor(methodVisitor);
			asmConverter.convert2New(asmContext,flag,fromInfo.getBType(),fromInfo.getBClass(),toInfo.getBType(),toInfo.getBClass());
			
			methodVisitor.visitMaxs(0, 0);
			methodVisitor.visitEnd();
		}
		for(int i=0;i<4;i++) {
			asmContext.getNextLine();
		}
		{
			methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "convert0", "(Lorg/kepe/beancp/config/BeancpContext;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "<T:Ljava/lang/Object;R:Ljava/lang/Object;>(Lorg/kepe/beancp/config/BeancpContext;TT;TR;)TR;", null);
			methodVisitor.visitParameter("context", 0);
			methodVisitor.visitParameter("fromObj", 0);
			methodVisitor.visitParameter("toObj", 0);
			methodVisitor.visitCode();
			asmContext.setMethodVisitor(methodVisitor);
			asmConverter.convert2Object(asmContext,flag,fromInfo.getBType(),fromInfo.getBClass(),toInfo.getBType(),toInfo.getBClass());
			methodVisitor.visitMaxs(0, 0);
			methodVisitor.visitEnd();
		}
		classWriter.visitEnd();
		byte[] code;
		if(checkClass) {
			code = ((ClassWriter)classWriter.getDelegate()).toByteArray();
		}else {
			code = ((ClassWriter)classWriter).toByteArray();
		}
        Class<? extends BeancpConvertASMProvider> clazz=(Class<? extends BeancpConvertASMProvider>) CLASS_LOADER.defineClassPublic(null, code, 0, code.length);

		try {
			return (BeancpConvertASMProvider) clazz.getConstructors()[0].newInstance(provider,flag, info, fromInfo, toInfo);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
	public static void initProxyOpClass(BeancpInfo info){
		List<BeancpInitInfo> proxyOpList1=new ArrayList<>();
    	List<BeancpGetInfo> proxyOpList2=new ArrayList<>();
    	List<BeancpSetInfo> proxyOpList3=new ArrayList<>();
    	if(info.inits!=null) {
    		for(BeancpInitInfo initInfo:info.inits) {
        		if(initInfo.needProxy()) {
        			proxyOpList1.add(initInfo);
        		}
        	}
    	}
    	if(info.fields!=null) {
    		for(BeancpFieldInfo fieldInfo:info.fields.values()) {
        		for(BeancpGetInfo getInfo:fieldInfo.getGetterList()) {
        			if(getInfo.needProxy()) {
        				proxyOpList2.add(getInfo);
        			}
        		}
        		for(BeancpSetInfo setInfo:fieldInfo.getSetterList()) {
        			if(setInfo.needProxy()) {
        				proxyOpList3.add(setInfo);
        			}
        		}
        	}
    	}
    	
    	if(proxyOpList1.isEmpty()&&proxyOpList2.isEmpty()&&proxyOpList3.isEmpty()&&(info.cloneInfo==null||!info.cloneInfo.needProxy())) {
    		return;
    	}
    	Class<?> clazz=null;
    	try {
    		clazz=generateProxyOpClass(false,info,proxyOpList1,proxyOpList2,proxyOpList3);
		} catch (Exception e) {
			e.printStackTrace();
    		try {
				clazz=generateProxyOpClass(true,info,proxyOpList1,proxyOpList2,proxyOpList3);
			} catch (Exception e1) {e1.printStackTrace();
			}
		}
    	if(clazz!=null) {
    		Method[] methods=clazz.getDeclaredMethods();
    		for(int i=0;i<proxyOpList1.size();i++){
    			BeancpInitInfo initInfo=proxyOpList1.get(i);
    			String methodName="init"+i;
    			for(Method method:methods) {
    				if(methodName.equals(method.getName())) {
    					initInfo.updateMethod(method);
    					break;
    				}
    			}
    		}
    		for(int i=0;i<proxyOpList2.size();i++){
    			BeancpGetInfo getInfo=proxyOpList2.get(i);
    			String methodName="get"+i;
    			for(Method method:methods) {
    				if(methodName.equals(method.getName())) {
    					getInfo.updateMethod(method);
    					break;
    				}
    			}
    		}
    		for(int i=0;i<proxyOpList3.size();i++){
    			BeancpSetInfo setInfo=proxyOpList3.get(i);
    			String methodName="set"+i;
    			for(Method method:methods) {
    				if(methodName.equals(method.getName())) {
    					setInfo.updateMethod(method);
    					break;
    				}
    			}
    		}
    		if(info.cloneInfo!=null&&info.cloneInfo.needProxy()) {
    			for(Method method:methods) {
    				if("clone".equals(method.getName())) {
    					info.cloneInfo.updateMethod(method);
    					break;
    				}
    			}
    		}
    		
    	}
	}
	private static Class<?> generateProxyOpClass(boolean isInvoke,BeancpInfo info,List<BeancpInitInfo> proxyOpList1,List<BeancpGetInfo> proxyOpList2,List<BeancpSetInfo> proxyOpList3){
    	if(proxyOpList1.isEmpty()&&proxyOpList2.isEmpty()&&proxyOpList3.isEmpty()) {
    		return null;
    	}
    	
    	ClassVisitor classWriter;
    	if(checkClass) {
        	classWriter = new CheckClassAdapter( new ClassWriter(ClassWriter.COMPUTE_MAXS));
    	}else {
        	classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
    	}
		FieldVisitor fieldVisitor;
		RecordComponentVisitor recordComponentVisitor;
		MethodVisitor methodVisitor;
		AnnotationVisitor annotationVisitor0;
		
		int classIndex=-1;
		for(int i=0;i<1000000;i++) {
			try {
				if(isInvoke) {
					Class.forName(BeancpConvertASMProvider.class.getPackage().getName()+".BeancpProxy"+i);
				}else {
					Class.forName(info.getFClass().getPackage().getName()+".BeancpProxy"+i);
				}
				//info.getFClass().getClassLoader().loadClass(info.getFClass().getPackage().getName()+"."+i);
			} catch (Exception e) {
				classIndex=i;
				break;
			}
		}
		if(classIndex==-1) {
			return null;
		}
		String classpath;
		if(isInvoke) {
			classpath=BeancpConvertASMProvider.class.getPackage().getName().replace(".", "/")+"/BeancpProxy"+classIndex;
		}else {
			classpath=info.getFClass().getPackage().getName().replace(".", "/")+"/BeancpProxy"+classIndex;
		}
		String className=classpath.replace('/', '.');
		classWriter.visit(V1_8, ACC_PUBLIC | ACC_SUPER, classpath, null, "java/lang/Object", null);

		classWriter.visitSource("BeancpProxy"+classIndex+".java", null);
		MethodASMContext asmContext=new MethodASMContext(classpath,classWriter, null, 1);
		for(int i=0;i<proxyOpList1.size();i++){
			BeancpInitInfo initInfo=proxyOpList1.get(i);
			fieldVisitor = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, "constructorInit"+i, "Ljava/lang/reflect/Constructor;", null, null);
			fieldVisitor.visitEnd();
		}
		for(int i=0;i<proxyOpList2.size();i++){
			BeancpGetInfo getInfo=proxyOpList2.get(i);
			if(getInfo.getMethod()!=null) {
				fieldVisitor = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, "methodGet"+i, "Ljava/lang/reflect/Method;", null, null);
			}else {
				fieldVisitor = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, "fieldGet"+i, "Ljava/lang/reflect/Field;", null, null);
			}
			fieldVisitor.visitEnd();
		}
		for(int i=0;i<proxyOpList3.size();i++){
			BeancpSetInfo setInfo=proxyOpList3.get(i);
			if(setInfo.getMethod()!=null) {
				fieldVisitor = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, "methodSet"+i, "Ljava/lang/reflect/Method;", null, null);
			}else {
				fieldVisitor = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, "fieldSet"+i, "Ljava/lang/reflect/Field;", null, null);
			}
			fieldVisitor.visitEnd();
		}
		if(info.cloneInfo!=null&&info.cloneInfo.needProxy()) {
			fieldVisitor = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, "methodClone", "Ljava/lang/reflect/Method;", null, null);
			fieldVisitor.visitEnd();
		}
		asmContext.getNextLine(4);
		{
			methodVisitor = classWriter.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
			methodVisitor.visitCode();
			for(int i=0;i<proxyOpList1.size();i++){
				BeancpInitInfo initInfo=proxyOpList1.get(i);
				Label label0 = new Label();
				methodVisitor.visitLabel(label0);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label0);
				visitInt(methodVisitor,info.getId());
				methodVisitor.visitMethodInsn(INVOKESTATIC, "org/kepe/beancp/info/BeancpInfo", "getInfoById", "(I)Lorg/kepe/beancp/info/BeancpInfo;", false);
				visitInt(methodVisitor, info.inits.indexOf(initInfo));
				methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/info/BeancpInfo", "getInitMethod", "(I)Ljava/lang/reflect/Constructor;", false);
				methodVisitor.visitFieldInsn(PUTSTATIC, classpath, "constructorInit"+i, "Ljava/lang/reflect/Constructor;");
			}
			for(int i=0;i<proxyOpList2.size();i++){
				BeancpGetInfo getInfo=proxyOpList2.get(i);
				if(getInfo.getMethod()!=null) {
					Label label2 = new Label();
					methodVisitor.visitLabel(label2);
					methodVisitor.visitLineNumber(asmContext.getNextLine(), label2);
					visitInt(methodVisitor, info.getId());
					methodVisitor.visitMethodInsn(INVOKESTATIC, "org/kepe/beancp/info/BeancpInfo", "getInfoById", "(I)Lorg/kepe/beancp/info/BeancpInfo;", false);
					methodVisitor.visitLdcInsn(getInfo.getName().name);
					visitInt(methodVisitor, info.fields.get(getInfo.getName().name).getGetterList().indexOf(getInfo),true);
					methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/info/BeancpInfo", "getGetMethod", "(Ljava/lang/String;I)Ljava/lang/reflect/Method;", false);
					methodVisitor.visitFieldInsn(PUTSTATIC, classpath, "methodGet"+i, "Ljava/lang/reflect/Method;");
				}else {
					Label label3 = new Label();
					methodVisitor.visitLabel(label3);
					methodVisitor.visitLineNumber(asmContext.getNextLine(), label3);
					visitInt(methodVisitor, info.getId());
					methodVisitor.visitMethodInsn(INVOKESTATIC, "org/kepe/beancp/info/BeancpInfo", "getInfoById", "(I)Lorg/kepe/beancp/info/BeancpInfo;", false);
					methodVisitor.visitLdcInsn(getInfo.getName().name);
					visitInt(methodVisitor, info.fields.get(getInfo.getName().name).getGetterList().indexOf(getInfo),true);
					methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/info/BeancpInfo", "getGetField", "(Ljava/lang/String;I)Ljava/lang/reflect/Field;", false);
					methodVisitor.visitFieldInsn(PUTSTATIC, classpath, "fieldGet"+i, "Ljava/lang/reflect/Field;");
				}
			}
			for(int i=0;i<proxyOpList3.size();i++){
				BeancpSetInfo setInfo=proxyOpList3.get(i);
				if(setInfo.getMethod()!=null) {
					Label label4 = new Label();
					methodVisitor.visitLabel(label4);
					methodVisitor.visitLineNumber(asmContext.getNextLine(), label4);
					visitInt(methodVisitor, info.getId());
					methodVisitor.visitMethodInsn(INVOKESTATIC, "org/kepe/beancp/info/BeancpInfo", "getInfoById", "(I)Lorg/kepe/beancp/info/BeancpInfo;", false);
					methodVisitor.visitLdcInsn(setInfo.getName().name);
					visitInt(methodVisitor, info.fields.get(setInfo.getName().name).getSetterList().indexOf(setInfo),true);
					methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/info/BeancpInfo", "getSetMethod", "(Ljava/lang/String;I)Ljava/lang/reflect/Method;", false);
					methodVisitor.visitFieldInsn(PUTSTATIC, classpath, "methodSet"+i, "Ljava/lang/reflect/Method;");
				}else {
					Label label5 = new Label();
					methodVisitor.visitLabel(label5);
					methodVisitor.visitLineNumber(asmContext.getNextLine(), label5);
					visitInt(methodVisitor, info.getId());
					methodVisitor.visitMethodInsn(INVOKESTATIC, "org/kepe/beancp/info/BeancpInfo", "getInfoById", "(I)Lorg/kepe/beancp/info/BeancpInfo;", false);
					methodVisitor.visitLdcInsn(setInfo.getName().name);
					visitInt(methodVisitor, info.fields.get(setInfo.getName().name).getSetterList().indexOf(setInfo),true);
					methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/info/BeancpInfo", "getSetField", "(Ljava/lang/String;I)Ljava/lang/reflect/Field;", false);
					methodVisitor.visitFieldInsn(PUTSTATIC, classpath, "fieldSet"+i, "Ljava/lang/reflect/Field;");
				}
			}
			if(info.cloneInfo!=null&&info.cloneInfo.needProxy()) {
				Label label2 = new Label();
				methodVisitor.visitLabel(label2);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label2);
				visitInt(methodVisitor, info.getId());
				methodVisitor.visitMethodInsn(INVOKESTATIC, "org/kepe/beancp/info/BeancpInfo", "getInfoById", "(I)Lorg/kepe/beancp/info/BeancpInfo;", false);
				methodVisitor.visitFieldInsn(GETFIELD, "org/kepe/beancp/info/BeancpInfo", "cloneInfo", "Lorg/kepe/beancp/info/BeancpCloneInfo;");
				methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/info/BeancpCloneInfo", "getMethod", "()Ljava/lang/reflect/Method;", false);
				methodVisitor.visitFieldInsn(PUTSTATIC, classpath, "methodClone", "Ljava/lang/reflect/Method;");
			}
			methodVisitor.visitInsn(RETURN);
			methodVisitor.visitMaxs(3, 0);
			methodVisitor.visitEnd();
		}
		asmContext.getNextLine(4);
		{
			methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
			methodVisitor.visitCode();
			Label label0 = new Label();
			methodVisitor.visitLabel(label0);
			methodVisitor.visitLineNumber(asmContext.getNextLine(), label0);
			methodVisitor.visitVarInsn(ALOAD, 0);
			methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
			methodVisitor.visitInsn(RETURN);
			//Label label1 = new Label();
			//methodVisitor.visitLabel(label1);
			//methodVisitor.visitLocalVariable("this", "Lorg/kepe/test/BeancpProxy1;", null, label0, label1, 0);
			methodVisitor.visitMaxs(1, 1);
			methodVisitor.visitEnd();
		}
		for(int i=0;i<proxyOpList1.size();i++){
			asmContext.getNextLine(3);
			BeancpInitInfo initInfo=proxyOpList1.get(i);
			if(!isInvoke&&initInfo.getAccess()==1) {
				methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, "init"+i, getMethodDesc(initInfo.getConstructor(),false), null, new String[] { "java/lang/Exception" });
				for(String key:initInfo.getParams().keySet()) {
					methodVisitor.visitParameter(key, 0);
				}
				methodVisitor.visitCode();
				Label label0 = new Label();
				Label label1 = new Label();
				Label label2 = new Label();
				methodVisitor.visitTryCatchBlock(label0, label1, label2, "java/lang/IllegalAccessError");
				methodVisitor.visitLabel(label0);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label0);
				methodVisitor.visitTypeInsn(NEW, getClassName(initInfo.getConstructor().getDeclaringClass()));
				methodVisitor.visitInsn(DUP);
				int m=0;
				for(BeancpInfo infok:initInfo.getParams().values()) {
					visitLoad(methodVisitor, infok, m);
					m++;
				}
				methodVisitor.visitMethodInsn(INVOKESPECIAL, getClassName(initInfo.getConstructor().getDeclaringClass()), "<init>", getMethodDesc(initInfo.getConstructor()), false);
				methodVisitor.visitLabel(label1);
				methodVisitor.visitInsn(ARETURN);
				methodVisitor.visitLabel(label2);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label2);
				methodVisitor.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {"java/lang/IllegalAccessError"});
				methodVisitor.visitVarInsn(ASTORE, initInfo.getParams().size());
				Label label3 = new Label();
				methodVisitor.visitLabel(label3);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label3);
				methodVisitor.visitFieldInsn(GETSTATIC, classpath, "constructorInit"+i, "Ljava/lang/reflect/Constructor;");
				visitInt(methodVisitor, initInfo.getParams().size());
				methodVisitor.visitTypeInsn(ANEWARRAY, "java/lang/Object");
				m=0;
				for(BeancpInfo infok:initInfo.getParams().values()) {
					methodVisitor.visitInsn(DUP);
					visitInt(methodVisitor, m);
					visitLoad(methodVisitor, infok, m);
					visitPrimCastObj(methodVisitor, infok);
					methodVisitor.visitInsn(AASTORE);
					m++;
				}
				methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "invokeNewInstance", "(Ljava/lang/reflect/Constructor;[Ljava/lang/Object;)Ljava/lang/Object;", false);
				methodVisitor.visitTypeInsn(CHECKCAST, getClassName(initInfo.getConstructor().getDeclaringClass()));
				methodVisitor.visitInsn(ARETURN);
//				Label label4 = new Label();
//				methodVisitor.visitLabel(label4);
//				methodVisitor.visitLocalVariable("key1", "Ljava/lang/String;", null, label0, label4, 0);
//				methodVisitor.visitLocalVariable("key3", "I", null, label0, label4, 1);
//				methodVisitor.visitLocalVariable("e", "Ljava/lang/IllegalAccessError;", null, label3, label4, 2);
				methodVisitor.visitMaxs(5, 3);
				methodVisitor.visitEnd();
			}else {
				methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, "init"+i, getInitProxyMethodDesc((Constructor<?>) initInfo.getConstructor()), null, null);
				for(String key:initInfo.getParams().keySet()) {
					methodVisitor.visitParameter(key, 0);
				}
				methodVisitor.visitCode();
				Label label0 = new Label();
				methodVisitor.visitLabel(label0);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label0);
				methodVisitor.visitFieldInsn(GETSTATIC, classpath, "constructorInit"+i, "Ljava/lang/reflect/Constructor;");
				visitInt(methodVisitor, initInfo.getParams().size());
				methodVisitor.visitTypeInsn(ANEWARRAY, "java/lang/Object");
				int m=0;
				for(BeancpInfo infok:initInfo.getParams().values()) {
					methodVisitor.visitInsn(DUP);
					visitInt(methodVisitor, m);
					visitLoad(methodVisitor, infok, m);
					visitPrimCastObj(methodVisitor, infok);
					methodVisitor.visitInsn(AASTORE);
					m++;
				}
				methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "invokeNewInstance", "(Ljava/lang/reflect/Constructor;[Ljava/lang/Object;)Ljava/lang/Object;", false);
				methodVisitor.visitTypeInsn(CHECKCAST, getClassName(BeancpBeanTool.getFinalPublicClass(initInfo.getConstructor().getDeclaringClass())));
				methodVisitor.visitInsn(ARETURN);
				methodVisitor.visitMaxs(5, 2);
				methodVisitor.visitEnd();
			}
			
		}
		for(int i=0;i<proxyOpList2.size();i++){
			asmContext.getNextLine(3);
			BeancpGetInfo getInfo=proxyOpList2.get(i);
			if(!isInvoke&&getInfo.getAccess()==1) {
				methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, "get"+i, "("+desc(getInfo.getParentInfo().getFClass())+")"+desc(getInfo.getMethod()!=null?getInfo.getMethod().getReturnType():getInfo.getField().getType()), null, (getInfo.getMethod()!=null?new String[] { "java/lang/Exception" }:null));
				methodVisitor.visitParameter("h", 0);
				methodVisitor.visitCode();
				Label label0 = new Label();
				Label label1 = new Label();
				Label label2 = new Label();
				methodVisitor.visitTryCatchBlock(label0, label1, label2, "java/lang/IllegalAccessError");
				methodVisitor.visitLabel(label0);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label0);
				methodVisitor.visitVarInsn(ALOAD, 0);
				visitGetInfo(methodVisitor, getInfo);
				methodVisitor.visitLabel(label1);
				visitReturn(methodVisitor, getInfo.getInfo());	
				methodVisitor.visitLabel(label2);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label2);
				methodVisitor.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {"java/lang/IllegalAccessError"});
				methodVisitor.visitVarInsn(ASTORE, 1);
				Label label3 = new Label();
				methodVisitor.visitLabel(label3);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label3);
				methodVisitor.visitVarInsn(ALOAD, 0);
				if(getInfo.getMethod()!=null) {
					methodVisitor.visitFieldInsn(GETSTATIC, classpath, "methodGet"+i, "Ljava/lang/reflect/Method;");
					methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "invokeGetMethod", "(Ljava/lang/Object;Ljava/lang/reflect/Method;)Ljava/lang/Object;", false);
				}else {
					methodVisitor.visitFieldInsn(GETSTATIC, classpath, "fieldGet"+i, "Ljava/lang/reflect/Field;");
					methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "invokeGetField", "(Ljava/lang/Object;Ljava/lang/reflect/Field;)Ljava/lang/Object;", false);
				}
				visitCast(methodVisitor, getInfo.getInfo());
				visitReturn(methodVisitor, getInfo.getInfo());				
				methodVisitor.visitMaxs(2, 2);
				methodVisitor.visitEnd();
			}else {
				methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, "get"+i, getGetProxyMethodDesc(getInfo), null, null);
				methodVisitor.visitParameter("h", 0);
				methodVisitor.visitCode();
				Label label0 = new Label();
				methodVisitor.visitLabel(label0);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label0);
				methodVisitor.visitVarInsn(ALOAD, 0);
				if(getInfo.getMethod()!=null) {
					methodVisitor.visitFieldInsn(GETSTATIC, classpath, "methodGet"+i, "Ljava/lang/reflect/Method;");
					methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "invokeGetMethod", "(Ljava/lang/Object;Ljava/lang/reflect/Method;)Ljava/lang/Object;", false);
				}else {
					methodVisitor.visitFieldInsn(GETSTATIC, classpath, "fieldGet"+i, "Ljava/lang/reflect/Field;");
					methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "invokeGetField", "(Ljava/lang/Object;Ljava/lang/reflect/Field;)Ljava/lang/Object;", false);
				}
				visitCast(methodVisitor, getInfo.getInfo());
				visitReturn(methodVisitor, getInfo.getInfo());
				methodVisitor.visitMaxs(2, 1);
				methodVisitor.visitEnd();
			}
			
		}
		for(int i=0;i<proxyOpList3.size();i++){
			asmContext.getNextLine(3);
			BeancpSetInfo setInfo=proxyOpList3.get(i);
			if(!isInvoke&&setInfo.getAccess()==1) {
				methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, "set"+i, "("+desc(setInfo.getParentInfo().getBClass())+desc(setInfo.getMethod()!=null?setInfo.getMethod().getParameterTypes()[0]:setInfo.getField().getType())+")V", null, (setInfo.getMethod()!=null?new String[] { "java/lang/Exception" }:null));
				methodVisitor.visitParameter("h", 0);
				methodVisitor.visitParameter("v", 0);
				methodVisitor.visitCode();
				Label label0 = new Label();
				Label label1 = new Label();
				Label label2 = new Label();
				methodVisitor.visitTryCatchBlock(label0, label1, label2, "java/lang/IllegalAccessError");
				methodVisitor.visitLabel(label0);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label0);
				methodVisitor.visitVarInsn(ALOAD, 0);
				visitLoad(methodVisitor, setInfo.getInfo(), 1);
				visitSetInfo(methodVisitor, setInfo);
				methodVisitor.visitLabel(label1);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label1);
				Label label3 = new Label();
				methodVisitor.visitJumpInsn(GOTO, label3);
				methodVisitor.visitLabel(label2);
				methodVisitor.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {"java/lang/IllegalAccessError"});
				methodVisitor.visitVarInsn(ASTORE, 2);
				Label label4 = new Label();
				methodVisitor.visitLabel(label4);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label4);
				methodVisitor.visitVarInsn(ALOAD, 0);
				if(setInfo.getMethod()!=null) {
					methodVisitor.visitFieldInsn(GETSTATIC, classpath, "methodSet"+i, "Ljava/lang/reflect/Method;");
				}else {
					methodVisitor.visitFieldInsn(GETSTATIC, classpath, "fieldSet"+i, "Ljava/lang/reflect/Field;");
				}
				visitLoad(methodVisitor, setInfo.getInfo(), 1);
				visitPrimCastObj(methodVisitor, setInfo.getInfo());
				if(setInfo.getMethod()!=null) {
					methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "invokeSetMethod", "(Ljava/lang/Object;Ljava/lang/reflect/Method;Ljava/lang/Object;)V", false);
				}else {
					methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "invokeSetField", "(Ljava/lang/Object;Ljava/lang/reflect/Field;Ljava/lang/Object;)V", false);
				}
				methodVisitor.visitLabel(label3);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label3);
				methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
				methodVisitor.visitInsn(RETURN);
				methodVisitor.visitMaxs(3, 3);
				methodVisitor.visitEnd();
			}else {
				methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, "set"+i, getSetProxyMethodDesc(setInfo), null, null);
				methodVisitor.visitParameter("h", 0);
				methodVisitor.visitParameter("v", 0);
				methodVisitor.visitCode();
				Label label0 = new Label();
				methodVisitor.visitLabel(label0);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label0);
				methodVisitor.visitVarInsn(ALOAD, 0);
				if(setInfo.getMethod()!=null) {
					methodVisitor.visitFieldInsn(GETSTATIC, classpath, "methodSet"+i, "Ljava/lang/reflect/Method;");
				}else {
					methodVisitor.visitFieldInsn(GETSTATIC, classpath, "fieldSet"+i, "Ljava/lang/reflect/Field;");
				}
				visitLoad(methodVisitor, setInfo.getInfo(), 1);
				visitPrimCastObj(methodVisitor, setInfo.getInfo());
				if(setInfo.getMethod()!=null) {
					methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "invokeSetMethod", "(Ljava/lang/Object;Ljava/lang/reflect/Method;Ljava/lang/Object;)V", false);
				}else {
					methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "invokeSetField", "(Ljava/lang/Object;Ljava/lang/reflect/Field;Ljava/lang/Object;)V", false);
				}
				Label label1 = new Label();
				methodVisitor.visitLabel(label1);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label1);
				methodVisitor.visitInsn(RETURN);
				methodVisitor.visitMaxs(3, 2);
				methodVisitor.visitEnd();
			}
		}
		if(info.cloneInfo!=null&&info.cloneInfo.needProxy()) {
			asmContext.getNextLine(3);
			BeancpCloneInfo cloneInfo=info.cloneInfo;
			if(!isInvoke&&cloneInfo.getAccess()==1) {
				methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, "clone", "("+desc(info.getFinalPublicClass())+")Ljava/lang/Object;", null, new String[] { "java/lang/Exception" });
				methodVisitor.visitParameter("h", 0);
				methodVisitor.visitCode();
				Label label0 = new Label();
				Label label1 = new Label();
				Label label2 = new Label();
				methodVisitor.visitTryCatchBlock(label0, label1, label2, "java/lang/IllegalAccessError");
				methodVisitor.visitLabel(label0);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label0);
				methodVisitor.visitVarInsn(ALOAD, 0);
				visitMethod(methodVisitor, cloneInfo.getMethod());
				methodVisitor.visitLabel(label1);
				methodVisitor.visitInsn(ARETURN);
				methodVisitor.visitLabel(label2);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label2);
				methodVisitor.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {"java/lang/IllegalAccessError"});
				methodVisitor.visitVarInsn(ASTORE, 1);
				Label label3 = new Label();
				methodVisitor.visitLabel(label3);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label3);
				methodVisitor.visitVarInsn(ALOAD, 0);
				methodVisitor.visitFieldInsn(GETSTATIC, classpath, "methodClone", "Ljava/lang/reflect/Method;");
				methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "invokeCloneMethod", "(Ljava/lang/Object;Ljava/lang/reflect/Method;)Ljava/lang/Object;", false);
				methodVisitor.visitInsn(ARETURN);				
				methodVisitor.visitMaxs(2, 2);
				methodVisitor.visitEnd();
			}else {
				methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, "clone", "("+desc(info.getFinalPublicClass())+")Ljava/lang/Object;", null, null);
				methodVisitor.visitParameter("h", 0);
				methodVisitor.visitCode();
				Label label0 = new Label();
				methodVisitor.visitLabel(label0);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label0);
				methodVisitor.visitVarInsn(ALOAD, 0);
				methodVisitor.visitFieldInsn(GETSTATIC, classpath, "methodClone", "Ljava/lang/reflect/Method;");
				methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "invokeCloneMethod", "(Ljava/lang/Object;Ljava/lang/reflect/Method;)Ljava/lang/Object;", false);
				methodVisitor.visitInsn(ARETURN);
				methodVisitor.visitMaxs(2, 1);
				methodVisitor.visitEnd();
			}
		}
		classWriter.visitEnd();
		byte[] code;
		if(checkClass) {
			code = ((ClassWriter)classWriter.getDelegate()).toByteArray();
		}else {
			code = ((ClassWriter)classWriter).toByteArray();
		}
		if(isInvoke) {
	        return CLASS_LOADER.defineClassPublic(null, code, 0, code.length);
		}else {
			return loadClassByClassLoader(info.getFClass().getClassLoader(),className,code);
		}
		//Class<?> clazz=
		//return loadClassByClassLoader(info.getFClass().getClassLoader(),className,code);
	}
	
	
	public static BeancpConvertMapper generateASMMapper(BeancpInfo info,BeancpFeature feature){
		Map<String,BeancpFieldInfo> fields1=new HashMap<>();
		if(info.fields!=null) {
			for(Map.Entry<String,BeancpFieldInfo> e:info.fields.entrySet()) {
				String key=e.getKey();
				BeancpFieldInfo fieldInfo=e.getValue();
				BeancpFieldInfo fieldInfo1=new BeancpFieldInfo(key,null);
				Set<Integer> sets1=new HashSet<>(); 
				for(BeancpGetInfo getInfo:fieldInfo.getGetterList()) {
					if(getInfo.isUseful(feature)) {
						Integer id=getInfo.getInfo().getId();
						if(!sets1.contains(id)) {
							fieldInfo1.addGetInfo(getInfo);
							sets1.add(id);
						}
					}
				}
				sets1.clear();
				for(BeancpSetInfo setInfo:fieldInfo.getSetterList()) {
					if(setInfo.isUseful(feature)) {
						Integer id=setInfo.getInfo().getId();
						if(!sets1.contains(id)) {
							fieldInfo1.addSetInfo(setInfo);
							sets1.add(id);
						}
					}
				}
				if(!fieldInfo1.getGetterList().isEmpty()||!fieldInfo1.getSetterList().isEmpty()) {
					fields1.put(key, fieldInfo1);
				}
			}
		}
		
		Set<String> getKeys=new HashSet<>();
		Set<String> setKeys=new HashSet<>();
		for(Map.Entry<String,BeancpFieldInfo> e:fields1.entrySet()) {
			String key=e.getKey();
			BeancpFieldInfo fieldInfo=e.getValue();
			if(!fieldInfo.getGetterList().isEmpty()) {
				//If there is no add, there is no add. If there is a key in the field, there is an add in the field that is the same as mine in the opposite direction, then there is no add. If I am a non is add, there is no add
				for(BeancpGetInfo getInfo:fieldInfo.getGetterList()) {
					if(!getInfo.isFake()) {
						getKeys.add(key);
					}
				}
			}
			
			if(!fieldInfo.getSetterList().isEmpty()) {
				//If there is no add, there is no add. If there is a key in the field, there is an add in the field that is the same as mine in the opposite direction, then there is no add. If I am a non is add, there is no add
				for(BeancpSetInfo setInfo:fieldInfo.getSetterList()) {
					if(!setInfo.isFake()) {
						setKeys.add(key);
					}
				}
			}
		}
		
		Map<String,Tuple2<BeancpFieldInfo,Integer>> fields=new HashMap<>();
		for(Map.Entry<String,BeancpFieldInfo> e:fields1.entrySet()) {
			BeancpFieldInfo fieldInfo=e.getValue();
			BeancpName name=fieldInfo.getName();
			fields.put(name.name, new Tuple2<>(fieldInfo,null));
		}
		
		for(Map.Entry<String,BeancpFieldInfo> e:fields1.entrySet()) {
			BeancpFieldInfo fieldInfo=e.getValue();
			BeancpName name=fieldInfo.getName();
			for(BeancpName fname: name.friends) {
				if((fname.type==BeancpName.NameType.UNDERLINE||fname.type==BeancpName.NameType.UPPERUNDERLINE)&&!fields.containsKey(fname.name)) {
					fields.put(fname.name, new Tuple2<>(fieldInfo,null));
				}
			}
		}
		List<String> keys=new ArrayList<>(fields.keySet());
		for(Entry<String, Tuple2<BeancpFieldInfo, Integer>> e:fields.entrySet()) {
			e.getValue().r2=Integer.valueOf(keys.indexOf(e.getKey()));
		}
		
		List<BeancpInitInfo> inits=new ArrayList<>();
		if(info.inits!=null) {
			for(BeancpInitInfo initInfo1:info.inits) {
				if(!initInfo1.isUseful(feature,null)) {
					continue;
				}
				inits.add(initInfo1);
			}
		}
		
    	ClassVisitor classWriter;
    	if(checkClass) {
        	classWriter = new CheckClassAdapter( new ClassWriter(ClassWriter.COMPUTE_MAXS));
    	}else {
        	classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
    	}
    	
    	FieldVisitor fieldVisitor;
    	RecordComponentVisitor recordComponentVisitor;
    	MethodVisitor methodVisitor;
    	AnnotationVisitor annotationVisitor0;
    	int classIndex=INDEX.incrementAndGet();
		String classpath=BeancpConvertMapper.class.getPackage().getName().replace('.', '/')+"/"+CLASS_NAME_PREFIX_MAPPER+classIndex;

    	classWriter.visit(V1_8, ACC_PUBLIC | ACC_SUPER, classpath, null, getClassName(BeancpConvertMapper.class), null);
		classWriter.visitSource(CLASS_NAME_PREFIX+classIndex+".java", null);
		MethodASMContext asmContext=new MethodASMContext(classpath,classWriter, null, 1);
		asmContext.getNextLine(20);
    	{
    		methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "(Lorg/kepe/beancp/info/BeancpInfo;Lorg/kepe/beancp/config/BeancpFeature;Ljava/util/Set;Ljava/util/Set;Ljava/util/Map;Ljava/util/List;Ljava/util/List;)V", "(Lorg/kepe/beancp/info/BeancpInfo;Lorg/kepe/beancp/config/BeancpFeature;Ljava/util/Set<Ljava/lang/String;>;Ljava/util/Set<Ljava/lang/String;>;Ljava/util/Map<Ljava/lang/String;Lorg/kepe/beancp/tool/vo/Tuple2<Lorg/kepe/beancp/info/BeancpFieldInfo;Ljava/lang/Integer;>;>;Ljava/util/List<Ljava/lang/String;>;Ljava/util/List<Lorg/kepe/beancp/info/BeancpInitInfo;>;)V", null);
    		methodVisitor.visitParameter("info", 0);
    		methodVisitor.visitParameter("feature", 0);
    		methodVisitor.visitParameter("getKeys", 0);
    		methodVisitor.visitParameter("setKeys", 0);
    		methodVisitor.visitParameter("fields", 0);
    		methodVisitor.visitParameter("keys", 0);
    		methodVisitor.visitParameter("inits", 0);
    		methodVisitor.visitCode();
    		Label label0 = new Label();
    		methodVisitor.visitLabel(label0);
    		methodVisitor.visitLineNumber(25, label0);
    		methodVisitor.visitVarInsn(ALOAD, 0);
    		methodVisitor.visitVarInsn(ALOAD, 1);
    		methodVisitor.visitVarInsn(ALOAD, 2);
    		methodVisitor.visitVarInsn(ALOAD, 3);
    		methodVisitor.visitVarInsn(ALOAD, 4);
    		methodVisitor.visitVarInsn(ALOAD, 5);
    		methodVisitor.visitVarInsn(ALOAD, 6);
    		methodVisitor.visitVarInsn(ALOAD, 7);
    		methodVisitor.visitMethodInsn(INVOKESPECIAL, BeancpInfoASMTool.getClassName(BeancpConvertMapper.class), "<init>", "(Lorg/kepe/beancp/info/BeancpInfo;Lorg/kepe/beancp/config/BeancpFeature;Ljava/util/Set;Ljava/util/Set;Ljava/util/Map;Ljava/util/List;Ljava/util/List;)V", false);
    		Label label1 = new Label();
    		methodVisitor.visitLabel(label1);
    		methodVisitor.visitLineNumber(26, label1);
    		methodVisitor.visitInsn(RETURN);
    		methodVisitor.visitMaxs(8, 8);
    		methodVisitor.visitEnd();
    	}
    	boolean isAllwaysNew=feature.is(BeancpFeature.ALLWAYS_NEW);
    	boolean isSetValueWhenNull=!feature.is(BeancpFeature.SETVALUE_WHENNOTNULL);
    	Class<?>[] bases=new Class[]{Object.class,int.class,long.class,char.class,byte.class,short.class,boolean.class,float.class,double.class};
    	for(Class<?> clazz:bases) {
    		int py=0;
    		if(clazz==double.class||clazz==long.class) {
    			py=1;
    		}
    		boolean isGetPrim=clazz.isPrimitive();
    		BeancpInfo clazzInfo=BeancpInfo.of(clazz);
    		methodVisitor = classWriter.visitMethod(ACC_PROTECTED, "put", "(Ljava/lang/Object;II"+desc(clazz)+"[Ljava/lang/String;Lorg/kepe/beancp/info/BeancpInfo;Lorg/kepe/beancp/info/BeancpInfo;Lorg/kepe/beancp/config/BeancpContext;)V", null, new String[] { "java/lang/Exception" });
    		methodVisitor.visitParameter("obj", 0);
    		methodVisitor.visitParameter("idx", 0);
    		methodVisitor.visitParameter("setIdx", 0);
    		methodVisitor.visitParameter("value", 0);
    		methodVisitor.visitParameter("key", 0);
    		methodVisitor.visitParameter("valueInfo", 0);
    		methodVisitor.visitParameter("toInfo", 0);
    		methodVisitor.visitParameter("context", 0);
    		methodVisitor.visitCode();
    		if(!keys.isEmpty()) {
    			Label label0 = new Label();
        		methodVisitor.visitLabel(label0);
        		methodVisitor.visitLineNumber(asmContext.getNextLine(), label0);
        		methodVisitor.visitVarInsn(ILOAD, 2);
    		}
    		
    		List<Label> labelList=new ArrayList<>();
    		for(String key:keys) {
    			labelList.add(new Label());
    		}
    		Label label4 = new Label();
    		if(!keys.isEmpty()) {
        		methodVisitor.visitTableSwitchInsn(0, keys.size()-1, label4,labelList.toArray(new Label[] {}));
    		}
    		for(int i=0;i<labelList.size();i++) {
    			Label label=labelList.get(i);
    			String key=keys.get(i);
    			List<BeancpSetInfo> setterList=fields.get(key).r1.getSetterList();
    			methodVisitor.visitLabel(label);
        		methodVisitor.visitLineNumber(asmContext.getNextLine(), label);
        		methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        		if(setterList.isEmpty()) {
        			methodVisitor.visitJumpInsn(GOTO, label4);
        			continue;
        		}
        		methodVisitor.visitVarInsn(ILOAD, 3);
        		List<Label> labelList2=new ArrayList<>();
        		for(BeancpSetInfo setInfo:setterList) {
        			labelList2.add(new Label());
        		}
        		Label label6 = new Label();
        		methodVisitor.visitTableSwitchInsn(0, setterList.size()-1, label6, labelList2.toArray(new Label[] {}));
        		for(int j=0;j<labelList2.size();j++) {
        			Label label2=labelList2.get(j);
        			BeancpSetInfo setInfo=setterList.get(j);
        			methodVisitor.visitLabel(label2);
            		methodVisitor.visitLineNumber(asmContext.getNextLine(), label2);
            		methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            		boolean isSetPrim=setInfo.getInfo().isPrimitive;
            		
            		//Non null assignment always new
            		if(isGetPrim&&isSetPrim&&clazz==setInfo.getInfo().getBClass()) {
            			methodVisitor.visitVarInsn(ALOAD, 1);
            			methodVisitor.visitTypeInsn(CHECKCAST, getClassName(info.getFinalPublicClass()));
            			methodVisitor.visitVarInsn(ALOAD, 0);
            			methodVisitor.visitVarInsn(ALOAD, 1);
            			methodVisitor.visitVarInsn(ALOAD, 5+py);
            			visitLoad(methodVisitor, clazzInfo, 4);
            			methodVisitor.visitVarInsn(ALOAD, 8+py);
            			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, classpath, "filterValue", "(Ljava/lang/Object;[Ljava/lang/String;"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+"Lorg/kepe/beancp/config/BeancpContext;)"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
            			if(!isSetPrim) {
							methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(setInfo.getInfo().getBClass()));
						}
            			BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
            		}else if((!isSetValueWhenNull&&isAllwaysNew&&!isSetPrim)||(!isSetValueWhenNull&&isAllwaysNew&&isGetPrim&&!isSetPrim)) {
            			methodVisitor.visitVarInsn(ALOAD, 0);
            			methodVisitor.visitVarInsn(ALOAD, 1);
            			methodVisitor.visitVarInsn(ALOAD, 5+py);
            			methodVisitor.visitVarInsn(ALOAD, 0);
            			methodVisitor.visitFieldInsn(GETFIELD, classpath, "feature", "Lorg/kepe/beancp/config/BeancpFeature;");
            			methodVisitor.visitVarInsn(ALOAD, 6+py);
            			methodVisitor.visitVarInsn(ALOAD, 7+py);
            			methodVisitor.visitMethodInsn(INVOKESTATIC, "org/kepe/beancp/ct/BeancpConvertProvider", "of", "(Lorg/kepe/beancp/config/BeancpFeature;Lorg/kepe/beancp/info/BeancpInfo;Lorg/kepe/beancp/info/BeancpInfo;)Lorg/kepe/beancp/ct/BeancpConvertProvider;", false);
            			methodVisitor.visitVarInsn(ALOAD, 8+py);
            			visitLoad(methodVisitor, clazzInfo, 4);
            			methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(clazz):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);

            			methodVisitor.visitVarInsn(ALOAD, 8+py);
            			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, classpath, "filterValue", "(Ljava/lang/Object;[Ljava/lang/String;"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+"Lorg/kepe/beancp/config/BeancpContext;)"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
            			methodVisitor.visitVarInsn(ASTORE, 9+py);
            			Label label8 = new Label();
            			methodVisitor.visitLabel(label8);
            			methodVisitor.visitLineNumber(asmContext.getNextLine(), label8);
            			methodVisitor.visitVarInsn(ALOAD, 9+py);
            			methodVisitor.visitJumpInsn(IFNULL, label4);
            			Label label9 = new Label();
            			methodVisitor.visitLabel(label9);
            			methodVisitor.visitLineNumber(asmContext.getNextLine(), label9);
            			methodVisitor.visitVarInsn(ALOAD, 1);
            			methodVisitor.visitTypeInsn(CHECKCAST, getClassName(info.getFinalPublicClass()));
            			methodVisitor.visitVarInsn(ALOAD, 9+py);
						if(!isSetPrim) {
							methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(setInfo.getInfo().getBClass()));
						}
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
            		}else if((isSetValueWhenNull&&isAllwaysNew)||(!isSetValueWhenNull&&isAllwaysNew&&isSetPrim)||(!isAllwaysNew&&(isGetPrim|isSetPrim))) {
            			methodVisitor.visitVarInsn(ALOAD, 1);
            			methodVisitor.visitTypeInsn(CHECKCAST, getClassName(info.getFinalPublicClass()));
            			methodVisitor.visitVarInsn(ALOAD, 0);
            			methodVisitor.visitVarInsn(ALOAD, 1);
            			methodVisitor.visitVarInsn(ALOAD, 5+py);
            			methodVisitor.visitVarInsn(ALOAD, 0);
            			methodVisitor.visitFieldInsn(GETFIELD, classpath, "feature", "Lorg/kepe/beancp/config/BeancpFeature;");
            			methodVisitor.visitVarInsn(ALOAD, 6+py);
            			methodVisitor.visitVarInsn(ALOAD, 7+py);
            			methodVisitor.visitMethodInsn(INVOKESTATIC, "org/kepe/beancp/ct/BeancpConvertProvider", "of", "(Lorg/kepe/beancp/config/BeancpFeature;Lorg/kepe/beancp/info/BeancpInfo;Lorg/kepe/beancp/info/BeancpInfo;)Lorg/kepe/beancp/ct/BeancpConvertProvider;", false);
            			methodVisitor.visitVarInsn(ALOAD, 8+py);
            			visitLoad(methodVisitor, clazzInfo, 4);
            			methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(clazz):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
            			methodVisitor.visitVarInsn(ALOAD, 8+py);
            			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, classpath, "filterValue", "(Ljava/lang/Object;[Ljava/lang/String;"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+"Lorg/kepe/beancp/config/BeancpContext;)"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
            			if(!isSetPrim) {
							methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(setInfo.getInfo().getBClass()));
						}
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
            		}else if(!isSetValueWhenNull&&!isAllwaysNew&&!isGetPrim&&!isSetPrim) {
            			methodVisitor.visitVarInsn(ALOAD, 6+py);
            			methodVisitor.visitVarInsn(ALOAD, 7+py);
            			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/info/BeancpInfo", "instanceOf", "(Lorg/kepe/beancp/info/BeancpInfo;)Z", false);
            			Label label14 = new Label();
            			methodVisitor.visitJumpInsn(IFEQ, label14);
            			Label label15 = new Label();
            			methodVisitor.visitLabel(label15);
            			methodVisitor.visitLineNumber(asmContext.getNextLine(), label15);
            			methodVisitor.visitVarInsn(ALOAD, 0);
            			methodVisitor.visitVarInsn(ALOAD, 1);
            			methodVisitor.visitVarInsn(ALOAD, 5+py);
            			visitLoad(methodVisitor, clazzInfo, 4);
            			methodVisitor.visitVarInsn(ALOAD, 8+py);
            			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, classpath, "filterValue", "(Ljava/lang/Object;[Ljava/lang/String;"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+"Lorg/kepe/beancp/config/BeancpContext;)"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
            			methodVisitor.visitVarInsn(ASTORE, 9+py);
            			Label label16 = new Label();
            			methodVisitor.visitLabel(label16);
            			methodVisitor.visitLineNumber(asmContext.getNextLine(), label16);
            			methodVisitor.visitVarInsn(ALOAD, 9+py);
            			methodVisitor.visitJumpInsn(IFNULL, label4);
            			Label label17 = new Label();
            			methodVisitor.visitLabel(label17);
            			methodVisitor.visitLineNumber(asmContext.getNextLine(), label17);
            			methodVisitor.visitVarInsn(ALOAD, 1);
            			methodVisitor.visitTypeInsn(CHECKCAST, getClassName(info.getFinalPublicClass()));
            			methodVisitor.visitVarInsn(ALOAD, 9+py);
            			if(!isSetPrim) {
							methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(setInfo.getInfo().getBClass()));
						}
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
            			Label label18 = new Label();
            			methodVisitor.visitLabel(label18);
            			methodVisitor.visitLineNumber(asmContext.getNextLine(), label18);
            			methodVisitor.visitJumpInsn(GOTO, label4);
            			methodVisitor.visitLabel(label14);
            			methodVisitor.visitLineNumber(asmContext.getNextLine(), label14);
            			methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            			methodVisitor.visitVarInsn(ALOAD, 0);
            			methodVisitor.visitVarInsn(ALOAD, 1);
            			methodVisitor.visitVarInsn(ALOAD, 5+py);
            			methodVisitor.visitVarInsn(ALOAD, 0);
            			methodVisitor.visitFieldInsn(GETFIELD, classpath, "feature", "Lorg/kepe/beancp/config/BeancpFeature;");
            			methodVisitor.visitVarInsn(ALOAD, 6+py);
            			methodVisitor.visitVarInsn(ALOAD, 7+py);
            			methodVisitor.visitMethodInsn(INVOKESTATIC, "org/kepe/beancp/ct/BeancpConvertProvider", "of", "(Lorg/kepe/beancp/config/BeancpFeature;Lorg/kepe/beancp/info/BeancpInfo;Lorg/kepe/beancp/info/BeancpInfo;)Lorg/kepe/beancp/ct/BeancpConvertProvider;", false);
            			methodVisitor.visitVarInsn(ALOAD, 8+py);
            			visitLoad(methodVisitor, clazzInfo, 4);
            			methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(clazz):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
            			methodVisitor.visitVarInsn(ALOAD, 8+py);
            			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, classpath, "filterValue", "(Ljava/lang/Object;[Ljava/lang/String;"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+"Lorg/kepe/beancp/config/BeancpContext;)"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
            			methodVisitor.visitVarInsn(ASTORE, 9+py);
            			Label label19 = new Label();
            			methodVisitor.visitLabel(label19);
            			methodVisitor.visitLineNumber(asmContext.getNextLine(), label19);
            			methodVisitor.visitVarInsn(ALOAD, 9+py);
            			methodVisitor.visitJumpInsn(IFNULL, label4);
            			Label label20 = new Label();
            			methodVisitor.visitLabel(label20);
            			methodVisitor.visitLineNumber(asmContext.getNextLine(), label20);
            			methodVisitor.visitVarInsn(ALOAD, 1);
            			methodVisitor.visitTypeInsn(CHECKCAST, getClassName(info.getFinalPublicClass()));
            			methodVisitor.visitVarInsn(ALOAD, 9+py);
            			if(!isSetPrim) {
							methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(setInfo.getInfo().getBClass()));
						}
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
            		}else if((isSetValueWhenNull&&!isAllwaysNew&&!isGetPrim&&!isSetPrim)) {
            			methodVisitor.visitVarInsn(ALOAD, 6+py);
            			methodVisitor.visitVarInsn(ALOAD, 7+py);
            			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/info/BeancpInfo", "instanceOf", "(Lorg/kepe/beancp/info/BeancpInfo;)Z", false);
            			Label label22 = new Label();
            			methodVisitor.visitJumpInsn(IFEQ, label22);
            			Label label23 = new Label();
            			methodVisitor.visitLabel(label23);
            			methodVisitor.visitLineNumber(asmContext.getNextLine(), label23);
            			methodVisitor.visitVarInsn(ALOAD, 1);
            			methodVisitor.visitTypeInsn(CHECKCAST, getClassName(info.getFinalPublicClass()));
            			methodVisitor.visitVarInsn(ALOAD, 0);
            			methodVisitor.visitVarInsn(ALOAD, 1);
            			methodVisitor.visitVarInsn(ALOAD, 5+py);
            			visitLoad(methodVisitor, clazzInfo, 4);
            			methodVisitor.visitVarInsn(ALOAD, 8+py);
            			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, classpath, "filterValue", "(Ljava/lang/Object;[Ljava/lang/String;"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+"Lorg/kepe/beancp/config/BeancpContext;)"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
            			if(!isSetPrim) {
							methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(setInfo.getInfo().getBClass()));
						}
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
            			Label label24 = new Label();
            			methodVisitor.visitLabel(label24);
            			methodVisitor.visitLineNumber(asmContext.getNextLine(), label24);
            			methodVisitor.visitJumpInsn(GOTO, label4);
            			methodVisitor.visitLabel(label22);
            			methodVisitor.visitLineNumber(asmContext.getNextLine(), label22);
            			methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            			methodVisitor.visitVarInsn(ALOAD, 1);
            			methodVisitor.visitTypeInsn(CHECKCAST, getClassName(info.getFinalPublicClass()));
            			methodVisitor.visitVarInsn(ALOAD, 0);
            			methodVisitor.visitVarInsn(ALOAD, 1);
            			methodVisitor.visitVarInsn(ALOAD, 5+py);
            			methodVisitor.visitVarInsn(ALOAD, 0);
            			methodVisitor.visitFieldInsn(GETFIELD, classpath, "feature", "Lorg/kepe/beancp/config/BeancpFeature;");
            			methodVisitor.visitVarInsn(ALOAD, 6+py);
            			methodVisitor.visitVarInsn(ALOAD, 7+py);
            			methodVisitor.visitMethodInsn(INVOKESTATIC, "org/kepe/beancp/ct/BeancpConvertProvider", "of", "(Lorg/kepe/beancp/config/BeancpFeature;Lorg/kepe/beancp/info/BeancpInfo;Lorg/kepe/beancp/info/BeancpInfo;)Lorg/kepe/beancp/ct/BeancpConvertProvider;", false);
            			methodVisitor.visitVarInsn(ALOAD, 8+py);
            			visitLoad(methodVisitor, clazzInfo, 4);
            			methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(clazz):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
            			methodVisitor.visitVarInsn(ALOAD, 8+py);
            			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, classpath, "filterValue", "(Ljava/lang/Object;[Ljava/lang/String;"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+"Lorg/kepe/beancp/config/BeancpContext;)"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
            			if(!isSetPrim) {
							methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(setInfo.getInfo().getBClass()));
						}
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
            		}
            		if(j==labelList2.size()-1) {
            			methodVisitor.visitLabel(label6);
                		methodVisitor.visitLineNumber(asmContext.getNextLine(), label6);
                		methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                		methodVisitor.visitJumpInsn(GOTO, label4);
            		}else {
            			Label label10 = new Label();
            			methodVisitor.visitLabel(label10);
            			methodVisitor.visitLineNumber(asmContext.getNextLine(), label10);
            			methodVisitor.visitJumpInsn(GOTO, label4);
            		}
            		
        		}
        		
    		}
    		methodVisitor.visitLabel(label4);
    		methodVisitor.visitLineNumber(asmContext.getNextLine(), label4);
    		methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
    		methodVisitor.visitInsn(RETURN);
    		methodVisitor.visitMaxs(3, 5);
    		methodVisitor.visitEnd();
    		
    	}
    	
    	for(Class<?> clazz:bases) {
    		int py=0;
    		if(clazz==double.class||clazz==long.class) {
    			py=1;
    		}
    		BeancpInfo clazzInfo=BeancpInfo.of(clazz);
    		methodVisitor = classWriter.visitMethod(ACC_PROTECTED, "get", "(Ljava/lang/Object;II"+desc(clazz)+"Lorg/kepe/beancp/info/BeancpInfo;Lorg/kepe/beancp/info/BeancpInfo;Lorg/kepe/beancp/config/BeancpContext;)"+desc(clazz), null, new String[] { "java/lang/Exception" });
    		methodVisitor.visitParameter("obj", 0);
    		methodVisitor.visitParameter("idx", 0);
    		methodVisitor.visitParameter("getIdx", 0);
    		methodVisitor.visitParameter("value", 0);
    		methodVisitor.visitParameter("valueInfo", 0);
    		methodVisitor.visitParameter("fromInfo", 0);
    		methodVisitor.visitParameter("context", 0);
    		methodVisitor.visitCode();
    		if(!keys.isEmpty()) {
    			Label label0 = new Label();
        		methodVisitor.visitLabel(label0);
        		methodVisitor.visitLineNumber(asmContext.getNextLine(), label0);
        		methodVisitor.visitVarInsn(ILOAD, 2);
    		}
    		
    		List<Label> labelList=new ArrayList<>();
    		for(String key:keys) {
    			labelList.add(new Label());
    		}
    		Label label4 = new Label();
    		if(!keys.isEmpty()) {
        		methodVisitor.visitTableSwitchInsn(0, keys.size()-1, label4,labelList.toArray(new Label[] {}));
    		}
    		for(int i=0;i<labelList.size();i++) {
    			Label label=labelList.get(i);
    			String key=keys.get(i);
    			List<BeancpGetInfo> getterList=fields.get(key).r1.getGetterList();
    			methodVisitor.visitLabel(label);
        		methodVisitor.visitLineNumber(asmContext.getNextLine(), label);
        		methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        		if(getterList.isEmpty()) {
        			methodVisitor.visitJumpInsn(GOTO, label4);
        			continue;
        		}
        		methodVisitor.visitVarInsn(ILOAD, 3);
        		List<Label> labelList2=new ArrayList<>();
        		for(BeancpGetInfo getInfo:getterList) {
        			labelList2.add(new Label());
        		}
        		Label label6 = new Label();
        		methodVisitor.visitTableSwitchInsn(0, getterList.size()-1, label6, labelList2.toArray(new Label[] {}));
        		for(int j=0;j<labelList2.size();j++) {
        			Label label2=labelList2.get(j);
        			BeancpGetInfo getInfo=getterList.get(j);
        			methodVisitor.visitLabel(label2);
            		methodVisitor.visitLineNumber(asmContext.getNextLine(), label2);
            		methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            		boolean isGetPrim=getInfo.getInfo().isPrimitive;
            		boolean isSetPrim=clazz.isPrimitive();
            		if((!isGetPrim&&!isSetPrim)) {//||(isGetPrim&&isSetPrim&&getInfo.getInfo().getBClass()!=clazz)
            			methodVisitor.visitVarInsn(ALOAD, 6+py);
                		methodVisitor.visitVarInsn(ALOAD, 5+py);
                		methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/info/BeancpInfo", "instanceOf", "(Lorg/kepe/beancp/info/BeancpInfo;)Z", false);
                		Label label7 = new Label();
                		methodVisitor.visitJumpInsn(IFEQ, label7);
                		Label label8 = new Label();
                		methodVisitor.visitLabel(label8);
                		methodVisitor.visitLineNumber(asmContext.getNextLine(), label8);
                		if(clazz.isPrimitive()&&!getInfo.getInfo().isPrimitive) {
                			methodVisitor.visitInsn(getCONST(clazz));
                		}else {
                			methodVisitor.visitVarInsn(ALOAD, 1);
                    		methodVisitor.visitTypeInsn(CHECKCAST, getClassName(info.getFinalPublicClass()));
                    		visitGetInfo(methodVisitor, getInfo);
                    		//visitPrimCastObj(methodVisitor, getInfo.getInfo());
                		}
                		visitReturn(methodVisitor, clazz);
                		methodVisitor.visitLabel(label7);
                		methodVisitor.visitLineNumber(asmContext.getNextLine(), label7);
                		methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                		methodVisitor.visitVarInsn(ALOAD, 0);
                		methodVisitor.visitFieldInsn(GETFIELD, classpath, "feature", "Lorg/kepe/beancp/config/BeancpFeature;");
                		methodVisitor.visitVarInsn(ALOAD, 6+py);
                		methodVisitor.visitVarInsn(ALOAD, 5+py);
                		methodVisitor.visitMethodInsn(INVOKESTATIC, "org/kepe/beancp/ct/BeancpConvertProvider", "of", "(Lorg/kepe/beancp/config/BeancpFeature;Lorg/kepe/beancp/info/BeancpInfo;Lorg/kepe/beancp/info/BeancpInfo;)Lorg/kepe/beancp/ct/BeancpConvertProvider;", false);
                		methodVisitor.visitVarInsn(ALOAD, 7+py);
                		methodVisitor.visitVarInsn(ALOAD, 1);
                		methodVisitor.visitTypeInsn(CHECKCAST, getClassName(info.getFinalPublicClass()));
                		visitGetInfo(methodVisitor, getInfo);
            			visitLoad(methodVisitor, clazzInfo, 4);
    					methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(clazz):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(clazz):"Ljava/lang/Object;"), false);
    					visitReturn(methodVisitor, clazz);
            		}else if(isGetPrim&&isSetPrim&&getInfo.getInfo().getBClass()==clazz) {
            			methodVisitor.visitVarInsn(ALOAD, 1);
                		methodVisitor.visitTypeInsn(CHECKCAST, getClassName(info.getFinalPublicClass()));
                		visitGetInfo(methodVisitor, getInfo);
    					visitReturn(methodVisitor, clazz);
            		}else{
            			methodVisitor.visitVarInsn(ALOAD, 0);
            			methodVisitor.visitFieldInsn(GETFIELD, classpath, "feature", "Lorg/kepe/beancp/config/BeancpFeature;");
            			methodVisitor.visitVarInsn(ALOAD, 6+py);
            			methodVisitor.visitVarInsn(ALOAD, 5+py);
            			methodVisitor.visitMethodInsn(INVOKESTATIC, "org/kepe/beancp/ct/BeancpConvertProvider", "of", "(Lorg/kepe/beancp/config/BeancpFeature;Lorg/kepe/beancp/info/BeancpInfo;Lorg/kepe/beancp/info/BeancpInfo;)Lorg/kepe/beancp/ct/BeancpConvertProvider;", false);
            			methodVisitor.visitVarInsn(ALOAD, 7+py);
            			methodVisitor.visitVarInsn(ALOAD, 1);
                		methodVisitor.visitTypeInsn(CHECKCAST, getClassName(info.getFinalPublicClass()));
                		visitGetInfo(methodVisitor, getInfo);
            			visitLoad(methodVisitor, clazzInfo, 4);
    					methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(clazz):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(clazz):"Ljava/lang/Object;"), false);
    					visitReturn(methodVisitor, clazz);
            		}
            		
            		if(j==labelList2.size()-1) {
            			methodVisitor.visitLabel(label6);
                		methodVisitor.visitLineNumber(asmContext.getNextLine(), label6);
                		methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                		methodVisitor.visitJumpInsn(GOTO, label4);
            		}
            		
        		}
        		
    		}
    		
    		methodVisitor.visitLabel(label4);
    		methodVisitor.visitLineNumber(asmContext.getNextLine(), label4);
    		methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
    		methodVisitor.visitInsn(getCONST(clazz));
    		visitReturn(methodVisitor, clazz);
    		methodVisitor.visitMaxs(2, 5);
    		methodVisitor.visitEnd();
    	}
    	{
    		methodVisitor = classWriter.visitMethod(ACC_PROTECTED, "newInstance", "(I[Ljava/lang/Object;[Lorg/kepe/beancp/info/BeancpInfo;Lorg/kepe/beancp/config/BeancpContext;)Ljava/lang/Object;", null, new String[] { "java/lang/Exception" });
    		methodVisitor.visitParameter("idx", 0);
    		methodVisitor.visitParameter("args", 0);
    		methodVisitor.visitParameter("argInfos", 0);
    		methodVisitor.visitParameter("context", 0);
    		methodVisitor.visitCode();
    		if(!inits.isEmpty()) {
    			Label label0 = new Label();
        		methodVisitor.visitLabel(label0);
        		methodVisitor.visitLineNumber(asmContext.getNextLine(), label0);
        		methodVisitor.visitVarInsn(ILOAD, 1);
    		}
    		
    		List<Label> labelList=new ArrayList<>();
    		for(BeancpInitInfo initInfo:inits) {
    			labelList.add(new Label());
    		}
    		Label label4 = new Label();
    		if(!inits.isEmpty()) {
        		methodVisitor.visitTableSwitchInsn(0, inits.size()-1, label4,labelList.toArray(new Label[] {}));
    		}
    		for(int i=0;i<labelList.size();i++) {
    			Label label=labelList.get(i);
    			BeancpInitInfo initInfo=inits.get(i);
    			methodVisitor.visitLabel(label);
    			methodVisitor.visitLineNumber(asmContext.getNextLine(), label);
    			methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
    			if(!initInfo.isProxyMode()) {
					methodVisitor.visitTypeInsn(NEW, BeancpInfoASMTool.getClassName(info.getFinalPublicClass()));
					methodVisitor.visitInsn(DUP);
				}
    			int pi=0;
    			for(Entry<String, BeancpInfo> e: initInfo.getParams().entrySet()) {
    				String paramName=e.getKey();
    				BeancpInfo paramInfo=e.getValue();
    				if(isAllwaysNew||paramInfo.isPrimitive) {
    					methodVisitor.visitVarInsn(ALOAD, 0);
        				methodVisitor.visitLdcInsn(paramName);
        				methodVisitor.visitVarInsn(ALOAD, 0);
        				methodVisitor.visitFieldInsn(GETFIELD, classpath, "feature", "Lorg/kepe/beancp/config/BeancpFeature;");
        				methodVisitor.visitVarInsn(ALOAD, 3);
        				visitInt(methodVisitor, pi);
        				//methodVisitor.visitInsn(ICONST_0);
        				methodVisitor.visitInsn(AALOAD);
        				//methodVisitor.visitIntInsn(BIPUSH, 123);
        				visitInt(methodVisitor, paramInfo.getId());
        				methodVisitor.visitMethodInsn(INVOKESTATIC, "org/kepe/beancp/info/BeancpInfo", "getInfoById", "(I)Lorg/kepe/beancp/info/BeancpInfo;", false);
        				methodVisitor.visitMethodInsn(INVOKESTATIC, "org/kepe/beancp/ct/BeancpConvertProvider", "of", "(Lorg/kepe/beancp/config/BeancpFeature;Lorg/kepe/beancp/info/BeancpInfo;Lorg/kepe/beancp/info/BeancpInfo;)Lorg/kepe/beancp/ct/BeancpConvertProvider;", false);
        				methodVisitor.visitVarInsn(ALOAD, 4);
        				methodVisitor.visitVarInsn(ALOAD, 2);
        				visitInt(methodVisitor, pi);
        				methodVisitor.visitInsn(AALOAD);
        				methodVisitor.visitInsn(getCONST(paramInfo));
    					methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;Ljava/lang/Object;"+(paramInfo.isPrimitive?BeancpInfoASMTool.desc(paramInfo.getBClass()):"Ljava/lang/Object;")+")"+(paramInfo.isPrimitive?BeancpInfoASMTool.desc(paramInfo.getBClass()):"Ljava/lang/Object;"), false);
        				methodVisitor.visitVarInsn(ALOAD, 4);
        				methodVisitor.visitMethodInsn(INVOKEVIRTUAL, classpath, "filterValue", "(Ljava/lang/String;"+(paramInfo.isPrimitive?BeancpInfoASMTool.desc(paramInfo.getBClass()):"Ljava/lang/Object;")+"Lorg/kepe/beancp/config/BeancpContext;)"+(paramInfo.isPrimitive?BeancpInfoASMTool.desc(paramInfo.getBClass()):"Ljava/lang/Object;"), false);
        				if(!paramInfo.isPrimitive) {
							methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(paramInfo.getBClass()));
						}
    				}else {
    					methodVisitor.visitVarInsn(ALOAD, 0);
    					methodVisitor.visitLdcInsn(paramName);
    					methodVisitor.visitVarInsn(ALOAD, 0);
    					methodVisitor.visitLdcInsn(paramName);
    					methodVisitor.visitVarInsn(ALOAD, 2);
    					visitInt(methodVisitor, pi);
    					methodVisitor.visitInsn(AALOAD);
    					methodVisitor.visitVarInsn(ALOAD, 3);
    					visitInt(methodVisitor, pi);
    					methodVisitor.visitInsn(AALOAD);
    					visitInt(methodVisitor, paramInfo.getId());
    					methodVisitor.visitMethodInsn(INVOKESTATIC, "org/kepe/beancp/info/BeancpInfo", "getInfoById", "(I)Lorg/kepe/beancp/info/BeancpInfo;", false);
    					methodVisitor.visitVarInsn(ALOAD, 4);
    					methodVisitor.visitMethodInsn(INVOKEVIRTUAL, classpath, "convertValue", "(Ljava/lang/String;Ljava/lang/Object;Lorg/kepe/beancp/info/BeancpInfo;Lorg/kepe/beancp/info/BeancpInfo;Lorg/kepe/beancp/config/BeancpContext;)Ljava/lang/Object;", false);
    					methodVisitor.visitVarInsn(ALOAD, 4);
        				methodVisitor.visitMethodInsn(INVOKEVIRTUAL, classpath, "filterValue", "(Ljava/lang/String;"+(paramInfo.isPrimitive?BeancpInfoASMTool.desc(paramInfo.getBClass()):"Ljava/lang/Object;")+"Lorg/kepe/beancp/config/BeancpContext;)"+(paramInfo.isPrimitive?BeancpInfoASMTool.desc(paramInfo.getBClass()):"Ljava/lang/Object;"), false);
    					if(!paramInfo.isPrimitive) {
							methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(paramInfo.getBClass()));
						}
    				}
    				pi++;
    			}
    			if(initInfo.isProxyMode()) {
					methodVisitor.visitMethodInsn(INVOKESTATIC,  BeancpInfoASMTool.getClassName(initInfo.getConstructor().getDeclaringClass()), initInfo.getConstructor().getName(), BeancpInfoASMTool.getMethodDesc(initInfo.getConstructor()), false);
				}else {
					methodVisitor.visitMethodInsn(INVOKESPECIAL, BeancpInfoASMTool.getClassName(initInfo.getConstructor().getDeclaringClass()), "<init>", BeancpInfoASMTool.getMethodDesc(initInfo.getConstructor()), false);
				}
    			methodVisitor.visitInsn(ARETURN);
    		}
    		methodVisitor.visitLabel(label4);
    		methodVisitor.visitLineNumber(asmContext.getNextLine(), label4);
    		methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
    		methodVisitor.visitInsn(ACONST_NULL);
    		methodVisitor.visitInsn(ARETURN);
    		methodVisitor.visitMaxs(2, 3);
    		methodVisitor.visitEnd();
    	}
    	{
    		methodVisitor = classWriter.visitMethod(ACC_PROTECTED, "newInstance", "(ILjava/lang/Object;"+BeancpInfoASMTool.desc(BeancpConvertMapper.class)+"Lorg/kepe/beancp/config/BeancpContext;)Ljava/lang/Object;", null, new String[] { "java/lang/Exception" });
    		methodVisitor.visitParameter("idx", 0);
    		methodVisitor.visitParameter("bean", 0);
    		methodVisitor.visitParameter("beanmapper", 0);
    		methodVisitor.visitParameter("context", 0);
    		methodVisitor.visitCode();
    		if(!inits.isEmpty()) {
    			Label label0 = new Label();
        		methodVisitor.visitLabel(label0);
        		methodVisitor.visitLineNumber(asmContext.getNextLine(), label0);
        		methodVisitor.visitVarInsn(ILOAD, 1);
    		}
    		
    		List<Label> labelList=new ArrayList<>();
    		for(BeancpInitInfo initInfo:inits) {
    			labelList.add(new Label());
    		}
    		Label label4 = new Label();
    		if(!inits.isEmpty()) {
        		methodVisitor.visitTableSwitchInsn(0, inits.size()-1, label4,labelList.toArray(new Label[] {}));
    		}
    		for(int i=0;i<labelList.size();i++) {
    			Label label=labelList.get(i);
    			BeancpInitInfo initInfo=inits.get(i);
    			methodVisitor.visitLabel(label);
    			methodVisitor.visitLineNumber(asmContext.getNextLine(), label);
    			methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
    			if(!initInfo.isProxyMode()) {
					methodVisitor.visitTypeInsn(NEW, BeancpInfoASMTool.getClassName(info.getFinalPublicClass()));
					methodVisitor.visitInsn(DUP);
				}
    			int pi=0;
    			for(Entry<String, BeancpInfo> e: initInfo.getParams().entrySet()) {
    				String paramName=e.getKey();
    				BeancpInfo paramInfo=e.getValue();
    				methodVisitor.visitVarInsn(ALOAD, 0);
    				methodVisitor.visitLdcInsn(paramName);
    				methodVisitor.visitVarInsn(ALOAD, 3);
    				methodVisitor.visitVarInsn(ALOAD, 2);
    				methodVisitor.visitLdcInsn(paramName);
    				methodVisitor.visitInsn(getCONST(paramInfo));
    				visitInt(methodVisitor, paramInfo.getId());
    				methodVisitor.visitMethodInsn(INVOKESTATIC, "org/kepe/beancp/info/BeancpInfo", "getInfoById", "(I)Lorg/kepe/beancp/info/BeancpInfo;", false);
    				methodVisitor.visitVarInsn(ALOAD, 4);
    				methodVisitor.visitMethodInsn(INVOKEVIRTUAL, BeancpInfoASMTool.getClassName(BeancpConvertMapper.class), "get", "(Ljava/lang/Object;Ljava/lang/String;"+(paramInfo.isPrimitive?BeancpInfoASMTool.desc(paramInfo.getBClass()):"Ljava/lang/Object;")+"Lorg/kepe/beancp/info/BeancpInfo;Lorg/kepe/beancp/config/BeancpContext;)"+(paramInfo.isPrimitive?BeancpInfoASMTool.desc(paramInfo.getBClass()):"Ljava/lang/Object;"), false);
    				methodVisitor.visitVarInsn(ALOAD, 4);
    				methodVisitor.visitMethodInsn(INVOKEVIRTUAL, classpath, "filterValue", "(Ljava/lang/String;"+(paramInfo.isPrimitive?BeancpInfoASMTool.desc(paramInfo.getBClass()):"Ljava/lang/Object;")+"Lorg/kepe/beancp/config/BeancpContext;)"+(paramInfo.isPrimitive?BeancpInfoASMTool.desc(paramInfo.getBClass()):"Ljava/lang/Object;"), false);
    				if(!paramInfo.isPrimitive) {
						methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(paramInfo.getBClass()));
					}
    				pi++;
    			}
    			if(initInfo.isProxyMode()) {
					methodVisitor.visitMethodInsn(INVOKESTATIC,  BeancpInfoASMTool.getClassName(initInfo.getConstructor().getDeclaringClass()), initInfo.getConstructor().getName(), BeancpInfoASMTool.getMethodDesc(initInfo.getConstructor()), false);
				}else {
					methodVisitor.visitMethodInsn(INVOKESPECIAL, BeancpInfoASMTool.getClassName(initInfo.getConstructor().getDeclaringClass()), "<init>", BeancpInfoASMTool.getMethodDesc(initInfo.getConstructor()), false);
				}
    			methodVisitor.visitInsn(ARETURN);
    		}
    		methodVisitor.visitLabel(label4);
    		methodVisitor.visitLineNumber(asmContext.getNextLine(), label4);
    		methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
    		methodVisitor.visitInsn(ACONST_NULL);
    		methodVisitor.visitInsn(ARETURN);
    		methodVisitor.visitMaxs(2, 3);
    		methodVisitor.visitEnd();
    	}
    	{
    		methodVisitor = classWriter.visitMethod(ACC_PROTECTED, "clone", "(Ljava/lang/Object;)Ljava/lang/Object;", null, new String[] { "java/lang/Exception" });
    		methodVisitor.visitCode();
    		Label label0 = new Label();
    		methodVisitor.visitLabel(label0);
    		methodVisitor.visitLineNumber(asmContext.getNextLine(), label0);
    		if(info.cloneInfo!=null&&info.cloneInfo.isUseful(feature,null)) {
    			methodVisitor.visitVarInsn(ALOAD, 1);
        		methodVisitor.visitTypeInsn(CHECKCAST, getClassName(info.getFinalPublicClass()));
        		visitMethod(methodVisitor, info.cloneInfo.getMethod());
    		}else {
    			methodVisitor.visitInsn(ACONST_NULL);
    		}
    		
    		methodVisitor.visitInsn(ARETURN);
    		
    		methodVisitor.visitMaxs(1, 2);
    		methodVisitor.visitEnd();
    	}
    	classWriter.visitEnd();
		byte[] code;
		if(checkClass) {
			code = ((ClassWriter)classWriter.getDelegate()).toByteArray();
		}else {
			code = ((ClassWriter)classWriter).toByteArray();
		}
        Class<? extends BeancpConvertASMProvider> clazz=(Class<? extends BeancpConvertASMProvider>) CLASS_LOADER.defineClassPublic(null, code, 0, code.length);
//        try {
//			Textifier.main(new String[]{clazz.getName()});
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		try {
			return (BeancpConvertMapper) clazz.getConstructors()[0].newInstance(info, feature, getKeys, setKeys,fields,keys,inits);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
//	for(Class<?> clazz:bases) {
//		methodVisitor = classWriter.visitMethod(ACC_PROTECTED, "put", "(Ljava/lang/Object;II"+desc(clazz)+")V", null, new String[] { "java/lang/Exception" });
//		methodVisitor.visitParameter("obj", 0);
//		methodVisitor.visitParameter("idx", 0);
//		methodVisitor.visitParameter("setIdx", 0);
//		methodVisitor.visitParameter("value", 0);
//		methodVisitor.visitCode();
//		if(!keys.isEmpty()) {
//			Label label0 = new Label();
//    		methodVisitor.visitLabel(label0);
//    		methodVisitor.visitLineNumber(asmContext.getNextLine(), label0);
//    		methodVisitor.visitVarInsn(ILOAD, 2);
//		}
//		
//		List<Label> labelList=new ArrayList<>();
//		for(String key:keys) {
//			labelList.add(new Label());
//		}
//		Label label4 = new Label();
//		if(!keys.isEmpty()) {
//    		methodVisitor.visitTableSwitchInsn(0, keys.size()-1, label4,labelList.toArray(new Label[] {}));
//		}
//		for(int i=0;i<labelList.size();i++) {
//			Label label=labelList.get(i);
//			String key=keys.get(i);
//			List<BeancpSetInfo> setterList=fields.get(key).r1.getSetterList();
//			methodVisitor.visitLabel(label);
//    		methodVisitor.visitLineNumber(asmContext.getNextLine(), label);
//    		methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//    		if(setterList.isEmpty()) {
//    			methodVisitor.visitJumpInsn(GOTO, label4);
//    			continue;
//    		}
//    		methodVisitor.visitVarInsn(ILOAD, 3);
//    		List<Label> labelList2=new ArrayList<>();
//    		for(BeancpSetInfo setInfo:setterList) {
//    			labelList2.add(new Label());
//    		}
//    		Label label6 = new Label();
//    		methodVisitor.visitTableSwitchInsn(0, setterList.size()-1, label6, labelList2.toArray(new Label[] {}));
//    		for(int j=0;j<labelList2.size();j++) {
//    			Label label2=labelList2.get(j);
//    			BeancpSetInfo setInfo=setterList.get(j);
//    			methodVisitor.visitLabel(label2);
//        		methodVisitor.visitLineNumber(asmContext.getNextLine(), label2);
//        		methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//        		methodVisitor.visitVarInsn(ALOAD, 1);
//        		methodVisitor.visitTypeInsn(CHECKCAST, getClassName(info.getFinalPublicClass()));
//        		if(clazz.isPrimitive()) {
//        			if(setInfo.getInfo().getBType()==clazz) {
//        				visitLoad(methodVisitor, setInfo.getInfo(), 4);
//        			}else {
//        				methodVisitor.visitInsn(getCONST(setInfo.getInfo()));
//        			}
//        		}else {
//        			methodVisitor.visitVarInsn(ALOAD, 4);
//            		visitCast(methodVisitor, setInfo.getInfo());
//        		}
//        		visitSetInfo(methodVisitor, setInfo);
//        		if(j==labelList2.size()-1) {
//        			methodVisitor.visitLabel(label6);
//            		methodVisitor.visitLineNumber(asmContext.getNextLine(), label6);
//            		methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//            		methodVisitor.visitJumpInsn(GOTO, label4);
//        		}else {
//        			Label label10 = new Label();
//        			methodVisitor.visitLabel(label10);
//        			methodVisitor.visitLineNumber(asmContext.getNextLine(), label10);
//        			methodVisitor.visitJumpInsn(GOTO, label4);
//        		}
//        		
//    		}
//    		
//		}
//		methodVisitor.visitLabel(label4);
//		methodVisitor.visitLineNumber(asmContext.getNextLine(), label4);
//		methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//		methodVisitor.visitInsn(RETURN);
//		methodVisitor.visitMaxs(3, 5);
//		methodVisitor.visitEnd();
//		
//	}
//	
//	for(Class<?> clazz:bases) {
//		methodVisitor = classWriter.visitMethod(ACC_PROTECTED, "get", "(Ljava/lang/Object;II"+desc(clazz)+")"+desc(clazz), null, new String[] { "java/lang/Exception" });
//		methodVisitor.visitParameter("obj", 0);
//		methodVisitor.visitParameter("idx", 0);
//		methodVisitor.visitParameter("getIdx", 0);
//		methodVisitor.visitParameter("value", 0);
//		methodVisitor.visitCode();
//		if(!keys.isEmpty()) {
//			Label label0 = new Label();
//    		methodVisitor.visitLabel(label0);
//    		methodVisitor.visitLineNumber(asmContext.getNextLine(), label0);
//    		methodVisitor.visitVarInsn(ILOAD, 2);
//		}
//		
//		List<Label> labelList=new ArrayList<>();
//		for(String key:keys) {
//			labelList.add(new Label());
//		}
//		Label label4 = new Label();
//		if(!keys.isEmpty()) {
//    		methodVisitor.visitTableSwitchInsn(0, keys.size()-1, label4,labelList.toArray(new Label[] {}));
//		}
//		for(int i=0;i<labelList.size();i++) {
//			Label label=labelList.get(i);
//			String key=keys.get(i);
//			List<BeancpGetInfo> getterList=fields.get(key).r1.getGetterList();
//			methodVisitor.visitLabel(label);
//    		methodVisitor.visitLineNumber(asmContext.getNextLine(), label);
//    		methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//    		if(getterList.isEmpty()) {
//    			methodVisitor.visitJumpInsn(GOTO, label4);
//    			continue;
//    		}
//    		methodVisitor.visitVarInsn(ILOAD, 3);
//    		List<Label> labelList2=new ArrayList<>();
//    		for(BeancpGetInfo getInfo:getterList) {
//    			labelList2.add(new Label());
//    		}
//    		Label label6 = new Label();
//    		methodVisitor.visitTableSwitchInsn(0, getterList.size()-1, label6, labelList2.toArray(new Label[] {}));
//    		for(int j=0;j<labelList2.size();j++) {
//    			Label label2=labelList2.get(j);
//    			BeancpGetInfo getInfo=getterList.get(j);
//    			methodVisitor.visitLabel(label2);
//        		methodVisitor.visitLineNumber(asmContext.getNextLine(), label2);
//        		methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//        		if(clazz.isPrimitive()) {
//        			if(getInfo.getInfo().getBType()==clazz) {
//        				visitGetInfo(methodVisitor, getInfo);
//        			}else {
//        				methodVisitor.visitInsn(getCONST(clazz));
//        			}
//        		}else {
//        			methodVisitor.visitVarInsn(ALOAD, 1);
//            		methodVisitor.visitTypeInsn(CHECKCAST, getClassName(info.getFinalPublicClass()));
//            		visitGetInfo(methodVisitor, getInfo);
//            		visitPrimCastObj(methodVisitor, getInfo.getInfo());
//        		}
//        		visitReturn(methodVisitor, getInfo.getInfo());
//        		if(j==labelList2.size()-1) {
//        			methodVisitor.visitLabel(label6);
//            		methodVisitor.visitLineNumber(asmContext.getNextLine(), label6);
//            		methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//            		methodVisitor.visitJumpInsn(GOTO, label4);
//        		}
//        		
//    		}
//    		
//		}
//		
//		methodVisitor.visitLabel(label4);
//		methodVisitor.visitLineNumber(asmContext.getNextLine(), label4);
//		methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//		methodVisitor.visitInsn(getCONST(clazz));
//		visitReturn(methodVisitor, clazz);
//		methodVisitor.visitMaxs(2, 5);
//		methodVisitor.visitEnd();
//	}
	
	private static Class loadClassByClassLoader(ClassLoader loader,String className,byte[] b) {
	    // Override defineClass (as it is protected) and define the class.
	    Class clazz = null;
	    try {
	      Class cls = Class.forName("java.lang.ClassLoader");
	      java.lang.reflect.Method method =
	    		  cls.getDeclaredMethod(
	              "defineClass", 
	              new Class[] { String.class, byte[].class, int.class, int.class });

	      // Protected method invocation.
	      method.setAccessible(true);
	      try {
	        Object[] args = 
	            new Object[] { className, b, new Integer(0), new Integer(b.length)};
	        clazz = (Class) method.invoke(loader, args);
	      } finally {
	        method.setAccessible(false);
	      }
	    } catch (Exception e) {
	      throw new BeancpException(e.getMessage(),e);
	    }
	    return clazz;
	  }
    public static String getClassName(Class<?> clazz) {
    	String type = typeMapping.get(clazz);
        if (type != null) {
            return type;
        }

        if (clazz.isArray()) {
            return "[" + desc(clazz.getComponentType());
        }
        String str = clazz.getName().replace('.', '/');
        return str;
    }
    public static String desc(Class<?> clazz) {
        String desc = descMapping.get(clazz);
        if (desc != null) {
            return desc;
        }

        if (clazz.isArray()) {
            Class<?> componentType = clazz.getComponentType();
            return "[" + desc(componentType);
        }

        String className = clazz.getName();
        char[] chars = descCacheRef.getAndSet(null);
        if (chars == null) {
            chars = new char[512];
        }
        chars[0] = 'L';
        className.getChars(0, className.length(), chars, 1);
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] == '.') {
                chars[i] = '/';
            }
        }
        chars[className.length() + 1] = ';';

        String str = new String(chars, 0, className.length() + 2);
        descCacheRef.compareAndSet(null, chars);
        return str;
    }
    public static String getMethodDesc(Executable method) {
    	return getMethodDesc(method,true);
    }
    public static String getMethodDesc(Executable method,boolean invoke) {
		Class<?>[] types=method.getParameterTypes();
		StringBuffer sb=new StringBuffer();
		sb.append("(");
		for(Class<?> tclazz:types) {
			sb.append(desc(tclazz));
		}
		sb.append(")");
		if(method instanceof Method) {
			sb.append(desc(((Method)method).getReturnType()));
		}else {
			if(invoke) {
				sb.append("V");
			}else {
				sb.append(desc(method.getDeclaringClass()));
			}
			
		}
		return sb.toString();
	}
    public static String getGetProxyMethodDesc(BeancpGetInfo getInfo) {
		StringBuffer sb=new StringBuffer();
		sb.append("(").append(desc(getInfo.getParentInfo().getFinalPublicClass()));
		sb.append(")").append(desc(BeancpBeanTool.getFinalPublicClass(getInfo.getMethod()!=null?getInfo.getMethod().getReturnType():getInfo.getField().getType())));
		return sb.toString();
	}
    public static String getSetProxyMethodDesc(BeancpSetInfo setInfo) {
		StringBuffer sb=new StringBuffer();
		sb.append("(").append(desc(setInfo.getParentInfo().getFinalPublicClass())).append(desc(BeancpBeanTool.getFinalPublicClass(setInfo.getMethod()!=null?setInfo.getMethod().getParameterTypes()[0]:setInfo.getField().getType())));
		sb.append(")V");
		return sb.toString();
	}
    //"("+desc(setInfo.getParentInfo().getBClass())+desc(setInfo.getMethod()!=null?setInfo.getMethod().getParameterTypes()[0]:setInfo.getField().getType())+")V"
    public static String getInitProxyMethodDesc(Constructor<?> constructor) {
		Class<?>[] types=constructor.getParameterTypes();
		StringBuffer sb=new StringBuffer();
		sb.append("(");
		for(Class<?> tclazz:types) {
			sb.append(desc(BeancpBeanTool.getFinalPublicClass(tclazz)));
		}
		sb.append(")");
		sb.append(desc(BeancpBeanTool.getFinalPublicClass(constructor.getDeclaringClass())));
		return sb.toString();
	}
    
    public static void visitGetInfo(MethodVisitor methodVisitor,BeancpGetInfo getInfo) {
    	Method method=getInfo.getMethod();
    	if(method!=null) {
    		int mod=method.getModifiers();
        	if(Modifier.isStatic(mod)) {
        		methodVisitor.visitMethodInsn(INVOKESTATIC, getClassName(method.getDeclaringClass()), method.getName(), getMethodDesc(method), method.getDeclaringClass().isInterface());
        	}else {
        		methodVisitor.visitMethodInsn(INVOKEVIRTUAL, getClassName(method.getDeclaringClass()), method.getName(), getMethodDesc(method), method.getDeclaringClass().isInterface());
        	}
    	}else {
    		Field field=getInfo.getField();
    		methodVisitor.visitFieldInsn(GETFIELD, getClassName(field.getDeclaringClass()), field.getName(), desc(field.getType()));
    	}
    }
    
    public static void visitSetInfo(MethodVisitor methodVisitor,BeancpSetInfo setInfo) {
    	Method method=setInfo.getMethod();
    	if(method!=null) {
    		int mod=method.getModifiers();
        	if(Modifier.isStatic(mod)) {
        		methodVisitor.visitMethodInsn(INVOKESTATIC, getClassName(method.getDeclaringClass()), method.getName(), getMethodDesc(method), method.getDeclaringClass().isInterface());
        	}else {
        		methodVisitor.visitMethodInsn(INVOKEVIRTUAL, getClassName(method.getDeclaringClass()), method.getName(), getMethodDesc(method), method.getDeclaringClass().isInterface());
        	}
    	}else {
    		Field field=setInfo.getField();
    		methodVisitor.visitFieldInsn(PUTFIELD, getClassName(field.getDeclaringClass()), field.getName(), desc(field.getType()));
    	}
    }
    public static void visitInt(MethodVisitor methodVisitor,int i) {
    	visitInt(methodVisitor, i, false);
    }
    public static void visitInt(MethodVisitor methodVisitor,int i,boolean si) {
    	if(i==-1) {
    		methodVisitor.visitInsn(ICONST_M1);
    	}else if(i==0) {
    		methodVisitor.visitInsn(ICONST_0);
    	}else if(i==1) {
    		methodVisitor.visitInsn(ICONST_1);
    	}else if(i==2) {
    		methodVisitor.visitInsn(ICONST_2);
    	}else if(i==3) {
    		methodVisitor.visitInsn(ICONST_3);
    	}else if(i==4) {
    		methodVisitor.visitInsn(ICONST_4);
    	}else if(i==5) {
    		methodVisitor.visitInsn(ICONST_5);
    	}else {
    		if(si) {
        		methodVisitor.visitIntInsn(SIPUSH, i);
        	}else {
        		methodVisitor.visitIntInsn(BIPUSH, i);
        	}
    	}
    	
    	
    }
    public static void visitPrimCastObj(MethodVisitor methodVisitor,BeancpInfo info) {
    	if(info.isPrimitive) {
    		Type type=info.getBType();
    		if(Long.TYPE==type) {
    	    	methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
    		}else if(Boolean.TYPE==type) {
    	    	methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;", false);
    		}else if(Character.TYPE==type) {
    	    	methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;", false);
    		}else if(Byte.TYPE==type) {
    	    	methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
    		}else if(Short.TYPE==type) {
    	    	methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;", false);
    		}else if(Integer.TYPE==type) {
    	    	methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
    		}else if(Float.TYPE==type) {
    	    	methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;", false);
    		}else if(Double.TYPE==type) {
    	    	methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
    		}
    	}
    }
    public static void visitCast(MethodVisitor methodVisitor,BeancpInfo info) {
    	visitCast(methodVisitor,info,true,true);
    }
    public static void visitCast(MethodVisitor methodVisitor,BeancpInfo info,boolean needCastFirst,boolean isToValue) {
    	if(needCastFirst) {
    		if(info.isPrimitive) {
    			Type type=info.getBType();
        		if(Long.TYPE==type) {
    				methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(Long.class));
        		}else if(Boolean.TYPE==type) {
    				methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(Boolean.class));
        		}else if(Character.TYPE==type) {
    				methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(Character.class));
        		}else if(Byte.TYPE==type) {
    				methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(Byte.class));
        		}else if(Short.TYPE==type) {
    				methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(Short.class));
        		}else if(Integer.TYPE==type) {
    				methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(Integer.class));
        		}else if(Float.TYPE==type) {
    				methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(Float.class));
        		}else if(Double.TYPE==type) {
    				methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(Double.class));
        		}
    		}else {
				methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(info.getFinalPublicClass()));
    		}
    	}
    	if(isToValue&&info.isPrimitive) {
    		Type type=info.getBType();
    		if(Long.TYPE==type) {
    			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J", false);
    		}else if(Boolean.TYPE==type) {
    			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z", false);
    		}else if(Character.TYPE==type) {
    			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Character", "charValue", "()C", false);
    		}else if(Byte.TYPE==type) {
    			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Byte", "byteValue", "()B", false);
    		}else if(Short.TYPE==type) {
    			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Short", "shortValue", "()S", false);
    		}else if(Integer.TYPE==type) {
    			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
    		}else if(Float.TYPE==type) {
    			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F", false);
    		}else if(Double.TYPE==type) {
    			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D", false);
    		}
    	}
    }
    public static void visitMethod(MethodVisitor methodVisitor,Method method) {
    	int mod=method.getModifiers();
    	if(Modifier.isStatic(mod)) {
    		methodVisitor.visitMethodInsn(INVOKESTATIC, getClassName(method.getDeclaringClass()), method.getName(), getMethodDesc(method), method.getDeclaringClass().isInterface());
    	}else {
    		methodVisitor.visitMethodInsn(INVOKEVIRTUAL, getClassName(method.getDeclaringClass()), method.getName(), getMethodDesc(method), method.getDeclaringClass().isInterface());
    	}
    }
    public static void visitReturn(MethodVisitor methodVisitor,BeancpInfo info) {
    	if(info.isPrimitive) {
    		Type type=info.getBType();
    		if(Long.TYPE==type) {
        		methodVisitor.visitInsn(LRETURN);
    		}else if(Boolean.TYPE==type) {
        		methodVisitor.visitInsn(IRETURN);
    		}else if(Character.TYPE==type) {
        		methodVisitor.visitInsn(IRETURN);
    		}else if(Byte.TYPE==type) {
        		methodVisitor.visitInsn(IRETURN);
    		}else if(Short.TYPE==type) {
        		methodVisitor.visitInsn(IRETURN);
    		}else if(Integer.TYPE==type) {
        		methodVisitor.visitInsn(IRETURN);
    		}else if(Float.TYPE==type) {
    			methodVisitor.visitInsn(FRETURN);
    		}else if(Double.TYPE==type) {
    			methodVisitor.visitInsn(DRETURN);
    		}else {
    			methodVisitor.visitInsn(IRETURN);
    		}
    	}else {
    		methodVisitor.visitInsn(ARETURN);
    	}
    }
    public static void visitReturn(MethodVisitor methodVisitor,Class clazz) {
    	if(clazz.isPrimitive()) {
    		Type type=clazz;
    		if(Long.TYPE==type) {
        		methodVisitor.visitInsn(LRETURN);
    		}else if(Boolean.TYPE==type) {
        		methodVisitor.visitInsn(IRETURN);
    		}else if(Character.TYPE==type) {
        		methodVisitor.visitInsn(IRETURN);
    		}else if(Byte.TYPE==type) {
        		methodVisitor.visitInsn(IRETURN);
    		}else if(Short.TYPE==type) {
        		methodVisitor.visitInsn(IRETURN);
    		}else if(Integer.TYPE==type) {
        		methodVisitor.visitInsn(IRETURN);
    		}else if(Float.TYPE==type) {
    			methodVisitor.visitInsn(FRETURN);
    		}else if(Double.TYPE==type) {
    			methodVisitor.visitInsn(DRETURN);
    		}else {
    			methodVisitor.visitInsn(IRETURN);
    		}
    	}else {
    		methodVisitor.visitInsn(ARETURN);
    	}
    }
    public static void visitLoad(MethodVisitor methodVisitor,BeancpInfo info,int index) {
    	if(info.isPrimitive) {
    		Type type=info.getBType();
    		if(Long.TYPE==type) {
    			methodVisitor.visitVarInsn(LLOAD, index);;
    		}else if(Boolean.TYPE==type) {
    			methodVisitor.visitVarInsn(ILOAD, index);;
    		}else if(Character.TYPE==type) {
    			methodVisitor.visitVarInsn(ILOAD, index);;
    		}else if(Byte.TYPE==type) {
    			methodVisitor.visitVarInsn(ILOAD, index);;
    		}else if(Short.TYPE==type) {
    			methodVisitor.visitVarInsn(ILOAD, index);;
    		}else if(Integer.TYPE==type) {
    			methodVisitor.visitVarInsn(ILOAD, index);;
    		}else if(Float.TYPE==type) {
    			methodVisitor.visitVarInsn(FLOAD, index);;
    		}else if(Double.TYPE==type) {
    			methodVisitor.visitVarInsn(DLOAD, index);;
    		}else {
    			methodVisitor.visitVarInsn(ILOAD, index);
    		}
			
    	}else {
    		methodVisitor.visitVarInsn(ALOAD, index);
    	}
    }
    public static int getCONST(BeancpInfo info) {
    	if(info.isPrimitive) {
    		Type type=info.getBType();
    		if(Long.TYPE==type) {
    			return LCONST_0;
    		}else if(Boolean.TYPE==type) {
    			return ICONST_0;
    		}else if(Character.TYPE==type) {
    			return ICONST_0;
    		}else if(Byte.TYPE==type) {
    			return ICONST_0;
    		}else if(Short.TYPE==type) {
    			return ICONST_0;
    		}else if(Integer.TYPE==type) {
    			return ICONST_0;
    		}else if(Float.TYPE==type) {
    			return FCONST_0;
    		}else if(Double.TYPE==type) {
    			return DCONST_0;
    		}
    		return ICONST_0;
    	}else {
    		return ACONST_NULL;
    	}
    }
    public static int getCONST(Class clazz) {
    	if(clazz.isPrimitive()) {
    		Type type=clazz;
    		if(Long.TYPE==type) {
    			return LCONST_0;
    		}else if(Boolean.TYPE==type) {
    			return ICONST_0;
    		}else if(Character.TYPE==type) {
    			return ICONST_0;
    		}else if(Byte.TYPE==type) {
    			return ICONST_0;
    		}else if(Short.TYPE==type) {
    			return ICONST_0;
    		}else if(Integer.TYPE==type) {
    			return ICONST_0;
    		}else if(Float.TYPE==type) {
    			return FCONST_0;
    		}else if(Double.TYPE==type) {
    			return DCONST_0;
    		}
    		return ICONST_0;
    	}else {
    		return ACONST_NULL;
    	}
    }
    
    
}
