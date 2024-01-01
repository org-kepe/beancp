package org.kepe.beancp.config;

import org.kepe.beancp.ct.invocation.BeancpInvocation;

public interface BeancpCompare {
	<T,R> BeancpCompareFlag compare(BeancpInvocation invocation,T fromObj,R toObj);

	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,T fromObj,int toObj) {
		return this.compare(invocation, fromObj, (R)Integer.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,T fromObj,float toObj) {
		return this.compare(invocation, fromObj, (R)Float.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,T fromObj,boolean toObj) {
		return this.compare(invocation, fromObj, (R)Boolean.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,T fromObj,char toObj) {
		return this.compare(invocation, fromObj, (R)Character.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,T fromObj,double toObj) {
		return this.compare(invocation, fromObj, (R)Double.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,T fromObj,short toObj) {
		return this.compare(invocation, fromObj, (R)Short.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,T fromObj,byte toObj) {
		return this.compare(invocation, fromObj, (R)Byte.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,T fromObj,long toObj) {
		return this.compare(invocation, fromObj, (R)Long.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,int fromObj,R toObj) {
		return this.compare(invocation, (T)Integer.valueOf(fromObj), toObj);
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,int fromObj,int toObj) {
		return this.compare(invocation, (T)Integer.valueOf(fromObj), (R)Integer.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,int fromObj,float toObj) {
		return this.compare(invocation, (T)Integer.valueOf(fromObj), (R)Float.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,int fromObj,boolean toObj) {
		return this.compare(invocation, (T)Integer.valueOf(fromObj), (R)Boolean.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,int fromObj,char toObj) {
		return this.compare(invocation, (T)Integer.valueOf(fromObj), (R)Character.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,int fromObj,double toObj) {
		return this.compare(invocation, (T)Integer.valueOf(fromObj), (R)Double.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,int fromObj,short toObj) {
		return this.compare(invocation, (T)Integer.valueOf(fromObj), (R)Short.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,int fromObj,byte toObj) {
		return this.compare(invocation, (T)Integer.valueOf(fromObj), (R)Byte.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,int fromObj,long toObj) {
		return this.compare(invocation, (T)Integer.valueOf(fromObj), (R)Long.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,float fromObj,R toObj) {
		return this.compare(invocation, (T)Float.valueOf(fromObj), toObj);
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,float fromObj,int toObj) {
		return this.compare(invocation, (T)Float.valueOf(fromObj), (R)Integer.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,float fromObj,float toObj) {
		return this.compare(invocation, (T)Float.valueOf(fromObj), (R)Float.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,float fromObj,boolean toObj) {
		return this.compare(invocation, (T)Float.valueOf(fromObj), (R)Boolean.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,float fromObj,char toObj) {
		return this.compare(invocation, (T)Float.valueOf(fromObj), (R)Character.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,float fromObj,double toObj) {
		return this.compare(invocation, (T)Float.valueOf(fromObj), (R)Double.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,float fromObj,short toObj) {
		return this.compare(invocation, (T)Float.valueOf(fromObj), (R)Short.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,float fromObj,byte toObj) {
		return this.compare(invocation, (T)Float.valueOf(fromObj), (R)Byte.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,float fromObj,long toObj) {
		return this.compare(invocation, (T)Float.valueOf(fromObj), (R)Long.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,boolean fromObj,R toObj) {
		return this.compare(invocation, (T)Boolean.valueOf(fromObj), toObj);
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,boolean fromObj,int toObj) {
		return this.compare(invocation, (T)Boolean.valueOf(fromObj), (R)Integer.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,boolean fromObj,float toObj) {
		return this.compare(invocation, (T)Boolean.valueOf(fromObj), (R)Float.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,boolean fromObj,boolean toObj) {
		return this.compare(invocation, (T)Boolean.valueOf(fromObj), (R)Boolean.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,boolean fromObj,char toObj) {
		return this.compare(invocation, (T)Boolean.valueOf(fromObj), (R)Character.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,boolean fromObj,double toObj) {
		return this.compare(invocation, (T)Boolean.valueOf(fromObj), (R)Double.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,boolean fromObj,short toObj) {
		return this.compare(invocation, (T)Boolean.valueOf(fromObj), (R)Short.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,boolean fromObj,byte toObj) {
		return this.compare(invocation, (T)Boolean.valueOf(fromObj), (R)Byte.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,boolean fromObj,long toObj) {
		return this.compare(invocation, (T)Boolean.valueOf(fromObj), (R)Long.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,char fromObj,R toObj) {
		return this.compare(invocation, (T)Character.valueOf(fromObj), toObj);
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,char fromObj,int toObj) {
		return this.compare(invocation, (T)Character.valueOf(fromObj), (R)Integer.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,char fromObj,float toObj) {
		return this.compare(invocation, (T)Character.valueOf(fromObj), (R)Float.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,char fromObj,boolean toObj) {
		return this.compare(invocation, (T)Character.valueOf(fromObj), (R)Boolean.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,char fromObj,char toObj) {
		return this.compare(invocation, (T)Character.valueOf(fromObj), (R)Character.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,char fromObj,double toObj) {
		return this.compare(invocation, (T)Character.valueOf(fromObj), (R)Double.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,char fromObj,short toObj) {
		return this.compare(invocation, (T)Character.valueOf(fromObj), (R)Short.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,char fromObj,byte toObj) {
		return this.compare(invocation, (T)Character.valueOf(fromObj), (R)Byte.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,char fromObj,long toObj) {
		return this.compare(invocation, (T)Character.valueOf(fromObj), (R)Long.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,double fromObj,R toObj) {
		return this.compare(invocation, (T)Double.valueOf(fromObj), toObj);
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,double fromObj,int toObj) {
		return this.compare(invocation, (T)Double.valueOf(fromObj), (R)Integer.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,double fromObj,float toObj) {
		return this.compare(invocation, (T)Double.valueOf(fromObj), (R)Float.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,double fromObj,boolean toObj) {
		return this.compare(invocation, (T)Double.valueOf(fromObj), (R)Boolean.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,double fromObj,char toObj) {
		return this.compare(invocation, (T)Double.valueOf(fromObj), (R)Character.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,double fromObj,double toObj) {
		return this.compare(invocation, (T)Double.valueOf(fromObj), (R)Double.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,double fromObj,short toObj) {
		return this.compare(invocation, (T)Double.valueOf(fromObj), (R)Short.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,double fromObj,byte toObj) {
		return this.compare(invocation, (T)Double.valueOf(fromObj), (R)Byte.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,double fromObj,long toObj) {
		return this.compare(invocation, (T)Double.valueOf(fromObj), (R)Long.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,short fromObj,R toObj) {
		return this.compare(invocation, (T)Short.valueOf(fromObj), toObj);
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,short fromObj,int toObj) {
		return this.compare(invocation, (T)Short.valueOf(fromObj), (R)Integer.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,short fromObj,float toObj) {
		return this.compare(invocation, (T)Short.valueOf(fromObj), (R)Float.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,short fromObj,boolean toObj) {
		return this.compare(invocation, (T)Short.valueOf(fromObj), (R)Boolean.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,short fromObj,char toObj) {
		return this.compare(invocation, (T)Short.valueOf(fromObj), (R)Character.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,short fromObj,double toObj) {
		return this.compare(invocation, (T)Short.valueOf(fromObj), (R)Double.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,short fromObj,short toObj) {
		return this.compare(invocation, (T)Short.valueOf(fromObj), (R)Short.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,short fromObj,byte toObj) {
		return this.compare(invocation, (T)Short.valueOf(fromObj), (R)Byte.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,short fromObj,long toObj) {
		return this.compare(invocation, (T)Short.valueOf(fromObj), (R)Long.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,byte fromObj,R toObj) {
		return this.compare(invocation, (T)Byte.valueOf(fromObj), toObj);
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,byte fromObj,int toObj) {
		return this.compare(invocation, (T)Byte.valueOf(fromObj), (R)Integer.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,byte fromObj,float toObj) {
		return this.compare(invocation, (T)Byte.valueOf(fromObj), (R)Float.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,byte fromObj,boolean toObj) {
		return this.compare(invocation, (T)Byte.valueOf(fromObj), (R)Boolean.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,byte fromObj,char toObj) {
		return this.compare(invocation, (T)Byte.valueOf(fromObj), (R)Character.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,byte fromObj,double toObj) {
		return this.compare(invocation, (T)Byte.valueOf(fromObj), (R)Double.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,byte fromObj,short toObj) {
		return this.compare(invocation, (T)Byte.valueOf(fromObj), (R)Short.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,byte fromObj,byte toObj) {
		return this.compare(invocation, (T)Byte.valueOf(fromObj), (R)Byte.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,byte fromObj,long toObj) {
		return this.compare(invocation, (T)Byte.valueOf(fromObj), (R)Long.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,long fromObj,R toObj) {
		return this.compare(invocation, (T)Long.valueOf(fromObj), toObj);
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,long fromObj,int toObj) {
		return this.compare(invocation, (T)Long.valueOf(fromObj), (R)Integer.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,long fromObj,float toObj) {
		return this.compare(invocation, (T)Long.valueOf(fromObj), (R)Float.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,long fromObj,boolean toObj) {
		return this.compare(invocation, (T)Long.valueOf(fromObj), (R)Boolean.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,long fromObj,char toObj) {
		return this.compare(invocation, (T)Long.valueOf(fromObj), (R)Character.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,long fromObj,double toObj) {
		return this.compare(invocation, (T)Long.valueOf(fromObj), (R)Double.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,long fromObj,short toObj) {
		return this.compare(invocation, (T)Long.valueOf(fromObj), (R)Short.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,long fromObj,byte toObj) {
		return this.compare(invocation, (T)Long.valueOf(fromObj), (R)Byte.valueOf(toObj));
	}
	default <T,R> BeancpCompareFlag compare(BeancpInvocation invocation,long fromObj,long toObj) {
		return this.compare(invocation, (T)Long.valueOf(fromObj), (R)Long.valueOf(toObj));
	}
	
}
