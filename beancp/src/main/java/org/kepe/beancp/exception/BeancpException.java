package org.kepe.beancp.exception;

public class BeancpException
        extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5438146538164658686L;
	
	private EType etype;
	public BeancpException(String message) {
        super(message);
    }
	public BeancpException(EType etype,String message) {
        super(message);
        this.etype=etype;
    }
	public BeancpException(EType etype,Throwable cause) {
        super(cause);
    }
    public BeancpException(Throwable cause) {
        super(cause);
    }
    public BeancpException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public EType getEType(){
    	return this.etype;
    }
    
    public static enum EType{
    	NORMAL,IGNORE
    }
}
