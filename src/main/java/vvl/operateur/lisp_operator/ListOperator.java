package vvl.operateur.lisp_operator;

import vvl.lisp.LispError;
import vvl.lisp.MyLispEvaluate;
import vvl.operateur.Operator;
import vvl.util.ConsList;
import vvl.util.ConsListFactory;
import vvl.util.Demo;

public class ListOperator implements Operator{

    @Override
    public Object apply(ConsList<Object> operandes) throws LispError {
    	ConsList<Object> consList = ConsListFactory.nil();
    	Demo<Object> lisp = ( Demo<Object>)operandes;
    	
    	while(!lisp.isEmpty()) {
    		if(lisp.car().getClass() == Demo.class)
    			consList = consList.append(new MyLispEvaluate(lisp.car()).makeOperands((Demo<Object>) lisp.car()));
    		else
    			consList = consList.append(lisp.car());
    		lisp = (Demo<Object>) lisp.cdr();
    	}
        return consList;
    }

}
