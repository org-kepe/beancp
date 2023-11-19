package org.kepe.beancp.ct.reg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Date;
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

public class BeancpBase5Registers  implements BeancpRegister{
	public static void registers() {
		
		BeancpCustomConverter converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 4;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				if(fromObj==null) {
					return toObj;
				}
				if(toObj!=null) {
					((Date)toObj).setTime((Long)fromObj);
					return toObj;
				}
				return ((BeancpInvocationImp)invocation).getToInfo().getDefaultMapper().newInstance(context, fromObj);
			};
			@Override
			public Object convert(BeancpInvocationJO invocation, BeancpContext context, long fromObj, Object toObj) {
				if(toObj!=null) {
					((Date)toObj).setTime(fromObj);
					return toObj;
				}
				return ((BeancpInvocationImp)invocation).getToInfo().getDefaultMapper().newInstance(context, fromObj);
			}
			
		};
		registerEq(String.class,char[].class, BeancpTool.create(5, (invocation,context,fromObj,toObj)->{
			if(fromObj==null) {
				return toObj;
			}
			return ((String)fromObj).toCharArray();
		}), PRIORITY8);
		register(Serializable.class,byte[].class, BeancpTool.create(110, (invocation,context,fromObj,toObj)->{
			if(fromObj==null) {
				return toObj;
			}
			ByteArrayOutputStream bos=null;
			ObjectOutputStream out=null;
			try {
				bos=new ByteArrayOutputStream();
				out=new ObjectOutputStream(bos);
				out.writeObject(fromObj);
				return bos.toByteArray();
			} catch (IOException e) {
				throw new BeancpException(e);
			} finally {
				try {
					out.close();
				} catch (Exception e) {}
				try {
					bos.close();
				} catch (Exception e) {}
			}
			
		}), PRIORITY8);
		register(byte[].class,Serializable.class, BeancpTool.create(110, (invocation,context,fromObj,toObj)->{
			if(fromObj==null) {
				return toObj;
			}
			ByteArrayInputStream bis=null;
			ObjectInputStream in=null;
			try {
				bis=new ByteArrayInputStream((byte[]) fromObj);
				in=new ObjectInputStream(bis);
				return in.readObject();
			} catch (IOException e) {
				throw new BeancpException(e);
			} catch (ClassNotFoundException e) {
				throw new BeancpException(e);
			} finally {
				try {
					in.close();
				} catch (Exception e) {}
				try {
					bis.close();
				} catch (Exception e) {}
			}
			
		}), PRIORITY8);
		
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
