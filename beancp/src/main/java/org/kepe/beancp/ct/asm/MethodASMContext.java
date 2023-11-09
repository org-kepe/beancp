package org.kepe.beancp.ct.asm;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class MethodASMContext {
	private ClassVisitor classWriter;
	private MethodVisitor methodVisitor;
	private int line;
	private String classPath;
	private Map<String,Object> objs=new HashMap<>();
	
	public MethodASMContext(String classPath,ClassVisitor classWriter,MethodVisitor methodVisitor,int line) {
		this.classWriter=classWriter;
		this.methodVisitor=methodVisitor;
		this.line=line;
		this.classPath=classPath;
	}
	
	public <T> T getObj(String key) {
		return (T) objs.get(key);
	}
	public <T> void setObj(String key,T value) {
		objs.put(key, value);
	}
	public ClassVisitor getClassWriter() {
		return classWriter;
	}

	public MethodVisitor getMethodVisitor() {
		return methodVisitor;
	}
	public void setMethodVisitor(MethodVisitor methodVisitor) {
		this.methodVisitor=methodVisitor;
	}
	public int getLine() {
		return line;
	}
	public int getNextLine() {
		return ++this.line;
	}
	public int getNextLine(int add) {
		this.line+=add;
		return this.line;
	}

	public String getClassPath() {
		return classPath;
	}
	
}
