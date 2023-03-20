package vvl.operateur.lisp_operator;

import vvl.lisp.LispBoolean;
import vvl.lisp.LispError;
import vvl.lisp.LispFactory;
import vvl.lisp.MyLispEvaluate;
import vvl.operateur.Operator;
import vvl.util.ConsList;
import vvl.util.Demo;

public class IfOperator implements Operator{

    @Override
    public Object apply(ConsList<Object> operandes) throws LispError {
        Demo<Object> lisp = (Demo<Object>)operandes;
        if(lisp.size() != 3)
            throw new LispError("Invalid number of operands");
        if(lisp.car().getClass() == LispBoolean.class) {
        	return isLispBoolean(lisp);
        }
        else {
        	return isNotLispBoolean(lisp);
           
        }
       
    }
    
    private Object isLispBoolean(Demo<Object> lisp) {
    	 if(lisp.car().equals(LispBoolean.TRUE))
             return lisp.cdr().car();
         else
             return lisp.cdr().cdr().car();
    }
    
    private Object isNotLispBoolean(Demo<Object> lisp) throws LispError {
    	 Object result = new MyLispEvaluate(lisp.car()).makeOperands((Demo<Object>) lisp.car());
         
         if(result.equals(LispBoolean.TRUE)) {
         	if(lisp.cdr().car().getClass() == Demo.class)
         		return new MyLispEvaluate(lisp).makeOperands((Demo<Object>) lisp.cdr().car());
         	else if(lisp.cdr().car().getClass() == String.class)
         		return LispFactory.getMyVar().get(lisp.cdr().car());
         	return lisp.cdr().car();
         }
             
         else if(result.equals(LispBoolean.FALSE)) {
         	if (lisp.cdr().cdr().car().getClass() == Demo.class)
         		return new MyLispEvaluate(lisp).makeOperands((Demo<Object>)  lisp.cdr().cdr().car());
         	else if(lisp.cdr().cdr().car().getClass() == String.class)
         		return LispFactory.getMyVar().get(lisp.cdr().cdr().car());
             return lisp.cdr().cdr().car();
         }

         else
         	throw new LispError("Not a Boolean");
   }
    

}
