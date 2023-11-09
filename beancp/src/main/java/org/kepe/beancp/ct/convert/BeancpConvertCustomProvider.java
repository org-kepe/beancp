package org.kepe.beancp.ct.convert;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.BeancpConvertProvider;
import org.kepe.beancp.ct.converter.BeancpConverterInfo;
import org.kepe.beancp.ct.itf.BeancpCustomConverter;
import org.kepe.beancp.info.BeancpInfo;

/**
 * Hello world!
 *
 */
public class BeancpConvertCustomProvider extends BeancpConvertProvider
{
    
    public BeancpConvertCustomProvider(BeancpFeature flag, BeancpConverterInfo info, BeancpInfo fromInfo,
            BeancpInfo toInfo) {
        super(flag, info, fromInfo, toInfo);
    }

    @Override
    public <T,R> R convert(BeancpContext context,T fromObj, R toObj) {
    	BeancpCustomConverter<T,R> converter=(BeancpCustomConverter<T,R>)info.getConverter();
        return (R) converter.convert(this.flag,context, fromObj, fromInfo.getBType(), (Class<T>)fromInfo.getBClass(), toObj, toInfo.getBType(), (Class<R>)toInfo.getBClass());
    }
    
    @Override
    public <T> int convert(BeancpContext context, T fromObj, int toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, fromInfo.getBType(), (Class<T>)fromInfo.getBClass(), toObj);
    }
    @Override
    public <T> float convert(BeancpContext context, T fromObj, float toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, fromInfo.getBType(), (Class<T>)fromInfo.getBClass(), toObj);
    }
    @Override
    public <T> boolean convert(BeancpContext context, T fromObj, boolean toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, fromInfo.getBType(), (Class<T>)fromInfo.getBClass(), toObj);
    }
    @Override
    public <T> char convert(BeancpContext context, T fromObj, char toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, fromInfo.getBType(), (Class<T>)fromInfo.getBClass(), toObj);
    }
    @Override
    public <T> double convert(BeancpContext context, T fromObj, double toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, fromInfo.getBType(), (Class<T>)fromInfo.getBClass(), toObj);
    }
    @Override
    public <T> short convert(BeancpContext context, T fromObj, short toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, fromInfo.getBType(), (Class<T>)fromInfo.getBClass(), toObj);
    }
    @Override
    public <T> byte convert(BeancpContext context, T fromObj, byte toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, fromInfo.getBType(), (Class<T>)fromInfo.getBClass(), toObj);
    }
    @Override
    public <T> long convert(BeancpContext context, T fromObj, long toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, fromInfo.getBType(), (Class<T>)fromInfo.getBClass(), toObj);
    }
    @Override
    public <R> R convert(BeancpContext context, int fromObj, R toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return (R) converter.convert(this.flag,context, fromObj, toObj, toInfo.getBType(), (Class<R>)toInfo.getBClass());
    }
    @Override
    public int convert(BeancpContext context, int fromObj, int toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public float convert(BeancpContext context, int fromObj, float toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public boolean convert(BeancpContext context, int fromObj, boolean toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public char convert(BeancpContext context, int fromObj, char toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public double convert(BeancpContext context, int fromObj, double toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public short convert(BeancpContext context, int fromObj, short toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public byte convert(BeancpContext context, int fromObj, byte toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public long convert(BeancpContext context, int fromObj, long toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public <R> R convert(BeancpContext context, float fromObj, R toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return (R) converter.convert(this.flag,context, fromObj, toObj, toInfo.getBType(), (Class<R>)toInfo.getBClass());
    }
    @Override
    public int convert(BeancpContext context, float fromObj, int toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public float convert(BeancpContext context, float fromObj, float toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public boolean convert(BeancpContext context, float fromObj, boolean toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public char convert(BeancpContext context, float fromObj, char toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public double convert(BeancpContext context, float fromObj, double toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public short convert(BeancpContext context, float fromObj, short toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public byte convert(BeancpContext context, float fromObj, byte toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public long convert(BeancpContext context, float fromObj, long toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public <R> R convert(BeancpContext context, boolean fromObj, R toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return (R) converter.convert(this.flag,context, fromObj, toObj, toInfo.getBType(), (Class<R>)toInfo.getBClass());
    }
    @Override
    public int convert(BeancpContext context, boolean fromObj, int toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public float convert(BeancpContext context, boolean fromObj, float toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public boolean convert(BeancpContext context, boolean fromObj, boolean toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public char convert(BeancpContext context, boolean fromObj, char toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public double convert(BeancpContext context, boolean fromObj, double toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public short convert(BeancpContext context, boolean fromObj, short toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public byte convert(BeancpContext context, boolean fromObj, byte toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public long convert(BeancpContext context, boolean fromObj, long toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public <R> R convert(BeancpContext context, char fromObj, R toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return (R) converter.convert(this.flag,context, fromObj, toObj, toInfo.getBType(), (Class<R>)toInfo.getBClass());
    }
    @Override
    public int convert(BeancpContext context, char fromObj, int toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public float convert(BeancpContext context, char fromObj, float toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public boolean convert(BeancpContext context, char fromObj, boolean toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public char convert(BeancpContext context, char fromObj, char toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public double convert(BeancpContext context, char fromObj, double toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public short convert(BeancpContext context, char fromObj, short toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public byte convert(BeancpContext context, char fromObj, byte toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public long convert(BeancpContext context, char fromObj, long toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public <R> R convert(BeancpContext context, double fromObj, R toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return (R) converter.convert(this.flag,context, fromObj, toObj, toInfo.getBType(), (Class<R>)toInfo.getBClass());
    }
    @Override
    public int convert(BeancpContext context, double fromObj, int toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public float convert(BeancpContext context, double fromObj, float toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public boolean convert(BeancpContext context, double fromObj, boolean toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public char convert(BeancpContext context, double fromObj, char toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public double convert(BeancpContext context, double fromObj, double toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public short convert(BeancpContext context, double fromObj, short toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public byte convert(BeancpContext context, double fromObj, byte toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public long convert(BeancpContext context, double fromObj, long toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public <R> R convert(BeancpContext context, short fromObj, R toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return (R) converter.convert(this.flag,context, fromObj, toObj, toInfo.getBType(), (Class<R>)toInfo.getBClass());
    }
    @Override
    public int convert(BeancpContext context, short fromObj, int toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public float convert(BeancpContext context, short fromObj, float toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public boolean convert(BeancpContext context, short fromObj, boolean toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public char convert(BeancpContext context, short fromObj, char toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public double convert(BeancpContext context, short fromObj, double toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public short convert(BeancpContext context, short fromObj, short toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public byte convert(BeancpContext context, short fromObj, byte toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public long convert(BeancpContext context, short fromObj, long toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public <R> R convert(BeancpContext context, byte fromObj, R toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return (R) converter.convert(this.flag,context, fromObj, toObj, toInfo.getBType(), (Class<R>)toInfo.getBClass());
    }
    @Override
    public int convert(BeancpContext context, byte fromObj, int toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public float convert(BeancpContext context, byte fromObj, float toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public boolean convert(BeancpContext context, byte fromObj, boolean toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public char convert(BeancpContext context, byte fromObj, char toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public double convert(BeancpContext context, byte fromObj, double toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public short convert(BeancpContext context, byte fromObj, short toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public byte convert(BeancpContext context, byte fromObj, byte toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public long convert(BeancpContext context, byte fromObj, long toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public <R> R convert(BeancpContext context, long fromObj, R toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return (R) converter.convert(this.flag,context, fromObj, toObj, toInfo.getBType(), (Class<R>)toInfo.getBClass());
    }
    @Override
    public int convert(BeancpContext context, long fromObj, int toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public float convert(BeancpContext context, long fromObj, float toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public boolean convert(BeancpContext context, long fromObj, boolean toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public char convert(BeancpContext context, long fromObj, char toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public double convert(BeancpContext context, long fromObj, double toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public short convert(BeancpContext context, long fromObj, short toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public byte convert(BeancpContext context, long fromObj, byte toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
    @Override
    public long convert(BeancpContext context, long fromObj, long toObj) {
    	BeancpCustomConverter converter=(BeancpCustomConverter)info.getConverter();
        return converter.convert(this.flag,context, fromObj, toObj);
    }
   
}
