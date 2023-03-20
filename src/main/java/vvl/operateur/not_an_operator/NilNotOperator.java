package vvl.operateur.not_an_operator;

import vvl.lisp.LispError;
import vvl.operateur.Operator;
import vvl.util.ConsList;

public class NilNotOperator implements Operator {

	@Override
	public Object apply(ConsList<Object> operandes) throws LispError {
		throw new LispError("nil is not a valid operator");
	
	}

}
