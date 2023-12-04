package org.kepe.beancp;

import java.lang.reflect.Type;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.config.BeancpOOConverter;
import org.kepe.beancp.config.BeancpTypeConverter;
import org.kepe.beancp.config.BeancpTypeMatcher;
import org.kepe.beancp.config.BeancpTypeRelMatcher;
import org.kepe.beancp.ct.convert.BeancpContextImp;
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
     * Obtain generic types, such as {@code List<String>} is {@code BeancpUtil.type(List.class,String.class)} , {@code HashMap<String,Long>} is {@code BeancpUtil.type(HashMap.class,String.class,Long.class)} ...
     * @param clazz the class of generic type
     * @param typeArguments Describing generic parameters in order
     * @return type
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
    
	/**
	 * register a type converter
	 * @param <T>
	 * @param <R>
	 * @param fromType from object type
	 * @param toType to object type
	 * @param converter custom converter
	 * @param priority The priority of this converter should be greater than zero if there are no special requirements
	 */
	public static <T,R> void registerTypeConversion(Type fromType,Type toType,BeancpTypeConverter<T,R> converter,int priority) {
		BeancpTool.registerTypeConversion(fromType, toType, converter, priority);
	}
    /**
     * register a type converter
     * @param <T>
     * @param <R>
     * @param fromType from object type
     * @param toType to object type
     * @param distance The distance from fromType to toType. Closer distances will be prioritized for conversion, negative numbers indicate inability to convert
     * @param oconverter custom converter
     * @param priority The priority of this converter should be greater than zero if there are no special requirements
     */
    public static <T,R> void registerTypeConversion(Type fromType,Type toType,int distance, BeancpOOConverter<T,R> oconverter,int priority) {
		BeancpTool.registerTypeConversion(fromType, toType, distance, oconverter, priority);

	}
    /**
     * register a type converter
     * @param <T>
     * @param <R>
     * @param fromTypeMatcher
     * @param toTypeMatcher
     * @param converter
     * @param priority The priority of this converter should be greater than zero if there are no special requirements
     */
    public static <T,R> void registerTypeConversion(BeancpTypeMatcher fromTypeMatcher,BeancpTypeMatcher toTypeMatcher,BeancpTypeConverter<T,R> converter,int priority) {
		BeancpTool.registerTypeConversion(fromTypeMatcher, toTypeMatcher, converter, priority);
	}
    /**
     * register a type converter
     * @param <T>
     * @param <R>
     * @param fromTypeMatcher
     * @param toTypeMatcher
     * @param distance The distance from fromType to toType. Closer distances will be prioritized for conversion, negative numbers indicate inability to convert
     * @param oconverter
     * @param priority The priority of this converter should be greater than zero if there are no special requirements
     */
    public static <T,R> void registerTypeConversion(BeancpTypeMatcher fromTypeMatcher,BeancpTypeMatcher toTypeMatcher,int distance, BeancpOOConverter<T,R> oconverter,int priority) {
		BeancpTool.registerTypeConversion(fromTypeMatcher, toTypeMatcher, distance, oconverter, priority);
	}
    /**
     * register a type converter
     * @param <T>
     * @param <R>
     * @param fromTypeMatcher
     * @param toTypeMatcher
     * @param typeRelMatcher
     * @param converter
     * @param priority The priority of this converter should be greater than zero if there are no special requirements
     */
    public static <T,R> void registerTypeConversion(BeancpTypeMatcher fromTypeMatcher,BeancpTypeMatcher toTypeMatcher,BeancpTypeRelMatcher typeRelMatcher, BeancpTypeConverter<T,R> converter,int priority) {
		BeancpTool.registerTypeConversion(fromTypeMatcher, toTypeMatcher, typeRelMatcher, converter, priority);
	}
    /**
     * register a type converter
     * @param <T>
     * @param <R>
     * @param fromTypeMatcher
     * @param toTypeMatcher
     * @param typeRelMatcher
     * @param distance The distance from fromType to toType. Closer distances will be prioritized for conversion, negative numbers indicate inability to convert
     * @param oconverter
     * @param priority The priority of this converter should be greater than zero if there are no special requirements
     */
    public static <T,R> void registerTypeConversion(BeancpTypeMatcher fromTypeMatcher,BeancpTypeMatcher toTypeMatcher,BeancpTypeRelMatcher typeRelMatcher,int distance, BeancpOOConverter<T,R> oconverter,int priority) {
		BeancpTool.registerTypeConversion(fromTypeMatcher, toTypeMatcher, typeRelMatcher, distance, oconverter, priority);
	}
	
	
}
