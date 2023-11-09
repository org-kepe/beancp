package org.kepe.beancp.tool;

/**
 * Hello world!
 *
 */
public class StringTool
{
    public static boolean isEmpty(Object str){
        if(str==null||"".equals(str)){
            return true;
        }
        return false;
    }
}
