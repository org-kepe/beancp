package org.kepe.beancp.ct.itf;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpConvertByObject {
	public Object convertByObject(BeancpContext context,Object fromObj,Object toObj);
}
