package org.kepe.beancp.ct.reg;




import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.zone.ZoneRules;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

import org.kepe.beancp.config.BeancpCompare;
import org.kepe.beancp.config.BeancpCompareFlag;
import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.BeancpConvertProvider;
import org.kepe.beancp.ct.convert.BeancpConvertMapper;
import org.kepe.beancp.ct.converter.BeancpConverterInfo;
import org.kepe.beancp.ct.invocation.BeancpInvocation;
import org.kepe.beancp.ct.invocation.BeancpInvocationImp;
import org.kepe.beancp.ct.invocation.BeancpInvocationJO;
import org.kepe.beancp.ct.invocation.BeancpInvocationOJ;
import org.kepe.beancp.ct.invocation.BeancpInvocationOO;
import org.kepe.beancp.ct.itf.BeancpConverter;
import org.kepe.beancp.ct.itf.BeancpCustomConverter;
import org.kepe.beancp.ct.reg.compare.BeancpDefaultCustomCompare;
import org.kepe.beancp.exception.BeancpException;
import org.kepe.beancp.info.BeancpInfo;
import org.kepe.beancp.tool.BeancpInfoMatcherTool;
import org.kepe.beancp.tool.BeancpTool;


public class BeancpBase4Registers extends BeancpRegister{
	public static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();
    public static final String SHANGHAI_ZONE_ID_NAME = "Asia/Shanghai";
    public static final ZoneId SHANGHAI_ZONE_ID
            = SHANGHAI_ZONE_ID_NAME.equals(DEFAULT_ZONE_ID.getId())
            ? DEFAULT_ZONE_ID
            : ZoneId.of(SHANGHAI_ZONE_ID_NAME);
    public static final ZoneRules SHANGHAI_ZONE_RULES = SHANGHAI_ZONE_ID.getRules();
    public static final String OFFSET_8_ZONE_ID_NAME = "+08:00";
    public static final ZoneId OFFSET_8_ZONE_ID = ZoneId.of(OFFSET_8_ZONE_ID_NAME);
	private static final LocalDate LOCAL_DATE_19700101 = LocalDate.of(1970, 1, 1);
	private static final DateTimeFormatter DEFAULT_DATE_FORMATTER=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.nnnnnnnnn");
	private static final DateTimeFormatter DEFAULT_DATE_FORMATTER2=DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final DateTimeFormatter DEFAULT_DATE_FORMATTER3=DateTimeFormatter.ofPattern("HH:mm:ss.nnnnnnnnn");
	public static void registers() {
		
		BeancpCustomConverter converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 5;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				if(toObj!=null) {
					((Date)toObj).setTime((Long)fromObj);
					return toObj;
				}
				return ((BeancpInvocationImp)invocation).getToInfo().getDefaultMapper().newInstance(context, fromObj);
				
			};
			@Override
			public Object convert(BeancpInvocationJO invocation, BeancpContext context, long fromObj, Object toObj) {
				if(toObj!=null) {
					((Date)toObj).setTime(fromObj);
					return toObj;
				}
				return ((BeancpInvocationImp)invocation).getToInfo().getDefaultMapper().newInstance(context, fromObj);
			}
			
		};
		registerEq2Extends(long.class,Date.class, converter1, PRIORITY8);
		registerEq2Extends(Long.class,Date.class, converter1, PRIORITY8);
		
		BeancpCompare compare1=new BeancpDefaultCustomCompare() {
			@Override
			public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, long fromObj, R toObj) {
				return this.compare(invocation, fromObj, ((Date)toObj).getTime());
			}
			@Override
			public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, T fromObj, R toObj) {
				return this.compare(invocation, ((Long)toObj).longValue(), ((Date)toObj).getTime());
			}
		};
		cregister(long.class,Date.class,compare1,PRIORITY8);
		cregister(Long.class,Date.class,compare1,PRIORITY8);
		
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 5;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return LocalDateTime.ofInstant(Instant.ofEpochMilli((Long)fromObj), ZoneId.systemDefault());
			};
			@Override
			public Object convert(BeancpInvocationJO invocation, BeancpContext context, long fromObj, Object toObj) {
				return LocalDateTime.ofInstant(Instant.ofEpochMilli(fromObj), ZoneId.systemDefault());
			}
			
		};
		registerEq(long.class,LocalDateTime.class, converter1, PRIORITY8);
		registerEq(Long.class,LocalDateTime.class, converter1, PRIORITY8);
		
		compare1=new BeancpDefaultCustomCompare() {
			@Override
			public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, long fromObj, R toObj) {
				Instant instant=((LocalDateTime)toObj).atZone(ZoneId.systemDefault()).toInstant();
				return this.compare(invocation, fromObj*1000000, instant.getEpochSecond()*1000000000+instant.getNano());
			}
			@Override
			public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, T fromObj, R toObj) {
				Instant instant=((LocalDateTime)toObj).atZone(ZoneId.systemDefault()).toInstant();
				return this.compare(invocation, ((Long)toObj).longValue()*1000000, instant.getEpochSecond()*1000000000+instant.getNano());
			}
		};
		cregister(long.class,LocalDateTime.class,compare1,PRIORITY8);
		cregister(Long.class,LocalDateTime.class,compare1,PRIORITY8);
		
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 5;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return Instant.ofEpochMilli((Long)fromObj);
			};
			@Override
			public Object convert(BeancpInvocationJO invocation, BeancpContext context, long fromObj, Object toObj) {
				return Instant.ofEpochMilli(fromObj);
			}
			
		};
		registerEq(long.class,Instant.class, converter1, PRIORITY8);
		registerEq(Long.class,Instant.class, converter1, PRIORITY8);
		
		compare1=new BeancpDefaultCustomCompare() {
			@Override
			public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, long fromObj, R toObj) {
				Instant instant=(Instant)toObj;
				return this.compare(invocation, fromObj*1000000, instant.getEpochSecond()*1000000000+instant.getNano());
			}
			@Override
			public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, T fromObj, R toObj) {
				Instant instant=(Instant)toObj;
				return this.compare(invocation, ((Long)toObj).longValue()*1000000, instant.getEpochSecond()*1000000000+instant.getNano());
			}
		};
		cregister(long.class,Instant.class,compare1,PRIORITY8);
		cregister(Long.class,Instant.class,compare1,PRIORITY8);
		
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 5;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return LocalDateTime.ofInstant(Instant.ofEpochMilli((Long)fromObj), ZoneId.systemDefault()).toLocalDate();
			};
			@Override
			public Object convert(BeancpInvocationJO invocation, BeancpContext context, long fromObj, Object toObj) {
				return LocalDateTime.ofInstant(Instant.ofEpochMilli(fromObj), ZoneId.systemDefault()).toLocalDate();
			}
			
		};
		registerEq(long.class,LocalDate.class, converter1, PRIORITY8);
		registerEq(Long.class,LocalDate.class, converter1, PRIORITY8);
		
		
		compare1=new BeancpDefaultCustomCompare() {
			@Override
			public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, long fromObj, R toObj) {
				return this.compare(invocation, fromObj, ((LocalDate)toObj).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
			}
			@Override
			public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, T fromObj, R toObj) {
				return this.compare(invocation, ((Long)toObj).longValue(), ((LocalDate)toObj).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
			}
		};
		cregister(long.class,LocalDate.class,compare1,PRIORITY8);
		cregister(Long.class,LocalDate.class,compare1,PRIORITY8);
		
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 5;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return LocalDateTime.ofInstant(Instant.ofEpochMilli((Long)fromObj), ZoneId.systemDefault()).toLocalTime();
			};
			@Override
			public Object convert(BeancpInvocationJO invocation, BeancpContext context, long fromObj, Object toObj) {
				return LocalDateTime.ofInstant(Instant.ofEpochMilli(fromObj), ZoneId.systemDefault()).toLocalTime();
			}
			
		};
		registerEq(long.class,LocalTime.class, converter1, PRIORITY8);
		registerEq(Long.class,LocalTime.class, converter1, PRIORITY8);
		
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 5;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return ((Date)fromObj).getTime();
			};
			@Override
			public long convert(BeancpInvocationOJ invocation, BeancpContext context, Object fromObj, long toObj) {
				return ((Date)fromObj).getTime();
			}
			
		};
		registerExtends2Eq(Date.class,long.class, converter1, PRIORITY8);
		registerExtends2Eq(Date.class,Long.class, converter1, PRIORITY8);
		
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 5;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				ZoneId zone = ZoneId.systemDefault();
				Instant instant = ((LocalDateTime)fromObj).atZone(zone).toInstant();
				return instant.toEpochMilli();
			};
			@Override
			public long convert(BeancpInvocationOJ invocation, BeancpContext context, Object fromObj, long toObj) {
				ZoneId zone = ZoneId.systemDefault();
				Instant instant = ((LocalDateTime)fromObj).atZone(zone).toInstant();
				return instant.toEpochMilli();
			}
			
		};
		registerEq(LocalDateTime.class,long.class, converter1, PRIORITY8);
		registerEq(LocalDateTime.class,Long.class, converter1, PRIORITY8);
		
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 5;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return ((Instant)fromObj).toEpochMilli();
			};
			@Override
			public long convert(BeancpInvocationOJ invocation, BeancpContext context, Object fromObj, long toObj) {
				return ((Instant)fromObj).toEpochMilli();
			}
			
		};
		registerEq(Instant.class,long.class, converter1, PRIORITY8);
		registerEq(Instant.class,Long.class, converter1, PRIORITY8);
		
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 5;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				ZoneId zone = ZoneId.systemDefault();
				Instant instant = ((LocalDate)fromObj).atStartOfDay().atZone(zone).toInstant();
				return instant.toEpochMilli();
			};
			@Override
			public long convert(BeancpInvocationOJ invocation, BeancpContext context, Object fromObj, long toObj) {
				ZoneId zone = ZoneId.systemDefault();
				Instant instant = ((LocalDate)fromObj).atStartOfDay().atZone(zone).toInstant();
				return instant.toEpochMilli();
			}
			
		};
		registerEq(LocalDate.class,long.class, converter1, PRIORITY8);
		registerEq(LocalDate.class,Long.class, converter1, PRIORITY8);
		
		registerEq2Extends(String.class,Date.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			if(toObj!=null) {
				Instant instant=parseLocalDateTime((String)fromObj).atZone(DEFAULT_ZONE_ID).toInstant();
				((Date)toObj).setTime(instant.toEpochMilli());
				if(toObj instanceof Timestamp) {
					((Timestamp)toObj).setNanos(instant.getNano());
				}
				return toObj;
			}
			Object ret=((BeancpInvocationImp)invocation).getToInfo().getDefaultMapper().newInstance(context, parseLocalDateTime((String)fromObj).atZone(DEFAULT_ZONE_ID).toInstant().toEpochMilli());
			if(fromObj instanceof Timestamp&&ret instanceof Timestamp) {
				((Timestamp)ret).setNanos(((Timestamp)fromObj).getNanos());
			}
			return ret;
		}), PRIORITY8);
		
		registerEq(String.class,LocalDateTime.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			return parseLocalDateTime((String)fromObj);
		}), PRIORITY8);
		registerEq(String.class,LocalDate.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			return parseLocalDateTime((String)fromObj).toLocalDate();
		}), PRIORITY8);
		registerEq(String.class,LocalTime.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			return parseLocalDateTime((String)fromObj).toLocalTime();
		}), PRIORITY8);
		registerEq(String.class,Instant.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			return parseLocalDateTime((String)fromObj).atZone(DEFAULT_ZONE_ID).toInstant();
		}), PRIORITY8);
		
		registerExtends2Eq(Date.class,String.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			return shortDateStr(DEFAULT_DATE_FORMATTER.format(LocalDateTime.ofInstant(((Date)fromObj).toInstant(),DEFAULT_ZONE_ID)));
		}), PRIORITY8);
		registerEq(Instant.class,String.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			return shortDateStr(DEFAULT_DATE_FORMATTER.format(LocalDateTime.ofInstant((Instant)fromObj,DEFAULT_ZONE_ID)));
		}), PRIORITY8);
		registerEq(LocalDateTime.class,String.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			return shortDateStr(DEFAULT_DATE_FORMATTER.format((LocalDateTime)fromObj));
		}), PRIORITY8);
		registerEq(LocalDate.class,String.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			return DEFAULT_DATE_FORMATTER2.format((LocalDate)fromObj);
		}), PRIORITY8);
		registerEq(LocalTime.class,String.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			return shortDateStr2(DEFAULT_DATE_FORMATTER3.format((LocalTime)fromObj));
		}), PRIORITY8);
		
		register(Date.class,Date.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			if(toObj!=null) {
				((Date)toObj).setTime(((Date)fromObj).getTime());
				if(fromObj instanceof Timestamp&&toObj instanceof Timestamp) {
					((Timestamp)toObj).setNanos(((Timestamp)fromObj).getNanos());
				}
				return toObj;
			}
			if(((BeancpInvocationImp)invocation).getToInfo().equals(((BeancpInvocationImp)invocation).getToInfo())&&!invocation.getFeature().is(BeancpFeature.ALLWAYS_NEW)) {
				return fromObj;
			}
			Object ret=((BeancpInvocationImp)invocation).getToInfo().getDefaultMapper().newInstance(context, ((Date)fromObj).getTime());
			if(fromObj instanceof Timestamp&&ret instanceof Timestamp) {
				((Timestamp)ret).setNanos(((Timestamp)fromObj).getNanos());
			}
			return ret;
		}), PRIORITY8);
		registerExtends2Eq(Date.class,Instant.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			return ((Date)fromObj).toInstant();
		}), PRIORITY8);
		cregister(Date.class,Instant.class, new BeancpDefaultCustomCompare() {
			
			@Override
			public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, T fromObj, R toObj) {
				Instant instant=(Instant)toObj;
				return this.compare(invocation, ((Date) fromObj).getTime()*1000000, instant.getEpochSecond()*1000000000+instant.getNano());
			}
		}, PRIORITY8);
		registerExtends2Eq(Date.class,LocalDateTime.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			return LocalDateTime.ofInstant(((Date)fromObj).toInstant(), DEFAULT_ZONE_ID);
		}), PRIORITY8);
		cregister(Date.class,LocalDateTime.class, new BeancpDefaultCustomCompare() {
			
			@Override
			public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, T fromObj, R toObj) {
				Instant instant=((LocalDateTime)toObj).atZone(DEFAULT_ZONE_ID).toInstant();
				return this.compare(invocation, ((Date) fromObj).getTime()*1000000, instant.getEpochSecond()*1000000000+instant.getNano());
			}
		}, PRIORITY8);
		registerExtends2Eq(Date.class,LocalDate.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			return LocalDateTime.ofInstant(((Date)fromObj).toInstant(), DEFAULT_ZONE_ID).toLocalDate();
		}), PRIORITY8);
		cregister(Date.class,LocalDate.class, new BeancpDefaultCustomCompare() {
			
			@Override
			public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, T fromObj, R toObj) {
				Instant instant=((LocalDate)toObj).atStartOfDay().atZone(DEFAULT_ZONE_ID).toInstant();
				return this.compare(invocation, ((Date) fromObj).getTime(), instant.toEpochMilli());
			}
		}, PRIORITY8);
		registerExtends2Eq(Date.class,LocalTime.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			return LocalDateTime.ofInstant(((Date)fromObj).toInstant(), DEFAULT_ZONE_ID).toLocalTime();
		}), PRIORITY8);
		registerEq2Extends(Instant.class,Date.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			Object ret=((BeancpInvocationImp)invocation).getToInfo().getDefaultMapper().newInstance(context, ((Instant)fromObj).toEpochMilli());
			if(ret instanceof Timestamp) {
				((Timestamp)ret).setNanos(((Instant)fromObj).getNano());
			}
			return ret;
		}), PRIORITY8);
		registerEq2Extends(LocalDateTime.class,Date.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			Instant instant=((LocalDateTime)fromObj).atZone(DEFAULT_ZONE_ID).toInstant();
			Object ret=((BeancpInvocationImp)invocation).getToInfo().getDefaultMapper().newInstance(context, instant.toEpochMilli());
			if(ret instanceof Timestamp) {
				((Timestamp)ret).setNanos(instant.getNano());
			}
			return ret;
		}), PRIORITY8);
		registerEq2Extends(LocalDate.class,Date.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			Instant instant=((LocalDate)fromObj).atStartOfDay().atZone(DEFAULT_ZONE_ID).toInstant();
			Object ret=((BeancpInvocationImp)invocation).getToInfo().getDefaultMapper().newInstance(context, instant.toEpochMilli());
			return ret;
		}), PRIORITY8);
		
		registerEq(Instant.class,LocalDateTime.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			return LocalDateTime.ofInstant((Instant)fromObj, DEFAULT_ZONE_ID);
		}), PRIORITY8);
		cregister(Instant.class,LocalDateTime.class, new BeancpDefaultCustomCompare() {
			
			@Override
			public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, T fromObj, R toObj) {
				Instant instant1=((Instant)fromObj);
				Instant instant=((LocalDateTime)toObj).atZone(DEFAULT_ZONE_ID).toInstant();
				return this.compare(invocation, instant1.getEpochSecond()*1000000000+instant1.getNano(), instant.getEpochSecond()*1000000000+instant.getNano());
			}
		}, PRIORITY8);
		registerEq(Instant.class,LocalDate.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			return LocalDateTime.ofInstant((Instant)fromObj, DEFAULT_ZONE_ID).toLocalDate();
		}), PRIORITY8);
		cregister(Instant.class,LocalDate.class, new BeancpDefaultCustomCompare() {
			
			@Override
			public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, T fromObj, R toObj) {
				Instant instant1=((Instant)fromObj);
				Instant instant=((LocalDate)toObj).atStartOfDay().atZone(DEFAULT_ZONE_ID).toInstant();
				return this.compare(invocation, instant1.getEpochSecond()*1000000000+instant1.getNano(), instant.getEpochSecond()*1000000000+instant.getNano());
			}
		}, PRIORITY8);
		
		registerEq(LocalDateTime.class,Instant.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			return ((LocalDateTime)fromObj).atZone(DEFAULT_ZONE_ID).toInstant();
		}), PRIORITY8);
		registerEq(LocalDate.class,Instant.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			return ((LocalDate)fromObj).atStartOfDay().atZone(DEFAULT_ZONE_ID).toInstant();
		}), PRIORITY8);
		
		registerEq(LocalDateTime.class,LocalDate.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			return ((LocalDateTime)fromObj).toLocalDate();
		}), PRIORITY8);
		cregister(LocalDateTime.class,LocalDate.class, new BeancpDefaultCustomCompare() {
			@Override
			public <T, R> BeancpCompareFlag compare(BeancpInvocation invocation, T fromObj, R toObj) {
				return BeancpCompareFlag.of( ((LocalDateTime)fromObj).compareTo(((LocalDate)toObj).atStartOfDay()));
			}
		}, PRIORITY8);
		registerEq(LocalDateTime.class,LocalTime.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			return ((LocalDateTime)fromObj).toLocalTime();
		}), PRIORITY8);
		registerEq(LocalDate.class,LocalDateTime.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			return ((LocalDate)fromObj).atStartOfDay();
		}), PRIORITY8); 
		
		
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 5;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				return ((Calendar)fromObj).getTimeInMillis();
			};
			@Override
			public long convert(BeancpInvocationOJ invocation, BeancpContext context, Object fromObj, long toObj) {
				return ((Calendar)fromObj).getTimeInMillis();
			}
			
		};
		registerExtends2Eq(Calendar.class,long.class, converter1, PRIORITY8);
		registerExtends2Eq(Calendar.class,Long.class, converter1, PRIORITY8);
		
		converter1=new BeancpCustomConverter() {
			public int distance(BeancpFeature feature, Class fromClass, Class toClass) {
				return 5;
			}

			@Override
			public Object convert(BeancpInvocationOO invocation, BeancpContext context, Object fromObj,
					Object toObj) {
				if(toObj!=null) {
					((Calendar)toObj).setTimeInMillis((Long)fromObj);
					return toObj;
				}
				Calendar cal=Calendar.getInstance();
				cal.setTimeInMillis((Long)fromObj);
				return cal;
				
			};
			@Override
			public Object convert(BeancpInvocationJO invocation, BeancpContext context, long fromObj, Object toObj) {
				if(toObj!=null) {
					((Calendar)toObj).setTimeInMillis(fromObj);
					return toObj;
				}
				Calendar cal=Calendar.getInstance();
				cal.setTimeInMillis(fromObj);
				return cal;
			}
			
		};
		registerEq2Extends(long.class,Calendar.class, converter1, PRIORITY8);
		registerEq2Extends(Long.class,Calendar.class, converter1, PRIORITY8);


		registerEq2Extends(String.class,Calendar.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			if(toObj!=null) {
				Instant instant=parseLocalDateTime((String)fromObj).atZone(DEFAULT_ZONE_ID).toInstant();
				((Calendar)toObj).setTimeInMillis(instant.toEpochMilli());
				return toObj;
			}
			Calendar cal=Calendar.getInstance();
			cal.setTimeInMillis(parseLocalDateTime((String)fromObj).atZone(DEFAULT_ZONE_ID).toInstant().toEpochMilli());
			return cal;
		}), PRIORITY8);

		registerExtends2Eq(Calendar.class,String.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			return shortDateStr(DEFAULT_DATE_FORMATTER.format(LocalDateTime.ofInstant(((Calendar)fromObj).toInstant(),DEFAULT_ZONE_ID)));
		}), PRIORITY8);


		register(Calendar.class,Calendar.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			if(toObj!=null) {
				((Calendar)toObj).setTimeInMillis(((Calendar)fromObj).getTimeInMillis());
				return toObj;
			}
			if(((BeancpInvocationImp)invocation).getToInfo().equals(((BeancpInvocationImp)invocation).getToInfo())&&!invocation.getFeature().is(BeancpFeature.ALLWAYS_NEW)) {
				return fromObj;
			}
			Calendar cal=Calendar.getInstance();
			cal.setTimeInMillis(((Calendar)fromObj).getTimeInMillis());
			return cal;
		}), PRIORITY8);

		registerExtends2Eq(Calendar.class,Instant.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			return ((Calendar)fromObj).toInstant();
		}), PRIORITY8);
		registerExtends2Eq(Calendar.class,LocalDateTime.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			return LocalDateTime.ofInstant(((Calendar)fromObj).toInstant(), DEFAULT_ZONE_ID);
		}), PRIORITY8);
		registerExtends2Eq(Calendar.class,LocalDate.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			return LocalDateTime.ofInstant(((Calendar)fromObj).toInstant(), DEFAULT_ZONE_ID).toLocalDate();
		}), PRIORITY8);
		registerExtends2Eq(Calendar.class,LocalTime.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			return LocalDateTime.ofInstant(((Calendar)fromObj).toInstant(), DEFAULT_ZONE_ID).toLocalTime();
		}), PRIORITY8);
		registerEq2Extends(Instant.class,Calendar.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			Calendar cal=Calendar.getInstance();
			cal.setTimeInMillis(((Instant)fromObj).toEpochMilli());
			return cal;
		}), PRIORITY8);
		registerEq2Extends(LocalDateTime.class,Date.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			Instant instant=((LocalDateTime)fromObj).atZone(DEFAULT_ZONE_ID).toInstant();
			Calendar cal=Calendar.getInstance();
			cal.setTimeInMillis(instant.toEpochMilli());
			return cal;
		}), PRIORITY8);
		registerEq2Extends(LocalDate.class,Date.class,BeancpTool.create(10, (invocation,context,fromObj,toObj)->{
			Instant instant=((LocalDate)fromObj).atStartOfDay().atZone(DEFAULT_ZONE_ID).toInstant();
			Calendar cal=Calendar.getInstance();
			cal.setTimeInMillis(instant.toEpochMilli());
			return cal;
		}), PRIORITY8);
		//Calendar long String Date Instant LocalDateTime LocalDate LocalTime
	}
	
	
	private static void register(Type fromType, Type toType, BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(toType)), converter, priority));
    }
	private static void registerExtends2Eq(Type fromType, Type toType, BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createEqualMatcher(BeancpInfo.of(toType)), converter, priority));
    }
	private static void registerEq(Type fromType, Type toType, BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createEqualMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createEqualMatcher(BeancpInfo.of(toType)), converter, priority));
    }
	private static void registerEq2Extends(Type fromType, Type toType, BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createEqualMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(toType)), converter, priority));
    }
	private static void registerEq2Super(Type fromType, Type toType, BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createEqualMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createSuperMatcher(BeancpInfo.of(toType)), converter, priority));
    }
	private static void register2EqType(Type fromType, Type toType, BeancpConverter converter,int priority) {
        BeancpConvertProvider.register(BeancpConverterInfo.of(BeancpInfoMatcherTool.createExtendsMatcher(BeancpInfo.of(fromType)), BeancpInfoMatcherTool.createEqualMatcher(BeancpInfo.of(toType)), converter, priority));
    }
	
	private static final Pattern DEFAULT_SPLIT=Pattern.compile("[^\\d]");
	private static String shortDateStr(String str) {
		int len=str.length();
		if(str.endsWith("000")) {
			len-=3;
			if(str.endsWith("000000")) {
				len-=3;
				if(str.endsWith("000000000")) {
					len-=4;
					if(str.endsWith("00:00:00.000000000")) {
						len-=9;
					}
				}
			}
			str=str.substring(0,len);
		}
		return str;
	}
	private static String shortDateStr2(String str) {
		int len=str.length();
		if(str.endsWith("000")) {
			len-=3;
			if(str.endsWith("000000")) {
				len-=3;
				if(str.endsWith("000000000")) {
					len-=4;
				}
			}
			str=str.substring(0,len);
		}
		return str;
	}
	private static LocalDateTime parseLocalDateTime(String str) {
        if (str == null||"".equals(str)) {
            return null;
        }
        try {
			return parseLocalDateTime(str, 0, str.length());
		} catch (Exception e) {
			throw new BeancpException(BeancpException.EType.IGNORE,e);
		}
	}
	private static LocalDateTime parseLocalDateTime(String str, int off, int len) {
        if (str == null || len == 0) {
            return null;
        }

        
        char[] chars = new char[len];
        str.getChars(off, off + len, chars, 0);
        LocalDateTime ldt = parseLocalDateTime(chars, off, len);
        if (ldt == null) {
            switch (str) {
                case "":
                case "null":
                case "00000000":
                case "000000000000":
                case "0000年00月00日":
                case "0000-0-00":
                case "0000-00-0":
                case "0000-00-00":
                    return null;
                default:
                    throw new DateTimeParseException(str, str, off);
            }
        }

        return ldt;
    }
	
	public static LocalDateTime parseLocalDateTime(char[] str, int off, int len) {
        if (str == null || len == 0) {
            return null;
        }

        switch (len) {
            case 4:
                if (str[off] == 'n' && str[off + 1] == 'u' && str[off + 2] == 'l' && str[off + 3] == 'l') {
                    return null;
                }
                String input = new String(str, off, len);
                throw new DateTimeParseException("illegal input " + input, input, 0);
            case 8: {
                if (str[2] == ':' && str[5] == ':') {
                    LocalTime localTime = parseLocalTime8(str, off);
                    return LocalDateTime.of(LOCAL_DATE_19700101, localTime);
                }
                LocalDate localDate = parseLocalDate8(str, off);
                if (localDate == null) {
                    return null;
                }
                return LocalDateTime.of(localDate, LocalTime.MIN);
            }
            case 9: {
                LocalDate localDate = parseLocalDate9(str, off);
                if (localDate == null) {
                    return null;
                }
                return LocalDateTime.of(localDate, LocalTime.MIN);
            }
            case 10: {
                LocalDate localDate = parseLocalDate10(str, off);
                if (localDate == null) {
                    return null;
                }
                return LocalDateTime.of(localDate, LocalTime.MIN);
            }
            case 11: {
                LocalDate localDate = parseLocalDate11(str, off);
                if (localDate == null) {
                    return null;
                }
                return LocalDateTime.of(localDate, LocalTime.MIN);
            }
            case 12:
                return parseLocalDateTime12(str, off);
            case 14:
                return parseLocalDateTime14(str, off);
            case 16:
                return parseLocalDateTime16(str, off);
            case 17:
                return parseLocalDateTime17(str, off);
            case 18:
                return parseLocalDateTime18(str, off);
            case 19:
                return parseLocalDateTime19(str, off);
            case 20:
                return parseLocalDateTime20(str, off);
            default:
                return parseLocalDateTimeX(str, off, len);
        }
    }

    public static LocalTime parseLocalTime5(byte[] bytes, int off) {
        if (off + 5 > bytes.length) {
            return null;
        }

        byte c0 = bytes[off];
        byte c1 = bytes[off + 1];
        byte c2 = bytes[off + 2];
        byte c3 = bytes[off + 3];
        byte c4 = bytes[off + 4];

        byte h0, h1, i0, i1;
        if (c2 == ':') {
            h0 = c0;
            h1 = c1;
            i0 = c3;
            i1 = c4;
        } else {
            return null;
        }

        int hour;
        if (h0 >= '0' && h0 <= '9'
                && h1 >= '0' && h1 <= '9'
        ) {
            hour = (h0 - '0') * 10 + (h1 - '0');
        } else {
            return null;
        }

        int minute;
        if (i0 >= '0' && i0 <= '9'
                && i1 >= '0' && i1 <= '9'
        ) {
            minute = (i0 - '0') * 10 + (i1 - '0');
        } else {
            return null;
        }

        return LocalTime.of(hour, minute);
    }

    public static LocalTime parseLocalTime5(char[] chars, int off) {
        if (off + 5 > chars.length) {
            return null;
        }

        char c0 = chars[off];
        char c1 = chars[off + 1];
        char c2 = chars[off + 2];
        char c3 = chars[off + 3];
        char c4 = chars[off + 4];

        char h0, h1, i0, i1;
        if (c2 == ':') {
            h0 = c0;
            h1 = c1;
            i0 = c3;
            i1 = c4;
        } else {
            return null;
        }

        int hour;
        if (h0 >= '0' && h0 <= '9'
                && h1 >= '0' && h1 <= '9'
        ) {
            hour = (h0 - '0') * 10 + (h1 - '0');
        } else {
            return null;
        }

        int minute;
        if (i0 >= '0' && i0 <= '9'
                && i1 >= '0' && i1 <= '9'
        ) {
            minute = (i0 - '0') * 10 + (i1 - '0');
        } else {
            return null;
        }

        return LocalTime.of(hour, minute);
    }

    public static LocalTime parseLocalTime8(byte[] bytes, int off) {
        if (off + 8 > bytes.length) {
            return null;
        }

        char c0 = (char) bytes[off];
        char c1 = (char) bytes[off + 1];
        char c2 = (char) bytes[off + 2];
        char c3 = (char) bytes[off + 3];
        char c4 = (char) bytes[off + 4];
        char c5 = (char) bytes[off + 5];
        char c6 = (char) bytes[off + 6];
        char c7 = (char) bytes[off + 7];

        return parseLocalTime(c0, c1, c2, c3, c4, c5, c6, c7);
    }

    public static LocalTime parseLocalTime8(char[] bytes, int off) {
        if (off + 8 > bytes.length) {
            return null;
        }

        char c0 = bytes[off];
        char c1 = bytes[off + 1];
        char c2 = bytes[off + 2];
        char c3 = bytes[off + 3];
        char c4 = bytes[off + 4];
        char c5 = bytes[off + 5];
        char c6 = bytes[off + 6];
        char c7 = bytes[off + 7];

        return parseLocalTime(c0, c1, c2, c3, c4, c5, c6, c7);
    }

    public static LocalTime parseLocalTime(
            char c0,
            char c1,
            char c2,
            char c3,
            char c4,
            char c5,
            char c6,
            char c7
    ) {
        char h0, h1, i0, i1, s0, s1;
        if (c2 == ':' && c5 == ':') {
            h0 = c0;
            h1 = c1;
            i0 = c3;
            i1 = c4;
            s0 = c6;
            s1 = c7;
        } else {
            return null;
        }

        int hour;
        if (h0 >= '0' && h0 <= '9'
                && h1 >= '0' && h1 <= '9'
        ) {
            hour = (h0 - '0') * 10 + (h1 - '0');
        } else {
            return null;
        }

        int minute;
        if (i0 >= '0' && i0 <= '9'
                && i1 >= '0' && i1 <= '9'
        ) {
            minute = (i0 - '0') * 10 + (i1 - '0');
        } else {
            return null;
        }

        int second;
        if (s0 >= '0' && s0 <= '9'
                && s1 >= '0' && s1 <= '9'
        ) {
            second = (s0 - '0') * 10 + (s1 - '0');
        } else {
            return null;
        }

        return LocalTime.of(hour, minute, second);
    }

    public static LocalTime parseLocalTime10(byte[] bytes, int off) {
        if (off + 10 > bytes.length) {
            return null;
        }

        byte c0 = bytes[off];
        byte c1 = bytes[off + 1];
        byte c2 = bytes[off + 2];
        byte c3 = bytes[off + 3];
        byte c4 = bytes[off + 4];
        byte c5 = bytes[off + 5];
        byte c6 = bytes[off + 6];
        byte c7 = bytes[off + 7];
        byte c8 = bytes[off + 8];
        byte c9 = bytes[off + 9];

        byte h0, h1, i0, i1, s0, s1, m0;
        if (c2 == ':' && c5 == ':' && c8 == '.') {
            h0 = c0;
            h1 = c1;
            i0 = c3;
            i1 = c4;
            s0 = c6;
            s1 = c7;
            m0 = c9;
        } else {
            return null;
        }

        int hour;
        if (h0 >= '0' && h0 <= '9'
                && h1 >= '0' && h1 <= '9'
        ) {
            hour = (h0 - '0') * 10 + (h1 - '0');
        } else {
            return null;
        }

        int minute;
        if (i0 >= '0' && i0 <= '9'
                && i1 >= '0' && i1 <= '9'
        ) {
            minute = (i0 - '0') * 10 + (i1 - '0');
        } else {
            return null;
        }

        int second;
        if (s0 >= '0' && s0 <= '9'
                && s1 >= '0' && s1 <= '9'
        ) {
            second = (s0 - '0') * 10 + (s1 - '0');
        } else {
            return null;
        }

        int millis;
        if (m0 >= '0' && m0 <= '9') {
            millis = (m0 - '0') * 100;
            millis *= 1000_000;
        } else {
            return null;
        }

        return LocalTime.of(hour, minute, second, millis);
    }

    public static LocalTime parseLocalTime10(char[] bytes, int off) {
        if (off + 10 > bytes.length) {
            return null;
        }

        char c0 = bytes[off];
        char c1 = bytes[off + 1];
        char c2 = bytes[off + 2];
        char c3 = bytes[off + 3];
        char c4 = bytes[off + 4];
        char c5 = bytes[off + 5];
        char c6 = bytes[off + 6];
        char c7 = bytes[off + 7];
        char c8 = bytes[off + 8];
        char c9 = bytes[off + 9];

        char h0, h1, i0, i1, s0, s1, m0;
        if (c2 == ':' && c5 == ':' && c8 == '.') {
            h0 = c0;
            h1 = c1;
            i0 = c3;
            i1 = c4;
            s0 = c6;
            s1 = c7;
            m0 = c9;
        } else {
            return null;
        }

        int hour;
        if (h0 >= '0' && h0 <= '9'
                && h1 >= '0' && h1 <= '9'
        ) {
            hour = (h0 - '0') * 10 + (h1 - '0');
        } else {
            return null;
        }

        int minute;
        if (i0 >= '0' && i0 <= '9'
                && i1 >= '0' && i1 <= '9'
        ) {
            minute = (i0 - '0') * 10 + (i1 - '0');
        } else {
            return null;
        }

        int second;
        if (s0 >= '0' && s0 <= '9'
                && s1 >= '0' && s1 <= '9'
        ) {
            second = (s0 - '0') * 10 + (s1 - '0');
        } else {
            return null;
        }

        int millis;
        if (m0 >= '0' && m0 <= '9') {
            millis = (m0 - '0') * 100;
            millis *= 1000_000;
        } else {
            return null;
        }

        return LocalTime.of(hour, minute, second, millis);
    }

    public static LocalTime parseLocalTime11(byte[] bytes, int off) {
        if (off + 11 > bytes.length) {
            return null;
        }

        byte c0 = bytes[off];
        byte c1 = bytes[off + 1];
        byte c2 = bytes[off + 2];
        byte c3 = bytes[off + 3];
        byte c4 = bytes[off + 4];
        byte c5 = bytes[off + 5];
        byte c6 = bytes[off + 6];
        byte c7 = bytes[off + 7];
        byte c8 = bytes[off + 8];
        byte c9 = bytes[off + 9];
        byte c10 = bytes[off + 10];

        byte h0, h1, i0, i1, s0, s1, m0, m1;
        if (c2 == ':' && c5 == ':' && c8 == '.') {
            h0 = c0;
            h1 = c1;
            i0 = c3;
            i1 = c4;
            s0 = c6;
            s1 = c7;
            m0 = c9;
            m1 = c10;
        } else {
            return null;
        }

        int hour;
        if (h0 >= '0' && h0 <= '9'
                && h1 >= '0' && h1 <= '9'
        ) {
            hour = (h0 - '0') * 10 + (h1 - '0');
        } else {
            return null;
        }

        int minute;
        if (i0 >= '0' && i0 <= '9'
                && i1 >= '0' && i1 <= '9'
        ) {
            minute = (i0 - '0') * 10 + (i1 - '0');
        } else {
            return null;
        }

        int second;
        if (s0 >= '0' && s0 <= '9'
                && s1 >= '0' && s1 <= '9'
        ) {
            second = (s0 - '0') * 10 + (s1 - '0');
        } else {
            return null;
        }

        int millis;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
        ) {
            millis = (m0 - '0') * 100 + (m1 - '0') * 10;
            millis *= 1000_000;
        } else {
            return null;
        }

        return LocalTime.of(hour, minute, second, millis);
    }

    public static LocalTime parseLocalTime11(char[] bytes, int off) {
        if (off + 11 > bytes.length) {
            return null;
        }

        char c0 = bytes[off];
        char c1 = bytes[off + 1];
        char c2 = bytes[off + 2];
        char c3 = bytes[off + 3];
        char c4 = bytes[off + 4];
        char c5 = bytes[off + 5];
        char c6 = bytes[off + 6];
        char c7 = bytes[off + 7];
        char c8 = bytes[off + 8];
        char c9 = bytes[off + 9];
        char c10 = bytes[off + 10];

        char h0, h1, i0, i1, s0, s1, m0, m1;
        if (c2 == ':' && c5 == ':' && c8 == '.') {
            h0 = c0;
            h1 = c1;
            i0 = c3;
            i1 = c4;
            s0 = c6;
            s1 = c7;
            m0 = c9;
            m1 = c10;
        } else {
            return null;
        }

        int hour;
        if (h0 >= '0' && h0 <= '9'
                && h1 >= '0' && h1 <= '9'
        ) {
            hour = (h0 - '0') * 10 + (h1 - '0');
        } else {
            return null;
        }

        int minute;
        if (i0 >= '0' && i0 <= '9'
                && i1 >= '0' && i1 <= '9'
        ) {
            minute = (i0 - '0') * 10 + (i1 - '0');
        } else {
            return null;
        }

        int second;
        if (s0 >= '0' && s0 <= '9'
                && s1 >= '0' && s1 <= '9'
        ) {
            second = (s0 - '0') * 10 + (s1 - '0');
        } else {
            return null;
        }

        int millis;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9') {
            millis = (m0 - '0') * 100 + (m1 - '0') * 10;
            millis *= 1000_000;
        } else {
            return null;
        }

        return LocalTime.of(hour, minute, second, millis);
    }

    public static LocalTime parseLocalTime12(byte[] bytes, int off) {
        if (off + 12 > bytes.length) {
            return null;
        }

        byte c0 = bytes[off];
        byte c1 = bytes[off + 1];
        byte c2 = bytes[off + 2];
        byte c3 = bytes[off + 3];
        byte c4 = bytes[off + 4];
        byte c5 = bytes[off + 5];
        byte c6 = bytes[off + 6];
        byte c7 = bytes[off + 7];
        byte c8 = bytes[off + 8];
        byte c9 = bytes[off + 9];
        byte c10 = bytes[off + 10];
        byte c11 = bytes[off + 11];

        byte h0, h1, i0, i1, s0, s1, m0, m1, m2;
        if (c2 == ':' && c5 == ':' && c8 == '.') {
            h0 = c0;
            h1 = c1;
            i0 = c3;
            i1 = c4;
            s0 = c6;
            s1 = c7;
            m0 = c9;
            m1 = c10;
            m2 = c11;
        } else {
            return null;
        }

        int hour;
        if (h0 >= '0' && h0 <= '9'
                && h1 >= '0' && h1 <= '9'
        ) {
            hour = (h0 - '0') * 10 + (h1 - '0');
        } else {
            return null;
        }

        int minute;
        if (i0 >= '0' && i0 <= '9'
                && i1 >= '0' && i1 <= '9'
        ) {
            minute = (i0 - '0') * 10 + (i1 - '0');
        } else {
            return null;
        }

        int second;
        if (s0 >= '0' && s0 <= '9'
                && s1 >= '0' && s1 <= '9'
        ) {
            second = (s0 - '0') * 10 + (s1 - '0');
        } else {
            return null;
        }

        int millis;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
                && m2 >= '0' && m2 <= '9'
        ) {
            millis = (m0 - '0') * 100 + (m1 - '0') * 10 + (m2 - '0');
            millis *= 1000_000;
        } else {
            return null;
        }

        return LocalTime.of(hour, minute, second, millis);
    }

    public static LocalTime parseLocalTime12(char[] bytes, int off) {
        if (off + 12 > bytes.length) {
            return null;
        }

        char c0 = bytes[off];
        char c1 = bytes[off + 1];
        char c2 = bytes[off + 2];
        char c3 = bytes[off + 3];
        char c4 = bytes[off + 4];
        char c5 = bytes[off + 5];
        char c6 = bytes[off + 6];
        char c7 = bytes[off + 7];
        char c8 = bytes[off + 8];
        char c9 = bytes[off + 9];
        char c10 = bytes[off + 10];
        char c11 = bytes[off + 11];

        char h0, h1, i0, i1, s0, s1, m0, m1, m2;
        if (c2 == ':' && c5 == ':' && c8 == '.') {
            h0 = c0;
            h1 = c1;
            i0 = c3;
            i1 = c4;
            s0 = c6;
            s1 = c7;
            m0 = c9;
            m1 = c10;
            m2 = c11;
        } else {
            return null;
        }

        int hour;
        if (h0 >= '0' && h0 <= '9'
                && h1 >= '0' && h1 <= '9'
        ) {
            hour = (h0 - '0') * 10 + (h1 - '0');
        } else {
            return null;
        }

        int minute;
        if (i0 >= '0' && i0 <= '9'
                && i1 >= '0' && i1 <= '9'
        ) {
            minute = (i0 - '0') * 10 + (i1 - '0');
        } else {
            return null;
        }

        int second;
        if (s0 >= '0' && s0 <= '9'
                && s1 >= '0' && s1 <= '9'
        ) {
            second = (s0 - '0') * 10 + (s1 - '0');
        } else {
            return null;
        }

        int millis;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
                && m2 >= '0' && m2 <= '9'
        ) {
            millis = (m0 - '0') * 100 + (m1 - '0') * 10 + (m2 - '0');
            millis *= 1000_000;
        } else {
            return null;
        }

        return LocalTime.of(hour, minute, second, millis);
    }

    public static LocalTime parseLocalTime18(byte[] bytes, int off) {
        if (off + 18 > bytes.length) {
            return null;
        }

        byte c0 = bytes[off];
        byte c1 = bytes[off + 1];
        byte c2 = bytes[off + 2];
        byte c3 = bytes[off + 3];
        byte c4 = bytes[off + 4];
        byte c5 = bytes[off + 5];
        byte c6 = bytes[off + 6];
        byte c7 = bytes[off + 7];
        byte c8 = bytes[off + 8];
        byte c9 = bytes[off + 9];
        byte c10 = bytes[off + 10];
        byte c11 = bytes[off + 11];
        byte c12 = bytes[off + 12];
        byte c13 = bytes[off + 13];
        byte c14 = bytes[off + 14];
        byte c15 = bytes[off + 15];
        byte c16 = bytes[off + 16];
        byte c17 = bytes[off + 17];

        byte h0, h1, i0, i1, s0, s1, m0, m1, m2, m3, m4, m5, m6, m7, m8;
        if (c2 == ':' && c5 == ':' && c8 == '.') {
            h0 = c0;
            h1 = c1;
            i0 = c3;
            i1 = c4;
            s0 = c6;
            s1 = c7;
            m0 = c9;
            m1 = c10;
            m2 = c11;
            m3 = c12;
            m4 = c13;
            m5 = c14;
            m6 = c15;
            m7 = c16;
            m8 = c17;
        } else {
            return null;
        }

        int hour;
        if (h0 >= '0' && h0 <= '9'
                && h1 >= '0' && h1 <= '9'
        ) {
            hour = (h0 - '0') * 10 + (h1 - '0');
        } else {
            return null;
        }

        int minute;
        if (i0 >= '0' && i0 <= '9'
                && i1 >= '0' && i1 <= '9'
        ) {
            minute = (i0 - '0') * 10 + (i1 - '0');
        } else {
            return null;
        }

        int second;
        if (s0 >= '0' && s0 <= '9'
                && s1 >= '0' && s1 <= '9'
        ) {
            second = (s0 - '0') * 10 + (s1 - '0');
        } else {
            return null;
        }

        int millis;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
                && m2 >= '0' && m2 <= '9'
                && m3 >= '0' && m3 <= '9'
                && m4 >= '0' && m4 <= '9'
                && m5 >= '0' && m5 <= '9'
                && m6 >= '0' && m6 <= '9'
                && m7 >= '0' && m7 <= '9'
                && m8 >= '0' && m8 <= '9'
        ) {
            millis = (m0 - '0') * 1000_000_00
                    + (m1 - '0') * 1000_000_0
                    + (m2 - '0') * 1000_000
                    + (m3 - '0') * 1000_00
                    + (m4 - '0') * 1000_0
                    + (m5 - '0') * 1000
                    + (m6 - '0') * 100
                    + (m7 - '0') * 10
                    + (m8 - '0');
        } else {
            return null;
        }

        return LocalTime.of(hour, minute, second, millis);
    }

    public static LocalTime parseLocalTime18(char[] bytes, int off) {
        if (off + 18 > bytes.length) {
            return null;
        }

        char c0 = bytes[off];
        char c1 = bytes[off + 1];
        char c2 = bytes[off + 2];
        char c3 = bytes[off + 3];
        char c4 = bytes[off + 4];
        char c5 = bytes[off + 5];
        char c6 = bytes[off + 6];
        char c7 = bytes[off + 7];
        char c8 = bytes[off + 8];
        char c9 = bytes[off + 9];
        char c10 = bytes[off + 10];
        char c11 = bytes[off + 11];
        char c12 = bytes[off + 12];
        char c13 = bytes[off + 13];
        char c14 = bytes[off + 14];
        char c15 = bytes[off + 15];
        char c16 = bytes[off + 16];
        char c17 = bytes[off + 17];

        char h0, h1, i0, i1, s0, s1, m0, m1, m2, m3, m4, m5, m6, m7, m8;
        if (c2 == ':' && c5 == ':' && c8 == '.') {
            h0 = c0;
            h1 = c1;
            i0 = c3;
            i1 = c4;
            s0 = c6;
            s1 = c7;
            m0 = c9;
            m1 = c10;
            m2 = c11;
            m3 = c12;
            m4 = c13;
            m5 = c14;
            m6 = c15;
            m7 = c16;
            m8 = c17;
        } else {
            return null;
        }

        int hour;
        if (h0 >= '0' && h0 <= '9'
                && h1 >= '0' && h1 <= '9'
        ) {
            hour = (h0 - '0') * 10 + (h1 - '0');
        } else {
            return null;
        }

        int minute;
        if (i0 >= '0' && i0 <= '9'
                && i1 >= '0' && i1 <= '9'
        ) {
            minute = (i0 - '0') * 10 + (i1 - '0');
        } else {
            return null;
        }

        int second;
        if (s0 >= '0' && s0 <= '9'
                && s1 >= '0' && s1 <= '9'
        ) {
            second = (s0 - '0') * 10 + (s1 - '0');
        } else {
            return null;
        }

        int millis;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
                && m2 >= '0' && m2 <= '9'
                && m3 >= '0' && m3 <= '9'
                && m4 >= '0' && m4 <= '9'
                && m5 >= '0' && m5 <= '9'
                && m6 >= '0' && m6 <= '9'
                && m7 >= '0' && m7 <= '9'
                && m8 >= '0' && m8 <= '9'
        ) {
            millis = (m0 - '0') * 1000_000_00
                    + (m1 - '0') * 1000_000_0
                    + (m2 - '0') * 1000_000
                    + (m3 - '0') * 1000_00
                    + (m4 - '0') * 1000_0
                    + (m5 - '0') * 1000
                    + (m6 - '0') * 100
                    + (m7 - '0') * 10
                    + (m8 - '0');
        } else {
            return null;
        }

        return LocalTime.of(hour, minute, second, millis);
    }

    public static LocalDateTime parseLocalDateTime(byte[] str, int off, int len) {
        if (str == null || len == 0) {
            return null;
        }

        switch (len) {
            case 4:
                if (str[off] == 'n' && str[off + 1] == 'u' && str[off + 2] == 'l' && str[off + 3] == 'l') {
                    return null;
                }
                String input = new String(str, off, len);
                throw new DateTimeParseException("illegal input " + input, input, 0);
            case 8: {
                LocalDate localDate = parseLocalDate8(str, off);
                if (localDate == null) {
                    return null;
                }
                return LocalDateTime.of(localDate, LocalTime.MIN);
            }
            case 9: {
                LocalDate localDate = parseLocalDate9(str, off);
                if (localDate == null) {
                    return null;
                }
                return LocalDateTime.of(localDate, LocalTime.MIN);
            }
            case 10: {
                LocalDate localDate = parseLocalDate10(str, off);
                if (localDate == null) {
                    return null;
                }
                return LocalDateTime.of(localDate, LocalTime.MIN);
            }
            case 11: {
                return LocalDateTime.of(
                        parseLocalDate11(str, off),
                        LocalTime.MIN
                );
            }
            case 12:
                return parseLocalDateTime12(str, off);
            case 14:
                return parseLocalDateTime14(str, off);
            case 16:
                return parseLocalDateTime16(str, off);
            case 17:
                return parseLocalDateTime17(str, off);
            case 18:
                return parseLocalDateTime18(str, off);
            case 19:
                return parseLocalDateTime19(str, off);
            case 20:
                return parseLocalDateTime20(str, off);
            default:
                return parseLocalDateTimeX(str, off, len);
        }
    }

    public static LocalDate parseLocalDate(String str) {
        if (str == null) {
            return null;
        }

        LocalDate localDate = parseLocalDate(str.toCharArray(), 0, str.length());
        if (localDate == null) {
            switch (str) {
                case "":
                case "null":
                case "00000000":
                case "0000年00月00日":
                case "0000-0-00":
                case "0000-00-00":
                    return null;
                default:
                    throw new DateTimeParseException(str, str, 0);
            }
        }

        return localDate;
    }

    public static LocalDate parseLocalDate(byte[] str, int off, int len) {
        if (str == null || len == 0) {
            return null;
        }

        if (off + len > str.length) {
            String input = new String(str, off, len);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        switch (len) {
            case 8:
                return parseLocalDate8(str, off);
            case 9:
                return parseLocalDate9(str, off);
            case 10:
                return parseLocalDate10(str, off);
            case 11:
                return parseLocalDate11(str, off);
            default:
                if (len == 4 && str[off] == 'n' && str[off + 1] == 'u' && str[off + 2] == 'l' && str[off + 3] == 'l') {
                    return null;
                }
                String input = new String(str, off, len);
                throw new DateTimeParseException("illegal input " + input, input, 0);
        }
    }

    public static LocalDate parseLocalDate(char[] str, int off, int len) {
        if (str == null || len == 0) {
            return null;
        }

        if (off + len > str.length) {
            String input = new String(str, off, len);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        switch (len) {
            case 8:
                return parseLocalDate8(str, off);
            case 9:
                return parseLocalDate9(str, off);
            case 10:
                return parseLocalDate10(str, off);
            case 11:
                return parseLocalDate11(str, off);
            default:
                if (len == 4 && str[off] == 'n' && str[off + 1] == 'u' && str[off + 2] == 'l' && str[off + 3] == 'l') {
                    return null;
                }
                String input = new String(str, off, len);
                throw new DateTimeParseException("illegal input " + input, input, 0);
        }
    }
    /**
     * yyyy-m-d
     * yyyyMMdd
     * d-MMM-yy
     */
    public static LocalDate parseLocalDate8(byte[] str, int off) {
        if (off + 8 > str.length) {
            return null;
        }

        char c0 = (char) str[off];
        char c1 = (char) str[off + 1];
        char c2 = (char) str[off + 2];
        char c3 = (char) str[off + 3];
        char c4 = (char) str[off + 4];
        char c5 = (char) str[off + 5];
        char c6 = (char) str[off + 6];
        char c7 = (char) str[off + 7];

        char y0, y1, y2, y3, m0, m1, d0, d1;
        if (c4 == '-' && c6 == '-') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = '0';
            m1 = c5;

            d0 = '0';
            d1 = c7;
        } else if (c1 == '/' && c3 == '/') {
            m0 = '0';
            m1 = c0;

            d0 = '0';
            d1 = c2;

            y0 = c4;
            y1 = c5;
            y2 = c6;
            y3 = c7;
        } else if (c1 == '-' && c5 == '-') {
            // d-MMM-yy
            d0 = '0';
            d1 = c0;

            int month = month(c2, c3, c4);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                return null;
            }

            y0 = '2';
            y1 = '0';
            y2 = c6;
            y3 = c7;
        } else {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c4;
            m1 = c5;

            d0 = c6;
            d1 = c7;
        }

        int year;
        if (y0 >= '0' && y0 <= '9'
                && y1 >= '0' && y1 <= '9'
                && y2 >= '0' && y2 <= '9'
                && y3 >= '0' && y3 <= '9'
        ) {
            year = (y0 - '0') * 1000 + (y1 - '0') * 100 + (y2 - '0') * 10 + (y3 - '0');
        } else {
            return null;
        }

        int month;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
        ) {
            month = (m0 - '0') * 10 + (m1 - '0');
        } else {
            return null;
        }

        int dom;
        if (d0 >= '0' && d0 <= '9'
                && d1 >= '0' && d1 <= '9'
        ) {
            dom = (d0 - '0') * 10 + (d1 - '0');
        } else {
            return null;
        }

        if (year == 0 && month == 0 && dom == 0) {
            return null;
        }

        return LocalDate.of(year, month, dom);
    }

    /**
     * yyyy-m-d
     * yyyyMMdd
     * d-MMM-yy
     */
    public static LocalDate parseLocalDate8(char[] str, int off) {
        if (off + 8 > str.length) {
            return null;
        }

        char c0 = str[off];
        char c1 = str[off + 1];
        char c2 = str[off + 2];
        char c3 = str[off + 3];
        char c4 = str[off + 4];
        char c5 = str[off + 5];
        char c6 = str[off + 6];
        char c7 = str[off + 7];

        char y0, y1, y2, y3, m0, m1, d0, d1;
        if (c4 == '-' && c6 == '-') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = '0';
            m1 = c5;

            d0 = '0';
            d1 = c7;
        } else if (c1 == '/' && c3 == '/') {
            m0 = '0';
            m1 = c0;

            d0 = '0';
            d1 = c2;

            y0 = c4;
            y1 = c5;
            y2 = c6;
            y3 = c7;
        } else if (c1 == '-' && c5 == '-') {
            // d-MMM-yy
            d0 = '0';
            d1 = c0;

            int month = month(c2, c3, c4);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                return null;
            }

            y0 = '2';
            y1 = '0';
            y2 = c6;
            y3 = c7;
        } else {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c4;
            m1 = c5;

            d0 = c6;
            d1 = c7;
        }

        int year;
        if (y0 >= '0' && y0 <= '9'
                && y1 >= '0' && y1 <= '9'
                && y2 >= '0' && y2 <= '9'
                && y3 >= '0' && y3 <= '9'
        ) {
            year = (y0 - '0') * 1000 + (y1 - '0') * 100 + (y2 - '0') * 10 + (y3 - '0');
        } else {
            return null;
        }

        int month;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
        ) {
            month = (m0 - '0') * 10 + (m1 - '0');
        } else {
            return null;
        }

        int dom;
        if (d0 >= '0' && d0 <= '9'
                && d1 >= '0' && d1 <= '9'
        ) {
            dom = (d0 - '0') * 10 + (d1 - '0');
        } else {
            return null;
        }

        if (year == 0 && month == 0 && dom == 0) {
            return null;
        }

        return LocalDate.of(year, month, dom);
    }

    /**
     * yyyy-MM-d
     * yyyy-M-dd
     * dd-MMM-yy
     */
    public static LocalDate parseLocalDate9(byte[] str, int off) {
        if (off + 9 > str.length) {
            return null;
        }

        char c0 = (char) str[off];
        char c1 = (char) str[off + 1];
        char c2 = (char) str[off + 2];
        char c3 = (char) str[off + 3];
        char c4 = (char) str[off + 4];
        char c5 = (char) str[off + 5];
        char c6 = (char) str[off + 6];
        char c7 = (char) str[off + 7];
        char c8 = (char) str[off + 8];

        char y0, y1, y2, y3, m0, m1, d0, d1;
        if (c4 == '-' && c7 == '-') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = '0';
            d1 = c8;
        } else if (c4 == '-' && c6 == '-') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = '0';
            m1 = c5;

            d0 = c7;
            d1 = c8;
        } else if (c4 == '/' && c7 == '/') { // tw : yyyy/mm/d
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = '0';
            d1 = c8;
        } else if (c4 == '/' && c6 == '/') { // tw : yyyy/m/dd
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = '0';
            m1 = c5;

            d0 = c7;
            d1 = c8;
        } else if (c1 == '.' && c4 == '.') {
            d0 = '0';
            d1 = c0;

            m0 = c2;
            m1 = c3;

            y0 = c5;
            y1 = c6;
            y2 = c7;
            y3 = c8;
        } else if (c2 == '.' && c4 == '.') {
            d0 = c0;
            d1 = c1;

            m0 = '0';
            m1 = c3;

            y0 = c5;
            y1 = c6;
            y2 = c7;
            y3 = c8;
        } else if (c1 == '-' && c4 == '-') {
            d0 = '0';
            d1 = c0;

            m0 = c2;
            m1 = c3;

            y0 = c5;
            y1 = c6;
            y2 = c7;
            y3 = c8;
        } else if (c2 == '-' && c4 == '-') {
            d0 = c0;
            d1 = c1;

            m0 = '0';
            m1 = c3;

            y0 = c5;
            y1 = c6;
            y2 = c7;
            y3 = c8;
        } else if (c2 == '-' && c6 == '-') {
            // dd-MMM-yy
            d0 = c0;
            d1 = c1;

            int month = month(c3, c4, c5);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                return null;
            }

            y0 = '2';
            y1 = '0';
            y2 = c7;
            y3 = c8;
        } else if (c1 == '/' && c4 == '/') {
            // M/dd/dddd
            m0 = '0';
            m1 = c0;

            d0 = c2;
            d1 = c3;

            y0 = c5;
            y1 = c6;
            y2 = c7;
            y3 = c8;
        } else if (c2 == '/' && c4 == '/') {
            // MM/d/dddd
            m0 = c0;
            m1 = c1;

            d0 = '0';
            d1 = c3;

            y0 = c5;
            y1 = c6;
            y2 = c7;
            y3 = c8;
        } else {
            return null;
        }

        int year;
        if (y0 >= '0' && y0 <= '9'
                && y1 >= '0' && y1 <= '9'
                && y2 >= '0' && y2 <= '9'
                && y3 >= '0' && y3 <= '9'
        ) {
            year = (y0 - '0') * 1000 + (y1 - '0') * 100 + (y2 - '0') * 10 + (y3 - '0');
        } else {
            return null;
        }

        int month;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
        ) {
            month = (m0 - '0') * 10 + (m1 - '0');
        } else {
            return null;
        }

        int dom;
        if (d0 >= '0' && d0 <= '9'
                && d1 >= '0' && d1 <= '9'
        ) {
            dom = (d0 - '0') * 10 + (d1 - '0');
        } else {
            return null;
        }

        if (year == 0 && month == 0 && dom == 0) {
            return null;
        }

        return LocalDate.of(year, month, dom);
    }

    /**
     * yyyy-MM-d
     * yyyy-M-dd
     * dd-MMM-yy
     */
    public static LocalDate parseLocalDate9(char[] str, int off) {
        if (off + 9 > str.length) {
            return null;
        }

        char c0 = str[off];
        char c1 = str[off + 1];
        char c2 = str[off + 2];
        char c3 = str[off + 3];
        char c4 = str[off + 4];
        char c5 = str[off + 5];
        char c6 = str[off + 6];
        char c7 = str[off + 7];
        char c8 = str[off + 8];

        char y0, y1, y2, y3, m0, m1, d0, d1;
        if (c4 == '-' && c7 == '-') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = '0';
            d1 = c8;
        } else if (c4 == '-' && c6 == '-') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = '0';
            m1 = c5;

            d0 = c7;
            d1 = c8;
        } else if (c4 == '/' && c7 == '/') { // tw : yyyy/mm/d
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = '0';
            d1 = c8;
        } else if (c4 == '/' && c6 == '/') { // tw : yyyy/m/dd
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = '0';
            m1 = c5;

            d0 = c7;
            d1 = c8;
        } else if (c1 == '.' && c4 == '.') {
            d0 = '0';
            d1 = c0;

            m0 = c2;
            m1 = c3;

            y0 = c5;
            y1 = c6;
            y2 = c7;
            y3 = c8;
        } else if (c2 == '.' && c4 == '.') {
            d0 = c0;
            d1 = c1;

            m0 = '0';
            m1 = c3;

            y0 = c5;
            y1 = c6;
            y2 = c7;
            y3 = c8;
        } else if (c1 == '-' && c4 == '-') {
            d0 = '0';
            d1 = c0;

            m0 = c2;
            m1 = c3;

            y0 = c5;
            y1 = c6;
            y2 = c7;
            y3 = c8;
        } else if (c2 == '-' && c4 == '-') {
            d0 = c0;
            d1 = c1;

            m0 = '0';
            m1 = c3;

            y0 = c5;
            y1 = c6;
            y2 = c7;
            y3 = c8;
        } else if (c4 == '年' && c6 == '月' && c8 == '日') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = '0';
            m1 = c5;

            d0 = '0';
            d1 = c7;
        } else if (c4 == '년' && c6 == '월' && c8 == '일') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = '0';
            m1 = c5;

            d0 = '0';
            d1 = c7;
        } else if (c2 == '-' && c6 == '-') {
            // dd-MMM-yy
            d0 = c0;
            d1 = c1;

            int month = month(c3, c4, c5);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                return null;
            }

            y0 = '2';
            y1 = '0';
            y2 = c7;
            y3 = c8;
        } else if (c1 == '/' && c4 == '/') {
            // M/dd/dddd
            m0 = '0';
            m1 = c0;

            d0 = c2;
            d1 = c3;

            y0 = c5;
            y1 = c6;
            y2 = c7;
            y3 = c8;
        } else if (c2 == '/' && c4 == '/') {
            // MM/d/dddd
            m0 = c0;
            m1 = c1;

            d0 = '0';
            d1 = c3;

            y0 = c5;
            y1 = c6;
            y2 = c7;
            y3 = c8;
        } else {
            return null;
        }

        int year;
        if (y0 >= '0' && y0 <= '9'
                && y1 >= '0' && y1 <= '9'
                && y2 >= '0' && y2 <= '9'
                && y3 >= '0' && y3 <= '9'
        ) {
            year = (y0 - '0') * 1000 + (y1 - '0') * 100 + (y2 - '0') * 10 + (y3 - '0');
        } else {
            return null;
        }

        int month;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
        ) {
            month = (m0 - '0') * 10 + (m1 - '0');
        } else {
            return null;
        }

        int dom;
        if (d0 >= '0' && d0 <= '9'
                && d1 >= '0' && d1 <= '9'
        ) {
            dom = (d0 - '0') * 10 + (d1 - '0');
        } else {
            return null;
        }

        if (year == 0 && month == 0 && dom == 0) {
            return null;
        }

        return LocalDate.of(year, month, dom);
    }

    /**
     * yyyy-MM-dd
     * yyyy/MM/dd
     * MM/dd/yyyy
     * dd.MM.yyyy
     * yyyy年M月dd日
     * yyyy年MM月d日
     * yyyy MMM d
     */
    public static LocalDate parseLocalDate10(byte[] str, int off) {
        if (off + 10 > str.length) {
            return null;
        }

        char c0 = (char) str[off];
        char c1 = (char) str[off + 1];
        char c2 = (char) str[off + 2];
        char c3 = (char) str[off + 3];
        char c4 = (char) str[off + 4];
        char c5 = (char) str[off + 5];
        char c6 = (char) str[off + 6];
        char c7 = (char) str[off + 7];
        char c8 = (char) str[off + 8];
        char c9 = (char) str[off + 9];

        char y0, y1, y2, y3, m0, m1, d0, d1;
        if (c4 == '-' && c7 == '-') {
            // yyyy-MM-dd
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;
        } else if (c4 == '/' && c7 == '/') {
            // tw : yyyy/mm/dd
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;
        } else if (c2 == '.' && c5 == '.') {
            // dd.MM.yyyy
            d0 = c0;
            d1 = c1;

            m0 = c3;
            m1 = c4;

            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;
        } else if (c2 == '-' && c5 == '-') {
            // dd-MM-yyyy
            d0 = c0;
            d1 = c1;

            m0 = c3;
            m1 = c4;

            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;
        } else if (c2 == '/' && c5 == '/') {
            // MM/dd/yyyy
            m0 = c0;
            m1 = c1;

            d0 = c3;
            d1 = c4;

            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;
        } else if (c1 == ' ' && c5 == ' ') {
            // yyyy MMM d
            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;

            int month = month(c2, c3, c4);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                return null;
            }

            d0 = '0';
            d1 = c0;
        } else {
            return null;
        }

        int year;
        if (y0 >= '0' && y0 <= '9'
                && y1 >= '0' && y1 <= '9'
                && y2 >= '0' && y2 <= '9'
                && y3 >= '0' && y3 <= '9'
        ) {
            year = (y0 - '0') * 1000 + (y1 - '0') * 100 + (y2 - '0') * 10 + (y3 - '0');
        } else {
            return null;
        }

        int month;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
        ) {
            month = (m0 - '0') * 10 + (m1 - '0');
        } else {
            return null;
        }

        int dom;
        if (d0 >= '0' && d0 <= '9'
                && d1 >= '0' && d1 <= '9'
        ) {
            dom = (d0 - '0') * 10 + (d1 - '0');
        } else {
            return null;
        }

        if (year == 0 && month == 0 && dom == 0) {
            return null;
        }

        return LocalDate.of(year, month, dom);
    }

    /**
     * yyyy-MM-dd
     * yyyy/MM/dd
     * MM/dd/yyyy
     * dd.MM.yyyy
     * yyyy年M月dd日
     * yyyy年MM月d日
     * yyyy MMM d
     */
    public static LocalDate parseLocalDate10(char[] str, int off) {
        if (off + 10 > str.length) {
            return null;
        }

        char c0 = str[off];
        char c1 = str[off + 1];
        char c2 = str[off + 2];
        char c3 = str[off + 3];
        char c4 = str[off + 4];
        char c5 = str[off + 5];
        char c6 = str[off + 6];
        char c7 = str[off + 7];
        char c8 = str[off + 8];
        char c9 = str[off + 9];

        char y0, y1, y2, y3, m0, m1, d0, d1;
        if (c4 == '-' && c7 == '-') {
            // yyyy-MM-dd
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;
        } else if (c4 == '/' && c7 == '/') {
            // tw : yyyy/mm/dd
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;
        } else if (c2 == '.' && c5 == '.') {
            // dd.MM.yyyy
            d0 = c0;
            d1 = c1;

            m0 = c3;
            m1 = c4;

            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;
        } else if (c2 == '-' && c5 == '-') {
            // dd-MM-yyyy
            d0 = c0;
            d1 = c1;

            m0 = c3;
            m1 = c4;

            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;
        } else if (c2 == '/' && c5 == '/') {
            // MM/dd/yyyy
            m0 = c0;
            m1 = c1;

            d0 = c3;
            d1 = c4;

            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;
        } else if (c4 == '年' && c6 == '月' && c9 == '日') {
            // yyyy年M月dd日
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = '0';
            m1 = c5;

            d0 = c7;
            d1 = c8;
        } else if (c4 == '년' && c6 == '월' && c9 == '일') {
            // yyyy년M월dd일
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = '0';
            m1 = c5;

            d0 = c7;
            d1 = c8;
        } else if (c4 == '年' && c7 == '月' && c9 == '日') {
            // // yyyy年MM月d日
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = '0';
            d1 = c8;
        } else if (c4 == '년' && c7 == '월' && c9 == '일') {
            // yyyy년MM월d일
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = '0';
            d1 = c8;
        } else if (c1 == ' ' && c5 == ' ') {
            // yyyy MMM d
            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;

            int month = month(c2, c3, c4);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                return null;
            }

            d0 = '0';
            d1 = c0;
        } else {
            return null;
        }

        int year;
        if (y0 >= '0' && y0 <= '9'
                && y1 >= '0' && y1 <= '9'
                && y2 >= '0' && y2 <= '9'
                && y3 >= '0' && y3 <= '9'
        ) {
            year = (y0 - '0') * 1000 + (y1 - '0') * 100 + (y2 - '0') * 10 + (y3 - '0');
        } else {
            return null;
        }

        int month;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
        ) {
            month = (m0 - '0') * 10 + (m1 - '0');
        } else {
            return null;
        }

        int dom;
        if (d0 >= '0' && d0 <= '9'
                && d1 >= '0' && d1 <= '9'
        ) {
            dom = (d0 - '0') * 10 + (d1 - '0');
        } else {
            return null;
        }

        if (year == 0 && month == 0 && dom == 0) {
            return null;
        }

        return LocalDate.of(year, month, dom);
    }

    /**
     * yyyy年MM月dd日
     * yyyy년MM월dd일
     */
    public static LocalDate parseLocalDate11(char[] str, int off) {
        if (off + 11 > str.length) {
            return null;
        }

        char c0 = str[off];
        char c1 = str[off + 1];
        char c2 = str[off + 2];
        char c3 = str[off + 3];
        char c4 = str[off + 4];
        char c5 = str[off + 5];
        char c6 = str[off + 6];
        char c7 = str[off + 7];
        char c8 = str[off + 8];
        char c9 = str[off + 9];
        char c10 = str[off + 10];

        char y0, y1, y2, y3, m0, m1, d0, d1;
        if (c4 == '年' && c7 == '月' && c10 == '日') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;
        } else if (c4 == '-' && c7 == '-' && c10 == 'Z') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;
        } else if (c4 == '년' && c7 == '월' && c10 == '일') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;
        } else if (c2 == ' ' && c6 == ' ') {
            y0 = c7;
            y1 = c8;
            y2 = c9;
            y3 = c10;

            int month = month(c3, c4, c5);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                return null;
            }

            d0 = c0;
            d1 = c1;
        } else {
            return null;
        }

        int year;
        if (y0 >= '0' && y0 <= '9'
                && y1 >= '0' && y1 <= '9'
                && y2 >= '0' && y2 <= '9'
                && y3 >= '0' && y3 <= '9'
        ) {
            year = (y0 - '0') * 1000 + (y1 - '0') * 100 + (y2 - '0') * 10 + (y3 - '0');
        } else {
            return null;
        }

        int month;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
        ) {
            month = (m0 - '0') * 10 + (m1 - '0');
        } else {
            return null;
        }

        int dom;
        if (d0 >= '0' && d0 <= '9'
                && d1 >= '0' && d1 <= '9'
        ) {
            dom = (d0 - '0') * 10 + (d1 - '0');
        } else {
            return null;
        }

        if (year == 0 && month == 0 && dom == 0) {
            return null;
        }

        return LocalDate.of(year, month, dom);
    }

    /**
     *
     */
    public static LocalDate parseLocalDate11(byte[] str, int off) {
        if (off + 11 > str.length) {
            return null;
        }

        char c0 = (char) str[off];
        char c1 = (char) str[off + 1];
        char c2 = (char) str[off + 2];
        char c3 = (char) str[off + 3];
        char c4 = (char) str[off + 4];
        char c5 = (char) str[off + 5];
        char c6 = (char) str[off + 6];
        char c7 = (char) str[off + 7];
        char c8 = (char) str[off + 8];
        char c9 = (char) str[off + 9];
        char c10 = (char) str[off + 10];

        char y0, y1, y2, y3, m0, m1, d0, d1;
        if (c4 == '-' && c7 == '-' && c10 == 'Z') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;
        } else if (c2 == ' ' && c6 == ' ') {
            y0 = c7;
            y1 = c8;
            y2 = c9;
            y3 = c10;

            int month = month(c3, c4, c5);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                return null;
            }

            d0 = c0;
            d1 = c1;
        } else {
            return null;
        }

        int year;
        if (y0 >= '0' && y0 <= '9'
                && y1 >= '0' && y1 <= '9'
                && y2 >= '0' && y2 <= '9'
                && y3 >= '0' && y3 <= '9'
        ) {
            year = (y0 - '0') * 1000 + (y1 - '0') * 100 + (y2 - '0') * 10 + (y3 - '0');
        } else {
            return null;
        }

        int month;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
        ) {
            month = (m0 - '0') * 10 + (m1 - '0');
        } else {
            return null;
        }

        int dom;
        if (d0 >= '0' && d0 <= '9'
                && d1 >= '0' && d1 <= '9'
        ) {
            dom = (d0 - '0') * 10 + (d1 - '0');
        } else {
            return null;
        }

        if (year == 0 && month == 0 && dom == 0) {
            return null;
        }

        return LocalDate.of(year, month, dom);
    }

    public static LocalDateTime parseLocalDateTime12(char[] str, int off) {
        if (off + 12 > str.length) {
            String input = new String(str, off, str.length - off);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        char y0 = str[off];
        char y1 = str[off + 1];
        char y2 = str[off + 2];
        char y3 = str[off + 3];
        char m0 = str[off + 4];
        char m1 = str[off + 5];
        char d0 = str[off + 6];
        char d1 = str[off + 7];
        char h0 = str[off + 8];
        char h1 = str[off + 9];
        char i0 = str[off + 10];
        char i1 = str[off + 11];

        int year;
        if (y0 >= '0' && y0 <= '9'
                && y1 >= '0' && y1 <= '9'
                && y2 >= '0' && y2 <= '9'
                && y3 >= '0' && y3 <= '9'
        ) {
            year = (y0 - '0') * 1000 + (y1 - '0') * 100 + (y2 - '0') * 10 + (y3 - '0');
        } else {
            String input = new String(str, off, off + 12);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int month;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
        ) {
            month = (m0 - '0') * 10 + (m1 - '0');
        } else {
            String input = new String(str, off, off + 12);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int dom;
        if (d0 >= '0' && d0 <= '9'
                && d1 >= '0' && d1 <= '9'
        ) {
            dom = (d0 - '0') * 10 + (d1 - '0');
        } else {
            String input = new String(str, off, off + 12);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int hour;
        if (h0 >= '0' && h0 <= '9'
                && h1 >= '0' && h1 <= '9'
        ) {
            hour = (h0 - '0') * 10 + (h1 - '0');
        } else {
            String input = new String(str, off, off + 12);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int minute;
        if (i0 >= '0' && i0 <= '9'
                && i1 >= '0' && i1 <= '9'
        ) {
            minute = (i0 - '0') * 10 + (i1 - '0');
        } else {
            String input = new String(str, off, off + 12);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        if (year == 0 && month == 0 && dom == 0 && hour == 0 && minute == 0) {
            return null;
        }

        return LocalDateTime.of(year, month, dom, hour, minute, 0);
    }

    /**
     * parseLocalDateTime use format 'yyyyMMddHHmm'
     */
    public static LocalDateTime parseLocalDateTime12(byte[] str, int off) {
        if (off + 12 > str.length) {
            String input = new String(str, off, str.length - off);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        char y0 = (char) str[off];
        char y1 = (char) str[off + 1];
        char y2 = (char) str[off + 2];
        char y3 = (char) str[off + 3];
        char m0 = (char) str[off + 4];
        char m1 = (char) str[off + 5];
        char d0 = (char) str[off + 6];
        char d1 = (char) str[off + 7];
        char h0 = (char) str[off + 8];
        char h1 = (char) str[off + 9];
        char i0 = (char) str[off + 10];
        char i1 = (char) str[off + 11];

        int year;
        if (y0 >= '0' && y0 <= '9'
                && y1 >= '0' && y1 <= '9'
                && y2 >= '0' && y2 <= '9'
                && y3 >= '0' && y3 <= '9'
        ) {
            year = (y0 - '0') * 1000 + (y1 - '0') * 100 + (y2 - '0') * 10 + (y3 - '0');
        } else {
            String input = new String(str, off, off + 12);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int month;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
        ) {
            month = (m0 - '0') * 10 + (m1 - '0');
        } else {
            String input = new String(str, off, off + 12);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int dom;
        if (d0 >= '0' && d0 <= '9'
                && d1 >= '0' && d1 <= '9'
        ) {
            dom = (d0 - '0') * 10 + (d1 - '0');
        } else {
            String input = new String(str, off, off + 12);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int hour;
        if (h0 >= '0' && h0 <= '9'
                && h1 >= '0' && h1 <= '9'
        ) {
            hour = (h0 - '0') * 10 + (h1 - '0');
        } else {
            String input = new String(str, off, off + 12);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int minute;
        if (i0 >= '0' && i0 <= '9'
                && i1 >= '0' && i1 <= '9'
        ) {
            minute = (i0 - '0') * 10 + (i1 - '0');
        } else {
            String input = new String(str, off, off + 12);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        if (year == 0 && month == 0 && dom == 0 && hour == 0 && minute == 0) {
            return null;
        }

        return LocalDateTime.of(year, month, dom, hour, minute, 0);
    }

    /**
     * yyyyMMddHHmmss
     */
    public static LocalDateTime parseLocalDateTime14(char[] str, int off) {
        if (off + 14 > str.length) {
            return null;
        }

        char y0 = str[off];
        char y1 = str[off + 1];
        char y2 = str[off + 2];
        char y3 = str[off + 3];
        char m0 = str[off + 4];
        char m1 = str[off + 5];
        char d0 = str[off + 6];
        char d1 = str[off + 7];
        char h0 = str[off + 8];
        char h1 = str[off + 9];
        char i0 = str[off + 10];
        char i1 = str[off + 11];
        char s0 = str[off + 12];
        char s1 = str[off + 13];

        int year;
        if (y0 >= '0' && y0 <= '9'
                && y1 >= '0' && y1 <= '9'
                && y2 >= '0' && y2 <= '9'
                && y3 >= '0' && y3 <= '9'
        ) {
            year = (y0 - '0') * 1000 + (y1 - '0') * 100 + (y2 - '0') * 10 + (y3 - '0');
        } else {
            return null;
        }

        int month;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
        ) {
            month = (m0 - '0') * 10 + (m1 - '0');
        } else {
            return null;
        }

        int dom;
        if (d0 >= '0' && d0 <= '9'
                && d1 >= '0' && d1 <= '9'
        ) {
            dom = (d0 - '0') * 10 + (d1 - '0');
        } else {
            return null;
        }

        int hour;
        if (h0 >= '0' && h0 <= '9'
                && h1 >= '0' && h1 <= '9'
        ) {
            hour = (h0 - '0') * 10 + (h1 - '0');
        } else {
            return null;
        }

        int minute;
        if (i0 >= '0' && i0 <= '9'
                && i1 >= '0' && i1 <= '9'
        ) {
            minute = (i0 - '0') * 10 + (i1 - '0');
        } else {
            return null;
        }

        int second;
        if (s0 >= '0' && s0 <= '9'
                && s1 >= '0' && s1 <= '9'
        ) {
            second = (s0 - '0') * 10 + (s1 - '0');
        } else {
            return null;
        }

        return LocalDateTime.of(year, month, dom, hour, minute, second);
    }

    /**
     * yyyyMMddHHmmss
     */
    public static LocalDateTime parseLocalDateTime14(byte[] str, int off) {
        if (off + 14 > str.length) {
            return null;
        }

        char y0 = (char) str[off];
        char y1 = (char) str[off + 1];
        char y2 = (char) str[off + 2];
        char y3 = (char) str[off + 3];
        char m0 = (char) str[off + 4];
        char m1 = (char) str[off + 5];
        char d0 = (char) str[off + 6];
        char d1 = (char) str[off + 7];
        char h0 = (char) str[off + 8];
        char h1 = (char) str[off + 9];
        char i0 = (char) str[off + 10];
        char i1 = (char) str[off + 11];
        char s0 = (char) str[off + 12];
        char s1 = (char) str[off + 13];

        int year;
        if (y0 >= '0' && y0 <= '9'
                && y1 >= '0' && y1 <= '9'
                && y2 >= '0' && y2 <= '9'
                && y3 >= '0' && y3 <= '9'
        ) {
            year = (y0 - '0') * 1000 + (y1 - '0') * 100 + (y2 - '0') * 10 + (y3 - '0');
        } else {
            return null;
        }

        int month;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
        ) {
            month = (m0 - '0') * 10 + (m1 - '0');
        } else {
            return null;
        }

        int dom;
        if (d0 >= '0' && d0 <= '9'
                && d1 >= '0' && d1 <= '9'
        ) {
            dom = (d0 - '0') * 10 + (d1 - '0');
        } else {
            return null;
        }

        int hour;
        if (h0 >= '0' && h0 <= '9'
                && h1 >= '0' && h1 <= '9'
        ) {
            hour = (h0 - '0') * 10 + (h1 - '0');
        } else {
            return null;
        }

        int minute;
        if (i0 >= '0' && i0 <= '9'
                && i1 >= '0' && i1 <= '9'
        ) {
            minute = (i0 - '0') * 10 + (i1 - '0');
        } else {
            return null;
        }

        int second;
        if (s0 >= '0' && s0 <= '9'
                && s1 >= '0' && s1 <= '9'
        ) {
            second = (s0 - '0') * 10 + (s1 - '0');
        } else {
            return null;
        }

        return LocalDateTime.of(year, month, dom, hour, minute, second);
    }

    /**
     * yyyy-MM-ddTHH:mm
     * yyyy-MM-dd HH:mm
     * yyyyMMddTHHmmssZ
     * yyyy-MM-ddTH:m:s
     * yyyy-MM-dd H:m:s
     */
    public static LocalDateTime parseLocalDateTime16(char[] str, int off) {
        if (off + 16 > str.length) {
            return null;
        }

        char c0 = str[off];
        char c1 = str[off + 1];
        char c2 = str[off + 2];
        char c3 = str[off + 3];
        char c4 = str[off + 4];
        char c5 = str[off + 5];
        char c6 = str[off + 6];
        char c7 = str[off + 7];
        char c8 = str[off + 8];
        char c9 = str[off + 9];
        char c10 = str[off + 10];
        char c11 = str[off + 11];
        char c12 = str[off + 12];
        char c13 = str[off + 13];
        char c14 = str[off + 14];
        char c15 = str[off + 15];

        char y0, y1, y2, y3, m0, m1, d0, d1, h0, h1, i0, i1, s0 = '0', s1 = '0';
        if (c4 == '-' && c7 == '-' && (c10 == 'T' || c10 == ' ') && c13 == ':') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;
        } else if (c8 == 'T' && c15 == 'Z') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;
            m0 = c4;
            m1 = c5;
            d0 = c6;
            d1 = c7;
            h0 = c9;
            h1 = c10;
            i0 = c11;
            i1 = c12;
            s0 = c13;
            s1 = c14;
        } else if (c4 == '-' && c7 == '-' && (c10 == 'T' || c10 == ' ') && c12 == ':' && c14 == ':') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;

            h0 = '0';
            h1 = c11;

            i0 = '0';
            i1 = c13;

            s1 = c15;
        } else if (c1 == ' ' && c5 == ' ' && c10 == ' ' && c13 == ':') {
            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;

            int month = month(c2, c3, c4);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                return null;
            }

            d0 = '0';
            d1 = c0;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;
        } else if (c1 == ' ' && c5 == ' ' && c10 == ' ' && c12 == ':' && c14 == ':') {
            // d MMM yyyy H:m:ss
            // 6 DEC 2020 2:3:14
            d0 = '0';
            d1 = c0;

            int month = month(c2, c3, c4);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                return null;
            }

            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;

            h0 = '0';
            h1 = c11;

            i0 = '0';
            i1 = c13;

            s1 = c15;
        } else {
            return null;
        }

        int year;
        if (y0 >= '0' && y0 <= '9'
                && y1 >= '0' && y1 <= '9'
                && y2 >= '0' && y2 <= '9'
                && y3 >= '0' && y3 <= '9'
        ) {
            year = (y0 - '0') * 1000 + (y1 - '0') * 100 + (y2 - '0') * 10 + (y3 - '0');
        } else {
            return null;
        }

        int month;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
        ) {
            month = (m0 - '0') * 10 + (m1 - '0');
        } else {
            return null;
        }

        int dom;
        if (d0 >= '0' && d0 <= '9'
                && d1 >= '0' && d1 <= '9'
        ) {
            dom = (d0 - '0') * 10 + (d1 - '0');
        } else {
            return null;
        }

        int hour;
        if (h0 >= '0' && h0 <= '9'
                && h1 >= '0' && h1 <= '9'
        ) {
            hour = (h0 - '0') * 10 + (h1 - '0');
        } else {
            return null;
        }

        int minute;
        if (i0 >= '0' && i0 <= '9'
                && i1 >= '0' && i1 <= '9'
        ) {
            minute = (i0 - '0') * 10 + (i1 - '0');
        } else {
            return null;
        }

        int second;
        if (s0 >= '0' && s0 <= '9'
                && s1 >= '0' && s1 <= '9'
        ) {
            second = (s0 - '0') * 10 + (s1 - '0');
        } else {
            return null;
        }

        return LocalDateTime.of(year, month, dom, hour, minute, second);
    }

    /**
     * yyyy-MM-ddTHH:mm
     * yyyy-MM-dd HH:mm
     * yyyyMMddTHHmmssZ
     * yyyy-MM-ddTH:m:s
     * yyyy-MM-dd H:m:s
     */
    public static LocalDateTime parseLocalDateTime16(byte[] str, int off) {
        if (off + 16 > str.length) {
            return null;
        }

        byte c0 = str[off];
        byte c1 = str[off + 1];
        byte c2 = str[off + 2];
        byte c3 = str[off + 3];
        byte c4 = str[off + 4];
        byte c5 = str[off + 5];
        byte c6 = str[off + 6];
        byte c7 = str[off + 7];
        byte c8 = str[off + 8];
        byte c9 = str[off + 9];
        byte c10 = str[off + 10];
        byte c11 = str[off + 11];
        byte c12 = str[off + 12];
        byte c13 = str[off + 13];
        byte c14 = str[off + 14];
        byte c15 = str[off + 15];

        char y0, y1, y2, y3, m0, m1, d0, d1, h0, h1, i0, i1, s0 = '0', s1 = '0';
        if (c4 == '-' && c7 == '-' && (c10 == 'T' || c10 == ' ') && c13 == ':') {
            y0 = (char) c0;
            y1 = (char) c1;
            y2 = (char) c2;
            y3 = (char) c3;

            m0 = (char) c5;
            m1 = (char) c6;

            d0 = (char) c8;
            d1 = (char) c9;

            h0 = (char) c11;
            h1 = (char) c12;

            i0 = (char) c14;
            i1 = (char) c15;
        } else if (c8 == 'T' && c15 == 'Z') {
            y0 = (char) c0;
            y1 = (char) c1;
            y2 = (char) c2;
            y3 = (char) c3;
            m0 = (char) c4;
            m1 = (char) c5;
            d0 = (char) c6;
            d1 = (char) c7;
            h0 = (char) c9;
            h1 = (char) c10;
            i0 = (char) c11;
            i1 = (char) c12;
            s0 = (char) c13;
            s1 = (char) c14;
        } else if (c4 == -27 && c5 == -71 && c6 == -76 // 年
                && c8 == -26 && c9 == -100 && c10 == -120 // 月
                && c13 == -26 && c14 == -105 && c15 == -91 // 日
        ) {
            y0 = (char) c0;
            y1 = (char) c1;
            y2 = (char) c2;
            y3 = (char) c3;

            m0 = '0';
            m1 = (char) c7;

            d0 = (char) c11;
            d1 = (char) c12;

            h0 = '0';
            h1 = '0';

            i0 = '0';
            i1 = '0';
        } else if (c4 == -27 && c5 == -71 && c6 == -76 // 年
                && c9 == -26 && c10 == -100 && c11 == -120 // 月
                && c13 == -26 && c14 == -105 && c15 == -91 // 日
        ) {
            y0 = (char) c0;
            y1 = (char) c1;
            y2 = (char) c2;
            y3 = (char) c3;

            m0 = (char) c7;
            m1 = (char) c8;

            d0 = '0';
            d1 = (char) c12;

            h0 = '0';
            h1 = '0';

            i0 = '0';
            i1 = '0';
        } else if (c4 == '-' && c7 == '-' && (c10 == 'T' || c10 == ' ') && c12 == ':' && c14 == ':') {
            y0 = (char) c0;
            y1 = (char) c1;
            y2 = (char) c2;
            y3 = (char) c3;

            m0 = (char) c5;
            m1 = (char) c6;

            d0 = (char) c8;
            d1 = (char) c9;

            h0 = '0';
            h1 = (char) c11;

            i0 = '0';
            i1 = (char) c13;

            s1 = (char) c15;
        } else if (c1 == ' ' && c5 == ' ' && c10 == ' ' && c13 == ':') {
            y0 = (char) c6;
            y1 = (char) c7;
            y2 = (char) c8;
            y3 = (char) c9;

            int month = month((char) c2, (char) c3, (char) c4);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                return null;
            }

            d0 = '0';
            d1 = (char) c0;

            h0 = (char) c11;
            h1 = (char) c12;

            i0 = (char) c14;
            i1 = (char) c15;
        } else if (c1 == ' ' && c5 == ' ' && c10 == ' ' && c12 == ':' && c14 == ':') {
            // d MMM yyyy H:m:ss
            // 6 DEC 2020 2:3:14
            d0 = '0';
            d1 = (char) c0;

            int month = month((char) c2, (char) c3, (char) c4);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                return null;
            }

            y0 = (char) c6;
            y1 = (char) c7;
            y2 = (char) c8;
            y3 = (char) c9;

            h0 = '0';
            h1 = (char) c11;

            i0 = '0';
            i1 = (char) c13;

            s1 = (char) c15;
        } else {
            return null;
        }

        int year;
        if (y0 >= '0' && y0 <= '9'
                && y1 >= '0' && y1 <= '9'
                && y2 >= '0' && y2 <= '9'
                && y3 >= '0' && y3 <= '9'
        ) {
            year = (y0 - '0') * 1000 + (y1 - '0') * 100 + (y2 - '0') * 10 + (y3 - '0');
        } else {
            return null;
        }

        int month;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
        ) {
            month = (m0 - '0') * 10 + (m1 - '0');
        } else {
            return null;
        }

        int dom;
        if (d0 >= '0' && d0 <= '9'
                && d1 >= '0' && d1 <= '9'
        ) {
            dom = (d0 - '0') * 10 + (d1 - '0');
        } else {
            return null;
        }

        int hour;
        if (h0 >= '0' && h0 <= '9'
                && h1 >= '0' && h1 <= '9'
        ) {
            hour = (h0 - '0') * 10 + (h1 - '0');
        } else {
            return null;
        }

        int minute;
        if (i0 >= '0' && i0 <= '9'
                && i1 >= '0' && i1 <= '9'
        ) {
            minute = (i0 - '0') * 10 + (i1 - '0');
        } else {
            return null;
        }

        int second;
        if (s0 >= '0' && s0 <= '9'
                && s1 >= '0' && s1 <= '9'
        ) {
            second = (s0 - '0') * 10 + (s1 - '0');
        } else {
            return null;
        }

        return LocalDateTime.of(year, month, dom, hour, minute, second);
    }

    /**
     * yyyy-MM-ddTHH:mmZ
     * yyyy-MM-dd HH:mmZ
     * yyyy-M-dTHH:mm:ss
     * yyyy-M-d HH:mm:ss
     */
    public static LocalDateTime parseLocalDateTime17(char[] str, int off) {
        if (off + 17 > str.length) {
            String input = new String(str, off, str.length - off);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        char c0 = str[off];
        char c1 = str[off + 1];
        char c2 = str[off + 2];
        char c3 = str[off + 3];
        char c4 = str[off + 4];
        char c5 = str[off + 5];
        char c6 = str[off + 6];
        char c7 = str[off + 7];
        char c8 = str[off + 8];
        char c9 = str[off + 9];
        char c10 = str[off + 10];
        char c11 = str[off + 11];
        char c12 = str[off + 12];
        char c13 = str[off + 13];
        char c14 = str[off + 14];
        char c15 = str[off + 15];
        char c16 = str[off + 16];

        char y0, y1, y2, y3, m0, m1, d0, d1, h0, h1, i0, i1, s0, s1;
        int nanoOfSecond = 0;
        if (c4 == '-' && c7 == '-' && (c10 == 'T' || c10 == ' ') && c13 == ':' && c16 == 'Z') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = '0';
            s1 = '0';
        } else if (c4 == '-' && c6 == '-' && (c8 == ' ' || c8 == 'T') && c11 == ':' && c14 == ':') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = '0';
            m1 = c5;

            d0 = '0';
            d1 = c7;

            h0 = c9;
            h1 = c10;

            i0 = c12;
            i1 = c13;

            s0 = c15;
            s1 = c16;
        } else if (c2 == ' ' && c6 == ' ' && c11 == ' ' && c14 == ':') {
            y0 = c7;
            y1 = c8;
            y2 = c9;
            y3 = c10;

            int month = month(c3, c4, c5);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                return null;
            }

            d0 = c0;
            d1 = c1;

            h0 = c12;
            h1 = c13;

            i0 = c15;
            i1 = c16;

            s0 = '0';
            s1 = '0';
        } else if (c1 == ' ' && c5 == ' ' && c10 == ' ' && c12 == ':' && c14 == ':') {
            // d MMM yyyy H:m:ss
            // 6 DEC 2020 1:3:14
            d0 = '0';
            d1 = c0;

            int month = month(c2, c3, c4);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                return null;
            }

            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;

            h0 = '0';
            h1 = c11;

            i0 = '0';
            i1 = c13;

            s0 = c15;
            s1 = c16;
        } else if (c1 == ' ' && c5 == ' ' && c10 == ' ' && c12 == ':' && c15 == ':') {
            // d MMM yyyy H:mm:s
            // 6 DEC 2020 1:13:4
            d0 = '0';
            d1 = c0;

            int month = month(c2, c3, c4);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                return null;
            }

            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;

            h0 = '0';
            h1 = c11;

            i0 = c13;
            i1 = c14;

            s0 = '0';
            s1 = c16;
        } else if (c1 == ' ' && c5 == ' ' && c10 == ' ' && c13 == ':' && c15 == ':') {
            // d MMM yyyy HH:m:s
            // 6 DEC 2020 11:3:4
            d0 = '0';
            d1 = c0;

            int month = month(c2, c3, c4);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                return null;
            }

            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;

            h0 = c11;
            h1 = c12;

            i0 = '0';
            i1 = c14;

            s0 = '0';
            s1 = c16;
        } else if (c2 == ' ' && c6 == ' ' && c11 == ' ' && c13 == ':' && c15 == ':') {
            // dd MMM yyyy H:m:s
            // 16 DEC 2020 1:3:4
            d0 = c0;
            d1 = c1;

            int month = month(c3, c4, c5);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                return null;
            }

            y0 = c7;
            y1 = c8;
            y2 = c9;
            y3 = c10;

            h0 = '0';
            h1 = c12;

            i0 = '0';
            i1 = c14;

            s0 = '0';
            s1 = c16;
        } else {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c4;
            m1 = c5;

            d0 = c6;
            d1 = c7;

            h0 = c8;
            h1 = c9;

            i0 = c10;
            i1 = c11;

            s0 = c12;
            s1 = c13;

            if (c14 >= '0' && c14 <= '9'
                    && c15 >= '0' && c15 <= '9'
                    && c16 >= '0' && c16 <= '9'
            ) {
                nanoOfSecond = ((c14 - '0') * 100 + (c15 - '0') * 10 + (c16 - '0')) * 1_000_000;
            } else {
                return null;
            }
        }

        int year;
        if (y0 >= '0' && y0 <= '9'
                && y1 >= '0' && y1 <= '9'
                && y2 >= '0' && y2 <= '9'
                && y3 >= '0' && y3 <= '9'
        ) {
            year = (y0 - '0') * 1000 + (y1 - '0') * 100 + (y2 - '0') * 10 + (y3 - '0');
        } else {
            return null;
        }

        int month;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
        ) {
            month = (m0 - '0') * 10 + (m1 - '0');
        } else {
            return null;
        }

        int dom;
        if (d0 >= '0' && d0 <= '9'
                && d1 >= '0' && d1 <= '9'
        ) {
            dom = (d0 - '0') * 10 + (d1 - '0');
        } else {
            return null;
        }

        int hour;
        if (h0 >= '0' && h0 <= '9'
                && h1 >= '0' && h1 <= '9'
        ) {
            hour = (h0 - '0') * 10 + (h1 - '0');
        } else {
            return null;
        }

        int minute;
        if (i0 >= '0' && i0 <= '9'
                && i1 >= '0' && i1 <= '9'
        ) {
            minute = (i0 - '0') * 10 + (i1 - '0');
        } else {
            return null;
        }

        int second;
        if (s0 >= '0' && s0 <= '9'
                && s1 >= '0' && s1 <= '9'
        ) {
            second = (s0 - '0') * 10 + (s1 - '0');
        } else {
            return null;
        }

        return LocalDateTime.of(year, month, dom, hour, minute, second, nanoOfSecond);
    }

    /**
     * yyyy-MM-ddTHH:mmZ
     * yyyy-MM-dd HH:mmZ
     * yyyy-M-dTHH:mm:ss
     * yyyy-M-d HH:mm:ss
     */
    public static LocalDateTime parseLocalDateTime17(byte[] str, int off) {
        if (off + 17 > str.length) {
            String input = new String(str, off, str.length - off);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        byte c0 = str[off];
        byte c1 = str[off + 1];
        byte c2 = str[off + 2];
        byte c3 = str[off + 3];
        byte c4 = str[off + 4];
        byte c5 = str[off + 5];
        byte c6 = str[off + 6];
        byte c7 = str[off + 7];
        byte c8 = str[off + 8];
        byte c9 = str[off + 9];
        byte c10 = str[off + 10];
        byte c11 = str[off + 11];
        byte c12 = str[off + 12];
        byte c13 = str[off + 13];
        byte c14 = str[off + 14];
        byte c15 = str[off + 15];
        byte c16 = str[off + 16];

        char y0, y1, y2, y3, m0, m1, d0, d1, h0, h1, i0, i1, s0, s1;
        int nanoOfSecond = 0;
        if (c4 == '-' && c7 == '-' && (c10 == 'T' || c10 == ' ') && c13 == ':' && c16 == 'Z') {
            y0 = (char) c0;
            y1 = (char) c1;
            y2 = (char) c2;
            y3 = (char) c3;

            m0 = (char) c5;
            m1 = (char) c6;

            d0 = (char) c8;
            d1 = (char) c9;

            h0 = (char) c11;
            h1 = (char) c12;

            i0 = (char) c14;
            i1 = (char) c15;

            s0 = '0';
            s1 = '0';
        } else if (c4 == '-' && c6 == '-' && (c8 == ' ' || c8 == 'T') && c11 == ':' && c14 == ':') {
            y0 = (char) c0;
            y1 = (char) c1;
            y2 = (char) c2;
            y3 = (char) c3;

            m0 = '0';
            m1 = (char) c5;

            d0 = '0';
            d1 = (char) c7;

            h0 = (char) c9;
            h1 = (char) c10;

            i0 = (char) c12;
            i1 = (char) c13;

            s0 = (char) c15;
            s1 = (char) c16;
        } else if (c4 == -27 && c5 == -71 && c6 == -76 // 年
                && c9 == -26 && c10 == -100 && c11 == -120 // 月
                && c14 == -26 && c15 == -105 && c16 == -91 // 日
        ) {
            y0 = (char) c0;
            y1 = (char) c1;
            y2 = (char) c2;
            y3 = (char) c3;

            m0 = (char) c7;
            m1 = (char) c8;

            d0 = (char) c12;
            d1 = (char) c13;

            h0 = '0';
            h1 = '0';

            i0 = '0';
            i1 = '0';

            s0 = '0';
            s1 = '0';
        } else if (c2 == ' ' && c6 == ' ' && c11 == ' ' && c14 == ':') {
            y0 = (char) c7;
            y1 = (char) c8;
            y2 = (char) c9;
            y3 = (char) c10;

            int month = month((char) c3, (char) c4, (char) c5);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                String input = new String(str, off, 17);
                throw new DateTimeParseException("illegal input " + input, input, 0);
            }

            d0 = (char) c0;
            d1 = (char) c1;

            h0 = (char) c12;
            h1 = (char) c13;

            i0 = (char) c15;
            i1 = (char) c16;

            s0 = '0';
            s1 = '0';
        } else if (c1 == ' ' && c5 == ' ' && c10 == ' ' && c12 == ':' && c14 == ':') {
            // d MMM yyyy H:m:ss
            // 6 DEC 2020 1:3:14
            d0 = '0';
            d1 = (char) c0;

            int month = month((char) c2, (char) c3, (char) c4);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                String input = new String(str, off, 17);
                throw new DateTimeParseException("illegal input " + input, input, 0);
            }

            y0 = (char) c6;
            y1 = (char) c7;
            y2 = (char) c8;
            y3 = (char) c9;

            h0 = '0';
            h1 = (char) c11;

            i0 = '0';
            i1 = (char) c13;

            s0 = (char) c15;
            s1 = (char) c16;
        } else if (c1 == ' ' && c5 == ' ' && c10 == ' ' && c12 == ':' && c15 == ':') {
            // d MMM yyyy H:mm:s
            // 6 DEC 2020 1:13:4
            d0 = '0';
            d1 = (char) c0;

            int month = month((char) c2, (char) c3, (char) c4);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                String input = new String(str, off, 17);
                throw new DateTimeParseException("illegal input " + input, input, 0);
            }

            y0 = (char) c6;
            y1 = (char) c7;
            y2 = (char) c8;
            y3 = (char) c9;

            h0 = '0';
            h1 = (char) c11;

            i0 = (char) c13;
            i1 = (char) c14;

            s0 = '0';
            s1 = (char) c16;
        } else if (c1 == ' ' && c5 == ' ' && c10 == ' ' && c13 == ':' && c15 == ':') {
            // d MMM yyyy HH:m:s
            // 6 DEC 2020 11:3:4
            d0 = '0';
            d1 = (char) c0;

            int month = month((char) c2, (char) c3, (char) c4);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                String input = new String(str, off, 17);
                throw new DateTimeParseException("illegal input " + input, input, 0);
            }

            y0 = (char) c6;
            y1 = (char) c7;
            y2 = (char) c8;
            y3 = (char) c9;

            h0 = (char) c11;
            h1 = (char) c12;

            i0 = '0';
            i1 = (char) c14;

            s0 = '0';
            s1 = (char) c16;
        } else if (c2 == ' ' && c6 == ' ' && c11 == ' ' && c13 == ':' && c15 == ':') {
            // dd MMM yyyy H:m:s
            // 16 DEC 2020 1:3:4
            d0 = (char) c0;
            d1 = (char) c1;

            int month = month((char) c3, (char) c4, (char) c5);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                String input = new String(str, off, 17);
                throw new DateTimeParseException("illegal input " + input, input, 0);
            }

            y0 = (char) c7;
            y1 = (char) c8;
            y2 = (char) c9;
            y3 = (char) c10;

            h0 = '0';
            h1 = (char) c12;

            i0 = '0';
            i1 = (char) c14;

            s0 = '0';
            s1 = (char) c16;
        } else {
            y0 = (char) c0;
            y1 = (char) c1;
            y2 = (char) c2;
            y3 = (char) c3;

            m0 = (char) c4;
            m1 = (char) c5;

            d0 = (char) c6;
            d1 = (char) c7;

            h0 = (char) c8;
            h1 = (char) c9;

            i0 = (char) c10;
            i1 = (char) c11;

            s0 = (char) c12;
            s1 = (char) c13;

            if (c14 >= '0' && c14 <= '9'
                    && c15 >= '0' && c15 <= '9'
                    && c16 >= '0' && c16 <= '9'
            ) {
                nanoOfSecond = ((c14 - '0') * 100 + (c15 - '0') * 10 + (c16 - '0')) * 1_000_000;
            } else {
                return null;
            }
        }

        int year;
        if (y0 >= '0' && y0 <= '9'
                && y1 >= '0' && y1 <= '9'
                && y2 >= '0' && y2 <= '9'
                && y3 >= '0' && y3 <= '9'
        ) {
            year = (y0 - '0') * 1000 + (y1 - '0') * 100 + (y2 - '0') * 10 + (y3 - '0');
        } else {
            String input = new String(str, off, 17);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int month;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
        ) {
            month = (m0 - '0') * 10 + (m1 - '0');
        } else {
            String input = new String(str, off, 17);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int dom;
        if (d0 >= '0' && d0 <= '9'
                && d1 >= '0' && d1 <= '9'
        ) {
            dom = (d0 - '0') * 10 + (d1 - '0');
        } else {
            String input = new String(str, off, 17);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int hour;
        if (h0 >= '0' && h0 <= '9'
                && h1 >= '0' && h1 <= '9'
        ) {
            hour = (h0 - '0') * 10 + (h1 - '0');
        } else {
            String input = new String(str, off, 17);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int minute;
        if (i0 >= '0' && i0 <= '9'
                && i1 >= '0' && i1 <= '9'
        ) {
            minute = (i0 - '0') * 10 + (i1 - '0');
        } else {
            String input = new String(str, off, 17);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int second;
        if (s0 >= '0' && s0 <= '9'
                && s1 >= '0' && s1 <= '9'
        ) {
            second = (s0 - '0') * 10 + (s1 - '0');
        } else {
            String input = new String(str, off, 17);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        return LocalDateTime.of(year, month, dom, hour, minute, second, nanoOfSecond);
    }

    /**
     * yyyy-M-ddTHH:mm:ss
     * yyyy-M-dd HH:mm:ss
     * yyyy-MM-dTHH:mm:ss
     * yyyy-MM-d HH:mm:ss
     * yyyy-MM-ddTH:mm:ss
     * yyyy-MM-dd H:mm:ss
     * yyyy-MM-ddTHH:m:ss
     * yyyy-MM-dd HH:m:ss
     * yyyy-MM-ddTHH:mm:s
     * yyyy-MM-dd HH:mm:s
     */
    public static LocalDateTime parseLocalDateTime18(char[] str, int off) {
        if (off + 18 > str.length) {
            String input = new String(str, off, str.length - off);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        char c0 = str[off];
        char c1 = str[off + 1];
        char c2 = str[off + 2];
        char c3 = str[off + 3];
        char c4 = str[off + 4];
        char c5 = str[off + 5];
        char c6 = str[off + 6];
        char c7 = str[off + 7];
        char c8 = str[off + 8];
        char c9 = str[off + 9];
        char c10 = str[off + 10];
        char c11 = str[off + 11];
        char c12 = str[off + 12];
        char c13 = str[off + 13];
        char c14 = str[off + 14];
        char c15 = str[off + 15];
        char c16 = str[off + 16];
        char c17 = str[off + 17];

        char y0, y1, y2, y3, m0, m1, d0, d1, h0, h1, i0, i1, s0, s1;
        if (c4 == '-' && c6 == '-' && (c9 == ' ' || c9 == 'T') && c12 == ':' && c15 == ':') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = '0';
            m1 = c5;

            d0 = c7;
            d1 = c8;

            h0 = c10;
            h1 = c11;

            i0 = c13;
            i1 = c14;

            s0 = c16;
            s1 = c17;
        } else if (c4 == '-' && c7 == '-' && (c9 == ' ' || c9 == 'T') && c12 == ':' && c15 == ':') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = '0';
            d1 = c8;

            h0 = c10;
            h1 = c11;

            i0 = c13;
            i1 = c14;

            s0 = c16;
            s1 = c17;
        } else if (c4 == '-' && c7 == '-' && (c10 == ' ' || c10 == 'T') && c12 == ':' && c15 == ':') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;

            h0 = '0';
            h1 = c11;

            i0 = c13;
            i1 = c14;

            s0 = c16;
            s1 = c17;
        } else if (c4 == '-' && c7 == '-' && (c10 == ' ' || c10 == 'T') && c13 == ':' && c15 == ':') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;

            h0 = c11;
            h1 = c12;

            i0 = '0';
            i1 = c14;

            s0 = c16;
            s1 = c17;
        } else if (c4 == '-' && c7 == '-' && (c10 == ' ' || c10 == 'T') && c13 == ':' && c16 == ':') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = '0';
            s1 = c17;
        } else if (c1 == ' ' && c5 == ' ' && c10 == ' ' && c12 == ':' && c15 == ':') {
            // d MMM yyyy H:mm:ss
            // 6 DEC 2020 2:13:14
            d0 = '0';
            d1 = c0;

            int month = month(c2, c3, c4);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                String input = new String(str, off, 18);
                throw new DateTimeParseException("illegal input " + input, input, 0);
            }

            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;

            h0 = '0';
            h1 = c11;

            i0 = c13;
            i1 = c14;

            s0 = c16;
            s1 = c17;
        } else if (c1 == ' ' && c5 == ' ' && c10 == ' ' && c13 == ':' && c15 == ':') {
            // d MMM yyyy HH:m:ss
            // 6 DEC 2020 12:3:14
            d0 = '0';
            d1 = c0;

            int month = month(c2, c3, c4);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                String input = new String(str, off, 18);
                throw new DateTimeParseException("illegal input " + input, input, 0);
            }

            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;

            h0 = c11;
            h1 = c12;

            i0 = '0';
            i1 = c14;

            s0 = c16;
            s1 = c17;
        } else if (c1 == ' ' && c5 == ' ' && c10 == ' ' && c13 == ':' && c16 == ':') {
            // d MMM yyyy HH:m:ss
            // 6 DEC 2020 12:3:14
            d0 = '0';
            d1 = c0;

            int month = month(c2, c3, c4);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                String input = new String(str, off, 18);
                throw new DateTimeParseException("illegal input " + input, input, 0);
            }

            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = '0';
            s1 = c17;
        } else if (c2 == ' ' && c6 == ' ' && c11 == ' ' && c14 == ':' && c16 == ':') {
            // dd MMM yyyy HH:m:s
            // 16 DEC 2020 12:3:4
            d0 = c0;
            d1 = c1;

            int month = month(c3, c4, c5);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                String input = new String(str, off, 18);
                throw new DateTimeParseException("illegal input " + input, input, 0);
            }

            y0 = c7;
            y1 = c8;
            y2 = c9;
            y3 = c10;

            h0 = c12;
            h1 = c13;

            i0 = '0';
            i1 = c15;

            s0 = '0';
            s1 = c17;
        } else if (c2 == ' ' && c6 == ' ' && c11 == ' ' && c13 == ':' && c16 == ':') {
            // dd MMM yyyy H:mm:s
            // 16 DEC 2020 1:13:4
            d0 = c0;
            d1 = c1;

            int month = month(c3, c4, c5);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                String input = new String(str, off, 18);
                throw new DateTimeParseException("illegal input " + input, input, 0);
            }

            y0 = c7;
            y1 = c8;
            y2 = c9;
            y3 = c10;

            h0 = '0';
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = '0';
            s1 = c17;
        } else if (c2 == ' ' && c6 == ' ' && c11 == ' ' && c13 == ':' && c15 == ':') {
            // dd MMM yyyy H:mm:s
            // 16 DEC 2020 1:13:4
            d0 = c0;
            d1 = c1;

            int month = month(c3, c4, c5);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                String input = new String(str, off, 18);
                throw new DateTimeParseException("illegal input " + input, input, 0);
            }

            y0 = c7;
            y1 = c8;
            y2 = c9;
            y3 = c10;

            h0 = '0';
            h1 = c12;

            i0 = '0';
            i1 = c14;

            s0 = c16;
            s1 = c17;
        } else {
            String input = new String(str, off, 18);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int year;
        if (y0 >= '0' && y0 <= '9'
                && y1 >= '0' && y1 <= '9'
                && y2 >= '0' && y2 <= '9'
                && y3 >= '0' && y3 <= '9'
        ) {
            year = (y0 - '0') * 1000 + (y1 - '0') * 100 + (y2 - '0') * 10 + (y3 - '0');
        } else {
            String input = new String(str, off, 18);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int month;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
        ) {
            month = (m0 - '0') * 10 + (m1 - '0');
        } else {
            String input = new String(str, off, 18);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int dom;
        if (d0 >= '0' && d0 <= '9'
                && d1 >= '0' && d1 <= '9'
        ) {
            dom = (d0 - '0') * 10 + (d1 - '0');
        } else {
            String input = new String(str, off, 18);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int hour;
        if (h0 >= '0' && h0 <= '9'
                && h1 >= '0' && h1 <= '9'
        ) {
            hour = (h0 - '0') * 10 + (h1 - '0');
        } else {
            String input = new String(str, off, 18);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int minute;
        if (i0 >= '0' && i0 <= '9'
                && i1 >= '0' && i1 <= '9'
        ) {
            minute = (i0 - '0') * 10 + (i1 - '0');
        } else {
            String input = new String(str, off, 18);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int second;
        if (s0 >= '0' && s0 <= '9'
                && s1 >= '0' && s1 <= '9'
        ) {
            second = (s0 - '0') * 10 + (s1 - '0');
        } else {
            String input = new String(str, off, 18);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        return LocalDateTime.of(year, month, dom, hour, minute, second);
    }

    /**
     * yyyy-M-ddTHH:mm:ss
     * yyyy-M-dd HH:mm:ss
     * yyyy-MM-dTHH:mm:ss
     * yyyy-MM-d HH:mm:ss
     * yyyy-MM-ddTH:mm:ss
     * yyyy-MM-dd H:mm:ss
     * yyyy-MM-ddTHH:m:ss
     * yyyy-MM-dd HH:m:ss
     * yyyy-MM-ddTHH:mm:s
     * yyyy-MM-dd HH:mm:s
     */
    public static LocalDateTime parseLocalDateTime18(byte[] str, int off) {
        if (off + 18 > str.length) {
            String input = new String(str, off, str.length - off);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        char c0 = (char) str[off];
        char c1 = (char) str[off + 1];
        char c2 = (char) str[off + 2];
        char c3 = (char) str[off + 3];
        char c4 = (char) str[off + 4];
        char c5 = (char) str[off + 5];
        char c6 = (char) str[off + 6];
        char c7 = (char) str[off + 7];
        char c8 = (char) str[off + 8];
        char c9 = (char) str[off + 9];
        char c10 = (char) str[off + 10];
        char c11 = (char) str[off + 11];
        char c12 = (char) str[off + 12];
        char c13 = (char) str[off + 13];
        char c14 = (char) str[off + 14];
        char c15 = (char) str[off + 15];
        char c16 = (char) str[off + 16];
        char c17 = (char) str[off + 17];

        char y0, y1, y2, y3, m0, m1, d0, d1, h0, h1, i0, i1, s0, s1;
        if (c4 == '-' && c6 == '-' && (c9 == ' ' || c9 == 'T') && c12 == ':' && c15 == ':') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = '0';
            m1 = c5;

            d0 = c7;
            d1 = c8;

            h0 = c10;
            h1 = c11;

            i0 = c13;
            i1 = c14;

            s0 = c16;
            s1 = c17;
        } else if (c4 == '-' && c7 == '-' && (c9 == ' ' || c9 == 'T') && c12 == ':' && c15 == ':') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = '0';
            d1 = c8;

            h0 = c10;
            h1 = c11;

            i0 = c13;
            i1 = c14;

            s0 = c16;
            s1 = c17;
        } else if (c4 == '-' && c7 == '-' && (c10 == ' ' || c10 == 'T') && c12 == ':' && c15 == ':') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;

            h0 = '0';
            h1 = c11;

            i0 = c13;
            i1 = c14;

            s0 = c16;
            s1 = c17;
        } else if (c4 == '-' && c7 == '-' && (c10 == ' ' || c10 == 'T') && c13 == ':' && c15 == ':') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;

            h0 = c11;
            h1 = c12;

            i0 = '0';
            i1 = c14;

            s0 = c16;
            s1 = c17;
        } else if (c4 == '-' && c7 == '-' && (c10 == ' ' || c10 == 'T') && c13 == ':' && c16 == ':') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = '0';
            s1 = c17;
        } else if (c1 == ' ' && c5 == ' ' && c10 == ' ' && c12 == ':' && c15 == ':') {
            // d MMM yyyy H:mm:ss
            // 6 DEC 2020 2:13:14
            d0 = '0';
            d1 = c0;

            int month = month(c2, c3, c4);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                String input = new String(str, off, 18);
                throw new DateTimeParseException("illegal input " + input, input, 0);
            }

            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;

            h0 = '0';
            h1 = c11;

            i0 = c13;
            i1 = c14;

            s0 = c16;
            s1 = c17;
        } else if (c1 == ' ' && c5 == ' ' && c10 == ' ' && c13 == ':' && c15 == ':') {
            // d MMM yyyy HH:m:ss
            // 6 DEC 2020 12:3:14
            d0 = '0';
            d1 = c0;

            int month = month(c2, c3, c4);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                String input = new String(str, off, 18);
                throw new DateTimeParseException("illegal input " + input, input, 0);
            }

            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;

            h0 = c11;
            h1 = c12;

            i0 = '0';
            i1 = c14;

            s0 = c16;
            s1 = c17;
        } else if (c1 == ' ' && c5 == ' ' && c10 == ' ' && c13 == ':' && c16 == ':') {
            // d MMM yyyy HH:m:ss
            // 6 DEC 2020 12:3:14
            d0 = '0';
            d1 = c0;

            int month = month(c2, c3, c4);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                String input = new String(str, off, 18);
                throw new DateTimeParseException("illegal input " + input, input, 0);
            }

            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = '0';
            s1 = c17;
        } else if (c2 == ' ' && c6 == ' ' && c11 == ' ' && c14 == ':' && c16 == ':') {
            // dd MMM yyyy HH:m:s
            // 16 DEC 2020 12:3:4
            d0 = c0;
            d1 = c1;

            int month = month(c3, c4, c5);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                String input = new String(str, off, 18);
                throw new DateTimeParseException("illegal input " + input, input, 0);
            }

            y0 = c7;
            y1 = c8;
            y2 = c9;
            y3 = c10;

            h0 = c12;
            h1 = c13;

            i0 = '0';
            i1 = c15;

            s0 = '0';
            s1 = c17;
        } else if (c2 == ' ' && c6 == ' ' && c11 == ' ' && c13 == ':' && c16 == ':') {
            // dd MMM yyyy H:mm:s
            // 16 DEC 2020 1:13:4
            d0 = c0;
            d1 = c1;

            int month = month(c3, c4, c5);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                String input = new String(str, off, 18);
                throw new DateTimeParseException("illegal input " + input, input, 0);
            }

            y0 = c7;
            y1 = c8;
            y2 = c9;
            y3 = c10;

            h0 = '0';
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = '0';
            s1 = c17;
        } else if (c2 == ' ' && c6 == ' ' && c11 == ' ' && c13 == ':' && c15 == ':') {
            // dd MMM yyyy H:mm:s
            // 16 DEC 2020 1:13:4
            d0 = c0;
            d1 = c1;

            int month = month(c3, c4, c5);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                String input = new String(str, off, 18);
                throw new DateTimeParseException("illegal input " + input, input, 0);
            }

            y0 = c7;
            y1 = c8;
            y2 = c9;
            y3 = c10;

            h0 = '0';
            h1 = c12;

            i0 = '0';
            i1 = c14;

            s0 = c16;
            s1 = c17;
        } else {
            String input = new String(str, off, 18);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int year;
        if (y0 >= '0' && y0 <= '9'
                && y1 >= '0' && y1 <= '9'
                && y2 >= '0' && y2 <= '9'
                && y3 >= '0' && y3 <= '9'
        ) {
            year = (y0 - '0') * 1000 + (y1 - '0') * 100 + (y2 - '0') * 10 + (y3 - '0');
        } else {
            String input = new String(str, off, 18);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int month;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
        ) {
            month = (m0 - '0') * 10 + (m1 - '0');
        } else {
            String input = new String(str, off, 18);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int dom;
        if (d0 >= '0' && d0 <= '9'
                && d1 >= '0' && d1 <= '9'
        ) {
            dom = (d0 - '0') * 10 + (d1 - '0');
        } else {
            String input = new String(str, off, 18);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int hour;
        if (h0 >= '0' && h0 <= '9'
                && h1 >= '0' && h1 <= '9'
        ) {
            hour = (h0 - '0') * 10 + (h1 - '0');
        } else {
            String input = new String(str, off, 18);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int minute;
        if (i0 >= '0' && i0 <= '9'
                && i1 >= '0' && i1 <= '9'
        ) {
            minute = (i0 - '0') * 10 + (i1 - '0');
        } else {
            String input = new String(str, off, 18);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int second;
        if (s0 >= '0' && s0 <= '9'
                && s1 >= '0' && s1 <= '9'
        ) {
            second = (s0 - '0') * 10 + (s1 - '0');
        } else {
            String input = new String(str, off, 18);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        return LocalDateTime.of(year, month, dom, hour, minute, second);
    }

    /**
     * yyyy-MM-ddTHH:mm:ss
     * yyyy-MM-dd HH:mm:ss
     * yyyy/MM/ddTHH:mm:ss
     * yyyy/MM/dd HH:mm:ss
     */
    public static LocalDateTime parseLocalDateTime19(char[] str, int off) {
        if (off + 19 > str.length) {
            return null;
        }

        char c0 = str[off];
        char c1 = str[off + 1];
        char c2 = str[off + 2];
        char c3 = str[off + 3];
        char c4 = str[off + 4];
        char c5 = str[off + 5];
        char c6 = str[off + 6];
        char c7 = str[off + 7];
        char c8 = str[off + 8];
        char c9 = str[off + 9];
        char c10 = str[off + 10];
        char c11 = str[off + 11];
        char c12 = str[off + 12];
        char c13 = str[off + 13];
        char c14 = str[off + 14];
        char c15 = str[off + 15];
        char c16 = str[off + 16];
        char c17 = str[off + 17];
        char c18 = str[off + 18];

        char y0, y1, y2, y3, m0, m1, d0, d1, h0, h1, i0, i1, s0, s1;
        if (c4 == '-' && c7 == '-' && (c10 == ' ' || c10 == 'T') && c13 == ':' && c16 == ':') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = c17;
            s1 = c18;
        } else if (c4 == '/' && c7 == '/' && (c10 == ' ' || c10 == 'T') && c13 == ':' && c16 == ':') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = c17;
            s1 = c18;
        } else if (c2 == '/' && c5 == '/' && (c10 == ' ' || c10 == 'T') && c13 == ':' && c16 == ':') {
            d0 = c0;
            d1 = c1;

            m0 = c3;
            m1 = c4;

            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = c17;
            s1 = c18;
        } else if (c1 == ' ' && c5 == ' ' && c10 == ' ' && c13 == ':' && c16 == ':') {
            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;

            int month = month(c2, c3, c4);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                m0 = '0';
                m1 = '0';
            }

            d0 = '0';
            d1 = c0;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = c17;
            s1 = c18;
        } else {
            return null;
        }

        return localDateTime(y0, y1, y2, y3, m0, m1, d0, d1, h0, h1, i0, i1, s0, s1);
    }

    public static LocalDateTime parseLocalDateTime19(String str, int off) {
        if (off + 19 > str.length()) {
            return null;
        }
        char[] chars = new char[19];
        str.getChars(off, off + 19, chars, 0);
        LocalDateTime ldt = parseLocalDateTime19(chars, off);
        return ldt;
    }

    /**
     * yyyy-MM-ddTHH:mm:ss
     * yyyy-MM-dd HH:mm:ss
     * yyyy/MM/ddTHH:mm:ss
     * yyyy/MM/dd HH:mm:ss
     */
    public static LocalDateTime parseLocalDateTime19(byte[] str, int off) {
        if (off + 19 > str.length) {
            return null;
        }

        char c0 = (char) str[off];
        char c1 = (char) str[off + 1];
        char c2 = (char) str[off + 2];
        char c3 = (char) str[off + 3];
        char c4 = (char) str[off + 4];
        char c5 = (char) str[off + 5];
        char c6 = (char) str[off + 6];
        char c7 = (char) str[off + 7];
        char c8 = (char) str[off + 8];
        char c9 = (char) str[off + 9];
        char c10 = (char) str[off + 10];
        char c11 = (char) str[off + 11];
        char c12 = (char) str[off + 12];
        char c13 = (char) str[off + 13];
        char c14 = (char) str[off + 14];
        char c15 = (char) str[off + 15];
        char c16 = (char) str[off + 16];
        char c17 = (char) str[off + 17];
        char c18 = (char) str[off + 18];

        char y0, y1, y2, y3, m0, m1, d0, d1, h0, h1, i0, i1, s0, s1;
        if (c4 == '-' && c7 == '-' && (c10 == ' ' || c10 == 'T') && c13 == ':' && c16 == ':') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = c17;
            s1 = c18;
        } else if (c4 == '/' && c7 == '/' && (c10 == ' ' || c10 == 'T') && c13 == ':' && c16 == ':') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = c17;
            s1 = c18;
        } else if (c2 == '/' && c5 == '/' && (c10 == ' ' || c10 == 'T') && c13 == ':' && c16 == ':') {
            d0 = c0;
            d1 = c1;

            m0 = c3;
            m1 = c4;

            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = c17;
            s1 = c18;
        } else if (c1 == ' ' && c5 == ' ' && c10 == ' ' && c13 == ':' && c16 == ':') {
            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;

            int month = month(c2, c3, c4);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                m0 = '0';
                m1 = '0';
            }

            d0 = '0';
            d1 = c0;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = c17;
            s1 = c18;
        } else {
            return null;
        }

        return localDateTime(y0, y1, y2, y3, m0, m1, d0, d1, h0, h1, i0, i1, s0, s1);
    }

    public static LocalDateTime parseLocalDateTime20(char[] str, int off) {
        if (off + 19 > str.length) {
            return null;
        }

        char c0 = str[off];
        char c1 = str[off + 1];
        char c2 = str[off + 2];
        char c3 = str[off + 3];
        char c4 = str[off + 4];
        char c5 = str[off + 5];
        char c6 = str[off + 6];
        char c7 = str[off + 7];
        char c8 = str[off + 8];
        char c9 = str[off + 9];
        char c10 = str[off + 10];
        char c11 = str[off + 11];
        char c12 = str[off + 12];
        char c13 = str[off + 13];
        char c14 = str[off + 14];
        char c15 = str[off + 15];
        char c16 = str[off + 16];
        char c17 = str[off + 17];
        char c18 = str[off + 18];
        char c19 = str[off + 19];

        if (c2 != ' ' || c6 != ' ' || c11 != ' ' || c14 != ':' || c17 != ':') {
            return null;
        }

        char m0, m1;

        int month = month(c3, c4, c5);
        if (month > 0) {
            m0 = (char) ('0' + month / 10);
            m1 = (char) ('0' + (month % 10));
        } else {
            m0 = '0';
            m1 = '0';
        }

        return localDateTime(
                c7, c8, c9, c10, // yyyy
                m0, m1,
                c0, c1, // dd
                c12, c13, // HH
                c15, c16, // mm
                c18, c19 // ss
        );
    }

    public static LocalDateTime parseLocalDateTime20(byte[] str, int off) {
        if (off + 19 > str.length) {
            return null;
        }

        char c0 = (char) str[off];
        char c1 = (char) str[off + 1];
        char c2 = (char) str[off + 2];
        char c3 = (char) str[off + 3];
        char c4 = (char) str[off + 4];
        char c5 = (char) str[off + 5];
        char c6 = (char) str[off + 6];
        char c7 = (char) str[off + 7];
        char c8 = (char) str[off + 8];
        char c9 = (char) str[off + 9];
        char c10 = (char) str[off + 10];
        char c11 = (char) str[off + 11];
        char c12 = (char) str[off + 12];
        char c13 = (char) str[off + 13];
        char c14 = (char) str[off + 14];
        char c15 = (char) str[off + 15];
        char c16 = (char) str[off + 16];
        char c17 = (char) str[off + 17];
        char c18 = (char) str[off + 18];
        char c19 = (char) str[off + 19];

        if (c2 != ' ' || c6 != ' ' || c11 != ' ' || c14 != ':' || c17 != ':') {
            return null;
        }

        char m0, m1;

        int month = month(c3, c4, c5);
        if (month > 0) {
            m0 = (char) ('0' + month / 10);
            m1 = (char) ('0' + (month % 10));
        } else {
            m0 = '0';
            m1 = '0';
        }

        return localDateTime(
                c7, c8, c9, c10, // yyyy
                m0, m1,
                c0, c1, // dd
                c12, c13, // HH
                c15, c16, // mm
                c18, c19 // ss
        );
    }

    public static LocalDateTime parseLocalDateTime26(byte[] str, int off) {
        if (off + 26 > str.length) {
            return null;
        }

        char c0 = (char) str[off];
        char c1 = (char) str[off + 1];
        char c2 = (char) str[off + 2];
        char c3 = (char) str[off + 3];
        char c4 = (char) str[off + 4];
        char c5 = (char) str[off + 5];
        char c6 = (char) str[off + 6];
        char c7 = (char) str[off + 7];
        char c8 = (char) str[off + 8];
        char c9 = (char) str[off + 9];
        char c10 = (char) str[off + 10];
        char c11 = (char) str[off + 11];
        char c12 = (char) str[off + 12];
        char c13 = (char) str[off + 13];
        char c14 = (char) str[off + 14];
        char c15 = (char) str[off + 15];
        char c16 = (char) str[off + 16];
        char c17 = (char) str[off + 17];
        char c18 = (char) str[off + 18];
        char c19 = (char) str[off + 19];
        char c20 = (char) str[off + 20];
        char c21 = (char) str[off + 21];
        char c22 = (char) str[off + 22];
        char c23 = (char) str[off + 23];
        char c24 = (char) str[off + 24];
        char c25 = (char) str[off + 25];

        if (c4 != '-' || c7 != '-' || (c10 != ' ' && c10 != 'T') || c13 != ':' || c16 != ':' || c19 != '.') {
            return null;
        }

        return localDateTime(
                c0, c1, c2, c3, // yyyy
                c5, c6, // MM
                c8, c9, // dd
                c11, c12, // HH
                c14, c15, // mm
                c17, c18, // ss
                c20, c21, c22, c23, c24, c25, '0', '0', '0'
        );
    }

    public static LocalDateTime parseLocalDateTime26(char[] str, int off) {
        if (off + 26 > str.length) {
            return null;
        }

        char c0 = str[off];
        char c1 = str[off + 1];
        char c2 = str[off + 2];
        char c3 = str[off + 3];
        char c4 = str[off + 4];
        char c5 = str[off + 5];
        char c6 = str[off + 6];
        char c7 = str[off + 7];
        char c8 = str[off + 8];
        char c9 = str[off + 9];
        char c10 = str[off + 10];
        char c11 = str[off + 11];
        char c12 = str[off + 12];
        char c13 = str[off + 13];
        char c14 = str[off + 14];
        char c15 = str[off + 15];
        char c16 = str[off + 16];
        char c17 = str[off + 17];
        char c18 = str[off + 18];
        char c19 = str[off + 19];
        char c20 = str[off + 20];
        char c21 = str[off + 21];
        char c22 = str[off + 22];
        char c23 = str[off + 23];
        char c24 = str[off + 24];
        char c25 = str[off + 25];

        if (c4 != '-' || c7 != '-' || (c10 != ' ' && c10 != 'T') || c13 != ':' || c16 != ':' || c19 != '.') {
            return null;
        }

        return localDateTime(
                c0, c1, c2, c3, // yyyy
                c5, c6, // MM
                c8, c9, // dd
                c11, c12, // HH
                c14, c15, // mm
                c17, c18, // SS
                c20, c21, c22, c23, c24, c25,
                '0', '0', '0'
        );
    }

    public static LocalDateTime parseLocalDateTime27(byte[] str, int off) {
        if (off + 27 > str.length) {
            return null;
        }

        char c0 = (char) str[off];
        char c1 = (char) str[off + 1];
        char c2 = (char) str[off + 2];
        char c3 = (char) str[off + 3];
        char c4 = (char) str[off + 4];
        char c5 = (char) str[off + 5];
        char c6 = (char) str[off + 6];
        char c7 = (char) str[off + 7];
        char c8 = (char) str[off + 8];
        char c9 = (char) str[off + 9];
        char c10 = (char) str[off + 10];
        char c11 = (char) str[off + 11];
        char c12 = (char) str[off + 12];
        char c13 = (char) str[off + 13];
        char c14 = (char) str[off + 14];
        char c15 = (char) str[off + 15];
        char c16 = (char) str[off + 16];
        char c17 = (char) str[off + 17];
        char c18 = (char) str[off + 18];
        char c19 = (char) str[off + 19];
        char c20 = (char) str[off + 20];
        char c21 = (char) str[off + 21];
        char c22 = (char) str[off + 22];
        char c23 = (char) str[off + 23];
        char c24 = (char) str[off + 24];
        char c25 = (char) str[off + 25];
        char c26 = (char) str[off + 26];

        if (c4 != '-' || c7 != '-' || (c10 != ' ' && c10 != 'T') || c13 != ':' || c16 != ':' || c19 != '.') {
            return null;
        }

        return localDateTime(
                c0, c1, c2, c3, // yyyy
                c5, c6, // MM
                c8, c9, // dd
                c11, c12, // HH
                c14, c15, // mm
                c17, c18, // ss
                c20, c21, c22, c23, c24, c25, c26, '0', '0'
        );
    }

    public static LocalDateTime parseLocalDateTime27(char[] str, int off) {
        if (off + 27 > str.length) {
            return null;
        }

        char c0 = str[off];
        char c1 = str[off + 1];
        char c2 = str[off + 2];
        char c3 = str[off + 3];
        char c4 = str[off + 4];
        char c5 = str[off + 5];
        char c6 = str[off + 6];
        char c7 = str[off + 7];
        char c8 = str[off + 8];
        char c9 = str[off + 9];
        char c10 = str[off + 10];
        char c11 = str[off + 11];
        char c12 = str[off + 12];
        char c13 = str[off + 13];
        char c14 = str[off + 14];
        char c15 = str[off + 15];
        char c16 = str[off + 16];
        char c17 = str[off + 17];
        char c18 = str[off + 18];
        char c19 = str[off + 19];
        char c20 = str[off + 20];
        char c21 = str[off + 21];
        char c22 = str[off + 22];
        char c23 = str[off + 23];
        char c24 = str[off + 24];
        char c25 = str[off + 25];
        char c26 = str[off + 26];

        if (c4 != '-' || c7 != '-' || (c10 != ' ' && c10 != 'T') || c13 != ':' || c16 != ':' || c19 != '.') {
            return null;
        }

        return localDateTime(
                c0, c1, c2, c3, // yyyy
                c5, c6, // MM
                c8, c9, // dd
                c11, c12, // hh
                c14, c15, // mm
                c17, c18, // ss
                c20, c21, c22, c23, c24, c25, c26, '0', '0'
        );
    }

    public static LocalDateTime parseLocalDateTime28(char[] str, int off) {
        if (off + 28 > str.length) {
            return null;
        }

        char c0 = str[off];
        char c1 = str[off + 1];
        char c2 = str[off + 2];
        char c3 = str[off + 3];
        char c4 = str[off + 4];
        char c5 = str[off + 5];
        char c6 = str[off + 6];
        char c7 = str[off + 7];
        char c8 = str[off + 8];
        char c9 = str[off + 9];
        char c10 = str[off + 10];
        char c11 = str[off + 11];
        char c12 = str[off + 12];
        char c13 = str[off + 13];
        char c14 = str[off + 14];
        char c15 = str[off + 15];
        char c16 = str[off + 16];
        char c17 = str[off + 17];
        char c18 = str[off + 18];
        char c19 = str[off + 19];
        char c20 = str[off + 20];
        char c21 = str[off + 21];
        char c22 = str[off + 22];
        char c23 = str[off + 23];
        char c24 = str[off + 24];
        char c25 = str[off + 25];
        char c26 = str[off + 26];
        char c27 = str[off + 27];

        if (c4 != '-' || c7 != '-' || (c10 != ' ' && c10 != 'T') || c13 != ':' || c16 != ':' || c19 != '.') {
            return null;
        }

        return localDateTime(
                c0, c1, c2, c3, // yyyy
                c5, c6, // MM
                c8, c9, // dd
                c11, c12, // HH
                c14, c15, // mm
                c17, c18, // ss
                c20, c21, c22, c23, c24, c25, c26, c27, '0'
        );
    }

    public static LocalDateTime parseLocalDateTime28(byte[] str, int off) {
        if (off + 28 > str.length) {
            return null;
        }

        char c0 = (char) str[off];
        char c1 = (char) str[off + 1];
        char c2 = (char) str[off + 2];
        char c3 = (char) str[off + 3];
        char c4 = (char) str[off + 4];
        char c5 = (char) str[off + 5];
        char c6 = (char) str[off + 6];
        char c7 = (char) str[off + 7];
        char c8 = (char) str[off + 8];
        char c9 = (char) str[off + 9];
        char c10 = (char) str[off + 10];
        char c11 = (char) str[off + 11];
        char c12 = (char) str[off + 12];
        char c13 = (char) str[off + 13];
        char c14 = (char) str[off + 14];
        char c15 = (char) str[off + 15];
        char c16 = (char) str[off + 16];
        char c17 = (char) str[off + 17];
        char c18 = (char) str[off + 18];
        char c19 = (char) str[off + 19];
        char c20 = (char) str[off + 20];
        char c21 = (char) str[off + 21];
        char c22 = (char) str[off + 22];
        char c23 = (char) str[off + 23];
        char c24 = (char) str[off + 24];
        char c25 = (char) str[off + 25];
        char c26 = (char) str[off + 26];
        char c27 = (char) str[off + 27];

        if (c4 != '-' || c7 != '-' || (c10 != ' ' && c10 != 'T') || c13 != ':' || c16 != ':' || c19 != '.') {
            return null;
        }

        return localDateTime(
                c0, c1, c2, c3, // yyyy
                c5, c6, // MM
                c8, c9, // dd
                c11, c12, // HH
                c14, c15, // mm
                c17, c18, // ss
                c20, c21, c22, c23, c24, c25, c26, c27, '0'
        );
    }

    public static LocalDateTime parseLocalDateTime29(byte[] str, int off) {
        if (off + 29 > str.length) {
            return null;
        }

        char c0 = (char) str[off];
        char c1 = (char) str[off + 1];
        char c2 = (char) str[off + 2];
        char c3 = (char) str[off + 3];
        char c4 = (char) str[off + 4];
        char c5 = (char) str[off + 5];
        char c6 = (char) str[off + 6];
        char c7 = (char) str[off + 7];
        char c8 = (char) str[off + 8];
        char c9 = (char) str[off + 9];
        char c10 = (char) str[off + 10];
        char c11 = (char) str[off + 11];
        char c12 = (char) str[off + 12];
        char c13 = (char) str[off + 13];
        char c14 = (char) str[off + 14];
        char c15 = (char) str[off + 15];
        char c16 = (char) str[off + 16];
        char c17 = (char) str[off + 17];
        char c18 = (char) str[off + 18];
        char c19 = (char) str[off + 19];
        char c20 = (char) str[off + 20];
        char c21 = (char) str[off + 21];
        char c22 = (char) str[off + 22];
        char c23 = (char) str[off + 23];
        char c24 = (char) str[off + 24];
        char c25 = (char) str[off + 25];
        char c26 = (char) str[off + 26];
        char c27 = (char) str[off + 27];
        char c28 = (char) str[off + 28];

        if (c4 != '-' || c7 != '-' || (c10 != ' ' && c10 != 'T') || c13 != ':' || c16 != ':' || c19 != '.') {
            return null;
        }

        return localDateTime(
                c0, c1, c2, c3, // yyyy
                c5, c6, // MM
                c8, c9, // dd
                c11, c12, // HH
                c14, c15, // mm
                c17, c18, // ss
                c20, c21, c22, c23, c24, c25, c26, c27, c28
        );
    }

    public static LocalDateTime parseLocalDateTime29(char[] str, int off) {
        if (off + 29 > str.length) {
            return null;
        }

        char c0 = str[off];
        char c1 = str[off + 1];
        char c2 = str[off + 2];
        char c3 = str[off + 3];
        char c4 = str[off + 4];
        char c5 = str[off + 5];
        char c6 = str[off + 6];
        char c7 = str[off + 7];
        char c8 = str[off + 8];
        char c9 = str[off + 9];
        char c10 = str[off + 10];
        char c11 = str[off + 11];
        char c12 = str[off + 12];
        char c13 = str[off + 13];
        char c14 = str[off + 14];
        char c15 = str[off + 15];
        char c16 = str[off + 16];
        char c17 = str[off + 17];
        char c18 = str[off + 18];
        char c19 = str[off + 19];
        char c20 = str[off + 20];
        char c21 = str[off + 21];
        char c22 = str[off + 22];
        char c23 = str[off + 23];
        char c24 = str[off + 24];
        char c25 = str[off + 25];
        char c26 = str[off + 26];
        char c27 = str[off + 27];
        char c28 = str[off + 28];

        if (c4 != '-' || c7 != '-' || (c10 != ' ' && c10 != 'T') || c13 != ':' || c16 != ':' || c19 != '.') {
            return null;
        }

        return localDateTime(
                c0, c1, c2, c3, // yyyy
                c5, c6, // MM
                c8, c9, // dd
                c11, c12, // HH
                c14, c15, // mm
                c17, c18, // ss
                c20, c21, c22, c23, c24, c25, c26, c27, c28 // S0 - S9
        );
    }

    public static LocalDateTime parseLocalDateTimeX(char[] str, int offset, int len) {
        if (str == null || len == 0) {
            return null;
        }

        if (len < 21 || len > 29) {
            return null;
        }

        char c0 = str[offset];
        char c1 = str[offset + 1];
        char c2 = str[offset + 2];
        char c3 = str[offset + 3];
        char c4 = str[offset + 4];
        char c5 = str[offset + 5];
        char c6 = str[offset + 6];
        char c7 = str[offset + 7];
        char c8 = str[offset + 8];
        char c9 = str[offset + 9];
        char c10 = str[offset + 10];
        char c11 = str[offset + 11];
        char c12 = str[offset + 12];
        char c13 = str[offset + 13];
        char c14 = str[offset + 14];
        char c15 = str[offset + 15];
        char c16 = str[offset + 16];
        char c17 = str[offset + 17];
        char c18 = str[offset + 18];
        char c19 = str[offset + 19];
        char c20, c21 = '0', c22 = '0', c23 = '0', c24 = '0', c25 = '0', c26 = '0', c27 = '0', c28 = '0';
        switch (len) {
            case 21:
                c20 = str[offset + 20];
                break;
            case 22:
                c20 = str[offset + 20];
                c21 = str[offset + 21];
                break;
            case 23:
                c20 = str[offset + 20];
                c21 = str[offset + 21];
                c22 = str[offset + 22];
                break;
            case 24:
                c20 = str[offset + 20];
                c21 = str[offset + 21];
                c22 = str[offset + 22];
                c23 = str[offset + 23];
                break;
            case 25:
                c20 = str[offset + 20];
                c21 = str[offset + 21];
                c22 = str[offset + 22];
                c23 = str[offset + 23];
                c24 = str[offset + 24];
                break;
            case 26:
                c20 = str[offset + 20];
                c21 = str[offset + 21];
                c22 = str[offset + 22];
                c23 = str[offset + 23];
                c24 = str[offset + 24];
                c25 = str[offset + 25];
                break;
            case 27:
                c20 = str[offset + 20];
                c21 = str[offset + 21];
                c22 = str[offset + 22];
                c23 = str[offset + 23];
                c24 = str[offset + 24];
                c25 = str[offset + 25];
                c26 = str[offset + 26];
                break;
            case 28:
                c20 = str[offset + 20];
                c21 = str[offset + 21];
                c22 = str[offset + 22];
                c23 = str[offset + 23];
                c24 = str[offset + 24];
                c25 = str[offset + 25];
                c26 = str[offset + 26];
                c27 = str[offset + 27];
                break;
            default:
                c20 = str[offset + 20];
                c21 = str[offset + 21];
                c22 = str[offset + 22];
                c23 = str[offset + 23];
                c24 = str[offset + 24];
                c25 = str[offset + 25];
                c26 = str[offset + 26];
                c27 = str[offset + 27];
                c28 = str[offset + 28];
                break;
        }

        char y0, y1, y2, y3, m0, m1, d0, d1, h0, h1, i0, i1, s0, s1, S0, S1, S2, S3, S4, S5, S6, S7, S8;
        if (c4 == '-' && c7 == '-' && (c10 == ' ' || c10 == 'T') && c13 == ':' && c16 == ':' && c19 == '.') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = c17;
            s1 = c18;

            S0 = c20;
            S1 = c21;
            S2 = c22;
            S3 = c23;
            S4 = c24;
            S5 = c25;
            S6 = c26;
            S7 = c27;
            S8 = c28;
        } else {
            return null;
        }

        return localDateTime(y0, y1, y2, y3, m0, m1, d0, d1, h0, h1, i0, i1, s0, s1, S0, S1, S2, S3, S4, S5, S6, S7, S8);
    }

    public static LocalDateTime parseLocalDateTimeX(byte[] str, int offset, int len) {
        if (str == null || len == 0) {
            return null;
        }

        if (len < 21 || len > 29) {
            return null;
        }

        char c0 = (char) str[offset];
        char c1 = (char) str[offset + 1];
        char c2 = (char) str[offset + 2];
        char c3 = (char) str[offset + 3];
        char c4 = (char) str[offset + 4];
        char c5 = (char) str[offset + 5];
        char c6 = (char) str[offset + 6];
        char c7 = (char) str[offset + 7];
        char c8 = (char) str[offset + 8];
        char c9 = (char) str[offset + 9];
        char c10 = (char) str[offset + 10];
        char c11 = (char) str[offset + 11];
        char c12 = (char) str[offset + 12];
        char c13 = (char) str[offset + 13];
        char c14 = (char) str[offset + 14];
        char c15 = (char) str[offset + 15];
        char c16 = (char) str[offset + 16];
        char c17 = (char) str[offset + 17];
        char c18 = (char) str[offset + 18];
        char c19 = (char) str[offset + 19];
        char c20, c21 = '0', c22 = '0', c23 = '0', c24 = '0', c25 = '0', c26 = '0', c27 = '0', c28 = '0';
        switch (len) {
            case 21:
                c20 = (char) str[offset + 20];
                break;
            case 22:
                c20 = (char) str[offset + 20];
                c21 = (char) str[offset + 21];
                break;
            case 23:
                c20 = (char) str[offset + 20];
                c21 = (char) str[offset + 21];
                c22 = (char) str[offset + 22];
                break;
            case 24:
                c20 = (char) str[offset + 20];
                c21 = (char) str[offset + 21];
                c22 = (char) str[offset + 22];
                c23 = (char) str[offset + 23];
                break;
            case 25:
                c20 = (char) str[offset + 20];
                c21 = (char) str[offset + 21];
                c22 = (char) str[offset + 22];
                c23 = (char) str[offset + 23];
                c24 = (char) str[offset + 24];
                break;
            case 26:
                c20 = (char) str[offset + 20];
                c21 = (char) str[offset + 21];
                c22 = (char) str[offset + 22];
                c23 = (char) str[offset + 23];
                c24 = (char) str[offset + 24];
                c25 = (char) str[offset + 25];
                break;
            case 27:
                c20 = (char) str[offset + 20];
                c21 = (char) str[offset + 21];
                c22 = (char) str[offset + 22];
                c23 = (char) str[offset + 23];
                c24 = (char) str[offset + 24];
                c25 = (char) str[offset + 25];
                c26 = (char) str[offset + 26];
                break;
            case 28:
                c20 = (char) str[offset + 20];
                c21 = (char) str[offset + 21];
                c22 = (char) str[offset + 22];
                c23 = (char) str[offset + 23];
                c24 = (char) str[offset + 24];
                c25 = (char) str[offset + 25];
                c26 = (char) str[offset + 26];
                c27 = (char) str[offset + 27];
                break;
            default:
                c20 = (char) str[offset + 20];
                c21 = (char) str[offset + 21];
                c22 = (char) str[offset + 22];
                c23 = (char) str[offset + 23];
                c24 = (char) str[offset + 24];
                c25 = (char) str[offset + 25];
                c26 = (char) str[offset + 26];
                c27 = (char) str[offset + 27];
                c28 = (char) str[offset + 28];
                break;
        }

        char y0, y1, y2, y3, m0, m1, d0, d1, h0, h1, i0, i1, s0, s1, S0, S1, S2, S3, S4, S5, S6, S7, S8;
        if (c4 == '-' && c7 == '-' && (c10 == ' ' || c10 == 'T') && c13 == ':' && c16 == ':' && c19 == '.') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = c17;
            s1 = c18;

            S0 = c20;
            S1 = c21;
            S2 = c22;
            S3 = c23;
            S4 = c24;
            S5 = c25;
            S6 = c26;
            S7 = c27;
            S8 = c28;
        } else {
            return null;
        }

        return localDateTime(y0, y1, y2, y3, m0, m1, d0, d1, h0, h1, i0, i1, s0, s1, S0, S1, S2, S3, S4, S5, S6, S7, S8);
    }

    /**
     * ISO Date with offset, example '2011-12-03+01:00'
     */
    static ZonedDateTime parseZonedDateTime16(char[] str, int off, ZoneId defaultZonedId) {
        if (off + 16 > str.length) {
            String input = new String(str, off, str.length - off);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        char c0 = str[off];
        char c1 = str[off + 1];
        char c2 = str[off + 2];
        char c3 = str[off + 3];
        char c4 = str[off + 4];
        char c5 = str[off + 5];
        char c6 = str[off + 6];
        char c7 = str[off + 7];
        char c8 = str[off + 8];
        char c9 = str[off + 9];
        char c10 = str[off + 10];
        char c13 = str[off + 13];

        char y0, y1, y2, y3, m0, m1, d0, d1;
        if (c4 == '-' && c7 == '-' && (c10 == '+' || c10 == '-') && c13 == ':') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;
        } else {
            String input = new String(str, off, 16);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int year;
        if (y0 >= '0' && y0 <= '9'
                && y1 >= '0' && y1 <= '9'
                && y2 >= '0' && y2 <= '9'
                && y3 >= '0' && y3 <= '9'
        ) {
            year = (y0 - '0') * 1000 + (y1 - '0') * 100 + (y2 - '0') * 10 + (y3 - '0');
        } else {
            String input = new String(str, off, 16);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int month;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
        ) {
            month = (m0 - '0') * 10 + (m1 - '0');
        } else {
            String input = new String(str, off, 16);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int dom;
        if (d0 >= '0' && d0 <= '9'
                && d1 >= '0' && d1 <= '9'
        ) {
            dom = (d0 - '0') * 10 + (d1 - '0');
        } else {
            String input = new String(str, off, 16);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        ZoneId zoneId;
        String zoneIdStr = new String(str, off + 10, 6);
        zoneId = getZoneId(zoneIdStr, defaultZonedId);

        LocalDateTime ldt = LocalDateTime.of(LocalDate.of(year, month, dom), LocalTime.MIN);
        return ZonedDateTime.of(ldt, zoneId);
    }

    /**
     * ISO Date with offset, example '2011-12-03+01:00'
     */
    static ZonedDateTime parseZonedDateTime16(byte[] str, int off, ZoneId defaultZonedId) {
        if (off + 16 > str.length) {
            String input = new String(str, off, str.length - off);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        char c0 = (char) str[off];
        char c1 = (char) str[off + 1];
        char c2 = (char) str[off + 2];
        char c3 = (char) str[off + 3];
        char c4 = (char) str[off + 4];
        char c5 = (char) str[off + 5];
        char c6 = (char) str[off + 6];
        char c7 = (char) str[off + 7];
        char c8 = (char) str[off + 8];
        char c9 = (char) str[off + 9];
        char c10 = (char) str[off + 10];
        char c13 = (char) str[off + 13];

        char y0, y1, y2, y3, m0, m1, d0, d1;
        if (c4 == '-' && c7 == '-' && (c10 == '+' || c10 == '-') && c13 == ':') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;
        } else {
            String input = new String(str, off, 16);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int year;
        if (y0 >= '0' && y0 <= '9'
                && y1 >= '0' && y1 <= '9'
                && y2 >= '0' && y2 <= '9'
                && y3 >= '0' && y3 <= '9'
        ) {
            year = (y0 - '0') * 1000 + (y1 - '0') * 100 + (y2 - '0') * 10 + (y3 - '0');
        } else {
            String input = new String(str, off, 16);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int month;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
        ) {
            month = (m0 - '0') * 10 + (m1 - '0');
        } else {
            String input = new String(str, off, 16);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        int dom;
        if (d0 >= '0' && d0 <= '9'
                && d1 >= '0' && d1 <= '9'
        ) {
            dom = (d0 - '0') * 10 + (d1 - '0');
        } else {
            String input = new String(str, off, 16);
            throw new DateTimeParseException("illegal input " + input, input, 0);
        }

        ZoneId zoneId;
        String zoneIdStr = new String(str, off + 10, 6);
        zoneId = getZoneId(zoneIdStr, defaultZonedId);

        LocalDateTime ldt = LocalDateTime.of(LocalDate.of(year, month, dom), LocalTime.MIN);
        return ZonedDateTime.of(ldt, zoneId);
    }



   
    public static ZoneId getZoneId(String zoneIdStr, ZoneId defaultZoneId) {
        if (zoneIdStr == null) {
            return defaultZoneId != null ? defaultZoneId : ZoneId.systemDefault();
        }

        ZoneId zoneId;
        int p0, p1;
        switch (zoneIdStr) {
            case "000":
                zoneId = ZoneOffset.UTC;
                break;
            case "+08:00":
                zoneId = OFFSET_8_ZONE_ID;
                break;
            case "CST":
                zoneId = SHANGHAI_ZONE_ID;
                break;
            default:
                char c0;
                if (zoneIdStr.length() > 0 && ((c0 = zoneIdStr.charAt(0)) == '+' || c0 == '-') && zoneIdStr.charAt(zoneIdStr.length() - 1) != ']') {
                    zoneId = ZoneOffset.of(zoneIdStr);
                } else if ((p0 = zoneIdStr.indexOf('[')) > 0 && (p1 = zoneIdStr.indexOf(']', p0)) > 0) {
                    String str = zoneIdStr.substring(p0 + 1, p1);
                    zoneId = ZoneId.of(str);
                } else {
                    zoneId = ZoneId.of(zoneIdStr);
                }
                break;
        }
        return zoneId;
    }

    public static long parseMillisYMDHMS19(String str, ZoneId zoneId) {
        if (str == null) {
            return 0;
        }

        char c0, c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18;
        
        if (str.length() != 19) {
            throw new DateTimeParseException("illegal input " + str, str, 0);
        }

        c0 = str.charAt(0);
        c1 = str.charAt(1);
        c2 = str.charAt(2);
        c3 = str.charAt(3);
        c4 = str.charAt(4);
        c5 = str.charAt(5);
        c6 = str.charAt(6);
        c7 = str.charAt(7);
        c8 = str.charAt(8);
        c9 = str.charAt(9);
        c10 = str.charAt(10);
        c11 = str.charAt(11);
        c12 = str.charAt(12);
        c13 = str.charAt(13);
        c14 = str.charAt(14);
        c15 = str.charAt(15);
        c16 = str.charAt(16);
        c17 = str.charAt(17);
        c18 = str.charAt(18);
        

        if (c4 != '-' || c7 != '-' || c10 != ' ' || c13 != ':' || c16 != ':') {
            throw new DateTimeParseException("illegal input", str, 0);
        }

        char y0 = c0;
        char y1 = c1;
        char y2 = c2;
        char y3 = c3;

        char m0 = c5;
        char m1 = c6;

        char d0 = c8;
        char d1 = c9;

        char h0 = c11;
        char h1 = c12;

        char i0 = c14;
        char i1 = c15;

        char s0 = c17;
        char s1 = c18;

        int year;
        if (y0 >= '0' && y0 <= '9'
                && y1 >= '0' && y1 <= '9'
                && y2 >= '0' && y2 <= '9'
                && y3 >= '0' && y3 <= '9'
        ) {
            year = (y0 - '0') * 1000 + (y1 - '0') * 100 + (y2 - '0') * 10 + (y3 - '0');
        } else {
            throw new DateTimeParseException("illegal input", str, 0);
        }

        int month;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
        ) {
            month = (m0 - '0') * 10 + (m1 - '0');

            if ((month == 0 && year != 0) || month > 12) {
                throw new DateTimeParseException("illegal input", str, 0);
            }
        } else {
            throw new DateTimeParseException("illegal input", str, 0);
        }

        int dom;
        if (d0 >= '0' && d0 <= '9'
                && d1 >= '0' && d1 <= '9'
        ) {
            dom = (d0 - '0') * 10 + (d1 - '0');

            int max = 31;
            switch (month) {
                case 2:
                    boolean leapYear = (year & 3) == 0 && ((year % 100) != 0 || (year % 400) == 0);
                    max = leapYear ? 29 : 28;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    max = 30;
                    break;
            }

            if ((dom == 0 && year != 0) || dom > max) {
                throw new DateTimeParseException("illegal input", str, 0);
            }
        } else {
            throw new DateTimeParseException("illegal input", str, 0);
        }

        int hour;
        if (h0 >= '0' && h0 <= '9'
                && h1 >= '0' && h1 <= '9'
        ) {
            hour = (h0 - '0') * 10 + (h1 - '0');
        } else {
            throw new DateTimeParseException("illegal input", str, 0);
        }

        int minute;
        if (i0 >= '0' && i0 <= '9'
                && i1 >= '0' && i1 <= '9'
        ) {
            minute = (i0 - '0') * 10 + (i1 - '0');
        } else {
            throw new DateTimeParseException("illegal input", str, 0);
        }

        int second;
        if (s0 >= '0' && s0 <= '9'
                && s1 >= '0' && s1 <= '9'
        ) {
            second = (s0 - '0') * 10 + (s1 - '0');
        } else {
            throw new DateTimeParseException("illegal input", str, 0);
        }

        if (year == 0 && month == 0 && dom == 0) {
            year = 1970;
            month = 1;
            dom = 1;
        }

        long utcSeconds;
        {
            final int DAYS_PER_CYCLE = 146097;
            final long DAYS_0000_TO_1970 = (DAYS_PER_CYCLE * 5L) - (30L * 365L + 7L);

            long total = (365 * year)
                    + ((year + 3) / 4 - (year + 99) / 100 + (year + 399) / 400)
                    + ((367 * month - 362) / 12)
                    + (dom - 1);

            if (month > 2) {
                total--;
                boolean leapYear = (year & 3) == 0 && ((year % 100) != 0 || (year % 400) == 0);
                if (!leapYear) {
                    total--;
                }
            }

            long epochDay = total - DAYS_0000_TO_1970;
            utcSeconds = epochDay * 86400
                    + hour * 3600
                    + minute * 60
                    + second;
        }

        int zoneOffsetTotalSeconds;

        if (zoneId == null) {
            zoneId = DEFAULT_ZONE_ID;
        }
        boolean shanghai = zoneId == SHANGHAI_ZONE_ID || zoneId.getRules() == SHANGHAI_ZONE_RULES;
        long SECONDS_1991_09_15_02 = 684900000; // utcMillis(1991, 9, 15, 2, 0, 0);
        if (shanghai && utcSeconds >= SECONDS_1991_09_15_02) {
            zoneOffsetTotalSeconds = 28800; // OFFSET_0800_TOTAL_SECONDS
        } else if (zoneId == ZoneOffset.UTC || "UTC".equals(zoneId.getId())) {
            zoneOffsetTotalSeconds = 0;
        } else {
            LocalDate localDate = LocalDate.of(year, month, dom);
            LocalTime localTime = LocalTime.of(hour, minute, second, 0);
            LocalDateTime ldt = LocalDateTime.of(localDate, localTime);
            ZoneOffset offset = zoneId.getRules().getOffset(ldt);
            zoneOffsetTotalSeconds = offset.getTotalSeconds();
        }

        return (utcSeconds - zoneOffsetTotalSeconds) * 1000L;
    }

    static long parseMillis19(
            String str,
            ZoneId zoneId,
            DateTimeFormatPattern pattern
    ) {
        if (str == null || "null".equals(str)) {
            return 0;
        }

        if (pattern.length != 19) {
            throw new UnsupportedOperationException();
        }

        char c0, c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18;
        if (str.length() != 19) {
            throw new DateTimeParseException("illegal input " + str, str, 0);
        }

        c0 = str.charAt(0);
        c1 = str.charAt(1);
        c2 = str.charAt(2);
        c3 = str.charAt(3);
        c4 = str.charAt(4);
        c5 = str.charAt(5);
        c6 = str.charAt(6);
        c7 = str.charAt(7);
        c8 = str.charAt(8);
        c9 = str.charAt(9);
        c10 = str.charAt(10);
        c11 = str.charAt(11);
        c12 = str.charAt(12);
        c13 = str.charAt(13);
        c14 = str.charAt(14);
        c15 = str.charAt(15);
        c16 = str.charAt(16);
        c17 = str.charAt(17);
        c18 = str.charAt(18);
        

        char y0, y1, y2, y3, m0, m1, d0, d1, h0, h1, i0, i1, s0, s1;
        switch (pattern) {
            case DATE_TIME_FORMAT_19_DASH:
                if (c4 != '-' || c7 != '-' || c10 != ' ' || c13 != ':' || c16 != ':') {
                    throw new DateTimeParseException("illegal input", str, 0);
                }
                y0 = c0;
                y1 = c1;
                y2 = c2;
                y3 = c3;

                m0 = c5;
                m1 = c6;

                d0 = c8;
                d1 = c9;

                h0 = c11;
                h1 = c12;

                i0 = c14;
                i1 = c15;

                s0 = c17;
                s1 = c18;
                break;
            case DATE_TIME_FORMAT_19_DASH_T:
                if (c4 != '-' || c7 != '-' || c10 != 'T' || c13 != ':' || c16 != ':') {
                    throw new DateTimeParseException("illegal input", str, 0);
                }
                y0 = c0;
                y1 = c1;
                y2 = c2;
                y3 = c3;

                m0 = c5;
                m1 = c6;

                d0 = c8;
                d1 = c9;

                h0 = c11;
                h1 = c12;

                i0 = c14;
                i1 = c15;

                s0 = c17;
                s1 = c18;
                break;
            case DATE_TIME_FORMAT_19_SLASH:
                if (c4 != '/' || c7 != '/' || c10 != ' ' || c13 != ':' || c16 != ':') {
                    throw new DateTimeParseException("illegal input", str, 0);
                }
                y0 = c0;
                y1 = c1;
                y2 = c2;
                y3 = c3;

                m0 = c5;
                m1 = c6;

                d0 = c8;
                d1 = c9;

                h0 = c11;
                h1 = c12;

                i0 = c14;
                i1 = c15;

                s0 = c17;
                s1 = c18;
                break;
            case DATE_TIME_FORMAT_19_DOT:
                if (c2 != '.' || c5 != '.' || c10 != ' ' || c13 != ':' || c16 != ':') {
                    throw new DateTimeParseException("illegal input", str, 0);
                }
                d0 = c0;
                d1 = c1;

                m0 = c3;
                m1 = c4;

                y0 = c6;
                y1 = c7;
                y2 = c8;
                y3 = c9;

                h0 = c11;
                h1 = c12;

                i0 = c14;
                i1 = c15;

                s0 = c17;
                s1 = c18;
                break;
            default:
                throw new DateTimeParseException("illegal input", str, 0);
        }

        int year;
        if (y0 >= '0' && y0 <= '9'
                && y1 >= '0' && y1 <= '9'
                && y2 >= '0' && y2 <= '9'
                && y3 >= '0' && y3 <= '9'
        ) {
            year = (y0 - '0') * 1000 + (y1 - '0') * 100 + (y2 - '0') * 10 + (y3 - '0');
        } else {
            throw new DateTimeParseException("illegal input", str, 0);
        }

        int month;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
        ) {
            month = (m0 - '0') * 10 + (m1 - '0');

            if ((month == 0 && year != 0) || month > 12) {
                throw new DateTimeParseException("illegal input", str, 0);
            }
        } else {
            throw new DateTimeParseException("illegal input", str, 0);
        }

        int dom;
        if (d0 >= '0' && d0 <= '9'
                && d1 >= '0' && d1 <= '9'
        ) {
            dom = (d0 - '0') * 10 + (d1 - '0');

            int max = 31;
            switch (month) {
                case 2:
                    boolean leapYear = (year & 3) == 0 && ((year % 100) != 0 || (year % 400) == 0);
                    max = leapYear ? 29 : 28;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    max = 30;
                    break;
            }

            if ((dom == 0 && year != 0) || dom > max) {
                throw new DateTimeParseException("illegal input", str, 0);
            }
        } else {
            throw new DateTimeParseException("illegal input", str, 0);
        }

        int hour;
        if (h0 >= '0' && h0 <= '9'
                && h1 >= '0' && h1 <= '9'
        ) {
            hour = (h0 - '0') * 10 + (h1 - '0');
        } else {
            throw new DateTimeParseException("illegal input", str, 0);
        }

        int minute;
        if (i0 >= '0' && i0 <= '9'
                && i1 >= '0' && i1 <= '9'
        ) {
            minute = (i0 - '0') * 10 + (i1 - '0');
        } else {
            throw new DateTimeParseException("illegal input", str, 0);
        }

        int second;
        if (s0 >= '0' && s0 <= '9'
                && s1 >= '0' && s1 <= '9'
        ) {
            second = (s0 - '0') * 10 + (s1 - '0');
        } else {
            throw new DateTimeParseException("illegal input", str, 0);
        }

        if (year == 0 && month == 0 && dom == 0) {
            year = 1970;
            month = 1;
            dom = 1;
        }

        long utcSeconds;
        {
            final int DAYS_PER_CYCLE = 146097;
            final long DAYS_0000_TO_1970 = (DAYS_PER_CYCLE * 5L) - (30L * 365L + 7L);

            long total = (365 * year)
                    + ((year + 3) / 4 - (year + 99) / 100 + (year + 399) / 400)
                    + ((367 * month - 362) / 12)
                    + (dom - 1);

            if (month > 2) {
                total--;
                boolean leapYear = (year & 3) == 0 && ((year % 100) != 0 || (year % 400) == 0);
                if (!leapYear) {
                    total--;
                }
            }

            long epochDay = total - DAYS_0000_TO_1970;
            utcSeconds = epochDay * 86400
                    + hour * 3600
                    + minute * 60
                    + second;
        }

        int zoneOffsetTotalSeconds;

        if (zoneId == null) {
            zoneId = DEFAULT_ZONE_ID;
        }
        boolean shanghai = zoneId == SHANGHAI_ZONE_ID || zoneId.getRules() == SHANGHAI_ZONE_RULES;
        long SECONDS_1991_09_15_02 = 684900000; // utcMillis(1991, 9, 15, 2, 0, 0);
        if (shanghai && utcSeconds >= SECONDS_1991_09_15_02) {
            zoneOffsetTotalSeconds = 28800; // OFFSET_0800_TOTAL_SECONDS
        } else if (zoneId == ZoneOffset.UTC || "UTC".equals(zoneId.getId())) {
            zoneOffsetTotalSeconds = 0;
        } else {
            LocalDate localDate = LocalDate.of(year, month, dom);
            LocalTime localTime = LocalTime.of(hour, minute, second, 0);
            LocalDateTime ldt = LocalDateTime.of(localDate, localTime);
            ZoneOffset offset = zoneId.getRules().getOffset(ldt);
            zoneOffsetTotalSeconds = offset.getTotalSeconds();
        }

        return (utcSeconds - zoneOffsetTotalSeconds) * 1000L;
    }

    static long parseMillis10(
            String str,
            ZoneId zoneId,
            DateTimeFormatPattern pattern
    ) {
        if (str == null || "null".equals(str)) {
            return 0;
        }

        if (pattern.length != 10) {
            throw new UnsupportedOperationException();
        }

        char c0, c1, c2, c3, c4, c5, c6, c7, c8, c9;
        
        if (str.length() != 10) {
            throw new DateTimeParseException("illegal input " + str, str, 0);
        }

        c0 = str.charAt(0);
        c1 = str.charAt(1);
        c2 = str.charAt(2);
        c3 = str.charAt(3);
        c4 = str.charAt(4);
        c5 = str.charAt(5);
        c6 = str.charAt(6);
        c7 = str.charAt(7);
        c8 = str.charAt(8);
        c9 = str.charAt(9);
        

        char y0, y1, y2, y3, m0, m1, d0, d1;
        switch (pattern) {
            case DATE_FORMAT_10_DASH:
                if (c4 != '-' || c7 != '-') {
                    throw new DateTimeParseException("illegal input", str, 0);
                }
                y0 = c0;
                y1 = c1;
                y2 = c2;
                y3 = c3;

                m0 = c5;
                m1 = c6;

                d0 = c8;
                d1 = c9;
                break;
            case DATE_FORMAT_10_SLASH:
                if (c4 != '/' || c7 != '/') {
                    throw new DateTimeParseException("illegal input", str, 0);
                }
                y0 = c0;
                y1 = c1;
                y2 = c2;
                y3 = c3;

                m0 = c5;
                m1 = c6;

                d0 = c8;
                d1 = c9;
                break;
            default:
                throw new DateTimeParseException("illegal input", str, 0);
        }

        int year;
        if (y0 >= '0' && y0 <= '9'
                && y1 >= '0' && y1 <= '9'
                && y2 >= '0' && y2 <= '9'
                && y3 >= '0' && y3 <= '9'
        ) {
            year = (y0 - '0') * 1000 + (y1 - '0') * 100 + (y2 - '0') * 10 + (y3 - '0');
        } else {
            throw new DateTimeParseException("illegal input", str, 0);
        }

        int month;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
        ) {
            month = (m0 - '0') * 10 + (m1 - '0');

            if ((month == 0 && year != 0) || month > 12) {
                throw new DateTimeParseException("illegal input", str, 0);
            }
        } else {
            throw new DateTimeParseException("illegal input", str, 0);
        }

        int dom;
        if (d0 >= '0' && d0 <= '9'
                && d1 >= '0' && d1 <= '9'
        ) {
            dom = (d0 - '0') * 10 + (d1 - '0');

            int max = 31;
            switch (month) {
                case 2:
                    boolean leapYear = (year & 3) == 0 && ((year % 100) != 0 || (year % 400) == 0);
                    max = leapYear ? 29 : 28;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    max = 30;
                    break;
            }

            if ((dom == 0 && year != 0) || dom > max) {
                throw new DateTimeParseException("illegal input", str, 0);
            }
        } else {
            throw new DateTimeParseException("illegal input", str, 0);
        }

        if (year == 0 && month == 0 && dom == 0) {
            year = 1970;
            month = 1;
            dom = 1;
        }

        long utcSeconds;
        {
            final int DAYS_PER_CYCLE = 146097;
            final long DAYS_0000_TO_1970 = (DAYS_PER_CYCLE * 5L) - (30L * 365L + 7L);

            long total = (365 * year)
                    + ((year + 3) / 4 - (year + 99) / 100 + (year + 399) / 400)
                    + ((367 * month - 362) / 12)
                    + (dom - 1);

            if (month > 2) {
                total--;
                boolean leapYear = (year & 3) == 0 && ((year % 100) != 0 || (year % 400) == 0);
                if (!leapYear) {
                    total--;
                }
            }

            long epochDay = total - DAYS_0000_TO_1970;
            utcSeconds = epochDay * 86400;
        }

        int zoneOffsetTotalSeconds;

        boolean shanghai = zoneId == SHANGHAI_ZONE_ID || zoneId.getRules() == SHANGHAI_ZONE_RULES;
        long SECONDS_1991_09_15_02 = 684900000; // utcMillis(1991, 9, 15, 2, 0, 0);
        if (shanghai && utcSeconds >= SECONDS_1991_09_15_02) {
            zoneOffsetTotalSeconds = 28800; // OFFSET_0800_TOTAL_SECONDS
        } else if (zoneId == ZoneOffset.UTC || "UTC".equals(zoneId.getId())) {
            zoneOffsetTotalSeconds = 0;
        } else {
            LocalDate localDate = LocalDate.of(year, month, dom);
            LocalDateTime ldt = LocalDateTime.of(localDate, LocalTime.MIN);
            ZoneOffset offset = zoneId.getRules().getOffset(ldt);
            zoneOffsetTotalSeconds = offset.getTotalSeconds();
        }

        return (utcSeconds - zoneOffsetTotalSeconds) * 1000L;
    }

    public static long parseMillis19(String str, ZoneId zoneId) {
        if (str == null) {
            throw new NullPointerException();
        }

        char c0, c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18;
        
        if (str.length() != 19) {
            throw new DateTimeParseException("illegal input " + str, str, 0);
        }

        c0 = str.charAt(0);
        c1 = str.charAt(1);
        c2 = str.charAt(2);
        c3 = str.charAt(3);
        c4 = str.charAt(4);
        c5 = str.charAt(5);
        c6 = str.charAt(6);
        c7 = str.charAt(7);
        c8 = str.charAt(8);
        c9 = str.charAt(9);
        c10 = str.charAt(10);
        c11 = str.charAt(11);
        c12 = str.charAt(12);
        c13 = str.charAt(13);
        c14 = str.charAt(14);
        c15 = str.charAt(15);
        c16 = str.charAt(16);
        c17 = str.charAt(17);
        c18 = str.charAt(18);
        

        char y0, y1, y2, y3, m0, m1, d0, d1, h0, h1, i0, i1, s0, s1;
        if (c4 == '-' && c7 == '-' && (c10 == ' ' || c10 == 'T') && c13 == ':' && c16 == ':') {
            // yyyy-MM-dd HH:mm:ss
            // yyyy-MM-dd'T'HH:mm:ss
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = c17;
            s1 = c18;
        } else if (c4 == '/' && c7 == '/' && (c10 == ' ' || c10 == 'T') && c13 == ':' && c16 == ':') {
            // yyyy/MM/dd HH:mm:ss
            // yyyy/MM/dd'T'HH:mm:ss
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = c17;
            s1 = c18;
        } else if (c2 == '/' && c5 == '/' && c10 == ' ' && c13 == ':' && c16 == ':') {
            // dd/MM/yyyy HH:mm:ss
            d0 = c0;
            d1 = c1;

            m0 = c3;
            m1 = c4;

            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = c17;
            s1 = c18;
        } else if (c2 == '.' && c5 == '.' && c10 == ' ' && c13 == ':' && c16 == ':') {
            // dd.MM.yyyy HH:mm:ss
            d0 = c0;
            d1 = c1;

            m0 = c3;
            m1 = c4;

            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = c17;
            s1 = c18;
        } else if (c1 == ' ' && c5 == ' ' && c10 == ' ' && c13 == ':' && c16 == ':') {
            // d MMM yyyy HH:mm:ss
            // 6 DEC 2020 12:13:14
            d0 = '0';
            d1 = c0;

            int month = month(c2, c3, c4);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                throw new DateTimeParseException("illegal input " + str, str, 0);
            }

            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = c17;
            s1 = c18;
        } else if (c2 == ' ' && c6 == ' ' && c11 == ' ' && c13 == ':' && c16 == ':') {
            // dd MMM yyyy H:mm:ss
            // 16 DEC 2020 2:13:14
            d0 = c0;
            d1 = c1;

            int month = month(c3, c4, c5);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                throw new DateTimeParseException("illegal input " + str, str, 0);
            }

            y0 = c7;
            y1 = c8;
            y2 = c9;
            y3 = c10;

            h0 = '0';
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = c17;
            s1 = c18;
        } else if (c2 == ' ' && c6 == ' ' && c11 == ' ' && c14 == ':' && c16 == ':') {
            // dd MMM yyyy HH:m:ss
            // 16 DEC 2020 12:3:14
            d0 = c0;
            d1 = c1;

            int month = month(c3, c4, c5);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                throw new DateTimeParseException("illegal input " + str, str, 0);
            }

            y0 = c7;
            y1 = c8;
            y2 = c9;
            y3 = c10;

            h0 = c12;
            h1 = c13;

            i0 = '0';
            i1 = c15;

            s0 = c17;
            s1 = c18;
        } else if (c2 == ' ' && c6 == ' ' && c11 == ' ' && c14 == ':' && c17 == ':') {
            // dd MMM yyyy HH:m:ss
            // 16 DEC 2020 12:3:14
            d0 = c0;
            d1 = c1;

            int month = month(c3, c4, c5);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                throw new DateTimeParseException("illegal input " + str, str, 0);
            }

            y0 = c7;
            y1 = c8;
            y2 = c9;
            y3 = c10;

            h0 = c12;
            h1 = c13;

            i0 = c15;
            i1 = c16;

            s0 = '0';
            s1 = c18;
        } else {
            throw new DateTimeParseException("illegal input " + str, str, 0);
        }

        int year;
        if (y0 >= '0' && y0 <= '9'
                && y1 >= '0' && y1 <= '9'
                && y2 >= '0' && y2 <= '9'
                && y3 >= '0' && y3 <= '9'
        ) {
            year = (y0 - '0') * 1000 + (y1 - '0') * 100 + (y2 - '0') * 10 + (y3 - '0');
        } else {
            throw new DateTimeParseException("illegal input " + str, str, 0);
        }

        int month;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
        ) {
            month = (m0 - '0') * 10 + (m1 - '0');

            if ((month == 0 && year != 0) || month > 12) {
                throw new DateTimeParseException("illegal input " + str, str, 0);
            }
        } else {
            throw new DateTimeParseException("illegal input " + str, str, 0);
        }

        int dom;
        if (d0 >= '0' && d0 <= '9'
                && d1 >= '0' && d1 <= '9'
        ) {
            dom = (d0 - '0') * 10 + (d1 - '0');

            int max = 31;
            switch (month) {
                case 2:
                    boolean leapYear = (year & 3) == 0 && ((year % 100) != 0 || (year % 400) == 0);
                    max = leapYear ? 29 : 28;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    max = 30;
                    break;
            }

            if ((dom == 0 && year != 0) || dom > max) {
                throw new DateTimeParseException("illegal input " + str, str, 0);
            }
        } else {
            throw new DateTimeParseException("illegal input " + str, str, 0);
        }

        int hour;
        if (h0 >= '0' && h0 <= '9'
                && h1 >= '0' && h1 <= '9'
        ) {
            hour = (h0 - '0') * 10 + (h1 - '0');
        } else {
            throw new DateTimeParseException("illegal input " + str, str, 0);
        }

        int minute;
        if (i0 >= '0' && i0 <= '9'
                && i1 >= '0' && i1 <= '9'
        ) {
            minute = (i0 - '0') * 10 + (i1 - '0');
        } else {
            throw new DateTimeParseException("illegal input " + str, str, 0);
        }

        int second;
        if (s0 >= '0' && s0 <= '9'
                && s1 >= '0' && s1 <= '9'
        ) {
            second = (s0 - '0') * 10 + (s1 - '0');
        } else {
            throw new DateTimeParseException("illegal input " + str, str, 0);
        }

        if (year == 0 && month == 0 && dom == 0) {
            year = 1970;
            month = 1;
            dom = 1;
        }

        long utcSeconds;
        {
            final int DAYS_PER_CYCLE = 146097;
            final long DAYS_0000_TO_1970 = (DAYS_PER_CYCLE * 5L) - (30L * 365L + 7L);

            long total = (365 * year)
                    + ((year + 3) / 4 - (year + 99) / 100 + (year + 399) / 400)
                    + ((367 * month - 362) / 12)
                    + (dom - 1);

            if (month > 2) {
                total--;
                boolean leapYear = (year & 3) == 0 && ((year % 100) != 0 || (year % 400) == 0);
                if (!leapYear) {
                    total--;
                }
            }

            long epochDay = total - DAYS_0000_TO_1970;
            utcSeconds = epochDay * 86400
                    + hour * 3600
                    + minute * 60
                    + second;
        }

        int zoneOffsetTotalSeconds;

        if (zoneId == null) {
            zoneId = DEFAULT_ZONE_ID;
        }
        boolean shanghai = zoneId == SHANGHAI_ZONE_ID || zoneId.getRules() == SHANGHAI_ZONE_RULES;
        long SECONDS_1991_09_15_02 = 684900000; // utcMillis(1991, 9, 15, 2, 0, 0);
        if (shanghai && utcSeconds >= SECONDS_1991_09_15_02) {
            zoneOffsetTotalSeconds = 28800; // OFFSET_0800_TOTAL_SECONDS
        } else if (zoneId == ZoneOffset.UTC || "UTC".equals(zoneId.getId())) {
            zoneOffsetTotalSeconds = 0;
        } else {
            LocalDate localDate = LocalDate.of(year, month, dom);
            LocalTime localTime = LocalTime.of(hour, minute, second, 0);
            LocalDateTime ldt = LocalDateTime.of(localDate, localTime);
            ZoneOffset offset = zoneId.getRules().getOffset(ldt);
            zoneOffsetTotalSeconds = offset.getTotalSeconds();
        }

        return (utcSeconds - zoneOffsetTotalSeconds) * 1000L;
    }

    public static long parseMillis19(byte[] bytes, int off, ZoneId zoneId) {
        if (bytes == null) {
            throw new NullPointerException();
        }

        char c0, c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18;
        c0 = (char) bytes[off];
        c1 = (char) bytes[off + 1];
        c2 = (char) bytes[off + 2];
        c3 = (char) bytes[off + 3];
        c4 = (char) bytes[off + 4];
        c5 = (char) bytes[off + 5];
        c6 = (char) bytes[off + 6];
        c7 = (char) bytes[off + 7];
        c8 = (char) bytes[off + 8];
        c9 = (char) bytes[off + 9];
        c10 = (char) bytes[off + 10];
        c11 = (char) bytes[off + 11];
        c12 = (char) bytes[off + 12];
        c13 = (char) bytes[off + 13];
        c14 = (char) bytes[off + 14];
        c15 = (char) bytes[off + 15];
        c16 = (char) bytes[off + 16];
        c17 = (char) bytes[off + 17];
        c18 = (char) bytes[off + 18];

        char y0, y1, y2, y3, m0, m1, d0, d1, h0, h1, i0, i1, s0, s1;
        if (c4 == '-' && c7 == '-' && (c10 == ' ' || c10 == 'T') && c13 == ':' && c16 == ':') {
            // yyyy-MM-dd HH:mm:ss
            // yyyy-MM-dd'T'HH:mm:ss
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = c17;
            s1 = c18;
        } else if (c4 == '/' && c7 == '/' && (c10 == ' ' || c10 == 'T') && c13 == ':' && c16 == ':') {
            // yyyy/MM/dd HH:mm:ss
            // yyyy/MM/dd'T'HH:mm:ss
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = c17;
            s1 = c18;
        } else if (c2 == '/' && c5 == '/' && c10 == ' ' && c13 == ':' && c16 == ':') {
            // dd/MM/yyyy HH:mm:ss
            d0 = c0;
            d1 = c1;

            m0 = c3;
            m1 = c4;

            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = c17;
            s1 = c18;
        } else if (c2 == '.' && c5 == '.' && c10 == ' ' && c13 == ':' && c16 == ':') {
            // dd.MM.yyyy HH:mm:ss
            d0 = c0;
            d1 = c1;

            m0 = c3;
            m1 = c4;

            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = c17;
            s1 = c18;
        } else if (c1 == ' ' && c5 == ' ' && c10 == ' ' && c13 == ':' && c16 == ':') {
            // d MMM yyyy HH:mm:ss
            // 6 DEC 2020 12:13:14
            d0 = '0';
            d1 = c0;

            int month = month(c2, c3, c4);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                String str = new String(bytes, off, 19);
                throw new DateTimeParseException("illegal input " + str, str, 0);
            }

            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = c17;
            s1 = c18;
        } else if (c2 == ' ' && c6 == ' ' && c11 == ' ' && c13 == ':' && c16 == ':') {
            // dd MMM yyyy H:mm:ss
            // 16 DEC 2020 2:13:14
            d0 = c0;
            d1 = c1;

            int month = month(c3, c4, c5);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                String str = new String(bytes, off, 19);
                throw new DateTimeParseException("illegal input " + str, str, 0);
            }

            y0 = c7;
            y1 = c8;
            y2 = c9;
            y3 = c10;

            h0 = '0';
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = c17;
            s1 = c18;
        } else if (c2 == ' ' && c6 == ' ' && c11 == ' ' && c14 == ':' && c16 == ':') {
            // dd MMM yyyy HH:m:ss
            // 16 DEC 2020 12:3:14
            d0 = c0;
            d1 = c1;

            int month = month(c3, c4, c5);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                String str = new String(bytes, off, 19);
                throw new DateTimeParseException("illegal input " + str, str, 0);
            }

            y0 = c7;
            y1 = c8;
            y2 = c9;
            y3 = c10;

            h0 = c12;
            h1 = c13;

            i0 = '0';
            i1 = c15;

            s0 = c17;
            s1 = c18;
        } else if (c2 == ' ' && c6 == ' ' && c11 == ' ' && c14 == ':' && c17 == ':') {
            // dd MMM yyyy HH:m:ss
            // 16 DEC 2020 12:3:14
            d0 = c0;
            d1 = c1;

            int month = month(c3, c4, c5);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                String str = new String(bytes, off, 19);
                throw new DateTimeParseException("illegal input " + str, str, 0);
            }

            y0 = c7;
            y1 = c8;
            y2 = c9;
            y3 = c10;

            h0 = c12;
            h1 = c13;

            i0 = c15;
            i1 = c16;

            s0 = '0';
            s1 = c18;
        } else {
            String str = new String(bytes, off, 19);
            throw new DateTimeParseException("illegal input " + str, str, 0);
        }

        int year;
        if (y0 >= '0' && y0 <= '9'
                && y1 >= '0' && y1 <= '9'
                && y2 >= '0' && y2 <= '9'
                && y3 >= '0' && y3 <= '9'
        ) {
            year = (y0 - '0') * 1000 + (y1 - '0') * 100 + (y2 - '0') * 10 + (y3 - '0');
        } else {
            String str = new String(bytes, off, 19);
            throw new DateTimeParseException("illegal input " + str, str, 0);
        }

        int month;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
        ) {
            month = (m0 - '0') * 10 + (m1 - '0');

            if ((month == 0 && year != 0) || month > 12) {
                String str = new String(bytes, off, 19);
                throw new DateTimeParseException("illegal input " + str, str, 0);
            }
        } else {
            String str = new String(bytes, off, 19);
            throw new DateTimeParseException("illegal input " + str, str, 0);
        }

        int dom;
        if (d0 >= '0' && d0 <= '9'
                && d1 >= '0' && d1 <= '9'
        ) {
            dom = (d0 - '0') * 10 + (d1 - '0');

            int max = 31;
            switch (month) {
                case 2:
                    boolean leapYear = (year & 3) == 0 && ((year % 100) != 0 || (year % 400) == 0);
                    max = leapYear ? 29 : 28;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    max = 30;
                    break;
            }

            if ((dom == 0 && year != 0) || dom > max) {
                String str = new String(bytes, off, 19);
                throw new DateTimeParseException("illegal input " + str, str, 0);
            }
        } else {
            String str = new String(bytes, off, 19);
            throw new DateTimeParseException("illegal input " + str, str, 0);
        }

        int hour;
        if (h0 >= '0' && h0 <= '9'
                && h1 >= '0' && h1 <= '9'
        ) {
            hour = (h0 - '0') * 10 + (h1 - '0');
        } else {
            String str = new String(bytes, off, 19);
            throw new DateTimeParseException("illegal input " + str, str, 0);
        }

        int minute;
        if (i0 >= '0' && i0 <= '9'
                && i1 >= '0' && i1 <= '9'
        ) {
            minute = (i0 - '0') * 10 + (i1 - '0');
        } else {
            String str = new String(bytes, off, 19);
            throw new DateTimeParseException("illegal input " + str, str, 0);
        }

        int second;
        if (s0 >= '0' && s0 <= '9'
                && s1 >= '0' && s1 <= '9'
        ) {
            second = (s0 - '0') * 10 + (s1 - '0');
        } else {
            String str = new String(bytes, off, 19);
            throw new DateTimeParseException("illegal input " + str, str, 0);
        }

        if (year == 0 && month == 0 && dom == 0) {
            year = 1970;
            month = 1;
            dom = 1;
        }

        long utcSeconds;
        {
            final int DAYS_PER_CYCLE = 146097;
            final long DAYS_0000_TO_1970 = (DAYS_PER_CYCLE * 5L) - (30L * 365L + 7L);

            long total = (365 * year)
                    + ((year + 3) / 4 - (year + 99) / 100 + (year + 399) / 400)
                    + ((367 * month - 362) / 12)
                    + (dom - 1);

            if (month > 2) {
                total--;
                boolean leapYear = (year & 3) == 0 && ((year % 100) != 0 || (year % 400) == 0);
                if (!leapYear) {
                    total--;
                }
            }

            long epochDay = total - DAYS_0000_TO_1970;
            utcSeconds = epochDay * 86400
                    + hour * 3600
                    + minute * 60
                    + second;
        }

        int zoneOffsetTotalSeconds;

        if (zoneId == null) {
            zoneId = DEFAULT_ZONE_ID;
        }
        boolean shanghai = zoneId == SHANGHAI_ZONE_ID || zoneId.getRules() == SHANGHAI_ZONE_RULES;
        long SECONDS_1991_09_15_02 = 684900000; // utcMillis(1991, 9, 15, 2, 0, 0);
        if (shanghai && utcSeconds >= SECONDS_1991_09_15_02) {
            zoneOffsetTotalSeconds = 28800; // OFFSET_0800_TOTAL_SECONDS
        } else if (zoneId == ZoneOffset.UTC || "UTC".equals(zoneId.getId())) {
            zoneOffsetTotalSeconds = 0;
        } else {
            LocalDate localDate = LocalDate.of(year, month, dom);
            LocalTime localTime = LocalTime.of(hour, minute, second, 0);
            LocalDateTime ldt = LocalDateTime.of(localDate, localTime);
            ZoneOffset offset = zoneId.getRules().getOffset(ldt);
            zoneOffsetTotalSeconds = offset.getTotalSeconds();
        }

        return (utcSeconds - zoneOffsetTotalSeconds) * 1000L;
    }

    public static long parseMillis19(char[] bytes, int off, ZoneId zoneId) {
        if (bytes == null) {
            throw new NullPointerException();
        }

        char c0, c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18;
        c0 = bytes[off];
        c1 = bytes[off + 1];
        c2 = bytes[off + 2];
        c3 = bytes[off + 3];
        c4 = bytes[off + 4];
        c5 = bytes[off + 5];
        c6 = bytes[off + 6];
        c7 = bytes[off + 7];
        c8 = bytes[off + 8];
        c9 = bytes[off + 9];
        c10 = bytes[off + 10];
        c11 = bytes[off + 11];
        c12 = bytes[off + 12];
        c13 = bytes[off + 13];
        c14 = bytes[off + 14];
        c15 = bytes[off + 15];
        c16 = bytes[off + 16];
        c17 = bytes[off + 17];
        c18 = bytes[off + 18];

        char y0, y1, y2, y3, m0, m1, d0, d1, h0, h1, i0, i1, s0, s1;
        if (c4 == '-' && c7 == '-' && (c10 == ' ' || c10 == 'T') && c13 == ':' && c16 == ':') {
            // yyyy-MM-dd HH:mm:ss
            // yyyy-MM-dd'T'HH:mm:ss
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = c17;
            s1 = c18;
        } else if (c4 == '/' && c7 == '/' && (c10 == ' ' || c10 == 'T') && c13 == ':' && c16 == ':') {
            // yyyy/MM/dd HH:mm:ss
            // yyyy/MM/dd'T'HH:mm:ss
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;

            m0 = c5;
            m1 = c6;

            d0 = c8;
            d1 = c9;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = c17;
            s1 = c18;
        } else if (c2 == '/' && c5 == '/' && c10 == ' ' && c13 == ':' && c16 == ':') {
            // dd/MM/yyyy HH:mm:ss
            d0 = c0;
            d1 = c1;

            m0 = c3;
            m1 = c4;

            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = c17;
            s1 = c18;
        } else if (c2 == '.' && c5 == '.' && c10 == ' ' && c13 == ':' && c16 == ':') {
            // dd.MM.yyyy HH:mm:ss
            d0 = c0;
            d1 = c1;

            m0 = c3;
            m1 = c4;

            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = c17;
            s1 = c18;
        } else if (c1 == ' ' && c5 == ' ' && c10 == ' ' && c13 == ':' && c16 == ':') {
            // d MMM yyyy HH:mm:ss
            // 6 DEC 2020 12:13:14
            d0 = '0';
            d1 = c0;

            int month = month(c2, c3, c4);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                String str = new String(bytes, off, 19);
                throw new DateTimeParseException("illegal input " + str, str, 0);
            }

            y0 = c6;
            y1 = c7;
            y2 = c8;
            y3 = c9;

            h0 = c11;
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = c17;
            s1 = c18;
        } else if (c2 == ' ' && c6 == ' ' && c11 == ' ' && c13 == ':' && c16 == ':') {
            // dd MMM yyyy H:mm:ss
            // 16 DEC 2020 2:13:14
            d0 = c0;
            d1 = c1;

            int month = month(c3, c4, c5);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                String str = new String(bytes, off, 19);
                throw new DateTimeParseException("illegal input " + str, str, 0);
            }

            y0 = c7;
            y1 = c8;
            y2 = c9;
            y3 = c10;

            h0 = '0';
            h1 = c12;

            i0 = c14;
            i1 = c15;

            s0 = c17;
            s1 = c18;
        } else if (c2 == ' ' && c6 == ' ' && c11 == ' ' && c14 == ':' && c16 == ':') {
            // dd MMM yyyy HH:m:ss
            // 16 DEC 2020 12:3:14
            d0 = c0;
            d1 = c1;

            int month = month(c3, c4, c5);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                String str = new String(bytes, off, 19);
                throw new DateTimeParseException("illegal input " + str, str, 0);
            }

            y0 = c7;
            y1 = c8;
            y2 = c9;
            y3 = c10;

            h0 = c12;
            h1 = c13;

            i0 = '0';
            i1 = c15;

            s0 = c17;
            s1 = c18;
        } else if (c2 == ' ' && c6 == ' ' && c11 == ' ' && c14 == ':' && c17 == ':') {
            // dd MMM yyyy HH:m:ss
            // 16 DEC 2020 12:3:14
            d0 = c0;
            d1 = c1;

            int month = month(c3, c4, c5);
            if (month > 0) {
                m0 = (char) ('0' + month / 10);
                m1 = (char) ('0' + (month % 10));
            } else {
                String str = new String(bytes, off, 19);
                throw new DateTimeParseException("illegal input " + str, str, 0);
            }

            y0 = c7;
            y1 = c8;
            y2 = c9;
            y3 = c10;

            h0 = c12;
            h1 = c13;

            i0 = c15;
            i1 = c16;

            s0 = '0';
            s1 = c18;
        } else {
            String str = new String(bytes, off, 19);
            throw new DateTimeParseException("illegal input " + str, str, 0);
        }

        int year;
        if (y0 >= '0' && y0 <= '9'
                && y1 >= '0' && y1 <= '9'
                && y2 >= '0' && y2 <= '9'
                && y3 >= '0' && y3 <= '9'
        ) {
            year = (y0 - '0') * 1000 + (y1 - '0') * 100 + (y2 - '0') * 10 + (y3 - '0');
        } else {
            String str = new String(bytes, off, 19);
            throw new DateTimeParseException("illegal input " + str, str, 0);
        }

        int month;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
        ) {
            month = (m0 - '0') * 10 + (m1 - '0');

            if ((month == 0 && year != 0) || month > 12) {
                String str = new String(bytes, off, 19);
                throw new DateTimeParseException("illegal input " + str, str, 0);
            }
        } else {
            String str = new String(bytes, off, 19);
            throw new DateTimeParseException("illegal input " + str, str, 0);
        }

        int dom;
        if (d0 >= '0' && d0 <= '9'
                && d1 >= '0' && d1 <= '9'
        ) {
            dom = (d0 - '0') * 10 + (d1 - '0');

            int max = 31;
            switch (month) {
                case 2:
                    boolean leapYear = (year & 3) == 0 && ((year % 100) != 0 || (year % 400) == 0);
                    max = leapYear ? 29 : 28;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    max = 30;
                    break;
            }

            if ((dom == 0 && year != 0) || dom > max) {
                String str = new String(bytes, off, 19);
                throw new DateTimeParseException("illegal input " + str, str, 0);
            }
        } else {
            String str = new String(bytes, off, 19);
            throw new DateTimeParseException("illegal input " + str, str, 0);
        }

        int hour;
        if (h0 >= '0' && h0 <= '9'
                && h1 >= '0' && h1 <= '9'
        ) {
            hour = (h0 - '0') * 10 + (h1 - '0');
        } else {
            String str = new String(bytes, off, 19);
            throw new DateTimeParseException("illegal input " + str, str, 0);
        }

        int minute;
        if (i0 >= '0' && i0 <= '9'
                && i1 >= '0' && i1 <= '9'
        ) {
            minute = (i0 - '0') * 10 + (i1 - '0');
        } else {
            String str = new String(bytes, off, 19);
            throw new DateTimeParseException("illegal input " + str, str, 0);
        }

        int second;
        if (s0 >= '0' && s0 <= '9'
                && s1 >= '0' && s1 <= '9'
        ) {
            second = (s0 - '0') * 10 + (s1 - '0');
        } else {
            String str = new String(bytes, off, 19);
            throw new DateTimeParseException("illegal input " + str, str, 0);
        }

        if (year == 0 && month == 0 && dom == 0) {
            year = 1970;
            month = 1;
            dom = 1;
        }

        long utcSeconds;
        {
            final int DAYS_PER_CYCLE = 146097;
            final long DAYS_0000_TO_1970 = (DAYS_PER_CYCLE * 5L) - (30L * 365L + 7L);

            long total = (365 * year)
                    + ((year + 3) / 4 - (year + 99) / 100 + (year + 399) / 400)
                    + ((367 * month - 362) / 12)
                    + (dom - 1);

            if (month > 2) {
                total--;
                boolean leapYear = (year & 3) == 0 && ((year % 100) != 0 || (year % 400) == 0);
                if (!leapYear) {
                    total--;
                }
            }

            long epochDay = total - DAYS_0000_TO_1970;
            utcSeconds = epochDay * 86400
                    + hour * 3600
                    + minute * 60
                    + second;
        }

        int zoneOffsetTotalSeconds;

        if (zoneId == null) {
            zoneId = DEFAULT_ZONE_ID;
        }
        boolean shanghai = zoneId == SHANGHAI_ZONE_ID || zoneId.getRules() == SHANGHAI_ZONE_RULES;
        long SECONDS_1991_09_15_02 = 684900000; // utcMillis(1991, 9, 15, 2, 0, 0);
        if (shanghai && utcSeconds >= SECONDS_1991_09_15_02) {
            zoneOffsetTotalSeconds = 28800; // OFFSET_0800_TOTAL_SECONDS
        } else if (zoneId == ZoneOffset.UTC || "UTC".equals(zoneId.getId())) {
            zoneOffsetTotalSeconds = 0;
        } else {
            LocalDate localDate = LocalDate.of(year, month, dom);
            LocalTime localTime = LocalTime.of(hour, minute, second, 0);
            LocalDateTime ldt = LocalDateTime.of(localDate, localTime);
            ZoneOffset offset = zoneId.getRules().getOffset(ldt);
            zoneOffsetTotalSeconds = offset.getTotalSeconds();
        }

        return (utcSeconds - zoneOffsetTotalSeconds) * 1000L;
    }

    public static LocalDateTime localDateTime(
            char y0,
            char y1,
            char y2,
            char y3,
            char m0,
            char m1,
            char d0,
            char d1,
            char h0,
            char h1,
            char i0,
            char i1,
            char s0,
            char s1
    ) {
        int year;
        if (y0 >= '0' && y0 <= '9'
                && y1 >= '0' && y1 <= '9'
                && y2 >= '0' && y2 <= '9'
                && y3 >= '0' && y3 <= '9'
        ) {
            year = (y0 - '0') * 1000 + (y1 - '0') * 100 + (y2 - '0') * 10 + (y3 - '0');
        } else {
            return null;
        }

        int month;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
        ) {
            month = (m0 - '0') * 10 + (m1 - '0');
        } else {
            return null;
        }

        int dom;
        if (d0 >= '0' && d0 <= '9'
                && d1 >= '0' && d1 <= '9'
        ) {
            dom = (d0 - '0') * 10 + (d1 - '0');
        } else {
            return null;
        }

        int hour;
        if (h0 >= '0' && h0 <= '9'
                && h1 >= '0' && h1 <= '9'
        ) {
            hour = (h0 - '0') * 10 + (h1 - '0');
        } else {
            return null;
        }

        int minute;
        if (i0 >= '0' && i0 <= '9'
                && i1 >= '0' && i1 <= '9'
        ) {
            minute = (i0 - '0') * 10 + (i1 - '0');
        } else {
            return null;
        }

        int second;
        if (s0 >= '0' && s0 <= '9'
                && s1 >= '0' && s1 <= '9'
        ) {
            second = (s0 - '0') * 10 + (s1 - '0');
        } else {
            return null;
        }

        if (year == 0 && month == 0 && dom == 0 && hour == 0 && minute == 0 && second == 0) {
            return null;
        }

        if (hour > 24 || minute > 60 || second > 60) {
            return null;
        }

        return LocalDateTime.of(year, month, dom, hour, minute, second, 0);
    }

    public static LocalDateTime localDateTime(
            char y0,
            char y1,
            char y2,
            char y3,
            char m0,
            char m1,
            char d0,
            char d1,
            char h0,
            char h1,
            char i0,
            char i1,
            char s0,
            char s1,
            char S0,
            char S1,
            char S2,
            char S3,
            char S4,
            char S5,
            char S6,
            char S7,
            char S8) {
        int year;
        if (y0 >= '0' && y0 <= '9'
                && y1 >= '0' && y1 <= '9'
                && y2 >= '0' && y2 <= '9'
                && y3 >= '0' && y3 <= '9'
        ) {
            year = (y0 - '0') * 1000 + (y1 - '0') * 100 + (y2 - '0') * 10 + (y3 - '0');
        } else {
            return null;
        }

        int month;
        if (m0 >= '0' && m0 <= '9'
                && m1 >= '0' && m1 <= '9'
        ) {
            month = (m0 - '0') * 10 + (m1 - '0');
        } else {
            return null;
        }

        int dom;
        if (d0 >= '0' && d0 <= '9'
                && d1 >= '0' && d1 <= '9'
        ) {
            dom = (d0 - '0') * 10 + (d1 - '0');
        } else {
            return null;
        }

        int hour;
        if (h0 >= '0' && h0 <= '9'
                && h1 >= '0' && h1 <= '9'
        ) {
            hour = (h0 - '0') * 10 + (h1 - '0');
        } else {
            return null;
        }

        int minute;
        if (i0 >= '0' && i0 <= '9'
                && i1 >= '0' && i1 <= '9'
        ) {
            minute = (i0 - '0') * 10 + (i1 - '0');
        } else {
            return null;
        }

        int second;
        if (s0 >= '0' && s0 <= '9'
                && s1 >= '0' && s1 <= '9'
        ) {
            second = (s0 - '0') * 10 + (s1 - '0');
        } else {
            return null;
        }

        int nanos;
        if (S0 >= '0' && S0 <= '9'
                && S1 >= '0' && S1 <= '9'
                && S2 >= '0' && S2 <= '9'
                && S3 >= '0' && S3 <= '9'
                && S4 >= '0' && S4 <= '9'
                && S5 >= '0' && S5 <= '9'
                && S6 >= '0' && S6 <= '9'
                && S7 >= '0' && S7 <= '9'
                && S8 >= '0' && S8 <= '9'
        ) {
            nanos = (S0 - '0') * 1000_000_00
                    + (S1 - '0') * 1000_000_0
                    + (S2 - '0') * 1000_000
                    + (S3 - '0') * 1000_00
                    + (S4 - '0') * 1000_0
                    + (S5 - '0') * 1000
                    + (S6 - '0') * 100
                    + (S7 - '0') * 10
                    + (S8 - '0');
        } else {
            return null;
        }

        return LocalDateTime.of(year, month, dom, hour, minute, second, nanos);
    }

    public static long millis(LocalDateTime ldt) {
        return millis(
                null,
                ldt.getYear(),
                ldt.getMonthValue(),
                ldt.getDayOfMonth(),
                ldt.getHour(),
                ldt.getMinute(),
                ldt.getSecond(),
                ldt.getNano()
        );
    }

    public static long millis(LocalDateTime ldt, ZoneId zoneId) {
        return millis(
                zoneId,
                ldt.getYear(),
                ldt.getMonthValue(),
                ldt.getDayOfMonth(),
                ldt.getHour(),
                ldt.getMinute(),
                ldt.getSecond(),
                ldt.getNano()
        );
    }

    public static long millis(
            ZoneId zoneId,
            int year,
            int month,
            int dom,
            int hour,
            int minute,
            int second,
            int nanoOfSecond
    ) {
        if (zoneId == null) {
            zoneId = DEFAULT_ZONE_ID;
        }

        long utcSeconds;
        {
            final int DAYS_PER_CYCLE = 146097;
            final long DAYS_0000_TO_1970 = (DAYS_PER_CYCLE * 5L) - (30L * 365L + 7L);

            long total = (365 * year)
                    + ((year + 3) / 4 - (year + 99) / 100 + (year + 399) / 400)
                    + ((367 * month - 362) / 12)
                    + (dom - 1);

            if (month > 2) {
                total--;
                boolean leapYear = (year & 3) == 0 && ((year % 100) != 0 || (year % 400) == 0);
                if (!leapYear) {
                    total--;
                }
            }

            long epochDay = total - DAYS_0000_TO_1970;
            utcSeconds = epochDay * 86400
                    + hour * 3600
                    + minute * 60
                    + second;
        }

        int zoneOffsetTotalSeconds;

        boolean shanghai = zoneId == SHANGHAI_ZONE_ID || zoneId.getRules() == SHANGHAI_ZONE_RULES;
        long SECONDS_1991_09_15_02 = 684900000; // utcMillis(1991, 9, 15, 2, 0, 0);
        if (shanghai && utcSeconds >= SECONDS_1991_09_15_02) {
            zoneOffsetTotalSeconds = 28800; // OFFSET_0800_TOTAL_SECONDS
        } else if (zoneId == ZoneOffset.UTC || "UTC".equals(zoneId.getId())) {
            zoneOffsetTotalSeconds = 0;
        } else {
            LocalDate localDate = LocalDate.of(year, month, dom);
            LocalTime localTime = LocalTime.of(hour, minute, second, nanoOfSecond);
            LocalDateTime ldt = LocalDateTime.of(localDate, localTime);
            ZoneOffset offset = zoneId.getRules().getOffset(ldt);
            zoneOffsetTotalSeconds = offset.getTotalSeconds();
        }

        long millis = (utcSeconds - zoneOffsetTotalSeconds) * 1000L;
        if (nanoOfSecond != 0) {
            millis += nanoOfSecond / 1_000_000;
        }
        return millis;
    }

    public static long utcSeconds(
            int year,
            int month,
            int dom,
            int hour,
            int minute,
            int second
    ) {
        final int DAYS_PER_CYCLE = 146097;
        final long DAYS_0000_TO_1970 = (DAYS_PER_CYCLE * 5L) - (30L * 365L + 7L);

        long total = (365 * year)
                + ((year + 3) / 4 - (year + 99) / 100 + (year + 399) / 400)
                + ((367 * month - 362) / 12)
                + (dom - 1);

        if (month > 2) {
            total--;
            boolean leapYear = (year & 3) == 0 && ((year % 100) != 0 || (year % 400) == 0);
            if (!leapYear) {
                total--;
            }
        }

        long epochDay = total - DAYS_0000_TO_1970;
        return epochDay * 86400
                + hour * 3600
                + minute * 60
                + second;
    }
    
    


    public static int month(char c0, char c1, char c2) {
        switch (c0) {
            case 'J':
                // Jan
                if (c1 == 'a' && c2 == 'n') {
                    return 1;
                }
                if (c1 == 'u') {
                    if (c2 == 'n') {
                        return 6;
                    }
                    if (c2 == 'l') {
                        return 7;
                    }
                }
                break;
            case 'F':
                if (c1 == 'e' && c2 == 'b') {
                    return 2;
                }
                break;
            case 'M':
                if (c1 == 'a') {
                    if (c2 == 'r') {
                        return 3;
                    }
                    if (c2 == 'y') {
                        return 5;
                    }
                }
                break;
            case 'A':
                if (c1 == 'p' && c2 == 'r') {
                    return 4;
                }
                if (c1 == 'u' && c2 == 'g') {
                    return 8;
                }
                break;
            case 'S':
                if (c1 == 'e' && c2 == 'p') {
                    return 9;
                }
                break;
            case 'O':
                if (c1 == 'c' && c2 == 't') {
                    return 10;
                }
                break;
            case 'N':
                if (c1 == 'o' && c2 == 'v') {
                    return 11;
                }
                break;
            case 'D':
                if (c1 == 'e' && c2 == 'c') {
                    return 12;
                }
                break;
            default:
                break;
        }

        return 0;
    }

    public static int hourAfterNoon(char h0, char h1) {
        if (h0 == '0') {
            switch (h1) {
                case '0':
                    h0 = '1';
                    h1 = '2';
                    break;
                case '1':
                    h0 = '1';
                    h1 = '3';
                    break;
                case '2':
                    h0 = '1';
                    h1 = '4';
                    break;
                case '3':
                    h0 = '1';
                    h1 = '5';
                    break;
                case '4':
                    h0 = '1';
                    h1 = '6';
                    break;
                case '5':
                    h0 = '1';
                    h1 = '7';
                    break;
                case '6':
                    h0 = '1';
                    h1 = '8';
                    break;
                case '7':
                    h0 = '1';
                    h1 = '9';
                    break;
                case '8':
                    h0 = '2';
                    h1 = '0';
                    break;
                case '9':
                    h0 = '2';
                    h1 = '1';
                    break;
                default:
                    break;
            }
        } else if (h0 == '1') {
            switch (h1) {
                case '0':
                    h0 = '2';
                    h1 = '2';
                    break;
                case '1':
                    h0 = '2';
                    h1 = '3';
                    break;
                case '2':
                    h0 = '2';
                    h1 = '4';
                    break;
                default:
                    break;
            }
        }

        return h0 << 16 | h1;
    }

    public static int getShanghaiZoneOffsetTotalSeconds(long seconds) {
        long SECONDS_1991_09_15_02 = 684900000; // utcMillis(1991, 9, 15, 2, 0, 0);
        long SECONDS_1991_04_14_03 = 671598000; // utcMillis(1991, 4, 14, 3, 0, 0);
        long SECONDS_1990_09_16_02 = 653450400; // utcMillis(1990, 9, 16, 2, 0, 0);
        long SECONDS_1990_04_15_03 = 640148400; // utcMillis(1990, 4, 15, 3, 0, 0);
        long SECONDS_1989_09_17_02 = 622000800; // utcMillis(1989, 9, 17, 2, 0, 0);
        long SECONDS_1989_04_16_03 = 608698800; // utcMillis(1989, 4, 16, 3, 0, 0);
        long SECONDS_1988_09_11_02 = 589946400; // utcMillis(1988, 9, 11, 2, 0, 0);
        long SECONDS_1988_04_17_03 = 577249200; // utcMillis(1988, 4, 17, 3, 0, 0);
        long SECONDS_1987_09_13_02 = 558496800; // utcMillis(1987, 9, 13, 2, 0, 0);
        long SECONDS_1987_04_12_03 = 545194800; // utcMillis(1987, 4, 12, 3, 0, 0);
        long SECONDS_1986_09_14_02 = 527047200; // utcMillis(1986, 9, 14, 2, 0, 0);
        long SECONDS_1986_05_04_03 = 515559600; // utcMillis(1986, 5, 4, 3, 0, 0);
        long SECONDS_1949_05_28_00 = -649987200; // utcMillis(1949, 5, 28, 0, 0, 0);
        long SECONDS_1949_05_01_01 = -652316400; // utcMillis(1949, 5, 1, 1, 0, 0);
        long SECONDS_1948_10_01_00 = -670636800; // utcMillis(1948, 10, 1, 0, 0, 0);
        long SECONDS_1948_05_01_01 = -683852400; // utcMillis(1948, 5, 1, 1, 0, 0);
        long SECONDS_1947_11_01_00 = -699580800; // utcMillis(1947, 11, 1, 0, 0, 0);
        long SECONDS_1947_04_15_01 = -716857200; // utcMillis(1947, 4, 15, 1, 0, 0);
        long SECONDS_1946_10_01_00 = -733795200; // utcMillis(1946, 10, 1, 0, 0, 0);
        long SECONDS_1946_05_15_01 = -745801200; // utcMillis(1946, 5, 15, 1, 0, 0);
        long SECONDS_1945_09_02_00 = -767836800; // utcMillis(1945, 9, 2, 0, 0, 0);
        long SECONDS_1942_01_31_01 = -881017200; // utcMillis(1942, 1, 31, 1, 0, 0);
        long SECONDS_1941_11_02_00 = -888796800; // utcMillis(1941, 11, 2, 0, 0, 0);
        long SECONDS_1941_03_15_01 = -908838000; // utcMillis(1941, 3, 15, 1, 0, 0);
        long SECONDS_1940_10_13_00 = -922060800; // utcMillis(1940, 10, 13, 0, 0, 0);
        long SECONDS_1940_06_01_01 = -933634800L; //utcMillis(1940, 6, 1, 1, 0, 0);
        long SECONDS_1919_10_01_00 = -1585872000L; // utcMillis(1919, 10, 1, 0, 0, 0);
        long SECONDS_1919_04_13_01 = -1600642800L; // utcMillis(1919, 4, 13, 1, 0, 0);
        long SECONDS_1901_01_01_00 = -2177452800L; // utcMillis(1901, 1, 1, 0, 0, 0);

        final int OFFSET_0900_TOTAL_SECONDS = 32400;
        final int OFFSET_0800_TOTAL_SECONDS = 28800;
        final int OFFSET_0543_TOTAL_SECONDS = 29143;

        int zoneOffsetTotalSeconds;
        if (seconds >= SECONDS_1991_09_15_02) {
            zoneOffsetTotalSeconds = OFFSET_0800_TOTAL_SECONDS;
        } else if (seconds >= SECONDS_1991_04_14_03) {
            zoneOffsetTotalSeconds = OFFSET_0900_TOTAL_SECONDS;
        } else if (seconds >= SECONDS_1990_09_16_02) {
            zoneOffsetTotalSeconds = OFFSET_0800_TOTAL_SECONDS;
        } else if (seconds >= SECONDS_1990_04_15_03) {
            zoneOffsetTotalSeconds = OFFSET_0900_TOTAL_SECONDS;
        } else if (seconds >= SECONDS_1989_09_17_02) {
            zoneOffsetTotalSeconds = OFFSET_0800_TOTAL_SECONDS;
        } else if (seconds >= SECONDS_1989_04_16_03) {
            zoneOffsetTotalSeconds = OFFSET_0900_TOTAL_SECONDS;
        } else if (seconds >= SECONDS_1988_09_11_02) {
            zoneOffsetTotalSeconds = OFFSET_0800_TOTAL_SECONDS;
        } else if (seconds >= SECONDS_1988_04_17_03) {
            zoneOffsetTotalSeconds = OFFSET_0900_TOTAL_SECONDS;
        } else if (seconds >= SECONDS_1987_09_13_02) {
            zoneOffsetTotalSeconds = OFFSET_0800_TOTAL_SECONDS;
        } else if (seconds >= SECONDS_1987_04_12_03) {
            zoneOffsetTotalSeconds = OFFSET_0900_TOTAL_SECONDS;
        } else if (seconds >= SECONDS_1986_09_14_02) {
            zoneOffsetTotalSeconds = OFFSET_0800_TOTAL_SECONDS;
        } else if (seconds >= SECONDS_1986_05_04_03) {
            zoneOffsetTotalSeconds = OFFSET_0900_TOTAL_SECONDS;
        } else if (seconds >= SECONDS_1949_05_28_00) {
            zoneOffsetTotalSeconds = OFFSET_0800_TOTAL_SECONDS;
        } else if (seconds >= SECONDS_1949_05_01_01) {
            zoneOffsetTotalSeconds = OFFSET_0900_TOTAL_SECONDS;
        } else if (seconds >= SECONDS_1948_10_01_00) {
            zoneOffsetTotalSeconds = OFFSET_0800_TOTAL_SECONDS;
        } else if (seconds >= SECONDS_1948_05_01_01) {
            zoneOffsetTotalSeconds = OFFSET_0900_TOTAL_SECONDS;
        } else if (seconds >= SECONDS_1947_11_01_00) {
            zoneOffsetTotalSeconds = OFFSET_0800_TOTAL_SECONDS;
        } else if (seconds >= SECONDS_1947_04_15_01) {
            zoneOffsetTotalSeconds = OFFSET_0900_TOTAL_SECONDS;
        } else if (seconds >= SECONDS_1946_10_01_00) {
            zoneOffsetTotalSeconds = OFFSET_0800_TOTAL_SECONDS;
        } else if (seconds >= SECONDS_1946_05_15_01) {
            zoneOffsetTotalSeconds = OFFSET_0900_TOTAL_SECONDS;
        } else if (seconds >= SECONDS_1945_09_02_00) {
            zoneOffsetTotalSeconds = OFFSET_0800_TOTAL_SECONDS;
        } else if (seconds >= SECONDS_1942_01_31_01) {
            zoneOffsetTotalSeconds = OFFSET_0900_TOTAL_SECONDS;
        } else if (seconds >= SECONDS_1941_11_02_00) {
            zoneOffsetTotalSeconds = OFFSET_0800_TOTAL_SECONDS;
        } else if (seconds >= SECONDS_1941_03_15_01) {
            zoneOffsetTotalSeconds = OFFSET_0900_TOTAL_SECONDS;
        } else if (seconds >= SECONDS_1940_10_13_00) {
            zoneOffsetTotalSeconds = OFFSET_0800_TOTAL_SECONDS;
        } else if (seconds >= SECONDS_1940_06_01_01) {
            zoneOffsetTotalSeconds = OFFSET_0900_TOTAL_SECONDS;
        } else if (seconds >= SECONDS_1919_10_01_00) {
            zoneOffsetTotalSeconds = OFFSET_0800_TOTAL_SECONDS;
        } else if (seconds >= SECONDS_1919_04_13_01) {
            zoneOffsetTotalSeconds = OFFSET_0900_TOTAL_SECONDS;
        } else if (seconds >= SECONDS_1901_01_01_00) {
            zoneOffsetTotalSeconds = OFFSET_0800_TOTAL_SECONDS;
        } else {
            zoneOffsetTotalSeconds = OFFSET_0543_TOTAL_SECONDS;
        }

        return zoneOffsetTotalSeconds;
    }

    public enum DateTimeFormatPattern {
        DATE_FORMAT_10_DASH("yyyy-MM-dd", 10),
        DATE_FORMAT_10_SLASH("yyyy/MM/dd", 10),
        DATE_FORMAT_10_DOT("dd.MM.yyyy", 10),
        DATE_TIME_FORMAT_19_DASH("yyyy-MM-dd HH:mm:ss", 19),
        DATE_TIME_FORMAT_19_DASH_T("yyyy-MM-dd'T'HH:mm:ss", 19),
        DATE_TIME_FORMAT_19_SLASH("yyyy/MM/dd HH:mm:ss", 19),
        DATE_TIME_FORMAT_19_DOT("dd.MM.yyyy HH:mm:ss", 19);

        public final String pattern;
        public final int length;

        DateTimeFormatPattern(String pattern, int length) {
            this.pattern = pattern;
            this.length = length;
        }
    }

    public static boolean isLocalTime(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        char h0, h1, m0, m1, s0, s1;
        if (str.length() == 8 && str.charAt(2) == ':' && str.charAt(5) == ':') {
            h0 = str.charAt(0);
            h1 = str.charAt(1);
            m0 = str.charAt(3);
            m1 = str.charAt(4);
            s0 = str.charAt(6);
            s1 = str.charAt(7);
        } else {
            try {
                LocalTime.parse(str);
                return true;
            } catch (DateTimeParseException ignored) {
                return false;
            }
        }

        if (h0 >= '0' && h0 <= '2'
                && h1 >= '0' && h1 <= '9'
                && m0 >= '0' && m0 <= '6'
                && m1 >= '0' && m1 <= '9'
                && s0 >= '0' && s0 <= '6'
                && s1 >= '0' && s1 <= '9'
        ) {
            int hh = (h0 - '0') * 10 + (h1 - '0');
            if (hh > 24) {
                return false;
            }

            int mm = (m0 - '0') * 10 + (m1 - '0');
            if (mm > 60) {
                return false;
            }

            int ss = (s0 - '0') * 10 + (s1 - '0');
            return ss <= 61;
        }

        return false;
    }

    public static int readNanos(final char[] chars, final int len, final int offset) {
        switch (len) {
            case 1:
                return 100000000 * (chars[offset] - 48);
            case 2:
                return 100000000 * (chars[offset] - 48)
                        + 10000000 * (chars[offset + 1] - 48);
            case 3:
                return 100000000 * (chars[offset] - 48)
                        + 10000000 * (chars[offset + 1] - 48)
                        + 1000000 * (chars[offset + 2] - 48);
            case 4:
                return 100000000 * (chars[offset] - 48)
                        + 10000000 * (chars[offset + 1] - 48)
                        + 1000000 * (chars[offset + 2] - 48)
                        + 100000 * (chars[offset + 3] - 48);
            case 5:
                return 100000000 * (chars[offset] - 48)
                        + 10000000 * (chars[offset + 1] - 48)
                        + 1000000 * (chars[offset + 2] - 48)
                        + 100000 * (chars[offset + 3] - 48)
                        + 10000 * (chars[offset + 4] - 48);
            case 6:
                return 100000000 * (chars[offset] - 48)
                        + 10000000 * (chars[offset + 1] - 48)
                        + 1000000 * (chars[offset + 2] - 48)
                        + 100000 * (chars[offset + 3] - 48)
                        + 10000 * (chars[offset + 4] - 48)
                        + 1000 * (chars[offset + 5] - 48);
            case 7:
                return 100000000 * (chars[offset] - 48)
                        + 10000000 * (chars[offset + 1] - 48)
                        + 1000000 * (chars[offset + 2] - 48)
                        + 100000 * (chars[offset + 3] - 48)
                        + 10000 * (chars[offset + 4] - 48)
                        + 1000 * (chars[offset + 5] - 48)
                        + 100 * (chars[offset + 6] - 48);
            case 8:
                return 100000000 * (chars[offset] - 48)
                        + 10000000 * (chars[offset + 1] - 48)
                        + 1000000 * (chars[offset + 2] - 48)
                        + 100000 * (chars[offset + 3] - 48)
                        + 10000 * (chars[offset + 4] - 48)
                        + 1000 * (chars[offset + 5] - 48)
                        + 100 * (chars[offset + 6] - 48)
                        + 10 * (chars[offset + 7] - 48);
            default:
                return 100000000 * (chars[offset] - 48)
                        + 10000000 * (chars[offset + 1] - 48)
                        + 1000000 * (chars[offset + 2] - 48)
                        + 100000 * (chars[offset + 3] - 48)
                        + 10000 * (chars[offset + 4] - 48)
                        + 1000 * (chars[offset + 5] - 48)
                        + 100 * (chars[offset + 6] - 48)
                        + 10 * (chars[offset + 7] - 48)
                        + chars[offset + 8] - 48;
        }
    }

    public static int readNanos(final byte[] bytes, final int len, final int offset) {
        switch (len) {
            case 1:
                return 100000000 * (bytes[offset] - 48);
            case 2:
                return 100000000 * (bytes[offset] - 48) + 10000000 * (bytes[offset + 1] - 48);
            case 3:
                return 100000000 * (bytes[offset] - 48)
                        + 10000000 * (bytes[offset + 1] - 48)
                        + 1000000 * (bytes[offset + 2] - 48);
            case 4:
                return 100000000 * (bytes[offset] - 48)
                        + 10000000 * (bytes[offset + 1] - 48)
                        + 1000000 * (bytes[offset + 2] - 48)
                        + 100000 * (bytes[offset + 3] - 48);
            case 5:
                return 100000000 * (bytes[offset] - 48)
                        + 10000000 * (bytes[offset + 1] - 48)
                        + 1000000 * (bytes[offset + 2] - 48)
                        + 100000 * (bytes[offset + 3] - 48)
                        + 10000 * (bytes[offset + 4] - 48);
            case 6:
                return 100000000 * (bytes[offset] - 48)
                        + 10000000 * (bytes[offset + 1] - 48)
                        + 1000000 * (bytes[offset + 2] - 48)
                        + 100000 * (bytes[offset + 3] - 48)
                        + 10000 * (bytes[offset + 4] - 48)
                        + 1000 * (bytes[offset + 5] - 48);
            case 7:
                return 100000000 * (bytes[offset] - 48)
                        + 10000000 * (bytes[offset + 1] - 48)
                        + 1000000 * (bytes[offset + 2] - 48)
                        + 100000 * (bytes[offset + 3] - 48)
                        + 10000 * (bytes[offset + 4] - 48)
                        + 1000 * (bytes[offset + 5] - 48)
                        + 100 * (bytes[offset + 6] - 48);
            case 8:
                return 100000000 * (bytes[offset] - 48)
                        + 10000000 * (bytes[offset + 1] - 48)
                        + 1000000 * (bytes[offset + 2] - 48)
                        + 100000 * (bytes[offset + 3] - 48)
                        + 10000 * (bytes[offset + 4] - 48)
                        + 1000 * (bytes[offset + 5] - 48)
                        + 100 * (bytes[offset + 6] - 48)
                        + 10 * (bytes[offset + 7] - 48);
            default:
                return 100000000 * (bytes[offset] - 48)
                        + 10000000 * (bytes[offset + 1] - 48)
                        + 1000000 * (bytes[offset + 2] - 48)
                        + 100000 * (bytes[offset + 3] - 48)
                        + 10000 * (bytes[offset + 4] - 48)
                        + 1000 * (bytes[offset + 5] - 48)
                        + 100 * (bytes[offset + 6] - 48)
                        + 10 * (bytes[offset + 7] - 48)
                        + bytes[offset + 8] - 48;
        }
    }
	
	private static boolean isNumber(String str) {
        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            if (ch == '+' || ch == '-') {
                if (i != 0) {
                    return false;
                }
            } else if (ch < '0' || ch > '9') {
                return false;
            }
        }
        return true;
    }
	private static boolean isNum(String str) {
        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            if (ch < '0' || ch > '9') {
                return false;
            }
        }
        return true;
    }
}
