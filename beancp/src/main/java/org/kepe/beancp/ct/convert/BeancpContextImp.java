package org.kepe.beancp.ct.convert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kepe.beancp.config.BeancpAllowFilter;
import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpExceptionFilter;
import org.kepe.beancp.config.BeancpValueFilter;
import org.kepe.beancp.tool.vo.Tuple2;

public final class BeancpContextImp implements BeancpContext {
	private Map<String,Set<Class>> disallowKeys;
	private Map<String,Set<Class>> allowOnlyKeys;
	private List<Tuple2<Class,BeancpAllowFilter>> allowFilters;
	private Map<String,List<Tuple2<Class,BeancpValueFilter>>> keyValueFilters;
	//private List<Tuple2<Class,BeancpValueFilter>> valueFilters;
	private BeancpExceptionFilter exceptionFilter;
	
	public BeancpContextImp() {}
	
	@Override
	public BeancpContext disallowKey(Class<?> clazz,String... key) {
		if(clazz==null) {return this;}
		if(disallowKeys==null) {
			disallowKeys=new HashMap<>();
		}
		for(String k:key) {
			disallowKeys.computeIfAbsent(k, value->new HashSet<>()).add(clazz);
		}
		return this;
	}
	@Override
	public BeancpContext allowOnly(Class<?> clazz,String... key) {
		if(clazz==null) {return this;}
		if(allowOnlyKeys==null) {
			allowOnlyKeys=new HashMap<>();
		}
		for(String k:key) {
			allowOnlyKeys.computeIfAbsent(k, value->new HashSet<>()).add(clazz);
		}
		return this;
	}
	@Override
	public BeancpContext addAllowFilter(Class<?> clazz,BeancpAllowFilter allowFilter) {
		if(clazz==null||allowFilter==null) {return this;}
		if(allowFilters==null) {
			allowFilters=new ArrayList<>();
		}
		allowFilters.add(new Tuple2(clazz,allowFilter));
		return this;
	}
	@Override
	public BeancpContext addValueFilter(Class<?> clazz,String key,BeancpValueFilter valueFilter) {
		if(clazz==null||valueFilter==null) {return this;}
		if(keyValueFilters==null) {
			keyValueFilters=new HashMap<>();
		}
		keyValueFilters.computeIfAbsent(key, value->new ArrayList<>()).add(new Tuple2(clazz,valueFilter));
		return this;
	}
//	@Override
//	public BeancpContext addValueFilter(Class<?> clazz,BeancpValueFilter valueFilter) {
//		if(clazz==null||valueFilter==null) {return this;}
//		if(valueFilters==null) {
//			valueFilters=new ArrayList<>();
//		}
//		valueFilters.add(new Tuple2(clazz,valueFilter));
//		return this;
//	}
	
	@Override
	public BeancpContext setExceptionFilter(BeancpExceptionFilter exceptionFilter) {
		this.exceptionFilter=exceptionFilter;
		return this;
	}
	protected int keyState(Class objClass,String key) {
		if(this.disallowKeys!=null) {
			Set<Class> sets=disallowKeys.get(key);
			if(sets!=null) {
				for(Class clazz:sets) {
					if(clazz.isAssignableFrom(objClass)) {
						return -1;
					}
				}
			}
		}
		if(this.allowOnlyKeys!=null) {
			Set<Class> sets=allowOnlyKeys.get(key);
			if(sets!=null) {
				for(Class clazz:sets) {
					if(clazz.isAssignableFrom(objClass)) {
						return 1;
					}
				}
			}
		}
		if(this.allowFilters!=null) {
			for(Tuple2<Class,BeancpAllowFilter> t2:this.allowFilters) {
				if(t2.r1.isAssignableFrom(objClass)) {
					Boolean b=t2.r2.isAllow(null, key);
					if(b!=null) {
						if(b) {
							return 1;
						}else {
							return -1;
						}
					}
				}
			}
		}
		return 0;
	}
	protected int keyState(Object obj,String key) {
		if(this.disallowKeys!=null) {
			Set<Class> sets=disallowKeys.get(key);
			if(sets!=null) {
				for(Class clazz:sets) {
					if(clazz.isInstance(obj)) {
						return -1;
					}
				}
			}
		}
		if(this.allowOnlyKeys!=null) {
			Set<Class> sets=allowOnlyKeys.get(key);
			if(sets!=null) {
				for(Class clazz:sets) {
					if(clazz.isInstance(obj)) {
						return 1;
					}
				}
			}
		}
		if(this.allowFilters!=null) {
			for(Tuple2<Class,BeancpAllowFilter> t2:this.allowFilters) {
				if(t2.r1.isInstance(obj)) {
					Boolean b=t2.r2.isAllow(obj, key);
					if(b!=null) {
						if(b) {
							return 1;
						}else {
							return -1;
						}
					}
				}
			}
		}
		return 0;
	}
	protected boolean hasValueFilter(Object obj,String key) {
		if(this.keyValueFilters!=null) {
			List<Tuple2<Class,BeancpValueFilter>> list=keyValueFilters.get(key);
			if(list!=null) {
				for(Tuple2<Class,BeancpValueFilter> t2:list) {
					if(t2.r1.isInstance(obj)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	protected BeancpValueFilter getValueFilter(Object obj,String key) {
		if(this.keyValueFilters!=null) {
			List<Tuple2<Class,BeancpValueFilter>> list=keyValueFilters.get(key);
			if(list!=null) {
				for(Tuple2<Class,BeancpValueFilter> t2:list) {
					if(t2.r1.isInstance(obj)) {
						return t2.r2;
					}
				}
			}
		}
		return null;
	}
	protected BeancpValueFilter getValueFilter(Class clazz,String key) {
		if(this.keyValueFilters!=null) {
			List<Tuple2<Class,BeancpValueFilter>> list=keyValueFilters.get(key);
			if(list!=null) {
				for(Tuple2<Class,BeancpValueFilter> t2:list) {
					if(t2.r1.isAssignableFrom(clazz)) {
						return t2.r2;
					}
				}
			}
		}
		return null;
	}
	protected Object filterValue(Object obj,String key,Object value) {
		if(this.keyValueFilters!=null) {
			List<Tuple2<Class,BeancpValueFilter>> list=keyValueFilters.get(key);
			if(list!=null) {
				for(Tuple2<Class,BeancpValueFilter> t2:list) {
					if(t2.r1.isInstance(obj)) {
						return t2.r2.filterValue(obj, key, value);
					}
				}
			}
		}
		return null;
	}
	
	protected BeancpExceptionFilter getExceptionFilter() {
		return this.exceptionFilter;
	}
	
	
}
