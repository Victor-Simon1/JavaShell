package vvl.operateur.math_operator;

import java.math.BigInteger;

import vvl.lisp.LispError;
import vvl.lisp.LispFactory;
import vvl.lisp.MyLispEvaluate;
import vvl.operateur.Operator;
import vvl.util.ConsList;
import vvl.util.ConsListFactory;
import vvl.util.Demo;

public class DivOperator  implements Operator {
    private boolean isBigInt;
    private Object result;
    public DivOperator() {

        result = BigInteger.valueOf(0);
        isBigInt = true;

    }
	@Override
	public Object apply(ConsList<Object> operandes) throws LispError {
      
        Demo<Object> list = (Demo<Object>)operandes;
        
        if(list.size() != 2)
        	throw new LispError("Invalid number of operands");
        result = list.car();
        	
        if(result.getClass() == Demo.class) {
        	result = new MyLispEvaluate(result).makeOperands((Demo<Object>) result);
        	isBigInt = (result.getClass() == BigInteger.class);
        }
            
        else if(result.getClass() == String.class) {
        	result = LispFactory.getMyVar().get(result);
        	isBigInt = (result.getClass() == BigInteger.class);
        }
        else if(result.getClass() == BigInteger.class) {
        	
        	isBigInt = (result.getClass() == BigInteger.class);
        }
        else {
        	isBigInt = false;
        }

        list = (Demo<Object>) list.cdr();
        while(list != ConsListFactory.nil()) {
       
            if(list.car().getClass() == Demo.class) {
                result = isDemo(list);
            }
            else if(list.car().getClass() == BigInteger.class) {
                result = isBigInt( list.car());   
            }
            else if(list.car().getClass() == String.class) {
                result = isBigInt(LispFactory.getMyVar().get(list.car()));   
              }
            else {
                result = isDouble(list);
            }
                
            list = (Demo<Object>) list.cdr();
        }

        return result;
	}
	
	private Object divDouble(double val,Object result)  throws LispError{
	    if(val == 0.0)
            throw new LispError("Division by zero");
	    result = (double)result / val;
	    return result;
	}
	
	private Object divBigInt(BigInteger val,Object result) throws LispError {
	    if(val.equals(BigInteger.valueOf(0)))
	        throw new LispError("Division by zero");
	    result = ((BigInteger) result).divide(val);
	    return result;
	}
	
	private Object isDemo(Demo<Object> list) throws LispError {
	    
	    Object res = new MyLispEvaluate(list).makeOperands((Demo<Object>) list.car());
        if(res.getClass() == BigInteger.class) {
            if(!isBigInt) {
                isBigInt = true;
                result =divDouble(((BigInteger) res).doubleValue(),result);
            }
            else {
                result = divBigInt((BigInteger)res,result);
            }
                
        }
        else {
            if(!isBigInt)
                result = divDouble((double) res,result);
            else {
                result = ((BigInteger) result).doubleValue();
                result = divDouble((double)res,result);
            }
        }
	    
	    return result;
	}
	

    private Object isBigInt(Object list) throws LispError {
        
        if(!isBigInt) {
            result = divDouble(((BigInteger) list).doubleValue(),result);
        }
        else {

            result  = divBigInt((BigInteger) list,result);
        }
        return result;
    }

    private Object isDouble(Demo<Object> list) throws LispError {

        if(isBigInt) {
            result = ((BigInteger) result).doubleValue();
            isBigInt = false;
            
        }
        result = divDouble(((double) list.car()),result);
        return result;
    }
}
