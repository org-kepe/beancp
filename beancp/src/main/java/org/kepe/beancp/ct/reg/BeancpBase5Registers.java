package org.kepe.beancp.ct.reg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.sql.Blob;
import java.sql.Clob;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

import org.kepe.beancp.config.BeancpCompareFlag;
import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.BeancpConvertProvider;
import org.kepe.beancp.ct.converter.BeancpConverterInfo;
import org.kepe.beancp.ct.invocation.BeancpInvocation;
import org.kepe.beancp.ct.invocation.BeancpInvocationImp;
import org.kepe.beancp.ct.invocation.BeancpInvocationJO;
import org.kepe.beancp.ct.invocation.BeancpInvocationOO;
import org.kepe.beancp.ct.itf.BeancpConverter;
import org.kepe.beancp.ct.itf.BeancpCustomConverter;
import org.kepe.beancp.ct.reg.compare.BeancpDefaultCustomCompare;
import org.kepe.beancp.exception.BeancpException;
import org.kepe.beancp.info.BeancpInfo;
import org.kepe.beancp.tool.BeancpInfoMatcherTool;
import org.kepe.beancp.tool.BeancpTool;

public class BeancpBase5Registers extends BeancpRegister{
	public static void registers() {
		
		BeancpCustomConverter converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 4;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
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
			return ((String)fromObj).toCharArray();
		}), PRIORITY8);
		registerEq(char[].class,String.class, BeancpTool.create(5, (invocation,context,fromObj,toObj)->{
			return String.valueOf((char[])fromObj);
		}), PRIORITY8);
		cregister(String.class,char[].class, new BeancpDefaultCustomCompare() {
			
			@Override
			public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, T fromObj, R toObj) {
				return BeancpCompareFlag.of(((String)fromObj).compareTo(new String((char[])toObj)));
			}
		}, PRIORITY8);
		register(Serializable.class,byte[].class, BeancpTool.create(110, (invocation,context,fromObj,toObj)->{
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
			
		}), PRIORITY7);
		register(byte[].class,Serializable.class, BeancpTool.create(110, (invocation,context,fromObj,toObj)->{
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
			
		}), PRIORITY7);
		
		registerExtends2Eq(Clob.class,String.class, BeancpTool.create(110, (invocation,context,fromObj,toObj)->{
			
			Clob contentClob = (Clob)fromObj;
			Reader inStraeam = null;
			try {
				inStraeam = contentClob.getCharacterStream();
				char[] cH = new char[(int) contentClob.length()];
				inStraeam.read(cH);
				String msg_content = new String(cH);
				return msg_content;
			} catch (Exception e) {
				throw new BeancpException(e);
			} finally {
				try {
					inStraeam.close();
				} catch (Exception e) {
				}
			}
			
		}), PRIORITY8);
		
		registerExtends2Eq(Blob.class,byte[].class, BeancpTool.create(110, (invocation,context,fromObj,toObj)->{
			InputStream bin=null;
			ByteArrayOutputStream output =null;
			try {
				bin=((Blob)fromObj).getBinaryStream();
				output = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024*4];
				int n = 0;
				while (-1 != (n = bin.read(buffer))) {
					output.write(buffer, 0, n);
				}
				return output.toByteArray();
			} catch (Exception e) {
				throw new BeancpException(e);
			} finally {
				try {
					output.close();
				} catch (Exception e1) {
				}
				if(bin!=null) {
					try {
						bin.close();
					} catch (Exception e) {
					}
				}
			}
		}), PRIORITY8);
		register(Blob.class,InputStream.class, BeancpTool.create(110, (invocation,context,fromObj,toObj)->{
			try {
				return ((Blob)fromObj).getBinaryStream();
			} catch (Exception e) {
				throw new BeancpException(e);
			} finally {
			}
		}), PRIORITY8);
		register(Blob.class,File.class, BeancpTool.create(110, (invocation,context,fromObj,toObj)->{
			if(toObj==null) {
				return toObj;
			}
			File newFile=(File)toObj;
			if(!newFile.exists()) {
				
				try {
					newFile.getParentFile().mkdirs();
					newFile.createNewFile();
				} catch (IOException e) {
					throw new BeancpException(e);
				}
			}
			
			InputStream bin=null;
			OutputStream fout=null;
			try {
				bin=((Blob)fromObj).getBinaryStream();
				fout = new FileOutputStream(newFile);
				byte[] b = new byte[1024];
				int len = 0;
				while ( (len = bin.read(b)) != -1) {
					fout.write(b, 0, len);
				}
				return newFile;
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				try {
					fout.close();
				} catch (Exception e1) {
				}

				if(bin!=null) {
					try {
						bin.close();
					} catch (Exception e) {
					}
				}
			}
			
		}), PRIORITY8);
		
		register(Blob.class,OutputStream.class, BeancpTool.create(110, (invocation,context,fromObj,toObj)->{
			if(toObj==null) {
				return toObj;
			}
			InputStream bin=null;
			try {
				OutputStream out=(OutputStream)toObj;
				bin=((Blob)fromObj).getBinaryStream();
				byte[] b = new byte[1024];
				int len = 0;
				while ( (len = bin.read(b)) != -1) {
					out.write(b, 0, len);
				}
				return out;
			} catch (Exception e) {
				throw new BeancpException(e);
			} finally {
				if(bin!=null) {
					try {
						bin.close();
					} catch (Exception e) {
					}
				}
			}
		}), PRIORITY8);
		
		
	}
	
	private static void register(Type fromType, Type toType, BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(toType)), converter, priority));
    }
	private static void registerExtends2Eq(Type fromType, Type toType, BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createEqualMatcher(BeancpInfo.of(toType)), converter, priority));
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
