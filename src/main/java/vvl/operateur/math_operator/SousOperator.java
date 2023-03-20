package vvl.operateur.math_operator;

import java.math.BigInteger;

import vvl.lisp.LispError;
import vvl.lisp.LispFactory;
import vvl.lisp.MyLispEvaluate;
import vvl.operateur.Operator;
import vvl.util.ConsList;
import vvl.util.Demo;

public class SousOperator  implements Operator{

    private Object result ;
    private boolean isBigInt;
	@Override
	public Object apply(ConsList<Object> operandes) throws LispError {
	         result = BigInteger.valueOf(0);
	        Demo<Object> list = (Demo<Object>)operandes;
	         isBigInt = true;
	        if(list.size() == 0)
	        	throw new LispError("Invalid number of operands");
	        if(list.size() == 1) {
	            if(list.car().getClass() == BigInteger.class)
	                return ((BigInteger) list.car()).multiply(BigInteger.valueOf(-1));
	            else if(list.car().getClass() ==  Demo.class) {
	            	Object res = new MyLispEvaluate(list).makeOperands((Demo<Object>) list.car());
	            	if(res.getClass() == BigInteger.class)
	            		return ((BigInteger)res).multiply(BigInteger.valueOf(-1));
	            	return (double) res * -1.0;
	            }
	            return  (double)list.car() * -1.0;
	        }
	        if(list.size() > 2) {
	            throw new LispError("Invalid number of operands");
	        }
	        else {
	           result =  makeFirst(list);
	           result = makeSecond( list);
	        }
	        return result;
	}
	private Object sousBigInt(BigInteger val,Object result) {
        result = ((BigInteger) result).subtract(val);
        return result;
        
    }
    
    private Object sousDouble(double val,Object result) {
        result = (double)result - val;
        return result;
        
    }
    
    private Object makeFirst(Demo<Object> list) throws LispError {
        result = list.car();
        if(result.getClass() == Demo.class) {
             Object res =  new MyLispEvaluate(list).makeOperands((Demo<Object>) list.car());
             if(res.getClass() != BigInteger.class) {
                 result = res;
                 isBigInt = false;
             }
             else {
                 result = res;
             } 
         }
        else if(list.car().getClass() == String.class) {
        	result = LispFactory.getMyVar().get(list.car());
        }
        else if(list.car().getClass() != BigInteger.class) {
            isBigInt = !isBigInt;
        }
        return result;
    }
    
    private Object makeSecond(Demo<Object> list) throws LispError {
        if(list.cdr().car().getClass() == Demo.class) {
             secondIsDemo(list);
         }
        else if(list.cdr().car().getClass() == BigInteger.class) {
            if(isBigInt)
                result = sousBigInt((BigInteger) list.cdr().car(),result);
            else {
                result = sousDouble(((BigInteger) list.cdr().car()).doubleValue(),result);
            }
               
        }
        else if(list.cdr().car().getClass() == String.class) {
        	result = sousBigInt((BigInteger) LispFactory.getMyVar().get(list.cdr().car()),result);
        }
        else {
            if(isBigInt)
                result = ((BigInteger) result).doubleValue();
            result = sousDouble( (double) list.cdr().car(),result);
        }
        return result;
    }
    private void secondIsDemo(Demo<Object> list) throws LispError {
    	Object res =  new MyLispEvaluate(list).makeOperands((Demo<Object>) list.cdr().car());
        if(res.getClass() != BigInteger.class) {
            if(result.getClass() == BigInteger.class)
                result = ((BigInteger) result).doubleValue();
            result = sousDouble((double) res,result);
            isBigInt = false;
        }
        else {
       
            if(result.getClass() == BigInteger.class)
                result = sousBigInt((BigInteger)new MyLispEvaluate(list).makeOperands((Demo<Object>) list.cdr().car()),result);
            else {
                if(res.getClass() == BigInteger.class) {
                    res = ((BigInteger) res).doubleValue();
                }
                result = sousDouble( (double) res,result);
            }
               
        }
    }
}
