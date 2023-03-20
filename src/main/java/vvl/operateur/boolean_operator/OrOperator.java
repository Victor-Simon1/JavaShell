package vvl.operateur.boolean_operator;

import vvl.lisp.LispBoolean;
import vvl.lisp.LispError;
import vvl.lisp.MyLispEvaluate;
import vvl.operateur.Operator;
import vvl.util.ConsList;
import vvl.util.ConsListFactory;
import vvl.util.Demo;

public class OrOperator implements Operator {
	private static final  String ERROR = "Not a Boolean";
	@Override
	public Object apply(ConsList<Object> operandes) throws LispError {
		  Demo<Object> list = (Demo<Object>)operandes;
	       var isTrue = false;
	        while(list != ConsListFactory.nil()) {
	
	        	if(list.car().equals(LispBoolean.TRUE))
	        		isTrue = true;
	        	else if(list.car().getClass() != LispBoolean.class) {
	        		if(list.car().getClass() == Demo.class) {
	        			isTrue = notABoolean(list.car(),isTrue);
	        		}
	        		else if(!isTrue)
	        			throw new LispError(ERROR);
	        	}
	        		
	        	list = (Demo<Object>)list.cdr();
	        	
	        }
	        if(isTrue)
	        	return LispBoolean.TRUE;
	        
	        return LispBoolean.FALSE;
	}

	public boolean notABoolean(Object car,boolean isTrue) throws LispError {
		Object res = new MyLispEvaluate(car).makeOperands((Demo<Object>) car);
		if(res.getClass() == Demo.class){
				throw new LispError(ERROR);
		}
		else if (res.getClass() == LispBoolean.class && res.equals(LispBoolean.TRUE)) {
			isTrue = true;
		}
		else if(res.getClass() != LispBoolean.class && !isTrue) {
		
				throw new LispError(ERROR);
		}
		return isTrue;
	}
}
