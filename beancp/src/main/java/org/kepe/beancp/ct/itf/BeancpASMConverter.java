package org.kepe.beancp.ct.itf;

import java.lang.reflect.Type;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.asm.MethodASMContext;

import org.kepe.beancp.ct.itf.BeancpConverter;

/**
 * Hello world!
 *
 */
public interface BeancpASMConverter extends BeancpConverter
{
	void createField(MethodASMContext asmContext,BeancpFeature flag,Type fromType,Class fromClass,Type toType,Class toClass);
    void init(MethodASMContext asmContext,BeancpFeature flag,Type fromType,Class fromClass,Type toType,Class toClass);

    void convert2New(MethodASMContext asmContext,BeancpFeature flag,Type fromType,Class fromClass,Type toType,Class toClass);
    
    void convert2Object(MethodASMContext asmContext,BeancpFeature flag,Type fromType,Class fromClass,Type toType,Class toClass);
}
