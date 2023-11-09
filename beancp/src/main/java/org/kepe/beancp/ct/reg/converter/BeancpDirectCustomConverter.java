package org.kepe.beancp.ct.reg.converter;

import java.lang.reflect.Type;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.itf.BeancpConverter;
import org.kepe.beancp.ct.itf.BeancpCustomConverter;

public class BeancpDirectCustomConverter implements BeancpCustomConverter {
	public final static BeancpDirectCustomConverter INSTANCE=new BeancpDirectCustomConverter();
	
	@Override
	public int distance(BeancpFeature flag, Class fromClass, Class toClass) {
		return 0;
	};
	@Override
	public Object convert(BeancpFeature feature, BeancpContext context, Object fromObj, Type fromType,
			Class fromClass, Object toObj, Type toType, Class toClass) {
		return fromObj;
	}
	@Override
	public Object convert(BeancpFeature feature, BeancpContext context, int fromObj, Object toObj,
			Type toType, Class toClass) {
		return Integer.valueOf(fromObj);
	}
	@Override
	public Object convert(BeancpFeature feature, BeancpContext context, byte fromObj, Object toObj,
			Type toType, Class toClass) {
		return Byte.valueOf(fromObj);
	}
	@Override
	public Object convert(BeancpFeature feature, BeancpContext context, long fromObj, Object toObj,
			Type toType, Class toClass) {
		return Long.valueOf(fromObj);
	}
	@Override
	public Object convert(BeancpFeature feature, BeancpContext context, float fromObj, Object toObj,
			Type toType, Class toClass) {
		return Float.valueOf(fromObj);
	}
	@Override
	public Object convert(BeancpFeature feature, BeancpContext context, double fromObj, Object toObj,
			Type toType, Class toClass) {
		return Double.valueOf(fromObj);
	}
	@Override
	public Object convert(BeancpFeature feature, BeancpContext context, short fromObj, Object toObj,
			Type toType, Class toClass) {
		return Short.valueOf(fromObj);
	}
	@Override
	public Object convert(BeancpFeature feature, BeancpContext context, boolean fromObj, Object toObj,
			Type toType, Class toClass) {
		return Boolean.valueOf(fromObj);
	}
	@Override
	public Object convert(BeancpFeature feature, BeancpContext context, char fromObj, Object toObj,
			Type toType, Class toClass) {
		return Character.valueOf(fromObj);
	}
	
	@Override
	public boolean convert(BeancpFeature feature, BeancpContext context, Object fromObj, Type fromType, Class fromClass,
			boolean toObj) {
		return ((Boolean)fromObj).booleanValue();
	}
	
	@Override
	public int convert(BeancpFeature feature, BeancpContext context, Object fromObj, Type fromType, Class fromClass,
			int toObj) {
		return ((Integer)fromObj).intValue();
	}
	@Override
	public long convert(BeancpFeature feature, BeancpContext context, Object fromObj, Type fromType, Class fromClass,
			long toObj) {
		return ((Long)fromObj).longValue();
	}
	@Override
	public float convert(BeancpFeature feature, BeancpContext context, Object fromObj, Type fromType, Class fromClass,
			float toObj) {
		return ((Float)fromObj).floatValue();
	}
	@Override
	public double convert(BeancpFeature feature, BeancpContext context, Object fromObj, Type fromType, Class fromClass,
			double toObj) {
		return ((Double)fromObj).doubleValue();
	}
	@Override
	public short convert(BeancpFeature feature, BeancpContext context, Object fromObj, Type fromType, Class fromClass,
			short toObj) {
		return ((Short)fromObj).shortValue();
	}
	@Override
	public byte convert(BeancpFeature feature, BeancpContext context, Object fromObj, Type fromType, Class fromClass,
			byte toObj) {
		return ((Byte)fromObj).byteValue();
	}
	@Override
	public char convert(BeancpFeature feature, BeancpContext context, Object fromObj, Type fromType, Class fromClass,
			char toObj) {
		return ((Character)fromObj).charValue();
	}
}
