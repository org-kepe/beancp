package org.kepe.beancp.ct.invocation;

import java.lang.reflect.Type;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.BeancpConvertProvider;
import org.kepe.beancp.exception.BeancpException;
import org.kepe.beancp.info.BeancpInfo;//

public class BeancpInvocationImp implements BeancpInvocationOI,BeancpInvocationOF,BeancpInvocationOO,BeancpInvocationII,BeancpInvocationOZ,BeancpInvocationOC,BeancpInvocationOD,BeancpInvocationOS,BeancpInvocationOB,BeancpInvocationOJ,BeancpInvocationIO,BeancpInvocationIF,BeancpInvocationIZ,BeancpInvocationIC,BeancpInvocationID,BeancpInvocationIS,BeancpInvocationIB,BeancpInvocationIJ,BeancpInvocationFO,BeancpInvocationFI,BeancpInvocationFF,BeancpInvocationFZ,BeancpInvocationFC,BeancpInvocationFD,BeancpInvocationFS,BeancpInvocationFB,BeancpInvocationFJ,BeancpInvocationZO,BeancpInvocationZI,BeancpInvocationZF,BeancpInvocationZZ,BeancpInvocationZC,BeancpInvocationZD,BeancpInvocationZS,BeancpInvocationZB,BeancpInvocationZJ,BeancpInvocationCO,BeancpInvocationCI,BeancpInvocationCF,BeancpInvocationCZ,BeancpInvocationCC,BeancpInvocationCD,BeancpInvocationCS,BeancpInvocationCB,BeancpInvocationCJ,BeancpInvocationDO,BeancpInvocationDI,BeancpInvocationDF,BeancpInvocationDZ,BeancpInvocationDC,BeancpInvocationDD,BeancpInvocationDS,BeancpInvocationDB,BeancpInvocationDJ,BeancpInvocationSO,BeancpInvocationSI,BeancpInvocationSF,BeancpInvocationSZ,BeancpInvocationSC,BeancpInvocationSD,BeancpInvocationSS,BeancpInvocationSB,BeancpInvocationSJ,BeancpInvocationBO,BeancpInvocationBI,BeancpInvocationBF,BeancpInvocationBZ,BeancpInvocationBC,BeancpInvocationBD,BeancpInvocationBS,BeancpInvocationBB,BeancpInvocationBJ,BeancpInvocationJO,BeancpInvocationJI,BeancpInvocationJF,BeancpInvocationJZ,BeancpInvocationJC,BeancpInvocationJD,BeancpInvocationJS,BeancpInvocationJB,BeancpInvocationJJ{
	private BeancpConvertProvider provider;
	private BeancpConvertProvider parent;
	private BeancpFeature feature;
	private BeancpInfo fromInfo;
	private BeancpInfo toInfo;
	public BeancpInvocationImp(BeancpConvertProvider parent,BeancpConvertProvider provider,BeancpFeature feature,BeancpInfo fromInfo,BeancpInfo toInfo) {
		this.provider=provider;
		this.parent=parent;
		this.feature=feature;
		this.fromInfo=fromInfo;
		this.toInfo=toInfo;
	}
	
	@Override
	public Object proceed(BeancpContext context, Object fromObj, Object toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public int proceed(BeancpContext context, Object fromObj, int toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public float proceed(BeancpContext context, Object fromObj, float toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public boolean proceed(BeancpContext context, Object fromObj, boolean toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public char proceed(BeancpContext context, Object fromObj, char toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public double proceed(BeancpContext context, Object fromObj, double toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public short proceed(BeancpContext context, Object fromObj, short toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public byte proceed(BeancpContext context, Object fromObj, byte toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public long proceed(BeancpContext context, Object fromObj, long toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public Object proceed(BeancpContext context, int fromObj, Object toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public int proceed(BeancpContext context, int fromObj, int toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public float proceed(BeancpContext context, int fromObj, float toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public boolean proceed(BeancpContext context, int fromObj, boolean toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public char proceed(BeancpContext context, int fromObj, char toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public double proceed(BeancpContext context, int fromObj, double toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public short proceed(BeancpContext context, int fromObj, short toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public byte proceed(BeancpContext context, int fromObj, byte toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public long proceed(BeancpContext context, int fromObj, long toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public Object proceed(BeancpContext context, float fromObj, Object toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public int proceed(BeancpContext context, float fromObj, int toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public float proceed(BeancpContext context, float fromObj, float toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public boolean proceed(BeancpContext context, float fromObj, boolean toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public char proceed(BeancpContext context, float fromObj, char toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public double proceed(BeancpContext context, float fromObj, double toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public short proceed(BeancpContext context, float fromObj, short toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public byte proceed(BeancpContext context, float fromObj, byte toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public long proceed(BeancpContext context, float fromObj, long toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public Object proceed(BeancpContext context, boolean fromObj, Object toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public int proceed(BeancpContext context, boolean fromObj, int toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public float proceed(BeancpContext context, boolean fromObj, float toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public boolean proceed(BeancpContext context, boolean fromObj, boolean toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public char proceed(BeancpContext context, boolean fromObj, char toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public double proceed(BeancpContext context, boolean fromObj, double toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public short proceed(BeancpContext context, boolean fromObj, short toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public byte proceed(BeancpContext context, boolean fromObj, byte toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public long proceed(BeancpContext context, boolean fromObj, long toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public Object proceed(BeancpContext context, char fromObj, Object toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public int proceed(BeancpContext context, char fromObj, int toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public float proceed(BeancpContext context, char fromObj, float toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public boolean proceed(BeancpContext context, char fromObj, boolean toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public char proceed(BeancpContext context, char fromObj, char toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public double proceed(BeancpContext context, char fromObj, double toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public short proceed(BeancpContext context, char fromObj, short toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public byte proceed(BeancpContext context, char fromObj, byte toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public long proceed(BeancpContext context, char fromObj, long toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public Object proceed(BeancpContext context, double fromObj, Object toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public int proceed(BeancpContext context, double fromObj, int toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public float proceed(BeancpContext context, double fromObj, float toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public boolean proceed(BeancpContext context, double fromObj, boolean toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public char proceed(BeancpContext context, double fromObj, char toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public double proceed(BeancpContext context, double fromObj, double toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public short proceed(BeancpContext context, double fromObj, short toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public byte proceed(BeancpContext context, double fromObj, byte toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public long proceed(BeancpContext context, double fromObj, long toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public Object proceed(BeancpContext context, short fromObj, Object toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public int proceed(BeancpContext context, short fromObj, int toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public float proceed(BeancpContext context, short fromObj, float toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public boolean proceed(BeancpContext context, short fromObj, boolean toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public char proceed(BeancpContext context, short fromObj, char toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public double proceed(BeancpContext context, short fromObj, double toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public short proceed(BeancpContext context, short fromObj, short toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public byte proceed(BeancpContext context, short fromObj, byte toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public long proceed(BeancpContext context, short fromObj, long toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public Object proceed(BeancpContext context, byte fromObj, Object toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public int proceed(BeancpContext context, byte fromObj, int toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public float proceed(BeancpContext context, byte fromObj, float toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public boolean proceed(BeancpContext context, byte fromObj, boolean toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public char proceed(BeancpContext context, byte fromObj, char toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public double proceed(BeancpContext context, byte fromObj, double toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public short proceed(BeancpContext context, byte fromObj, short toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public byte proceed(BeancpContext context, byte fromObj, byte toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public long proceed(BeancpContext context, byte fromObj, long toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public Object proceed(BeancpContext context, long fromObj, Object toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public int proceed(BeancpContext context, long fromObj, int toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public float proceed(BeancpContext context, long fromObj, float toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public boolean proceed(BeancpContext context, long fromObj, boolean toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public char proceed(BeancpContext context, long fromObj, char toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public double proceed(BeancpContext context, long fromObj, double toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public short proceed(BeancpContext context, long fromObj, short toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public byte proceed(BeancpContext context, long fromObj, byte toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public long proceed(BeancpContext context, long fromObj, long toObj) {
		if(parent!=null) {
			return parent.convert(context, fromObj, toObj);
		}
		throw new BeancpException("can't proceed this provider");
	}
	@Override
	public Class getFromClass() {
		return this.fromInfo.getBClass();
	}
	public BeancpInfo getFromInfo() {
		return fromInfo;
	}
	public BeancpInfo getToInfo() {
		return toInfo;
	}
	@Override
	public BeancpFeature getFeature() {
		return this.feature;
	}
	
	@Override
	public Type getFromType() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Type getToType() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Class getToClass() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	 
}

