package vvl.operateur.math_operator;

import java.math.BigInteger;

import vvl.lisp.LispError;
import vvl.lisp.LispFactory;
import vvl.lisp.MyLispEvaluate;
import vvl.operateur.Operator;
import vvl.util.ConsList;
import vvl.util.ConsListFactory;
import vvl.util.Demo;

public class MultOperator  implements Operator {
    
    private Object result ;
    private boolean isBigInt;
	@Override
	public Object apply(ConsList<Object> operandes) throws LispError {
	    result = BigInteger.valueOf(1);
	    Demo<Object> list = (Demo<Object>)operandes;
	    isBigInt = true;
        while(list!= ConsListFactory.nil()) {
            if(list.car().getClass() == Demo.class) {
                result = isDemo( list);
                       
            }
            else if(list.car().getClass() == BigInteger.class ) {
                result = isBigInt( list);
            }
            else if(list.car().getClass() == String.class) {
            	result = multiplyBigInt((BigInteger) LispFactory.getMyVar().get(list.car()),result);
            }
            else {
                result = isDouble( list);
            }
            list = (Demo<Object>) list.cdr();
        }
        return result;
	}
	
	private Object multiplyBigInt(BigInteger val,Object result) {
	    result = ((BigInteger) result).multiply(val);
        return result;
	    
	}
	
	private Object multiplyDouble(double val,Object result) {
	    result = (double)result * val;
        return result;
	    
	}
	
	private Object isDemo(Demo<Object> list) throws LispError {
	    Object res = new MyLispEvaluate(list).makeOperands((Demo<Object>) list.car());
        if(res.getClass() == BigInteger.class)   {
            if(!isBigInt) {   
                result = multiplyDouble( ((BigInteger) res).doubleValue(),result);
            }
            else
                result = multiplyBigInt((BigInteger)res,result);
        }
        else {
            if(!isBigInt)
                result = multiplyDouble((double) res,result);
            else {
                isBigInt = false;
                result = ((BigInteger) result).doubleValue();
                result = multiplyDouble((double)res,result);
            } 
        }
        
        return result;
	}
	
	private Object isBigInt(Demo<Object> list) {
	    if(!isBigInt) {
            result = multiplyDouble(((BigInteger) list.car()).doubleValue(),result);
        }
        else
            result = multiplyBigInt((BigInteger) list.car(),result);
	    return result;
    }
	
	private Object isDouble(Demo<Object> list) {
	    if(isBigInt) {
            result = ((BigInteger) result).doubleValue();
            isBigInt = false;
        }
        result = multiplyDouble((double) list.car(),result);
        return result;
	}

}
