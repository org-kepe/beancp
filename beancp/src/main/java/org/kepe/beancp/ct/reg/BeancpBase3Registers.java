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
				if("".equals(fromObj)) {
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
				if("".equals(fromObj)) {
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
				if("".equals(fromObj)) {
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
				if("".equals(fromObj)) {
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
				if("".equals(fromObj)) {
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
				if("".equals(fromObj)) {
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
				if("".equals(fromObj)) {
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
				if("".equals(fromObj)) {
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
			if("".equals(fromObj)) {
				return null;
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
			if("".equals(fromObj)) {
				return null;
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
			if("".equals(fromObj)) {
				return null;
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
			if("".equals(fromObj)) {
				return null;
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
			if("".equals(fromObj)) {
				return null;
			}
			return ((String)fromObj).charAt(0);
		}),PRIORITY8);
		registerEq(String.class,Boolean.class,BeancpTool.create(5, (invocation,context,fromObj,toObj)->{
			if("".equals(fromObj)) {
				return null;
			}
			String str=(String)fromObj;
			if(str.equals("1")||str.equalsIgnoreCase("Y")||str.equalsIgnoreCase("true")) {
				return Boolean.TRUE;
			}
			return Boolean.FALSE;
		}),PRIORITY8);
		registerEq(String.class,BigDecimal.class,BeancpTool.create(5, (invocation,context,fromObj,toObj)->{
			if("".equals(fromObj)) {
				return null;
			}
			try {
				return parseBigDecimal((String)fromObj);
			} catch (NumberFormatException e) {
				throw new BeancpException(BeancpException.EType.IGNORE,e);
			}
		}),PRIORITY8);
		registerEq(String.class,Number.class,BeancpTool.create(5, (invocation,context,fromObj,toObj)->{
			if("".equals(fromObj)) {
				return null;
			}
			try {
				return parseBigDecimal((String)fromObj);
			} catch (NumberFormatException e) {
				throw new BeancpException(BeancpException.EType.IGNORE,e);
			}
		}),PRIORITY8);
		registerEq(String.class,BigInteger.class,BeancpTool.create(5, (invocation,context,fromObj,toObj)->{
			if("".equals(fromObj)) {
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
			return ((BigDecimal)fromObj).toPlainString();
		}),PRIORITY8);
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return  ((Integer)fromObj).intValue();
			}
			@Override
			public int convert(BeancpInvocationOI invocation, BeancpContext context, Object fromObj, int toObj) {
				return  ((Number)fromObj).intValue();
			}
			@Override
			public long convert(BeancpInvocationOJ invocation, BeancpContext context, Object fromObj, long toObj) {
				return  ((Number)fromObj).longValue();
			}
			@Override
			public short convert(BeancpInvocationOS invocation, BeancpContext context, Object fromObj, short toObj) {
				return  ((Number)fromObj).shortValue();
			}
			@Override
			public float convert(BeancpInvocationOF invocation, BeancpContext context, Object fromObj, float toObj) {
				return  ((Number)fromObj).floatValue();
			}
			@Override
			public double convert(BeancpInvocationOD invocation, BeancpContext context, Object fromObj, double toObj) {
				return  ((Number)fromObj).doubleValue();
			}
			@Override
			public byte convert(BeancpInvocationOB invocation, BeancpContext context, Object fromObj, byte toObj) {
				return  ((Number)fromObj).byteValue();
			}
			
		};
		registerExtends2Eq(Number.class,Integer.class,converter1,PRIORITY8);
		registerExtends2Eq(Number.class,int.class,converter1,PRIORITY8);
		registerExtends2Eq(Number.class,long.class,converter1,PRIORITY8);
		registerExtends2Eq(Number.class,short.class,converter1,PRIORITY8);
		registerExtends2Eq(Number.class,float.class,converter1,PRIORITY8);
		registerExtends2Eq(Number.class,double.class,converter1,PRIORITY8);
		registerExtends2Eq(Number.class,byte.class,converter1,PRIORITY8);
		registerExtends2Eq(Number.class,Long.class,BeancpTool.create(2, (invocation,context,fromObj,toObj)->{
			return ((Number)fromObj).longValue();
		}),PRIORITY8);
		registerExtends2Eq(Number.class,Short.class,BeancpTool.create(2, (invocation,context,fromObj,toObj)->{
			return ((Number)fromObj).shortValue();
		}),PRIORITY8);
		registerExtends2Eq(Number.class,Float.class,BeancpTool.create(2, (invocation,context,fromObj,toObj)->{
			return ((Number)fromObj).floatValue();
		}),PRIORITY8);
		registerExtends2Eq(Number.class,Double.class,BeancpTool.create(2, (invocation,context,fromObj,toObj)->{
			return ((Number)fromObj).doubleValue();
		}),PRIORITY8);
		registerExtends2Eq(Number.class,Byte.class,BeancpTool.create(2, (invocation,context,fromObj,toObj)->{
			return ((Number)fromObj).byteValue();
		}),PRIORITY8);
		
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return  ((Number)fromObj).longValue();
			}
			@Override
			public Object convert(BeancpInvocationIO invocation, BeancpContext context, int fromObj, Object toObj) {
				return  ((Number)fromObj).longValue();
			}
		};
		registerEq(int.class,Long.class,converter1,PRIORITY8);
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return  ((Number)fromObj).doubleValue();
			}
			@Override
			public Object convert(BeancpInvocationIO invocation, BeancpContext context, int fromObj, Object toObj) {
				return  ((Number)fromObj).doubleValue();
			}
		};
		registerEq(int.class,Double.class,converter1,PRIORITY8);
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return  ((Number)fromObj).floatValue();
			}
			@Override
			public Object convert(BeancpInvocationIO invocation, BeancpContext context, int fromObj, Object toObj) {
				return  ((Number)fromObj).floatValue();
			}
		};
		registerEq(int.class,Float.class,converter1,PRIORITY8);
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return  ((Number)fromObj).shortValue();
			}
			@Override
			public Object convert(BeancpInvocationIO invocation, BeancpContext context, int fromObj, Object toObj) {
				return  ((Number)fromObj).shortValue();
			}
		};
		registerEq(int.class,Short.class,converter1,PRIORITY8);
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return  (Integer)fromObj>0;
			}
			@Override
			public Object convert(BeancpInvocationIO invocation, BeancpContext context, int fromObj, Object toObj) {
				return  fromObj>0;
			}
			@Override
			public boolean convert(BeancpInvocationOZ invocation, BeancpContext context, Object fromObj, boolean toObj) {
				return  (Integer)fromObj>0;
			}
		};
		registerEq(Integer.class,Boolean.class,converter1,PRIORITY8);
		registerEq(Integer.class,boolean.class,converter1,PRIORITY8);
		registerEq(int.class,Boolean.class,converter1,PRIORITY8);
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return  (char)(((Integer)fromObj).intValue());
			}
			@Override
			public Object convert(BeancpInvocationIO invocation, BeancpContext context, int fromObj, Object toObj) {
				return  (char)fromObj;
			}
			@Override
			public char convert(BeancpInvocationOC invocation, BeancpContext context, Object fromObj, char toObj) {
				return  (char)((Integer)fromObj).intValue();
			}
		};
		registerEq(Integer.class,Character.class,converter1,PRIORITY8);
		registerEq(Integer.class,char.class,converter1,PRIORITY8);
		registerEq(int.class,Character.class,converter1,PRIORITY8);
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return  new BigDecimal((Integer)fromObj);
			}
			@Override
			public Object convert(BeancpInvocationIO invocation, BeancpContext context, int fromObj, Object toObj) {
				return  new BigDecimal(fromObj);
			}
		};
		registerEq(Integer.class,BigDecimal.class,converter1,PRIORITY8);
		registerEq(int.class,BigDecimal.class,converter1,PRIORITY8);
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return BigInteger.valueOf((Integer)fromObj);
			}
			@Override
			public Object convert(BeancpInvocationIO invocation, BeancpContext context, int fromObj, Object toObj) {
				return BigInteger.valueOf(fromObj);
			}
		};
		registerEq(Integer.class,BigInteger.class,converter1,PRIORITY8);
		registerEq(int.class,BigInteger.class,converter1,PRIORITY8);
		
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return ((Number)fromObj).intValue();
			}
			@Override
			public Object convert(BeancpInvocationDO invocation, BeancpContext context, double fromObj, Object toObj) {
				return (int)fromObj;
			}
			@Override
			public Object convert(BeancpInvocationJO invocation, BeancpContext context, long fromObj, Object toObj) {
				return (int)fromObj;
			}
			@Override
			public Object convert(BeancpInvocationFO invocation, BeancpContext context, float fromObj, Object toObj) {
				return (int)fromObj;
			}
			@Override
			public Object convert(BeancpInvocationSO invocation, BeancpContext context, short fromObj, Object toObj) {
				return (int)fromObj;
			}
			@Override
			public Object convert(BeancpInvocationZO invocation, BeancpContext context, boolean fromObj, Object toObj) {
				return fromObj?1:0;
			}
			@Override
			public Object convert(BeancpInvocationCO invocation, BeancpContext context, char fromObj, Object toObj) {
				return (int)fromObj;
			}
		};
		registerEq(double.class,Integer.class,converter1,PRIORITY8);
		registerEq(long.class,Integer.class,converter1,PRIORITY8);
		registerEq(float.class,Integer.class,converter1,PRIORITY8);
		registerEq(short.class,Integer.class,converter1,PRIORITY8);
		registerEq(boolean.class,Integer.class,converter1,PRIORITY8);
		registerEq(char.class,Integer.class,converter1,PRIORITY8);
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return fromObj.equals(Boolean.TRUE)?1:0;
			}
			@Override
			public int convert(BeancpInvocationOI invocation, BeancpContext context, Object fromObj, int toObj) {
				return fromObj.equals(Boolean.TRUE)?1:0;
			}
			
		};
		registerEq(Boolean.class,Integer.class,converter1,PRIORITY8);
		registerEq(Boolean.class,int.class,converter1,PRIORITY8);
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return (int)((Character)fromObj).charValue();
			}
			@Override
			public int convert(BeancpInvocationOI invocation, BeancpContext context, Object fromObj, int toObj) {
				return (int)((Character)fromObj).charValue();
			}
			
		};
		registerEq(Character.class,Integer.class,converter1,PRIORITY8);
		registerEq(Character.class,int.class,converter1,PRIORITY8);
		
		
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return ((Number)fromObj).floatValue();
			}
			@Override
			public Object convert(BeancpInvocationJO invocation, BeancpContext context, long fromObj, Object toObj) {
				return ((Number)fromObj).doubleValue();
			}
			
		};
		registerEq(long.class,Double.class,converter1,PRIORITY8);
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return ((Number)fromObj).floatValue();
			}
			@Override
			public Object convert(BeancpInvocationJO invocation, BeancpContext context, long fromObj, Object toObj) {
				return ((Number)fromObj).floatValue();
			}
			
		};
		registerEq(long.class,Float.class,converter1,PRIORITY8);
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return ((Number)fromObj).floatValue();
			}
			@Override
			public Object convert(BeancpInvocationJO invocation, BeancpContext context, long fromObj, Object toObj) {
				return ((Number)fromObj).shortValue();
			}
			
		};
		registerEq(long.class,Short.class,converter1,PRIORITY8);
		registerEq(long.class,Float.class,converter1,PRIORITY8);
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return new BigDecimal((Long)fromObj);
			}
			@Override
			public Object convert(BeancpInvocationJO invocation, BeancpContext context, long fromObj, Object toObj) {
				return new BigDecimal(fromObj);
			}
			
		};
		registerEq(Long.class,BigDecimal.class,converter1,PRIORITY8);
		registerEq(long.class,BigDecimal.class,converter1,PRIORITY8);
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return BigInteger.valueOf((Long)fromObj);
			}
			@Override
			public Object convert(BeancpInvocationJO invocation, BeancpContext context, long fromObj, Object toObj) {
				return BigInteger.valueOf(fromObj);
			}
			
		};
		registerEq(Long.class,BigInteger.class,converter1,PRIORITY8);
		registerEq(long.class,BigInteger.class,converter1,PRIORITY8);
		
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return BigInteger.valueOf((Long)fromObj);
			}
			@Override
			public Object convert(BeancpInvocationDO invocation, BeancpContext context, double fromObj, Object toObj) {
				return (long)fromObj;
			}
			
		};
		registerEq(double.class,Long.class,converter1,PRIORITY8);
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return BigInteger.valueOf((Long)fromObj);
			}
			@Override
			public Object convert(BeancpInvocationFO invocation, BeancpContext context, float fromObj, Object toObj) {
				return (long)fromObj;
			}
			
		};
		registerEq(float.class,Long.class,converter1,PRIORITY8);
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return BigInteger.valueOf((Long)fromObj);
			}
			@Override
			public Object convert(BeancpInvocationSO invocation, BeancpContext context, short fromObj, Object toObj) {
				return (long)fromObj;
			}
			
		};
		registerEq(short.class,Long.class,converter1,PRIORITY8);
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return BigInteger.valueOf((Long)fromObj);
			}
			@Override
			public Object convert(BeancpInvocationDO invocation, BeancpContext context, double fromObj, Object toObj) {
				return (float)fromObj;
			}
			
		};
		registerEq(double.class,Float.class,converter1,PRIORITY8);
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return BigInteger.valueOf((Long)fromObj);
			}
			@Override
			public Object convert(BeancpInvocationDO invocation, BeancpContext context, double fromObj, Object toObj) {
				return (short)fromObj;
			}
			
		};
		registerEq(double.class,Short.class,converter1,PRIORITY8);
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return new BigDecimal(String.valueOf(fromObj));
			}
			@Override
			public Object convert(BeancpInvocationDO invocation, BeancpContext context, double fromObj, Object toObj) {
				return new BigDecimal(String.valueOf(fromObj));
			}
			
		};
		registerEq(double.class,BigDecimal.class,converter1,PRIORITY8);
		registerEq(Double.class,BigDecimal.class,converter1,PRIORITY8);
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return BigInteger.valueOf(((Double)fromObj).longValue());
			}
			@Override
			public Object convert(BeancpInvocationDO invocation, BeancpContext context, double fromObj, Object toObj) {
				return BigInteger.valueOf((long)fromObj);
			}
			
		};
		registerEq(double.class,BigInteger.class,converter1,PRIORITY8);
		registerEq(Double.class,BigInteger.class,converter1,PRIORITY8);
		
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return BigInteger.valueOf(((Double)fromObj).longValue());
			}
			@Override
			public Object convert(BeancpInvocationFO invocation, BeancpContext context, float fromObj, Object toObj) {
				return (double)fromObj;
			}
			@Override
			public Object convert(BeancpInvocationSO invocation, BeancpContext context, short fromObj, Object toObj) {
				return (double)fromObj;
			}
		};
		registerEq(float.class,Double.class,converter1,PRIORITY8);
		registerEq(short.class,Double.class,converter1,PRIORITY8);
		
		
		
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return BigInteger.valueOf((Long)fromObj);
			}
			@Override
			public Object convert(BeancpInvocationFO invocation, BeancpContext context, float fromObj, Object toObj) {
				return (short)fromObj;
			}
			
		};
		registerEq(float.class,Short.class,converter1,PRIORITY8);
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return new BigDecimal(String.valueOf(fromObj));
			}
			@Override
			public Object convert(BeancpInvocationFO invocation, BeancpContext context, float fromObj, Object toObj) {
				return new BigDecimal(String.valueOf(fromObj));
			}
			
		};
		registerEq(float.class,BigDecimal.class,converter1,PRIORITY8);
		registerEq(Float.class,BigDecimal.class,converter1,PRIORITY8);
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return BigInteger.valueOf(((Double)fromObj).longValue());
			}
			@Override
			public Object convert(BeancpInvocationFO invocation, BeancpContext context, float fromObj, Object toObj) {
				return BigInteger.valueOf((long)fromObj);
			}
			
		};
		registerEq(float.class,BigInteger.class,converter1,PRIORITY8);
		registerEq(Float.class,BigInteger.class,converter1,PRIORITY8);
		
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return BigInteger.valueOf(((Double)fromObj).longValue());
			}
			@Override
			public Object convert(BeancpInvocationSO invocation, BeancpContext context, short fromObj, Object toObj) {
				return (float)fromObj;
			}
		};
		registerEq(short.class,Float.class,converter1,PRIORITY8);
		
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return (Short)fromObj>0;
			}
			@Override
			public Object convert(BeancpInvocationSO invocation, BeancpContext context, short fromObj, Object toObj) {
				return fromObj>0;
			}
			@Override
			public boolean convert(BeancpInvocationOZ invocation, BeancpContext context, Object fromObj, boolean toObj) {
				return (Short)fromObj>0;
			}
		};
		registerEq(short.class,Boolean.class,converter1,PRIORITY8);
		registerEq(Short.class,boolean.class,converter1,PRIORITY8);
		registerEq(Short.class,Boolean.class,converter1,PRIORITY8);
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return (char)((Short)fromObj).intValue();
			}
			@Override
			public Object convert(BeancpInvocationSO invocation, BeancpContext context, short fromObj, Object toObj) {
				return (char)((int)fromObj);
			}
			@Override
			public char convert(BeancpInvocationOC invocation, BeancpContext context, Object fromObj, char toObj) {
				return (char)((Short)fromObj).intValue();
			}
		};
		registerEq(short.class,Character.class,converter1,PRIORITY8);
		registerEq(Short.class,char.class,converter1,PRIORITY8);
		registerEq(Short.class,Character.class,converter1,PRIORITY8);
		
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return new BigDecimal(((Short)fromObj).intValue());
			}
			@Override
			public Object convert(BeancpInvocationSO invocation, BeancpContext context, short fromObj, Object toObj) {
				return new BigDecimal(fromObj);
			}
		};
		registerEq(short.class,BigDecimal.class,converter1,PRIORITY8);
		registerEq(Short.class,BigDecimal.class,converter1,PRIORITY8);
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return BigInteger.valueOf(((Short)fromObj).longValue());
			}
			@Override
			public Object convert(BeancpInvocationSO invocation, BeancpContext context, short fromObj, Object toObj) {
				return BigInteger.valueOf(fromObj);
			}
		};
		registerEq(short.class,BigInteger.class,converter1,PRIORITY8);
		registerEq(Short.class,BigInteger.class,converter1,PRIORITY8);
		
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return (short)(fromObj.equals(Boolean.TRUE)?1:0);
			}
			@Override
			public Object convert(BeancpInvocationZO invocation, BeancpContext context, boolean fromObj, Object toObj) {
				return (short)(fromObj?1:0);
			}
			@Override
			public short convert(BeancpInvocationOS invocation, BeancpContext context, Object fromObj, short toObj) {
				return (short)(fromObj.equals(Boolean.TRUE)?1:0);
			}
		};
		registerEq(Boolean.class,short.class,converter1,PRIORITY8);
		registerEq(Boolean.class,Short.class,converter1,PRIORITY8);
		registerEq(boolean.class,Short.class,converter1,PRIORITY8);
		
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return (short)((Character)fromObj).charValue();
			}
			@Override
			public Object convert(BeancpInvocationCO invocation, BeancpContext context, char fromObj, Object toObj) {
				return (short)(fromObj);
			}
			@Override
			public short convert(BeancpInvocationOS invocation, BeancpContext context, Object fromObj, short toObj) {
				return (short)((Character)fromObj).charValue();
			}
		};
		registerEq(Character.class,short.class,converter1,PRIORITY8);
		registerEq(Character.class,Short.class,converter1,PRIORITY8);
		registerEq(char.class,Short.class,converter1,PRIORITY8);
		
		
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return fromObj.equals(Boolean.TRUE)?'1':'0';
			}
			@Override
			public Object convert(BeancpInvocationZO invocation, BeancpContext context, boolean fromObj, Object toObj) {
				return fromObj?'1':'0';
			}
		};
		registerEq(boolean.class,Character.class,converter1,PRIORITY8);
		registerEq(Boolean.class,char.class,converter1,PRIORITY8);
		
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 2;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return fromObj.equals(Boolean.TRUE)?'1':'0';
			}
			@Override
			public Object convert(BeancpInvocationCO invocation, BeancpContext context, char fromObj, Object toObj) {
		    	return fromObj=='1'||fromObj=='Y'||fromObj=='y';
			}
			@Override
			public boolean convert(BeancpInvocationOZ invocation, BeancpContext context, Object fromObj, boolean toObj) {
				char fromObj1=((Character)fromObj).charValue();
		    	return fromObj1=='1'||fromObj1=='Y'||fromObj1=='y';
			}
		};
		registerEq(Character.class,boolean.class,converter1,PRIORITY8);
		registerEq(char.class,Boolean.class,converter1,PRIORITY8);
		registerEq(BigDecimal.class,BigInteger.class,BeancpTool.create(2, (invocation,context,fromObj,toObj)->{
			return ((BigDecimal)fromObj).toBigInteger();
		}),PRIORITY8);
		registerEq(BigInteger.class,BigDecimal.class,BeancpTool.create(2, (invocation,context,fromObj,toObj)->{
			return new BigDecimal(((BigInteger)fromObj).longValue());
		}),PRIORITY8);
		//String int/Integer long/Long double/Double float/Float short/Short boolean/Boolean char/Character BigDecimal/Number BigInteger

//		registerEq(Integer.class,Long.class,BeancpTool.create(2, (invocation,context,fromObj,toObj)->{
//			return null;
//		}),PRIORITY8);
		
	}
	
	
	
	private static void register(Type fromType, Type toType, BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(toType)), converter, priority));
    }
	private static void registerEq2Extends(Type fromType, Type toType, BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createEqualMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(toType)), converter, priority));
    }
	private static void registerExtends2Eq(Type fromType, Type toType, BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createEqualMatcher(BeancpInfo.of(toType)), converter, priority));
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
