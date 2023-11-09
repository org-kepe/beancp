package org.kepe.beancp.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.kepe.beancp.config.BeancpFeature;

/**
 * Hello world!
 *
 */
public class BeancpFeature
{
    private final static Map<Long,BeancpFeature> FLAGCACHE_MAP=new ConcurrentHashMap<>();
    //javabean转换为map时优先使用下滑线
    public final static BeancpFeature BEAN2MAP_UNDERLINE = getFlag(1);
    //即使源属性是空也进行赋值
    public final static BeancpFeature SETVALUE_WHENNOTNULL = getFlag(1<<1);
    //赋值新对象即使与原对象类型一致
    public final static BeancpFeature ALLWAYS_NEW = getFlag(1<<2);
    //只有类型完全一致时才赋值
    public final static BeancpFeature SETVALUE_TYPEEQUALS = getFlag(1<<3);
    //包括受保护的属性和默认权限属性
    public final static BeancpFeature ACCESS_PROTECTED = getFlag(1<<4);
    //包括受保护的属性和默认权限属性
    public final static BeancpFeature ACCESS_PRIVATE = getFlag(1<<5);
    //遇到异常是否抛出
    public final static BeancpFeature THROW_EXCEPTION = getFlag(1<<6);
    public final static BeancpFeature BEAN2MAP_UNDERLINE_UPPER = getFlag(1<<7);
    private final long flag;
    private final static BeancpFeature FEATURE0=getFlag(0);
    public final static BeancpFeature DEFAULT_FEATURE=FEATURE0;
    public static BeancpFeature newFlag(){
        return FEATURE0;
    }
    private BeancpFeature(Long flag){
        this.flag=flag;
    }
    public BeancpFeature add(BeancpFeature feature){
        return getFlag(this.flag|feature.flag);
    }
    public BeancpFeature remove(BeancpFeature feature){
        return getFlag(this.flag&~feature.flag);
    }
    
    public boolean is(BeancpFeature feature) {
    	return (this.flag&feature.flag)==feature.flag;
    }
    private boolean is(int flag) {
    	return (this.flag&flag)==flag;
    }
    private static BeancpFeature getFlag(long flag){
    	return new BeancpFeature(flag);
    }
    
    @Override
    public int hashCode() {
    	return Long.hashCode(flag);
    }
    
    @Override
    public boolean equals(Object obj) {
    	if(obj instanceof BeancpFeature) {
    		return this.flag==((BeancpFeature)obj).flag;
    	}
    	return false;
    }
    
}
