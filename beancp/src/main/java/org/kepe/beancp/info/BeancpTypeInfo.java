package org.kepe.beancp.info;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.kepe.beancp.tool.BeancpBeanTool;
import org.kepe.beancp.tool.vo.Tuple2;

public class BeancpTypeInfo {
    private static ThreadLocal<Map<Type,BeancpTypeInfo>> typeLocal=new ThreadLocal<>();
    private static final Map<Type,BeancpTypeInfo> C_MAP=new ConcurrentHashMap<>();
    
    private List<Tuple2<Class<?>,TypeVariable<?>[]>> genericSuperInfo;
    private Type type;
    private Class<?> rawClass;
    private BeancpTypeInfo upperBound;
    private BeancpTypeInfo lowerBound;
    private BeancpTypeInfo componentType;
    private int arrayCount;
    private TypeVariable<?>[] typeVariables;
    private int mode;//class  1 class<T>  2 T  3 ?  4 array
    private boolean isObject;
    private boolean isPrim;
    private BeancpTypeInfo[] actualTypeArguments;
    
    public Class<?> getRawClass() {
    	return rawClass;
    }
    public Type getType() {
    	return type;
    }
    
    public BeancpTypeInfo ofTypeArguments(Type... typeArguments) {
    	if(actualTypeArguments==null||actualTypeArguments.length==0) {
    		return this;
    	}
    	int len=actualTypeArguments.length;
    	if(len<typeArguments.length) {
    		typeArguments=Arrays.copyOf(typeArguments, len);
    	}else if(len>typeArguments.length) {
    		typeArguments=Arrays.copyOf(typeArguments, len);
    		for(int i=typeArguments.length;i<len;i++) {
    			typeArguments[i]=actualTypeArguments[i].type;
    		}
    	}
    	return of(BeancpBeanTool.newParameterizedTypeWithOwner(null, rawClass, typeArguments));
    }
    
    public boolean instanceOf(BeancpTypeInfo info){
    	if(this.type.equals(info.type)) {
    		return true;
    	}
    	if(info.isObject&&!isPrim) {
    		return true;
    	}
    	switch(this.mode) {
    	case 0:
    		switch(info.mode) {
    		case 0:
    			return info.rawClass.isAssignableFrom(this.rawClass);
    		case 1:
    			return info.rawClass.isAssignableFrom(this.rawClass);
    		case 2:
    			return this.instanceOf(info.upperBound);
    		case 3:
    			if(info.upperBound!=null&&!this.instanceOf(info.upperBound)) {
    				return false;
    			}
    			if(info.lowerBound!=null) {
    				return info.lowerBound.instanceOf(this);
    			}
    			return true;
    		case 4:
    			return false;
    		}
    		break;
    	case 1:
    		switch(info.mode) {
    		case 0:
    			return info.rawClass.isAssignableFrom(this.rawClass);
    		case 1:
    			if(info.rawClass==this.rawClass) {
    				for(int i=0;i<this.actualTypeArguments.length;i++) {
    					if(!this.actualTypeArguments[i].instanceOf(info.actualTypeArguments[i])) {
    						return false;
    					}
    				}
    				return true;
    			}else if(info.rawClass.isAssignableFrom(this.rawClass)) {
    				for(Tuple2<Class<?>,TypeVariable<?>[]> t2:genericSuperInfo) {
        				if(t2.r1==info.rawClass) {
        					TypeVariable<?>[] tvs2=t2.r2;
        					for(int i=0;i<tvs2.length;i++) {
        						TypeVariable<?> tv=tvs2[i];
        						if(tv!=null) {
        							for(int j=0;j<this.typeVariables.length;i++) {
        								if(this.typeVariables[j].getName().equals(tv.getName())) {
        									if(this.actualTypeArguments[j].instanceOf(info.actualTypeArguments[i])) {
        										return false;
        									}
        								}
        							}
        						}
        					}
        				}
        			}
    				return true;
    			}
    			return false;
    		case 2:
    			return this.instanceOf(info.upperBound);
    		case 3:
    			if(info.upperBound!=null&&!this.instanceOf(info.upperBound)) {
    				return false;
    			}
    			if(info.lowerBound!=null) {
    				return info.lowerBound.instanceOf(this);
    			}
    			return true;
    		case 4:
    			return false;
    		}
    		break;
    	case 2://T
    		switch(info.mode) {
    		case 0:
    			return info.instanceOf(this.upperBound);
    		case 1:
    			return info.instanceOf(this.upperBound);
    		case 2:
    			return true;
    		case 3:
    			if(info.lowerBound!=null) {
    				return info.lowerBound.instanceOf(this.upperBound);
    			}
    			return true;
    		case 4:
    			return info.instanceOf(this.upperBound);
    		}
    		break;
    	case 3:
    		switch(info.mode) {
    		case 0:
    			if(this.upperBound!=null&&!info.instanceOf(this.upperBound)) {
    				return false;
    			}
    			if(this.lowerBound!=null&&!this.lowerBound.instanceOf(info)) {
    				return false;
    			}
    			return true;
    		case 1:
    			if(this.upperBound!=null&&!info.instanceOf(this.upperBound)) {
    				return false;
    			}
    			if(this.lowerBound!=null&&!this.lowerBound.instanceOf(info)) {
    				return false;
    			}
    			return true;
    		case 2:
    			if(this.lowerBound!=null) {
    				return this.lowerBound.instanceOf(info.upperBound);
    			}
    			return true;
    		case 3:
    			if(this.lowerBound!=null&&info.upperBound!=null&&!this.lowerBound.instanceOf(info.upperBound)) {
    				return false;
    			}
    			if(info.lowerBound!=null&&this.upperBound!=null&&!info.lowerBound.instanceOf(this.upperBound)) {
    				return false;
    			}
    			return true;
    		case 4:
    			if(this.upperBound!=null&&!info.instanceOf(this.upperBound)) {
    				return false;
    			}
    			if(this.lowerBound!=null&&!this.lowerBound.instanceOf(info)) {
    				return false;
    			}
    			return true;
    		}
    		break;
    	case 4:
    		switch(info.mode) {
    		case 0:
    			return false;
    		case 1:
    			return false;
    		case 2:
    			return this.instanceOf(info.upperBound);
    		case 3:
    			if(info.upperBound!=null&&!this.instanceOf(info.upperBound)) {
    				return false;
    			}
    			if(info.lowerBound!=null) {
    				return info.lowerBound.instanceOf(this);
    			}
    			return true;
    		case 4:
    			if(this.arrayCount==info.arrayCount) {
    				return this.componentType.instanceOf(info.componentType);
    			}
    			return false;
    		}
    		break;
    	}
    	return false;
    }
    
    
    public static BeancpTypeInfo of(Type type) {
    	BeancpTypeInfo info=C_MAP.get(type);
    	if(info==null) {
    		info=new BeancpTypeInfo();
    		info.type=type;
    		info.rawClass=getClassByType(type);
    		info.init();
    		C_MAP.put(type, info);
    	}
    	return info;
    	
    }
    private BeancpTypeInfo getTypeInfo(Type type) {
    	Map<Type,BeancpTypeInfo> map=typeLocal.get();
    	if(map==null) {
    		return of(type);
    	}
    	BeancpTypeInfo info=map.get(type);
    	if(info!=null) {
    		return info;
    	}
    	return of(type);
    }
    private void init() {
    	Map<Type,BeancpTypeInfo> localmap=typeLocal.get();
    	if(localmap==null) {
    		localmap=new HashMap<>();
    		typeLocal.set(localmap);
    	}
    	localmap.put(type, this);
    	try {
    		this.isObject=Object.class==type;
    		this.isPrim=this.rawClass.isPrimitive();
    		this.genericSuperInfo=new ArrayList<>();
        	this.typeVariables=new TypeVariable[0];
        	if (type.getClass() == Class.class) {
        		Class clazz=(Class)type;
        		if(clazz.isArray()) {
        			this.mode=4;
        			int jz=1;
        			Class componentType=clazz.getComponentType();
        			while(true) {
                    	if(componentType.isArray()) {
                    		componentType = componentType.getComponentType();
                    		jz++;
                    	}else {
                    		break;
                    	}
                    }
        			this.arrayCount=jz;
        			this.typeVariables=componentType.getTypeParameters();
        			this.componentType=getTypeInfo(componentType);
        			addGenericSuperInfo(genericSuperInfo,componentType);
        		}else {
        			this.typeVariables=clazz.getTypeParameters();
        			if(typeVariables.length>0) {
        				this.mode=1;
        				Type[] types=this.typeVariables;
                    	this.actualTypeArguments=new BeancpTypeInfo[types.length];
                    	for(int i=0;i<types.length;i++) {
                    		this.actualTypeArguments[i]=getTypeInfo(types[i]);
                    	}
        			}
        			addGenericSuperInfo(genericSuperInfo,type);
        		}
            }else if (type instanceof ParameterizedType) {
            	this.mode=1;
            	this.typeVariables=rawClass.getTypeParameters();
            	Type[] types=((ParameterizedType)type).getActualTypeArguments();
            	this.actualTypeArguments=new BeancpTypeInfo[types.length];
            	for(int i=0;i<types.length;i++) {
            		this.actualTypeArguments[i]=getTypeInfo(types[i]);
            	}
            	addGenericSuperInfo(genericSuperInfo,type);
            }else if (type instanceof TypeVariable) {
            	this.mode=2;
                Type boundType = ((TypeVariable<?>) type).getBounds()[0];
                this.upperBound=getTypeInfo(boundType);
            }else if (type instanceof WildcardType) {
            	this.mode=3;
                Type[] upperBounds = ((WildcardType) type).getUpperBounds();
                if (upperBounds.length == 1) {
                	this.upperBound=getTypeInfo(upperBounds[0]);
                }
                Type[] lowerBounds = ((WildcardType) type).getLowerBounds();
                if (lowerBounds.length == 1) {
                	this.lowerBound=getTypeInfo(lowerBounds[0]);
                }
            }else if (type instanceof GenericArrayType) {
            	this.mode=4;
                Type genericComponentType = ((GenericArrayType) type).getGenericComponentType();
                int jz=1;
                while(true) {
                	if(genericComponentType instanceof GenericArrayType) {
                		genericComponentType = ((GenericArrayType) genericComponentType).getGenericComponentType();
                		jz++;
                	}else {
                		break;
                	}
                }
                this.componentType=getTypeInfo(genericComponentType);
                this.arrayCount=jz;
                List<Tuple2<Class<?>,TypeVariable<?>[]>> list=new ArrayList<>();
                addGenericSuperInfo(list,genericComponentType);
                if(!list.isEmpty()) {
                	genericSuperInfo=list;
                	this.typeVariables=rawClass.getTypeParameters();
                }else if(type instanceof TypeVariable) {
                	Type boundType = ((TypeVariable<?>) genericComponentType).getBounds()[0];
                    this.upperBound=getTypeInfo(boundType);
                }else if (type instanceof WildcardType) {
                    Type[] upperBounds = ((WildcardType) genericComponentType).getUpperBounds();
                    if (upperBounds.length == 1) {
                    	this.upperBound=getTypeInfo(upperBounds[0]);
                    }
                    Type[] lowerBounds = ((WildcardType) genericComponentType).getLowerBounds();
                    if (lowerBounds.length == 1) {
                    	this.lowerBound=getTypeInfo(lowerBounds[0]);
                    }
                }
            }
    	} finally {
    		localmap.remove(type);
    		if(localmap.isEmpty()) {
    			typeLocal.set(null);
    		}
		}
    	
		
	}
    
    
	private void addGenericSuperInfo(List list,Type type) {
		if (type.getClass() == Class.class) {
			Class clazz=(Class)type;
			if(clazz!=Object.class) {
				TypeVariable[] tvs=clazz.getTypeParameters();
				if(tvs.length>0) {
					addGenericSuperInfo(list,tvs,clazz.getSuperclass());
					for(Class clazz1:clazz.getInterfaces()) {
						addGenericSuperInfo(list,tvs,clazz1);
					}
				}
			}
        }else if (type instanceof ParameterizedType) {
			Class clazz=getClassByType(type);
			TypeVariable[] tvs=clazz.getTypeParameters();
			if(clazz!=Object.class) {
				if(tvs.length>0) {
					addGenericSuperInfo(list,tvs,clazz.getSuperclass());
					for(Class clazz1:clazz.getInterfaces()) {
						addGenericSuperInfo(list,tvs,clazz1);
					}
				}
			}
        }
	}
	private void addGenericSuperInfo4ArrayType(List list,TypeVariable[] superTvs,Class clazz) {
		TypeVariable[] tvs=clazz.getTypeParameters();
		if(tvs.length==0) {
			return;
		}
		boolean hasFind=false;
		for(int i=0;i<tvs.length;i++) {
			TypeVariable tv=tvs[i];
			if(tv==null) {
				continue;
			}
			boolean find=false;
			for(TypeVariable tv2:superTvs) {
				if(tv2==null) {
					continue;
				}
				if(Objects.equals(tv.getName(), tv2.getName())) {
					find=true;
					hasFind=true;
					break;
				}
			}
			if(!find) {
				tvs[i]=null;
			}
		}
		if(!hasFind) {
			return;
		}
		list.add(new Tuple2(clazz,tvs));
		addGenericSuperInfo(list,tvs,clazz.getSuperclass());
		for(Class clazz1:clazz.getInterfaces()) {
			addGenericSuperInfo(list,tvs,clazz1);
		}
	}
	private void addGenericSuperInfo(List list,TypeVariable[] superTvs,Class clazz) {
		if(clazz==null) {
			return;
		}
		TypeVariable[] tvs=clazz.getTypeParameters();
		if(tvs.length==0) {
			return;
		}
		boolean hasFind=false;
		for(int i=0;i<tvs.length;i++) {
			TypeVariable tv=tvs[i];
			if(tv==null) {
				continue;
			}
			boolean find=false;
			for(TypeVariable tv2:superTvs) {
				if(tv2==null) {
					continue;
				}
				if(Objects.equals(tv.getName(), tv2.getName())) {
					find=true;
					hasFind=true;
					break;
				}
			}
			if(!find) {
				tvs[i]=null;
			}
		}
		if(!hasFind) {
			return;
		}
		list.add(new Tuple2(clazz,tvs));
		addGenericSuperInfo(list,tvs,clazz.getSuperclass());
		for(Class clazz1:clazz.getInterfaces()) {
			addGenericSuperInfo(list,tvs,clazz1);
		}
	}
	
	private static Class<?> getClassByType(Type type){
    	if (type == null) {
            return null;
        }

        if (type.getClass() == Class.class) {
            return (Class<?>) type;
        }

        if (type instanceof ParameterizedType) {
            return getClassByType(((ParameterizedType) type).getRawType());
        }

        if (type instanceof TypeVariable) {
            Type boundType = ((TypeVariable<?>) type).getBounds()[0];
            if (boundType instanceof Class) {
                return (Class) boundType;
            }
            return getClassByType(boundType);
        }

        if (type instanceof WildcardType) {
            Type[] upperBounds = ((WildcardType) type).getUpperBounds();
            if (upperBounds.length == 1) {
                return getClassByType(upperBounds[0]);
            }
        }

        if (type instanceof GenericArrayType) {
        	//TODO Accelerate speed
            Type genericComponentType = ((GenericArrayType) type).getGenericComponentType();
            Class<?> componentClass = getClassByType(genericComponentType);
            return getArrayClass(componentClass);
        }
        return Object.class;
    }
	private static Class<?> getArrayClass(Class componentClass) {
        if (componentClass == int.class) {
            return int[].class;
        }
        if (componentClass == byte.class) {
            return byte[].class;
        }
        if (componentClass == short.class) {
            return short[].class;
        }
        if (componentClass == long.class) {
            return long[].class;
        }
        if (componentClass == String.class) {
            return String[].class;
        }
        if (componentClass == Object.class) {
            return Object[].class;
        }
        return Array.newInstance(componentClass, 1).getClass();
    }
}
