package org.kepe.beancp.ct.reg;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.kepe.beancp.BeancpUtil;
import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.BeancpConvertProvider;
import org.kepe.beancp.ct.converter.BeancpConverterInfo;
import org.kepe.beancp.ct.invocation.BeancpInvocationImp;
import org.kepe.beancp.ct.invocation.BeancpInvocationOO;
import org.kepe.beancp.ct.itf.BeancpConverter;
import org.kepe.beancp.ct.itf.BeancpCustomConverter;
import org.kepe.beancp.info.BeancpInfo;
import org.kepe.beancp.tool.BeancpBeanTool;
import org.kepe.beancp.tool.BeancpInfoMatcherTool;
import org.kepe.beancp.tool.BeancpStringTool;

public class BeancpCollectionRegisters implements BeancpRegister{
	public static void registers() {
		
		
		registerArray2Array(new BeancpCustomConverter() {
			@Override
			public int distance(BeancpFeature feature, Type fromType, Class fromClass, Type toType, Class toClass) {
				BeancpInfo fromInfo=BeancpInfo.of(fromType, fromClass);
				BeancpInfo toInfo=BeancpInfo.of(toType, toClass);
				return fromInfo.getComponentType().distance(feature, toInfo.getComponentType());
			}
			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj, Object toObj) {
				if(toObj==null) {
					BeancpInfo fromInfo=((BeancpInvocationImp)invocation).getToInfo();
					BeancpInfo toInfo=((BeancpInvocationImp)invocation).getToInfo();
					BeancpConvertProvider provider=BeancpConvertProvider.of(invocation.getFeature(), fromInfo.getComponentType(), toInfo.getComponentType());
					int len=Array.getLength(fromObj);
					toObj=Array.newInstance(toInfo.getComponentType().getBClass(), len);
					for(int i=0;i<len;i++) {
						switch(fromInfo.stage) {
						case 0:
							switch(toInfo.stage) {
							case 0:
								Object obj=Array.get(fromObj, i);
								Array.set(toObj, i, provider.of(obj).convert(context, obj, null));
								break;
							case 1:
								obj=Array.get(fromObj, i);
								Array.setInt(toObj, i, provider.of(obj).convert(context, obj, 0));
								break;
							case 2:
								obj=Array.get(fromObj, i);
								Array.setLong(toObj, i, provider.of(obj).convert(context, obj, 0L));
								break;
							case 3:
								obj=Array.get(fromObj, i);
								Array.setBoolean(toObj, i, provider.of(obj).convert(context, obj, false));
								break;
							case 4:
								obj=Array.get(fromObj, i);
								Array.setFloat(toObj, i, provider.of(obj).convert(context, obj, (float)0));
								break;
							case 5:
								obj=Array.get(fromObj, i);
								Array.setDouble(toObj, i, provider.of(obj).convert(context, obj, (double)0));
								break;
							case 6:
								obj=Array.get(fromObj, i);
								Array.setChar(toObj, i, provider.of(obj).convert(context, obj, (char)0));
								break;
							case 7:
								obj=Array.get(fromObj, i);
								Array.setShort(toObj, i, provider.of(obj).convert(context, obj, (short)0));
								break;
							case 8:
								obj=Array.get(fromObj, i);
								Array.setByte(toObj, i, provider.of(obj).convert(context, obj, (byte)0));
								break;
							}
							break;
						case 1:
							switch(toInfo.stage) {
							case 0:
								Array.set(toObj, i, provider.convert(context, Array.getInt(fromObj, i), null));
								break;
							case 1:
								Array.setInt(toObj, i, provider.convert(context, Array.getInt(fromObj, i), 0));
								break;
							case 2:
								Array.setLong(toObj, i, provider.convert(context, Array.getInt(fromObj, i), 0L));
								break;
							case 3:
								Array.setBoolean(toObj, i, provider.convert(context, Array.getInt(fromObj, i), false));
								break;
							case 4:
								Array.setFloat(toObj, i, provider.convert(context, Array.getInt(fromObj, i), (float)0));
								break;
							case 5:
								Array.setDouble(toObj, i, provider.convert(context, Array.getInt(fromObj, i), (double)0));
								break;
							case 6:
								Array.setChar(toObj, i, provider.convert(context, Array.getInt(fromObj, i), (char)0));
								break;
							case 7:
								Array.setShort(toObj, i, provider.convert(context, Array.getInt(fromObj, i), (short)0));
								break;
							case 8:
								Array.setByte(toObj, i, provider.convert(context, Array.getInt(fromObj, i), (byte)0));
								break;
							}
							break;
						case 2:
							switch(toInfo.stage) {
							case 0:
								Array.set(toObj, i, provider.convert(context, Array.getLong(fromObj, i), null));
								break;
							case 1:
								Array.setInt(toObj, i, provider.convert(context, Array.getLong(fromObj, i), 0));
								break;
							case 2:
								Array.setLong(toObj, i, provider.convert(context, Array.getLong(fromObj, i), 0L));
								break;
							case 3:
								Array.setBoolean(toObj, i, provider.convert(context, Array.getLong(fromObj, i), false));
								break;
							case 4:
								Array.setFloat(toObj, i, provider.convert(context, Array.getLong(fromObj, i), (float)0));
								break;
							case 5:
								Array.setDouble(toObj, i, provider.convert(context, Array.getLong(fromObj, i), (double)0));
								break;
							case 6:
								Array.setChar(toObj, i, provider.convert(context, Array.getLong(fromObj, i), (char)0));
								break;
							case 7:
								Array.setShort(toObj, i, provider.convert(context, Array.getLong(fromObj, i), (short)0));
								break;
							case 8:
								Array.setByte(toObj, i, provider.convert(context, Array.getLong(fromObj, i), (byte)0));
								break;
							}
							break;
						case 3:
							switch(toInfo.stage) {
							case 0:
								Array.set(toObj, i, provider.convert(context, Array.getBoolean(fromObj, i), null));
								break;
							case 1:
								Array.setInt(toObj, i, provider.convert(context, Array.getBoolean(fromObj, i), 0));
								break;
							case 2:
								Array.setLong(toObj, i, provider.convert(context, Array.getBoolean(fromObj, i), 0L));
								break;
							case 3:
								Array.setBoolean(toObj, i, provider.convert(context, Array.getBoolean(fromObj, i), false));
								break;
							case 4:
								Array.setFloat(toObj, i, provider.convert(context, Array.getBoolean(fromObj, i), (float)0));
								break;
							case 5:
								Array.setDouble(toObj, i, provider.convert(context, Array.getBoolean(fromObj, i), (double)0));
								break;
							case 6:
								Array.setChar(toObj, i, provider.convert(context, Array.getBoolean(fromObj, i), (char)0));
								break;
							case 7:
								Array.setShort(toObj, i, provider.convert(context, Array.getBoolean(fromObj, i), (short)0));
								break;
							case 8:
								Array.setByte(toObj, i, provider.convert(context, Array.getBoolean(fromObj, i), (byte)0));
								break;
							}
							break;
						case 4:
							switch(toInfo.stage) {
							case 0:
								Array.set(toObj, i, provider.convert(context, Array.getFloat(fromObj, i), null));
								break;
							case 1:
								Array.setInt(toObj, i, provider.convert(context, Array.getFloat(fromObj, i), 0));
								break;
							case 2:
								Array.setLong(toObj, i, provider.convert(context, Array.getFloat(fromObj, i), 0L));
								break;
							case 3:
								Array.setBoolean(toObj, i, provider.convert(context, Array.getFloat(fromObj, i), false));
								break;
							case 4:
								Array.setFloat(toObj, i, provider.convert(context, Array.getFloat(fromObj, i), (float)0));
								break;
							case 5:
								Array.setDouble(toObj, i, provider.convert(context, Array.getFloat(fromObj, i), (double)0));
								break;
							case 6:
								Array.setChar(toObj, i, provider.convert(context, Array.getFloat(fromObj, i), (char)0));
								break;
							case 7:
								Array.setShort(toObj, i, provider.convert(context, Array.getFloat(fromObj, i), (short)0));
								break;
							case 8:
								Array.setByte(toObj, i, provider.convert(context, Array.getFloat(fromObj, i), (byte)0));
								break;
							}
							break;
						case 5:
							switch(toInfo.stage) {
							case 0:
								Array.set(toObj, i, provider.convert(context, Array.getDouble(fromObj, i), null));
								break;
							case 1:
								Array.setInt(toObj, i, provider.convert(context, Array.getDouble(fromObj, i), 0));
								break;
							case 2:
								Array.setLong(toObj, i, provider.convert(context, Array.getDouble(fromObj, i), 0L));
								break;
							case 3:
								Array.setBoolean(toObj, i, provider.convert(context, Array.getDouble(fromObj, i), false));
								break;
							case 4:
								Array.setFloat(toObj, i, provider.convert(context, Array.getDouble(fromObj, i), (float)0));
								break;
							case 5:
								Array.setDouble(toObj, i, provider.convert(context, Array.getDouble(fromObj, i), (double)0));
								break;
							case 6:
								Array.setChar(toObj, i, provider.convert(context, Array.getDouble(fromObj, i), (char)0));
								break;
							case 7:
								Array.setShort(toObj, i, provider.convert(context, Array.getDouble(fromObj, i), (short)0));
								break;
							case 8:
								Array.setByte(toObj, i, provider.convert(context, Array.getDouble(fromObj, i), (byte)0));
								break;
							}
							break;
						case 6:
							switch(toInfo.stage) {
							case 0:
								Array.set(toObj, i, provider.convert(context, Array.getChar(fromObj, i), null));
								break;
							case 1:
								Array.setInt(toObj, i, provider.convert(context, Array.getChar(fromObj, i), 0));
								break;
							case 2:
								Array.setLong(toObj, i, provider.convert(context, Array.getChar(fromObj, i), 0L));
								break;
							case 3:
								Array.setBoolean(toObj, i, provider.convert(context, Array.getChar(fromObj, i), false));
								break;
							case 4:
								Array.setFloat(toObj, i, provider.convert(context, Array.getChar(fromObj, i), (float)0));
								break;
							case 5:
								Array.setDouble(toObj, i, provider.convert(context, Array.getChar(fromObj, i), (double)0));
								break;
							case 6:
								Array.setChar(toObj, i, provider.convert(context, Array.getChar(fromObj, i), (char)0));
								break;
							case 7:
								Array.setShort(toObj, i, provider.convert(context, Array.getChar(fromObj, i), (short)0));
								break;
							case 8:
								Array.setByte(toObj, i, provider.convert(context, Array.getChar(fromObj, i), (byte)0));
								break;
							}
							break;
						case 7:
							switch(toInfo.stage) {
							case 0:
								Array.set(toObj, i, provider.convert(context, Array.getShort(fromObj, i), null));
								break;
							case 1:
								Array.setInt(toObj, i, provider.convert(context, Array.getShort(fromObj, i), 0));
								break;
							case 2:
								Array.setLong(toObj, i, provider.convert(context, Array.getShort(fromObj, i), 0L));
								break;
							case 3:
								Array.setBoolean(toObj, i, provider.convert(context, Array.getShort(fromObj, i), false));
								break;
							case 4:
								Array.setFloat(toObj, i, provider.convert(context, Array.getShort(fromObj, i), (float)0));
								break;
							case 5:
								Array.setDouble(toObj, i, provider.convert(context, Array.getShort(fromObj, i), (double)0));
								break;
							case 6:
								Array.setChar(toObj, i, provider.convert(context, Array.getShort(fromObj, i), (char)0));
								break;
							case 7:
								Array.setShort(toObj, i, provider.convert(context, Array.getShort(fromObj, i), (short)0));
								break;
							case 8:
								Array.setByte(toObj, i, provider.convert(context, Array.getShort(fromObj, i), (byte)0));
								break;
							}
							break;
						case 8:
							switch(toInfo.stage) {
							case 0:
								Array.set(toObj, i, provider.convert(context, Array.getByte(fromObj, i), null));
								break;
							case 1:
								Array.setInt(toObj, i, provider.convert(context, Array.getByte(fromObj, i), 0));
								break;
							case 2:
								Array.setLong(toObj, i, provider.convert(context, Array.getByte(fromObj, i), 0L));
								break;
							case 3:
								Array.setBoolean(toObj, i, provider.convert(context, Array.getByte(fromObj, i), false));
								break;
							case 4:
								Array.setFloat(toObj, i, provider.convert(context, Array.getByte(fromObj, i), (float)0));
								break;
							case 5:
								Array.setDouble(toObj, i, provider.convert(context, Array.getByte(fromObj, i), (double)0));
								break;
							case 6:
								Array.setChar(toObj, i, provider.convert(context, Array.getByte(fromObj, i), (char)0));
								break;
							case 7:
								Array.setShort(toObj, i, provider.convert(context, Array.getByte(fromObj, i), (short)0));
								break;
							case 8:
								Array.setByte(toObj, i, provider.convert(context, Array.getByte(fromObj, i), (byte)0));
								break;
							}
							break;
						}
					}
					return toObj;
				}
				BeancpInfo fromInfo=((BeancpInvocationImp)invocation).getToInfo();
				BeancpInfo toInfo=((BeancpInvocationImp)invocation).getToInfo();
				BeancpConvertProvider provider=BeancpConvertProvider.of(invocation.getFeature(), fromInfo.getComponentType(), toInfo.getComponentType());
				int len=Math.min(Array.getLength(fromObj), Array.getLength(toObj));
				for(int i=0;i<len;i++) {
					switch(fromInfo.getComponentType().stage) {
					case 0:
						switch(toInfo.getComponentType().stage) {
						case 0:
							Object obj=Array.get(fromObj, i);
							Object obj2=Array.get(toObj, i);
							Array.set(toObj, i, provider.of(obj,obj2).convert(context, obj, obj2));
							break;
						case 1:
							obj=Array.get(fromObj, i);
							Array.setInt(toObj, i, provider.of(obj).convert(context, obj, Array.getInt(toObj, i)));
							break;
						case 2:
							obj=Array.get(fromObj, i);
							Array.setLong(toObj, i, provider.of(obj).convert(context, obj, Array.getLong(toObj, i)));
							break;
						case 3:
							obj=Array.get(fromObj, i);
							Array.setBoolean(toObj, i, provider.of(obj).convert(context, obj, Array.getBoolean(toObj, i)));
							break;
						case 4:
							obj=Array.get(fromObj, i);
							Array.setFloat(toObj, i, provider.of(obj).convert(context, obj, Array.getFloat(toObj, i)));
							break;
						case 5:
							obj=Array.get(fromObj, i);
							Array.setDouble(toObj, i, provider.of(obj).convert(context, obj, Array.getDouble(toObj, i)));
							break;
						case 6:
							obj=Array.get(fromObj, i);
							Array.setChar(toObj, i, provider.of(obj).convert(context, obj, Array.getChar(toObj, i)));
							break;
						case 7:
							obj=Array.get(fromObj, i);
							Array.setShort(toObj, i, provider.of(obj).convert(context, obj, Array.getShort(toObj, i)));
							break;
						case 8:
							obj=Array.get(fromObj, i);
							Array.setByte(toObj, i, provider.of(obj).convert(context, obj, Array.getByte(toObj, i)));
							break;
						}
						break;
					case 1:
						switch(toInfo.getComponentType().stage) {
						case 0:
							Object obj2=Array.get(toObj, i);
							Array.set(toObj, i, provider.of2(obj2).convert(context, Array.getInt(fromObj, i), obj2));
							break;
						case 1:
							Array.setInt(toObj, i, provider.convert(context, Array.getInt(fromObj, i), Array.getInt(toObj, i)));
							break;
						case 2:
							Array.setLong(toObj, i, provider.convert(context, Array.getInt(fromObj, i), Array.getLong(toObj, i)));
							break;
						case 3:
							Array.setBoolean(toObj, i, provider.convert(context, Array.getInt(fromObj, i), Array.getBoolean(toObj, i)));
							break;
						case 4:
							Array.setFloat(toObj, i, provider.convert(context, Array.getInt(fromObj, i), Array.getFloat(toObj, i)));
							break;
						case 5:
							Array.setDouble(toObj, i, provider.convert(context, Array.getInt(fromObj, i), Array.getDouble(toObj, i)));
							break;
						case 6:
							Array.setChar(toObj, i, provider.convert(context, Array.getInt(fromObj, i), Array.getChar(toObj, i)));
							break;
						case 7:
							Array.setShort(toObj, i, provider.convert(context, Array.getInt(fromObj, i), Array.getShort(toObj, i)));
							break;
						case 8:
							Array.setByte(toObj, i, provider.convert(context, Array.getInt(fromObj, i), Array.getByte(toObj, i)));
							break;
						}
						break;
					case 2:
						switch(toInfo.getComponentType().stage) {
						case 0:
							Object obj2=Array.get(toObj, i);
							Array.set(toObj, i, provider.of2(obj2).convert(context, Array.getLong(fromObj, i), obj2));
							break;
						case 1:
							Array.setInt(toObj, i, provider.convert(context, Array.getLong(fromObj, i), Array.getInt(toObj, i)));
							break;
						case 2:
							Array.setLong(toObj, i, provider.convert(context, Array.getLong(fromObj, i), Array.getLong(toObj, i)));
							break;
						case 3:
							Array.setBoolean(toObj, i, provider.convert(context, Array.getLong(fromObj, i), Array.getBoolean(toObj, i)));
							break;
						case 4:
							Array.setFloat(toObj, i, provider.convert(context, Array.getLong(fromObj, i), Array.getFloat(toObj, i)));
							break;
						case 5:
							Array.setDouble(toObj, i, provider.convert(context, Array.getLong(fromObj, i), Array.getDouble(toObj, i)));
							break;
						case 6:
							Array.setChar(toObj, i, provider.convert(context, Array.getLong(fromObj, i), Array.getChar(toObj, i)));
							break;
						case 7:
							Array.setShort(toObj, i, provider.convert(context, Array.getLong(fromObj, i), Array.getShort(toObj, i)));
							break;
						case 8:
							Array.setByte(toObj, i, provider.convert(context, Array.getLong(fromObj, i), Array.getByte(toObj, i)));
							break;
						}
						break;
					case 3:
						switch(toInfo.getComponentType().stage) {
						case 0:
							Object obj2=Array.get(toObj, i);
							Array.set(toObj, i, provider.of2(obj2).convert(context, Array.getBoolean(fromObj, i), obj2));
							break;
						case 1:
							Array.setInt(toObj, i, provider.convert(context, Array.getBoolean(fromObj, i), Array.getInt(toObj, i)));
							break;
						case 2:
							Array.setLong(toObj, i, provider.convert(context, Array.getBoolean(fromObj, i), Array.getLong(toObj, i)));
							break;
						case 3:
							Array.setBoolean(toObj, i, provider.convert(context, Array.getBoolean(fromObj, i), Array.getBoolean(toObj, i)));
							break;
						case 4:
							Array.setFloat(toObj, i, provider.convert(context, Array.getBoolean(fromObj, i), Array.getFloat(toObj, i)));
							break;
						case 5:
							Array.setDouble(toObj, i, provider.convert(context, Array.getBoolean(fromObj, i), Array.getDouble(toObj, i)));
							break;
						case 6:
							Array.setChar(toObj, i, provider.convert(context, Array.getBoolean(fromObj, i), Array.getChar(toObj, i)));
							break;
						case 7:
							Array.setShort(toObj, i, provider.convert(context, Array.getBoolean(fromObj, i), Array.getShort(toObj, i)));
							break;
						case 8:
							Array.setByte(toObj, i, provider.convert(context, Array.getBoolean(fromObj, i), Array.getByte(toObj, i)));
							break;
						}
						break;
					case 4:
						switch(toInfo.getComponentType().stage) {
						case 0:
							Object obj2=Array.get(toObj, i);
							Array.set(toObj, i, provider.of2(obj2).convert(context, Array.getFloat(fromObj, i), obj2));
							break;
						case 1:
							Array.setInt(toObj, i, provider.convert(context, Array.getFloat(fromObj, i), Array.getInt(toObj, i)));
							break;
						case 2:
							Array.setLong(toObj, i, provider.convert(context, Array.getFloat(fromObj, i), Array.getLong(toObj, i)));
							break;
						case 3:
							Array.setBoolean(toObj, i, provider.convert(context, Array.getFloat(fromObj, i), Array.getBoolean(toObj, i)));
							break;
						case 4:
							Array.setFloat(toObj, i, provider.convert(context, Array.getFloat(fromObj, i), Array.getFloat(toObj, i)));
							break;
						case 5:
							Array.setDouble(toObj, i, provider.convert(context, Array.getFloat(fromObj, i), Array.getDouble(toObj, i)));
							break;
						case 6:
							Array.setChar(toObj, i, provider.convert(context, Array.getFloat(fromObj, i), Array.getChar(toObj, i)));
							break;
						case 7:
							Array.setShort(toObj, i, provider.convert(context, Array.getFloat(fromObj, i), Array.getShort(toObj, i)));
							break;
						case 8:
							Array.setByte(toObj, i, provider.convert(context, Array.getFloat(fromObj, i), Array.getByte(toObj, i)));
							break;
						}
						break;
					case 5:
						switch(toInfo.getComponentType().stage) {
						case 0:
							Object obj2=Array.get(toObj, i);
							Array.set(toObj, i, provider.of2(obj2).convert(context, Array.getDouble(fromObj, i), obj2));
							break;
						case 1:
							Array.setInt(toObj, i, provider.convert(context, Array.getDouble(fromObj, i), Array.getInt(toObj, i)));
							break;
						case 2:
							Array.setLong(toObj, i, provider.convert(context, Array.getDouble(fromObj, i), Array.getLong(toObj, i)));
							break;
						case 3:
							Array.setBoolean(toObj, i, provider.convert(context, Array.getDouble(fromObj, i), Array.getBoolean(toObj, i)));
							break;
						case 4:
							Array.setFloat(toObj, i, provider.convert(context, Array.getDouble(fromObj, i), Array.getFloat(toObj, i)));
							break;
						case 5:
							Array.setDouble(toObj, i, provider.convert(context, Array.getDouble(fromObj, i), Array.getDouble(toObj, i)));
							break;
						case 6:
							Array.setChar(toObj, i, provider.convert(context, Array.getDouble(fromObj, i), Array.getChar(toObj, i)));
							break;
						case 7:
							Array.setShort(toObj, i, provider.convert(context, Array.getDouble(fromObj, i), Array.getShort(toObj, i)));
							break;
						case 8:
							Array.setByte(toObj, i, provider.convert(context, Array.getDouble(fromObj, i), Array.getByte(toObj, i)));
							break;
						}
						break;
					case 6:
						switch(toInfo.getComponentType().stage) {
						case 0:
							Object obj2=Array.get(toObj, i);
							Array.set(toObj, i, provider.of2(obj2).convert(context, Array.getChar(fromObj, i), obj2));
							break;
						case 1:
							Array.setInt(toObj, i, provider.convert(context, Array.getChar(fromObj, i), Array.getInt(toObj, i)));
							break;
						case 2:
							Array.setLong(toObj, i, provider.convert(context, Array.getChar(fromObj, i), Array.getLong(toObj, i)));
							break;
						case 3:
							Array.setBoolean(toObj, i, provider.convert(context, Array.getChar(fromObj, i), Array.getBoolean(toObj, i)));
							break;
						case 4:
							Array.setFloat(toObj, i, provider.convert(context, Array.getChar(fromObj, i), Array.getFloat(toObj, i)));
							break;
						case 5:
							Array.setDouble(toObj, i, provider.convert(context, Array.getChar(fromObj, i), Array.getDouble(toObj, i)));
							break;
						case 6:
							Array.setChar(toObj, i, provider.convert(context, Array.getChar(fromObj, i), Array.getChar(toObj, i)));
							break;
						case 7:
							Array.setShort(toObj, i, provider.convert(context, Array.getChar(fromObj, i), Array.getShort(toObj, i)));
							break;
						case 8:
							Array.setByte(toObj, i, provider.convert(context, Array.getChar(fromObj, i), Array.getByte(toObj, i)));
							break;
						}
						break;
					case 7:
						switch(toInfo.getComponentType().stage) {
						case 0:
							Object obj2=Array.get(toObj, i);
							Array.set(toObj, i, provider.of2(obj2).convert(context, Array.getShort(fromObj, i), obj2));
							break;
						case 1:
							Array.setInt(toObj, i, provider.convert(context, Array.getShort(fromObj, i), Array.getInt(toObj, i)));
							break;
						case 2:
							Array.setLong(toObj, i, provider.convert(context, Array.getShort(fromObj, i), Array.getLong(toObj, i)));
							break;
						case 3:
							Array.setBoolean(toObj, i, provider.convert(context, Array.getShort(fromObj, i), Array.getBoolean(toObj, i)));
							break;
						case 4:
							Array.setFloat(toObj, i, provider.convert(context, Array.getShort(fromObj, i), Array.getFloat(toObj, i)));
							break;
						case 5:
							Array.setDouble(toObj, i, provider.convert(context, Array.getShort(fromObj, i), Array.getDouble(toObj, i)));
							break;
						case 6:
							Array.setChar(toObj, i, provider.convert(context, Array.getShort(fromObj, i), Array.getChar(toObj, i)));
							break;
						case 7:
							Array.setShort(toObj, i, provider.convert(context, Array.getShort(fromObj, i), Array.getShort(toObj, i)));
							break;
						case 8:
							Array.setByte(toObj, i, provider.convert(context, Array.getShort(fromObj, i), Array.getByte(toObj, i)));
							break;
						}
						break;
					case 8:
						switch(toInfo.getComponentType().stage) {
						case 0:
							Object obj2=Array.get(toObj, i);
							Array.set(toObj, i, provider.of2(obj2).convert(context, Array.getByte(fromObj, i), obj2));
							break;
						case 1:
							Array.setInt(toObj, i, provider.convert(context, Array.getByte(fromObj, i), Array.getInt(toObj, i)));
							break;
						case 2:
							Array.setLong(toObj, i, provider.convert(context, Array.getByte(fromObj, i), Array.getLong(toObj, i)));
							break;
						case 3:
							Array.setBoolean(toObj, i, provider.convert(context, Array.getByte(fromObj, i), Array.getBoolean(toObj, i)));
							break;
						case 4:
							Array.setFloat(toObj, i, provider.convert(context, Array.getByte(fromObj, i), Array.getFloat(toObj, i)));
							break;
						case 5:
							Array.setDouble(toObj, i, provider.convert(context, Array.getByte(fromObj, i), Array.getDouble(toObj, i)));
							break;
						case 6:
							Array.setChar(toObj, i, provider.convert(context, Array.getByte(fromObj, i), Array.getChar(toObj, i)));
							break;
						case 7:
							Array.setShort(toObj, i, provider.convert(context, Array.getByte(fromObj, i), Array.getShort(toObj, i)));
							break;
						case 8:
							Array.setByte(toObj, i, provider.convert(context, Array.getByte(fromObj, i), Array.getByte(toObj, i)));
							break;
						}
						break;
					}
				}
				return toObj;
			}
			
		}, PRIORITY6);
		registerArray2Extends(List.class,new BeancpCustomConverter() {
			@Override
			public int distance(BeancpFeature feature, Type fromType, Class fromClass, Type toType, Class toClass) {
				BeancpInfo fromInfo=BeancpInfo.of(fromType, fromClass);
				BeancpInfo toInfo=BeancpInfo.of(toType, toClass);
				return fromInfo.getComponentType().distance(feature, BeancpStringTool.nvl(toInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO) );
			}
			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj, Object toObj) {
				if(toObj==null) {
					List list=(List) ((BeancpInvocationImp)invocation).getToInfo().getDefaultMapper().newInstance(context);
					BeancpInfo fromInfo=((BeancpInvocationImp)invocation).getToInfo();
					BeancpInfo toInfo=((BeancpInvocationImp)invocation).getToInfo();
					BeancpConvertProvider provider=BeancpConvertProvider.of(invocation.getFeature(), fromInfo.getComponentType(), BeancpStringTool.nvl(toInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO));
					int len=Array.getLength(fromObj);
					for(int i=0;i<len;i++) {
						switch(fromInfo.getComponentType().stage) {
						case 0:
							Object obj=Array.get(fromObj, i);
							list.add(provider.of(obj).convert(context, obj, null));
							break;
						case 1:
							list.add(provider.convert(context, Array.getInt(fromObj, i), null));
							break;
						case 2:
							list.add(provider.convert(context, Array.getLong(fromObj, i), null));
							break;
						case 3:
							list.add(provider.convert(context, Array.getBoolean(fromObj, i), null));
							break;
						case 4:
							list.add(provider.convert(context, Array.getFloat(fromObj, i), null));
							break;
						case 5:
							list.add(provider.convert(context, Array.getDouble(fromObj, i), null));
							break;
						case 6:
							list.add(provider.convert(context, Array.getChar(fromObj, i), null));
							break;
						case 7:
							list.add(provider.convert(context, Array.getShort(fromObj, i), null));
							break;
						case 8:
							list.add(provider.convert(context, Array.getByte(fromObj, i), null));
							break;
						}
					}
					return list;
				}
				List list=(List)toObj;
				BeancpInfo fromInfo=((BeancpInvocationImp)invocation).getToInfo();
				BeancpInfo toInfo=((BeancpInvocationImp)invocation).getToInfo();
				BeancpConvertProvider provider=BeancpConvertProvider.of(invocation.getFeature(), fromInfo.getComponentType(), BeancpStringTool.nvl(toInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO));
				int len1=Array.getLength(fromObj);
				int len2=list.size();
				for(int i=0;i<len1;i++) {
					switch(fromInfo.getComponentType().stage) {
					case 0:
						Object obj=Array.get(fromObj, i);
						if(i>=len2) {
							list.add(provider.of(obj).convert(context, obj, null));
						}else {
							list.set(i, provider.of(obj).convert(context, obj, null));
						}
						break;
					case 1:
						if(i>=len2) {
							list.add(provider.convert(context, Array.getInt(fromObj, i), null));
						}else {
							list.set(i, provider.convert(context, Array.getInt(fromObj, i), null));
						}
						break;
					case 2:
						if(i>=len2) {
							list.add(provider.convert(context, Array.getLong(fromObj, i), null));
						}else {
							list.set(i, provider.convert(context, Array.getLong(fromObj, i), null));
						}
						break;
					case 3:
						if(i>=len2) {
							list.add(provider.convert(context, Array.getBoolean(fromObj, i), null));
						}else {
							list.set(i, provider.convert(context, Array.getBoolean(fromObj, i), null));
						}
						break;
					case 4:
						if(i>=len2) {
							list.add(provider.convert(context, Array.getFloat(fromObj, i), null));
						}else {
							list.set(i, provider.convert(context, Array.getFloat(fromObj, i), null));
						}
						break;
					case 5:
						if(i>=len2) {
							list.add(provider.convert(context, Array.getDouble(fromObj, i), null));
						}else {
							list.set(i, provider.convert(context, Array.getDouble(fromObj, i), null));
						}
						break;
					case 6:
						if(i>=len2) {
							list.add(provider.convert(context, Array.getChar(fromObj, i), null));
						}else {
							list.set(i, provider.convert(context, Array.getChar(fromObj, i), null));
						}
						break;
					case 7:
						if(i>=len2) {
							list.add(provider.convert(context, Array.getShort(fromObj, i), null));
						}else {
							list.set(i, provider.convert(context, Array.getShort(fromObj, i), null));
						}
						break;
					case 8:
						if(i>=len2) {
							list.add(provider.convert(context, Array.getByte(fromObj, i), null));
						}else {
							list.set(i, provider.convert(context, Array.getByte(fromObj, i), null));
						}
						break;
					}
				}
				return list;
				
			}
		}, PRIORITY6);
		
		registerArray2Extends(Set.class,new BeancpCustomConverter() {
			@Override
			public int distance(BeancpFeature feature, Type fromType, Class fromClass, Type toType, Class toClass) {
				BeancpInfo fromInfo=BeancpInfo.of(fromType, fromClass);
				BeancpInfo toInfo=BeancpInfo.of(toType, toClass);
				return fromInfo.getComponentType().distance(feature, BeancpStringTool.nvl(toInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO) );
			}
			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj, Object toObj) {
				if(toObj==null) {
					Set list=(Set) ((BeancpInvocationImp)invocation).getToInfo().getDefaultMapper().newInstance(context);
					BeancpInfo fromInfo=((BeancpInvocationImp)invocation).getToInfo();
					BeancpInfo toInfo=((BeancpInvocationImp)invocation).getToInfo();
					BeancpConvertProvider provider=BeancpConvertProvider.of(invocation.getFeature(), fromInfo.getComponentType(), BeancpStringTool.nvl(toInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO));
					int len=Array.getLength(fromObj);
					for(int i=0;i<len;i++) {
						switch(fromInfo.getComponentType().stage) {
						case 0:
							Object obj=Array.get(fromObj, i);
							list.add(provider.of(obj).convert(context, obj, null));
							break;
						case 1:
							list.add(provider.convert(context, Array.getInt(fromObj, i), null));
							break;
						case 2:
							list.add(provider.convert(context, Array.getLong(fromObj, i), null));
							break;
						case 3:
							list.add(provider.convert(context, Array.getBoolean(fromObj, i), null));
							break;
						case 4:
							list.add(provider.convert(context, Array.getFloat(fromObj, i), null));
							break;
						case 5:
							list.add(provider.convert(context, Array.getDouble(fromObj, i), null));
							break;
						case 6:
							list.add(provider.convert(context, Array.getChar(fromObj, i), null));
							break;
						case 7:
							list.add(provider.convert(context, Array.getShort(fromObj, i), null));
							break;
						case 8:
							list.add(provider.convert(context, Array.getByte(fromObj, i), null));
							break;
						}
					}
					return list;
				}
				Set list=(Set)toObj;
				BeancpInfo fromInfo=((BeancpInvocationImp)invocation).getToInfo();
				BeancpInfo toInfo=((BeancpInvocationImp)invocation).getToInfo();
				BeancpConvertProvider provider=BeancpConvertProvider.of(invocation.getFeature(), fromInfo.getComponentType(), BeancpStringTool.nvl(toInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO));
				int len1=Array.getLength(fromObj);
				for(int i=0;i<len1;i++) {
					switch(fromInfo.getComponentType().stage) {
					case 0:
						Object obj=Array.get(fromObj, i);
						list.add(provider.of(obj).convert(context, obj, null));
						break;
					case 1:
						list.add(provider.convert(context, Array.getInt(fromObj, i), null));
						break;
					case 2:
						list.add(provider.convert(context, Array.getLong(fromObj, i), null));
						break;
					case 3:
						list.add(provider.convert(context, Array.getBoolean(fromObj, i), null));
						break;
					case 4:
						list.add(provider.convert(context, Array.getFloat(fromObj, i), null));
						break;
					case 5:
						list.add(provider.convert(context, Array.getDouble(fromObj, i), null));
						break;
					case 6:
						list.add(provider.convert(context, Array.getChar(fromObj, i), null));
						break;
					case 7:
						list.add(provider.convert(context, Array.getShort(fromObj, i), null));
						break;
					case 8:
						list.add(provider.convert(context, Array.getByte(fromObj, i), null));
						break;
					}
				}
				
				return list;
				
			}
		}, PRIORITY6);
		
		registerExtends2Array(List.class,new BeancpCustomConverter() {
			@Override
			public int distance(BeancpFeature feature, Type fromType, Class fromClass, Type toType, Class toClass) {
				BeancpInfo fromInfo=BeancpInfo.of(fromType, fromClass);
				BeancpInfo toInfo=BeancpInfo.of(toType, toClass);
				return BeancpStringTool.nvl(fromInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO) .distance(feature, toInfo.getComponentType() );
			}
			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj, Object toObj) {
				if(toObj==null) {
					BeancpInfo fromInfo=((BeancpInvocationImp)invocation).getToInfo();
					BeancpInfo toInfo=((BeancpInvocationImp)invocation).getToInfo();
					List list=(List) fromObj;
					int len1=list.size();
					toObj=Array.newInstance(toInfo.getComponentType().getBClass(), len1);
					BeancpConvertProvider provider=BeancpConvertProvider.of(invocation.getFeature(), BeancpStringTool.nvl(fromInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO),toInfo.getComponentType() );
					for(int i=0;i<len1;i++) {
						Object obj=list.get(i);
						switch(toInfo.getComponentType().stage) {
						case 0:
							Array.set(toObj, i, provider.of(obj).convert(context, obj, null));
							break;
						case 1:
							Array.setInt(toObj, i, provider.of(obj).convert(context, obj, 0));
							break;
						case 2:
							Array.setLong(toObj, i, provider.of(obj).convert(context, obj, 0L));
							break;
						case 3:
							Array.setBoolean(toObj, i, provider.of(obj).convert(context, obj, false));
							break;
						case 4:
							Array.setFloat(toObj, i, provider.of(obj).convert(context, obj, (float)0));
							break;
						case 5:
							Array.setDouble(toObj, i, provider.of(obj).convert(context, obj, (double)0));
							break;
						case 6:
							Array.setChar(toObj, i, provider.of(obj).convert(context, obj, (char)0));
							break;
						case 7:
							Array.setShort(toObj, i, provider.of(obj).convert(context, obj, (short)0));
							break;
						case 8:
							Array.setByte(toObj, i, provider.of(obj).convert(context, obj, (byte)0));
							break;
						}
					}
					return list;
				}
				BeancpInfo fromInfo=((BeancpInvocationImp)invocation).getToInfo();
				BeancpInfo toInfo=((BeancpInvocationImp)invocation).getToInfo();
				List list=(List) fromObj;
				BeancpConvertProvider provider=BeancpConvertProvider.of(invocation.getFeature(), BeancpStringTool.nvl(fromInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO),toInfo.getComponentType() );
				int len=Math.min(list.size(), Array.getLength(toObj));
				for(int i=0;i<len;i++) {
					Object obj=list.get(i);
					switch(toInfo.getComponentType().stage) {
					case 0:
						Object obj2=Array.get(toObj, i);
						Array.set(toObj, i, provider.of(obj).convert(context, obj, obj2));
						break;
					case 1:
						Array.setInt(toObj, i, provider.of(obj).convert(context, obj, Array.getInt(toObj, i)));
						break;
					case 2:
						Array.setLong(toObj, i, provider.of(obj).convert(context, obj, Array.getLong(toObj, i)));
						break;
					case 3:
						Array.setBoolean(toObj, i, provider.of(obj).convert(context, obj, Array.getBoolean(toObj, i)));
						break;
					case 4:
						Array.setFloat(toObj, i, provider.of(obj).convert(context, obj, Array.getFloat(toObj, i)));
						break;
					case 5:
						Array.setDouble(toObj, i, provider.of(obj).convert(context, obj, Array.getDouble(toObj, i)));
						break;
					case 6:
						Array.setChar(toObj, i, provider.of(obj).convert(context, obj, Array.getChar(toObj, i)));
						break;
					case 7:
						Array.setShort(toObj, i, provider.of(obj).convert(context, obj, Array.getShort(toObj, i)));
						break;
					case 8:
						Array.setByte(toObj, i, provider.of(obj).convert(context, obj, Array.getByte(toObj, i)));
						break;
					}
				}
				return list;
				
			}
		}, PRIORITY6);
		
		registerExtends2Array(Set.class,new BeancpCustomConverter() {
			@Override
			public int distance(BeancpFeature feature, Type fromType, Class fromClass, Type toType, Class toClass) {
				BeancpInfo fromInfo=BeancpInfo.of(fromType, fromClass);
				BeancpInfo toInfo=BeancpInfo.of(toType, toClass);
				return BeancpStringTool.nvl(fromInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO) .distance(feature, toInfo.getComponentType() );
			}
			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj, Object toObj) {
				if(toObj==null) {
					BeancpInfo fromInfo=((BeancpInvocationImp)invocation).getToInfo();
					BeancpInfo toInfo=((BeancpInvocationImp)invocation).getToInfo();
					Set list=(Set) fromObj;
					int len1=list.size();
					toObj=Array.newInstance(toInfo.getComponentType().getBClass(), len1);
					BeancpConvertProvider provider=BeancpConvertProvider.of(invocation.getFeature(), BeancpStringTool.nvl(fromInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO),toInfo.getComponentType() );
					int i=0;
					for(Object obj:list) {
						switch(toInfo.getComponentType().stage) {
						case 0:
							Array.set(toObj, i, provider.of(obj).convert(context, obj, null));
							break;
						case 1:
							Array.setInt(toObj, i, provider.of(obj).convert(context, obj, 0));
							break;
						case 2:
							Array.setLong(toObj, i, provider.of(obj).convert(context, obj, 0L));
							break;
						case 3:
							Array.setBoolean(toObj, i, provider.of(obj).convert(context, obj, false));
							break;
						case 4:
							Array.setFloat(toObj, i, provider.of(obj).convert(context, obj, (float)0));
							break;
						case 5:
							Array.setDouble(toObj, i, provider.of(obj).convert(context, obj, (double)0));
							break;
						case 6:
							Array.setChar(toObj, i, provider.of(obj).convert(context, obj, (char)0));
							break;
						case 7:
							Array.setShort(toObj, i, provider.of(obj).convert(context, obj, (short)0));
							break;
						case 8:
							Array.setByte(toObj, i, provider.of(obj).convert(context, obj, (byte)0));
							break;
						}
						i++;
					}
					return toObj;
				}
				BeancpInfo fromInfo=((BeancpInvocationImp)invocation).getToInfo();
				BeancpInfo toInfo=((BeancpInvocationImp)invocation).getToInfo();
				Set list=(Set) fromObj;
				BeancpConvertProvider provider=BeancpConvertProvider.of(invocation.getFeature(), BeancpStringTool.nvl(fromInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO),toInfo.getComponentType() );
				int i=0;
				for(Object obj:list) {
					switch(toInfo.getComponentType().stage) {
					case 0:
						Object obj2=Array.get(toObj, i);
						Array.set(toObj, i, provider.of(obj).convert(context, obj, obj2));
						break;
					case 1:
						Array.setInt(toObj, i, provider.of(obj).convert(context, obj, Array.getInt(toObj, i)));
						break;
					case 2:
						Array.setLong(toObj, i, provider.of(obj).convert(context, obj, Array.getLong(toObj, i)));
						break;
					case 3:
						Array.setBoolean(toObj, i, provider.of(obj).convert(context, obj, Array.getBoolean(toObj, i)));
						break;
					case 4:
						Array.setFloat(toObj, i, provider.of(obj).convert(context, obj, Array.getFloat(toObj, i)));
						break;
					case 5:
						Array.setDouble(toObj, i, provider.of(obj).convert(context, obj, Array.getDouble(toObj, i)));
						break;
					case 6:
						Array.setChar(toObj, i, provider.of(obj).convert(context, obj, Array.getChar(toObj, i)));
						break;
					case 7:
						Array.setShort(toObj, i, provider.of(obj).convert(context, obj, Array.getShort(toObj, i)));
						break;
					case 8:
						Array.setByte(toObj, i, provider.of(obj).convert(context, obj, Array.getByte(toObj, i)));
						break;
					}
					i++;
				}
				return toObj;
				
			}
		}, PRIORITY6);
		
		
		register(List.class,List.class,new BeancpCustomConverter() {
			@Override
			public int distance(BeancpFeature feature, Type fromType, Class fromClass, Type toType, Class toClass) {
				BeancpInfo fromInfo=BeancpInfo.of(fromType, fromClass);
				BeancpInfo toInfo=BeancpInfo.of(toType, toClass);
				return BeancpStringTool.nvl(fromInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO).distance(feature, BeancpStringTool.nvl(toInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO) );
			}
			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj, Object toObj) {
				if(toObj==null) {
					BeancpInfo fromInfo=((BeancpInvocationImp)invocation).getToInfo();
					BeancpInfo toInfo=((BeancpInvocationImp)invocation).getToInfo();
					BeancpConvertProvider provider=BeancpConvertProvider.of(invocation.getFeature(), BeancpStringTool.nvl(fromInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO),BeancpStringTool.nvl(toInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO) );

					List list=(List) fromObj;
					int len1=list.size();
					List list2=(List) toInfo.getDefaultMapper().newInstance(context,len1);
					if(list2==null) {
						list2=(List) toInfo.getDefaultMapper().newInstance(context);
					}
					for(int i=0;i<len1;i++) {
						Object obj=list.get(i);
						list2.add(provider.of(obj).convert(context, obj, null));
					}
					return list2;
					
				}
				BeancpInfo fromInfo=((BeancpInvocationImp)invocation).getToInfo();
				BeancpInfo toInfo=((BeancpInvocationImp)invocation).getToInfo();
				List list=(List) fromObj;
				List list2=(List) toObj;
				BeancpConvertProvider provider=BeancpConvertProvider.of(invocation.getFeature(), BeancpStringTool.nvl(fromInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO),BeancpStringTool.nvl(toInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO) );
				int len1=list.size();
				int len2=list2.size();
				for(int i=0;i<len1;i++) {
					if(i>=len2) {
						Object obj=list.get(i);
						list2.add(provider.of(obj).convert(context, obj, null));
					}else {
						Object obj=list.get(i);
						Object obj2=list2.get(i);
						list2.set(i,provider.of(obj,obj2).convert(context, obj, obj2));
					}
				}
				
				return list2;
				
			}
		}, PRIORITY6);
		
		register(List.class,Set.class,new BeancpCustomConverter() {
			@Override
			public int distance(BeancpFeature feature, Type fromType, Class fromClass, Type toType, Class toClass) {
				BeancpInfo fromInfo=BeancpInfo.of(fromType, fromClass);
				BeancpInfo toInfo=BeancpInfo.of(toType, toClass);
				return BeancpStringTool.nvl(fromInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO).distance(feature, BeancpStringTool.nvl(toInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO) );
			}
			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj, Object toObj) {
				if(toObj==null) {
					BeancpInfo fromInfo=((BeancpInvocationImp)invocation).getToInfo();
					BeancpInfo toInfo=((BeancpInvocationImp)invocation).getToInfo();
					BeancpConvertProvider provider=BeancpConvertProvider.of(invocation.getFeature(), BeancpStringTool.nvl(fromInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO),BeancpStringTool.nvl(toInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO) );

					List list=(List) fromObj;
					int len1=list.size();
					Set list2=(Set) toInfo.getDefaultMapper().newInstance(context);
					for(int i=0;i<len1;i++) {
						Object obj=list.get(i);
						list2.add(provider.of(obj).convert(context, obj, null));
					}
					return list2;
					
				}
				BeancpInfo fromInfo=((BeancpInvocationImp)invocation).getToInfo();
				BeancpInfo toInfo=((BeancpInvocationImp)invocation).getToInfo();
				List list=(List) fromObj;
				Set list2=(Set) toObj;
				BeancpConvertProvider provider=BeancpConvertProvider.of(invocation.getFeature(), BeancpStringTool.nvl(fromInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO),BeancpStringTool.nvl(toInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO) );
				int len1=list.size();
				int len2=list2.size();
				for(int i=0;i<len1;i++) {
					Object obj=list.get(i);
					list2.add(provider.of(obj).convert(context, obj, null));
				}
				return list2;
				
			}
		}, PRIORITY6);
		
		register(Set.class,List.class,new BeancpCustomConverter() {
			@Override
			public int distance(BeancpFeature feature, Type fromType, Class fromClass, Type toType, Class toClass) {
				BeancpInfo fromInfo=BeancpInfo.of(fromType, fromClass);
				BeancpInfo toInfo=BeancpInfo.of(toType, toClass);
				return BeancpStringTool.nvl(fromInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO).distance(feature, BeancpStringTool.nvl(toInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO) );
			}
			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj, Object toObj) {
				if(toObj==null) {
					BeancpInfo fromInfo=((BeancpInvocationImp)invocation).getToInfo();
					BeancpInfo toInfo=((BeancpInvocationImp)invocation).getToInfo();
					BeancpConvertProvider provider=BeancpConvertProvider.of(invocation.getFeature(), BeancpStringTool.nvl(fromInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO),BeancpStringTool.nvl(toInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO) );

					Set list=(Set) fromObj;
					int len1=list.size();
					List list2=(List) toInfo.getDefaultMapper().newInstance(context,len1);
					if(list2==null) {
						list2=(List) toInfo.getDefaultMapper().newInstance(context);
					}
					for(Object obj:list) {
						list2.add(provider.of(obj).convert(context, obj, null));
					}
					return list2;
				}
				BeancpInfo fromInfo=((BeancpInvocationImp)invocation).getToInfo();
				BeancpInfo toInfo=((BeancpInvocationImp)invocation).getToInfo();
				Set list=(Set) fromObj;
				List list2=(List) toObj;
				BeancpConvertProvider provider=BeancpConvertProvider.of(invocation.getFeature(), BeancpStringTool.nvl(fromInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO),BeancpStringTool.nvl(toInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO) );
				int len1=list.size();
				int len2=list2.size();
				int i=0;
				for(Object obj:list) {
					if(i>=len2) {
						list2.add(provider.of(obj).convert(context, obj, null));
					}else {
						Object obj2=list2.get(i);
						list2.set(i,provider.of(obj,obj2).convert(context, obj, obj2));
					}
					i++;
				}
				return list2;
				
			}
		}, PRIORITY6);
		
		register(Set.class,Set.class,new BeancpCustomConverter() {
			@Override
			public int distance(BeancpFeature feature, Type fromType, Class fromClass, Type toType, Class toClass) {
				BeancpInfo fromInfo=BeancpInfo.of(fromType, fromClass);
				BeancpInfo toInfo=BeancpInfo.of(toType, toClass);
				return BeancpStringTool.nvl(fromInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO).distance(feature, BeancpStringTool.nvl(toInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO) );
			}
			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj, Object toObj) {
				if(toObj==null) {
					BeancpInfo fromInfo=((BeancpInvocationImp)invocation).getToInfo();
					BeancpInfo toInfo=((BeancpInvocationImp)invocation).getToInfo();
					BeancpConvertProvider provider=BeancpConvertProvider.of(invocation.getFeature(), BeancpStringTool.nvl(fromInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO),BeancpStringTool.nvl(toInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO) );

					Set list=(Set) fromObj;
					int len1=list.size();
					Set list2=(Set) toInfo.getDefaultMapper().newInstance(context);
					for(Object obj:list) {
						list2.add(provider.of(obj).convert(context, obj, null));
					}
					return list2;
				}
				BeancpInfo fromInfo=((BeancpInvocationImp)invocation).getToInfo();
				BeancpInfo toInfo=((BeancpInvocationImp)invocation).getToInfo();
				Set list=(Set) fromObj;
				Set list2=(Set) toObj;
				BeancpConvertProvider provider=BeancpConvertProvider.of(invocation.getFeature(), BeancpStringTool.nvl(fromInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO),BeancpStringTool.nvl(toInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO) );
				int len1=list.size();
				int len2=list2.size();
				for(Object obj:list) {
					list2.add(provider.of(obj).convert(context, obj, null));
				}
				return list2;
				
			}
		}, PRIORITY6);
		
		register(Map.class,Set.class,new BeancpCustomConverter() {
			@Override
			public int distance(BeancpFeature feature, Type fromType, Class fromClass, Type toType, Class toClass) {
				BeancpInfo fromInfo=BeancpInfo.of(fromType, fromClass);
				BeancpInfo toInfo=BeancpInfo.of(toType, toClass);
				return BeancpStringTool.nvl(fromInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO).distance(feature, BeancpStringTool.nvl(toInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO) );
			}
			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj, Object toObj) {
				BeancpInfo fromInfo=((BeancpInvocationImp)invocation).getToInfo();
				BeancpInfo toInfo=((BeancpInvocationImp)invocation).getToInfo();
				BeancpConvertProvider provider=BeancpConvertProvider.of(invocation.getFeature(), getSetType(BeancpStringTool.nvl(fromInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO)),BeancpStringTool.nvl(toInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO) );
				return provider.of(fromObj, toObj).convert(context, fromObj, toObj);
			}
		}, PRIORITY6);
		
		register(Map.class,Map.class,new BeancpCustomConverter() {
			@Override
			public int distance(BeancpFeature feature, Type fromType, Class fromClass, Type toType, Class toClass) {
				BeancpInfo fromInfo=BeancpInfo.of(fromType, fromClass);
				BeancpInfo toInfo=BeancpInfo.of(toType, toClass);
				int d0= BeancpStringTool.nvl(fromInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO).distance(feature, BeancpStringTool.nvl(toInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO) );
				if(d0<0) {
					return d0;
				}
				int d1=BeancpStringTool.nvl(fromInfo.getGenericInfo(1), BeancpInfo.OBJECT_INFO).distance(feature, BeancpStringTool.nvl(toInfo.getGenericInfo(1), BeancpInfo.OBJECT_INFO) );
				if(d1<0) {
					return d1;
				}
				return d0+d1;
			}
			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj, Object toObj) {
				BeancpInfo fromInfo=((BeancpInvocationImp)invocation).getToInfo();
				BeancpInfo toInfo=((BeancpInvocationImp)invocation).getToInfo();
				BeancpConvertProvider provider1=BeancpConvertProvider.of(invocation.getFeature(), BeancpStringTool.nvl(fromInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO),BeancpStringTool.nvl(toInfo.getGenericInfo(0), BeancpInfo.OBJECT_INFO) );
				BeancpConvertProvider provider2=BeancpConvertProvider.of(invocation.getFeature(), BeancpStringTool.nvl(fromInfo.getGenericInfo(1), BeancpInfo.OBJECT_INFO),BeancpStringTool.nvl(toInfo.getGenericInfo(1), BeancpInfo.OBJECT_INFO) );
				Map<Object,Object> map=(Map) fromObj;
				if(toObj==null) {
					Map map2=(Map) toInfo.getDefaultMapper().newInstance(context);
					for(Map.Entry<Object,Object> e: map.entrySet()) {
						Object key=e.getKey();
						Object value=e.getValue();
						map2.put(provider1.of(key).convert(context, key, null), provider2.of(value).convert(context, value, null));
					}
					return map2;
				}
				Map map2=(Map) toObj;
				for(Map.Entry<Object,Object> e: map.entrySet()) {
					Object key=e.getKey();
					Object value=e.getValue();
					Object k=provider1.of(key).convert(context, key, null);
					map2.put(k, provider2.of(value).convert(context, value, map2.get(k)));
				}
				return map2;
			}
		}, PRIORITY6);
		//array List Set Map
		
		
	}
	
	private static void register(Type fromType, Type toType, BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(toType)), converter, priority));
    }
	private static void registerExtends2Array(Type fromType,BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createArrayMatcher(), converter, priority));
    }
	private static void registerArray2Extends(Type toType, BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createArrayMatcher(), BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(toType)), converter, priority));
    }
	private static void registerArray2Array( BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createArrayMatcher(), BeancpInfoMatcherTool.createArrayMatcher(), converter, priority));
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
    private static final Map<BeancpInfo,BeancpInfo> C_MAP=new ConcurrentHashMap<>();
	private static BeancpInfo getSetType(BeancpInfo info) {
		return C_MAP.computeIfAbsent(info, key->{
			return BeancpInfo.of(BeancpBeanTool.newParameterizedTypeWithOwner(null, Set.class, info.getBType()));
		});
	}
}
