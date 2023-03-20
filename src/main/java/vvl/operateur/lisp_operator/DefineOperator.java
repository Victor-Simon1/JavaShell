package vvl.operateur.lisp_operator;

import vvl.lisp.LispError;
import vvl.lisp.LispFactory;
import vvl.lisp.MyLispEvaluate;
import vvl.operateur.Operator;
import vvl.operateur.OperatorEnum;
import vvl.util.ConsList;
import vvl.util.Demo;

public class DefineOperator implements Operator {
	private static final String ISNOT = " is not a valid identifier";
	@Override
	public Object apply(ConsList<Object> operandes) throws LispError {
		Demo<Object> lisp = (Demo<Object>)operandes;
		Object res= null;
		if(lisp.size() != 2) {
			throw new LispError("Invalid number of operands");
		}
		else {	
			
			if(lisp.car().getClass() == String.class) {
				res = isString(lisp);
			}
			
			else if(lisp.car().getClass() != String.class) {
			
				throw new LispError(lisp.car() + ISNOT);
			}
		}
		LispFactory.getMyVar().put((String) lisp.car(), res);

		return res;
	}
	
	private Object isString(Demo<Object> lisp) throws LispError {
		Object res = null;
		if(OperatorEnum.contains((String) lisp.car()))
			throw new LispError(lisp.car() + ISNOT);
		else if(LispFactory.getMyVar().containsKey(lisp.car())){

			throw new LispError(lisp.car() + ISNOT);
		}
		else {
			if(lisp.cdr().car().getClass() == Demo.class) {
				LispFactory.setTempName(lisp.car().toString());
				res = new MyLispEvaluate(lisp.cdr().car()).makeOperands((Demo<Object>) lisp.cdr().car());
				
			}
			else
				res = lisp.cdr().car();

		}
		return  res;
	}

}

