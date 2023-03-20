package vvl.operateur;

import vvl.lisp.LispError;
import vvl.util.ConsList;

public interface Operator {
	Object apply(ConsList<Object> operandes) throws LispError;
}
