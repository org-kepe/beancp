package org.kepe.beancp.ct.reg;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.config.BeancpOOConverter;
import org.kepe.beancp.ct.BeancpConvertProvider;
import org.kepe.beancp.ct.converter.BeancpConverterInfo;
import org.kepe.beancp.ct.invocation.BeancpInvocationBO;
import org.kepe.beancp.ct.invocation.BeancpInvocationCO;
import org.kepe.beancp.ct.invocation.BeancpInvocationDO;
import org.kepe.beancp.ct.invocation.BeancpInvocationFO;
import org.kepe.beancp.ct.invocation.BeancpInvocationIO;
import org.kepe.beancp.ct.invocation.BeancpInvocationJO;
import org.kepe.beancp.ct.invocation.BeancpInvocationOB;
import org.kepe.beancp.ct.invocation.BeancpInvocationOC;
import org.kepe.beancp.ct.invocation.BeancpInvocationOD;
import org.kepe.beancp.ct.invocation.BeancpInvocationOF;
import org.kepe.beancp.ct.invocation.BeancpInvocationOI;
import org.kepe.beancp.ct.invocation.BeancpInvocationOJ;
import org.kepe.beancp.ct.invocation.BeancpInvocationOO;
import org.kepe.beancp.ct.invocation.BeancpInvocationOS;
import org.kepe.beancp.ct.invocation.BeancpInvocationOZ;
import org.kepe.beancp.ct.invocation.BeancpInvocationSO;
import org.kepe.beancp.ct.invocation.BeancpInvocationZO;
import org.kepe.beancp.ct.itf.BeancpConverter;
import org.kepe.beancp.ct.itf.BeancpCustomConverter;
import org.kepe.beancp.ct.reg.converter.BeancpDirectCustomConverter;
import org.kepe.beancp.exception.BeancpException;
import org.kepe.beancp.info.BeancpInfo;
import org.kepe.beancp.tool.BeancpInfoMatcherTool;
import org.kepe.beancp.tool.BeancpTool;

public class BeancpBase3Registers  implements BeancpRegister{
	public static void registers() {
		
		BeancpCustomConverter converter1 = new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 5;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				if(fromObj==null||"".equals(fromObj)) {
					return 0;
				}
				String str=(String)fromObj;
				try {
					if(str.startsWith("0x")) {
						return Integer.valueOf(str.substring(2),16);
					}else {
						return Integer.valueOf(str);
					}
				} catch (NumberFormatException e) {
					try {
						return parseBigDecimal(str).intValue();
					} catch (Exception e1) {}
					throw new BeancpException(BeancpException.EType.IGNORE,e);
				}
			};
			@Override
			public int convert(BeancpInvocationOI invocation, BeancpContext context, Object fromObj, int toObj) {
				if(fromObj==null||"".equals(fromObj)) {
					return 0;
				}
				String str=(String)fromObj;
				try {
					if(str.startsWith("0x")) {
						return Integer.parseInt(str.substring(2),16);
					}else {
						return Integer.parseInt(str);
					}
				} catch (NumberFormatException e) {
					try {
						return parseBigDecimal(str).intValue();
					} catch (Exception e1) {}
					throw new BeancpException(BeancpException.EType.IGNORE,e);
				}
			};
			@Override
			public long convert(BeancpInvocationOJ invocation, BeancpContext context, Object fromObj, long toObj) {
				if(fromObj==null||"".equals(fromObj)) {
					return 0;
				}
				String str=(String)fromObj;
				try {
					if(str.startsWith("0x")) {
						return Long.parseLong(str.substring(2),16);
					}else {
						return Long.parseLong(str);
					}
				} catch (NumberFormatException e) {
					try {
						return parseBigDecimal(str).longValue();
					} catch (Exception e1) {}
					throw new BeancpException(BeancpException.EType.IGNORE,e);
				}
			};
			@Override
			public float convert(BeancpInvocationOF invocation, BeancpContext context, Object fromObj, float toObj) {
				if(fromObj==null||"".equals(fromObj)) {
					return 0;
				}
				try {
					return Float.parseFloat((String)fromObj);
				} catch (NumberFormatException e) {
					try {
						return parseBigDecimal((String)fromObj).floatValue();
					} catch (Exception e1) {}
					throw new BeancpException(BeancpException.EType.IGNORE,e);
				}
			};
			@Override
			public double convert(BeancpInvocationOD invocation, BeancpContext context, Object fromObj, double toObj) {
				if(fromObj==null||"".equals(fromObj)) {
					return 0;
				}
				try {
					return Double.parseDouble((String)fromObj);
				} catch (NumberFormatException e) {
					try {
						return parseBigDecimal((String)fromObj).doubleValue();
					} catch (Exception e1) {}
					throw new BeancpException(BeancpException.EType.IGNORE,e);
				}
			};
			@Override
			public short convert(BeancpInvocationOS invocation, BeancpContext context, Object fromObj, short toObj) {
				if(fromObj==null||"".equals(fromObj)) {
					return 0;
				}
				String str=(String)fromObj;
				try {
					if(str.startsWith("0x")) {
						return Short.parseShort(str.substring(2),16);
					}else {
						return Short.parseShort(str);
					}
				} catch (NumberFormatException e) {
					try {
						return parseBigDecimal(str).shortValue();
					} catch (Exception e1) {}
					throw new BeancpException(BeancpException.EType.IGNORE,e);
				}
			};
			@Override
			public boolean convert(BeancpInvocationOZ invocation, BeancpContext context, Object fromObj, boolean toObj) {
				if(fromObj==null||"".equals(fromObj)) {
					return false;
				}
				String str=(String)fromObj;
				if(str.equals("1")||str.equalsIgnoreCase("Y")||str.equalsIgnoreCase("true")) {
					return true;
				}
				return false;
			};
			@Override
			public char convert(BeancpInvocationOC invocation, BeancpContext context, Object fromObj, char toObj) {
				if(fromObj==null||"".equals(fromObj)) {
					return 0;
				}
				return ((String)fromObj).charAt(0);
			};
			
		};
		registerEq(String.class,Integer.class,converter1,PRIORITY8);
		registerEq(String.class,int.class,converter1,PRIORITY8);
		registerEq(String.class,long.class,converter1,PRIORITY8);
		registerEq(String.class,short.class,converter1,PRIORITY8);
		registerEq(String.class,double.class,converter1,PRIORITY8);
		registerEq(String.class,float.class,converter1,PRIORITY8);
		registerEq(String.class,char.class,converter1,PRIORITY8);
		registerEq(String.class,boolean.class,converter1,PRIORITY8);
		
		registerEq(String.class,Long.class,BeancpTool.create(5, (invocation,context,fromObj,toObj)->{
			if(fromObj==null||"".equals(fromObj)) {
				return 0;
			}
			String str=(String)fromObj;
			try {
				if(str.startsWith("0x")) {
					return Long.valueOf(str.substring(2),16);
				}else {
					return Long.valueOf(str);
				}
			} catch (NumberFormatException e) {
				try {
					return parseBigDecimal(str).longValue();
				} catch (Exception e1) {}
				throw new BeancpException(BeancpException.EType.IGNORE,e);
			} 
		}),PRIORITY8);
		registerEq(String.class,Short.class,BeancpTool.create(5, (invocation,context,fromObj,toObj)->{
			if(fromObj==null||"".equals(fromObj)) {
				return 0;
			}
			String str=(String)fromObj;
			try {
				if(str.startsWith("0x")) {
					return Short.parseShort(str.substring(2),16);
				}else {
					return Short.parseShort(str);
				}
			} catch (NumberFormatException e) {
				try {
					return parseBigDecimal(str).shortValue();
				} catch (Exception e1) {}
				throw new BeancpException(BeancpException.EType.IGNORE,e);
			}
		}),PRIORITY8);
		registerEq(String.class,Double.class,BeancpTool.create(5, (invocation,context,fromObj,toObj)->{
			if(fromObj==null||"".equals(fromObj)) {
				return 0;
			}
			try {
				return Double.parseDouble((String)fromObj);
			} catch (NumberFormatException e) {
				try {
					return parseBigDecimal((String)fromObj).doubleValue();
				} catch (Exception e1) {}
				throw new BeancpException(BeancpException.EType.IGNORE,e);
			}
		}),PRIORITY8);
		registerEq(String.class,Float.class,BeancpTool.create(5, (invocation,context,fromObj,toObj)->{
			if(fromObj==null||"".equals(fromObj)) {
				return 0;
			}
			try {
				return Float.parseFloat((String)fromObj);
			} catch (NumberFormatException e) {
				try {
					return parseBigDecimal((String)fromObj).floatValue();
				} catch (Exception e1) {}
				throw new BeancpException(BeancpException.EType.IGNORE,e);
			}
		}),PRIORITY8);
		registerEq(String.class,Character.class,BeancpTool.create(5, (invocation,context,fromObj,toObj)->{
			if(fromObj==null||"".equals(fromObj)) {
				return 0;
			}
			return ((String)fromObj).charAt(0);
		}),PRIORITY8);
		registerEq(String.class,Boolean.class,BeancpTool.create(5, (invocation,context,fromObj,toObj)->{
			if(fromObj==null||"".equals(fromObj)) {
				return null;
			}
			String str=(String)fromObj;
			if(str.equals("1")||str.equalsIgnoreCase("Y")||str.equalsIgnoreCase("true")) {
				return Boolean.TRUE;
			}
			return Boolean.FALSE;
		}),PRIORITY8);
		registerEq(String.class,BigDecimal.class,BeancpTool.create(5, (invocation,context,fromObj,toObj)->{
			if(fromObj==null||"".equals(fromObj)) {
				return null;
			}
			try {
				return parseBigDecimal((String)fromObj);
			} catch (NumberFormatException e) {
				throw new BeancpException(BeancpException.EType.IGNORE,e);
			}
		}),PRIORITY8);
		registerEq(String.class,Number.class,BeancpTool.create(5, (invocation,context,fromObj,toObj)->{
			if(fromObj==null||"".equals(fromObj)) {
				return null;
			}
			try {
				return parseBigDecimal((String)fromObj);
			} catch (NumberFormatException e) {
				throw new BeancpException(BeancpException.EType.IGNORE,e);
			}
		}),PRIORITY8);
		registerEq(String.class,BigInteger.class,BeancpTool.create(5, (invocation,context,fromObj,toObj)->{
			if(fromObj==null||"".equals(fromObj)) {
				return null;
			}
			try {
				return parseBigDecimal((String)fromObj).toBigInteger();
			} catch (NumberFormatException e) {
				throw new BeancpException(BeancpException.EType.IGNORE,e);
			}
		}),PRIORITY8);
		
		
		
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				if(fromObj==null) {
					return toObj;
				}
				return fromObj.toString();
			}
			@Override
			public Object convert(BeancpInvocationIO invocation, BeancpContext context, int fromObj, Object toObj) {
				return String.valueOf(fromObj);
			}
			@Override
			public Object convert(BeancpInvocationJO invocation, BeancpContext context, long fromObj, Object toObj) {
				return String.valueOf(fromObj);
			}
			@Override
			public Object convert(BeancpInvocationSO invocation, BeancpContext context, short fromObj, Object toObj) {
				return String.valueOf(fromObj);
			}
			@Override
			public Object convert(BeancpInvocationDO invocation, BeancpContext context, double fromObj, Object toObj) {
				return String.valueOf(fromObj);
			}
			@Override
			public Object convert(BeancpInvocationFO invocation, BeancpContext context, float fromObj, Object toObj) {
				return String.valueOf(fromObj);
			}
			@Override
			public Object convert(BeancpInvocationZO invocation, BeancpContext context, boolean fromObj, Object toObj) {
				return String.valueOf(fromObj);
			}
			@Override
			public Object convert(BeancpInvocationBO invocation, BeancpContext context, byte fromObj, Object toObj) {
				return String.valueOf(fromObj);
			}
			@Override
			public Object convert(BeancpInvocationCO invocation, BeancpContext context, char fromObj, Object toObj) {
				return String.valueOf(fromObj);
			}
		};
		 
		registerEq(Integer.class,String.class,converter1,PRIORITY8);
		registerEq(int.class,String.class,converter1,PRIORITY8);
		registerEq(long.class,String.class,converter1,PRIORITY8);
		registerEq(short.class,String.class,converter1,PRIORITY8);
		registerEq(double.class,String.class,converter1,PRIORITY8);
		registerEq(float.class,String.class,converter1,PRIORITY8);
		registerEq(char.class,String.class,converter1,PRIORITY8);
		registerEq(boolean.class,String.class,converter1,PRIORITY8);
		registerEq(Long.class,String.class,converter1,PRIORITY8);
		registerEq(Short.class,String.class,converter1,PRIORITY8);
		registerEq(Double.class,String.class,converter1,PRIORITY8);
		registerEq(Float.class,String.class,converter1,PRIORITY8);
		registerEq(Character.class,String.class,converter1,PRIORITY8);
		registerEq(Boolean.class,String.class,converter1,PRIORITY8);
		registerEq(BigInteger.class,String.class,converter1,PRIORITY8);
		registerEq(BigDecimal.class,String.class,BeancpTool.create(2, (invocation,context,fromObj,toObj)->{
			if(fromObj==null) {
				return toObj;
			}
			return ((BigDecimal)fromObj).toPlainString();
		}),PRIORITY8);
		
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				if(fromObj==null) {
					return toObj;
				}
				return  ((Integer)fromObj).longValue();
			}
			@Override
			public Object convert(BeancpInvocationIO invocation, BeancpContext context, int fromObj, Object toObj) {
				return  (long)fromObj;
			}
			@Override
			public Object convert(BeancpInvocationJO invocation, BeancpContext context, long fromObj, Object toObj) {
				return (int)fromObj;
			}
			@Override
			public Object convert(BeancpInvocationSO invocation, BeancpContext context, short fromObj, Object toObj) {
				return  (long)fromObj;
			}
			@Override
			public Object convert(BeancpInvocationDO invocation, BeancpContext context, double fromObj, Object toObj) {
				return  (long)fromObj;
			}
			@Override
			public Object convert(BeancpInvocationFO invocation, BeancpContext context, float fromObj, Object toObj) {
				return  (long)fromObj;
			}
			@Override
			public Object convert(BeancpInvocationZO invocation, BeancpContext context, boolean fromObj, Object toObj) {
				return fromObj?1L:0L;
			}
			@Override
			public Object convert(BeancpInvocationBO invocation, BeancpContext context, byte fromObj, Object toObj) {
				return (long)fromObj;
			}
			@Override
			public Object convert(BeancpInvocationCO invocation, BeancpContext context, char fromObj, Object toObj) {
				return (long)fromObj;
			}
			@Override
			public boolean convert(BeancpInvocationOZ invocation, BeancpContext context, Object fromObj, 
					boolean toObj) {
				if(fromObj==null) {
					return toObj;
				}
				return ((Long)fromObj).booleanValue();
			}
			
			@Override
			public int convert(BeancpInvocationOI invocation, BeancpContext context, Object fromObj, 
					int toObj) {
				return ((Integer)fromObj).intValue();
			}
			@Override
			public long convert(BeancpInvocationOJ invocation, BeancpContext context, Object fromObj, 
					long toObj) {
				return ((Long)fromObj).longValue();
			}
			@Override
			public float convert(BeancpInvocationOF invocation, BeancpContext context, Object fromObj, 
					float toObj) {
				return ((Float)fromObj).floatValue();
			}
			@Override
			public double convert(BeancpInvocationOD invocation, BeancpContext context, Object fromObj, 
					double toObj) {
				return ((Double)fromObj).doubleValue();
			}
			@Override
			public short convert(BeancpInvocationOS invocation, BeancpContext context, Object fromObj, 
					short toObj) {
				return ((Short)fromObj).shortValue();
			}
			@Override
			public byte convert(BeancpInvocationOB invocation, BeancpContext context, Object fromObj, 
					byte toObj) {
				return ((Byte)fromObj).byteValue();
			}
			@Override
			public char convert(BeancpInvocationOC invocation, BeancpContext context, Object fromObj, 
					char toObj) {
				return ((Character)fromObj).charValue();
			}
		};
		registerEq(Integer.class,Long.class,converter1,PRIORITY8);
		registerEq(int.class,Long.class,converter1,PRIORITY8);
		registerEq(long.class,Integer.class,converter1,PRIORITY8);
		registerEq(short.class,Long.class,converter1,PRIORITY8);
		registerEq(double.class,Long.class,converter1,PRIORITY8);
		registerEq(boolean.class,Long.class,converter1,PRIORITY8);
		registerEq(byte.class,Long.class,converter1,PRIORITY8);
		registerEq(char.class,Long.class,converter1,PRIORITY8);
		
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				if(fromObj==null) {
					return toObj;
				}
				return  ((Integer)fromObj).shortValue();
			}
			@Override
			public Object convert(BeancpInvocationIO invocation, BeancpContext context, int fromObj, Object toObj) {
				return  (short)fromObj;
			}
			@Override
			public Object convert(BeancpInvocationJO invocation, BeancpContext context, long fromObj, Object toObj) {
				return (short)fromObj;
			}
			@Override
			public Object convert(BeancpInvocationSO invocation, BeancpContext context, short fromObj, Object toObj) {
				return  (int)fromObj;
			}
			@Override
			public Object convert(BeancpInvocationDO invocation, BeancpContext context, double fromObj, Object toObj) {
				return  (short)fromObj;
			}
			@Override
			public Object convert(BeancpInvocationFO invocation, BeancpContext context, float fromObj, Object toObj) {
				return  (short)fromObj;
			}
			@Override
			public Object convert(BeancpInvocationZO invocation, BeancpContext context, boolean fromObj, Object toObj) {
				return (short)(fromObj?1:0);
			}
			@Override
			public Object convert(BeancpInvocationBO invocation, BeancpContext context, byte fromObj, Object toObj) {
				return (short)fromObj;
			}
			@Override
			public Object convert(BeancpInvocationCO invocation, BeancpContext context, char fromObj, Object toObj) {
				return (short)fromObj;
			}
		};
		registerEq(Integer.class,Short.class,converter1,PRIORITY8);
		registerEq(int.class,Short.class,converter1,PRIORITY8);
		registerEq(short.class,Integer.class,converter1,PRIORITY8);
		registerEq(short.class,Short.class,converter1,PRIORITY8);
		registerEq(double.class,Short.class,converter1,PRIORITY8);
		registerEq(boolean.class,Short.class,converter1,PRIORITY8);
		registerEq(byte.class,Short.class,converter1,PRIORITY8);
		registerEq(char.class,Short.class,converter1,PRIORITY8);
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
	
	
	private static BigDecimal parseBigDecimal(String str) {
    	if(str.startsWith("0x")) {
    		return new BigDecimal(Long.parseLong(str.substring(2), 16));
    	}else if(str.endsWith("%")) {
    		return new BigDecimal(str.substring(0,str.length()-1)).divide(new BigDecimal(100));
    	}else if(str.endsWith("\u2030")) {
    		return new BigDecimal(str.substring(0,str.length()-1)).divide(new BigDecimal(1000));
    	}else {
    		if(str.contains(",")) {
    			str=str.replace(",", "");
    		}
    		return new BigDecimal(str);
    	}
    }
}
