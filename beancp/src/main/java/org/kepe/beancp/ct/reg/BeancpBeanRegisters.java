package org.kepe.beancp.ct.reg;

import static org.objectweb.asm.Opcodes.*;
import static org.objectweb.asm.Opcodes.ACONST_NULL;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ARETURN;
import static org.objectweb.asm.Opcodes.ASTORE;
import static org.objectweb.asm.Opcodes.CHECKCAST;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.IFNONNULL;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.RETURN;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.BeancpConvertProvider;
import org.kepe.beancp.ct.asm.BeancpInfoASMTool;
import org.kepe.beancp.ct.asm.MethodASMContext;
import org.kepe.beancp.ct.convert.BeancpConvertMapper;
import org.kepe.beancp.ct.convert.BeancpConvertProviderTool;
import org.kepe.beancp.ct.converter.BeancpConverterInfo;
import org.kepe.beancp.ct.invocation.BeancpInvocation;
import org.kepe.beancp.ct.invocation.BeancpInvocationImp;
import org.kepe.beancp.ct.invocation.BeancpInvocationOO;
import org.kepe.beancp.ct.itf.BeancpASMConverter;
import org.kepe.beancp.ct.itf.BeancpConverter;
import org.kepe.beancp.ct.itf.BeancpCustomConverter;
import org.kepe.beancp.info.BeancpFieldInfo;
import org.kepe.beancp.info.BeancpGetInfo;
import org.kepe.beancp.info.BeancpInfo;
import org.kepe.beancp.info.BeancpInitInfo;
import org.kepe.beancp.info.BeancpSetInfo;
import org.kepe.beancp.tool.BeancpInfoMatcherTool;
import org.kepe.beancp.tool.vo.Tuple2;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class BeancpBeanRegisters {
	public static void registers() {
		registerBean2Bean(new BeancpASMConverter() {
			//BeancpInitInfo initInfo=null;
			//List<BeancpGetInfo> initGetList=null;
			//List<Tuple2<BeancpGetInfo,BeancpSetInfo>> gsetList=new ArrayList<>();
            @Override
            public int distance(BeancpFeature flag,Class fromClass,Class toClass){
                return 100;
            }
            @Override
			public void createField(MethodASMContext asmContext, BeancpFeature flag, Type fromType, Class fromClass,
					Type toType, Class toClass) {
            	BeancpInitInfo initInfo=null;
    			List<BeancpGetInfo> initGetList=null;
    			List<Tuple2<BeancpGetInfo,BeancpSetInfo>> gsetList=new ArrayList<>();
    			asmContext.setObj("gsetList", gsetList);
            	MethodVisitor methodVisitor=asmContext.getMethodVisitor();
				BeancpInfo fromInfo=BeancpInfo.of(fromType,fromClass);
				BeancpInfo toInfo=BeancpInfo.of(toType,toClass);
				if(flag.is(BeancpFeature.ACCESS_PRIVATE)||flag.is(BeancpFeature.ACCESS_PROTECTED)) {
					fromInfo.initProxyOpClass();
					toInfo.initProxyOpClass();
				}
				
				boolean isForceEquals=flag.is(BeancpFeature.SETVALUE_TYPEEQUALS);

				//确定如何初始化
				if(toInfo.inits!=null) {
					int minNum=Integer.MAX_VALUE;
					for(BeancpInitInfo initInfo1:toInfo.inits) {
						if(!initInfo1.isUseful(flag,null)) {
							continue;
						}
						if(initInfo1.order()==0) {
							initInfo=initInfo1;
							initGetList=new ArrayList<>();
							break;
						}
						Tuple2<Integer,List<BeancpGetInfo>> tuple2=initInfo1.fitDistance(flag,fromInfo);
						if(tuple2.r1>=0&&tuple2.r1<minNum) {
							initInfo=initInfo1;
							initGetList=tuple2.r2;
							minNum=tuple2.r1;
							if(tuple2.r1==0) {
								break;
							}
						}
					}
					if(initInfo==null||initGetList==null) {
						return;
					}
				}else {
					return;
				}
				if(toInfo.fields!=null&&fromInfo.fields!=null) {
					for(BeancpFieldInfo fieldInfo:toInfo.fields.values()) {
						BeancpFieldInfo fromFieldInfo=fromInfo.fields.get(fieldInfo.getName().name);
						if(fromFieldInfo!=null) {
							List<BeancpSetInfo> slist=fieldInfo.getSetterList();
							List<BeancpGetInfo> glist=fromFieldInfo.getGetterList();
							BeancpGetInfo r1=null;
							BeancpSetInfo r2=null;
							int minNum=Integer.MAX_VALUE;
							for(BeancpSetInfo sinfo:slist) {
								if(!sinfo.isUseful(flag)) {
									continue;
								}
								boolean isFinalFind=false;
								for(BeancpGetInfo ginfo:glist) {
									if(!ginfo.isUseful(flag)) {
										continue;
									}
									int num=ginfo.getInfo().distance(flag, sinfo.getInfo());
									if(num>=0&&num<minNum) {
										minNum=num;
										r1=ginfo;
										r2=sinfo;
										if(num==0) {
											isFinalFind=true;
											break;
										}
									}
								}
								if(isFinalFind) {
									break;
								}
							}
							if(r1!=null&&r2!=null) {
								if(isForceEquals&&minNum==0) {
									gsetList.add(new Tuple2<>(r1,r2));
								}else if(!isForceEquals) {
									gsetList.add(new Tuple2<>(r1,r2));
								}
							}
						}
					}
				}
				asmContext.setObj("initInfo", initInfo);
				asmContext.setObj("initGetList", initGetList);
				ClassVisitor classWriter=asmContext.getClassWriter();
				FieldVisitor fieldVisitor;
				boolean isAllwaysNew=flag.is(BeancpFeature.ALLWAYS_NEW);
				for(int i=0;i<gsetList.size();i++){
					boolean isDirectTrans=false;
					if(isAllwaysNew) {
						isDirectTrans=false;
					}else {
						isDirectTrans=gsetList.get(i).r1.getInfo().instanceOf(gsetList.get(i).r2.getInfo());
					}
					if(!isDirectTrans) {
						fieldVisitor = classWriter.visitField(ACC_PRIVATE, "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;", null, null);
						fieldVisitor.visitEnd();
					}
				}
				{
					fieldVisitor = classWriter.visitField(ACC_PRIVATE, "toMapper", BeancpInfoASMTool.desc(BeancpConvertMapper.class), null, null);
					fieldVisitor.visitEnd();
				}
			}
            @Override
			public void init(MethodASMContext asmContext, BeancpFeature flag, Type fromType, Class fromClass,
					Type toType, Class toClass) {
            	BeancpInitInfo initInfo=asmContext.getObj("initInfo");
    			List<BeancpGetInfo> initGetList=asmContext.getObj("initGetList");
    			List<Tuple2<BeancpGetInfo,BeancpSetInfo>> gsetList=asmContext.getObj("gsetList");
            	MethodVisitor methodVisitor=asmContext.getMethodVisitor();
            	boolean isAllwaysNew=flag.is(BeancpFeature.ALLWAYS_NEW);
            	for(int i=0;i<gsetList.size();i++){
            		boolean isDirectTrans=false;
					if(isAllwaysNew) {
						isDirectTrans=false;
					}else {
						isDirectTrans=gsetList.get(i).r1.getInfo().instanceOf(gsetList.get(i).r2.getInfo());
					}
					if(isDirectTrans) {
						continue;
					}
            		Label label1 = new Label();
                	methodVisitor.visitLabel(label1);
                	methodVisitor.visitLineNumber(asmContext.getNextLine(), label1);//--
                	methodVisitor.visitVarInsn(ALOAD, 0);
                	methodVisitor.visitVarInsn(ALOAD, 0);
                	methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "flag", "Lorg/kepe/beancp/config/BeancpFeature;");
                	methodVisitor.visitIntInsn(BIPUSH, gsetList.get(i).r1.getInfo().getId());
                	methodVisitor.visitMethodInsn(INVOKESTATIC, "org/kepe/beancp/info/BeancpInfo", "getInfoById", "(I)Lorg/kepe/beancp/info/BeancpInfo;", false);
                	methodVisitor.visitIntInsn(BIPUSH, gsetList.get(i).r2.getInfo().getId());
                	methodVisitor.visitMethodInsn(INVOKESTATIC, "org/kepe/beancp/info/BeancpInfo", "getInfoById", "(I)Lorg/kepe/beancp/info/BeancpInfo;", false);
                	methodVisitor.visitMethodInsn(INVOKESTATIC, "org/kepe/beancp/ct/BeancpConvertProvider", "of", "(Lorg/kepe/beancp/config/BeancpFeature;Lorg/kepe/beancp/info/BeancpInfo;Lorg/kepe/beancp/info/BeancpInfo;)Lorg/kepe/beancp/ct/BeancpConvertProvider;", false);
                	methodVisitor.visitFieldInsn(PUTFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
            	}
            	if(!initGetList.isEmpty()) {
            		Label label2 = new Label();
            		methodVisitor.visitLabel(label2);
            		methodVisitor.visitLineNumber(asmContext.getNextLine(), label2);
            		methodVisitor.visitVarInsn(ALOAD, 0);
            		methodVisitor.visitVarInsn(ALOAD, 4);
            		methodVisitor.visitVarInsn(ALOAD, 0);
            		methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "flag", "Lorg/kepe/beancp/config/BeancpFeature;");
            		methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertMapper.class), "of", "(Lorg/kepe/beancp/info/BeancpInfo;Lorg/kepe/beancp/config/BeancpFeature;)"+BeancpInfoASMTool.desc(BeancpConvertMapper.class), false);
            		methodVisitor.visitFieldInsn(PUTFIELD, asmContext.getClassPath(), "toMapper", BeancpInfoASMTool.desc(BeancpConvertMapper.class));
            	}
            	
			}
			@Override
			public void convert2New(MethodASMContext asmContext,BeancpFeature flag,Type fromType,Class fromClass,Type toType,Class toClass) {
				BeancpInitInfo initInfo=asmContext.getObj("initInfo");
    			List<BeancpGetInfo> initGetList=asmContext.getObj("initGetList");
    			List<Tuple2<BeancpGetInfo,BeancpSetInfo>> gsetList=asmContext.getObj("gsetList");
//    			for(int i=gsetList.size()-1;i>=0;i--) {
//    				
//    				if(!"key1".equals(gsetList.get(i).r2.getName().name)) {
//    					gsetList.remove(i);
//    				}else {
//    					System.out.println("擦擦擦："+gsetList.get(i).r2.getName().name);
//    				}
//    			}
    			//gsetList.clear();
				MethodVisitor methodVisitor=asmContext.getMethodVisitor();
				final int varContext=1;
				int varOriFromObj=2;
				BeancpInfo fromInfo=BeancpInfo.of(fromType,fromClass);
				BeancpInfo toInfo=BeancpInfo.of(toType,toClass); 
				if(!flag.is(BeancpFeature.ALLWAYS_NEW)&&fromInfo.instanceOf(toInfo)) {
					Label label40 = new Label();
					methodVisitor.visitLabel(label40);
					methodVisitor.visitLineNumber(asmContext.getNextLine(), label40);
					methodVisitor.visitVarInsn(ALOAD, varOriFromObj);
					methodVisitor.visitInsn(ARETURN);
					return;
//					methodVisitor.visitLabel(label69);
//					methodVisitor.visitLineNumber(31, label69);
//					methodVisitor.visitLdcInsn("\u738b\u8c6a");
//					methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "log", "(Ljava/lang/String;)V", false);
				}
				if(initInfo==null||initGetList==null) {
					Label label40 = new Label();
					methodVisitor.visitLabel(label40);
					methodVisitor.visitLineNumber(asmContext.getNextLine(), label40);
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "toInfo", "Lorg/kepe/beancp/info/BeancpInfo;");
					methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/info/BeancpInfo", "defaultInstance", "()Ljava/lang/Object;", false);
					methodVisitor.visitInsn(ARETURN);
					return;
				}
				//boolean isForceEquals=flag.is(BeancpFeature.SETVALUE_TYPEEQUALS);
				boolean isAllwaysNew=flag.is(BeancpFeature.ALLWAYS_NEW);
				boolean isSetValueWhenNull=!flag.is(BeancpFeature.SETVALUE_WHENNOTNULL);
				
				//开始初始化
				//几个gset   new一次大的
				List<Label[]> gsetTcLabelList=new ArrayList<>();
				for(Tuple2<BeancpGetInfo,BeancpSetInfo> tuple2:gsetList) {
					Label label0 = new Label();
					Label label1 = new Label();
					Label label2 = new Label();
					methodVisitor.visitTryCatchBlock(label0, label1, label2, "java/lang/Exception");
					gsetTcLabelList.add(new Label[] {label0,label1,label2});
				}
				List<Label[]> gsetTcLabelList2=new ArrayList<>();
				for(Tuple2<BeancpGetInfo,BeancpSetInfo> tuple2:gsetList) {
					Label label0 = new Label();
					Label label1 = new Label();
					Label label2 = new Label();
					methodVisitor.visitTryCatchBlock(label0, label1, label2, "java/lang/Exception");
					gsetTcLabelList2.add(new Label[] {label0,label1,label2});
				}
				Label label42 = new Label();
				Label label43 = new Label();
				Label label44 = new Label();
				methodVisitor.visitTryCatchBlock(label42, label43, label44, "java/lang/Exception");
				Label label45 = new Label();
				Label label46 = new Label();
				methodVisitor.visitTryCatchBlock(label45, label46, label44, "java/lang/Exception");
				methodVisitor.visitLabel(label42);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label42);
				methodVisitor.visitVarInsn(ALOAD, varContext);
				methodVisitor.visitJumpInsn(IFNONNULL, label45);
				Label label47 = new Label();
				methodVisitor.visitLabel(label47);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label47);
				methodVisitor.visitVarInsn(ALOAD, varOriFromObj);
				methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(fromInfo.getFinalPublicClass()));
				int varFromObj=3;
				methodVisitor.visitVarInsn(ASTORE, varFromObj);
				
				Label label48 = new Label();
				methodVisitor.visitLabel(label48);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label48);
				if(initGetList.isEmpty()) {
					if(!initInfo.isProxyMode()) {
						methodVisitor.visitTypeInsn(NEW, BeancpInfoASMTool.getClassName(toInfo.getFinalPublicClass()));
						methodVisitor.visitInsn(DUP);
					}
					for(BeancpGetInfo getInfo:initGetList) {
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
					}
					if(initInfo.isProxyMode()) {
						methodVisitor.visitMethodInsn(INVOKESTATIC,  BeancpInfoASMTool.getClassName(initInfo.getConstructor().getDeclaringClass()), initInfo.getConstructor().getName(), BeancpInfoASMTool.getMethodDesc(initInfo.getConstructor()), false);
					}else {
						methodVisitor.visitMethodInsn(INVOKESPECIAL, BeancpInfoASMTool.getClassName(initInfo.getConstructor().getDeclaringClass()), "<init>", BeancpInfoASMTool.getMethodDesc(initInfo.getConstructor()), false);
					}
				}else {
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "toMapper", BeancpInfoASMTool.desc(BeancpConvertMapper.class));
					methodVisitor.visitVarInsn(ALOAD, 1);
					methodVisitor.visitVarInsn(ALOAD, 2);
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "fromInfo", "Lorg/kepe/beancp/info/BeancpInfo;");
					methodVisitor.visitMethodInsn(INVOKEVIRTUAL, BeancpInfoASMTool.getClassName(BeancpConvertMapper.class), "newInstance", "(Lorg/kepe/beancp/config/BeancpContext;Ljava/lang/Object;Lorg/kepe/beancp/info/BeancpInfo;)Ljava/lang/Object;", false);
					methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(toInfo.getFinalPublicClass()));
				}
				
				int varToObj=4;
				methodVisitor.visitVarInsn(ASTORE, varToObj);
				int varTemp1=varToObj+1;
				int varTemp2=varToObj+2;
				int varTemp3=varToObj+3;
				Label returnLabel = new Label();
				
//
//				methodVisitor.visitMethodInsn(INVOKESPECIAL, BeancpInfoASMTool.getClassName(toInfo.getFClass()), "<init>", "()V", false);
//				methodVisitor.visitVarInsn(ASTORE, 2);
				for(int i=0;i<gsetList.size();i++){
					/**
					 * 等于类   setvalue类   判断null setvalue类   使用provider做类型转换类  代理set类
					 * get类型 set类型 是否要做类型转换   null值是否赋值  是否每次都是新对象
					 */
					Label nextLabel=returnLabel;
					boolean isLast=(i==gsetList.size()-1);
					if(!isLast) {
						nextLabel=gsetTcLabelList.get(i+1)[0];
					}
					Tuple2<BeancpGetInfo,BeancpSetInfo> tuple2=gsetList.get(i);
					BeancpGetInfo getInfo=tuple2.r1;
					BeancpSetInfo setInfo=tuple2.r2;
					Label[] labels=gsetTcLabelList.get(i);
					methodVisitor.visitLabel(labels[0]);
					methodVisitor.visitLineNumber(asmContext.getNextLine(), labels[0]);
					if(i>0) {
						methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
					}
					boolean isDirectTrans=false;
					if(isAllwaysNew) {
						isDirectTrans=false;
					}else {
						isDirectTrans=getInfo.getInfo().instanceOf(setInfo.getInfo());
					}
					boolean isGetPrim=getInfo.getInfo().isPrimitive;
					boolean isSetPrim=setInfo.getInfo().isPrimitive;
					
					if((isSetValueWhenNull&&isDirectTrans)||(!isSetValueWhenNull&&isDirectTrans&&isGetPrim)) {//null赋值、不转换
						//直接赋值
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						if(!isGetPrim&&!getInfo.isSameType()) {
							methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(setInfo.getInfo().getBClass()));
						}
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
					}else if(!isSetValueWhenNull&&isDirectTrans&&!isGetPrim) {//null赋值、不转换
						//判断null之后赋值
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitVarInsn(ASTORE, varTemp1);
						Label label54 = new Label();
						methodVisitor.visitLabel(label54);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label54);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitJumpInsn(IFNULL, nextLabel);
						Label label55 = new Label();
						methodVisitor.visitLabel(label55);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label55);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						if(!isGetPrim&&!getInfo.isSameType()) {
							methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(setInfo.getInfo().getBClass()));
						}
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
					}else if(isSetValueWhenNull&&!isDirectTrans&&!isGetPrim) {
						//类型转换后赋值
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitVarInsn(ASTORE, varTemp1);
						Label label56 = new Label();
						methodVisitor.visitLabel(label56);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label56);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "of", "(Ljava/lang/Object;)Lorg/kepe/beancp/ct/BeancpConvertProvider;", false);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						if(!isSetPrim) {
							methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(setInfo.getInfo().getBClass()));
						}
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
					}else if((isSetValueWhenNull&&!isDirectTrans&&isGetPrim)||(!isSetValueWhenNull&&!isDirectTrans&&isGetPrim&&isSetPrim)) {
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						//methodVisitor.visitInsn(POP);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						if(!isSetPrim) {
							methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(setInfo.getInfo().getBClass()));
						}
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
					}else if(!isSetValueWhenNull&&!isDirectTrans&&!isGetPrim&&!isSetPrim) {
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitVarInsn(ASTORE, varTemp1);
						Label label79 = new Label();
						methodVisitor.visitLabel(label79);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label79);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitJumpInsn(IFNULL, nextLabel);
						Label label80 = new Label();
						methodVisitor.visitLabel(label80);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label80);
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "of", "(Ljava/lang/Object;)Lorg/kepe/beancp/ct/BeancpConvertProvider;", false);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						if(!isSetPrim) {
							methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(setInfo.getInfo().getBClass()));
						}
						methodVisitor.visitVarInsn(ASTORE, varTemp2);
						Label label81 = new Label();
						methodVisitor.visitLabel(label81);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label81);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitJumpInsn(IFNULL, nextLabel);
						Label label82 = new Label();
						methodVisitor.visitLabel(label82);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label82);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
					}else if(!isSetValueWhenNull&&!isDirectTrans&&isGetPrim&&!isSetPrim) {
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						if(!isSetPrim) {
							methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(setInfo.getInfo().getBClass()));
						}
						methodVisitor.visitVarInsn(ASTORE, varTemp1);
						Label label83 = new Label();
						methodVisitor.visitLabel(label83);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label83);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitJumpInsn(IFNULL, nextLabel);
						Label label84 = new Label();
						methodVisitor.visitLabel(label84);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label84);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
					}else if(!isSetValueWhenNull&&!isDirectTrans&&!isGetPrim&&isSetPrim) {
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitVarInsn(ASTORE, varTemp1);
						Label label85 = new Label();
						methodVisitor.visitLabel(label85);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label85);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitJumpInsn(IFNULL, nextLabel);
						Label label86 = new Label();
						methodVisitor.visitLabel(label86);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label86);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "of", "(Ljava/lang/Object;)Lorg/kepe/beancp/ct/BeancpConvertProvider;", false);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						if(!isSetPrim) {
							methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(setInfo.getInfo().getBClass()));
						}
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
					}
					
					methodVisitor.visitLabel(labels[1]);
					methodVisitor.visitLineNumber(asmContext.getNextLine(), labels[1]);
					methodVisitor.visitJumpInsn(GOTO, nextLabel);
					methodVisitor.visitLabel(labels[2]);
					if(i>0) {
						methodVisitor.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {"java/lang/Exception"});
					}else {
						methodVisitor.visitFrame(Opcodes.F_FULL, 5, new Object[] {asmContext.getClassPath(), "org/kepe/beancp/config/BeancpContext", "java/lang/Object", BeancpInfoASMTool.getClassName(fromInfo.getFinalPublicClass()), BeancpInfoASMTool.getClassName(toInfo.getFinalPublicClass())}, 1, new Object[] {"java/lang/Exception"});
					}
					methodVisitor.visitVarInsn(ASTORE, varTemp1);
					Label label88 = new Label();
					methodVisitor.visitLabel(label88);
					methodVisitor.visitLineNumber(asmContext.getNextLine(), label88);
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "flag", "Lorg/kepe/beancp/config/BeancpFeature;");
					methodVisitor.visitVarInsn(ALOAD, varContext);
					methodVisitor.visitLdcInsn(setInfo.getName().name);
					methodVisitor.visitInsn(ICONST_1);
					methodVisitor.visitVarInsn(ALOAD, varTemp1);
					methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "handleException", "(Lorg/kepe/beancp/ct/BeancpConvertProvider;Lorg/kepe/beancp/config/BeancpFeature;Lorg/kepe/beancp/config/BeancpContext;Ljava/lang/String;ILjava/lang/Exception;)V", false);				
				}
				
				methodVisitor.visitLabel(returnLabel);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), returnLabel);
				if(!gsetList.isEmpty()) {
					methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
				}
				methodVisitor.visitVarInsn(ALOAD, varToObj);
				methodVisitor.visitLabel(label43);
				methodVisitor.visitInsn(ARETURN);
				
				methodVisitor.visitLabel(label45);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label45);
				if(gsetList.isEmpty()) {
					methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
				}else {
					methodVisitor.visitFrame(Opcodes.F_CHOP,2, null, 0, null);
				}
				//methodVisitor.visitFrame(Opcodes.F_FULL, 3, new Object[] {"org/kepe/beancp/ct/convert/BeancpConvertDemoProvider", "org/kepe/beancp/config/BeancpContext", "java/lang/Object"}, 0, new Object[] {});
				methodVisitor.visitVarInsn(ALOAD, varOriFromObj);
				methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(fromInfo.getFinalPublicClass()));
				methodVisitor.visitVarInsn(ASTORE, varFromObj);
				Label label60 = new Label();
				methodVisitor.visitLabel(label60);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label60);
				if(!initInfo.isProxyMode()) {
					methodVisitor.visitTypeInsn(NEW, BeancpInfoASMTool.getClassName(toInfo.getFinalPublicClass()));
					methodVisitor.visitInsn(DUP);
				}
				for(BeancpGetInfo getInfo:initGetList) {
					methodVisitor.visitVarInsn(ALOAD, varFromObj);
					BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
				}
				if(initInfo.isProxyMode()) {
					methodVisitor.visitMethodInsn(INVOKESTATIC,  BeancpInfoASMTool.getClassName(initInfo.getConstructor().getDeclaringClass()), initInfo.getConstructor().getName(), BeancpInfoASMTool.getMethodDesc(initInfo.getConstructor()), false);
				}else {
					methodVisitor.visitMethodInsn(INVOKESPECIAL, BeancpInfoASMTool.getClassName(initInfo.getConstructor().getDeclaringClass()), "<init>", BeancpInfoASMTool.getMethodDesc(initInfo.getConstructor()), false);
				}
				methodVisitor.visitVarInsn(ASTORE, varToObj);
				
				returnLabel = new Label();
				Consumer<BeancpSetInfo> visitLdc=(sinfo)->{
					for(String name:sinfo.getPossNames()) {
						methodVisitor.visitLdcInsn(name);
					}
				};
				Consumer<BeancpSetInfo> visitIsAllowSet=(sinfo)->{
					if(sinfo.getPossNames().length==2) {
						methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "isAllowSet", "(Lorg/kepe/beancp/config/BeancpContext;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;)Z", false);
					}else {
						methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "isAllowSet", "(Lorg/kepe/beancp/config/BeancpContext;Ljava/lang/Object;Ljava/lang/String;)Z", false);
					}
				};
				
				Consumer<BeancpSetInfo> visitGetValueFilter=(sinfo)->{
					if(sinfo.getPossNames().length==2) {
						methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "getValueFilter", "(Lorg/kepe/beancp/config/BeancpContext;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;)Lorg/kepe/beancp/config/BeancpValueFilter;", false);
					}else {
						methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "getValueFilter", "(Lorg/kepe/beancp/config/BeancpContext;Ljava/lang/Object;Ljava/lang/String;)Lorg/kepe/beancp/config/BeancpValueFilter;", false);
					}
				};
				Consumer<BeancpSetInfo> visitFilterValue=(sinfo)->{
					if(sinfo.getPossNames().length==2) {
						methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "filterValue", "(Lorg/kepe/beancp/config/BeancpValueFilter;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;", false);
					}else {
						methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "filterValue", "(Lorg/kepe/beancp/config/BeancpValueFilter;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;", false);
					}
				};
				for(int i=0;i<gsetList.size();i++){
					Label nextLabel=returnLabel;
					boolean isLast=(i==gsetList.size()-1);
					if(!isLast) {
						nextLabel=gsetTcLabelList2.get(i+1)[0];
					}
					Tuple2<BeancpGetInfo,BeancpSetInfo> tuple2=gsetList.get(i);
					BeancpGetInfo getInfo=tuple2.r1;
					BeancpSetInfo setInfo=tuple2.r2;
					Label[] labels=gsetTcLabelList2.get(i);
					methodVisitor.visitLabel(labels[0]);
					methodVisitor.visitLineNumber(asmContext.getNextLine(), labels[0]);
					if(i>0) {
						methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
					}
					boolean isDirectTrans=false;
					if(isAllwaysNew) {
						isDirectTrans=false;
					}else {
						isDirectTrans=getInfo.getInfo().instanceOf(setInfo.getInfo());
					}
					boolean isGetPrim=getInfo.getInfo().isPrimitive;
					boolean isSetPrim=setInfo.getInfo().isPrimitive;
					
					
					if((isSetValueWhenNull&&isDirectTrans)||(!isSetValueWhenNull&&isDirectTrans&&isGetPrim)) {//null赋值、不转换
						//直接赋值
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						visitIsAllowSet.accept(setInfo);
						methodVisitor.visitJumpInsn(IFEQ, nextLabel);
						Label label104 = new Label();
						methodVisitor.visitLabel(label104);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label104);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						visitGetValueFilter.accept(setInfo);
						methodVisitor.visitInsn(DUP);
						methodVisitor.visitVarInsn(ASTORE, varTemp1);
						Label label129 = new Label();
						methodVisitor.visitLabel(label129);
						Label label105 = new Label();
						methodVisitor.visitJumpInsn(IFNULL, label105);
						Label label106 = new Label();
						methodVisitor.visitLabel(label106);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label106);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						BeancpInfoASMTool.visitPrimCastObj(methodVisitor, setInfo.getInfo());
						visitFilterValue.accept(setInfo);
						BeancpInfoASMTool.visitCast(methodVisitor, setInfo.getInfo());
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
						Label label107 = new Label();
						methodVisitor.visitLabel(label107);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label107);
						methodVisitor.visitJumpInsn(GOTO, nextLabel);
						methodVisitor.visitLabel(label105);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label105);
						if(i==0) {
							methodVisitor.visitFrame(Opcodes.F_APPEND,3, new Object[] {BeancpInfoASMTool.getClassName(fromInfo.getFinalPublicClass()), BeancpInfoASMTool.getClassName(toInfo.getFinalPublicClass()), "org/kepe/beancp/config/BeancpValueFilter"}, 0, null);
						}else {
							methodVisitor.visitFrame(Opcodes.F_APPEND,1, new Object[] {"org/kepe/beancp/config/BeancpValueFilter"}, 0, null);

						}
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						if(!isGetPrim&&!getInfo.isSameType()) {
							methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(setInfo.getInfo().getBClass()));
						}
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
					}
					else if(!isSetValueWhenNull&&isDirectTrans&&!isGetPrim&&!isSetPrim) {
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						visitIsAllowSet.accept(setInfo);
						methodVisitor.visitJumpInsn(IFEQ, nextLabel);
						Label label133 = new Label();
						methodVisitor.visitLabel(label133);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label133);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						visitGetValueFilter.accept(setInfo);
						methodVisitor.visitInsn(DUP);
						methodVisitor.visitVarInsn(ASTORE, varTemp1);
						Label label129 = new Label();
						methodVisitor.visitLabel(label129);
						Label label134 = new Label();
						methodVisitor.visitJumpInsn(IFNULL, label134);
						Label label135 = new Label();
						methodVisitor.visitLabel(label135);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label135);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						visitFilterValue.accept(setInfo);
						BeancpInfoASMTool.visitCast(methodVisitor, setInfo.getInfo());
						methodVisitor.visitVarInsn(ASTORE, varTemp2);
						Label label136 = new Label();
						methodVisitor.visitLabel(label136);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label136);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitJumpInsn(IFNULL, nextLabel);
						Label label137 = new Label();
						methodVisitor.visitLabel(label137);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label137);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
						Label label138 = new Label();
						methodVisitor.visitLabel(label138);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label138);
						methodVisitor.visitJumpInsn(GOTO, nextLabel);
						methodVisitor.visitLabel(label134);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label134);
						if(i==0) {
							methodVisitor.visitFrame(Opcodes.F_APPEND,3, new Object[] {BeancpInfoASMTool.getClassName(fromInfo.getFinalPublicClass()), BeancpInfoASMTool.getClassName(toInfo.getFinalPublicClass()), "org/kepe/beancp/config/BeancpValueFilter"}, 0, null);
						}else {
							methodVisitor.visitFrame(Opcodes.F_APPEND,1, new Object[] {"org/kepe/beancp/config/BeancpValueFilter"}, 0, null);

						}
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitVarInsn(ASTORE, varTemp2);
						Label label139 = new Label();
						methodVisitor.visitLabel(label139);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label139);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitJumpInsn(IFNULL, nextLabel);
						Label label140 = new Label();
						methodVisitor.visitLabel(label140);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label140);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						if(!isGetPrim&&!getInfo.isSameType()) {
							methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(setInfo.getInfo().getBClass()));
						}
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
					}
					else if(isSetValueWhenNull&&!isDirectTrans&&!isGetPrim) {
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						visitIsAllowSet.accept(setInfo);
						methodVisitor.visitJumpInsn(IFEQ, nextLabel);
						Label label142 = new Label();
						methodVisitor.visitLabel(label142);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label142);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						visitGetValueFilter.accept(setInfo);
						methodVisitor.visitInsn(DUP);
						methodVisitor.visitVarInsn(ASTORE, varTemp1);
						Label label129 = new Label();
						methodVisitor.visitLabel(label129);
						Label label143 = new Label();
						methodVisitor.visitJumpInsn(IFNULL, label143);
						Label label144 = new Label();
						methodVisitor.visitLabel(label144);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label144);
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitVarInsn(ASTORE, varTemp2);
						Label label145 = new Label();
						methodVisitor.visitLabel(label145);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label145);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "of", "(Ljava/lang/Object;)Lorg/kepe/beancp/ct/BeancpConvertProvider;", false);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						if(setInfo.getInfo().isPrimitive) {
							BeancpInfoASMTool.visitPrimCastObj(methodVisitor, setInfo.getInfo());
						}else {
							BeancpInfoASMTool.visitCast(methodVisitor, setInfo.getInfo(),true,false);
						}
						visitFilterValue.accept(setInfo);
						BeancpInfoASMTool.visitCast(methodVisitor, setInfo.getInfo());
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
						Label label146 = new Label();
						methodVisitor.visitLabel(label146);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label146);
						methodVisitor.visitJumpInsn(GOTO, nextLabel);
						methodVisitor.visitLabel(label143);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label143);
						if(i==0) {
							methodVisitor.visitFrame(Opcodes.F_APPEND,3, new Object[] {BeancpInfoASMTool.getClassName(fromInfo.getFinalPublicClass()), BeancpInfoASMTool.getClassName(toInfo.getFinalPublicClass()), "org/kepe/beancp/config/BeancpValueFilter"}, 0, null);
						}else {
							methodVisitor.visitFrame(Opcodes.F_APPEND,1, new Object[] {"org/kepe/beancp/config/BeancpValueFilter"}, 0, null);
						}
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						if(!isSetPrim) {
							methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(setInfo.getInfo().getBClass()));
						}
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
					}
					else if((isSetValueWhenNull&&!isDirectTrans&&isGetPrim)||(!isSetValueWhenNull&&!isDirectTrans&&isGetPrim&&isSetPrim)) {
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						visitIsAllowSet.accept(setInfo);
						methodVisitor.visitJumpInsn(IFEQ, nextLabel);
						Label label159 = new Label();
						methodVisitor.visitLabel(label159);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label159);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						visitGetValueFilter.accept(setInfo);
						methodVisitor.visitInsn(DUP);
						methodVisitor.visitVarInsn(ASTORE, varTemp1);
						Label label129 = new Label();
						methodVisitor.visitLabel(label129);
						Label label160 = new Label();
						methodVisitor.visitJumpInsn(IFNULL, label160);
						Label label161 = new Label();
						methodVisitor.visitLabel(label161);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label161);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						if(setInfo.getInfo().isPrimitive) {
							BeancpInfoASMTool.visitPrimCastObj(methodVisitor, setInfo.getInfo());
						}else {
							BeancpInfoASMTool.visitCast(methodVisitor, setInfo.getInfo(),true,false);
						}
						visitFilterValue.accept(setInfo);
						BeancpInfoASMTool.visitCast(methodVisitor, setInfo.getInfo());
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
						Label label162 = new Label();
						methodVisitor.visitLabel(label162);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label162);
						methodVisitor.visitJumpInsn(GOTO, nextLabel);
						methodVisitor.visitLabel(label160);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label160);
						if(i==0) {
							methodVisitor.visitFrame(Opcodes.F_APPEND,3, new Object[] {BeancpInfoASMTool.getClassName(fromInfo.getFinalPublicClass()), BeancpInfoASMTool.getClassName(toInfo.getFinalPublicClass()), "org/kepe/beancp/config/BeancpValueFilter"}, 0, null);
						}else {
							methodVisitor.visitFrame(Opcodes.F_APPEND,1, new Object[] {"org/kepe/beancp/config/BeancpValueFilter"}, 0, null);
						}
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
					}else if(!isSetValueWhenNull&&!isDirectTrans&&!isGetPrim&&!isSetPrim) {
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						visitIsAllowSet.accept(setInfo);
						methodVisitor.visitJumpInsn(IFEQ, nextLabel);
						Label label164 = new Label();
						methodVisitor.visitLabel(label164);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label164);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						visitGetValueFilter.accept(setInfo);
						methodVisitor.visitInsn(DUP);
						methodVisitor.visitVarInsn(ASTORE, varTemp1);
						Label label129 = new Label();
						methodVisitor.visitLabel(label129);
						Label label165 = new Label();
						methodVisitor.visitJumpInsn(IFNULL, label165);
						Label label166 = new Label();
						methodVisitor.visitLabel(label166);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label166);
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitVarInsn(ASTORE, varTemp2);
						Label label167 = new Label();
						methodVisitor.visitLabel(label167);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label167);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "of", "(Ljava/lang/Object;)Lorg/kepe/beancp/ct/BeancpConvertProvider;", false);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						BeancpInfoASMTool.visitCast(methodVisitor, setInfo.getInfo(),true,false);
						visitFilterValue.accept(setInfo);
						BeancpInfoASMTool.visitCast(methodVisitor, setInfo.getInfo());
						methodVisitor.visitVarInsn(ASTORE, varTemp3);
						Label label168 = new Label();
						methodVisitor.visitLabel(label168);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label168);
						methodVisitor.visitVarInsn(ALOAD, varTemp3);
						methodVisitor.visitJumpInsn(IFNULL, nextLabel);
						Label label169 = new Label();
						methodVisitor.visitLabel(label169);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label169);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varTemp3);
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
						Label label170 = new Label();
						methodVisitor.visitLabel(label170);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label170);
						methodVisitor.visitJumpInsn(GOTO, nextLabel);
						methodVisitor.visitLabel(label165);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label165);
						if(i==0) {
							methodVisitor.visitFrame(Opcodes.F_APPEND,3, new Object[] {BeancpInfoASMTool.getClassName(fromInfo.getFinalPublicClass()), BeancpInfoASMTool.getClassName(toInfo.getFinalPublicClass()), "org/kepe/beancp/config/BeancpValueFilter"}, 0, null);
						}else {
							methodVisitor.visitFrame(Opcodes.F_APPEND,1, new Object[] {"org/kepe/beancp/config/BeancpValueFilter"}, 0, null);
						}
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitVarInsn(ASTORE, varTemp2);
						Label label171 = new Label();
						methodVisitor.visitLabel(label171);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label171);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitJumpInsn(IFNULL, nextLabel);
						Label label172 = new Label();
						methodVisitor.visitLabel(label172);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label172);
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "of", "(Ljava/lang/Object;)Lorg/kepe/beancp/ct/BeancpConvertProvider;", false);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
						methodVisitor.visitVarInsn(ASTORE, varTemp3);
						Label label173 = new Label();
						methodVisitor.visitLabel(label173);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label173);
						methodVisitor.visitVarInsn(ALOAD, varTemp3);
						methodVisitor.visitJumpInsn(IFNULL, nextLabel);
						Label label174 = new Label();
						methodVisitor.visitLabel(label174);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label174);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varTemp3);
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
					}else if(!isSetValueWhenNull&&!isDirectTrans&&isGetPrim&&!isSetPrim) {
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						visitIsAllowSet.accept(setInfo);
						methodVisitor.visitJumpInsn(IFEQ, nextLabel);
						Label label176 = new Label();
						methodVisitor.visitLabel(label176);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label176);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						visitGetValueFilter.accept(setInfo);
						methodVisitor.visitInsn(DUP);
						methodVisitor.visitVarInsn(ASTORE, varTemp1);
						Label label129 = new Label();
						methodVisitor.visitLabel(label129);
						Label label177 = new Label();
						methodVisitor.visitJumpInsn(IFNULL, label177);
						Label label178 = new Label();
						methodVisitor.visitLabel(label178);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label178);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						BeancpInfoASMTool.visitCast(methodVisitor, setInfo.getInfo(),true,false);
						visitFilterValue.accept(setInfo);
						BeancpInfoASMTool.visitCast(methodVisitor, setInfo.getInfo());
						methodVisitor.visitVarInsn(ASTORE, varTemp2);
						Label label179 = new Label();
						methodVisitor.visitLabel(label179);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label179);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitJumpInsn(IFNULL, nextLabel);
						Label label180 = new Label();
						methodVisitor.visitLabel(label180);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label180);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
						Label label181 = new Label();
						methodVisitor.visitLabel(label181);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label181);
						methodVisitor.visitJumpInsn(GOTO, nextLabel);
						methodVisitor.visitLabel(label177);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label177);
						if(i==0) {
							methodVisitor.visitFrame(Opcodes.F_APPEND,3, new Object[] {BeancpInfoASMTool.getClassName(fromInfo.getFinalPublicClass()), BeancpInfoASMTool.getClassName(toInfo.getFinalPublicClass()), "org/kepe/beancp/config/BeancpValueFilter"}, 0, null);
						}else {
							methodVisitor.visitFrame(Opcodes.F_APPEND,1, new Object[] {"org/kepe/beancp/config/BeancpValueFilter"}, 0, null);
						}
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
						methodVisitor.visitVarInsn(ASTORE, varTemp2);
						Label label182 = new Label();
						methodVisitor.visitLabel(label182);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label182);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitJumpInsn(IFNULL, nextLabel);
						Label label183 = new Label();
						methodVisitor.visitLabel(label183);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label183);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
					}else if(!isSetValueWhenNull&&!isDirectTrans&&!isGetPrim&&isSetPrim) {
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						visitIsAllowSet.accept(setInfo);
						methodVisitor.visitJumpInsn(IFEQ, nextLabel);
						Label label186 = new Label();
						methodVisitor.visitLabel(label186);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label186);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						methodVisitor.visitInsn(DUP);
						methodVisitor.visitVarInsn(ASTORE, varTemp1);
						Label label129 = new Label();
						methodVisitor.visitLabel(label129);
						Label label187 = new Label();
						methodVisitor.visitJumpInsn(IFNULL, label187);
						Label label188 = new Label();
						methodVisitor.visitLabel(label188);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label188);
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitVarInsn(ASTORE, varTemp2);
						Label label189 = new Label();
						methodVisitor.visitLabel(label189);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label189);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "of", "(Ljava/lang/Object;)Lorg/kepe/beancp/ct/BeancpConvertProvider;", false);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						BeancpInfoASMTool.visitPrimCastObj(methodVisitor, setInfo.getInfo());
						visitFilterValue.accept(setInfo);
						BeancpInfoASMTool.visitCast(methodVisitor, setInfo.getInfo());
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
						Label label190 = new Label();
						methodVisitor.visitLabel(label190);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label190);
						methodVisitor.visitJumpInsn(GOTO, nextLabel);
						methodVisitor.visitLabel(label187);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label187);
						if(i==0) {
							methodVisitor.visitFrame(Opcodes.F_APPEND,3, new Object[] {BeancpInfoASMTool.getClassName(fromInfo.getFinalPublicClass()), BeancpInfoASMTool.getClassName(toInfo.getFinalPublicClass()), "org/kepe/beancp/config/BeancpValueFilter"}, 0, null);
						}else {
							methodVisitor.visitFrame(Opcodes.F_APPEND,1, new Object[] {"org/kepe/beancp/config/BeancpValueFilter"}, 0, null);
						}
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitVarInsn(ASTORE, varTemp2);
						Label label191 = new Label();
						methodVisitor.visitLabel(label191);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label191);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitJumpInsn(IFNULL, nextLabel);
						Label label192 = new Label();
						methodVisitor.visitLabel(label192);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label192);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "of", "(Ljava/lang/Object;)Lorg/kepe/beancp/ct/BeancpConvertProvider;", false);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
					}
//					else {
//						methodVisitor.visitVarInsn(ALOAD, varFromObj);
//						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
//						methodVisitor.visitInsn(POP);
//					}
					
					methodVisitor.visitLabel(labels[1]);
					methodVisitor.visitLineNumber(asmContext.getNextLine(), labels[1]);
					methodVisitor.visitJumpInsn(GOTO, nextLabel);
					methodVisitor.visitLabel(labels[2]);
					if(i<0) {
						methodVisitor.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {"java/lang/Exception"});
					}else {
						methodVisitor.visitFrame(Opcodes.F_FULL, 5, new Object[] {asmContext.getClassPath(), "org/kepe/beancp/config/BeancpContext", "java/lang/Object", BeancpInfoASMTool.getClassName(fromInfo.getFinalPublicClass()), BeancpInfoASMTool.getClassName(toInfo.getFinalPublicClass())}, 1, new Object[] {"java/lang/Exception"});
					}
					methodVisitor.visitVarInsn(ASTORE, varTemp1);
					Label label88 = new Label();
					methodVisitor.visitLabel(label88);
					methodVisitor.visitLineNumber(asmContext.getNextLine(), label88);
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "flag", "Lorg/kepe/beancp/config/BeancpFeature;");
					methodVisitor.visitVarInsn(ALOAD, varContext);
					methodVisitor.visitLdcInsn(setInfo.getName().name);
					methodVisitor.visitInsn(ICONST_1);
					methodVisitor.visitVarInsn(ALOAD, varTemp1);
					methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "handleException", "(Lorg/kepe/beancp/ct/BeancpConvertProvider;Lorg/kepe/beancp/config/BeancpFeature;Lorg/kepe/beancp/config/BeancpContext;Ljava/lang/String;ILjava/lang/Exception;)V", false);
				}
				methodVisitor.visitLabel(returnLabel);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), returnLabel);
				if(!gsetList.isEmpty()) {
					methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
				}
				
				methodVisitor.visitVarInsn(ALOAD, varToObj);
				methodVisitor.visitLabel(label46);
				methodVisitor.visitInsn(ARETURN);
				
				methodVisitor.visitLabel(label44);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label44);
				if(gsetList.isEmpty()) {
					methodVisitor.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {"java/lang/Exception"});
				}else {
					methodVisitor.visitFrame(Opcodes.F_FULL, 3, new Object[] {asmContext.getClassPath(), "org/kepe/beancp/config/BeancpContext", "java/lang/Object"}, 1, new Object[] {"java/lang/Exception"});
				}
				methodVisitor.visitVarInsn(ASTORE, 3);
				Label label188 = new Label();
				methodVisitor.visitLabel(label188);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label188);
				methodVisitor.visitVarInsn(ALOAD, 0);
				methodVisitor.visitVarInsn(ALOAD, 0);
				methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "flag", "Lorg/kepe/beancp/config/BeancpFeature;");
				methodVisitor.visitVarInsn(ALOAD, 1);
				methodVisitor.visitLdcInsn("");
				methodVisitor.visitInsn(ICONST_0);
				methodVisitor.visitVarInsn(ALOAD, 3);
				methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "handleException", "(Lorg/kepe/beancp/ct/BeancpConvertProvider;Lorg/kepe/beancp/config/BeancpFeature;Lorg/kepe/beancp/config/BeancpContext;Ljava/lang/String;ILjava/lang/Exception;)V", false);
				Label label74 = new Label();
				methodVisitor.visitLabel(label74);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label74);
				methodVisitor.visitVarInsn(ALOAD, 0);
				methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "toInfo", "Lorg/kepe/beancp/info/BeancpInfo;");
				methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/info/BeancpInfo", "defaultInstance", "()Ljava/lang/Object;", false);
				methodVisitor.visitInsn(ARETURN);
			}

			@Override
			public void convert2Object(MethodASMContext asmContext,BeancpFeature flag,Type fromType,Class fromClass,Type toType,Class toClass) {
				BeancpInitInfo initInfo=asmContext.getObj("initInfo");
    			List<BeancpGetInfo> initGetList=asmContext.getObj("initGetList");
    			List<Tuple2<BeancpGetInfo,BeancpSetInfo>> gsetList=asmContext.getObj("gsetList");
				MethodVisitor methodVisitor=asmContext.getMethodVisitor();
				final int varContext=1;
				int varOriFromObj=2;
				final int varOriToObj=3;
				BeancpInfo fromInfo=BeancpInfo.of(fromType,fromClass);
				BeancpInfo toInfo=BeancpInfo.of(toType,toClass); 
				if(!flag.is(BeancpFeature.ALLWAYS_NEW)&&fromInfo.instanceOf(toInfo)) {
					Label label40 = new Label();
					methodVisitor.visitLabel(label40);
					methodVisitor.visitLineNumber(asmContext.getNextLine(), label40);
					methodVisitor.visitVarInsn(ALOAD, varOriFromObj);
					methodVisitor.visitInsn(ARETURN);
					return;
//					methodVisitor.visitLabel(label69);
//					methodVisitor.visitLineNumber(31, label69);
//					methodVisitor.visitLdcInsn("\u738b\u8c6a");
//					methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "log", "(Ljava/lang/String;)V", false);
				}
				if(initInfo==null||initGetList==null) {
					Label label40 = new Label();
					methodVisitor.visitLabel(label40);
					methodVisitor.visitLineNumber(asmContext.getNextLine(), label40);
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "toInfo", "Lorg/kepe/beancp/info/BeancpInfo;");
					methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/info/BeancpInfo", "defaultInstance", "()Ljava/lang/Object;", false);
					methodVisitor.visitInsn(ARETURN);
					return;
				}
				//boolean isForceEquals=flag.is(BeancpFeature.SETVALUE_TYPEEQUALS);
				boolean isAllwaysNew=flag.is(BeancpFeature.ALLWAYS_NEW);
				boolean isSetValueWhenNull=!flag.is(BeancpFeature.SETVALUE_WHENNOTNULL);
				
				//开始初始化
				//几个gset   new一次大的
				List<Label[]> gsetTcLabelList=new ArrayList<>();
				for(Tuple2<BeancpGetInfo,BeancpSetInfo> tuple2:gsetList) {
					Label label0 = new Label();
					Label label1 = new Label();
					Label label2 = new Label();
					methodVisitor.visitTryCatchBlock(label0, label1, label2, "java/lang/Exception");
					gsetTcLabelList.add(new Label[] {label0,label1,label2});
				}
				List<Label[]> gsetTcLabelList2=new ArrayList<>();
				for(Tuple2<BeancpGetInfo,BeancpSetInfo> tuple2:gsetList) {
					Label label0 = new Label();
					Label label1 = new Label();
					Label label2 = new Label();
					methodVisitor.visitTryCatchBlock(label0, label1, label2, "java/lang/Exception");
					gsetTcLabelList2.add(new Label[] {label0,label1,label2});
				}
				Label label42 = new Label();
				Label label43 = new Label();
				Label label44 = new Label();
				methodVisitor.visitTryCatchBlock(label42, label43, label44, "java/lang/Exception");
				Label label45 = new Label();
				Label label46 = new Label();
				methodVisitor.visitTryCatchBlock(label45, label46, label44, "java/lang/Exception");
				methodVisitor.visitLabel(label42);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label42);
				methodVisitor.visitVarInsn(ALOAD, varContext);
				methodVisitor.visitJumpInsn(IFNONNULL, label45);
				Label label47 = new Label();
				methodVisitor.visitLabel(label47);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label47);
				methodVisitor.visitVarInsn(ALOAD, varOriFromObj);
				methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(fromInfo.getFinalPublicClass()));
				int varFromObj=4;
				methodVisitor.visitVarInsn(ASTORE, varFromObj);
				
				Label label48 = new Label();
				methodVisitor.visitLabel(label48);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label48);
				methodVisitor.visitVarInsn(ALOAD, varOriToObj);
				methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(toInfo.getFinalPublicClass()));
				int varToObj=varFromObj+1;
				methodVisitor.visitVarInsn(ASTORE, varToObj);
				int varTemp1=varToObj+1;
				int varTemp2=varToObj+2;
				int varTemp3=varToObj+3;
				Label returnLabel = new Label();
				
//
//				methodVisitor.visitMethodInsn(INVOKESPECIAL, BeancpInfoASMTool.getClassName(toInfo.getFClass()), "<init>", "()V", false);
//				methodVisitor.visitVarInsn(ASTORE, 2);
				for(int i=0;i<gsetList.size();i++){
					/**
					 * 等于类   setvalue类   判断null setvalue类   使用provider做类型转换类  代理set类
					 * get类型 set类型 是否要做类型转换   null值是否赋值  是否每次都是新对象
					 */
					Label nextLabel=returnLabel;
					boolean isLast=(i==gsetList.size()-1);
					if(!isLast) {
						nextLabel=gsetTcLabelList.get(i+1)[0];
					}
					Tuple2<BeancpGetInfo,BeancpSetInfo> tuple2=gsetList.get(i);
					BeancpGetInfo getInfo=tuple2.r1;
					BeancpSetInfo setInfo=tuple2.r2;
					Label[] labels=gsetTcLabelList.get(i);
					methodVisitor.visitLabel(labels[0]);
					methodVisitor.visitLineNumber(asmContext.getNextLine(), labels[0]);
					if(i>0) {
						methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
					}
					boolean isDirectTrans=false;
					if(isAllwaysNew) {
						isDirectTrans=false;
					}else {
						isDirectTrans=getInfo.getInfo().instanceOf(setInfo.getInfo());
					}
					boolean isGetPrim=getInfo.getInfo().isPrimitive;
					boolean isSetPrim=setInfo.getInfo().isPrimitive;
					
					if((isSetValueWhenNull&&isDirectTrans)||(!isSetValueWhenNull&&isDirectTrans&&isGetPrim)) {//null赋值、不转换
						//直接赋值
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						if(!isGetPrim&&!getInfo.isSameType()) {
							methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(setInfo.getInfo().getBClass()));
						}
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
					}else if(!isSetValueWhenNull&&isDirectTrans&&!isGetPrim) {//null赋值、不转换
						//判断null之后赋值
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitVarInsn(ASTORE, varTemp1);
						Label label54 = new Label();
						methodVisitor.visitLabel(label54);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label54);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitJumpInsn(IFNULL, nextLabel);
						Label label55 = new Label();
						methodVisitor.visitLabel(label55);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label55);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						if(!isGetPrim&&!getInfo.isSameType()) {
							methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(setInfo.getInfo().getBClass()));
						}
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
					}else if(isSetValueWhenNull&&!isDirectTrans&&!isGetPrim) {
						//类型转换后赋值
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitVarInsn(ASTORE, varTemp1);
						Label label56 = new Label();
						methodVisitor.visitLabel(label56);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label56);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "of", "(Ljava/lang/Object;)Lorg/kepe/beancp/ct/BeancpConvertProvider;", false);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						if(!isSetPrim) {
							methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(setInfo.getInfo().getBClass()));
						}
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
					}else if((isSetValueWhenNull&&!isDirectTrans&&isGetPrim)||(!isSetValueWhenNull&&!isDirectTrans&&isGetPrim&&isSetPrim)) {
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						//methodVisitor.visitInsn(POP);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						if(!isSetPrim) {
							methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(setInfo.getInfo().getBClass()));
						}
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
					}else if(!isSetValueWhenNull&&!isDirectTrans&&!isGetPrim&&!isSetPrim) {
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitVarInsn(ASTORE, varTemp1);
						Label label79 = new Label();
						methodVisitor.visitLabel(label79);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label79);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitJumpInsn(IFNULL, nextLabel);
						Label label80 = new Label();
						methodVisitor.visitLabel(label80);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label80);
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "of", "(Ljava/lang/Object;)Lorg/kepe/beancp/ct/BeancpConvertProvider;", false);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						if(!isSetPrim) {
							methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(setInfo.getInfo().getBClass()));
						}
						methodVisitor.visitVarInsn(ASTORE, varTemp2);
						Label label81 = new Label();
						methodVisitor.visitLabel(label81);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label81);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitJumpInsn(IFNULL, nextLabel);
						Label label82 = new Label();
						methodVisitor.visitLabel(label82);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label82);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
					}else if(!isSetValueWhenNull&&!isDirectTrans&&isGetPrim&&!isSetPrim) {
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						if(!isSetPrim) {
							methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(setInfo.getInfo().getBClass()));
						}
						methodVisitor.visitVarInsn(ASTORE, varTemp1);
						Label label83 = new Label();
						methodVisitor.visitLabel(label83);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label83);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitJumpInsn(IFNULL, nextLabel);
						Label label84 = new Label();
						methodVisitor.visitLabel(label84);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label84);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
					}else if(!isSetValueWhenNull&&!isDirectTrans&&!isGetPrim&&isSetPrim) {
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitVarInsn(ASTORE, varTemp1);
						Label label85 = new Label();
						methodVisitor.visitLabel(label85);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label85);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitJumpInsn(IFNULL, nextLabel);
						Label label86 = new Label();
						methodVisitor.visitLabel(label86);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label86);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "of", "(Ljava/lang/Object;)Lorg/kepe/beancp/ct/BeancpConvertProvider;", false);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						if(!isSetPrim) {
							methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(setInfo.getInfo().getBClass()));
						}
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
					}
					
					methodVisitor.visitLabel(labels[1]);
					methodVisitor.visitLineNumber(asmContext.getNextLine(), labels[1]);
					methodVisitor.visitJumpInsn(GOTO, nextLabel);
					methodVisitor.visitLabel(labels[2]);
					if(i>0) {
						methodVisitor.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {"java/lang/Exception"});
					}else {
						methodVisitor.visitFrame(Opcodes.F_FULL, 6, new Object[] {asmContext.getClassPath(), "org/kepe/beancp/config/BeancpContext", "java/lang/Object", "java/lang/Object", BeancpInfoASMTool.getClassName(fromInfo.getFinalPublicClass()), BeancpInfoASMTool.getClassName(toInfo.getFinalPublicClass())}, 1, new Object[] {"java/lang/Exception"});
					}
					methodVisitor.visitVarInsn(ASTORE, varTemp1);
					Label label88 = new Label();
					methodVisitor.visitLabel(label88);
					methodVisitor.visitLineNumber(asmContext.getNextLine(), label88);
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "flag", "Lorg/kepe/beancp/config/BeancpFeature;");
					methodVisitor.visitVarInsn(ALOAD, varContext);
					methodVisitor.visitLdcInsn(setInfo.getName().name);
					methodVisitor.visitInsn(ICONST_1);
					methodVisitor.visitVarInsn(ALOAD, varTemp1);
					methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "handleException", "(Lorg/kepe/beancp/ct/BeancpConvertProvider;Lorg/kepe/beancp/config/BeancpFeature;Lorg/kepe/beancp/config/BeancpContext;Ljava/lang/String;ILjava/lang/Exception;)V", false);				
				}
				
				methodVisitor.visitLabel(returnLabel);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), returnLabel);
				if(!gsetList.isEmpty()) {
					methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
				}
				methodVisitor.visitVarInsn(ALOAD, varToObj);
				methodVisitor.visitLabel(label43);
				methodVisitor.visitInsn(ARETURN);
				
				methodVisitor.visitLabel(label45);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label45);
				if(gsetList.isEmpty()) {
					methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
				}else {
					methodVisitor.visitFrame(Opcodes.F_CHOP,2, null, 0, null);
				}
				//methodVisitor.visitFrame(Opcodes.F_FULL, 3, new Object[] {"org/kepe/beancp/ct/convert/BeancpConvertDemoProvider", "org/kepe/beancp/config/BeancpContext", "java/lang/Object"}, 0, new Object[] {});
				methodVisitor.visitVarInsn(ALOAD, varOriFromObj);
				methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(fromInfo.getFinalPublicClass()));
				methodVisitor.visitVarInsn(ASTORE, varFromObj);
				Label label60 = new Label();
				methodVisitor.visitLabel(label60);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label60);
				if(!initInfo.isProxyMode()) {
					methodVisitor.visitTypeInsn(NEW, BeancpInfoASMTool.getClassName(toInfo.getFinalPublicClass()));
					methodVisitor.visitInsn(DUP);
				}
				for(BeancpGetInfo getInfo:initGetList) {
					methodVisitor.visitVarInsn(ALOAD, varFromObj);
					BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
				}
				if(initInfo.isProxyMode()) {
					methodVisitor.visitMethodInsn(INVOKESTATIC,  BeancpInfoASMTool.getClassName(initInfo.getConstructor().getDeclaringClass()), initInfo.getConstructor().getName(), BeancpInfoASMTool.getMethodDesc(initInfo.getConstructor()), false);
				}else {
					methodVisitor.visitMethodInsn(INVOKESPECIAL, BeancpInfoASMTool.getClassName(initInfo.getConstructor().getDeclaringClass()), "<init>", BeancpInfoASMTool.getMethodDesc(initInfo.getConstructor()), false);
				}
				methodVisitor.visitVarInsn(ASTORE, varToObj);
				
				returnLabel = new Label();
				Consumer<BeancpSetInfo> visitLdc=(sinfo)->{
					for(String name:sinfo.getPossNames()) {
						methodVisitor.visitLdcInsn(name);
					}
				};
				Consumer<BeancpSetInfo> visitIsAllowSet=(sinfo)->{
					if(sinfo.getPossNames().length==2) {
						methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "isAllowSet", "(Lorg/kepe/beancp/config/BeancpContext;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;)Z", false);
					}else {
						methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "isAllowSet", "(Lorg/kepe/beancp/config/BeancpContext;Ljava/lang/Object;Ljava/lang/String;)Z", false);
					}
				};
				Consumer<BeancpSetInfo> visitGetValueFilter=(sinfo)->{
					if(sinfo.getPossNames().length==2) {
						methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "getValueFilter", "(Lorg/kepe/beancp/config/BeancpContext;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;)Lorg/kepe/beancp/config/BeancpValueFilter;", false);
					}else {
						methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "getValueFilter", "(Lorg/kepe/beancp/config/BeancpContext;Ljava/lang/Object;Ljava/lang/String;)Lorg/kepe/beancp/config/BeancpValueFilter;", false);
					}
				};
				Consumer<BeancpSetInfo> visitFilterValue=(sinfo)->{
					if(sinfo.getPossNames().length==2) {
						methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "filterValue", "(Lorg/kepe/beancp/config/BeancpValueFilter;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;", false);
					}else {
						methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "filterValue", "(Lorg/kepe/beancp/config/BeancpValueFilter;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;", false);
					}
				};
				for(int i=0;i<gsetList.size();i++){
					Label nextLabel=returnLabel;
					boolean isLast=(i==gsetList.size()-1);
					if(!isLast) {
						nextLabel=gsetTcLabelList2.get(i+1)[0];
					}
					Tuple2<BeancpGetInfo,BeancpSetInfo> tuple2=gsetList.get(i);
					BeancpGetInfo getInfo=tuple2.r1;
					BeancpSetInfo setInfo=tuple2.r2;
					Label[] labels=gsetTcLabelList2.get(i);
					methodVisitor.visitLabel(labels[0]);
					methodVisitor.visitLineNumber(asmContext.getNextLine(), labels[0]);
					if(i>0) {
						methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
					}
					boolean isDirectTrans=false;
					if(isAllwaysNew) {
						isDirectTrans=false;
					}else {
						isDirectTrans=getInfo.getInfo().instanceOf(setInfo.getInfo());
					}
					boolean isGetPrim=getInfo.getInfo().isPrimitive;
					boolean isSetPrim=setInfo.getInfo().isPrimitive;
					
					
					if((isSetValueWhenNull&&isDirectTrans)||(!isSetValueWhenNull&&isDirectTrans&&isGetPrim)) {//null赋值、不转换
						//直接赋值
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						visitIsAllowSet.accept(setInfo);
						methodVisitor.visitJumpInsn(IFEQ, nextLabel);
						Label label104 = new Label();
						methodVisitor.visitLabel(label104);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label104);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						visitGetValueFilter.accept(setInfo);
						methodVisitor.visitInsn(DUP);
						methodVisitor.visitVarInsn(ASTORE, varTemp1);
						Label label129 = new Label();
						methodVisitor.visitLabel(label129);
						Label label105 = new Label();
						methodVisitor.visitJumpInsn(IFNULL, label105);
						Label label106 = new Label();
						methodVisitor.visitLabel(label106);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label106);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						BeancpInfoASMTool.visitPrimCastObj(methodVisitor, setInfo.getInfo());
						visitFilterValue.accept(setInfo);
						BeancpInfoASMTool.visitCast(methodVisitor, setInfo.getInfo());
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
						Label label107 = new Label();
						methodVisitor.visitLabel(label107);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label107);
						methodVisitor.visitJumpInsn(GOTO, nextLabel);
						methodVisitor.visitLabel(label105);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label105);
						if(i==0) {
							methodVisitor.visitFrame(Opcodes.F_APPEND,3, new Object[] {BeancpInfoASMTool.getClassName(fromInfo.getFinalPublicClass()), BeancpInfoASMTool.getClassName(toInfo.getFinalPublicClass()), "org/kepe/beancp/config/BeancpValueFilter"}, 0, null);
						}else {
							methodVisitor.visitFrame(Opcodes.F_APPEND,1, new Object[] {"org/kepe/beancp/config/BeancpValueFilter"}, 0, null);

						}
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						if(!isGetPrim&&!getInfo.isSameType()) {
							methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(setInfo.getInfo().getBClass()));
						}
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
					}
					else if(!isSetValueWhenNull&&isDirectTrans&&!isGetPrim&&!isSetPrim) {
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						visitIsAllowSet.accept(setInfo);
						methodVisitor.visitJumpInsn(IFEQ, nextLabel);
						Label label133 = new Label();
						methodVisitor.visitLabel(label133);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label133);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						visitGetValueFilter.accept(setInfo);
						methodVisitor.visitInsn(DUP);
						methodVisitor.visitVarInsn(ASTORE, varTemp1);
						Label label129 = new Label();
						methodVisitor.visitLabel(label129);
						Label label134 = new Label();
						methodVisitor.visitJumpInsn(IFNULL, label134);
						Label label135 = new Label();
						methodVisitor.visitLabel(label135);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label135);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						visitFilterValue.accept(setInfo);
						BeancpInfoASMTool.visitCast(methodVisitor, setInfo.getInfo());
						methodVisitor.visitVarInsn(ASTORE, varTemp2);
						Label label136 = new Label();
						methodVisitor.visitLabel(label136);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label136);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitJumpInsn(IFNULL, nextLabel);
						Label label137 = new Label();
						methodVisitor.visitLabel(label137);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label137);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
						Label label138 = new Label();
						methodVisitor.visitLabel(label138);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label138);
						methodVisitor.visitJumpInsn(GOTO, nextLabel);
						methodVisitor.visitLabel(label134);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label134);
						if(i==0) {
							methodVisitor.visitFrame(Opcodes.F_APPEND,3, new Object[] {BeancpInfoASMTool.getClassName(fromInfo.getFinalPublicClass()), BeancpInfoASMTool.getClassName(toInfo.getFinalPublicClass()), "org/kepe/beancp/config/BeancpValueFilter"}, 0, null);
						}else {
							methodVisitor.visitFrame(Opcodes.F_APPEND,1, new Object[] {"org/kepe/beancp/config/BeancpValueFilter"}, 0, null);

						}
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitVarInsn(ASTORE, varTemp2);
						Label label139 = new Label();
						methodVisitor.visitLabel(label139);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label139);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitJumpInsn(IFNULL, nextLabel);
						Label label140 = new Label();
						methodVisitor.visitLabel(label140);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label140);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						if(!isGetPrim&&!getInfo.isSameType()) {
							methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(setInfo.getInfo().getBClass()));
						}
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
					}
					else if(isSetValueWhenNull&&!isDirectTrans&&!isGetPrim) {
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						visitIsAllowSet.accept(setInfo);
						methodVisitor.visitJumpInsn(IFEQ, nextLabel);
						Label label142 = new Label();
						methodVisitor.visitLabel(label142);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label142);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						visitGetValueFilter.accept(setInfo);
						methodVisitor.visitInsn(DUP);
						methodVisitor.visitVarInsn(ASTORE, varTemp1);
						Label label129 = new Label();
						methodVisitor.visitLabel(label129);
						Label label143 = new Label();
						methodVisitor.visitJumpInsn(IFNULL, label143);
						Label label144 = new Label();
						methodVisitor.visitLabel(label144);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label144);
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitVarInsn(ASTORE, varTemp2);
						Label label145 = new Label();
						methodVisitor.visitLabel(label145);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label145);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "of", "(Ljava/lang/Object;)Lorg/kepe/beancp/ct/BeancpConvertProvider;", false);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						if(setInfo.getInfo().isPrimitive) {
							BeancpInfoASMTool.visitPrimCastObj(methodVisitor, setInfo.getInfo());
						}else {
							BeancpInfoASMTool.visitCast(methodVisitor, setInfo.getInfo(),true,false);
						}
						visitFilterValue.accept(setInfo);
						BeancpInfoASMTool.visitCast(methodVisitor, setInfo.getInfo());
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
						Label label146 = new Label();
						methodVisitor.visitLabel(label146);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label146);
						methodVisitor.visitJumpInsn(GOTO, nextLabel);
						methodVisitor.visitLabel(label143);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label143);
						if(i==0) {
							methodVisitor.visitFrame(Opcodes.F_APPEND,3, new Object[] {BeancpInfoASMTool.getClassName(fromInfo.getFinalPublicClass()), BeancpInfoASMTool.getClassName(toInfo.getFinalPublicClass()), "org/kepe/beancp/config/BeancpValueFilter"}, 0, null);
						}else {
							methodVisitor.visitFrame(Opcodes.F_APPEND,1, new Object[] {"org/kepe/beancp/config/BeancpValueFilter"}, 0, null);
						}
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						if(!isSetPrim) {
							methodVisitor.visitTypeInsn(CHECKCAST, BeancpInfoASMTool.getClassName(setInfo.getInfo().getBClass()));
						}
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
					}
					else if((isSetValueWhenNull&&!isDirectTrans&&isGetPrim)||(!isSetValueWhenNull&&!isDirectTrans&&isGetPrim&&isSetPrim)) {
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						visitIsAllowSet.accept(setInfo);
						methodVisitor.visitJumpInsn(IFEQ, nextLabel);
						Label label159 = new Label();
						methodVisitor.visitLabel(label159);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label159);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						visitGetValueFilter.accept(setInfo);
						methodVisitor.visitInsn(DUP);
						methodVisitor.visitVarInsn(ASTORE, varTemp1);
						Label label129 = new Label();
						methodVisitor.visitLabel(label129);
						Label label160 = new Label();
						methodVisitor.visitJumpInsn(IFNULL, label160);
						Label label161 = new Label();
						methodVisitor.visitLabel(label161);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label161);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						if(setInfo.getInfo().isPrimitive) {
							BeancpInfoASMTool.visitPrimCastObj(methodVisitor, setInfo.getInfo());
						}else {
							BeancpInfoASMTool.visitCast(methodVisitor, setInfo.getInfo(),true,false);
						}
						visitFilterValue.accept(setInfo);
						BeancpInfoASMTool.visitCast(methodVisitor, setInfo.getInfo());
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
						Label label162 = new Label();
						methodVisitor.visitLabel(label162);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label162);
						methodVisitor.visitJumpInsn(GOTO, nextLabel);
						methodVisitor.visitLabel(label160);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label160);
						if(i==0) {
							methodVisitor.visitFrame(Opcodes.F_APPEND,3, new Object[] {BeancpInfoASMTool.getClassName(fromInfo.getFinalPublicClass()), BeancpInfoASMTool.getClassName(toInfo.getFinalPublicClass()), "org/kepe/beancp/config/BeancpValueFilter"}, 0, null);
						}else {
							methodVisitor.visitFrame(Opcodes.F_APPEND,1, new Object[] {"org/kepe/beancp/config/BeancpValueFilter"}, 0, null);
						}
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
					}else if(!isSetValueWhenNull&&!isDirectTrans&&!isGetPrim&&!isSetPrim) {
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						visitIsAllowSet.accept(setInfo);
						methodVisitor.visitJumpInsn(IFEQ, nextLabel);
						Label label164 = new Label();
						methodVisitor.visitLabel(label164);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label164);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						visitGetValueFilter.accept(setInfo);
						methodVisitor.visitInsn(DUP);
						methodVisitor.visitVarInsn(ASTORE, varTemp1);
						Label label129 = new Label();
						methodVisitor.visitLabel(label129);
						Label label165 = new Label();
						methodVisitor.visitJumpInsn(IFNULL, label165);
						Label label166 = new Label();
						methodVisitor.visitLabel(label166);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label166);
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitVarInsn(ASTORE, varTemp2);
						Label label167 = new Label();
						methodVisitor.visitLabel(label167);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label167);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "of", "(Ljava/lang/Object;)Lorg/kepe/beancp/ct/BeancpConvertProvider;", false);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						BeancpInfoASMTool.visitCast(methodVisitor, setInfo.getInfo(),true,false);
						visitFilterValue.accept(setInfo);
						BeancpInfoASMTool.visitCast(methodVisitor, setInfo.getInfo());
						methodVisitor.visitVarInsn(ASTORE, varTemp3);
						Label label168 = new Label();
						methodVisitor.visitLabel(label168);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label168);
						methodVisitor.visitVarInsn(ALOAD, varTemp3);
						methodVisitor.visitJumpInsn(IFNULL, nextLabel);
						Label label169 = new Label();
						methodVisitor.visitLabel(label169);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label169);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varTemp3);
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
						Label label170 = new Label();
						methodVisitor.visitLabel(label170);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label170);
						methodVisitor.visitJumpInsn(GOTO, nextLabel);
						methodVisitor.visitLabel(label165);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label165);
						if(i==0) {
							methodVisitor.visitFrame(Opcodes.F_APPEND,3, new Object[] {BeancpInfoASMTool.getClassName(fromInfo.getFinalPublicClass()), BeancpInfoASMTool.getClassName(toInfo.getFinalPublicClass()), "org/kepe/beancp/config/BeancpValueFilter"}, 0, null);
						}else {
							methodVisitor.visitFrame(Opcodes.F_APPEND,1, new Object[] {"org/kepe/beancp/config/BeancpValueFilter"}, 0, null);
						}
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitVarInsn(ASTORE, varTemp2);
						Label label171 = new Label();
						methodVisitor.visitLabel(label171);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label171);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitJumpInsn(IFNULL, nextLabel);
						Label label172 = new Label();
						methodVisitor.visitLabel(label172);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label172);
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "of", "(Ljava/lang/Object;)Lorg/kepe/beancp/ct/BeancpConvertProvider;", false);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
						methodVisitor.visitVarInsn(ASTORE, varTemp3);
						Label label173 = new Label();
						methodVisitor.visitLabel(label173);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label173);
						methodVisitor.visitVarInsn(ALOAD, varTemp3);
						methodVisitor.visitJumpInsn(IFNULL, nextLabel);
						Label label174 = new Label();
						methodVisitor.visitLabel(label174);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label174);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varTemp3);
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
					}else if(!isSetValueWhenNull&&!isDirectTrans&&isGetPrim&&!isSetPrim) {
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						visitIsAllowSet.accept(setInfo);
						methodVisitor.visitJumpInsn(IFEQ, nextLabel);
						Label label176 = new Label();
						methodVisitor.visitLabel(label176);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label176);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						visitGetValueFilter.accept(setInfo);
						methodVisitor.visitInsn(DUP);
						methodVisitor.visitVarInsn(ASTORE, varTemp1);
						Label label129 = new Label();
						methodVisitor.visitLabel(label129);
						Label label177 = new Label();
						methodVisitor.visitJumpInsn(IFNULL, label177);
						Label label178 = new Label();
						methodVisitor.visitLabel(label178);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label178);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						BeancpInfoASMTool.visitCast(methodVisitor, setInfo.getInfo(),true,false);
						visitFilterValue.accept(setInfo);
						BeancpInfoASMTool.visitCast(methodVisitor, setInfo.getInfo());
						methodVisitor.visitVarInsn(ASTORE, varTemp2);
						Label label179 = new Label();
						methodVisitor.visitLabel(label179);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label179);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitJumpInsn(IFNULL, nextLabel);
						Label label180 = new Label();
						methodVisitor.visitLabel(label180);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label180);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
						Label label181 = new Label();
						methodVisitor.visitLabel(label181);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label181);
						methodVisitor.visitJumpInsn(GOTO, nextLabel);
						methodVisitor.visitLabel(label177);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label177);
						if(i==0) {
							methodVisitor.visitFrame(Opcodes.F_APPEND,3, new Object[] {BeancpInfoASMTool.getClassName(fromInfo.getFinalPublicClass()), BeancpInfoASMTool.getClassName(toInfo.getFinalPublicClass()), "org/kepe/beancp/config/BeancpValueFilter"}, 0, null);
						}else {
							methodVisitor.visitFrame(Opcodes.F_APPEND,1, new Object[] {"org/kepe/beancp/config/BeancpValueFilter"}, 0, null);
						}
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Integer");
						methodVisitor.visitVarInsn(ASTORE, varTemp2);
						Label label182 = new Label();
						methodVisitor.visitLabel(label182);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label182);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitJumpInsn(IFNULL, nextLabel);
						Label label183 = new Label();
						methodVisitor.visitLabel(label183);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label183);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
					}else if(!isSetValueWhenNull&&!isDirectTrans&&!isGetPrim&&isSetPrim) {
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						visitIsAllowSet.accept(setInfo);
						methodVisitor.visitJumpInsn(IFEQ, nextLabel);
						Label label186 = new Label();
						methodVisitor.visitLabel(label186);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label186);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						methodVisitor.visitInsn(DUP);
						methodVisitor.visitVarInsn(ASTORE, varTemp1);
						Label label129 = new Label();
						methodVisitor.visitLabel(label129);
						Label label187 = new Label();
						methodVisitor.visitJumpInsn(IFNULL, label187);
						Label label188 = new Label();
						methodVisitor.visitLabel(label188);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label188);
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitVarInsn(ASTORE, varTemp2);
						Label label189 = new Label();
						methodVisitor.visitLabel(label189);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label189);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, varTemp1);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						visitLdc.accept(setInfo);
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "of", "(Ljava/lang/Object;)Lorg/kepe/beancp/ct/BeancpConvertProvider;", false);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						BeancpInfoASMTool.visitPrimCastObj(methodVisitor, setInfo.getInfo());
						visitFilterValue.accept(setInfo);
						BeancpInfoASMTool.visitCast(methodVisitor, setInfo.getInfo());
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
						Label label190 = new Label();
						methodVisitor.visitLabel(label190);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label190);
						methodVisitor.visitJumpInsn(GOTO, nextLabel);
						methodVisitor.visitLabel(label187);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label187);
						if(i==0) {
							methodVisitor.visitFrame(Opcodes.F_APPEND,3, new Object[] {BeancpInfoASMTool.getClassName(fromInfo.getFinalPublicClass()), BeancpInfoASMTool.getClassName(toInfo.getFinalPublicClass()), "org/kepe/beancp/config/BeancpValueFilter"}, 0, null);
						}else {
							methodVisitor.visitFrame(Opcodes.F_APPEND,1, new Object[] {"org/kepe/beancp/config/BeancpValueFilter"}, 0, null);
						}
						methodVisitor.visitVarInsn(ALOAD, varFromObj);
						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
						methodVisitor.visitVarInsn(ASTORE, varTemp2);
						Label label191 = new Label();
						methodVisitor.visitLabel(label191);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label191);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitJumpInsn(IFNULL, nextLabel);
						Label label192 = new Label();
						methodVisitor.visitLabel(label192);
						methodVisitor.visitLineNumber(asmContext.getNextLine(), label192);
						methodVisitor.visitVarInsn(ALOAD, varToObj);
						methodVisitor.visitVarInsn(ALOAD, 0);
						methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "provider"+i, "Lorg/kepe/beancp/ct/BeancpConvertProvider;");
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "of", "(Ljava/lang/Object;)Lorg/kepe/beancp/ct/BeancpConvertProvider;", false);
						methodVisitor.visitVarInsn(ALOAD, varContext);
						methodVisitor.visitVarInsn(ALOAD, varTemp2);
						methodVisitor.visitInsn(BeancpInfoASMTool.getCONST(setInfo.getInfo()));
						methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/ct/BeancpConvertProvider", "convert", "(Lorg/kepe/beancp/config/BeancpContext;"+(isGetPrim?BeancpInfoASMTool.desc(getInfo.getInfo().getBClass()):"Ljava/lang/Object;")+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;")+")"+(isSetPrim?BeancpInfoASMTool.desc(setInfo.getInfo().getBClass()):"Ljava/lang/Object;"), false);
						BeancpInfoASMTool.visitSetInfo(methodVisitor, setInfo);
					}
//					else {
//						methodVisitor.visitVarInsn(ALOAD, varFromObj);
//						BeancpInfoASMTool.visitGetInfo(methodVisitor, getInfo);
//						methodVisitor.visitInsn(POP);
//					}
					
					methodVisitor.visitLabel(labels[1]);
					methodVisitor.visitLineNumber(asmContext.getNextLine(), labels[1]);
					methodVisitor.visitJumpInsn(GOTO, nextLabel);
					methodVisitor.visitLabel(labels[2]);
					if(i<0) {
						methodVisitor.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {"java/lang/Exception"});
					}else {
						methodVisitor.visitFrame(Opcodes.F_FULL, 6, new Object[] {asmContext.getClassPath(), "org/kepe/beancp/config/BeancpContext", "java/lang/Object", "java/lang/Object", BeancpInfoASMTool.getClassName(fromInfo.getFinalPublicClass()), BeancpInfoASMTool.getClassName(toInfo.getFinalPublicClass())}, 1, new Object[] {"java/lang/Exception"});
					}
					methodVisitor.visitVarInsn(ASTORE, varTemp1);
					Label label88 = new Label();
					methodVisitor.visitLabel(label88);
					methodVisitor.visitLineNumber(asmContext.getNextLine(), label88);
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitVarInsn(ALOAD, 0);
					methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "flag", "Lorg/kepe/beancp/config/BeancpFeature;");
					methodVisitor.visitVarInsn(ALOAD, varContext);
					methodVisitor.visitLdcInsn(setInfo.getName().name);
					methodVisitor.visitInsn(ICONST_1);
					methodVisitor.visitVarInsn(ALOAD, varTemp1);
					methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "handleException", "(Lorg/kepe/beancp/ct/BeancpConvertProvider;Lorg/kepe/beancp/config/BeancpFeature;Lorg/kepe/beancp/config/BeancpContext;Ljava/lang/String;ILjava/lang/Exception;)V", false);
				}
				methodVisitor.visitLabel(returnLabel);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), returnLabel);
				if(!gsetList.isEmpty()) {
					methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
				}
				methodVisitor.visitVarInsn(ALOAD, varToObj);
				methodVisitor.visitLabel(label46);
				methodVisitor.visitInsn(ARETURN);
				
				methodVisitor.visitLabel(label44);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label44);
				if(gsetList.isEmpty()) {
					methodVisitor.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {"java/lang/Exception"});
				}else {
					methodVisitor.visitFrame(Opcodes.F_FULL, 4, new Object[] {asmContext.getClassPath(), "org/kepe/beancp/config/BeancpContext", "java/lang/Object", "java/lang/Object"}, 1, new Object[] {"java/lang/Exception"});
				}
				methodVisitor.visitVarInsn(ASTORE, varFromObj);
				Label label188 = new Label();
				methodVisitor.visitLabel(label188);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label188);
				methodVisitor.visitVarInsn(ALOAD, 0);
				methodVisitor.visitVarInsn(ALOAD, 0);
				methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "flag", "Lorg/kepe/beancp/config/BeancpFeature;");
				methodVisitor.visitVarInsn(ALOAD, 1);
				methodVisitor.visitLdcInsn("");
				methodVisitor.visitInsn(ICONST_0);
				methodVisitor.visitVarInsn(ALOAD, 4);
				methodVisitor.visitMethodInsn(INVOKESTATIC, BeancpInfoASMTool.getClassName(BeancpConvertProviderTool.class), "handleException", "(Lorg/kepe/beancp/ct/BeancpConvertProvider;Lorg/kepe/beancp/config/BeancpFeature;Lorg/kepe/beancp/config/BeancpContext;Ljava/lang/String;ILjava/lang/Exception;)V", false);
				Label label74 = new Label();
				methodVisitor.visitLabel(label74);
				methodVisitor.visitLineNumber(asmContext.getNextLine(), label74);
				methodVisitor.visitVarInsn(ALOAD, 0);
				methodVisitor.visitFieldInsn(GETFIELD, asmContext.getClassPath(), "toInfo", "Lorg/kepe/beancp/info/BeancpInfo;");
				methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "org/kepe/beancp/info/BeancpInfo", "defaultInstance", "()Ljava/lang/Object;", false);
				methodVisitor.visitInsn(ARETURN);
				
			}

			
        },0);
		registerMap2Bean(new BeancpCustomConverter() {

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj, Object toObj) {
				return BeancpConvertMapper.of( ((BeancpInvocationImp)invocation).getToInfo(), invocation.getFeature()).putAll(toObj, (Map)fromObj, ((BeancpInvocationImp)invocation).getFromInfo(), context);
			}
			
		},1);
		registerBean2Map(new BeancpCustomConverter() {

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj, Object toObj) {
				return BeancpConvertMapper.of(((BeancpInvocationImp)invocation).getFromInfo(), invocation.getFeature()).toMap(fromObj, (Map)toObj, ((BeancpInvocationImp)invocation).getToInfo(), context);
			}
			
		},1);
	}
	
	private static void registerBean2Bean(BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createBeanMatcher(), BeancpInfoMatcherTool.createBeanMatcher(), converter, priority));
    }
	private static void registerMap2Bean(BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(Map.class)), BeancpInfoMatcherTool.createBeanMatcher(), converter, priority));
    }
	private static void registerBean2Map(BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createBeanMatcher(), BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(Map.class)), converter, priority));
    }
}
