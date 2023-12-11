package org.kepe.beancp.info;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.time.temporal.Temporal;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.AbstractSequentialList;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.config.BeancpProperty;
import org.kepe.beancp.ct.BeancpConvertProvider;
import org.kepe.beancp.ct.asm.BeancpInfoASMTool;
import org.kepe.beancp.ct.convert.BeancpConvertMapper;
import org.kepe.beancp.tool.BeancpBeanTool;
import org.kepe.beancp.tool.BeancpStringTool;
import org.kepe.beancp.tool.BeancpTool;
import org.kepe.beancp.tool.vo.Tuple2;

/**
 * Hello world!
 *
 */
public class BeancpInfo
{
    private static final Map<Class<?>,Map<Type,BeancpInfo>> C_MAP=new ConcurrentHashMap<>();
    private static final AtomicInteger INDEX=new AtomicInteger(0);

	public final static BeancpInfo OBJECT_INFO=BeancpInfo.of(Object.class);
	public final static BeancpInfo INT_INFO=BeancpInfo.of(int.class);
	public final static BeancpInfo LONG_INFO=BeancpInfo.of(long.class);
	public final static BeancpInfo BOOLEAN_INFO=BeancpInfo.of(boolean.class);
	public final static BeancpInfo CHAR_INFO=BeancpInfo.of(char.class);
	public final static BeancpInfo SHORT_INFO=BeancpInfo.of(short.class);
	public final static BeancpInfo BYTE_INFO=BeancpInfo.of(byte.class);
	public final static BeancpInfo FLOAT_INFO=BeancpInfo.of(float.class);
	public final static BeancpInfo DOUBLE_INFO=BeancpInfo.of(double.class);
	public final static BeancpInfo STRING_INFO=BeancpInfo.of(String.class);
	
	static final Class CLASS_EMPTY_SET = Collections.emptySet().getClass();
    static final Class CLASS_EMPTY_LIST = Collections.emptyList().getClass();
    static final Class CLASS_SINGLETON = Collections.singleton(0).getClass();
    static final Class CLASS_SINGLETON_LIST = Collections.singletonList(0).getClass();
    static final Class CLASS_ARRAYS_LIST = Arrays.asList(0).getClass();

    static final Class CLASS_UNMODIFIABLE_COLLECTION = Collections.unmodifiableCollection(Collections.emptyList()).getClass();
    static final Class CLASS_UNMODIFIABLE_LIST = Collections.unmodifiableList(Collections.emptyList()).getClass();
    static final Class CLASS_UNMODIFIABLE_SET = Collections.unmodifiableSet(Collections.emptySet()).getClass();
    static final Class CLASS_UNMODIFIABLE_SORTED_SET = Collections.unmodifiableSortedSet(Collections.emptySortedSet()).getClass();
    static final Class CLASS_UNMODIFIABLE_NAVIGABLE_SET = Collections.unmodifiableNavigableSet(Collections.emptyNavigableSet()).getClass();
    static final Class CLASS_SINGLETON_MAP = Collections.singletonMap(1, 1).getClass();
    static final Class CLASS_EMPTY_MAP = Collections.EMPTY_MAP.getClass();
    static final Class CLASS_EMPTY_SORTED_MAP = Collections.emptySortedMap().getClass();
    static final Class CLASS_EMPTY_NAVIGABLE_MAP = Collections.emptyNavigableMap().getClass();
    static final Class CLASS_UNMODIFIABLE_MAP = Collections.unmodifiableMap(Collections.emptyMap()).getClass();
    static final Class CLASS_UNMODIFIABLE_SORTED_MAP = Collections.unmodifiableSortedMap(Collections.emptySortedMap()).getClass();
    static final Class CLASS_UNMODIFIABLE_NAVIGABLE_MAP = Collections.unmodifiableNavigableMap(Collections.emptyNavigableMap()).getClass();
    
    private int id;
    private Class<?> clazz;
    private Type type;
    private Class<?> fclazz;
    private Type ftype;
    private BeancpTypeInfo typeInfo;
    //private String typeDescriptionStr;
    private volatile boolean isInitTargetProxy;
    public boolean isBase;
    public boolean isChangeable;
    public boolean isPrimitive;
    public boolean isEnum;
    public boolean isList;
    public boolean isArray;
    public boolean isBean;
    public boolean isMap;
    public int stage;
    private boolean isFInterface;
    private boolean isFAbstract;
    private boolean hasType;
    public Map<String,BeancpFieldInfo> fields;
    private Set<String> simpleGetterKeys;
    private Set<String> simpleSetterKeys;
    public List<BeancpInitInfo> inits;
    private BeancpInfo[] genericInfo;
    public BeancpCloneInfo cloneInfo;
    private BeancpConvertMapper mapper;
    private Enum[] enumConstants;
    private BeancpInfo componentType;

    public BeancpInfo getComponentType() {
    	if(this.isArray&&this.componentType==null) {
    		if(type instanceof Class) {
    			this.componentType=of(((Class)type).getComponentType());
    		}else if(type instanceof GenericArrayType) {
    			this.componentType= of(((GenericArrayType) type).getGenericComponentType());
    		}
    	}
    	return this.componentType;
    }

    public boolean instanceOf(BeancpInfo info){
    	return this.typeInfo.instanceOf(info.typeInfo);
    }
    public boolean instanceOf(Class<?> clazz){
        if(clazz.isAssignableFrom(this.clazz)){
            return true;
        }
        return false;
    }
    private BeancpInfo[] getGenericInfos() {
    	if(genericInfo==null) {
    		Type ftype=this.ftype;
    		if(ftype instanceof ParameterizedType) {
    			Type[] ptype=((ParameterizedType)ftype).getActualTypeArguments();
    			BeancpInfo[] genericInfo=new BeancpInfo[ptype.length];
    			for(int i=0;i<ptype.length;i++) {
    				genericInfo[i]=BeancpInfo.of(ptype[i]);
    			}
    			this.genericInfo=genericInfo;
    		}else {
    			TypeVariable<?>[] tvs= this.clazz.getTypeParameters();
    			BeancpInfo[] genericInfo=new BeancpInfo[tvs.length];
    			for(int i=0;i<genericInfo.length;i++) {
    				genericInfo[i]=BeancpInfo.of(tvs[i]);
    			}
    			this.genericInfo=genericInfo;
    		}
    	}
    	return genericInfo;
    }
    public BeancpInfo getGenericInfo(int idx) {
    	this.getGenericInfos();
    	return genericInfo.length>idx?genericInfo[idx]:null;
    }

    public Type getBType(){
        return this.type;
    }
    public Class<?> getBClass(){
        return this.clazz;
    }
    public Class<?> getFClass(){
        return this.fclazz;
    }
    
    public BeancpConvertMapper  getDefaultMapper() {
    	if(mapper==null) {
    		if(this.isPrimitive) {
    			return null;
    		}
    		mapper=BeancpConvertMapper.of(this, BeancpFeature.DEFAULT_FEATURE);
    	}
    	return mapper;
    }
    public Class<?> getFinalPublicClass(){
    	return BeancpBeanTool.getFinalPublicClass(fclazz);
    }
    public int distance(BeancpFeature flag,BeancpInfo info) {
    	if(this.id==info.id) {
    		return 0;
    	}
    	if(info==OBJECT_INFO) {
    		return 0;
    	}
    	BeancpConvertProvider provider=BeancpConvertProvider.of(flag, this, info);
    	return provider.getDistance();
    }
    
    public static BeancpInfo getInfoById(int id) {
    	for(Map<Type, BeancpInfo> map:C_MAP.values()) {
    		for(BeancpInfo info:map.values()) {
    			if(info.id==id) {
    				return info;
    			}
    		}
    	}
    	return null;
    }

    
    
    public BeancpInfo of(Object obj){
        if(obj==null||this.clazz==obj.getClass()){
            return this;
        }
        return of(type,obj.getClass());
    }
    public static BeancpInfo of(Type type){
        return of(type,null,null);
    }
    public static BeancpInfo of(Type type,Class<?> clazz){
    	
    	if(type==null&&clazz==null) {
    		type=Object.class;
    		clazz=Object.class;
    	}
    	if(type==null) {
    		type=clazz;
    	}
    	if(clazz!=null&&type instanceof Class) {
    		type=clazz;
    	}
    	BeancpTypeInfo typeInfo=BeancpTypeInfo.of(type);
    	if(clazz!=null&&clazz!=Object.class) {
    		int mode=typeInfo.getMode();
    		if(mode==2||mode==3) {
    			type=clazz;
    			typeInfo=BeancpTypeInfo.of(type);
    		}
    	}
    	if(clazz==null){
            clazz=typeInfo.getRawClass();
        }
        return trans(typeInfo,clazz);
    }
    public static BeancpInfo of(Type type,Class<?> clazz,Object obj){
        if(obj!=null){
            clazz=obj.getClass();
        }
        return of(type,clazz);
    }
    
    private static BeancpInfo trans(BeancpTypeInfo typeInfo,Class clazz) {
    	Type type=typeInfo.getType();
    	BeancpInfo infoff= C_MAP.computeIfAbsent(clazz, key->new ConcurrentHashMap<>()).computeIfAbsent(type, key->{
        	BeancpInfo info=new BeancpInfo();
            info.id=INDEX.incrementAndGet();
            info.typeInfo=typeInfo;
            info.clazz=clazz;
            info.type=type;
            info.transFinalType();
            if(!(info.type instanceof Class)) {
            	info.hasType=true;
            }
            info.isPrimitive=info.fclazz.isPrimitive();
            info.isChangeable=true;
            if(info.clazz.isPrimitive()){
                info.isPrimitive=true;
                info.isBase=true;
                info.isChangeable=false;
                if(Long.TYPE==type) {
        			info.stage=2;
        		}else if(Boolean.TYPE==type) {
        			info.stage=3;
        		}else if(Character.TYPE==type) {
        			info.stage=6;
        		}else if(Byte.TYPE==type) {
        			info.stage=8;
        		}else if(Short.TYPE==type) {
        			info.stage=7;
        		}else if(Integer.TYPE==type) {
        			info.stage=1;
        		}else if(Float.TYPE==type) {
        			info.stage=4;
        		}else if(Double.TYPE==type) {
        			info.stage=5;
        		}
            }
            if(info.clazz.isEnum()) {
            	info.isEnum=true;
            	info.enumConstants=(Enum[]) info.clazz.getEnumConstants();
            }
            if(Map.class.isAssignableFrom(info.clazz)) {
            	info.isMap=true;
            }
            
            if(info.isPrimitive||info.isEnum||String.class.isAssignableFrom(info.clazz)||Number.class.isAssignableFrom(info.fclazz)||Date.class.isAssignableFrom(info.clazz)||InputStream.class.isAssignableFrom(info.clazz)||OutputStream.class.isAssignableFrom(info.clazz)||File.class.isAssignableFrom(info.clazz)||Clob.class.isAssignableFrom(info.clazz)||Blob.class.isAssignableFrom(info.clazz)||AtomicBoolean.class.isAssignableFrom(info.clazz)||AtomicInteger.class.isAssignableFrom(info.clazz)||AtomicLong.class.isAssignableFrom(info.clazz)||Calendar.class.isAssignableFrom(info.clazz)){
                info.isBase=true;
            }
            if(info.isEnum||String.class.isAssignableFrom(info.clazz)||Number.class.isAssignableFrom(info.fclazz)||Clob.class.isAssignableFrom(info.clazz)||Blob.class.isAssignableFrom(info.clazz)||Temporal.class.isAssignableFrom(info.clazz)) {
            	info.isChangeable=false;
            }
            if(info.fclazz.isInterface()) {
            	info.isFInterface=true;
            }
            if(Modifier.isAbstract(info.fclazz.getModifiers())) {
            	info.isFAbstract=true;
            }
            if(!info.isFAbstract&&!info.isFInterface&&!info.isPrimitive&&!info.isBase&&!info.isArray&&!info.isList&&!info.isMap&&!Collection.class.isAssignableFrom(info.clazz) ) {
            	info.isBean=true; 
            }
            if(info.isBean) {
            	Map<String,Field> fields=new HashMap<>();
            	Map<String,Method> methods=new HashMap<>();
            	Set<String> notAllowField=new HashSet<>();
            	Class mclazz=info.fclazz;
            	while(mclazz!=Object.class) {
            		if(BeancpBeanTool.isProxy(mclazz)) {
            			continue;
            		}
            		Field[] fields1=mclazz.getDeclaredFields();
            		
            		for(Field field:fields1) {
            			String keyname=field.getName();
            			if(keyname.contains("$")) {
            				continue;
            			}
            			if(!fields.containsKey(keyname)) {
            				if(!BeancpBeanTool.isAllowField(field)) {
                    			notAllowField.add(keyname);
                    		}
            				fields.put(keyname, field);
            			}
            		}
            		mclazz=mclazz.getSuperclass();
            	}
            	
            	mclazz=info.fclazz;
            	while(mclazz!=Object.class) {
            		if(BeancpBeanTool.isProxy(mclazz)) {
            			continue;
            		}
            		Method[] methods1=mclazz.getDeclaredMethods();
            		for(Method method:methods1) {
            			String methodName=method.getName();
            			if(BeancpBeanTool.isPossMethod(method)) {
            				Class<?>[] types=method.getParameterTypes();
            				StringBuffer sb=new StringBuffer();
            				sb.append(methodName).append(":");
            				for(Class<?> tclazz:types) {
            					sb.append(tclazz.getName());
            				}
            				String keyname=sb.toString();
            				if(!methods.containsKey(keyname)) {
            					methods.put(keyname, method);
                			}
            			}
            		}
            		mclazz=mclazz.getSuperclass();
            	}
            	Set<String> notAllowMethod=new HashSet<>();
            	for(Map.Entry<String, Method> e:methods.entrySet()) {
            		Method method=e.getValue();
            		String methodName=method.getName();
            		BeancpProperty bp=method.getAnnotation(BeancpProperty.class);
            		if(bp!=null) {
            			continue;
            		}
        			if(methodName.startsWith("is")) {
    					if(notAllowField.contains(methodName)||notAllowField.contains(methodName.substring(2,3).toLowerCase()+methodName.substring(3))) {
    						notAllowMethod.add(e.getKey());
    					}
    				}else {
    					if((methodName.startsWith("get")&&method.getReturnType()==boolean.class)||(methodName.startsWith("set")&&method.getParameterTypes()[0]==boolean.class)) {
    						if(notAllowField.contains(methodName.substring(3,4).toLowerCase()+methodName.substring(4))) {
    							notAllowMethod.add(e.getKey());
    						}else if(notAllowField.contains("is"+methodName.substring(3))) {
    							notAllowMethod.add(e.getKey());
    						}
    					}else if(notAllowField.contains(methodName.substring(3,4).toLowerCase()+methodName.substring(4))) {
    						notAllowMethod.add(e.getKey());
    					}
    				}
            	}
            	for(String name:notAllowMethod) {
            		methods.remove(name);
            	}
            	Map<String,BeancpFieldInfo> fieldInfoMap=new HashMap<>();
            	Map<Method,Field> mfs=new HashMap<>();
            	for(Method method:methods.values()) {
        			String methodName=method.getName();
        			if((methodName.startsWith("is")||methodName.startsWith("get"))&&method.getReturnType()==boolean.class&&BeancpBeanTool.isGetterMethod(method)){
    					if(methodName.startsWith("is")) {
    						String fname=methodName.substring(2,3).toLowerCase()+methodName.substring(3);
    						if(fields.containsKey(fname)) {
    							mfs.put(method, fields.get(fname));
    						}else if(fields.containsKey(methodName)){
    							mfs.put(method, fields.get(methodName));
    						}
    					}else {
    						String fname=methodName.substring(3,4).toLowerCase()+methodName.substring(4);
    						if(fields.containsKey(fname)) {
    							mfs.put(method, fields.get(fname));
    						}else {
    							fname="is"+methodName.substring(3,4).toLowerCase()+methodName.substring(4);
    							if(fields.containsKey(fname)) {
    								mfs.put(method, fields.get(methodName));
    							}
    						}
    					}
    				}
        			if((methodName.startsWith("is")||methodName.startsWith("set"))&&BeancpBeanTool.isSetterMethod(method)&&method.getParameterTypes()[0]==boolean.class){
    					if(methodName.startsWith("is")) {
    						String fname=methodName.substring(2,3).toLowerCase()+methodName.substring(3);
    						if(fields.containsKey(fname)) {
    							mfs.put(method, fields.get(fname));
    						}else if(fields.containsKey(methodName)){
    							mfs.put(method, fields.get(methodName));
    						}
    					}else {
    						String fname=methodName.substring(3,4).toLowerCase()+methodName.substring(4);
    						if(fields.containsKey(fname)) {
    							mfs.put(method, fields.get(fname));
    						}else {
    							fname="is"+methodName.substring(3,4).toLowerCase()+methodName.substring(4);
    							if(fields.containsKey(fname)) {
    								mfs.put(method, fields.get(methodName));
    							}
    						}
    					}
    				}
        		}
            	for(String name:notAllowField) {
            		fields.remove(name);
            	}
            	for(Field field:fields.values()) {
            		Set<String> sets = BeancpBeanTool.getProperties(field);
            		if(sets!=null) {
            			for(String fname:sets) {
            				BeancpFieldInfo fieldInfo=fieldInfoMap.computeIfAbsent(fname, key1->new BeancpFieldInfo(fname,info));
            				BeancpGetInfo getInfo=new BeancpGetInfo(fname,info,field);
                    		fieldInfo.addGetInfo(getInfo);
                    		fieldInfo.addSetInfo(new BeancpSetInfo(fname,info,field));
            			}
            		}
            	}
            	
            	for(Method method:methods.values()) {
            		if(BeancpBeanTool.isGetterMethod(method)) {
            			Tuple2<Set<String>,String> tuple2=BeancpBeanTool.getProperties(method, mfs.get(method));
            			if(tuple2!=null) {
            				for(String fname:tuple2.r1) {
            					BeancpFieldInfo fieldInfo=fieldInfoMap.computeIfAbsent(fname, key1->new BeancpFieldInfo(fname,info));
            					BeancpGetInfo getInfo=new BeancpGetInfo(fname,info,method);
                        		fieldInfo.addFirstGetInfo(getInfo);
                        		if(!BeancpStringTool.isEmpty(tuple2.r2)) {
                        			getInfo.setPossName(tuple2.r2);
                        			fieldInfo=fieldInfoMap.computeIfAbsent(tuple2.r2, key1->new BeancpFieldInfo(tuple2.r2,info));
                					getInfo=new BeancpGetInfo(tuple2.r2,info,method);
                					getInfo.setPossName(fname);
                					getInfo.setFake(true);
                            		fieldInfo.addFirstGetInfo(getInfo);
                        		}
            				}
            			}
            		}else if(BeancpBeanTool.isSetterMethod(method)) {
            			Tuple2<Set<String>,String> tuple2=BeancpBeanTool.getProperties(method, mfs.get(method));
            			if(tuple2!=null) {
            				for(String fname:tuple2.r1) {
            					BeancpFieldInfo fieldInfo=fieldInfoMap.computeIfAbsent(fname, key1->new BeancpFieldInfo(fname,info));
            					BeancpSetInfo setInfo=new BeancpSetInfo(fname,info,method);
                        		fieldInfo.addFirstSetInfo(setInfo);
                        		if(!BeancpStringTool.isEmpty(tuple2.r2)) {
                        			setInfo.setPossName(tuple2.r2);
                        			fieldInfo=fieldInfoMap.computeIfAbsent(tuple2.r2, key1->new BeancpFieldInfo(tuple2.r2,info));
                        			setInfo=new BeancpSetInfo(tuple2.r2,info,method);
                        			setInfo.setPossName(fname);
                        			setInfo.setFake(true);
                            		fieldInfo.addFirstSetInfo(setInfo);
                        		}
            				}
            			}
            		}
            	}
            	info.fields=fieldInfoMap;
            	
            	
            }
            if(!info.isPrimitive) {
            	info.inits=new ArrayList<>();
            	Constructor<?>[] inits= info.fclazz.getDeclaredConstructors();
            	for(Constructor<?> constructor:inits) {
            		info.inits.add(new BeancpInitInfo(info,constructor));
            	}
            	info.inits.sort((c1,c2)->{
            		return c1.order()-c2.order();
            	});
            }
            if(!info.isPrimitive&&Cloneable.class.isAssignableFrom(info.fclazz)) {
            	Class mclazz=info.fclazz;
            	Method method=null;
            	while(mclazz!=Object.class) {
            		try {
            			
						method=mclazz.getDeclaredMethod("clone");
					} catch (Exception e) {
					}
            		if(method!=null) {
            			break;
            		}
            		mclazz=mclazz.getSuperclass();
            	}
            	if(method!=null) {
            		info.cloneInfo=new BeancpCloneInfo(info, method);
            		if(info.cloneInfo.needProxy()) {
            			
            		}
            		
            	}
            	
            } 
            
            return info;
        });
    	//infoff.initProxyOpClass();
    	return infoff;
    }
    public void initProxyOpClass() {
    	if(isInitTargetProxy) {
    		return;
    	}
    	synchronized (this) {
    		if(isInitTargetProxy) {
        		return;
        	}
    		if(!this.isPrimitive) {
    			BeancpInfoASMTool.initProxyOpClass(this);
    		}
    		this.isInitTargetProxy=true;
		}
    	
    	
    	
    }
    public Constructor getInitMethod(int idx) {
    	return this.inits.get(idx).oconstructor;
    }
    public Field getGetField(String key,int idx) {
    	return (Field) this.fields.get(key).getGetterList().get(idx).omember;
    }
    public Method getGetMethod(String key,int idx) {
    	return (Method) this.fields.get(key).getGetterList().get(idx).omember;
    }
    public Field getSetField(String key,int idx) {
    	return (Field) this.fields.get(key).getSetterList().get(idx).omember;
    }
    public Method getSetMethod(String key,int idx) {
    	return (Method) this.fields.get(key).getSetterList().get(idx).omember;
    }
    public <T> T defaultInstance(){
        if(this.isPrimitive){
        	return (T) primitiveDefaultInstance();
        }
        return null;
    }
    private void transFinalType() {
    	Class fclazz=null;
    	Type ftype=null;
    	fclazz=defaultAbClass(this.clazz);
    	if(fclazz==null) {
    		int mod=this.clazz.getModifiers();
        	if(!Modifier.isPublic(mod)) {
        		if(this.clazz.isAnonymousClass()) {
        			fclazz=this.clazz.getSuperclass();
        		}
        	}
    	}
    	
    	if(fclazz==null) {
    		fclazz=this.clazz;
    	}
    	if(ftype==null) {
    		ftype=this.type;
    	}
        this.fclazz=fclazz;
        this.ftype=BeancpTypeInfo.of(this.fclazz).getFinalType(type);
        
    }
    
    private Class defaultAbClass(Class clazz) {
    	Class listClass=clazz;
    	if (listClass == Iterable.class
                || listClass == Collection.class
                || listClass == List.class
                || listClass == AbstractCollection.class
                || listClass == AbstractList.class
        ) {
            return ArrayList.class;
        } else if (listClass == Queue.class
                || listClass == Deque.class
                || listClass == AbstractSequentialList.class) {
        	return LinkedList.class;
        } else if (listClass == Set.class || listClass == AbstractSet.class) {
        	return HashSet.class;
        } else if (listClass == EnumSet.class) {
        	return HashSet.class;
        } else if (listClass == NavigableSet.class || listClass == SortedSet.class) {
        	return TreeSet.class;
        } else if (listClass == CLASS_SINGLETON) {
        	return ArrayList.class;
        } else if (listClass == CLASS_SINGLETON_LIST) {
        	return ArrayList.class;
        } else if (listClass == CLASS_ARRAYS_LIST) {
        	return ArrayList.class;
        } else if (listClass == CLASS_UNMODIFIABLE_COLLECTION) {
        	return ArrayList.class;
        } else if (listClass == CLASS_UNMODIFIABLE_LIST) {
        	return ArrayList.class;
        } else if (listClass == CLASS_UNMODIFIABLE_SET) {
        	return LinkedHashSet.class;
        } else if (listClass == CLASS_UNMODIFIABLE_SORTED_SET) {
        	return TreeSet.class;
        } else if (listClass == CLASS_UNMODIFIABLE_NAVIGABLE_SET) {
        	return TreeSet.class;
        } else {
            String typeName = listClass.getTypeName();
            switch (typeName) {
                case "com.google.common.collect.ImmutableList":
                case "com.google.common.collect.SingletonImmutableList":
                case "com.google.common.collect.RegularImmutableList":
                case "com.google.common.collect.AbstractMapBasedMultimap$RandomAccessWrappedList":
                	return ArrayList.class;
                case "com.google.common.collect.ImmutableSet":
                case "com.google.common.collect.SingletonImmutableSet":
                case "com.google.common.collect.RegularImmutableSet":
                	return ArrayList.class;
                case "com.google.common.collect.Lists$TransformingRandomAccessList":
                	return ArrayList.class;
                case "com.google.common.collect.Lists.TransformingSequentialList":
                	return LinkedList.class;
                case "java.util.Collections$SynchronizedRandomAccessList":
                	return ArrayList.class;
                case "java.util.Collections$SynchronizedCollection":
                	return ArrayList.class;
                case "java.util.Collections$SynchronizedSet":
                	return HashSet.class;
                case "java.util.Collections$SynchronizedSortedSet":
                	return TreeSet.class;
                case "java.util.Collections$SynchronizedNavigableSet":
                	return TreeSet.class;
                default:
                    break;
            }
        }
    	Class mapType=clazz;
    	if (mapType == Map.class
                || mapType == AbstractMap.class
                || mapType == CLASS_SINGLETON_MAP
        ) {
            return HashMap.class;
        } else if (mapType == CLASS_UNMODIFIABLE_MAP) {
        	return LinkedHashMap.class;
        } else if (mapType == SortedMap.class
                || mapType == CLASS_UNMODIFIABLE_SORTED_MAP
                || mapType == CLASS_UNMODIFIABLE_NAVIGABLE_MAP
        ) {
        	return TreeMap.class;
        } else if (mapType == ConcurrentMap.class) {
        	return ConcurrentHashMap.class;
        } else if (mapType == ConcurrentNavigableMap.class) {
        	return ConcurrentSkipListMap.class;
        } else {
            switch (mapType.getTypeName()) {
                case "com.google.common.collect.ImmutableMap":
                case "com.google.common.collect.RegularImmutableMap":
                	return HashMap.class;
                case "com.google.common.collect.SingletonImmutableBiMap":
                	return HashMap.class;
                case "java.util.Collections$SynchronizedMap":
                	return HashMap.class;
                case "java.util.Collections$SynchronizedNavigableMap":
                	return TreeMap.class;
                case "java.util.Collections$SynchronizedSortedMap":
                	return TreeMap.class;
                default:
                    break;
            }
        }
    	return null;
    }
    
    private Object primitiveDefaultInstance() {
    	if(Long.TYPE==type) {
			return 0L;
		}else if(Boolean.TYPE==type) {
			return false;
		}else if(Character.TYPE==type) {
			return (char)0;
		}else if(Byte.TYPE==type) {
			return (byte)0;
		}else if(Short.TYPE==type) {
			return (short)0;
		}else if(Integer.TYPE==type) {
			return (int)0;
		}else if(Float.TYPE==type) {
			return (float)0;
		}else if(Double.TYPE==type) {
			return (double)0;
		}
    	return null;
    }
    
    public <T> T instance(){
        if(this.isPrimitive){
            return this.defaultInstance();
        }
        try {
            return (T) clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public int getId(){
        return this.id;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BeancpInfo){
            return ((BeancpInfo)obj).id==this.id;
        }
        return false;
    }
    @Override
    public int hashCode() {
        return id;
    }

	public Type getFtype() {
		return ftype;
	}
    public Enum[] getEnumConstants() {
    	return this.enumConstants;
    }
	
}
