package vvl.operateur.boolean_operator;

import vvl.lisp.LispBoolean;
import vvl.lisp.LispError;
import vvl.lisp.MyLispEvaluate;
import vvl.operateur.Operator;
import vvl.util.ConsList;
import vvl.util.ConsListFactory;
import vvl.util.Demo;

public class AndOperator implements Operator {

	@Override
	public Object apply(ConsList<Object> operandes) throws LispError {
		Demo<?> list = (Demo<?>)operandes;
		LispBoolean fin = null;

        if(list.size() == 0)
            return LispBoolean.TRUE;
        while(list != ConsListFactory.nil()) {
            if(list.car().getClass() == LispBoolean.class) {
            	fin = isLispBoolean((LispBoolean) list.car());
          
            }
            else if(list.car().getClass() == Demo.class) {
            	
            	Object res = new MyLispEvaluate(list).makeOperands((Demo<Object>) list.car());
            	if(res.getClass() == LispBoolean.class) {
            		fin = isLispBoolean((LispBoolean) res);
            
            	}
            	else {
            		throw new LispError("Not a Boolean");
            	}
            }
            else
            	throw new LispError("Not a Boolean");
            if(fin == LispBoolean.FALSE)
            	return fin;
            list = (Demo<?>) list.cdr();
                
        }
        return LispBoolean.TRUE;
	}
	public LispBoolean isLispBoolean(LispBoolean l) {
		if(l != LispBoolean.TRUE)
			return LispBoolean.FALSE; 
		return null;
	}

}


	
