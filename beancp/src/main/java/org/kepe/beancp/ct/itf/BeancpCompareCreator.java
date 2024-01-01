package org.kepe.beancp.ct.itf;

import org.kepe.beancp.config.BeancpCompare;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.info.BeancpInfo;

public interface BeancpCompareCreator {
	BeancpCompare create(BeancpInfo fromInfo,BeancpInfo toInfo) ;
}
