package org.kepe.beancp.ct.itf;

import java.lang.reflect.Type;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.info.BeancpInfo;

import org.kepe.beancp.ct.itf.BeancpConverter;

/**
 * Hello world!
 *
 */
public interface BeancpCustomConverter<T,R> extends BeancpConverter
{
    R convert(BeancpFeature feature,BeancpContext context,T fromObj,Type fromType,Class<T> fromClass,R toObj,Type toType,Class<R> toClass);
//    default long convert(BeancpFeature feature,BeancpContext context,int fromObj,long toObj) {
//    	return (long)fromObj;
//    }
//    default long convert(BeancpFeature feature,BeancpContext context,T fromObj,Type fromType,Class<T> fromClass,long toObj) {
//    	return (Long)this.convert(feature, context, fromObj, fromType,fromClass,(R)Long.valueOf(toObj),long.class,(Class<R>)long.class);
//    }
//    default R convert(BeancpFeature feature,BeancpContext context,long fromObj,R toObj,Type toType,Class<R> toClass) {
//    	return this.convert(feature, context, (T)Long.valueOf(fromObj), long.class,(Class<T>)long.class,toObj,toType,toClass);
//    }
    
    default int convert(BeancpFeature feature,BeancpContext context,T fromObj,Type fromType,Class<T> fromClass,int toObj) {
    	return (Integer)this.convert(feature, context, fromObj, fromType,fromClass,(R)Integer.valueOf(toObj),int.class,(Class<R>)int.class);
    }
    default float convert(BeancpFeature feature,BeancpContext context,T fromObj,Type fromType,Class<T> fromClass,float toObj) {
    	return (Float)this.convert(feature, context, fromObj, fromType,fromClass,(R)Float.valueOf(toObj),float.class,(Class<R>)float.class);
    }
    default boolean convert(BeancpFeature feature,BeancpContext context,T fromObj,Type fromType,Class<T> fromClass,boolean toObj) {
    	return (Boolean)this.convert(feature, context, fromObj, fromType,fromClass,(R)Boolean.valueOf(toObj),boolean.class,(Class<R>)boolean.class);
    }
    default char convert(BeancpFeature feature,BeancpContext context,T fromObj,Type fromType,Class<T> fromClass,char toObj) {
    	return (Character)this.convert(feature, context, fromObj, fromType,fromClass,(R)Character.valueOf(toObj),char.class,(Class<R>)char.class);
    }
    default double convert(BeancpFeature feature,BeancpContext context,T fromObj,Type fromType,Class<T> fromClass,double toObj) {
    	return (Double)this.convert(feature, context, fromObj, fromType,fromClass,(R)Double.valueOf(toObj),double.class,(Class<R>)double.class);
    }
    default short convert(BeancpFeature feature,BeancpContext context,T fromObj,Type fromType,Class<T> fromClass,short toObj) {
    	return (Short)this.convert(feature, context, fromObj, fromType,fromClass,(R)Short.valueOf(toObj),short.class,(Class<R>)short.class);
    }
    default byte convert(BeancpFeature feature,BeancpContext context,T fromObj,Type fromType,Class<T> fromClass,byte toObj) {
    	return (Byte)this.convert(feature, context, fromObj, fromType,fromClass,(R)Byte.valueOf(toObj),byte.class,(Class<R>)byte.class);
    }
    default long convert(BeancpFeature feature,BeancpContext context,T fromObj,Type fromType,Class<T> fromClass,long toObj) {
    	return (Long)this.convert(feature, context, fromObj, fromType,fromClass,(R)Long.valueOf(toObj),long.class,(Class<R>)long.class);
    }
    default R convert(BeancpFeature feature,BeancpContext context,int fromObj,R toObj,Type toType,Class<R> toClass) {
    	return this.convert(feature, context, (T)Integer.valueOf(fromObj), int.class,(Class<T>)int.class,toObj,toType,toClass);
    }
    default int convert(BeancpFeature feature,BeancpContext context,int fromObj,int toObj) {
    	return fromObj;
    }
    default float convert(BeancpFeature feature,BeancpContext context,int fromObj,float toObj) {
    	return (float)fromObj;
    }
    default boolean convert(BeancpFeature feature,BeancpContext context,int fromObj,boolean toObj) {
    	return fromObj>0;
    }
    default char convert(BeancpFeature feature,BeancpContext context,int fromObj,char toObj) {
    	return (char)fromObj;
    }
    default double convert(BeancpFeature feature,BeancpContext context,int fromObj,double toObj) {
    	return (double)fromObj;
    }
    default short convert(BeancpFeature feature,BeancpContext context,int fromObj,short toObj) {
    	return (short)fromObj;
    }
    default byte convert(BeancpFeature feature,BeancpContext context,int fromObj,byte toObj) {
    	return (byte)fromObj;
    }
    default long convert(BeancpFeature feature,BeancpContext context,int fromObj,long toObj) {
    	return (long)fromObj;
    }
    default R convert(BeancpFeature feature,BeancpContext context,float fromObj,R toObj,Type toType,Class<R> toClass) {
    	return this.convert(feature, context, (T)Float.valueOf(fromObj), float.class,(Class<T>)float.class,toObj,toType,toClass);
    }
    default int convert(BeancpFeature feature,BeancpContext context,float fromObj,int toObj) {
    	return (int)fromObj;
    }
    default float convert(BeancpFeature feature,BeancpContext context,float fromObj,float toObj) {
    	return fromObj;
    }
    default boolean convert(BeancpFeature feature,BeancpContext context,float fromObj,boolean toObj) {
    	return fromObj>0;
    }
    default char convert(BeancpFeature feature,BeancpContext context,float fromObj,char toObj) {
    	return (char)fromObj;
    }
    default double convert(BeancpFeature feature,BeancpContext context,float fromObj,double toObj) {
    	return (double)fromObj;
    }
    default short convert(BeancpFeature feature,BeancpContext context,float fromObj,short toObj) {
    	return (short)fromObj;
    }
    default byte convert(BeancpFeature feature,BeancpContext context,float fromObj,byte toObj) {
    	return (byte)fromObj;
    }
    default long convert(BeancpFeature feature,BeancpContext context,float fromObj,long toObj) {
    	return (long)fromObj;
    }
    default R convert(BeancpFeature feature,BeancpContext context,boolean fromObj,R toObj,Type toType,Class<R> toClass) {
    	return this.convert(feature, context, (T)Boolean.valueOf(fromObj), boolean.class,(Class<T>)boolean.class,toObj,toType,toClass);
    }
    default int convert(BeancpFeature feature,BeancpContext context,boolean fromObj,int toObj) {
    	return fromObj?1:0;
    }
    default float convert(BeancpFeature feature,BeancpContext context,boolean fromObj,float toObj) {
    	return fromObj?1:0;
    }
    default boolean convert(BeancpFeature feature,BeancpContext context,boolean fromObj,boolean toObj) {
    	return fromObj;
    }
    default char convert(BeancpFeature feature,BeancpContext context,boolean fromObj,char toObj) {
    	return fromObj?'1':'0';
    }
    default double convert(BeancpFeature feature,BeancpContext context,boolean fromObj,double toObj) {
    	return fromObj?1:0;
    }
    default short convert(BeancpFeature feature,BeancpContext context,boolean fromObj,short toObj) {
    	return (short) (fromObj?1:0);
    }
    default byte convert(BeancpFeature feature,BeancpContext context,boolean fromObj,byte toObj) {
    	return (byte) (fromObj?1:0);
    }
    default long convert(BeancpFeature feature,BeancpContext context,boolean fromObj,long toObj) {
    	return fromObj?1:0;
    }
    default R convert(BeancpFeature feature,BeancpContext context,char fromObj,R toObj,Type toType,Class<R> toClass) {
    	return this.convert(feature, context, (T)Character.valueOf(fromObj), char.class,(Class<T>)char.class,toObj,toType,toClass);
    }
    default int convert(BeancpFeature feature,BeancpContext context,char fromObj,int toObj) {
    	return (int)fromObj;
    }
    default float convert(BeancpFeature feature,BeancpContext context,char fromObj,float toObj) {
    	return (float)fromObj;
    }
    default boolean convert(BeancpFeature feature,BeancpContext context,char fromObj,boolean toObj) {
    	return fromObj=='Y'||fromObj=='y'||fromObj=='1';
    }
    default char convert(BeancpFeature feature,BeancpContext context,char fromObj,char toObj) {
    	return fromObj;
    }
    default double convert(BeancpFeature feature,BeancpContext context,char fromObj,double toObj) {
    	return (double)fromObj;
    }
    default short convert(BeancpFeature feature,BeancpContext context,char fromObj,short toObj) {
    	return (short)fromObj;
    }
    default byte convert(BeancpFeature feature,BeancpContext context,char fromObj,byte toObj) {
    	return (byte)fromObj;
    }
    default long convert(BeancpFeature feature,BeancpContext context,char fromObj,long toObj) {
    	return (long)fromObj;
    }
    default R convert(BeancpFeature feature,BeancpContext context,double fromObj,R toObj,Type toType,Class<R> toClass) {
    	return this.convert(feature, context, (T)Double.valueOf(fromObj), double.class,(Class<T>)double.class,toObj,toType,toClass);
    }
    default int convert(BeancpFeature feature,BeancpContext context,double fromObj,int toObj) {
    	return (int)fromObj;
    }
    default float convert(BeancpFeature feature,BeancpContext context,double fromObj,float toObj) {
    	return (float)fromObj;
    }
    default boolean convert(BeancpFeature feature,BeancpContext context,double fromObj,boolean toObj) {
    	return fromObj>0;
    }
    default char convert(BeancpFeature feature,BeancpContext context,double fromObj,char toObj) {
    	return (char)fromObj;
    }
    default double convert(BeancpFeature feature,BeancpContext context,double fromObj,double toObj) {
    	return fromObj;
    }
    default short convert(BeancpFeature feature,BeancpContext context,double fromObj,short toObj) {
    	return (short)fromObj;
    }
    default byte convert(BeancpFeature feature,BeancpContext context,double fromObj,byte toObj) {
    	return (byte)fromObj;
    }
    default long convert(BeancpFeature feature,BeancpContext context,double fromObj,long toObj) {
    	return (long)fromObj;
    }
    default R convert(BeancpFeature feature,BeancpContext context,short fromObj,R toObj,Type toType,Class<R> toClass) {
    	return this.convert(feature, context, (T)Short.valueOf(fromObj), short.class,(Class<T>)short.class,toObj,toType,toClass);
    }
    default int convert(BeancpFeature feature,BeancpContext context,short fromObj,int toObj) {
    	return (int)fromObj;
    }
    default float convert(BeancpFeature feature,BeancpContext context,short fromObj,float toObj) {
    	return (float)fromObj;
    }
    default boolean convert(BeancpFeature feature,BeancpContext context,short fromObj,boolean toObj) {
    	return fromObj>0;
    }
    default char convert(BeancpFeature feature,BeancpContext context,short fromObj,char toObj) {
    	return (char)fromObj;
    }
    default double convert(BeancpFeature feature,BeancpContext context,short fromObj,double toObj) {
    	return (double)fromObj;
    }
    default short convert(BeancpFeature feature,BeancpContext context,short fromObj,short toObj) {
    	return fromObj;
    }
    default byte convert(BeancpFeature feature,BeancpContext context,short fromObj,byte toObj) {
    	return (byte)fromObj;
    }
    default long convert(BeancpFeature feature,BeancpContext context,short fromObj,long toObj) {
    	return (long)fromObj;
    }
    default R convert(BeancpFeature feature,BeancpContext context,byte fromObj,R toObj,Type toType,Class<R> toClass) {
    	return this.convert(feature, context, (T)Byte.valueOf(fromObj), byte.class,(Class<T>)byte.class,toObj,toType,toClass);
    }
    default int convert(BeancpFeature feature,BeancpContext context,byte fromObj,int toObj) {
    	return (int)fromObj;
    }
    default float convert(BeancpFeature feature,BeancpContext context,byte fromObj,float toObj) {
    	return (float)fromObj;
    }
    default boolean convert(BeancpFeature feature,BeancpContext context,byte fromObj,boolean toObj) {
    	return fromObj>0;
    }
    default char convert(BeancpFeature feature,BeancpContext context,byte fromObj,char toObj) {
    	return (char)fromObj;
    }
    default double convert(BeancpFeature feature,BeancpContext context,byte fromObj,double toObj) {
    	return (double)fromObj;
    }
    default short convert(BeancpFeature feature,BeancpContext context,byte fromObj,short toObj) {
    	return (short)fromObj;
    }
    default byte convert(BeancpFeature feature,BeancpContext context,byte fromObj,byte toObj) {
    	return fromObj;
    }
    default long convert(BeancpFeature feature,BeancpContext context,byte fromObj,long toObj) {
    	return (long)fromObj;
    }
    default R convert(BeancpFeature feature,BeancpContext context,long fromObj,R toObj,Type toType,Class<R> toClass) {
    	return this.convert(feature, context, (T)Long.valueOf(fromObj), long.class,(Class<T>)long.class,toObj,toType,toClass);
    }
    default int convert(BeancpFeature feature,BeancpContext context,long fromObj,int toObj) {
    	return (int)fromObj;
    }
    default float convert(BeancpFeature feature,BeancpContext context,long fromObj,float toObj) {
    	return (float)fromObj;
    }
    default boolean convert(BeancpFeature feature,BeancpContext context,long fromObj,boolean toObj) {
    	return fromObj>0;
    }
    default char convert(BeancpFeature feature,BeancpContext context,long fromObj,char toObj) {
    	return (char)fromObj;
    }
    default double convert(BeancpFeature feature,BeancpContext context,long fromObj,double toObj) {
    	return (double)fromObj;
    }
    default short convert(BeancpFeature feature,BeancpContext context,long fromObj,short toObj) {
    	return (short)fromObj;
    }
    default byte convert(BeancpFeature feature,BeancpContext context,long fromObj,byte toObj) {
    	return (byte)fromObj;
    }
    default long convert(BeancpFeature feature,BeancpContext context,long fromObj,long toObj) {
    	return fromObj;
    }
}
