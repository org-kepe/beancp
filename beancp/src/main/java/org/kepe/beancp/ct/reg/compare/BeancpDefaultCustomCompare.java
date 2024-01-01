package org.kepe.beancp.ct.reg.compare;

import org.kepe.beancp.config.BeancpCompare;
import org.kepe.beancp.config.BeancpCompareFlag;
import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.convert.BeancpConvertMapper;
import org.kepe.beancp.ct.invocation.BeancpInvocation;
import org.kepe.beancp.ct.invocation.BeancpInvocationBO;
import org.kepe.beancp.ct.invocation.BeancpInvocationCO;
import org.kepe.beancp.ct.invocation.BeancpInvocationDO;
import org.kepe.beancp.ct.invocation.BeancpInvocationFO;
import org.kepe.beancp.ct.invocation.BeancpInvocationIO;
import org.kepe.beancp.ct.invocation.BeancpInvocationImp;
import org.kepe.beancp.ct.invocation.BeancpInvocationJO;
import org.kepe.beancp.ct.invocation.BeancpInvocationOB;
import org.kepe.beancp.ct.invocation.BeancpInvocationOC;
import org.kepe.beancp.ct.invocation.BeancpInvocationOD;
import org.kepe.beancp.ct.invocation.BeancpInvocationOF;
import org.kepe.beancp.ct.invocation.BeancpInvocationOI;
import org.kepe.beancp.ct.invocation.BeancpInvocationOJ;
import org.kepe.beancp.ct.invocation.BeancpInvocationOO;
import org.kepe.beancp.ct.invocation.BeancpInvocationOS;
import org.kepe.beancp.ct.invocation.BeancpInvocationOZ;
import org.kepe.beancp.ct.invocation.BeancpInvocationSO;
import org.kepe.beancp.ct.invocation.BeancpInvocationZO;
import org.kepe.beancp.ct.itf.BeancpCustomConverter;

public class BeancpDefaultCustomCompare implements BeancpCompare {
	public final static BeancpDefaultCustomCompare INSTANCE=new BeancpDefaultCustomCompare();
	
	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, T fromObj, R toObj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, T fromObj, int toObj) {
		return this.compare(invocation, ((Integer)fromObj).intValue(), toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, T fromObj, float toObj) {
		return this.compare(invocation, ((Float)fromObj).floatValue(), toObj);

	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, T fromObj, boolean toObj) {
		return this.compare(invocation, ((Boolean)fromObj).booleanValue(), toObj);

	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, T fromObj, char toObj) {
		return this.compare(invocation, ((Character)fromObj).charValue(), toObj);

	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, T fromObj, double toObj) {
		return this.compare(invocation, ((Double)fromObj).doubleValue(), toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, T fromObj, short toObj) {
		return this.compare(invocation, ((Short)fromObj).shortValue(), toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, T fromObj, byte toObj) {
		return this.compare(invocation, ((Byte)fromObj).byteValue(), toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, T fromObj, long toObj) {
		return this.compare(invocation, ((Long)fromObj).longValue(), toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, int fromObj, R toObj) {
		return this.compare(invocation, fromObj, ((Integer)toObj).intValue());
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, int fromObj, int toObj) {
		if(fromObj>toObj) {
			return BeancpCompareFlag.GREATER;
		}else if(fromObj==toObj) {
			return BeancpCompareFlag.EQUALS;
		}else {
			return BeancpCompareFlag.LESS;
		}
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, int fromObj, float toObj) {
		return this.compare(invocation, (float)fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, int fromObj, boolean toObj) {
		return this.compare(invocation, fromObj, toObj?1:0);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, int fromObj, char toObj) {
		return this.compare(invocation, fromObj, (int)toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, int fromObj, double toObj) {
		return this.compare(invocation, (double)fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, int fromObj, short toObj) {
		return this.compare(invocation, fromObj, (int)toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, int fromObj, byte toObj) {
		return this.compare(invocation, fromObj, (int)toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, int fromObj, long toObj) {
		return this.compare(invocation, (long)fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, float fromObj, R toObj) {
		return this.compare(invocation, fromObj, ((Float)toObj).floatValue());
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, float fromObj, int toObj) {
		return this.compare(invocation, fromObj, (float)toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, float fromObj, float toObj) {
		if(fromObj>toObj) {
			return BeancpCompareFlag.GREATER;
		}else if(fromObj==toObj) {
			return BeancpCompareFlag.EQUALS;
		}else {
			return BeancpCompareFlag.LESS;
		}
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, float fromObj, boolean toObj) {
		return this.compare(invocation, fromObj, toObj?1f:0f);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, float fromObj, char toObj) {
		return this.compare(invocation, fromObj, (float)toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, float fromObj, double toObj) {
		return this.compare(invocation, (double)fromObj, toObj);

	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, float fromObj, short toObj) {
		return this.compare(invocation, fromObj, (float)toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, float fromObj, byte toObj) {
		return this.compare(invocation, fromObj, (float)toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, float fromObj, long toObj) {
		return this.compare(invocation, fromObj, (float)toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, boolean fromObj, R toObj) {
		return this.compare(invocation, fromObj, ((Boolean)toObj).booleanValue());
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, boolean fromObj, int toObj) {
		return this.compare(invocation, fromObj?1:0, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, boolean fromObj, float toObj) {
		return this.compare(invocation, fromObj?1f:0f, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, boolean fromObj, boolean toObj) {
		if(fromObj&&!toObj) {
			return BeancpCompareFlag.GREATER;
		}else if(fromObj==toObj) {
			return BeancpCompareFlag.EQUALS;
		}else {
			return BeancpCompareFlag.LESS;
		}
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, boolean fromObj, char toObj) {
		return this.compare(invocation, fromObj?1:0, (int)toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, boolean fromObj, double toObj) {
		return this.compare(invocation, fromObj?1d:0d, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, boolean fromObj, short toObj) {
		return this.compare(invocation, fromObj?(short)1:(short)0, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, boolean fromObj, byte toObj) {
		return this.compare(invocation, fromObj?1:0, (int)toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, boolean fromObj, long toObj) {
		return this.compare(invocation, fromObj?1l:0l, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, char fromObj, R toObj) {
		return this.compare(invocation, fromObj, ((Character)toObj).charValue());
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, char fromObj, int toObj) {
		return this.compare(invocation, (int)fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, char fromObj, float toObj) {
		return this.compare(invocation, (float)fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, char fromObj, boolean toObj) {
		return this.compare(invocation, (int)fromObj, toObj?1:0);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, char fromObj, char toObj) {
		return this.compare(invocation, (int)fromObj, (int)toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, char fromObj, double toObj) {
		return this.compare(invocation, (double)fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, char fromObj, short toObj) {
		return this.compare(invocation, (int)fromObj, (int)toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, char fromObj, byte toObj) {
		return this.compare(invocation, (int)fromObj, (int)toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, char fromObj, long toObj) {
		return this.compare(invocation, (long)fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, double fromObj, R toObj) {
		return this.compare(invocation, fromObj, ((Double)toObj).doubleValue());
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, double fromObj, int toObj) {
		return this.compare(invocation, fromObj, (double)toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, double fromObj, float toObj) {
		return this.compare(invocation, fromObj, (double)toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, double fromObj, boolean toObj) {
		return this.compare(invocation, fromObj, toObj?1d:0d);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, double fromObj, char toObj) {
		return this.compare(invocation, fromObj, (double)toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, double fromObj, double toObj) {
		if(fromObj>toObj) {
			return BeancpCompareFlag.GREATER;
		}else if(fromObj==toObj) {
			return BeancpCompareFlag.EQUALS;
		}else {
			return BeancpCompareFlag.LESS;
		}
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, double fromObj, short toObj) {
		return this.compare(invocation, fromObj, (double)toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, double fromObj, byte toObj) {
		return this.compare(invocation, fromObj, (double)toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, double fromObj, long toObj) {
		return this.compare(invocation, fromObj, (double)toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, short fromObj, R toObj) {
		return this.compare(invocation, fromObj, ((Short)toObj).shortValue());
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, short fromObj, int toObj) {
		return this.compare(invocation, (int)fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, short fromObj, float toObj) {
		return this.compare(invocation, (float)fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, short fromObj, boolean toObj) {
		return this.compare(invocation, fromObj, toObj?(short)1:(short)0);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, short fromObj, char toObj) {
		return this.compare(invocation, (int)fromObj, (int)toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, short fromObj, double toObj) {
		return this.compare(invocation, (double)fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, short fromObj, short toObj) {
		if(fromObj>toObj) {
			return BeancpCompareFlag.GREATER;
		}else if(fromObj==toObj) {
			return BeancpCompareFlag.EQUALS;
		}else {
			return BeancpCompareFlag.LESS;
		}
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, short fromObj, byte toObj) {
		return this.compare(invocation, fromObj, (short)toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, short fromObj, long toObj) {
		return this.compare(invocation, (long)fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, byte fromObj, R toObj) {
		return this.compare(invocation, fromObj, ((Byte)toObj).byteValue());
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, byte fromObj, int toObj) {
		return this.compare(invocation, (int)fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, byte fromObj, float toObj) {
		return this.compare(invocation, (float)fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, byte fromObj, boolean toObj) {
		return this.compare(invocation, (int)fromObj, toObj?1:0);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, byte fromObj, char toObj) {
		return this.compare(invocation, (int)fromObj, (int)toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, byte fromObj, double toObj) {
		return this.compare(invocation, (double)fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, byte fromObj, short toObj) {
		return this.compare(invocation, (short)fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, byte fromObj, byte toObj) {
		return this.compare(invocation, (int)fromObj, (int)toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, byte fromObj, long toObj) {
		return this.compare(invocation, (long)fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, long fromObj, R toObj) {
		return this.compare(invocation, fromObj, ((Long)toObj).longValue());
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, long fromObj, int toObj) {
		return this.compare(invocation, fromObj, (long)toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, long fromObj, float toObj) {
		return this.compare(invocation, (float)fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, long fromObj, boolean toObj) {
		return this.compare(invocation, fromObj, toObj?1l:0l);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, long fromObj, char toObj) {
		return this.compare(invocation, fromObj, (long)toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, long fromObj, double toObj) {
		return this.compare(invocation, (double)fromObj, toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, long fromObj, short toObj) {
		return this.compare(invocation, fromObj, (long)toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, long fromObj, byte toObj) {
		return this.compare(invocation, fromObj, (long)toObj);
	}

	@Override
	public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, long fromObj, long toObj) {
		if(fromObj>toObj) {
			return BeancpCompareFlag.GREATER;
		}else if(fromObj==toObj) {
			return BeancpCompareFlag.EQUALS;
		}else {
			return BeancpCompareFlag.LESS;
		}
	}
	
}
