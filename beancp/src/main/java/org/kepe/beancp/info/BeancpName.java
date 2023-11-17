package org.kepe.beancp.info;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BeancpName {
	public String name;
	public NameType type=NameType.UNKNOW;
	public Set<BeancpName> friends;
	private final static Map<String,BeancpName> C_MAP=new ConcurrentHashMap<>();
	
	
	public static BeancpName of(String name) {
		return C_MAP.computeIfAbsent(name, key->{
			Set<BeancpName> friends=new HashSet<>();
			BeancpName bname=new BeancpName(name);
			{
				String name1=name.toUpperCase();
				bname.computeType(name1, NameType.UPPERCASE);
				friends.add(new BeancpName(name1,NameType.UPPERCASE));
			}
			{
				String name1=name.toLowerCase();
				bname.computeType(name1, NameType.LOWERCASE);
				friends.add(new BeancpName(name1,NameType.LOWERCASE));
			}
			{
				String[] arr=name.split("_");
				StringBuffer sb=new StringBuffer();
				for(int i=0;i<arr.length;i++) {
					String str=arr[i];
					if(i==0) {
						sb.append(str.toLowerCase());
					}else {
						sb.append(str.substring(0, 1).toUpperCase()).append(str.substring(1));
					}
				}
				String name1=sb.toString();
				bname.computeType(name1, NameType.HUMP);
				friends.add(new BeancpName(name1,NameType.HUMP));
			}
			{
				String name1;
				String name2;
				if(name.contains("_")){
					name1=name.toLowerCase();
					name2=name1;
				}else {
					StringBuffer sb=new StringBuffer();
					StringBuffer sb2=new StringBuffer();
					char[] arr=name.toCharArray();
					boolean isNumFirst=false;
					for(int i=0;i<arr.length;i++) {
						char c=arr[i];
						if(i==0) {
							sb.append(Character.toLowerCase(c));
						}else if(c>='A'&&c<='Z') {
							sb.append("_").append(Character.toLowerCase(c));
						}else {
							sb.append(c);
						}
						if(c>='0'&&c<='9'&&!isNumFirst) {
							isNumFirst=true;
						}else {
							isNumFirst=false;
						}
						if(i==0) {
							sb2.append(Character.toLowerCase(c));
						}else if((c>='A'&&c<='Z')||isNumFirst) {
							sb2.append("_").append(Character.toLowerCase(c));
						}else {
							sb2.append(c);
						}
					}
					name1=sb.toString();
					name2=sb2.toString();
				}
				bname.computeType(name1, NameType.UNDERLINE);
				friends.add(new BeancpName(name1,NameType.UNDERLINE));
				bname.computeType(name1.toUpperCase(), NameType.UPPERUNDERLINE);
				friends.add(new BeancpName(name1.toUpperCase(),NameType.UPPERUNDERLINE));
				if(!name1.equals(name2)) {
					bname.computeType(name2, NameType.UNDERLINE);
					friends.add(new BeancpName(name2,NameType.UNDERLINE));
					bname.computeType(name2.toUpperCase(), NameType.UPPERUNDERLINE);
					friends.add(new BeancpName(name2.toUpperCase(),NameType.UPPERUNDERLINE));
				}
			}
			bname.friends=friends;
			for(BeancpName bn:friends) {
				bn.friends=friends;
			}
			return bname;
		});
	}
	private BeancpName(String name) {
		this.name=name;
	}
	private BeancpName(String name,NameType type) {
		this.name=name;
		this.type=type;
	}
	
	private void computeType(String name,NameType type) {
		if(this.name.equals(name)) {
			this.type=type;
		}
	}
	public static enum NameType{
		UNKNOW,UPPERCASE,LOWERCASE,HUMP,UNDERLINE,UPPERUNDERLINE
	}
}
