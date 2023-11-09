package org.kepe.beancp.ct.convert;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpExceptionFilter;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.config.BeancpValueFilter;
import org.kepe.beancp.ct.BeancpConvertProvider;
import org.kepe.beancp.exception.BeancpException;
import org.kepe.beancp.info.BeancpMethodCache;

public class BeancpConvertProviderTool {
	public static boolean isAllowSet(BeancpContext context,Class clazz,String key) {
		BeancpContextImp context1=(BeancpContextImp)context;
		int state=context1.keyState(clazz, key);
		return state>=0;
	}
	protected static boolean isAllowSet(BeancpContext context,Object obj,String key) {
		BeancpContextImp context1=(BeancpContextImp)context;
		int state=context1.keyState(obj, key);
		return state>=0;
	}
	protected static boolean isAllowSet(BeancpContext context,Object obj,String key1,String key2) {
		BeancpContextImp context1=(BeancpContextImp)context;
		int state1=context1.keyState(obj, key1);
		if(state1<0) {
			return false;
		}
		int state2=context1.keyState(obj, key2);
		if(state2<0) {
			return false;
		}
		return true;
	}
//	protected static <T> T filterValue(BeancpContext context,Object obj,String key,T value) {
//		return value;
//	}
	protected static BeancpValueFilter getValueFilter(BeancpContext context,Object obj,String key) {
		BeancpContextImp context1=(BeancpContextImp)context;
		return context1.getValueFilter(obj, key);
	}
	protected static BeancpValueFilter getValueFilter(BeancpContext context,Object obj,String key1,String key2) {
		BeancpContextImp context1=(BeancpContextImp)context;
		BeancpValueFilter filter=context1.getValueFilter(obj, key1);
		if(filter==null) {
			return context1.getValueFilter(obj, key2);
		}
		return filter;
	}
	protected static BeancpValueFilter getValueFilter(BeancpContext context,Class clazz,String key) {
		BeancpContextImp context1=(BeancpContextImp)context;
		return context1.getValueFilter(clazz, key);
	}
	protected static BeancpValueFilter getValueFilter(BeancpContext context,Class clazz,String key1,String key2) {
		BeancpContextImp context1=(BeancpContextImp)context;
		BeancpValueFilter filter=context1.getValueFilter(clazz, key1);
		if(filter==null) {
			return context1.getValueFilter(clazz, key2);
		}
		return filter;
	}
	protected static BeancpValueFilter getValueFilter(BeancpContext context,Object obj,String[] keys) {
		if(keys.length==1) {
			return getValueFilter(context,obj,keys[0]);
		}
		return getValueFilter(context,obj,keys[0],keys[1]);
	}
	protected static <T> T filterValue(BeancpValueFilter filter,Object obj,String[] key,T value) {
		return filter.filterValue(obj, key[0], value);
	}
	protected static <T> T filterValue(BeancpValueFilter filter,Object obj,String key,T value) {
		return filter.filterValue(obj, key, value);
	}
	protected static <T> T filterValue(BeancpValueFilter filter,Object obj,String key1,String key2,T value) {
		return filter.filterValue(obj, key1, value);
	}
//	protected static boolean hasSetValueValid(BeancpContext context,Object obj,String key) {
//		BeancpContextImp context1=(BeancpContextImp)context;
//		return context1.hasValueFilter(obj, key);
//	}
//	protected static boolean hasSetValueValid(BeancpContext context,Object obj,String key1,String key2) {
//		BeancpContextImp context1=(BeancpContextImp)context;
//		if(context1.hasValueFilter(obj, key1)) {
//			return true;
//		}else if(context1.hasValueFilter(obj, key2)) {
//			return true;
//		}
//		return false;
//	}
//	protected static <T> T filterValue(BeancpContext context,Object obj,String key1,String key2,T value) {
//		return value;
//	}
	
	protected static void log(String message) {
		System.out.println(message);
	}
	public static void log(Throwable e) {
		e.printStackTrace();
		System.out.println("eee:"+e.getMessage());
	}
	public static Object invokeGetField(Object obj,Field field) {
		boolean accessible=field.isAccessible();
		try {
			if(!accessible) {
				field.setAccessible(true);
			}
			return field.get(obj);
		} catch (Exception e) {
			throw new BeancpException("",e);
		} finally {
			if(!accessible) {
				try {
					field.setAccessible(false);
				} catch (Exception e) {
				}
			}
		}
	}
	public static void invokeSetField(Object obj,Field field,Object value) {
		boolean accessible=field.isAccessible();
		try {
			if(!accessible) {
				field.setAccessible(true);
			}
			field.set(obj,value);
		} catch (Exception e) {
			throw new BeancpException("",e);
		} finally {
			if(!accessible) {
				try {
					field.setAccessible(false);
				} catch (Exception e) {
				}
			}
		}
	}
	public static Object invokeGetMethod(Object obj,Method method) {
		boolean accessible=method.isAccessible();
		try {
			if(!accessible) {
				method.setAccessible(true);
			}
			return method.invoke(obj);
		} catch (Exception e) {
			throw new BeancpException("",e);
		} finally {
			if(!accessible) {
				try {
					method.setAccessible(false);
				} catch (Exception e) {
				}
			}
		}
	}
	
	public static void invokeSetMethod(Object obj,Method method,Object value) {
		boolean accessible=method.isAccessible();
		try {
			if(!accessible) {
				method.setAccessible(true);
			}
			method.invoke(obj,value);
		} catch (Exception e) {
			throw new BeancpException("",e);
		} finally {
			if(!accessible) {
				try {
					method.setAccessible(false);
				} catch (Exception e) {
				}
			}
		}
	}
	
	public static Object invokeNewInstance(Constructor constructor,Object... values) {
		boolean accessible=constructor.isAccessible();
		try {
			if(!accessible) {
				constructor.setAccessible(true);
			}
			return constructor.newInstance(values);
		} catch (Exception e) {
			throw new BeancpException("",e);
		} finally {
			if(!accessible) {
				try {
					constructor.setAccessible(false);
				} catch (Exception e) {
				}
			}
		}
	}
	
	protected static void handleException(Object provider,BeancpFeature feature,BeancpContext context,String key,int handle,Exception e) {
		if(context!=null) {
			BeancpContextImp context1=(BeancpContextImp)context;
			BeancpExceptionFilter filter=context1.getExceptionFilter();
			if(filter!=null) {
				Boolean isThrowException=null;
				try {
					isThrowException=filter.isThrowException(provider, feature, context1, key, handle, e);
				} catch (Exception e1) {}
				try {
					filter.filterException(provider, feature, context, key, handle, e);
				} catch (Exception e1) {}
				
				if(isThrowException==null) {
					if(feature.is(BeancpFeature.THROW_EXCEPTION)) {
						throw new BeancpException(e.getMessage(),e);
					}
				}else {
					if(isThrowException) {
						throw new BeancpException(e.getMessage(),e);
					}
				}
			}else {
				if(feature.is(BeancpFeature.THROW_EXCEPTION)) {
					throw new BeancpException(e.getMessage(),e);
				}
			}
		}else {
			if(feature.is(BeancpFeature.THROW_EXCEPTION)) {
				throw new BeancpException(e.getMessage(),e);
			}
		}
		
		e.printStackTrace();
	}
}
