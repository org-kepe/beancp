package org.kepe.beancp;

import java.lang.reflect.Type;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.convert.BeancpContextImp;
import org.kepe.beancp.exception.BeancpException;
import org.kepe.beancp.info.BeancpInfo;
import org.kepe.beancp.tool.BeancpBeanTool;
import org.kepe.beancp.tool.BeancpTool;

/**
 * Basic API
 *
 */
public class BeancpUtil
{
    /**
     * Assign properties of one object to another object
     * @param fromObj fromObj
     * @param toObj toObj
     */
    public static void copy(Object fromObj,Object toObj){
        if(toObj==null){
            return;
        }
        copy(fromObj, null,toObj, null, null, null, null);
    }
    public static void copy(Object fromObj,Object toObj,BeancpFeature feature){
        if(toObj==null){
            return;
        }
        copy(fromObj, null,toObj, null, null, feature, null);
    }
    public static void copy(Object fromObj,Object toObj,Type toType){
        if(toObj==null){
            return;
        }
        copy(fromObj, null,toObj, toType, null, null, null);
    }
    public static <T> T copy(Object fromObj,Class<T> toClass){
        return copy(fromObj,null,toClass,toClass,null,null);
    }
    public static <T> T copy(Object fromObj,Type fromType,Type toType){
    	return copy(fromObj,fromType,toType,null,null,null);
    }
    private static <T> void copy(Object fromObj,Type fromType,Object toObj,Type toType,Class<T> toClass,BeancpFeature feature,BeancpContext context){
        BeancpTool.convert(feature,context,fromType, null, fromObj, toType, toClass, toObj);
    }
    @SuppressWarnings("unchecked")
	public static <T> T copy(Object fromObj,Type fromType,Type toType,Class<T> toClass,BeancpFeature feature,BeancpContext context){
        return (T) BeancpTool.convert(feature,context,fromType, null, fromObj, toType, toClass, null);
    }
    public static <T> T copy(Object fromObj,Type toType,BeancpFeature feature,BeancpContext context){
    	return copy(fromObj,null,toType,null,null,context);
    }
    public static <T> T copy(Object fromObj,Type toType){
    	return copy(fromObj,null,toType,null,null,null);
    }
    public static <T> T copy(Object fromObj,Type toType,BeancpFeature feature){
    	return copy(fromObj,null,toType,null,feature,null);
    }
    public static <T> T copy(Object fromObj,Type toType,BeancpContext context){
    	return copy(fromObj,null,toType,null,null,context);
    }
    
    public static BeancpFeature getDefaultFeature() {
    	return BeancpTool.getDefaultFeature();
    }
    /**
     * Global feature addition
     * @param feature feature
     */
    public static void configAdd(BeancpFeature feature) {
    	BeancpTool.configAdd(feature);
    }
    /**
     * Global feature deletion
     * @param feature feature
     */
    public static void configRemove(BeancpFeature feature) {
    	BeancpTool.configRemove(feature);
    }
    /**
     * Obtain generic types, such as {@code List<String>} {@code HashMap<String,Long>}  ...
     * @param clazz the class of generic type
     * @param typeArguments Describing generic parameters in order
     * @return
     */
    public static Type type(Class<?> clazz,Type... typeArguments) {
    	return BeancpBeanTool.type(clazz,typeArguments);
    }
    /**
     * new context
     */
    public static BeancpContext newContext() {
    	return new BeancpContextImp();
    }
    /**
     * clone object
     * @param <T> 
     * @param obj
     * @return
     */
    public static <T> T clone(T obj) {
    	return clone(obj,null);
    }
    /**
     * clone object 
     * @param <T>
     * @param obj
     * @param type object type
     * @return
     */
    public static <T> T clone(T obj,Type type) {
    	return BeancpTool.clone(obj, type);
    }
    /**
     * Setting Property Value for Javabeans
     * @param obj
     * @param key
     * @param value
     */
    public static void setProperty(Object obj,String key,Object value) {
    	BeancpTool.setProperty(null, obj, key, value);
	}
    /**
     * Setting Property Value for Javabeans
     * @param type the type of object
     * @param obj
     * @param key
     * @param value
     */
    public static void setProperty(Type type,Object obj,String key,Object value) {
    	BeancpTool.setProperty(type, obj, key, value);
	}
    /**
     * Obtain Javabean property value
     * @param obj
     * @param key
     * @return
     */
    public static Object getProperty(Object obj,String key) {
    	return BeancpTool.getProperty(null, obj, key, null);
	}
    /**
     * Obtain Javabean property value
     * @param obj
     * @param key
     * @param valueType
     * @return
     */
    public static Object getProperty(Object obj,String key,Type valueType) {
    	return BeancpTool.getProperty(null, obj, key, valueType);
	}
    /**
     * Obtain Javabean property value
     * @param <T>
     * @param obj
     * @param key
     * @param valueType
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <T> T getProperty(Object obj,String key,Class<T> valueType) {
    	return (T) BeancpTool.getProperty(null, obj, key, valueType);
	}
    /**
     * Obtain Javabean property value
     * @param type
     * @param obj
     * @param key
     * @return
     */
    public static Object getProperty(Type type,Object obj,String key) {
    	return BeancpTool.getProperty(type, obj, key, null);
	}
    /**
     * Obtain Javabean property value
     * @param type
     * @param obj
     * @param key
     * @param valueType
     * @return
     */
	public static Object getProperty(Type type,Object obj,String key,Type valueType) {
		return BeancpTool.getProperty(type, obj, key, valueType);
	}
	/**
	 * Obtain Javabean property value
	 * @param <T>
	 * @param type
	 * @param obj
	 * @param key
	 * @param valueType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getProperty(Type type,Object obj,String key,Class<T> valueType) {
		return (T) BeancpTool.getProperty(type, obj, key, valueType);
	}
    
}
