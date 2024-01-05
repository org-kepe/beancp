package org.kepe.beancp.tool;

import java.util.Objects;

/**
 * Hello world!
 *
 */
public class BeancpStringTool
{
    public static boolean isEmpty(Object str){
        if(str==null||"".equals(str)){
            return true;
        }
        return false;
    }
    public static <T> T nvl(T t,T defaultValue) {
    	if(isEmpty(t)) {
    		return defaultValue;
    	}
    	return t;
    }
    
    public static boolean in(Object obj,Object... objs) {
		if(objs==null) {
			return false;
		}
		for(Object o:objs) {
			if(Objects.equals(obj, o)) {
				return true;
			}
		}
		return false;
	}
	public static boolean notIn(Object obj,Object... objs) {
		return !in(obj,objs);
	}
}
