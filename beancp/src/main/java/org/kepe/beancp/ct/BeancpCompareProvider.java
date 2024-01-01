package org.kepe.beancp.ct;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.kepe.beancp.config.BeancpCompare;
import org.kepe.beancp.config.BeancpCompareFlag;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.asm.BeancpInfoASMTool;
import org.kepe.beancp.ct.convert.BeancpConvertCustomProvider;
import org.kepe.beancp.ct.convert.BeancpConvertMapper;
import org.kepe.beancp.ct.convert.BeancpConvertNonProvider;
import org.kepe.beancp.ct.converter.BeancpConverterCompareInfo;
import org.kepe.beancp.ct.converter.BeancpConverterInfo;
import org.kepe.beancp.ct.invocation.BeancpInvocation;
import org.kepe.beancp.ct.itf.BeancpASMConverter;
import org.kepe.beancp.ct.itf.BeancpConverter;
import org.kepe.beancp.ct.itf.BeancpCustomConverter;
import org.kepe.beancp.info.BeancpCompareInfo;
import org.kepe.beancp.info.BeancpInfo;

public class BeancpCompareProvider implements BeancpCompare {
	private volatile BeancpCompare compare;
	private BeancpInfo fromInfo;
	private BeancpInfo toInfo;
	private BeancpConvertMapper mapper;
	private int compareIdx;
	private boolean positive=true;
	private boolean comparePositive=true;
	private static List<BeancpConverterCompareInfo> compareList=new ArrayList<>();
	private static Map<BeancpInfo,Map<BeancpInfo,BeancpCompareProvider>> C_MAP=new ConcurrentHashMap<>();
	
	public static BeancpCompareProvider of(BeancpInfo fromInfo,BeancpInfo toInfo) {
		return C_MAP.computeIfAbsent(fromInfo, key->new ConcurrentHashMap<>()).computeIfAbsent(toInfo, key->new BeancpCompareProvider(fromInfo,toInfo));
	}
	
	private BeancpCompareProvider(BeancpInfo fromInfo,BeancpInfo toInfo) {
		this.fromInfo=fromInfo;
		this.toInfo=toInfo;
		List<BeancpCompareInfo> fromList=this.fromInfo.compares;
		if(fromList!=null) {
			for(int i=0;i<fromList.size();i++) {
				BeancpCompareInfo compareInfo=fromList.get(i);
				if(toInfo.instanceOf(compareInfo.getInfo())) {
					mapper=compareInfo.getInfo().getDefaultMapper();
					compareIdx=i;
					break;
				}
			}
		}
		if(mapper==null) {
			List<BeancpCompareInfo> toList=this.toInfo.compares;
			if(fromList!=null) {
				for(int i=0;i<toList.size();i++) {
					BeancpCompareInfo compareInfo=toList.get(i);
					if(fromInfo.instanceOf(compareInfo.getInfo())) {
						mapper=compareInfo.getInfo().getDefaultMapper();
						compareIdx=i;
						positive=false;
						break;
					}
				}
			}
		}
		
		flush();
	}
	private BeancpCompareFlag reverse(BeancpCompareFlag flag) {
		if(flag==BeancpCompareFlag.LESS) {
			return BeancpCompareFlag.GREATER;
		}else if(flag==BeancpCompareFlag.GREATER) {
			return BeancpCompareFlag.LESS;
		}
		return flag;
	}
	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, T fromObj, R toObj) {
		if(fromObj==null&&toObj==null) {
			return BeancpCompareFlag.EQUALS;
		}
		if(fromObj==null) {
			return BeancpCompareFlag.LESS;
		}
		if(toObj==null) {
			return BeancpCompareFlag.GREATER;
		}
		BeancpCompareFlag flag=null;
		if(compare!=null) {
			flag=this.comparePositive?compare.compare(invocation, fromObj, toObj):reverse(compare.compare(invocation.reverse(), toObj, fromObj));
		}
		if(flag==null&&mapper!=null) {
			if(this.positive) {
				try {
					int result=mapper.compareTo(fromObj, this.compareIdx, toObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result<0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}else {
				try {
					int result=mapper.compareTo(toObj, this.compareIdx, fromObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result>0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}
		}
		if(flag==null) {
			return BeancpCompareFlag.UNKOWN;
		}
		return flag;
	}
	
	
	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, T fromObj, int toObj) {
		if(fromObj==null) {
			return BeancpCompareFlag.LESS;
		}
		BeancpCompareFlag flag=null;
		if(compare!=null) {
			flag=this.comparePositive?compare.compare(invocation, fromObj, toObj):reverse(compare.compare(invocation.reverse(), toObj, fromObj));
		}
		if(flag==null&&mapper!=null) {
			if(this.positive) {
				try {
					int result=mapper.compareTo(fromObj, this.compareIdx, toObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result<0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}else {
				try {
					int result=mapper.compareTo(toObj, this.compareIdx, fromObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result>0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}
		}
		if(flag==null) {
			return BeancpCompareFlag.UNKOWN;
		}
		return flag;
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, T fromObj, float toObj) {
		if(fromObj==null) {
			return BeancpCompareFlag.LESS;
		}
		BeancpCompareFlag flag=null;
		if(compare!=null) {
			flag=this.comparePositive?compare.compare(invocation, fromObj, toObj):reverse(compare.compare(invocation.reverse(), toObj, fromObj));
		}
		if(flag==null&&mapper!=null) {
			if(this.positive) {
				try {
					int result=mapper.compareTo(fromObj, this.compareIdx, toObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result<0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}else {
				try {
					int result=mapper.compareTo(toObj, this.compareIdx, fromObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result>0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}
		}
		if(flag==null) {
			return BeancpCompareFlag.UNKOWN;
		}
		return flag;
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, T fromObj, boolean toObj) {
		if(fromObj==null) {
			return BeancpCompareFlag.LESS;
		}
		BeancpCompareFlag flag=null;
		if(compare!=null) {
			flag=this.comparePositive?compare.compare(invocation, fromObj, toObj):reverse(compare.compare(invocation.reverse(), toObj, fromObj));
		}
		if(flag==null&&mapper!=null) {
			if(this.positive) {
				try {
					int result=mapper.compareTo(fromObj, this.compareIdx, toObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result<0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}else {
				try {
					int result=mapper.compareTo(toObj, this.compareIdx, fromObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result>0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}
		}
		if(flag==null) {
			return BeancpCompareFlag.UNKOWN;
		}
		return flag;
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, T fromObj, char toObj) {
		if(fromObj==null) {
			return BeancpCompareFlag.LESS;
		}
		BeancpCompareFlag flag=null;
		if(compare!=null) {
			flag=this.comparePositive?compare.compare(invocation, fromObj, toObj):reverse(compare.compare(invocation.reverse(), toObj, fromObj));
		}
		if(flag==null&&mapper!=null) {
			if(this.positive) {
				try {
					int result=mapper.compareTo(fromObj, this.compareIdx, toObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result<0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}else {
				try {
					int result=mapper.compareTo(toObj, this.compareIdx, fromObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result>0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}
		}
		if(flag==null) {
			return BeancpCompareFlag.UNKOWN;
		}
		return flag;
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, T fromObj, double toObj) {
		if(fromObj==null) {
			return BeancpCompareFlag.LESS;
		}
		BeancpCompareFlag flag=null;
		if(compare!=null) {
			flag=this.comparePositive?compare.compare(invocation, fromObj, toObj):reverse(compare.compare(invocation.reverse(), toObj, fromObj));
		}
		if(flag==null&&mapper!=null) {
			if(this.positive) {
				try {
					int result=mapper.compareTo(fromObj, this.compareIdx, toObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result<0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}else {
				try {
					int result=mapper.compareTo(toObj, this.compareIdx, fromObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result>0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}
		}
		if(flag==null) {
			return BeancpCompareFlag.UNKOWN;
		}
		return flag;
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, T fromObj, short toObj) {
		if(fromObj==null) {
			return BeancpCompareFlag.LESS;
		}
		BeancpCompareFlag flag=null;
		if(compare!=null) {
			flag=this.comparePositive?compare.compare(invocation, fromObj, toObj):reverse(compare.compare(invocation.reverse(), toObj, fromObj));
		}
		if(flag==null&&mapper!=null) {
			if(this.positive) {
				try {
					int result=mapper.compareTo(fromObj, this.compareIdx, toObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result<0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}else {
				try {
					int result=mapper.compareTo(toObj, this.compareIdx, fromObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result>0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}
		}
		if(flag==null) {
			return BeancpCompareFlag.UNKOWN;
		}
		return flag;
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, T fromObj, byte toObj) {
		if(fromObj==null) {
			return BeancpCompareFlag.LESS;
		}
		BeancpCompareFlag flag=null;
		if(compare!=null) {
			flag=this.comparePositive?compare.compare(invocation, fromObj, toObj):reverse(compare.compare(invocation.reverse(), toObj, fromObj));
		}
		if(flag==null&&mapper!=null) {
			if(this.positive) {
				try {
					int result=mapper.compareTo(fromObj, this.compareIdx, toObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result<0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}else {
				try {
					int result=mapper.compareTo(toObj, this.compareIdx, fromObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result>0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}
		}
		if(flag==null) {
			return BeancpCompareFlag.UNKOWN;
		}
		return flag;
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, T fromObj, long toObj) {
		if(fromObj==null) {
			return BeancpCompareFlag.LESS;
		}
		BeancpCompareFlag flag=null;
		if(compare!=null) {
			flag=this.comparePositive?compare.compare(invocation, fromObj, toObj):reverse(compare.compare(invocation.reverse(), toObj, fromObj));
		}
		if(flag==null&&mapper!=null) {
			if(this.positive) {
				try {
					int result=mapper.compareTo(fromObj, this.compareIdx, toObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result<0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}else {
				try {
					int result=mapper.compareTo(toObj, this.compareIdx, fromObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result>0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}
		}
		if(flag==null) {
			return BeancpCompareFlag.UNKOWN;
		}
		return flag;
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, int fromObj, R toObj) {
		if(toObj==null) {
			return BeancpCompareFlag.GREATER;
		}
		BeancpCompareFlag flag=null;
		if(compare!=null) {
			flag=this.comparePositive?compare.compare(invocation, fromObj, toObj):reverse(compare.compare(invocation.reverse(), toObj, fromObj));
		}
		if(flag==null&&mapper!=null) {
			if(this.positive) {
				try {
					int result=mapper.compareTo(fromObj, this.compareIdx, toObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result<0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}else {
				try {
					int result=mapper.compareTo(toObj, this.compareIdx, fromObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result>0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}
		}
		if(flag==null) {
			return BeancpCompareFlag.UNKOWN;
		}
		return flag;
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, int fromObj, int toObj) {
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, int fromObj, float toObj) {
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, int fromObj, boolean toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, int fromObj, char toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, int fromObj, double toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, int fromObj, short toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, int fromObj, byte toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, int fromObj, long toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, float fromObj, R toObj) {
		if(toObj==null) {
			return BeancpCompareFlag.GREATER;
		}
		BeancpCompareFlag flag=null;
		if(compare!=null) {
			flag=this.comparePositive?compare.compare(invocation, fromObj, toObj):reverse(compare.compare(invocation.reverse(), toObj, fromObj));
		}
		if(flag==null&&mapper!=null) {
			if(this.positive) {
				try {
					int result=mapper.compareTo(fromObj, this.compareIdx, toObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result<0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}else {
				try {
					int result=mapper.compareTo(toObj, this.compareIdx, fromObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result>0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}
		}
		if(flag==null) {
			return BeancpCompareFlag.UNKOWN;
		}
		return flag;
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, float fromObj, int toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, float fromObj, float toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, float fromObj, boolean toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, float fromObj, char toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, float fromObj, double toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, float fromObj, short toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, float fromObj, byte toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, float fromObj, long toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, boolean fromObj, R toObj) {
		if(toObj==null) {
			return BeancpCompareFlag.GREATER;
		}
		BeancpCompareFlag flag=null;
		if(compare!=null) {
			flag=this.comparePositive?compare.compare(invocation, fromObj, toObj):reverse(compare.compare(invocation.reverse(), toObj, fromObj));
		}
		if(flag==null&&mapper!=null) {
			if(this.positive) {
				try {
					int result=mapper.compareTo(fromObj, this.compareIdx, toObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result<0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}else {
				try {
					int result=mapper.compareTo(toObj, this.compareIdx, fromObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result>0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}
		}
		if(flag==null) {
			return BeancpCompareFlag.UNKOWN;
		}
		return flag;
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, boolean fromObj, int toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, boolean fromObj, float toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, boolean fromObj, boolean toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, boolean fromObj, char toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, boolean fromObj, double toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, boolean fromObj, short toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, boolean fromObj, byte toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, boolean fromObj, long toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, char fromObj, R toObj) {
		if(toObj==null) {
			return BeancpCompareFlag.GREATER;
		}
		BeancpCompareFlag flag=null;
		if(compare!=null) {
			flag=this.comparePositive?compare.compare(invocation, fromObj, toObj):reverse(compare.compare(invocation.reverse(), toObj, fromObj));
		}
		if(flag==null&&mapper!=null) {
			if(this.positive) {
				try {
					int result=mapper.compareTo(fromObj, this.compareIdx, toObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result<0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}else {
				try {
					int result=mapper.compareTo(toObj, this.compareIdx, fromObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result>0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}
		}
		if(flag==null) {
			return BeancpCompareFlag.UNKOWN;
		}
		return flag;
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, char fromObj, int toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, char fromObj, float toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, char fromObj, boolean toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, char fromObj, char toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, char fromObj, double toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, char fromObj, short toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, char fromObj, byte toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, char fromObj, long toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, double fromObj, R toObj) {
		if(toObj==null) {
			return BeancpCompareFlag.GREATER;
		}
		BeancpCompareFlag flag=null;
		if(compare!=null) {
			flag=this.comparePositive?compare.compare(invocation, fromObj, toObj):reverse(compare.compare(invocation.reverse(), toObj, fromObj));
		}
		if(flag==null&&mapper!=null) {
			if(this.positive) {
				try {
					int result=mapper.compareTo(fromObj, this.compareIdx, toObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result<0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}else {
				try {
					int result=mapper.compareTo(toObj, this.compareIdx, fromObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result>0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}
		}
		if(flag==null) {
			return BeancpCompareFlag.UNKOWN;
		}
		return flag;
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, double fromObj, int toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, double fromObj, float toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, double fromObj, boolean toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, double fromObj, char toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, double fromObj, double toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, double fromObj, short toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, double fromObj, byte toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, double fromObj, long toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, short fromObj, R toObj) {
		if(toObj==null) {
			return BeancpCompareFlag.GREATER;
		}
		BeancpCompareFlag flag=null;
		if(compare!=null) {
			flag=this.comparePositive?compare.compare(invocation, fromObj, toObj):reverse(compare.compare(invocation.reverse(), toObj, fromObj));
		}
		if(flag==null&&mapper!=null) {
			if(this.positive) {
				try {
					int result=mapper.compareTo(fromObj, this.compareIdx, toObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result<0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}else {
				try {
					int result=mapper.compareTo(toObj, this.compareIdx, fromObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result>0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}
		}
		if(flag==null) {
			return BeancpCompareFlag.UNKOWN;
		}
		return flag;
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, short fromObj, int toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, short fromObj, float toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, short fromObj, boolean toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, short fromObj, char toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, short fromObj, double toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, short fromObj, short toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, short fromObj, byte toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, short fromObj, long toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, byte fromObj, R toObj) {
		if(toObj==null) {
			return BeancpCompareFlag.GREATER;
		}
		BeancpCompareFlag flag=null;
		if(compare!=null) {
			flag=this.comparePositive?compare.compare(invocation, fromObj, toObj):reverse(compare.compare(invocation.reverse(), toObj, fromObj));
		}
		if(flag==null&&mapper!=null) {
			if(this.positive) {
				try {
					int result=mapper.compareTo(fromObj, this.compareIdx, toObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result<0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}else {
				try {
					int result=mapper.compareTo(toObj, this.compareIdx, fromObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result>0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}
		}
		if(flag==null) {
			return BeancpCompareFlag.UNKOWN;
		}
		return flag;
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, byte fromObj, int toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, byte fromObj, float toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, byte fromObj, boolean toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, byte fromObj, char toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, byte fromObj, double toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, byte fromObj, short toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, byte fromObj, byte toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, byte fromObj, long toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, long fromObj, R toObj) {
		if(toObj==null) {
			return BeancpCompareFlag.GREATER;
		}
		BeancpCompareFlag flag=null;
		if(compare!=null) {
			flag=this.comparePositive?compare.compare(invocation, fromObj, toObj):reverse(compare.compare(invocation.reverse(), toObj, fromObj));
		}
		if(flag==null&&mapper!=null) {
			if(this.positive) {
				try {
					int result=mapper.compareTo(fromObj, this.compareIdx, toObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result<0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}else {
				try {
					int result=mapper.compareTo(toObj, this.compareIdx, fromObj);
					if(result==0) {
						return BeancpCompareFlag.EQUALS;
					}else if(result>0) {
						return BeancpCompareFlag.LESS;
					}else {
						return BeancpCompareFlag.GREATER;
					}
				} catch (Exception e) {}
			}
		}
		if(flag==null) {
			return BeancpCompareFlag.UNKOWN;
		}
		return flag;
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, long fromObj, int toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, long fromObj, float toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, long fromObj, boolean toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, long fromObj, char toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, long fromObj, double toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, long fromObj, short toObj) {
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, long fromObj, byte toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, long fromObj, long toObj) {
		
		return compare==null?BeancpCompareFlag.UNKOWN:compare.compare(invocation, fromObj, toObj);
	}

	public BeancpCompare getCompare() {
		return compare;
	}
	public void setCompare(BeancpCompare compare) {
		this.compare = compare;
	}
	
	private void flush() {
		int length=compareList.size();
		boolean isFind=false;
		for(int i=length-1;i>=0;i--){
			BeancpConverterCompareInfo info=compareList.get(i);
            if(info.matches(fromInfo,toInfo)){
            	BeancpCompare converter=info.getConverter(fromInfo,toInfo);
                this.compare=converter;
                this.comparePositive=true;
                isFind=true;
                break;
            }
        }
		if(!isFind) {
			for(int i=length-1;i>=0;i--){
				BeancpConverterCompareInfo info=compareList.get(i);
	            if(info.matches(toInfo,fromInfo)){
	            	BeancpCompare converter=info.getConverter(toInfo,fromInfo);
	                this.compare=converter;
	                this.comparePositive=false;
	                break;
	            }
	        }
		}
	}
	
	public static void register(BeancpConverterCompareInfo info) {
    	synchronized(compareList){
    		compareList.add(info);
    		compareList.sort((key1,key2)->{
                return key1.getPriority()-key2.getPriority();
            });
        }
		for(Map<BeancpInfo, BeancpCompareProvider> map1:C_MAP.values()) {
			for(BeancpCompareProvider provider:map1.values()) {
				provider.flush();
			}
		}
    	
    }

}
