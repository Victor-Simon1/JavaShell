package vvl.lisp;

public class LispImp implements Lisp{
    
    @Override
    public Object parse(String expr) throws LispError {
    	return new MyParser().parse(expr);
    	
    }
 
	
	@Override
	public Object evaluate(Object ex) throws LispError {
	    
	    
	    return new MyLispEvaluate(ex).evalLisp();
	}
	

		
}
