package org.kepe.beancp.tool;

/**
 * Hello world!
 *
 */
public class BeancpStringTool
{
    public static boolean isEmpty(Object str){
        if(str==null||"".equals(str)){
            return true;
        }
        return false;
    }
}
