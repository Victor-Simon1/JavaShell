package vvl.operateur.math_operator;

import java.math.BigInteger;

import vvl.lisp.LispBoolean;
import vvl.lisp.LispError;
import vvl.lisp.LispFactory;
import vvl.lisp.MyLispEvaluate;
import vvl.operateur.Operator;
import vvl.util.ConsList;
import vvl.util.ConsListFactory;
import vvl.util.Demo;

public class InfStrictOperator implements Operator {

	@Override
	public Object apply(ConsList<Object> operandes) throws LispError {
		  Demo<Object> lisp = (Demo<Object>)operandes;
	        double list = initialize(lisp);
	        
	        lisp = (Demo<Object>) lisp.cdr();
	        while(lisp != ConsListFactory.nil()) {
	        	Object res = null;
	            if(lisp.car().getClass() == BigInteger.class) {
	                if(list >= ((BigInteger) lisp.car()).doubleValue()) {
	                    return LispBoolean.FALSE;
	                }
	            }
	            else if(lisp.car().getClass() == Demo.class) {
	           	 	 res = new MyLispEvaluate(lisp).makeOperands((Demo<Object>) lisp.car());
	           	 	res = bigIntToDouble(res);
	           	 	if(list >= ((double) res)) {
	                   return LispBoolean.FALSE;
	               }
	           }
	            else if(lisp.car().getClass() == String.class) {
	            	res = LispFactory.getMyVar().get(lisp.car());
	            	res = bigIntToDouble(res);
	            	if(list >= (double)res) {
	                    return LispBoolean.FALSE;
	                }
	            }
	            else if(list >= ((double) lisp.car())){
	               
	                    return LispBoolean.FALSE;
	                
	            }
	            
	            list = setList(lisp,res);
	            lisp = (Demo<Object>) lisp.cdr();
	        }
	            
	        return  LispBoolean.TRUE;
		}
	
	private double initialize(Demo<Object> lisp) throws LispError {
		double list ;
        if(lisp.size() == 0)
            throw new LispError("Invalid number of operands");
        if(lisp.car().getClass() == BigInteger.class)
            list =  ((BigInteger) lisp.car()).doubleValue();
        else if(lisp.car().getClass() == Demo.class) {
        	Object res= new MyLispEvaluate(lisp).makeOperands((Demo<Object>) lisp.car());
        	if(res.getClass() == BigInteger.class)
        		list = ((BigInteger) res).doubleValue();
        	else 
        		list = (double) res;
        }
        else if(lisp.car().getClass() == String.class) {
        	Object res = LispFactory.getMyVar().get(lisp.car());
        	if(res.getClass() == BigInteger.class)
        		list = ((BigInteger) res).doubleValue();
        	else
        		list = (double)res;
        }
        else
            list = (double) lisp.car();
        
        return list;
	}
    private double setList(Demo<Object> lisp,Object res) {
    	double list ;
    	if(lisp.car().getClass() == BigInteger.class) {
            list = ((BigInteger) lisp.car()).doubleValue();
        }
        else if(lisp.car().getClass() == Demo.class) {
        	list = (double) res;
        }
        else if (lisp.car().getClass() == String.class) {
        	 Object res2 = LispFactory.getMyVar().get(lisp.car());
        	 list =((BigInteger) res2).doubleValue();
        }
        else
            list = (double) lisp.car();
    	return list;
    }
	
    
    private double bigIntToDouble(Object res) {
    	if(res.getClass() == BigInteger.class)
    		res = ((BigInteger) res).doubleValue();
    	return (double) res;
    
    }
}
