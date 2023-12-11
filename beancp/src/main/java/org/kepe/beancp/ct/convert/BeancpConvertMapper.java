package org.kepe.beancp.ct.convert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.config.BeancpValueFilter;
import org.kepe.beancp.ct.BeancpConvertProvider;
import org.kepe.beancp.ct.asm.BeancpInfoASMTool;
import org.kepe.beancp.ct.invocation.BeancpInvocationII;
import org.kepe.beancp.exception.BeancpException;
import org.kepe.beancp.info.BeancpFieldInfo;
import org.kepe.beancp.info.BeancpGetInfo;
import org.kepe.beancp.info.BeancpInfo;
import org.kepe.beancp.info.BeancpInitInfo;
import org.kepe.beancp.info.BeancpName;
import org.kepe.beancp.info.BeancpSetInfo;
import org.kepe.beancp.tool.BeancpStringTool;
import org.kepe.beancp.tool.vo.Tuple2;

public abstract class BeancpConvertMapper {
    private final static Map<BeancpInfo,Map<BeancpFeature,BeancpConvertMapper>> C_MAP=new ConcurrentHashMap<>();

	protected BeancpInfo info;
	private boolean isMap;
	protected BeancpFeature feature;
	protected Set<String> getKeys;
	protected Set<BeancpName> getKeys2=new HashSet<>();
	protected Set<String> setKeys;
	protected Map<String,Tuple2<BeancpFieldInfo,Integer>> fields;
	protected List<String> keys;
	protected List<BeancpInitInfo> inits=new ArrayList<>();
	protected boolean isAllwaysNew;
	protected boolean isSetValueWhenNull;
	protected boolean isForceEquals;
	protected boolean isBean2MapUnderline;
	protected boolean isBean2MapUnderlineUpper;
	//protected BeancpInfo[] keysInfo;
	public BeancpConvertMapper(BeancpInfo info,BeancpFeature feature,Set<String> getKeys,Set<String> setKeys,Map<String,Tuple2<BeancpFieldInfo,Integer>> fields,List<String> keys,List<BeancpInitInfo> inits) {
		this.info=info;
		this.isMap=info.isMap;
		this.feature=feature;
		if(feature.is(BeancpFeature.ACCESS_PRIVATE)||feature.is(BeancpFeature.ACCESS_PROTECTED)) {
			info.initProxyOpClass();
		}
		if(info.cloneInfo!=null&&info.cloneInfo.needProxy()) {
			info.initProxyOpClass();
		}
		this.getKeys=Collections.unmodifiableSet(getKeys);
		for(String key:getKeys) {
			getKeys2.add(BeancpName.of(key));
		}
		this.setKeys=Collections.unmodifiableSet(setKeys);
		this.fields=fields;
		this.keys=keys;
		this.inits=inits;
		isAllwaysNew=feature.is(BeancpFeature.ALLWAYS_NEW);
		isSetValueWhenNull=!feature.is(BeancpFeature.SETVALUE_WHENNOTNULL);
		isForceEquals=feature.is(BeancpFeature.SETVALUE_TYPEEQUALS);
		isBean2MapUnderline=feature.is(BeancpFeature.BEAN2MAP_UNDERLINE);
		isBean2MapUnderlineUpper=feature.is(BeancpFeature.BEAN2MAP_UNDERLINE_UPPER);
	}
	
	public static BeancpConvertMapper of(BeancpInfo info,BeancpFeature feature) {
		return C_MAP.computeIfAbsent(info, key->new ConcurrentHashMap<>()).computeIfAbsent(feature, key->BeancpInfoASMTool.generateASMMapper(info, feature));
	}
	
	public Object newInstance(BeancpContext context,Object... args) {
		int initsLength=this.inits.size();
		if(initsLength==0) {
			return null;
		}
		try {
			if(args.length==0&&this.inits.get(0).getParams().size()==0) {
				return this.newInstance(0, args, (BeancpInfo[])null, context);
			}
			long minscore=Long.MAX_VALUE;
			int initIdx=-1;
			BeancpInfo[] fargInfos=null;
			for(int i=0;i<initsLength;i++) {
				BeancpInitInfo initInfo=this.inits.get(i);
				if(!initInfo.isUseful(feature, context)) {
					continue;
				}
				if(args.length!=initInfo.getParams().size()) {
					continue;
				}
				boolean allHave=true;
				long score=0;
				int pi=0;
				BeancpInfo[] argInfos=new BeancpInfo[args.length];
				for(Entry<String, BeancpInfo> e:initInfo.getParams().entrySet()) {
					//String key=e.getKey();
					
					BeancpInfo paramInfo=e.getValue();
					BeancpInfo argInfo=BeancpInfo.OBJECT_INFO.of(args[pi]);
					argInfos[pi]=argInfo;
					long pscore=argInfo.distance(feature, paramInfo);
					if(pscore<0) {
						allHave=false;
						break;
					}
					score+=pscore;
					pi++;
				}
				if(allHave) {
					if(score==0L) {
						initIdx=i;
						fargInfos=argInfos;
						break;
					}else if(score>0L&&score<minscore){
						minscore=score;
						initIdx=i;
						fargInfos=argInfos;
					}
				}
			}
			if(initIdx>=0) {
				return this.newInstance(initIdx, args, fargInfos, context);
			}
			return null;
		} catch (Exception e) {
			BeancpConvertProviderTool.handleException(this, feature, context, "", 0, e);
			return null;
		}
	}
	
	public Object newInstance(BeancpContext context,Map argsMap) {
		int initsLength=this.inits.size();
		if(initsLength==0) {
			return null;
		}
		try {
			if(this.inits.get(0).getParams().isEmpty()) {
				return this.newInstance(0, null, (BeancpInfo[])null, context);
			}
			if(argsMap==null) {
				return null;
			}
			long minscore=Long.MAX_VALUE;
			int initIdx=-1;
			Object[] fargs=null;
			BeancpInfo[] fargInfos=null;
			for(int i=0;i<initsLength;i++) {
				BeancpInitInfo initInfo=this.inits.get(0);
				if(!initInfo.isUseful(feature, context)) {
					continue;
				}
				boolean allHave=true;
				long score=0;
				Object[] args=new Object[initInfo.getParams().size()];
				BeancpInfo[] argInfos=new BeancpInfo[initInfo.getParams().size()];
				int pi=0;
				for(Entry<String, BeancpInfo> e:initInfo.getParams().entrySet()) {
					String key=e.getKey();
					BeancpInfo paramInfo=e.getValue();
					String mapkey=mapContainsKey(argsMap,e.getKey());
					if(mapkey==null) {
						allHave=false;
						break;
					}
					Object mapvalue=argsMap.get(mapkey);
					args[pi]=mapvalue;
					BeancpInfo argInfo=BeancpInfo.OBJECT_INFO.of(mapvalue);
					argInfos[pi]=argInfo;
					long pscore=argInfo.distance(feature, paramInfo);
					if(pscore<0) {
						allHave=false;
						break;
					}
					score+=pscore;
					pi++;
				}
				if(allHave) {
					if(score==0L) {
						initIdx=i;
						fargs=args;
						fargInfos=argInfos;
						break;
					}else if(score>0L&&score<minscore){
						minscore=score;
						initIdx=i;
						fargs=args;
						fargInfos=argInfos;
					}
				}
			}
			if(initIdx>=0) {
				return newInstance(initIdx,fargs,fargInfos,context);
			}
			return null;
		} catch (Exception e) {
			BeancpConvertProviderTool.handleException(this, feature, context, "", 0, e);
			return null;
		}
	}
	private String mapContainsKey(Map map,String key) {
		if(map.containsKey(key)) {
			return key;
		}
		for(BeancpName fname:BeancpName.of(key).friends) {
			if(fname.type==BeancpName.NameType.UNDERLINE||fname.type==BeancpName.NameType.UPPERUNDERLINE) {
				if(map.containsKey(fname.name)) {
					return fname.name;
				}
			}
		}
		return null;
		
	}
	
	public Object newInstance(BeancpContext context,Object bean,BeancpInfo beanInfo) {
		if(bean instanceof Map) {
			return newInstance(context,(Map)bean);
		}
		if(beanInfo==null) {
			beanInfo=BeancpInfo.OBJECT_INFO.of(bean);
		}
		int initsLength=this.inits.size();
		if(initsLength==0) {
			return null;
		}
		try {
			if(this.inits.get(0).getParams().isEmpty()) {
				return newInstance(0,null,(BeancpInfo[])null,context);
			}
			if(bean==null) {
				return null;
			}
			BeancpConvertMapper mapper=BeancpConvertMapper.of(beanInfo, feature);
			long minscore=Long.MAX_VALUE;
			int initIdx=-1;
			for(int i=0;i<initsLength;i++) {
				BeancpInitInfo initInfo=this.inits.get(0);
				if(!initInfo.isUseful(feature, context)) {
					continue;
				}
				boolean allHave=true;
				long score=0;
				//int pi=0;
				for(Entry<String, BeancpInfo> e:initInfo.getParams().entrySet()) {
					String key=e.getKey();
					BeancpInfo paramInfo=e.getValue();
					if(!mapper.fields.containsKey(key)) {
						allHave=false;
						break;
					}
					List<BeancpGetInfo> getterList = mapper.fields.get(key).r1.getGetterList();
					
					//Object mapvalue=mapper.get(bean, key, null, paramInfo, context);//  argsMap.get(mapkey);
					long pscore=distanceGetInfo(paramInfo,getterList);
					if(pscore<0) {
						allHave=false;
						break;
					}
					score+=pscore;
					//pi++;
				}
				if(allHave) {
					if(score==0L) {
						initIdx=i;
						break;
					}else if(score>0L&&score<minscore){
						minscore=score;
						initIdx=i;
					}
				}
			}
			if(initIdx>=0) {
				return this.newInstance(initIdx,bean, mapper, context);
			}
			return null;
		} catch (Exception e) {
			BeancpConvertProviderTool.handleException(this, feature, context, "", 0, e);
			return null;
		}
	}
	//protected abstract Object newInstance(int idx,Object[] args,BeancpContext context) throws Exception;
	protected abstract Object newInstance(int idx, Object[] args, BeancpInfo[] argInfos, BeancpContext context) throws Exception;
	protected abstract Object newInstance(int idx, Object bean,BeancpConvertMapper beanmapper, BeancpContext context) throws Exception;
	
	public Object get(Object obj,String key,Object value,BeancpInfo valueInfo,BeancpContext context) throws Exception {
		if(this.isMap) {
			Object keyValue=((Map)obj).get(key);
			if(value==null&&(valueInfo==null||BeancpInfo.OBJECT_INFO.equals(valueInfo))&&!this.isAllwaysNew) {
				return keyValue;
			}
			BeancpConvertProvider provider=BeancpConvertProvider.of(this.feature, BeancpStringTool.nvl(info.getGenericInfo(1), BeancpInfo.OBJECT_INFO).of(keyValue) , BeancpStringTool.nvl(valueInfo,BeancpInfo.OBJECT_INFO).of(value));
			return provider.convert(context, keyValue, value);
		}
		Tuple2<BeancpFieldInfo, Integer> t2=fields.get(key);
		if(t2==null) {
			return null;
		}
		List<BeancpGetInfo> getterList=t2.r1.getGetterList();
		if(getterList.isEmpty()) {
			return null;
		}
		if(value==null) {
			if(valueInfo==null) {
				valueInfo=BeancpInfo.OBJECT_INFO;
			}
		}else {
			valueInfo=valueInfo.of(value);
		}
		int idx=t2.r2.intValue();
		//if(this.isForceEquals)
		int getIdx=findGetInfo(valueInfo,getterList);
		if(getIdx<0) {return null;}
		BeancpGetInfo getInfo=getterList.get(getIdx);
		BeancpInfo fromInfo=getInfo.getInfo();
		return this.get(obj, idx, getIdx, value, valueInfo, fromInfo, context);
	}
	
	public int get(Object obj,String key,int value,BeancpInfo valueInfo,BeancpContext context) throws Exception {
		if(this.isMap) {
			Object keyValue=((Map)obj).get(key);
			BeancpConvertProvider provider=BeancpConvertProvider.of(this.feature, BeancpStringTool.nvl(info.getGenericInfo(1), BeancpInfo.OBJECT_INFO).of(keyValue) , BeancpStringTool.nvl(valueInfo,BeancpInfo.OBJECT_INFO).of(value));
			return provider.convert(context, keyValue, value);
		}
		Tuple2<BeancpFieldInfo, Integer> t2=fields.get(key);
		if(t2==null) {
			return 0;
		}
		List<BeancpGetInfo> getterList=t2.r1.getGetterList();
		if(getterList.isEmpty()) {
			return 0;
		}
		valueInfo=BeancpInfo.INT_INFO;
		int idx=t2.r2.intValue();
		//if(this.isForceEquals)
		int getIdx=findGetInfo(valueInfo,getterList);
		if(getIdx<0) {return 0;}
		BeancpGetInfo getInfo=getterList.get(getIdx);
		BeancpInfo fromInfo=getInfo.getInfo();
		return this.get(obj, idx, getIdx, value, valueInfo, fromInfo, context);
	}
	
	public long get(Object obj,String key,long value,BeancpInfo valueInfo,BeancpContext context) throws Exception {
		if(this.isMap) {
			Object keyValue=((Map)obj).get(key);
			BeancpConvertProvider provider=BeancpConvertProvider.of(this.feature, BeancpStringTool.nvl(info.getGenericInfo(1), BeancpInfo.OBJECT_INFO).of(keyValue) , BeancpStringTool.nvl(valueInfo,BeancpInfo.OBJECT_INFO).of(value));
			return provider.convert(context, keyValue, value);
		}
		Tuple2<BeancpFieldInfo, Integer> t2=fields.get(key);
		if(t2==null) {
			return 0;
		}
		List<BeancpGetInfo> getterList=t2.r1.getGetterList();
		if(getterList.isEmpty()) {
			return 0;
		}
		valueInfo=BeancpInfo.LONG_INFO;
		int idx=t2.r2.intValue();
		//if(this.isForceEquals)
		int getIdx=findGetInfo(valueInfo,getterList);
		if(getIdx<0) {return 0;}
		BeancpGetInfo getInfo=getterList.get(getIdx);
		BeancpInfo fromInfo=getInfo.getInfo();
		return this.get(obj, idx, getIdx, value, valueInfo, fromInfo, context);
	}
	public short get(Object obj,String key,short value,BeancpInfo valueInfo,BeancpContext context) throws Exception {
		if(this.isMap) {
			Object keyValue=((Map)obj).get(key);
			BeancpConvertProvider provider=BeancpConvertProvider.of(this.feature, BeancpStringTool.nvl(info.getGenericInfo(1), BeancpInfo.OBJECT_INFO).of(keyValue) , BeancpStringTool.nvl(valueInfo,BeancpInfo.OBJECT_INFO).of(value));
			return provider.convert(context, keyValue, value);
		}
		Tuple2<BeancpFieldInfo, Integer> t2=fields.get(key);
		if(t2==null) {
			return 0;
		}
		List<BeancpGetInfo> getterList=t2.r1.getGetterList();
		if(getterList.isEmpty()) {
			return 0;
		}
		valueInfo=BeancpInfo.SHORT_INFO;
		int idx=t2.r2.intValue();
		//if(this.isForceEquals)
		int getIdx=findGetInfo(valueInfo,getterList);
		if(getIdx<0) {return 0;}
		BeancpGetInfo getInfo=getterList.get(getIdx);
		BeancpInfo fromInfo=getInfo.getInfo();
		return this.get(obj, idx, getIdx, value, valueInfo, fromInfo, context);
	}
	public float get(Object obj,String key,float value,BeancpInfo valueInfo,BeancpContext context) throws Exception {
		if(this.isMap) {
			Object keyValue=((Map)obj).get(key);
			BeancpConvertProvider provider=BeancpConvertProvider.of(this.feature, BeancpStringTool.nvl(info.getGenericInfo(1), BeancpInfo.OBJECT_INFO).of(keyValue) , BeancpStringTool.nvl(valueInfo,BeancpInfo.OBJECT_INFO).of(value));
			return provider.convert(context, keyValue, value);
		}
		Tuple2<BeancpFieldInfo, Integer> t2=fields.get(key);
		if(t2==null) {
			return 0;
		}
		List<BeancpGetInfo> getterList=t2.r1.getGetterList();
		if(getterList.isEmpty()) {
			return 0;
		}
		valueInfo=BeancpInfo.FLOAT_INFO;
		int idx=t2.r2.intValue();
		//if(this.isForceEquals)
		int getIdx=findGetInfo(valueInfo,getterList);
		if(getIdx<0) {return 0;}
		BeancpGetInfo getInfo=getterList.get(getIdx);
		BeancpInfo fromInfo=getInfo.getInfo();
		return this.get(obj, idx, getIdx, value, valueInfo, fromInfo, context);
	}
	public double get(Object obj,String key,double value,BeancpInfo valueInfo,BeancpContext context) throws Exception {
		if(this.isMap) {
			Object keyValue=((Map)obj).get(key);
			BeancpConvertProvider provider=BeancpConvertProvider.of(this.feature, BeancpStringTool.nvl(info.getGenericInfo(1), BeancpInfo.OBJECT_INFO).of(keyValue) , BeancpStringTool.nvl(valueInfo,BeancpInfo.OBJECT_INFO).of(value));
			return provider.convert(context, keyValue, value);
		}
		Tuple2<BeancpFieldInfo, Integer> t2=fields.get(key);
		if(t2==null) {
			return 0;
		}
		List<BeancpGetInfo> getterList=t2.r1.getGetterList();
		if(getterList.isEmpty()) {
			return 0;
		}
		valueInfo=BeancpInfo.DOUBLE_INFO;
		int idx=t2.r2.intValue();
		//if(this.isForceEquals)
		int getIdx=findGetInfo(valueInfo,getterList);
		if(getIdx<0) {return 0;}
		BeancpGetInfo getInfo=getterList.get(getIdx);
		BeancpInfo fromInfo=getInfo.getInfo();
		return this.get(obj, idx, getIdx, value, valueInfo, fromInfo, context);
	}
	public byte get(Object obj,String key,byte value,BeancpInfo valueInfo,BeancpContext context) throws Exception {
		if(this.isMap) {
			Object keyValue=((Map)obj).get(key);
			BeancpConvertProvider provider=BeancpConvertProvider.of(this.feature, BeancpStringTool.nvl(info.getGenericInfo(1), BeancpInfo.OBJECT_INFO).of(keyValue) , BeancpStringTool.nvl(valueInfo,BeancpInfo.OBJECT_INFO).of(value));
			return provider.convert(context, keyValue, value);
		}
		Tuple2<BeancpFieldInfo, Integer> t2=fields.get(key);
		if(t2==null) {
			return 0;
		}
		List<BeancpGetInfo> getterList=t2.r1.getGetterList();
		if(getterList.isEmpty()) {
			return 0;
		}
		valueInfo=BeancpInfo.BYTE_INFO;
		int idx=t2.r2.intValue();
		//if(this.isForceEquals)
		int getIdx=findGetInfo(valueInfo,getterList);
		if(getIdx<0) {return 0;}
		BeancpGetInfo getInfo=getterList.get(getIdx);
		BeancpInfo fromInfo=getInfo.getInfo();
		return this.get(obj, idx, getIdx, value, valueInfo, fromInfo, context);
	}
	public char get(Object obj,String key,char value,BeancpInfo valueInfo,BeancpContext context) throws Exception {
		if(this.isMap) {
			Object keyValue=((Map)obj).get(key);
			BeancpConvertProvider provider=BeancpConvertProvider.of(this.feature, BeancpStringTool.nvl(info.getGenericInfo(1), BeancpInfo.OBJECT_INFO).of(keyValue) , BeancpStringTool.nvl(valueInfo,BeancpInfo.OBJECT_INFO).of(value));
			return provider.convert(context, keyValue, value);
		}
		Tuple2<BeancpFieldInfo, Integer> t2=fields.get(key);
		if(t2==null) {
			return 0;
		}
		List<BeancpGetInfo> getterList=t2.r1.getGetterList();
		if(getterList.isEmpty()) {
			return 0;
		}
		valueInfo=BeancpInfo.CHAR_INFO;
		int idx=t2.r2.intValue();
		//if(this.isForceEquals)
		int getIdx=findGetInfo(valueInfo,getterList);
		if(getIdx<0) {return 0;}
		BeancpGetInfo getInfo=getterList.get(getIdx);
		BeancpInfo fromInfo=getInfo.getInfo();
		return this.get(obj, idx, getIdx, value, valueInfo, fromInfo, context);
	}
	public boolean get(Object obj,String key,boolean value,BeancpInfo valueInfo,BeancpContext context) throws Exception {
		if(this.isMap) {
			Object keyValue=((Map)obj).get(key);
			BeancpConvertProvider provider=BeancpConvertProvider.of(this.feature, BeancpStringTool.nvl(info.getGenericInfo(1), BeancpInfo.OBJECT_INFO).of(keyValue) , BeancpStringTool.nvl(valueInfo,BeancpInfo.OBJECT_INFO).of(value));
			return provider.convert(context, keyValue, value);
		}
		Tuple2<BeancpFieldInfo, Integer> t2=fields.get(key);
		if(t2==null) {
			return false;
		}
		List<BeancpGetInfo> getterList=t2.r1.getGetterList();
		if(getterList.isEmpty()) {
			return false;
		}
		valueInfo=BeancpInfo.LONG_INFO;
		int idx=t2.r2.intValue();
		//if(this.isForceEquals)
		int getIdx=findGetInfo(valueInfo,getterList);
		if(getIdx<0) {return false;}
		BeancpGetInfo getInfo=getterList.get(getIdx);
		BeancpInfo fromInfo=getInfo.getInfo();
		return this.get(obj, idx, getIdx, value, valueInfo, fromInfo, context);
	}
	
	public void put(Object obj,String key,Object value,BeancpInfo valueInfo,BeancpContext context) {
		if(this.isMap) {
			Map<Object,Object> map=(Map<Object,Object>)obj;
			BeancpInfo toInfo=info.getGenericInfo(1);
			if((toInfo==null||BeancpInfo.OBJECT_INFO.equals(toInfo))&&!this.isAllwaysNew) {
				map.put(key, value);
				return;
			}
			BeancpConvertProvider provider=BeancpConvertProvider.of(this.feature, BeancpStringTool.nvl(valueInfo, BeancpInfo.OBJECT_INFO).of(value) , BeancpStringTool.nvl(toInfo,BeancpInfo.OBJECT_INFO));
			map.put(key, provider.convert(context, value, null));
			return;
		}
		if(context!=null&&!BeancpConvertProviderTool.isAllowSet(context, obj, key)) {
			return;
		}
		Tuple2<BeancpFieldInfo, Integer> t2=fields.get(key);
		if(t2==null) {
			return;
		}
		List<BeancpSetInfo> setterList=t2.r1.getSetterList();
		if(setterList.isEmpty()) {
			return;
		}
		if(!this.isSetValueWhenNull&&value==null) {
			return;
		}
		if(valueInfo==null) {
			valueInfo=BeancpInfo.OBJECT_INFO;
		}
		valueInfo=valueInfo.of(value);
		try {
			int idx=t2.r2.intValue();
			
			//if(this.isForceEquals)
			int setIdx=findSetInfo(valueInfo,setterList);
			if(setIdx<0) {return;}
			BeancpSetInfo setInfo=setterList.get(setIdx);
			BeancpInfo toInfo=setInfo.getInfo();
			this.put(obj, idx, setIdx, value, setInfo.getPossNames(), valueInfo, toInfo, context);
		} catch (Exception e) {
			BeancpConvertProviderTool.handleException(this, feature, context, key, 0, e);
		}
	}
	public void put(Object obj,String key,int value,BeancpInfo valueInfo,BeancpContext context) {
		if(this.isMap) {
			Map<Object,Object> map=(Map<Object,Object>)obj;
			BeancpInfo toInfo=info.getGenericInfo(1);
			BeancpConvertProvider provider=BeancpConvertProvider.of(this.feature, BeancpStringTool.nvl(valueInfo, BeancpInfo.OBJECT_INFO).of(value) , BeancpStringTool.nvl(toInfo,BeancpInfo.OBJECT_INFO));
			map.put(key, provider.convert(context, value, null));
			return;
		}
		Tuple2<BeancpFieldInfo, Integer> t2=fields.get(key);
		if(t2==null) {
			return;
		}
		List<BeancpSetInfo> setterList=t2.r1.getSetterList();
		if(setterList.isEmpty()) {
			return;
		}
		valueInfo=BeancpInfo.INT_INFO;
		try {
			int idx=t2.r2.intValue();
			
			//if(this.isForceEquals)
			int setIdx=findSetInfo(valueInfo,setterList);
			if(setIdx<0) {return;}
			BeancpSetInfo setInfo=setterList.get(setIdx);
			BeancpInfo toInfo=setInfo.getInfo();
			this.put(obj, idx, setIdx, value, setInfo.getPossNames(), valueInfo, toInfo, context);
			
		} catch (Exception e) {
			BeancpConvertProviderTool.handleException(this, feature, context, key, 0, e);
		}
	}
	public void put(Object obj,String key,long value,BeancpInfo valueInfo,BeancpContext context) {
		if(this.isMap) {
			Map<Object,Object> map=(Map<Object,Object>)obj;
			BeancpInfo toInfo=info.getGenericInfo(1);
			BeancpConvertProvider provider=BeancpConvertProvider.of(this.feature, BeancpStringTool.nvl(valueInfo, BeancpInfo.OBJECT_INFO).of(value) , BeancpStringTool.nvl(toInfo,BeancpInfo.OBJECT_INFO));
			map.put(key, provider.convert(context, value, null));
			return;
		}
		Tuple2<BeancpFieldInfo, Integer> t2=fields.get(key);
		if(t2==null) {
			return;
		}
		List<BeancpSetInfo> setterList=t2.r1.getSetterList();
		if(setterList.isEmpty()) {
			return;
		}
		valueInfo=BeancpInfo.LONG_INFO;
		try {
			int idx=t2.r2.intValue();
			
			//if(this.isForceEquals)
			int setIdx=findSetInfo(valueInfo,setterList);
			if(setIdx<0) {return;}
			BeancpSetInfo setInfo=setterList.get(setIdx);
			BeancpInfo toInfo=setInfo.getInfo();
			this.put(obj, idx, setIdx, value, setInfo.getPossNames(), valueInfo, toInfo, context);
			
		} catch (Exception e) {
			BeancpConvertProviderTool.handleException(this, feature, context, key, 0, e);
		}
	}
	public void put(Object obj,String key,float value,BeancpInfo valueInfo,BeancpContext context) {
		if(this.isMap) {
			Map<Object,Object> map=(Map<Object,Object>)obj;
			BeancpInfo toInfo=info.getGenericInfo(1);
			BeancpConvertProvider provider=BeancpConvertProvider.of(this.feature, BeancpStringTool.nvl(valueInfo, BeancpInfo.OBJECT_INFO).of(value) , BeancpStringTool.nvl(toInfo,BeancpInfo.OBJECT_INFO));
			map.put(key, provider.convert(context, value, null));
			return;
		}
		Tuple2<BeancpFieldInfo, Integer> t2=fields.get(key);
		if(t2==null) {
			return;
		}
		List<BeancpSetInfo> setterList=t2.r1.getSetterList();
		if(setterList.isEmpty()) {
			return;
		}
		valueInfo=BeancpInfo.FLOAT_INFO;
		try {
			int idx=t2.r2.intValue();
			
			//if(this.isForceEquals)
			int setIdx=findSetInfo(valueInfo,setterList);
			if(setIdx<0) {return;}
			BeancpSetInfo setInfo=setterList.get(setIdx);
			BeancpInfo toInfo=setInfo.getInfo();
			this.put(obj, idx, setIdx, value, setInfo.getPossNames(), valueInfo, toInfo, context);
			
		} catch (Exception e) {
			BeancpConvertProviderTool.handleException(this, feature, context, key, 0, e);
		}
	}
	public void put(Object obj,String key,double value,BeancpInfo valueInfo,BeancpContext context) {
		if(this.isMap) {
			Map<Object,Object> map=(Map<Object,Object>)obj;
			BeancpInfo toInfo=info.getGenericInfo(1);
			BeancpConvertProvider provider=BeancpConvertProvider.of(this.feature, BeancpStringTool.nvl(valueInfo, BeancpInfo.OBJECT_INFO).of(value) , BeancpStringTool.nvl(toInfo,BeancpInfo.OBJECT_INFO));
			map.put(key, provider.convert(context, value, null));
			return;
		}
		Tuple2<BeancpFieldInfo, Integer> t2=fields.get(key);
		if(t2==null) {
			return;
		}
		List<BeancpSetInfo> setterList=t2.r1.getSetterList();
		if(setterList.isEmpty()) {
			return;
		}
		valueInfo=BeancpInfo.DOUBLE_INFO;
		try {
			int idx=t2.r2.intValue();
			
			//if(this.isForceEquals)
			int setIdx=findSetInfo(valueInfo,setterList);
			if(setIdx<0) {return;}
			BeancpSetInfo setInfo=setterList.get(setIdx);
			BeancpInfo toInfo=setInfo.getInfo();
			this.put(obj, idx, setIdx, value, setInfo.getPossNames(), valueInfo, toInfo, context);
			
		} catch (Exception e) {
			BeancpConvertProviderTool.handleException(this, feature, context, key, 0, e);
		}
	}
	public void put(Object obj,String key,short value,BeancpInfo valueInfo,BeancpContext context) {
		if(this.isMap) {
			Map<Object,Object> map=(Map<Object,Object>)obj;
			BeancpInfo toInfo=info.getGenericInfo(1);
			BeancpConvertProvider provider=BeancpConvertProvider.of(this.feature, BeancpStringTool.nvl(valueInfo, BeancpInfo.OBJECT_INFO).of(value) , BeancpStringTool.nvl(toInfo,BeancpInfo.OBJECT_INFO));
			map.put(key, provider.convert(context, value, null));
			return;
		}
		Tuple2<BeancpFieldInfo, Integer> t2=fields.get(key);
		if(t2==null) {
			return;
		}
		List<BeancpSetInfo> setterList=t2.r1.getSetterList();
		if(setterList.isEmpty()) {
			return;
		}
		valueInfo=BeancpInfo.SHORT_INFO;
		try {
			int idx=t2.r2.intValue();
			
			//if(this.isForceEquals)
			int setIdx=findSetInfo(valueInfo,setterList);
			if(setIdx<0) {return;}
			BeancpSetInfo setInfo=setterList.get(setIdx);
			BeancpInfo toInfo=setInfo.getInfo();
			this.put(obj, idx, setIdx, value, setInfo.getPossNames(), valueInfo, toInfo, context);
			
		} catch (Exception e) {
			BeancpConvertProviderTool.handleException(this, feature, context, key, 0, e);
		}
	}
	public void put(Object obj,String key,boolean value,BeancpInfo valueInfo,BeancpContext context) {
		if(this.isMap) {
			Map<Object,Object> map=(Map<Object,Object>)obj;
			BeancpInfo toInfo=info.getGenericInfo(1);
			BeancpConvertProvider provider=BeancpConvertProvider.of(this.feature, BeancpStringTool.nvl(valueInfo, BeancpInfo.OBJECT_INFO).of(value) , BeancpStringTool.nvl(toInfo,BeancpInfo.OBJECT_INFO));
			map.put(key, provider.convert(context, value, null));
			return;
		}
		Tuple2<BeancpFieldInfo, Integer> t2=fields.get(key);
		if(t2==null) {
			return;
		}
		List<BeancpSetInfo> setterList=t2.r1.getSetterList();
		if(setterList.isEmpty()) {
			return;
		}
		valueInfo=BeancpInfo.BOOLEAN_INFO;
		try {
			int idx=t2.r2.intValue();
			
			//if(this.isForceEquals)
			int setIdx=findSetInfo(valueInfo,setterList);
			if(setIdx<0) {return;}
			BeancpSetInfo setInfo=setterList.get(setIdx);
			BeancpInfo toInfo=setInfo.getInfo();
			this.put(obj, idx, setIdx, value, setInfo.getPossNames(), valueInfo, toInfo, context);
			
		} catch (Exception e) {
			BeancpConvertProviderTool.handleException(this, feature, context, key, 0, e);
		}
	}
	public void put(Object obj,String key,byte value,BeancpInfo valueInfo,BeancpContext context) {
		if(this.isMap) {
			Map<Object,Object> map=(Map<Object,Object>)obj;
			BeancpInfo toInfo=info.getGenericInfo(1);
			BeancpConvertProvider provider=BeancpConvertProvider.of(this.feature, BeancpStringTool.nvl(valueInfo, BeancpInfo.OBJECT_INFO).of(value) , BeancpStringTool.nvl(toInfo,BeancpInfo.OBJECT_INFO));
			map.put(key, provider.convert(context, value, null));
			return;
		}
		Tuple2<BeancpFieldInfo, Integer> t2=fields.get(key);
		if(t2==null) {
			return;
		}
		List<BeancpSetInfo> setterList=t2.r1.getSetterList();
		if(setterList.isEmpty()) {
			return;
		}
		valueInfo=BeancpInfo.BYTE_INFO;
		try {
			int idx=t2.r2.intValue();
			
			//if(this.isForceEquals)
			int setIdx=findSetInfo(valueInfo,setterList);
			if(setIdx<0) {return;}
			BeancpSetInfo setInfo=setterList.get(setIdx);
			BeancpInfo toInfo=setInfo.getInfo();
			this.put(obj, idx, setIdx, value, setInfo.getPossNames(), valueInfo, toInfo, context);
			
		} catch (Exception e) {
			BeancpConvertProviderTool.handleException(this, feature, context, key, 0, e);
		}
	}
	public void put(Object obj,String key,char value,BeancpInfo valueInfo,BeancpContext context) {
		if(this.isMap) {
			Map<Object,Object> map=(Map<Object,Object>)obj;
			BeancpInfo toInfo=info.getGenericInfo(1);
			BeancpConvertProvider provider=BeancpConvertProvider.of(this.feature, BeancpStringTool.nvl(valueInfo, BeancpInfo.OBJECT_INFO).of(value) , BeancpStringTool.nvl(toInfo,BeancpInfo.OBJECT_INFO));
			map.put(key, provider.convert(context, value, null));
			return;
		}
		Tuple2<BeancpFieldInfo, Integer> t2=fields.get(key);
		if(t2==null) {
			return;
		}
		List<BeancpSetInfo> setterList=t2.r1.getSetterList();
		if(setterList.isEmpty()) {
			return;
		}
		valueInfo=BeancpInfo.CHAR_INFO;
		try {
			int idx=t2.r2.intValue();
			
			//if(this.isForceEquals)
			int setIdx=findSetInfo(valueInfo,setterList);
			if(setIdx<0) {return;}
			BeancpSetInfo setInfo=setterList.get(setIdx);
			BeancpInfo toInfo=setInfo.getInfo();
			this.put(obj, idx, setIdx, value, setInfo.getPossNames(), valueInfo, toInfo, context);
			
		} catch (Exception e) {
			BeancpConvertProviderTool.handleException(this, feature, context, key, 0, e);
		}
	}
	
	private int findGetInfo(BeancpInfo valueInfo,List<BeancpGetInfo> getterList) {
		if(getterList.size()==1) {
			BeancpInfo info0=getterList.get(0).getInfo();
			if(info0.distance(feature, valueInfo)>=0) {
				return 0;
			}
		}else {
			int min=Integer.MAX_VALUE;
			int setIdx=-1;
			for(int i=0;i<getterList.size();i++) {
				BeancpInfo info0=getterList.get(i).getInfo();
				int d=info0.distance(feature, valueInfo);
				if(d>=0&&d<min) {
					min=d;
					setIdx=i;
				}
			}
			return setIdx;
		}
		return -1;
	}
	private long distanceGetInfo(BeancpInfo valueInfo,List<BeancpGetInfo> getterList) {
		if(getterList.size()==1) {
			BeancpInfo info0=getterList.get(0).getInfo();
			return info0.distance(feature, valueInfo);
		}else {
			int min=-1;
			for(int i=0;i<getterList.size();i++) {
				BeancpInfo info0=getterList.get(i).getInfo();
				int d=info0.distance(feature, valueInfo);
				if(d==0) {
					return d;
				}else if(d>0){
					if(min<0) {
						min=d;
					}else if(min>d){
						min=d;
					}
					return d;
				}else {
					if(d>min) {
						min=d;
					}
				}
			}
			return min;
		}
		//return -1L;
	}
	private int findSetInfo(BeancpInfo valueInfo,List<BeancpSetInfo> setterList) {
		if(this.isForceEquals) {
			for(int i=0;i<setterList.size();i++) {
				BeancpInfo info0=setterList.get(i).getInfo();
				if(valueInfo.instanceOf(info0)||valueInfo.distance(feature, info0)==0) {
					return i;
				}
			}
		}else {
			if(setterList.size()==1) {
				BeancpInfo info0=setterList.get(0).getInfo();
				if(valueInfo.distance(feature, info0)>=0) {
					return 0;
				}
			}else {
				int min=Integer.MAX_VALUE;
				int setIdx=-1;
				for(int i=0;i<setterList.size();i++) {
					BeancpInfo info0=setterList.get(i).getInfo();
					int d=valueInfo.distance(feature, info0);
					if(d>=0&&d<min) {
						min=d;
						setIdx=i;
					}
				}
				return setIdx;
			}
		}
		return -1;
	}
	protected abstract void put(Object obj,int idx,int setIdx,Object value,String[] key,BeancpInfo valueInfo,BeancpInfo toInfo,BeancpContext context) throws Exception;
	protected abstract void put(Object obj,int idx,int setIdx,long value,String[] key,BeancpInfo valueInfo,BeancpInfo toInfo,BeancpContext context) throws Exception;
	protected abstract void put(Object obj,int idx,int setIdx,int value,String[] key,BeancpInfo valueInfo,BeancpInfo toInfo,BeancpContext context) throws Exception;
	protected abstract void put(Object obj,int idx,int setIdx,byte value,String[] key,BeancpInfo valueInfo,BeancpInfo toInfo,BeancpContext context) throws Exception;
	protected abstract void put(Object obj,int idx,int setIdx,char value,String[] key,BeancpInfo valueInfo,BeancpInfo toInfo,BeancpContext context) throws Exception;
	protected abstract void put(Object obj,int idx,int setIdx,boolean value,String[] key,BeancpInfo valueInfo,BeancpInfo toInfo,BeancpContext context) throws Exception;
	protected abstract void put(Object obj,int idx,int setIdx,float value,String[] key,BeancpInfo valueInfo,BeancpInfo toInfo,BeancpContext context) throws Exception;
	protected abstract void put(Object obj,int idx,int setIdx,double value,String[] key,BeancpInfo valueInfo,BeancpInfo toInfo,BeancpContext context) throws Exception;
	protected abstract void put(Object obj,int idx,int setIdx,short value,String[] key,BeancpInfo valueInfo,BeancpInfo toInfo,BeancpContext context) throws Exception;

	protected abstract Object get(Object obj,int idx,int getIdx,Object value,BeancpInfo valueInfo,BeancpInfo fromInfo,BeancpContext context) throws Exception;
	protected abstract long get(Object obj,int idx,int getIdx,long value,BeancpInfo valueInfo,BeancpInfo fromInfo,BeancpContext context) throws Exception;
	protected abstract int get(Object obj,int idx,int getIdx,int value,BeancpInfo valueInfo,BeancpInfo fromInfo,BeancpContext context) throws Exception;
	protected abstract byte get(Object obj,int idx,int getIdx,byte value,BeancpInfo valueInfo,BeancpInfo fromInfo,BeancpContext context) throws Exception;
	protected abstract char get(Object obj,int idx,int getIdx,char value,BeancpInfo valueInfo,BeancpInfo fromInfo,BeancpContext context) throws Exception;
	protected abstract boolean get(Object obj,int idx,int getIdx,boolean value,BeancpInfo valueInfo,BeancpInfo fromInfo,BeancpContext context) throws Exception;
	protected abstract float get(Object obj,int idx,int getIdx,float value,BeancpInfo valueInfo,BeancpInfo fromInfo,BeancpContext context) throws Exception;
	protected abstract double get(Object obj,int idx,int getIdx,double value,BeancpInfo valueInfo,BeancpInfo fromInfo,BeancpContext context) throws Exception;
	protected abstract short get(Object obj,int idx,int getIdx,short value,BeancpInfo valueInfo,BeancpInfo fromInfo,BeancpContext context) throws Exception;

	
	public Object putAll(Object obj,Map map,BeancpInfo mapInfo,BeancpContext context){
		if(obj==null) {
			obj=this.newInstance(context, map);
		}
		BeancpInfo valueInfo=mapInfo.getGenericInfo(1);
		if(map.size()>this.fields.size()) {
			for(String key:this.fields.keySet()) {
				if(map.containsKey(key)) {
					if(this.isSetValueWhenNull) {
						this.put(obj, key, map.get(key), valueInfo, context);
					}else {
						this.put(obj, key, map.get(key), valueInfo, context);
					}
					
				}
			}
		}else {
			for(Object okey:map.keySet()) {
				if(okey instanceof String&&this.fields.containsKey(okey)) {
					this.put(obj, (String)okey, map.get(okey), valueInfo, context);
				}
			}
		}
		return obj;
	}
	
	public Map toMap(Object obj,Map map,BeancpInfo mapInfo,BeancpContext context){
		if(obj==null) {
			return null;
		}
		BeancpInfo keyInfo=mapInfo.getGenericInfo(0);
		if(keyInfo!=null&&keyInfo!=BeancpInfo.OBJECT_INFO&&!keyInfo.instanceOf(BeancpInfo.STRING_INFO)) {
			return map;
		}
		if(map==null) {
			map=(Map) BeancpConvertMapper.of(mapInfo.of(map), feature).newInstance(context);
		}
		BeancpInfo valueInfo=mapInfo.getGenericInfo(1);
		for(BeancpName key:this.getKeys2) {
			try {
				Object o=this.get(obj, key.name, null, valueInfo, context);
				if(this.isSetValueWhenNull||o!=null) {
					if(isBean2MapUnderlineUpper) {
						for(BeancpName fname: key.friends) {
							if(fname.type==BeancpName.NameType.UPPERUNDERLINE) {
								map.put(fname.name, o);
								break;
							}
						}
					}else if(isBean2MapUnderline) {
						for(BeancpName fname: key.friends) {
							if(fname.type==BeancpName.NameType.UNDERLINE) {
								map.put(fname.name, o);
								break;
							}
						}
					}else {
						map.put(key.name, o);
					}
				}
			} catch (Exception e) {
				BeancpConvertProviderTool.handleException(this, feature, context, key.name, 1, e);
			}
		}
		return map;
	}
	public Object clone(BeancpContext context,Object obj) {
		Object ret=null;
		try {
			ret=this.clone(obj);
		} catch (Exception e) {
			Throwable e1=e;
			int i=0;
			boolean ignore=false;
			while(e1!=null) {
				if(i>10) {
					break;
				}
				i++;
				if(e1 instanceof CloneNotSupportedException) {
					ignore=true;
					break;
				}
				e1=e1.getCause();
			}
			if(!ignore) {
				if(e instanceof BeancpException) {
					throw (BeancpException)e;
				}
				throw new BeancpException(e);
			}
		}
		if(ret==null) {
			ret=this.newInstance(context, obj,this.info);
			ret=BeancpConvertProvider.of(feature, info, info).convert(context, obj, ret);
		}
		return ret;
	}
	protected abstract Object clone(Object obj) throws Exception;
	protected Object filterValue(Object obj,String[] key,Object value,BeancpContext context){
		if(context==null) {
			return value;
		}else {
			BeancpValueFilter valueFilter;
			if((valueFilter = BeancpConvertProviderTool.getValueFilter(context, obj, key))!=null) {
				return BeancpConvertProviderTool.filterValue(valueFilter, obj, key,value);
			}else {
				return value;
			}
		}
	}
	protected char filterValue(Object obj,String[] key,char value,BeancpContext context){
		if(context==null) {
			return value;
		}else {
			BeancpValueFilter valueFilter;
			if((valueFilter = BeancpConvertProviderTool.getValueFilter(context, obj, key))!=null) {
				return BeancpConvertProviderTool.filterValue(valueFilter, obj, key,value);
			}else {
				return value;
			}
		}
	}
	protected boolean filterValue(Object obj,String[] key,boolean value,BeancpContext context){
		if(context==null) {
			return value;
		}else {
			BeancpValueFilter valueFilter;
			if((valueFilter = BeancpConvertProviderTool.getValueFilter(context, obj, key))!=null) {
				return BeancpConvertProviderTool.filterValue(valueFilter, obj, key,value);
			}else {
				return value;
			}
		}
	}
	protected long filterValue(Object obj,String[] key,long value,BeancpContext context){
		if(context==null) {
			return value;
		}else {
			BeancpValueFilter valueFilter;
			if((valueFilter = BeancpConvertProviderTool.getValueFilter(context, obj, key))!=null) {
				return BeancpConvertProviderTool.filterValue(valueFilter, obj, key,value);
			}else {
				return value;
			}
		}
	}protected short filterValue(Object obj,String[] key,short value,BeancpContext context){
		if(context==null) {
			return value;
		}else {
			BeancpValueFilter valueFilter;
			if((valueFilter = BeancpConvertProviderTool.getValueFilter(context, obj, key))!=null) {
				return BeancpConvertProviderTool.filterValue(valueFilter, obj, key,value);
			}else {
				return value;
			}
		}
	}protected byte filterValue(Object obj,String[] key,byte value,BeancpContext context){
		if(context==null) {
			return value;
		}else {
			BeancpValueFilter valueFilter;
			if((valueFilter = BeancpConvertProviderTool.getValueFilter(context, obj, key))!=null) {
				return BeancpConvertProviderTool.filterValue(valueFilter, obj, key,value);
			}else {
				return value;
			}
		}
	}protected float filterValue(Object obj,String[] key,float value,BeancpContext context){
		if(context==null) {
			return value;
		}else {
			BeancpValueFilter valueFilter;
			if((valueFilter = BeancpConvertProviderTool.getValueFilter(context, obj, key))!=null) {
				return BeancpConvertProviderTool.filterValue(valueFilter, obj, key,value);
			}else {
				return value;
			}
		}
	}protected double filterValue(Object obj,String[] key,double value,BeancpContext context){
		if(context==null) {
			return value;
		}else {
			BeancpValueFilter valueFilter;
			if((valueFilter = BeancpConvertProviderTool.getValueFilter(context, obj, key))!=null) {
				return BeancpConvertProviderTool.filterValue(valueFilter, obj, key,value);
			}else {
				return value;
			}
		}
	}protected int filterValue(Object obj,String[] key,int value,BeancpContext context){
		if(context==null) {
			return value;
		}else {
			BeancpValueFilter valueFilter;
			if((valueFilter = BeancpConvertProviderTool.getValueFilter(context, obj, key))!=null) {
				return BeancpConvertProviderTool.filterValue(valueFilter, obj, key,value);
			}else {
				return value;
			}
		}
	}
	
	
	
	
	
	protected Object filterValue(String key,Object value,BeancpContext context){
		if(context==null) {
			return value;
		}else {
			BeancpValueFilter valueFilter;
			if((valueFilter = BeancpConvertProviderTool.getValueFilter(context, info.getFClass(), key))!=null) {
				return BeancpConvertProviderTool.filterValue(valueFilter, null, "key8",value);
			}else {
				return value;
			}
		}
	}
	
	protected short filterValue(String key,short value,BeancpContext context){
		if(context==null) {
			return value;
		}else {
			BeancpValueFilter valueFilter;
			if((valueFilter = BeancpConvertProviderTool.getValueFilter(context, info.getFClass(), key))!=null) {
				return BeancpConvertProviderTool.filterValue(valueFilter, (short)0, "key8",value);
			}else {
				return value;
			}
		}
	}
	
	protected boolean filterValue(String key,boolean value,BeancpContext context){
		if(context==null) {
			return value;
		}else {
			BeancpValueFilter valueFilter;
			if((valueFilter = BeancpConvertProviderTool.getValueFilter(context, info.getFClass(), key))!=null) {
				return BeancpConvertProviderTool.filterValue(valueFilter, false, "key8",value);
			}else {
				return value;
			}
		}
	}
	protected double filterValue(String key,double value,BeancpContext context){
		if(context==null) {
			return value;
		}else {
			BeancpValueFilter valueFilter;
			if((valueFilter = BeancpConvertProviderTool.getValueFilter(context, info.getFClass(), key))!=null) {
				return BeancpConvertProviderTool.filterValue(valueFilter, (double)0, "key8",value);
			}else {
				return value;
			}
		}
	}
	
	protected float filterValue(String key,float value,BeancpContext context){
		if(context==null) {
			return value;
		}else {
			BeancpValueFilter valueFilter;
			if((valueFilter = BeancpConvertProviderTool.getValueFilter(context, info.getFClass(), key))!=null) {
				return BeancpConvertProviderTool.filterValue(valueFilter, (float)0, "key8",value);
			}else {
				return value;
			}
		}
	}
	
	protected byte filterValue(String key,byte value,BeancpContext context){
		if(context==null) {
			return value;
		}else {
			BeancpValueFilter valueFilter;
			if((valueFilter = BeancpConvertProviderTool.getValueFilter(context, info.getFClass(), key))!=null) {
				return BeancpConvertProviderTool.filterValue(valueFilter, (byte)0, "key8",value);
			}else {
				return value;
			}
		}
	}
	
	protected long filterValue(String key,long value,BeancpContext context){
		if(context==null) {
			return value;
		}else {
			BeancpValueFilter valueFilter;
			if((valueFilter = BeancpConvertProviderTool.getValueFilter(context, info.getFClass(), key))!=null) {
				return BeancpConvertProviderTool.filterValue(valueFilter, 0L, "key8",value);
			}else {
				return value;
			}
		}
	}
	
	protected int filterValue(String key,int value,BeancpContext context){
		if(context==null) {
			return value;
		}else {
			BeancpValueFilter valueFilter;
			if((valueFilter = BeancpConvertProviderTool.getValueFilter(context, info.getFClass(), key))!=null) {
				return BeancpConvertProviderTool.filterValue(valueFilter, 0, "key8",value);
			}else {
				return value;
			}
		}
	}
	
	protected Object convertValue(String key,Object arg,BeancpInfo argInfo,BeancpInfo paramInfo,BeancpContext context) {
		if(argInfo.instanceOf(paramInfo)) {
			return arg;
		}else {
			return BeancpConvertProvider.of(feature, argInfo, paramInfo).convert(context, arg,null);
		}
	}
	
	
	
	
	protected int indexOf(String key) {
		return fields.get(key).r2.intValue();
	}

	

	
	
}
