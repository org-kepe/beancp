package org.kepe.beancp;

import java.lang.reflect.Type;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.convert.BeancpContextImp;
import org.kepe.beancp.tool.BeancpBeanTool;
import org.kepe.beancp.tool.BeancpTool;

/**
 * Hello world!
 *
 */
public class BeancpUtil
{
    /**
     * Assign properties of one object to another object
     * @param fromObj
     * @param toObj
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
    
    public static BeancpFeature getDefaultFeature() {
    	return BeancpTool.getDefaultFeature();
    }
    /**
     * Global feature addition
     * @param feature 
     */
    public static void configAdd(BeancpFeature feature) {
    	BeancpTool.configAdd(feature);
    }
    /**
     * Global feature deletion
     * @param feature
     */
    public static void configRemove(BeancpFeature feature) {
    	BeancpTool.configRemove(feature);
    }
    /**
     * Obtain generic types, such as List<String> HashMap<String,Long> ...
     * @param clazz the class of generic type
     * @param typeArguments Describing generic parameters in order
     * @return
     */
    public static Type type(Class<?> clazz,Type... typeArguments) {
    	return BeancpBeanTool.type(clazz,typeArguments);
    }
    
    public static BeancpContext newContext() {
    	return new BeancpContextImp();
    }
}
