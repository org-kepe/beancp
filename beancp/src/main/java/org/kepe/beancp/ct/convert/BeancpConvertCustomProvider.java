package org.kepe.beancp.ct.convert;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.BeancpConvertProvider;
import org.kepe.beancp.ct.converter.BeancpConverterInfo;
import org.kepe.beancp.ct.invocation.BeancpInvocationBB;
import org.kepe.beancp.ct.invocation.BeancpInvocationBC;
import org.kepe.beancp.ct.invocation.BeancpInvocationBD;
import org.kepe.beancp.ct.invocation.BeancpInvocationBF;
import org.kepe.beancp.ct.invocation.BeancpInvocationBI;
import org.kepe.beancp.ct.invocation.BeancpInvocationBJ;
import org.kepe.beancp.ct.invocation.BeancpInvocationBO;
import org.kepe.beancp.ct.invocation.BeancpInvocationBS;
import org.kepe.beancp.ct.invocation.BeancpInvocationBZ;
import org.kepe.beancp.ct.invocation.BeancpInvocationCB;
import org.kepe.beancp.ct.invocation.BeancpInvocationCC;
import org.kepe.beancp.ct.invocation.BeancpInvocationCD;
import org.kepe.beancp.ct.invocation.BeancpInvocationCF;
import org.kepe.beancp.ct.invocation.BeancpInvocationCI;
import org.kepe.beancp.ct.invocation.BeancpInvocationCJ;
import org.kepe.beancp.ct.invocation.BeancpInvocationCO;
import org.kepe.beancp.ct.invocation.BeancpInvocationCS;
import org.kepe.beancp.ct.invocation.BeancpInvocationCZ;
import org.kepe.beancp.ct.invocation.BeancpInvocationDB;
import org.kepe.beancp.ct.invocation.BeancpInvocationDC;
import org.kepe.beancp.ct.invocation.BeancpInvocationDD;
import org.kepe.beancp.ct.invocation.BeancpInvocationDF;
import org.kepe.beancp.ct.invocation.BeancpInvocationDI;
import org.kepe.beancp.ct.invocation.BeancpInvocationDJ;
import org.kepe.beancp.ct.invocation.BeancpInvocationDO;
import org.kepe.beancp.ct.invocation.BeancpInvocationDS;
import org.kepe.beancp.ct.invocation.BeancpInvocationDZ;
import org.kepe.beancp.ct.invocation.BeancpInvocationFB;
import org.kepe.beancp.ct.invocation.BeancpInvocationFC;
import org.kepe.beancp.ct.invocation.BeancpInvocationFD;
import org.kepe.beancp.ct.invocation.BeancpInvocationFF;
import org.kepe.beancp.ct.invocation.BeancpInvocationFI;
import org.kepe.beancp.ct.invocation.BeancpInvocationFJ;
import org.kepe.beancp.ct.invocation.BeancpInvocationFO;
import org.kepe.beancp.ct.invocation.BeancpInvocationFS;
import org.kepe.beancp.ct.invocation.BeancpInvocationFZ;
import org.kepe.beancp.ct.invocation.BeancpInvocationIB;
import org.kepe.beancp.ct.invocation.BeancpInvocationIC;
import org.kepe.beancp.ct.invocation.BeancpInvocationID;
import org.kepe.beancp.ct.invocation.BeancpInvocationIF;
import org.kepe.beancp.ct.invocation.BeancpInvocationII;
import org.kepe.beancp.ct.invocation.BeancpInvocationIJ;
import org.kepe.beancp.ct.invocation.BeancpInvocationIO;
import org.kepe.beancp.ct.invocation.BeancpInvocationIS;
import org.kepe.beancp.ct.invocation.BeancpInvocationIZ;
import org.kepe.beancp.ct.invocation.BeancpInvocationJB;
import org.kepe.beancp.ct.invocation.BeancpInvocationJC;
import org.kepe.beancp.ct.invocation.BeancpInvocationJD;
import org.kepe.beancp.ct.invocation.BeancpInvocationJF;
import org.kepe.beancp.ct.invocation.BeancpInvocationJI;
import org.kepe.beancp.ct.invocation.BeancpInvocationJJ;
import org.kepe.beancp.ct.invocation.BeancpInvocationJO;
import org.kepe.beancp.ct.invocation.BeancpInvocationJS;
import org.kepe.beancp.ct.invocation.BeancpInvocationJZ;
import org.kepe.beancp.ct.invocation.BeancpInvocationOB;
import org.kepe.beancp.ct.invocation.BeancpInvocationOC;
import org.kepe.beancp.ct.invocation.BeancpInvocationOD;
import org.kepe.beancp.ct.invocation.BeancpInvocationOF;
import org.kepe.beancp.ct.invocation.BeancpInvocationOI;
import org.kepe.beancp.ct.invocation.BeancpInvocationOJ;
import org.kepe.beancp.ct.invocation.BeancpInvocationOS;
import org.kepe.beancp.ct.invocation.BeancpInvocationOZ;
import org.kepe.beancp.ct.invocation.BeancpInvocationSB;
import org.kepe.beancp.ct.invocation.BeancpInvocationSC;
import org.kepe.beancp.ct.invocation.BeancpInvocationSD;
import org.kepe.beancp.ct.invocation.BeancpInvocationSF;
import org.kepe.beancp.ct.invocation.BeancpInvocationSI;
import org.kepe.beancp.ct.invocation.BeancpInvocationSJ;
import org.kepe.beancp.ct.invocation.BeancpInvocationSO;
import org.kepe.beancp.ct.invocation.BeancpInvocationSS;
import org.kepe.beancp.ct.invocation.BeancpInvocationSZ;
import org.kepe.beancp.ct.invocation.BeancpInvocationZB;
import org.kepe.beancp.ct.invocation.BeancpInvocationZC;
import org.kepe.beancp.ct.invocation.BeancpInvocationZD;
import org.kepe.beancp.ct.invocation.BeancpInvocationZF;
import org.kepe.beancp.ct.invocation.BeancpInvocationZI;
import org.kepe.beancp.ct.invocation.BeancpInvocationZJ;
import org.kepe.beancp.ct.invocation.BeancpInvocationZO;
import org.kepe.beancp.ct.invocation.BeancpInvocationZS;
import org.kepe.beancp.ct.invocation.BeancpInvocationZZ;
import org.kepe.beancp.ct.itf.BeancpCustomConverter;
import org.kepe.beancp.info.BeancpInfo;

/**
 * Hello world!
 *
 */
public class BeancpConvertCustomProvider extends BeancpConvertProvider
{
    public BeancpConvertCustomProvider(BeancpConvertProvider parent, BeancpFeature flag, BeancpConverterInfo info,
			BeancpInfo fromInfo, BeancpInfo toInfo) {
		super(parent, flag, info, fromInfo, toInfo);
	}

	@Override
    public <T,R> R convert(BeancpContext context,T fromObj, R toObj) {
    	try {
    		return (R) ((BeancpCustomConverter)converter).convert(this.getInvocation(), context,fromObj, toObj);
    	}catch(Exception e) {
    		BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
    	}
    	return null;
        
    }
    
//    @Override
//    public <T> int convert(BeancpContext context, T fromObj, int toObj) {
//    	;
//        return ((BeancpCustomConverter)converter).convert((BeancpInvocationOI)this.getInvocation(), context,fromObj, toObj);
//    }
//    @Override
//    public int convert(BeancpContext context, int fromObj, int toObj) {
//    	;
//        return ((BeancpCustomConverter)converter).convert((BeancpInvocationII)this.getInvocation(), context,fromObj, toObj);
//    }
//    @Override
//    public <R> R convert(BeancpContext context, int fromObj, R toObj) {
//    	;
//        return (R) ((BeancpCustomConverter)converter).convert((BeancpInvocationIO)(BeancpInvocationII)this.getInvocation(), context,fromObj, toObj);
//    }
    
	
	@Override
	public <T> int convert(BeancpContext context, T fromObj, int toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationOI)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return 0;
	}
	@Override
	public <T> float convert(BeancpContext context, T fromObj, float toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationOF)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (float)0;
	}
	@Override
	public <T> boolean convert(BeancpContext context, T fromObj, boolean toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationOZ)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return false;
	}
	@Override
	public <T> char convert(BeancpContext context, T fromObj, char toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationOC)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (char)0;
	}
	@Override
	public <T> double convert(BeancpContext context, T fromObj, double toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationOD)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (double)0;
	}
	@Override
	public <T> short convert(BeancpContext context, T fromObj, short toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationOS)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (short)0;
	}
	@Override
	public <T> byte convert(BeancpContext context, T fromObj, byte toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationOB)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (byte)0;
	}
	@Override
	public <T> long convert(BeancpContext context, T fromObj, long toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationOJ)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return 0L;
	}
	@Override
	public <R> R convert(BeancpContext context, int fromObj, R toObj) {
		;
		try {
	    	return (R) ((BeancpCustomConverter)converter).convert((BeancpInvocationIO)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return null;
	}
	@Override
	public int convert(BeancpContext context, int fromObj, int toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationII)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return 0;
	}
	@Override
	public float convert(BeancpContext context, int fromObj, float toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationIF)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (float)0;
	}
	@Override
	public boolean convert(BeancpContext context, int fromObj, boolean toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationIZ)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return false;
	}
	@Override
	public char convert(BeancpContext context, int fromObj, char toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationIC)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (char)0;
	}
	@Override
	public double convert(BeancpContext context, int fromObj, double toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationID)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (double)0;
	}
	@Override
	public short convert(BeancpContext context, int fromObj, short toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationIS)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (short)0;
	}
	@Override
	public byte convert(BeancpContext context, int fromObj, byte toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationIB)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (byte)0;
	}
	@Override
	public long convert(BeancpContext context, int fromObj, long toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationIJ)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return 0L;
	}
	@Override
	public <R> R convert(BeancpContext context, float fromObj, R toObj) {
		;
		try {
	    	return (R) ((BeancpCustomConverter)converter).convert((BeancpInvocationFO)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return null;
	}
	@Override
	public int convert(BeancpContext context, float fromObj, int toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationFI)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return 0;
	}
	@Override
	public float convert(BeancpContext context, float fromObj, float toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationFF)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (float)0;
	}
	@Override
	public boolean convert(BeancpContext context, float fromObj, boolean toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationFZ)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return false;
	}
	@Override
	public char convert(BeancpContext context, float fromObj, char toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationFC)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (char)0;
	}
	@Override
	public double convert(BeancpContext context, float fromObj, double toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationFD)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (double)0;
	}
	@Override
	public short convert(BeancpContext context, float fromObj, short toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationFS)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (short)0;
	}
	@Override
	public byte convert(BeancpContext context, float fromObj, byte toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationFB)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (byte)0;
	}
	@Override
	public long convert(BeancpContext context, float fromObj, long toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationFJ)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return 0L;
	}
	@Override
	public <R> R convert(BeancpContext context, boolean fromObj, R toObj) {
		;
		try {
	    	return (R) ((BeancpCustomConverter)converter).convert((BeancpInvocationZO)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return null;
	}
	@Override
	public int convert(BeancpContext context, boolean fromObj, int toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationZI)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return 0;
	}
	@Override
	public float convert(BeancpContext context, boolean fromObj, float toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationZF)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (float)0;
	}
	@Override
	public boolean convert(BeancpContext context, boolean fromObj, boolean toObj) {
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationZZ)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return false;
	}
	@Override
	public char convert(BeancpContext context, boolean fromObj, char toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationZC)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (char)0;
	}
	@Override
	public double convert(BeancpContext context, boolean fromObj, double toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationZD)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (double)0;
	}
	@Override
	public short convert(BeancpContext context, boolean fromObj, short toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationZS)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (short)0;
	}
	@Override
	public byte convert(BeancpContext context, boolean fromObj, byte toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationZB)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (byte)0;
	}
	@Override
	public long convert(BeancpContext context, boolean fromObj, long toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationZJ)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return 0L;
	}
	@Override
	public <R> R convert(BeancpContext context, char fromObj, R toObj) {
		;
		try {
	    	return (R) ((BeancpCustomConverter)converter).convert((BeancpInvocationCO)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return null;
	}
	@Override
	public int convert(BeancpContext context, char fromObj, int toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationCI)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return 0;
	}
	@Override
	public float convert(BeancpContext context, char fromObj, float toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationCF)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (float)0;
	}
	@Override
	public boolean convert(BeancpContext context, char fromObj, boolean toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationCZ)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return false;
	}
	@Override
	public char convert(BeancpContext context, char fromObj, char toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationCC)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (char)0;
	}
	@Override
	public double convert(BeancpContext context, char fromObj, double toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationCD)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (double)0;
	}
	@Override
	public short convert(BeancpContext context, char fromObj, short toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationCS)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (short)0;
	}
	@Override
	public byte convert(BeancpContext context, char fromObj, byte toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationCB)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (byte)0;
	}
	@Override
	public long convert(BeancpContext context, char fromObj, long toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationCJ)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return 0L;
	}
	@Override
	public <R> R convert(BeancpContext context, double fromObj, R toObj) {
		;
		try {
	    	return (R) ((BeancpCustomConverter)converter).convert((BeancpInvocationDO)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return null;
	}
	@Override
	public int convert(BeancpContext context, double fromObj, int toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationDI)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return 0;
	}
	@Override
	public float convert(BeancpContext context, double fromObj, float toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationDF)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (float)0;
	}
	@Override
	public boolean convert(BeancpContext context, double fromObj, boolean toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationDZ)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return false;
	}
	@Override
	public char convert(BeancpContext context, double fromObj, char toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationDC)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (char)0;
	}
	@Override
	public double convert(BeancpContext context, double fromObj, double toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationDD)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (double)0;
	}
	@Override
	public short convert(BeancpContext context, double fromObj, short toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationDS)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (short)0;
	}
	@Override
	public byte convert(BeancpContext context, double fromObj, byte toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationDB)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (byte)0;
	}
	@Override
	public long convert(BeancpContext context, double fromObj, long toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationDJ)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return 0L;
	}
	@Override
	public <R> R convert(BeancpContext context, short fromObj, R toObj) {
		;
		try {
	    	return (R) ((BeancpCustomConverter)converter).convert((BeancpInvocationSO)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return null;
	}
	@Override
	public int convert(BeancpContext context, short fromObj, int toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationSI)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return 0;
	}
	@Override
	public float convert(BeancpContext context, short fromObj, float toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationSF)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (float)0;
	}
	@Override
	public boolean convert(BeancpContext context, short fromObj, boolean toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationSZ)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return false;
	}
	@Override
	public char convert(BeancpContext context, short fromObj, char toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationSC)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (char)0;
	}
	@Override
	public double convert(BeancpContext context, short fromObj, double toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationSD)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (double)0;
	}
	@Override
	public short convert(BeancpContext context, short fromObj, short toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationSS)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (short)0;
	}
	@Override
	public byte convert(BeancpContext context, short fromObj, byte toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationSB)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (byte)0;
	}
	@Override
	public long convert(BeancpContext context, short fromObj, long toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationSJ)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return 0L;
	}
	@Override
	public <R> R convert(BeancpContext context, byte fromObj, R toObj) {
		;
		try {
	    	return (R) ((BeancpCustomConverter)converter).convert((BeancpInvocationBO)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return null;
	}
	@Override
	public int convert(BeancpContext context, byte fromObj, int toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationBI)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return 0;
	}
	@Override
	public float convert(BeancpContext context, byte fromObj, float toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationBF)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (float)0;
	}
	@Override
	public boolean convert(BeancpContext context, byte fromObj, boolean toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationBZ)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return false;
	}
	@Override
	public char convert(BeancpContext context, byte fromObj, char toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationBC)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (char)0;
	}
	@Override
	public double convert(BeancpContext context, byte fromObj, double toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationBD)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (double)0;
	}
	@Override
	public short convert(BeancpContext context, byte fromObj, short toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationBS)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (short)0;
	}
	@Override
	public byte convert(BeancpContext context, byte fromObj, byte toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationBB)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (byte)0;
	}
	@Override
	public long convert(BeancpContext context, byte fromObj, long toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationBJ)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return 0L;
	}
	@Override
	public <R> R convert(BeancpContext context, long fromObj, R toObj) {
		;
		try {
	    	return (R) ((BeancpCustomConverter)converter).convert((BeancpInvocationJO)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return null;
	}
	@Override
	public int convert(BeancpContext context, long fromObj, int toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationJI)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return 0;
	}
	@Override
	public float convert(BeancpContext context, long fromObj, float toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationJF)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (float)0;
	}
	@Override
	public boolean convert(BeancpContext context, long fromObj, boolean toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationJZ)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return false;
	}
	@Override
	public char convert(BeancpContext context, long fromObj, char toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationJC)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (char)0;
	}
	@Override
	public double convert(BeancpContext context, long fromObj, double toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationJD)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (double)0;
	}
	@Override
	public short convert(BeancpContext context, long fromObj, short toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationJS)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (short)0;
	}
	@Override
	public byte convert(BeancpContext context, long fromObj, byte toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationJB)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return (byte)0;
	}
	@Override
	public long convert(BeancpContext context, long fromObj, long toObj) {
		;
		try {
	    	return ((BeancpCustomConverter)converter).convert((BeancpInvocationJJ)this.getInvocation(), context,fromObj, toObj);
		}catch(Exception e) {
			BeancpConvertProviderTool.handleException(this, flag, context, "", 2, e);
		}
		return 0L;
	}
    
    
   
}
