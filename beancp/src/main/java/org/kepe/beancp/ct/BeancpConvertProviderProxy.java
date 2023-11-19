package org.kepe.beancp.ct;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.asm.BeancpInfoASMTool;
import org.kepe.beancp.ct.convert.BeancpConvertCustomProvider;
import org.kepe.beancp.ct.convert.BeancpConvertNonProvider;
import org.kepe.beancp.ct.converter.BeancpConverterInfo;
import org.kepe.beancp.ct.invocation.BeancpInvocationImp;
import org.kepe.beancp.ct.itf.BeancpASMConverter;
import org.kepe.beancp.ct.itf.BeancpConvertByObject;
import org.kepe.beancp.ct.itf.BeancpCustomConverter;
import org.kepe.beancp.exception.BeancpException;
import org.kepe.beancp.exception.BeancpException.EType;
import org.kepe.beancp.info.BeancpInfo;

public class BeancpConvertProviderProxy extends BeancpConvertProvider implements BeancpConvertByObject{
	protected static BeancpConvertProvider createProxy(BeancpConvertProvider provider) {
		return new BeancpConvertProviderProxy(provider);
	}

	private BeancpConvertProvider provider;
	private BeancpConvertByObject bo;
	private BeancpConvertProviderProxy(BeancpConvertProvider provider) {
		this(null,provider.flag,provider.info,provider.fromInfo,provider.toInfo);
		this.provider=provider;
		Type fromtype=this.fromInfo.getBType();
		Type totype=this.toInfo.getBType();
		if(!this.fromInfo.isPrimitive) {
			if(!this.toInfo.isPrimitive) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj, toObj));
			}else if(totype==Integer.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj, toObj==null?0:(int)toObj));
			}else if(totype==Float.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj, toObj==null?(float)0:(float)toObj));
			}else if(totype==Boolean.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj, toObj==null?false:(boolean)toObj));
			}else if(totype==Character.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj, toObj==null?(char)0:(char)toObj));
			}else if(totype==Double.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj, toObj==null?(double)0:(double)toObj));
			}else if(totype==Short.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj, toObj==null?(short)0:(short)toObj));
			}else if(totype==Byte.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj, toObj==null?(byte)0:(byte)toObj));
			}else if(totype==Long.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj, toObj==null?0L:(long)toObj));
			}
		}else if(fromtype==Integer.TYPE) {
			if(!this.toInfo.isPrimitive) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?0:(int)fromObj, toObj));
			}else if(totype==Integer.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?0:(int)fromObj, toObj==null?0:(int)toObj));
			}else if(totype==Float.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?0:(int)fromObj, toObj==null?(float)0:(float)toObj));
			}else if(totype==Boolean.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?0:(int)fromObj, toObj==null?false:(boolean)toObj));
			}else if(totype==Character.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?0:(int)fromObj, toObj==null?(char)0:(char)toObj));
			}else if(totype==Double.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?0:(int)fromObj, toObj==null?(double)0:(double)toObj));
			}else if(totype==Short.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?0:(int)fromObj, toObj==null?(short)0:(short)toObj));
			}else if(totype==Byte.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?0:(int)fromObj, toObj==null?(byte)0:(byte)toObj));
			}else if(totype==Long.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?0:(int)fromObj, toObj==null?0L:(long)toObj));
			}
		}else if(fromtype==Float.TYPE) {
			if(!this.toInfo.isPrimitive) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(float)0:(float)fromObj, toObj));
			}else if(totype==Integer.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(float)0:(float)fromObj, toObj==null?0:(int)toObj));
			}else if(totype==Float.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(float)0:(float)fromObj, toObj==null?(float)0:(float)toObj));
			}else if(totype==Boolean.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(float)0:(float)fromObj, toObj==null?false:(boolean)toObj));
			}else if(totype==Character.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(float)0:(float)fromObj, toObj==null?(char)0:(char)toObj));
			}else if(totype==Double.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(float)0:(float)fromObj, toObj==null?(double)0:(double)toObj));
			}else if(totype==Short.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(float)0:(float)fromObj, toObj==null?(short)0:(short)toObj));
			}else if(totype==Byte.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(float)0:(float)fromObj, toObj==null?(byte)0:(byte)toObj));
			}else if(totype==Long.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(float)0:(float)fromObj, toObj==null?0L:(long)toObj));
			}
		}else if(fromtype==Boolean.TYPE) {
			if(!this.toInfo.isPrimitive) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?false:(boolean)fromObj, toObj));
			}else if(totype==Integer.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?false:(boolean)fromObj, toObj==null?0:(int)toObj));
			}else if(totype==Float.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?false:(boolean)fromObj, toObj==null?(float)0:(float)toObj));
			}else if(totype==Boolean.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?false:(boolean)fromObj, toObj==null?false:(boolean)toObj));
			}else if(totype==Character.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?false:(boolean)fromObj, toObj==null?(char)0:(char)toObj));
			}else if(totype==Double.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?false:(boolean)fromObj, toObj==null?(double)0:(double)toObj));
			}else if(totype==Short.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?false:(boolean)fromObj, toObj==null?(short)0:(short)toObj));
			}else if(totype==Byte.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?false:(boolean)fromObj, toObj==null?(byte)0:(byte)toObj));
			}else if(totype==Long.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?false:(boolean)fromObj, toObj==null?0L:(long)toObj));
			}
		}else if(fromtype==Character.TYPE) {
			if(!this.toInfo.isPrimitive) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(char)0:(char)fromObj, toObj));
			}else if(totype==Integer.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(char)0:(char)fromObj, toObj==null?0:(int)toObj));
			}else if(totype==Float.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(char)0:(char)fromObj, toObj==null?(float)0:(float)toObj));
			}else if(totype==Boolean.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(char)0:(char)fromObj, toObj==null?false:(boolean)toObj));
			}else if(totype==Character.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(char)0:(char)fromObj, toObj==null?(char)0:(char)toObj));
			}else if(totype==Double.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(char)0:(char)fromObj, toObj==null?(double)0:(double)toObj));
			}else if(totype==Short.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(char)0:(char)fromObj, toObj==null?(short)0:(short)toObj));
			}else if(totype==Byte.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(char)0:(char)fromObj, toObj==null?(byte)0:(byte)toObj));
			}else if(totype==Long.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(char)0:(char)fromObj, toObj==null?0L:(long)toObj));
			}
		}else if(fromtype==Double.TYPE) {
			if(!this.toInfo.isPrimitive) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(double)0:(double)fromObj, toObj));
			}else if(totype==Integer.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(double)0:(double)fromObj, toObj==null?0:(int)toObj));
			}else if(totype==Float.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(double)0:(double)fromObj, toObj==null?(float)0:(float)toObj));
			}else if(totype==Boolean.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(double)0:(double)fromObj, toObj==null?false:(boolean)toObj));
			}else if(totype==Character.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(double)0:(double)fromObj, toObj==null?(char)0:(char)toObj));
			}else if(totype==Double.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(double)0:(double)fromObj, toObj==null?(double)0:(double)toObj));
			}else if(totype==Short.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(double)0:(double)fromObj, toObj==null?(short)0:(short)toObj));
			}else if(totype==Byte.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(double)0:(double)fromObj, toObj==null?(byte)0:(byte)toObj));
			}else if(totype==Long.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(double)0:(double)fromObj, toObj==null?0L:(long)toObj));
			}
		}else if(fromtype==Short.TYPE) {
			if(!this.toInfo.isPrimitive) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(short)0:(short)fromObj, toObj));
			}else if(totype==Integer.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(short)0:(short)fromObj, toObj==null?0:(int)toObj));
			}else if(totype==Float.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(short)0:(short)fromObj, toObj==null?(float)0:(float)toObj));
			}else if(totype==Boolean.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(short)0:(short)fromObj, toObj==null?false:(boolean)toObj));
			}else if(totype==Character.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(short)0:(short)fromObj, toObj==null?(char)0:(char)toObj));
			}else if(totype==Double.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(short)0:(short)fromObj, toObj==null?(double)0:(double)toObj));
			}else if(totype==Short.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(short)0:(short)fromObj, toObj==null?(short)0:(short)toObj));
			}else if(totype==Byte.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(short)0:(short)fromObj, toObj==null?(byte)0:(byte)toObj));
			}else if(totype==Long.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(short)0:(short)fromObj, toObj==null?0L:(long)toObj));
			}
		}else if(fromtype==Byte.TYPE) {
			if(!this.toInfo.isPrimitive) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(byte)0:(byte)fromObj, toObj));
			}else if(totype==Integer.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(byte)0:(byte)fromObj, toObj==null?0:(int)toObj));
			}else if(totype==Float.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(byte)0:(byte)fromObj, toObj==null?(float)0:(float)toObj));
			}else if(totype==Boolean.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(byte)0:(byte)fromObj, toObj==null?false:(boolean)toObj));
			}else if(totype==Character.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(byte)0:(byte)fromObj, toObj==null?(char)0:(char)toObj));
			}else if(totype==Double.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(byte)0:(byte)fromObj, toObj==null?(double)0:(double)toObj));
			}else if(totype==Short.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(byte)0:(byte)fromObj, toObj==null?(short)0:(short)toObj));
			}else if(totype==Byte.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(byte)0:(byte)fromObj, toObj==null?(byte)0:(byte)toObj));
			}else if(totype==Long.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?(byte)0:(byte)fromObj, toObj==null?0L:(long)toObj));
			}
		}else if(fromtype==Long.TYPE) {
			if(!this.toInfo.isPrimitive) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?0L:(long)fromObj, toObj));
			}else if(totype==Integer.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?0L:(long)fromObj, toObj==null?0:(int)toObj));
			}else if(totype==Float.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?0L:(long)fromObj, toObj==null?(float)0:(float)toObj));
			}else if(totype==Boolean.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?0L:(long)fromObj, toObj==null?false:(boolean)toObj));
			}else if(totype==Character.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?0L:(long)fromObj, toObj==null?(char)0:(char)toObj));
			}else if(totype==Double.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?0L:(long)fromObj, toObj==null?(double)0:(double)toObj));
			}else if(totype==Short.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?0L:(long)fromObj, toObj==null?(short)0:(short)toObj));
			}else if(totype==Byte.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?0L:(long)fromObj, toObj==null?(byte)0:(byte)toObj));
			}else if(totype==Long.TYPE) {
		    		bo=(context,fromObj,toObj)->(this.convert(context, fromObj==null?0L:(long)fromObj, toObj==null?0L:(long)toObj));
			}
		}
		
	}
	private BeancpConvertProviderProxy(BeancpConvertProvider parent, BeancpFeature flag, BeancpConverterInfo info,
			BeancpInfo fromInfo, BeancpInfo toInfo) {
		super(parent, flag, info, fromInfo, toInfo);
	}
	@Override
	public Object convertByObject(BeancpContext context,Object fromObj,Object toObj) {
		return bo.convertByObject(context, fromObj, toObj);
    }
	@Override
	public <T, R> R convert(BeancpContext context, T fromObj, R toObj) {
		if(this.getDistance()>=0) {if(fromObj==null) {return toObj;}return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public int getDistance() {
		return provider.getDistance();
	}
	@Override
	public <R> R convert(BeancpContext context, int fromObj, R toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public long convert(BeancpContext context, int fromObj, long toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public <T> int convert(BeancpContext context, T fromObj, int toObj) {
		if(this.getDistance()>=0) {
			if(fromObj==null) {return toObj;}return provider.convert(context, fromObj, toObj);
		}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public <T> float convert(BeancpContext context, T fromObj, float toObj) {
		if(this.getDistance()>=0) {if(fromObj==null) {return toObj;}return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public <T> boolean convert(BeancpContext context, T fromObj, boolean toObj) {
		if(this.getDistance()>=0) {if(fromObj==null) {return toObj;}return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public <T> char convert(BeancpContext context, T fromObj, char toObj) {
		if(this.getDistance()>=0) {if(fromObj==null) {return toObj;}return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public <T> double convert(BeancpContext context, T fromObj, double toObj) {
		if(this.getDistance()>=0) {if(fromObj==null) {return toObj;}return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public <T> short convert(BeancpContext context, T fromObj, short toObj) {
		if(this.getDistance()>=0) {if(fromObj==null) {return toObj;}return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public <T> byte convert(BeancpContext context, T fromObj, byte toObj) {
		if(this.getDistance()>=0) {if(fromObj==null) {return toObj;}return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public <T> long convert(BeancpContext context, T fromObj, long toObj) {
		if(this.getDistance()>=0) {if(fromObj==null) {return toObj;}return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public int convert(BeancpContext context, int fromObj, int toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public float convert(BeancpContext context, int fromObj, float toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public boolean convert(BeancpContext context, int fromObj, boolean toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public char convert(BeancpContext context, int fromObj, char toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public double convert(BeancpContext context, int fromObj, double toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public short convert(BeancpContext context, int fromObj, short toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public byte convert(BeancpContext context, int fromObj, byte toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public <R> R convert(BeancpContext context, float fromObj, R toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public int convert(BeancpContext context, float fromObj, int toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public float convert(BeancpContext context, float fromObj, float toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public boolean convert(BeancpContext context, float fromObj, boolean toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public char convert(BeancpContext context, float fromObj, char toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public double convert(BeancpContext context, float fromObj, double toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public short convert(BeancpContext context, float fromObj, short toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public byte convert(BeancpContext context, float fromObj, byte toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public long convert(BeancpContext context, float fromObj, long toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public <R> R convert(BeancpContext context, boolean fromObj, R toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public int convert(BeancpContext context, boolean fromObj, int toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public float convert(BeancpContext context, boolean fromObj, float toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public boolean convert(BeancpContext context, boolean fromObj, boolean toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public char convert(BeancpContext context, boolean fromObj, char toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public double convert(BeancpContext context, boolean fromObj, double toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public short convert(BeancpContext context, boolean fromObj, short toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public byte convert(BeancpContext context, boolean fromObj, byte toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public long convert(BeancpContext context, boolean fromObj, long toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public <R> R convert(BeancpContext context, char fromObj, R toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public int convert(BeancpContext context, char fromObj, int toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public float convert(BeancpContext context, char fromObj, float toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public boolean convert(BeancpContext context, char fromObj, boolean toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public char convert(BeancpContext context, char fromObj, char toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public double convert(BeancpContext context, char fromObj, double toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public short convert(BeancpContext context, char fromObj, short toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public byte convert(BeancpContext context, char fromObj, byte toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public long convert(BeancpContext context, char fromObj, long toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public <R> R convert(BeancpContext context, double fromObj, R toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public int convert(BeancpContext context, double fromObj, int toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public float convert(BeancpContext context, double fromObj, float toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public boolean convert(BeancpContext context, double fromObj, boolean toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public char convert(BeancpContext context, double fromObj, char toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public double convert(BeancpContext context, double fromObj, double toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public short convert(BeancpContext context, double fromObj, short toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public byte convert(BeancpContext context, double fromObj, byte toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public long convert(BeancpContext context, double fromObj, long toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public <R> R convert(BeancpContext context, short fromObj, R toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public int convert(BeancpContext context, short fromObj, int toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public float convert(BeancpContext context, short fromObj, float toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public boolean convert(BeancpContext context, short fromObj, boolean toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public char convert(BeancpContext context, short fromObj, char toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public double convert(BeancpContext context, short fromObj, double toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public short convert(BeancpContext context, short fromObj, short toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public byte convert(BeancpContext context, short fromObj, byte toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public long convert(BeancpContext context, short fromObj, long toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public <R> R convert(BeancpContext context, byte fromObj, R toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public int convert(BeancpContext context, byte fromObj, int toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public float convert(BeancpContext context, byte fromObj, float toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public boolean convert(BeancpContext context, byte fromObj, boolean toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public char convert(BeancpContext context, byte fromObj, char toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public double convert(BeancpContext context, byte fromObj, double toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public short convert(BeancpContext context, byte fromObj, short toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public byte convert(BeancpContext context, byte fromObj, byte toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public long convert(BeancpContext context, byte fromObj, long toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public <R> R convert(BeancpContext context, long fromObj, R toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public int convert(BeancpContext context, long fromObj, int toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public float convert(BeancpContext context, long fromObj, float toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public boolean convert(BeancpContext context, long fromObj, boolean toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public char convert(BeancpContext context, long fromObj, char toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public double convert(BeancpContext context, long fromObj, double toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public short convert(BeancpContext context, long fromObj, short toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public byte convert(BeancpContext context, long fromObj, byte toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public long convert(BeancpContext context, long fromObj, long toObj) {
		if(this.getDistance()>=0) {return provider.convert(context, fromObj, toObj);}
		throw new BeancpException(EType.IGNORE,"unexpected conversion");
	}
	@Override
	public BeancpConvertProvider of(Object fromObj, Object toObj) {
		return this.provider.of(fromObj,toObj);
	}
	@Override
	public BeancpConvertProvider of(Object fromObj) {
		return this.provider.of(fromObj);
	}
	@Override
	protected BeancpInvocationImp getInvocation() {
		return this.provider.getInvocation();
	}
	

}
