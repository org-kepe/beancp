package org.kepe.beancp.ct;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.asm.BeancpInfoASMTool;
import org.kepe.beancp.ct.convert.BeancpConvertCustomProvider;
import org.kepe.beancp.ct.convert.BeancpConvertNonProvider;
import org.kepe.beancp.ct.converter.BeancpConverterInfo;
import org.kepe.beancp.ct.converter.BeancpConverterInfoGroup;
import org.kepe.beancp.ct.invocation.BeancpInvocationImp;
import org.kepe.beancp.ct.itf.BeancpASMConverter;
import org.kepe.beancp.ct.itf.BeancpCustomConverter;
import org.kepe.beancp.info.BeancpInfo;

/**
 * Hello world!
 *
 */
public abstract class BeancpConvertProvider
{

    private final static Map<BeancpInfo,Map<BeancpInfo,Map<BeancpFeature,BeancpConvertProvider>>> C_MAP=new ConcurrentHashMap<>();
	private static List<BeancpConverterInfo> converterList=new ArrayList<>();

    protected BeancpConverterInfo info;
    protected BeancpInfo fromInfo;
    protected BeancpInfo toInfo;
    protected BeancpFeature flag;
    protected int distance;
    private BeancpConvertProvider parent;
    private BeancpInvocationImp invocation;

    public BeancpConvertProvider(BeancpConvertProvider parent,BeancpFeature flag,BeancpConverterInfo info,BeancpInfo fromInfo,BeancpInfo toInfo){
        this.info=info;
        this.fromInfo=fromInfo;
        this.toInfo=toInfo;
        this.flag=flag;
        if(info==null) {
        	this.distance=-1;
        }else {
            this.distance=info.getConverter().distance(flag, fromInfo.getBType(), fromInfo.getBClass(), toInfo.getBType(), toInfo.getBClass());
        }
        this.parent=parent;
        this.invocation=new BeancpInvocationImp(parent,this,flag,fromInfo,toInfo);
    }
    public int getDistance() {
    	return this.distance;
    }
    
    public abstract <T,R> R convert(BeancpContext context,T fromObj,R toObj);
    public <R> R convert(BeancpContext context,int fromObj,R toObj) {
    	return convert(context,(Integer)fromObj,toObj);
    }
    public long convert(BeancpContext context,int fromObj,long toObj) {
    	return (long)fromObj;
    }
    public <T> int convert(BeancpContext context,T fromObj,int toObj) {
      	return convert(context,fromObj,(Integer)toObj);
    }
    public <T> float convert(BeancpContext context,T fromObj,float toObj) {
      	return convert(context,fromObj,(Float)toObj);
    }
    public <T> boolean convert(BeancpContext context,T fromObj,boolean toObj) {
      	return convert(context,fromObj,(Boolean)toObj);
    }
    public <T> char convert(BeancpContext context,T fromObj,char toObj) {
      	return convert(context,fromObj,(Character)toObj);
    }
    public <T> double convert(BeancpContext context,T fromObj,double toObj) {
      	return convert(context,fromObj,(Double)toObj);
    }
    public <T> short convert(BeancpContext context,T fromObj,short toObj) {
      	return convert(context,fromObj,(Short)toObj);
    }
    public <T> byte convert(BeancpContext context,T fromObj,byte toObj) {
      	return convert(context,fromObj,(Byte)toObj);
    }
    public <T> long convert(BeancpContext context,T fromObj,long toObj) {
      	return convert(context,fromObj,(Long)toObj);
    }
    public int convert(BeancpContext context,int fromObj,int toObj) {
      	return convert(context,(Integer)fromObj,(Integer)toObj);
    }
    public float convert(BeancpContext context,int fromObj,float toObj) {
      	return convert(context,(Integer)fromObj,(Float)toObj);
    }
    public boolean convert(BeancpContext context,int fromObj,boolean toObj) {
      	return convert(context,(Integer)fromObj,(Boolean)toObj);
    }
    public char convert(BeancpContext context,int fromObj,char toObj) {
      	return convert(context,(Integer)fromObj,(Character)toObj);
    }
    public double convert(BeancpContext context,int fromObj,double toObj) {
      	return convert(context,(Integer)fromObj,(Double)toObj);
    }
    public short convert(BeancpContext context,int fromObj,short toObj) {
      	return convert(context,(Integer)fromObj,(Short)toObj);
    }
    public byte convert(BeancpContext context,int fromObj,byte toObj) {
      	return convert(context,(Integer)fromObj,(Byte)toObj);
    }
    public <R> R convert(BeancpContext context,float fromObj,R toObj) {
      	return convert(context,(Float)fromObj,toObj);
    }
    public int convert(BeancpContext context,float fromObj,int toObj) {
      	return convert(context,(Float)fromObj,(Integer)toObj);
    }
    public float convert(BeancpContext context,float fromObj,float toObj) {
      	return convert(context,(Float)fromObj,(Float)toObj);
    }
    public boolean convert(BeancpContext context,float fromObj,boolean toObj) {
      	return convert(context,(Float)fromObj,(Boolean)toObj);
    }
    public char convert(BeancpContext context,float fromObj,char toObj) {
      	return convert(context,(Float)fromObj,(Character)toObj);
    }
    public double convert(BeancpContext context,float fromObj,double toObj) {
      	return convert(context,(Float)fromObj,(Double)toObj);
    }
    public short convert(BeancpContext context,float fromObj,short toObj) {
      	return convert(context,(Float)fromObj,(Short)toObj);
    }
    public byte convert(BeancpContext context,float fromObj,byte toObj) {
      	return convert(context,(Float)fromObj,(Byte)toObj);
    }
    public long convert(BeancpContext context,float fromObj,long toObj) {
      	return convert(context,(Float)fromObj,(Long)toObj);
    }
    public <R> R convert(BeancpContext context,boolean fromObj,R toObj) {
      	return convert(context,(Boolean)fromObj,toObj);
    }
    public int convert(BeancpContext context,boolean fromObj,int toObj) {
      	return convert(context,(Boolean)fromObj,(Integer)toObj);
    }
    public float convert(BeancpContext context,boolean fromObj,float toObj) {
      	return convert(context,(Boolean)fromObj,(Float)toObj);
    }
    public boolean convert(BeancpContext context,boolean fromObj,boolean toObj) {
      	return convert(context,(Boolean)fromObj,(Boolean)toObj);
    }
    public char convert(BeancpContext context,boolean fromObj,char toObj) {
      	return convert(context,(Boolean)fromObj,(Character)toObj);
    }
    public double convert(BeancpContext context,boolean fromObj,double toObj) {
      	return convert(context,(Boolean)fromObj,(Double)toObj);
    }
    public short convert(BeancpContext context,boolean fromObj,short toObj) {
      	return convert(context,(Boolean)fromObj,(Short)toObj);
    }
    public byte convert(BeancpContext context,boolean fromObj,byte toObj) {
      	return convert(context,(Boolean)fromObj,(Byte)toObj);
    }
    public long convert(BeancpContext context,boolean fromObj,long toObj) {
      	return convert(context,(Boolean)fromObj,(Long)toObj);
    }
    public <R> R convert(BeancpContext context,char fromObj,R toObj) {
      	return convert(context,(Character)fromObj,toObj);
    }
    public int convert(BeancpContext context,char fromObj,int toObj) {
      	return convert(context,(Character)fromObj,(Integer)toObj);
    }
    public float convert(BeancpContext context,char fromObj,float toObj) {
      	return convert(context,(Character)fromObj,(Float)toObj);
    }
    public boolean convert(BeancpContext context,char fromObj,boolean toObj) {
      	return convert(context,(Character)fromObj,(Boolean)toObj);
    }
    public char convert(BeancpContext context,char fromObj,char toObj) {
      	return convert(context,(Character)fromObj,(Character)toObj);
    }
    public double convert(BeancpContext context,char fromObj,double toObj) {
      	return convert(context,(Character)fromObj,(Double)toObj);
    }
    public short convert(BeancpContext context,char fromObj,short toObj) {
      	return convert(context,(Character)fromObj,(Short)toObj);
    }
    public byte convert(BeancpContext context,char fromObj,byte toObj) {
      	return convert(context,(Character)fromObj,(Byte)toObj);
    }
    public long convert(BeancpContext context,char fromObj,long toObj) {
      	return convert(context,(Character)fromObj,(Long)toObj);
    }
    public <R> R convert(BeancpContext context,double fromObj,R toObj) {
      	return convert(context,(Double)fromObj,toObj);
    }
    public int convert(BeancpContext context,double fromObj,int toObj) {
      	return convert(context,(Double)fromObj,(Integer)toObj);
    }
    public float convert(BeancpContext context,double fromObj,float toObj) {
      	return convert(context,(Double)fromObj,(Float)toObj);
    }
    public boolean convert(BeancpContext context,double fromObj,boolean toObj) {
      	return convert(context,(Double)fromObj,(Boolean)toObj);
    }
    public char convert(BeancpContext context,double fromObj,char toObj) {
      	return convert(context,(Double)fromObj,(Character)toObj);
    }
    public double convert(BeancpContext context,double fromObj,double toObj) {
      	return convert(context,(Double)fromObj,(Double)toObj);
    }
    public short convert(BeancpContext context,double fromObj,short toObj) {
      	return convert(context,(Double)fromObj,(Short)toObj);
    }
    public byte convert(BeancpContext context,double fromObj,byte toObj) {
      	return convert(context,(Double)fromObj,(Byte)toObj);
    }
    public long convert(BeancpContext context,double fromObj,long toObj) {
      	return convert(context,(Double)fromObj,(Long)toObj);
    }
    public <R> R convert(BeancpContext context,short fromObj,R toObj) {
      	return convert(context,(Short)fromObj,toObj);
    }
    public int convert(BeancpContext context,short fromObj,int toObj) {
      	return convert(context,(Short)fromObj,(Integer)toObj);
    }
    public float convert(BeancpContext context,short fromObj,float toObj) {
      	return convert(context,(Short)fromObj,(Float)toObj);
    }
    public boolean convert(BeancpContext context,short fromObj,boolean toObj) {
      	return convert(context,(Short)fromObj,(Boolean)toObj);
    }
    public char convert(BeancpContext context,short fromObj,char toObj) {
      	return convert(context,(Short)fromObj,(Character)toObj);
    }
    public double convert(BeancpContext context,short fromObj,double toObj) {
      	return convert(context,(Short)fromObj,(Double)toObj);
    }
    public short convert(BeancpContext context,short fromObj,short toObj) {
      	return convert(context,(Short)fromObj,(Short)toObj);
    }
    public byte convert(BeancpContext context,short fromObj,byte toObj) {
      	return convert(context,(Short)fromObj,(Byte)toObj);
    }
    public long convert(BeancpContext context,short fromObj,long toObj) {
      	return convert(context,(Short)fromObj,(Long)toObj);
    }
    public <R> R convert(BeancpContext context,byte fromObj,R toObj) {
      	return convert(context,(Byte)fromObj,toObj);
    }
    public int convert(BeancpContext context,byte fromObj,int toObj) {
      	return convert(context,(Byte)fromObj,(Integer)toObj);
    }
    public float convert(BeancpContext context,byte fromObj,float toObj) {
      	return convert(context,(Byte)fromObj,(Float)toObj);
    }
    public boolean convert(BeancpContext context,byte fromObj,boolean toObj) {
      	return convert(context,(Byte)fromObj,(Boolean)toObj);
    }
    public char convert(BeancpContext context,byte fromObj,char toObj) {
      	return convert(context,(Byte)fromObj,(Character)toObj);
    }
    public double convert(BeancpContext context,byte fromObj,double toObj) {
      	return convert(context,(Byte)fromObj,(Double)toObj);
    }
    public short convert(BeancpContext context,byte fromObj,short toObj) {
      	return convert(context,(Byte)fromObj,(Short)toObj);
    }
    public byte convert(BeancpContext context,byte fromObj,byte toObj) {
      	return convert(context,(Byte)fromObj,(Byte)toObj);
    }
    public long convert(BeancpContext context,byte fromObj,long toObj) {
      	return convert(context,(Byte)fromObj,(Long)toObj);
    }
    public <R> R convert(BeancpContext context,long fromObj,R toObj) {
      	return convert(context,(Long)fromObj,toObj);
    }
    public int convert(BeancpContext context,long fromObj,int toObj) {
      	return convert(context,(Long)fromObj,(Integer)toObj);
    }
    public float convert(BeancpContext context,long fromObj,float toObj) {
      	return convert(context,(Long)fromObj,(Float)toObj);
    }
    public boolean convert(BeancpContext context,long fromObj,boolean toObj) {
      	return convert(context,(Long)fromObj,(Boolean)toObj);
    }
    public char convert(BeancpContext context,long fromObj,char toObj) {
      	return convert(context,(Long)fromObj,(Character)toObj);
    }
    public double convert(BeancpContext context,long fromObj,double toObj) {
      	return convert(context,(Long)fromObj,(Double)toObj);
    }
    public short convert(BeancpContext context,long fromObj,short toObj) {
      	return convert(context,(Long)fromObj,(Short)toObj);
    }
    public byte convert(BeancpContext context,long fromObj,byte toObj) {
      	return convert(context,(Long)fromObj,(Byte)toObj);
    }
    public long convert(BeancpContext context,long fromObj,long toObj) {
      	return convert(context,(Long)fromObj,(Long)toObj);
    }
    
    //public final  <T> T convert(BeancpContext context,Object fromObj,Object toObj);

    public BeancpConvertProvider of(Object fromObj,Object toObj){
        BeancpInfo fromInfo1=this.fromInfo.of(fromObj);
        BeancpInfo toInfo1=this.toInfo.of(toObj);
        if(fromInfo1==this.fromInfo&&toInfo1==this.toInfo){
            return this;
        }
        return of(this.flag,fromInfo1,toInfo1);
    }
    public BeancpConvertProvider of(Object fromObj){
        BeancpInfo fromInfo1=this.fromInfo.of(fromObj);
        if(fromInfo1==this.fromInfo){
            return this;
        }
        return of(this.flag,fromInfo1,this.toInfo);
    }
    protected BeancpInvocationImp getInvocation() {
    	return invocation;
    }
    public static BeancpConvertProvider of(BeancpFeature flag,BeancpInfo fromInfo,BeancpInfo toInfo){
        return C_MAP.computeIfAbsent(fromInfo, key->new ConcurrentHashMap<>()).computeIfAbsent(toInfo, key->new ConcurrentHashMap<>()).computeIfAbsent(flag, key->generateProvider(flag,fromInfo,toInfo));
    }
    

    
    
    public static void register(BeancpConverterInfo info) {
    	synchronized(converterList){
            converterList.add(info);
            converterList.sort((key1,key2)->{
                return key1.getPriority()-key2.getPriority();
            });
        }
    }
    private static BeancpConvertProvider generateProvider(BeancpFeature flag,BeancpInfo fromInfo,BeancpInfo toInfo){
        int length=converterList.size();
        BeancpConvertProvider provider=new BeancpConvertNonProvider(null,flag,fromInfo,toInfo);;
        for(int i=length-1;i>=0;i--){
            BeancpConverterInfo info=converterList.get(i);
            if(info.matches(fromInfo,toInfo)){
                if(info.getConverter() instanceof BeancpCustomConverter){
                	provider = new BeancpConvertCustomProvider(provider,flag,info,fromInfo,toInfo);
                }else if(info.getConverter() instanceof BeancpASMConverter){
                	provider = BeancpInfoASMTool.generateASMProvider(provider,info,flag,fromInfo,toInfo);
                }
            }
        }
        return BeancpConvertProviderProxy.createProxy(provider);
    }
	
}
