package org.kepe.beancp;

import java.lang.reflect.Type;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.convert.BeancpContextImp;
import org.kepe.beancp.tool.BeancpTool;

/**
 * Hello world!
 *
 */
public class BeancpUtil
{
    
    public static void copy(Object fromObj,Object toObj){
        if(toObj==null){
            return;
        }
        copy(fromObj, null,toObj, null, null, null, null);
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
    public static void configAdd(BeancpFeature feature) {
    	BeancpTool.configAdd(feature);
    }
    public static void configRemove(BeancpFeature feature) {
    	BeancpTool.configRemove(feature);
    }
    
    public static BeancpContext newContext() {
    	return new BeancpContextImp();
    }
}
