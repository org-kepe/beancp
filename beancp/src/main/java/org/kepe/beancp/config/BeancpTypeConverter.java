package org.kepe.beancp.config;

import org.kepe.beancp.ct.invocation.BeancpInvocationOO;
import org.kepe.beancp.ct.itf.BeancpCustomConverter;

/**
 * Hello world!
 *
 */
public interface BeancpTypeConverter<T,R> extends BeancpCustomConverter<T, R>
{
    R convert(BeancpInvocationOO<T, R> invocation, BeancpContext context,T fromObj,R toObj);

    
}
