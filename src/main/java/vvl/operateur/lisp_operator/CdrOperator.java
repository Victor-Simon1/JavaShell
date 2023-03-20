package vvl.operateur.lisp_operator;

import vvl.lisp.LispError;
import vvl.lisp.MyLispEvaluate;
import vvl.operateur.Operator;
import vvl.util.Cons;
import vvl.util.ConsList;
import vvl.util.ConsListFactory;
import vvl.util.Demo;

public class CdrOperator implements Operator{

    @Override
    public Object apply(ConsList<Object> operandes) throws LispError {
    	
    	
    	Demo<Object> lisp = (Demo<Object>)operandes;

    	if(lisp.size()!= 1)
    		throw new LispError("Invalid number of operands");
    	if(lisp.car().getClass() == Demo.class) {
    		if(((ConsList<Object>) lisp.car()).size() == 0) {
    			return ConsListFactory.nil();
    		}
    		Object r = new MyLispEvaluate(lisp).makeOperands((Demo<Object>) lisp.car());
    		 if(r.getClass() == Cons.class) {
          		return ((Cons) r).right();
          	}
    		if(r.getClass() != Demo.class)
    			throw new LispError("Not a Cons");
    		
    		return ((ConsList<Object>) r).cdr();
    			
    	}
    	
    	else {
    		throw new LispError("Not a Cons");
    	}

    }

}
