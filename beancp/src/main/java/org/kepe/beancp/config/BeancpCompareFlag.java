package org.kepe.beancp.config;

import java.lang.reflect.Type;

import org.kepe.beancp.info.BeancpInfo;

/**
 * Hello world!
 *
 */
public enum BeancpCompareFlag
{
	LESS(-1),GREATER(1),EQUALS(0),UNKOWN(-2);
	public final int flag;
	BeancpCompareFlag(int i) {
		this.flag=i;
	}
	public boolean isLessOrEquals() {
		return flag==-1||flag==0;
	}
	public boolean isGreaterOrEquals() {
		return flag==1||flag==0;
	}
	public static BeancpCompareFlag of(int compareInt) {
		if(compareInt>0) {
			return BeancpCompareFlag.GREATER;
		}else if(compareInt==0) {
			return BeancpCompareFlag.EQUALS;
		}else {
			return BeancpCompareFlag.LESS;
		}
	}
	
}
