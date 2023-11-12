package org.kepe.beancp.ct.reg.converter;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.convert.BeancpConvertMapper;
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

public class BeancpDirectCustomConverter implements BeancpCustomConverter {
	public final static BeancpDirectCustomConverter INSTANCE=new BeancpDirectCustomConverter();
	
	@Override
	public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
		return 0;
	};
	@Override
	public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj, Object toObj) {
		if(toObj==null) {
			if(invocation.getFeature().is(BeancpFeature.ALLWAYS_NEW)) {
				if(fromObj instanceof Cloneable) {
					return BeancpConvertMapper.of(((BeancpInvocationImp)invocation).getFromInfo(), invocation.getFeature()).clone(context,fromObj);
				}
			}else {
				return fromObj;
			}
		}else {
			return invocation.proceed(context, fromObj, toObj);
		}
		return fromObj;
	}
	
	
	@Override
	public Object convert(BeancpInvocationIO invocation, BeancpContext context, int fromObj, Object toObj
			) {
		return Integer.valueOf(fromObj);
	}
	@Override
	public Object convert(BeancpInvocationBO invocation, BeancpContext context, byte fromObj, Object toObj
			) {
		return Byte.valueOf(fromObj);
	}
	@Override
	public Object convert(BeancpInvocationJO invocation, BeancpContext context, long fromObj, Object toObj
			) {
		return Long.valueOf(fromObj);
	}
	@Override
	public Object convert(BeancpInvocationFO invocation, BeancpContext context, float fromObj, Object toObj
			) {
		return Float.valueOf(fromObj);
	}
	@Override
	public Object convert(BeancpInvocationDO invocation, BeancpContext context, double fromObj, Object toObj
			) {
		return Double.valueOf(fromObj);
	}
	@Override
	public Object convert(BeancpInvocationSO invocation, BeancpContext context, short fromObj, Object toObj
			) {
		return Short.valueOf(fromObj);
	}
	@Override
	public Object convert(BeancpInvocationZO invocation, BeancpContext context, boolean fromObj, Object toObj
			) {
		return Boolean.valueOf(fromObj);
	}
	@Override
	public Object convert(BeancpInvocationCO invocation, BeancpContext context, char fromObj, Object toObj
			) {
		return Character.valueOf(fromObj);
	}
	
	@Override
	public boolean convert(BeancpInvocationOZ invocation, BeancpContext context, Object fromObj, 
			boolean toObj) {
		return ((Boolean)fromObj).booleanValue();
	}
	
	@Override
	public int convert(BeancpInvocationOI invocation, BeancpContext context, Object fromObj, 
			int toObj) {
		return ((Integer)fromObj).intValue();
	}
	@Override
	public long convert(BeancpInvocationOJ invocation, BeancpContext context, Object fromObj, 
			long toObj) {
		return ((Long)fromObj).longValue();
	}
	@Override
	public float convert(BeancpInvocationOF invocation, BeancpContext context, Object fromObj, 
			float toObj) {
		return ((Float)fromObj).floatValue();
	}
	@Override
	public double convert(BeancpInvocationOD invocation, BeancpContext context, Object fromObj, 
			double toObj) {
		return ((Double)fromObj).doubleValue();
	}
	@Override
	public short convert(BeancpInvocationOS invocation, BeancpContext context, Object fromObj, 
			short toObj) {
		return ((Short)fromObj).shortValue();
	}
	@Override
	public byte convert(BeancpInvocationOB invocation, BeancpContext context, Object fromObj, 
			byte toObj) {
		return ((Byte)fromObj).byteValue();
	}
	@Override
	public char convert(BeancpInvocationOC invocation, BeancpContext context, Object fromObj, 
			char toObj) {
		return ((Character)fromObj).charValue();
	}
	
}
