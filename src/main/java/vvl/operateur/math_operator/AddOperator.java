package vvl.operateur.math_operator;

import java.math.BigInteger;

import vvl.lisp.LispError;
import vvl.lisp.LispFactory;
import vvl.lisp.MyLispEvaluate;
import vvl.operateur.Operator;
import vvl.operateur.OperatorEnum;
import vvl.util.ConsList;
import vvl.util.ConsListFactory;
import vvl.util.Demo;

public class AddOperator  implements Operator {
   private Object result;
   private boolean isBigInt;
	@Override
	public Object apply(ConsList<Object> operandes) throws LispError {
	        isBigInt = true;
	        result = BigInteger.valueOf(0);
	        Demo<Object> list = (Demo<Object>)operandes;
	        while(list != ConsListFactory.nil()) {
	     
	            if(list.car().getClass() == Demo.class) {
	               
	             result = isDemo(list);
	                    
	            }
	            else if(list.car().getClass() == BigInteger.class) {
	                result = isBigInt(list.car());
	            }
	            else if(list.car().getClass() == Double.class) {
	                result = isDouble(list);
	                
	            }
	            else if(list.car().getClass() == String.class) {
	            	
	            	if(OperatorEnum.contains((String) list.car())) {
	            	
	            		throw new LispError("Not a number");
	            	}
	            	result = isBigInt( LispFactory.getMyVar().get(list.car()));
	            }
	          
	            list = (Demo<Object>) list.cdr();
	        }
	        return  result;
	}
	private Object addBigInt(BigInteger val,Object result) {
	    result = ((BigInteger) result).add(val);
	    return  result;
	}
	
	private Object addDouble(double val,Object result) {
	    result = (double)result + val;
	    return  result;
	}

	private Object isDemo(Demo<Object> list) throws LispError {
	    Object res =  new MyLispEvaluate(list).makeOperands((Demo<Object>) list.car());
        if(res.getClass() != BigInteger.class) {
            if(result.getClass() == BigInteger.class)
                result = ((BigInteger) result).doubleValue();
            result = addDouble((double) res,result);
            isBigInt = false;
        }
        else {
       
            if(result.getClass() == BigInteger.class)
                result = addBigInt((BigInteger)res,result);
            else {
                if(res.getClass() == BigInteger.class) {
                    res = ((BigInteger) res).doubleValue();
                }
              
                result = addDouble( (double) res,result);
            }
               
        }
        return result;
	}
	
	private Object isBigInt(Object list){
	    if(!isBigInt) {
            result = addDouble(((BigInteger) list).doubleValue(),result);
        }
        else {
            result  = addBigInt((BigInteger) list,result);
        }
	    return result;
	}
	
	private Object isDouble(Demo<Object> list) {
	    if(isBigInt) {
            result = ((BigInteger) result).doubleValue();
            isBigInt = !isBigInt;
        }
        result = addDouble(((double) list.car()),result);
        return result;
    }
}
