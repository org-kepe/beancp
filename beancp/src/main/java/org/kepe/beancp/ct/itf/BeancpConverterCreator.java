package org.kepe.beancp.ct.itf;

import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.info.BeancpInfo;

public interface BeancpConverterCreator {
	BeancpConverter create(BeancpFeature feature, BeancpInfo fromInfo,BeancpInfo toInfo) ;
}
