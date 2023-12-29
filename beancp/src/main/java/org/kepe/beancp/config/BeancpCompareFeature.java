package org.kepe.beancp.config;

import java.lang.reflect.Type;

import org.kepe.beancp.info.BeancpInfo;

/**
 * Hello world!
 *
 */
public enum BeancpCompareFeature	
{
	LESS(-1),GREATER(1),EQUALS(0),UNKOWN(-2);
	public final int num;
	BeancpCompareFeature(int i) {
		this.num=i;
	}
	
}
