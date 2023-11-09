package org.kepe.beancp.info;

import java.util.ArrayList;
import java.util.List;

import org.kepe.beancp.info.BeancpGetInfo;
import org.kepe.beancp.info.BeancpInfo;
import org.kepe.beancp.info.BeancpName;
import org.kepe.beancp.info.BeancpSetInfo;

/**
 * Hello world!
 *
 */
public class BeancpFieldInfo
{
    private BeancpInfo parentInfo;
    private BeancpName name;
    private List<BeancpGetInfo> getterList=new ArrayList<>();
    

	private List<BeancpSetInfo> setterList=new ArrayList<>();
    
    public BeancpFieldInfo(String name,BeancpInfo parentInfo) {
    	this.name=BeancpName.of(name);
    	this.parentInfo=parentInfo;
    }
    public List<BeancpGetInfo> getGetterList() {
		return getterList;
	}
    public List<BeancpSetInfo> getSetterList() {
		return setterList;
	}
    public void addGetInfo(BeancpGetInfo getInfo) {
    	getterList.add(getInfo);
    }
    
    public void addSetInfo(BeancpSetInfo setInfo) {
    	setterList.add(setInfo);
    }
    
    public void addFirstGetInfo(BeancpGetInfo getInfo) {
    	getterList.add(0,getInfo);
    }
    
    public void addFirstSetInfo(BeancpSetInfo setInfo) {
    	setterList.add(0,setInfo);
    }
	public BeancpName getName() {
		return name;
	}
}
