package org.kepe.beancp.ct;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.kepe.beancp.config.BeancpCompare;
import org.kepe.beancp.config.BeancpCompareFlag;
import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.asm.BeancpInfoASMTool;
import org.kepe.beancp.ct.convert.BeancpConvertCustomProvider;
import org.kepe.beancp.ct.convert.BeancpConvertNonProvider;
import org.kepe.beancp.ct.converter.BeancpConverterInfo;
import org.kepe.beancp.ct.converter.BeancpConverterInfoGroup;
import org.kepe.beancp.ct.invocation.BeancpInvocation;
import org.kepe.beancp.ct.invocation.BeancpInvocationImp;
import org.kepe.beancp.ct.itf.BeancpASMConverter;
import org.kepe.beancp.ct.itf.BeancpConverter;
import org.kepe.beancp.ct.itf.BeancpCustomConverter;
import org.kepe.beancp.info.BeancpInfo;

/**
 * Hello world!
 *
 */
public abstract class BeancpConvertProvider
{

    private final static Map<BeancpInfo,Map<BeancpInfo,Map<BeancpFeature,BeancpConvertProvider>>> C_MAP=new ConcurrentHashMap<>();
	private static List<BeancpConverterInfo> converterList=new ArrayList<>();

    protected BeancpConverterInfo info;
    protected BeancpConverter converter ;
    protected BeancpInfo fromInfo;
    protected BeancpInfo toInfo;
    protected BeancpFeature flag;
    protected int distance;
    private BeancpConvertProvider parent;
    private BeancpInvocationImp invocation;
    private BeancpCompareProvider compareProvider;

    public BeancpConvertProvider(BeancpConvertProvider parent,BeancpFeature feature,BeancpConverterInfo info,BeancpInfo fromInfo,BeancpInfo toInfo){
        this.info=info;
        this.fromInfo=fromInfo;
        this.toInfo=toInfo;
        this.flag=feature;
        if(info==null) {
        	this.distance=-1;
        }else {
        	this.converter=info.getConverter(feature,fromInfo,toInfo);
            this.distance=converter.distance(feature, fromInfo.getBType(), fromInfo.getBClass(), toInfo.getBType(), toInfo.getBClass());
        }
        this.parent=parent;
        this.invocation=new BeancpInvocationImp(parent,this,feature,fromInfo,toInfo);
        this.compareProvider=BeancpCompareProvider.of(fromInfo, toInfo);
    }
    
    public int getDistance() {
    	return this.distance;
    }
    
	public <T, R> BeancpCompareFlag compare( T fromObj, R toObj) {
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( T fromObj, int toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( T fromObj, float toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( T fromObj, boolean toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( T fromObj, char toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( T fromObj, double toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( T fromObj, short toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( T fromObj, byte toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( T fromObj, long toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( int fromObj, R toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( int fromObj, int toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( int fromObj, float toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( int fromObj, boolean toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( int fromObj, char toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( int fromObj, double toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( int fromObj, short toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( int fromObj, byte toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( int fromObj, long toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( float fromObj, R toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( float fromObj, int toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( float fromObj, float toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( float fromObj, boolean toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( float fromObj, char toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( float fromObj, double toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( float fromObj, short toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( float fromObj, byte toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( float fromObj, long toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( boolean fromObj, R toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( boolean fromObj, int toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( boolean fromObj, float toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( boolean fromObj, boolean toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( boolean fromObj, char toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( boolean fromObj, double toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( boolean fromObj, short toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( boolean fromObj, byte toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( boolean fromObj, long toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( char fromObj, R toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( char fromObj, int toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( char fromObj, float toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( char fromObj, boolean toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( char fromObj, char toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( char fromObj, double toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( char fromObj, short toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( char fromObj, byte toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( char fromObj, long toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( double fromObj, R toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( double fromObj, int toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( double fromObj, float toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( double fromObj, boolean toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( double fromObj, char toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( double fromObj, double toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( double fromObj, short toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( double fromObj, byte toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( double fromObj, long toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( short fromObj, R toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( short fromObj, int toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( short fromObj, float toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( short fromObj, boolean toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( short fromObj, char toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( short fromObj, double toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( short fromObj, short toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( short fromObj, byte toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( short fromObj, long toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( byte fromObj, R toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( byte fromObj, int toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( byte fromObj, float toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( byte fromObj, boolean toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( byte fromObj, char toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( byte fromObj, double toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( byte fromObj, short toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( byte fromObj, byte toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( byte fromObj, long toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( long fromObj, R toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( long fromObj, int toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( long fromObj, float toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( long fromObj, boolean toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( long fromObj, char toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( long fromObj, double toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( long fromObj, short toObj) {
		
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( long fromObj, byte toObj) {
		return compareProvider.compare(invocation, fromObj, toObj);
	}

	
	public <T, R> BeancpCompareFlag compare( long fromObj, long toObj) {
		return compareProvider.compare(invocation, fromObj, toObj);
	}

    public abstract <T,R> R convert(BeancpContext context,T fromObj,R toObj);
    public <R> R convert(BeancpContext context,int fromObj,R toObj) {
    	return convert(context,(Integer)fromObj,toObj);
    }
    public long convert(BeancpContext context,int fromObj,long toObj) {
    	return (long)fromObj;
    }
    public <T> int convert(BeancpContext context,T fromObj,int toObj) {
      	return convert(context,fromObj,(Integer)toObj);
    }
    public <T> float convert(BeancpContext context,T fromObj,float toObj) {
      	return convert(context,fromObj,(Float)toObj);
    }
    public <T> boolean convert(BeancpContext context,T fromObj,boolean toObj) {
      	return convert(context,fromObj,(Boolean)toObj);
    }
    public <T> char convert(BeancpContext context,T fromObj,char toObj) {
      	return convert(context,fromObj,(Character)toObj);
    }
    public <T> double convert(BeancpContext context,T fromObj,double toObj) {
      	return convert(context,fromObj,(Double)toObj);
    }
    public <T> short convert(BeancpContext context,T fromObj,short toObj) {
      	return convert(context,fromObj,(Short)toObj);
    }
    public <T> byte convert(BeancpContext context,T fromObj,byte toObj) {
      	return convert(context,fromObj,(Byte)toObj);
    }
    public <T> long convert(BeancpContext context,T fromObj,long toObj) {
      	return convert(context,fromObj,(Long)toObj);
    }
    public int convert(BeancpContext context,int fromObj,int toObj) {
      	return convert(context,(Integer)fromObj,(Integer)toObj);
    }
    public float convert(BeancpContext context,int fromObj,float toObj) {
      	return convert(context,(Integer)fromObj,(Float)toObj);
    }
    public boolean convert(BeancpContext context,int fromObj,boolean toObj) {
      	return convert(context,(Integer)fromObj,(Boolean)toObj);
    }
    public char convert(BeancpContext context,int fromObj,char toObj) {
      	return convert(context,(Integer)fromObj,(Character)toObj);
    }
    public double convert(BeancpContext context,int fromObj,double toObj) {
      	return convert(context,(Integer)fromObj,(Double)toObj);
    }
    public short convert(BeancpContext context,int fromObj,short toObj) {
      	return convert(context,(Integer)fromObj,(Short)toObj);
    }
    public byte convert(BeancpContext context,int fromObj,byte toObj) {
      	return convert(context,(Integer)fromObj,(Byte)toObj);
    }
    public <R> R convert(BeancpContext context,float fromObj,R toObj) {
      	return convert(context,(Float)fromObj,toObj);
    }
    public int convert(BeancpContext context,float fromObj,int toObj) {
      	return convert(context,(Float)fromObj,(Integer)toObj);
    }
    public float convert(BeancpContext context,float fromObj,float toObj) {
      	return convert(context,(Float)fromObj,(Float)toObj);
    }
    public boolean convert(BeancpContext context,float fromObj,boolean toObj) {
      	return convert(context,(Float)fromObj,(Boolean)toObj);
    }
    public char convert(BeancpContext context,float fromObj,char toObj) {
      	return convert(context,(Float)fromObj,(Character)toObj);
    }
    public double convert(BeancpContext context,float fromObj,double toObj) {
      	return convert(context,(Float)fromObj,(Double)toObj);
    }
    public short convert(BeancpContext context,float fromObj,short toObj) {
      	return convert(context,(Float)fromObj,(Short)toObj);
    }
    public byte convert(BeancpContext context,float fromObj,byte toObj) {
      	return convert(context,(Float)fromObj,(Byte)toObj);
    }
    public long convert(BeancpContext context,float fromObj,long toObj) {
      	return convert(context,(Float)fromObj,(Long)toObj);
    }
    public <R> R convert(BeancpContext context,boolean fromObj,R toObj) {
      	return convert(context,(Boolean)fromObj,toObj);
    }
    public int convert(BeancpContext context,boolean fromObj,int toObj) {
      	return convert(context,(Boolean)fromObj,(Integer)toObj);
    }
    public float convert(BeancpContext context,boolean fromObj,float toObj) {
      	return convert(context,(Boolean)fromObj,(Float)toObj);
    }
    public boolean convert(BeancpContext context,boolean fromObj,boolean toObj) {
      	return convert(context,(Boolean)fromObj,(Boolean)toObj);
    }
    public char convert(BeancpContext context,boolean fromObj,char toObj) {
      	return convert(context,(Boolean)fromObj,(Character)toObj);
    }
    public double convert(BeancpContext context,boolean fromObj,double toObj) {
      	return convert(context,(Boolean)fromObj,(Double)toObj);
    }
    public short convert(BeancpContext context,boolean fromObj,short toObj) {
      	return convert(context,(Boolean)fromObj,(Short)toObj);
    }
    public byte convert(BeancpContext context,boolean fromObj,byte toObj) {
      	return convert(context,(Boolean)fromObj,(Byte)toObj);
    }
    public long convert(BeancpContext context,boolean fromObj,long toObj) {
      	return convert(context,(Boolean)fromObj,(Long)toObj);
    }
    public <R> R convert(BeancpContext context,char fromObj,R toObj) {
      	return convert(context,(Character)fromObj,toObj);
    }
    public int convert(BeancpContext context,char fromObj,int toObj) {
      	return convert(context,(Character)fromObj,(Integer)toObj);
    }
    public float convert(BeancpContext context,char fromObj,float toObj) {
      	return convert(context,(Character)fromObj,(Float)toObj);
    }
    public boolean convert(BeancpContext context,char fromObj,boolean toObj) {
      	return convert(context,(Character)fromObj,(Boolean)toObj);
    }
    public char convert(BeancpContext context,char fromObj,char toObj) {
      	return convert(context,(Character)fromObj,(Character)toObj);
    }
    public double convert(BeancpContext context,char fromObj,double toObj) {
      	return convert(context,(Character)fromObj,(Double)toObj);
    }
    public short convert(BeancpContext context,char fromObj,short toObj) {
      	return convert(context,(Character)fromObj,(Short)toObj);
    }
    public byte convert(BeancpContext context,char fromObj,byte toObj) {
      	return convert(context,(Character)fromObj,(Byte)toObj);
    }
    public long convert(BeancpContext context,char fromObj,long toObj) {
      	return convert(context,(Character)fromObj,(Long)toObj);
    }
    public <R> R convert(BeancpContext context,double fromObj,R toObj) {
      	return convert(context,(Double)fromObj,toObj);
    }
    public int convert(BeancpContext context,double fromObj,int toObj) {
      	return convert(context,(Double)fromObj,(Integer)toObj);
    }
    public float convert(BeancpContext context,double fromObj,float toObj) {
      	return convert(context,(Double)fromObj,(Float)toObj);
    }
    public boolean convert(BeancpContext context,double fromObj,boolean toObj) {
      	return convert(context,(Double)fromObj,(Boolean)toObj);
    }
    public char convert(BeancpContext context,double fromObj,char toObj) {
      	return convert(context,(Double)fromObj,(Character)toObj);
    }
    public double convert(BeancpContext context,double fromObj,double toObj) {
      	return convert(context,(Double)fromObj,(Double)toObj);
    }
    public short convert(BeancpContext context,double fromObj,short toObj) {
      	return convert(context,(Double)fromObj,(Short)toObj);
    }
    public byte convert(BeancpContext context,double fromObj,byte toObj) {
      	return convert(context,(Double)fromObj,(Byte)toObj);
    }
    public long convert(BeancpContext context,double fromObj,long toObj) {
      	return convert(context,(Double)fromObj,(Long)toObj);
    }
    public <R> R convert(BeancpContext context,short fromObj,R toObj) {
      	return convert(context,(Short)fromObj,toObj);
    }
    public int convert(BeancpContext context,short fromObj,int toObj) {
      	return convert(context,(Short)fromObj,(Integer)toObj);
    }
    public float convert(BeancpContext context,short fromObj,float toObj) {
      	return convert(context,(Short)fromObj,(Float)toObj);
    }
    public boolean convert(BeancpContext context,short fromObj,boolean toObj) {
      	return convert(context,(Short)fromObj,(Boolean)toObj);
    }
    public char convert(BeancpContext context,short fromObj,char toObj) {
      	return convert(context,(Short)fromObj,(Character)toObj);
    }
    public double convert(BeancpContext context,short fromObj,double toObj) {
      	return convert(context,(Short)fromObj,(Double)toObj);
    }
    public short convert(BeancpContext context,short fromObj,short toObj) {
      	return convert(context,(Short)fromObj,(Short)toObj);
    }
    public byte convert(BeancpContext context,short fromObj,byte toObj) {
      	return convert(context,(Short)fromObj,(Byte)toObj);
    }
    public long convert(BeancpContext context,short fromObj,long toObj) {
      	return convert(context,(Short)fromObj,(Long)toObj);
    }
    public <R> R convert(BeancpContext context,byte fromObj,R toObj) {
      	return convert(context,(Byte)fromObj,toObj);
    }
    public int convert(BeancpContext context,byte fromObj,int toObj) {
      	return convert(context,(Byte)fromObj,(Integer)toObj);
    }
    public float convert(BeancpContext context,byte fromObj,float toObj) {
      	return convert(context,(Byte)fromObj,(Float)toObj);
    }
    public boolean convert(BeancpContext context,byte fromObj,boolean toObj) {
      	return convert(context,(Byte)fromObj,(Boolean)toObj);
    }
    public char convert(BeancpContext context,byte fromObj,char toObj) {
      	return convert(context,(Byte)fromObj,(Character)toObj);
    }
    public double convert(BeancpContext context,byte fromObj,double toObj) {
      	return convert(context,(Byte)fromObj,(Double)toObj);
    }
    public short convert(BeancpContext context,byte fromObj,short toObj) {
      	return convert(context,(Byte)fromObj,(Short)toObj);
    }
    public byte convert(BeancpContext context,byte fromObj,byte toObj) {
      	return convert(context,(Byte)fromObj,(Byte)toObj);
    }
    public long convert(BeancpContext context,byte fromObj,long toObj) {
      	return convert(context,(Byte)fromObj,(Long)toObj);
    }
    public <R> R convert(BeancpContext context,long fromObj,R toObj) {
      	return convert(context,(Long)fromObj,toObj);
    }
    public int convert(BeancpContext context,long fromObj,int toObj) {
      	return convert(context,(Long)fromObj,(Integer)toObj);
    }
    public float convert(BeancpContext context,long fromObj,float toObj) {
      	return convert(context,(Long)fromObj,(Float)toObj);
    }
    public boolean convert(BeancpContext context,long fromObj,boolean toObj) {
      	return convert(context,(Long)fromObj,(Boolean)toObj);
    }
    public char convert(BeancpContext context,long fromObj,char toObj) {
      	return convert(context,(Long)fromObj,(Character)toObj);
    }
    public double convert(BeancpContext context,long fromObj,double toObj) {
      	return convert(context,(Long)fromObj,(Double)toObj);
    }
    public short convert(BeancpContext context,long fromObj,short toObj) {
      	return convert(context,(Long)fromObj,(Short)toObj);
    }
    public byte convert(BeancpContext context,long fromObj,byte toObj) {
      	return convert(context,(Long)fromObj,(Byte)toObj);
    }
    public long convert(BeancpContext context,long fromObj,long toObj) {
      	return convert(context,(Long)fromObj,(Long)toObj);
    }
    
    //public final  <T> T convert(BeancpContext context,Object fromObj,Object toObj);

    public BeancpConvertProvider of(Object fromObj,Object toObj){
        BeancpInfo fromInfo1=this.fromInfo.of(fromObj);
        BeancpInfo toInfo1=this.toInfo.of(toObj);
        if(fromInfo1==this.fromInfo&&toInfo1==this.toInfo){
            return this;
        }
        return of(this.flag,fromInfo1,toInfo1);
    }
    
    public BeancpConvertProvider of(Object fromObj){
        BeancpInfo fromInfo1=this.fromInfo.of(fromObj);
        if(fromInfo1==this.fromInfo){
            return this;
        }
        return of(this.flag,fromInfo1,this.toInfo);
    }
    public BeancpConvertProvider of2(Object toObj){
        BeancpInfo toInfo1=this.toInfo.of(toObj);
        if(toInfo1==this.toInfo){
            return this;
        }
        return of(this.flag,this.fromInfo,toInfo1);
    }
    public BeancpInvocationImp getInvocation() {
    	return invocation;
    }
    public static BeancpConvertProvider of(BeancpFeature flag,BeancpInfo fromInfo,BeancpInfo toInfo){
        return C_MAP.computeIfAbsent(fromInfo, key->new ConcurrentHashMap<>()).computeIfAbsent(toInfo, key->new ConcurrentHashMap<>()).computeIfAbsent(flag, key->generateProvider(flag,fromInfo,toInfo));
    }
    

    
    
    public static void register(BeancpConverterInfo info) {
    	synchronized(converterList){
            converterList.add(info);
            converterList.sort((key1,key2)->{
                return key1.getPriority()-key2.getPriority();
            });
        }
    	for(Map<BeancpInfo,Map<BeancpFeature,BeancpConvertProvider>> map:C_MAP.values()) {
    		for(Map<BeancpFeature,BeancpConvertProvider> map1:map.values()) {
    			for(BeancpConvertProvider provider:map1.values()) {
    				((BeancpConvertProviderProxy)provider).flush();
    			}
    		}
    	}
    }
    protected static BeancpConvertProvider generateProvider(BeancpFeature flag,BeancpInfo fromInfo,BeancpInfo toInfo){
        int length=converterList.size();
        BeancpConvertProvider provider=new BeancpConvertNonProvider(null,flag,fromInfo,toInfo);;
        int reaches=0;
        for(int i=0;i<length;i++){
            BeancpConverterInfo info=converterList.get(i);
            if(info.matches(fromInfo,toInfo)){
            	BeancpConverter converter=info.getConverter(flag,fromInfo,toInfo);
                if(converter instanceof BeancpCustomConverter){
                	reaches++;
                	provider = new BeancpConvertCustomProvider(provider,flag,info,fromInfo,toInfo);
                }else if(converter instanceof BeancpASMConverter){
                	reaches++;
                	provider = BeancpInfoASMTool.generateASMProvider(provider,info,flag,fromInfo,toInfo);
                }
            }
        }
        return BeancpConvertProviderProxy.createProxy(reaches,provider);
    }
    
    protected static int getProviderReaches(BeancpFeature flag,BeancpInfo fromInfo,BeancpInfo toInfo){
        int length=converterList.size();
        int reaches=0;
        for(int i=0;i<length;i++){
            BeancpConverterInfo info=converterList.get(i);
            if(info.matches(fromInfo,toInfo)){
            	BeancpConverter converter=info.getConverter(flag,fromInfo,toInfo);
                if(converter instanceof BeancpCustomConverter){
                	reaches++;
                }else if(converter instanceof BeancpASMConverter){
                	reaches++;
                }
            }
        }
        return reaches;
    }
	
}
