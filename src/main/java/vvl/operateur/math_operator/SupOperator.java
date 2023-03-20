package vvl.operateur.math_operator;

import java.math.BigInteger;

import vvl.lisp.LispBoolean;
import vvl.lisp.LispError;
import vvl.lisp.MyLispEvaluate;
import vvl.operateur.Operator;
import vvl.util.ConsList;
import vvl.util.ConsListFactory;
import vvl.util.Demo;

public class SupOperator implements Operator{

	@Override
	public Object apply(ConsList<Object> operandes) throws LispError {
	
	        Demo<Object> lisp = (Demo<Object>)operandes;
	        double list = initialize(lisp,null,true);
	        
	        lisp = (Demo<Object>) lisp.cdr();
	        while(lisp != ConsListFactory.nil()) {
	        	Object res = null;
	            if(lisp.car().getClass() == BigInteger.class) {
	                if(list < ((BigInteger) lisp.car()).doubleValue()) {
	                    return LispBoolean.FALSE;
	                }
	            }
	            else if(lisp.car().getClass() == Demo.class) {
	            	res = new MyLispEvaluate(lisp).makeOperands((Demo<Object>) lisp.car());
	            	if(res.getClass() == BigInteger.class)
	            		res = ((BigInteger) res).doubleValue();
	            	if(list < ((double)res)) {
	                    return LispBoolean.FALSE;
	                }
	            }

	            else if(list < ((double) lisp.car())) {
	              
	                    return LispBoolean.FALSE;
	                
	            }
	            list = initialize(lisp,res,false);
	            lisp = (Demo<Object>) lisp.cdr();
	        }
	            
	        return  LispBoolean.TRUE;
	    }

	private double initialize(Demo<Object> lisp,Object result, boolean init) throws LispError {
		double list;
		if(lisp.size() == 0)
	           throw new LispError("Invalid number of operands");
		if(lisp.car().getClass() == BigInteger.class)
			list =  ((BigInteger) lisp.car()).doubleValue();
		else if(lisp.car().getClass() == Demo.class) {
			if(init) {
				Object res= new MyLispEvaluate(lisp).makeOperands((Demo<Object>) lisp.car());
				if(res.getClass() == BigInteger.class)
					list = ((BigInteger) res).doubleValue();
				else 
					list = (double) res;
			}
			else {
				list = (double) result;
			}
		}
		else
			list = (double) lisp.car();
	    return list;
	}
	

}
