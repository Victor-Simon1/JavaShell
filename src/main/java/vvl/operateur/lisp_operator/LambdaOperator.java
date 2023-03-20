package vvl.operateur.lisp_operator;

import vvl.lisp.LispError;
import vvl.lisp.LispFactory;
import vvl.operateur.Operator;
import vvl.util.ConsList;

public class LambdaOperator implements Operator {

	@Override
	public Object apply(ConsList<Object> operandes) throws LispError {
		LispFactory.getMyFunc().put(LispFactory.getTempName(), operandes);
		return "lambda "+ operandes.car() + " " + operandes.cdr().car();
	}

}
