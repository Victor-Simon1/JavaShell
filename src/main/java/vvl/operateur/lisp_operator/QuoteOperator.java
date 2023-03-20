package vvl.operateur.lisp_operator;

import vvl.lisp.LispError;
import vvl.operateur.Operator;
import vvl.util.ConsList;
import vvl.util.ConsListFactory;
import vvl.util.Demo;

public class QuoteOperator implements Operator {

    @Override
    public Object apply(ConsList<Object> operandes) throws LispError {
        Demo<Object> lisp = (Demo<Object>)operandes;
        var cons = new StringBuilder();
        if(lisp.size() !=1)
            throw new LispError("Invalid number of operands");
        while(lisp != ConsListFactory.nil()) {
            cons.append(lisp.car());
            lisp = (Demo<Object>)lisp.cdr();
        }

        return cons;
    }

}
