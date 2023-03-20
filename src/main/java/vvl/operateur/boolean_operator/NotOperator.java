package vvl.operateur.boolean_operator;

import vvl.lisp.LispBoolean;
import vvl.lisp.LispError;
import vvl.lisp.MyLispEvaluate;
import vvl.operateur.Operator;
import vvl.util.ConsList;
import vvl.util.Demo;

public class NotOperator implements Operator {

	@Override
	public Object apply(ConsList<Object> operandes) throws LispError {
		  Demo<?> list = (Demo<?>)operandes;

	        if(list.size() != 1 )
	            throw new LispError("Invalid number of operands");
	        if(list.car().equals(LispBoolean.TRUE))
	            return LispBoolean.FALSE;
	        else if(list.car().equals(LispBoolean.FALSE))
	        	return LispBoolean.TRUE;
	        else if(list.car().getClass() == Demo.class) {
	        	Object res =  new MyLispEvaluate(list).makeOperands((Demo<Object>) list.car());
	        	if(res.getClass() == LispBoolean.class) {
	        		if(res.equals(LispBoolean.TRUE))
	        			return LispBoolean.FALSE;
	        		return LispBoolean.TRUE;
	        	}
	        	else{
	        	  	throw new LispError("Not a Boolean");
	        	}
	        }
	        else 
	        	throw new LispError("Not a Boolean");
	}

}
