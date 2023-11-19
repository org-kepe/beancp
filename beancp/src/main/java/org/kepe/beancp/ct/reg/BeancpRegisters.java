package org.kepe.beancp.ct.reg;

public class BeancpRegisters {
	
	public static void registers() {
		BeancpDirectRegisters.registers();
		BeancpBaseRegisters.registers();
		BeancpBeanRegisters.registers();
		BeancpCollectionRegisters.registers();
	}
	
}
