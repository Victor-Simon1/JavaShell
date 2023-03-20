package vvl.operateur.lisp_operator;

import vvl.lisp.LispError;
import vvl.lisp.LispFactory;
import vvl.lisp.MyLispEvaluate;
import vvl.operateur.Operator;
import vvl.operateur.OperatorEnum;
import vvl.util.ConsList;
import vvl.util.Demo;

public class SetOperator implements Operator {

	@Override
	public Object apply(ConsList<Object> operandes) throws LispError {
		Demo<Object> lisp = (Demo<Object>)operandes;
		if(LispFactory.getMyVar().containsKey(lisp.car())) {
			
			if(lisp.cdr().car().getClass() == Demo.class) {
				Object res = new MyLispEvaluate(lisp).makeOperands((Demo<Object>) lisp.cdr().car());
				LispFactory.getMyVar().replace((String) lisp.car(), res);
				return res;
			}
		
			else
				LispFactory.getMyVar().replace((String) lisp.car(), lisp.cdr().car());
		}
		else {
			
			if(lisp.car().getClass() != String.class || OperatorEnum.contains((String) lisp.car()))
				throw new LispError(lisp.car() + " is not a valid identifier");
			throw new LispError(lisp.car() + " is undefined");
		}
		return lisp.cdr().car();
	}

}
