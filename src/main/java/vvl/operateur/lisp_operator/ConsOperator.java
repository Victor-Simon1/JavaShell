package vvl.operateur.lisp_operator;

import vvl.lisp.LispError;
import vvl.lisp.LispFactory;
import vvl.lisp.MyLispEvaluate;
import vvl.operateur.Operator;
import vvl.util.Cons;
import vvl.util.ConsList;
import vvl.util.ConsListFactory;
import vvl.util.Demo;

public class ConsOperator implements Operator {

    @Override
    public Object apply(ConsList<Object> operandes) throws LispError {
        Demo<Object> lisp = (Demo<Object>)operandes;
        Cons<Object,Object> cons ;
        Demo<Object> consList = (Demo<Object>) ConsListFactory.nil();
        Object result = null;
        if(lisp.size() != 2) {
            throw new LispError("Invalid number of operands");
        }

        cons = makeCar(lisp);
        result = cons.left();
        
        if(lisp.cdr().car().equals("nil")) {
        	if(lisp.car().equals("nil"))
        		consList = (Demo<Object>) consList.append(ConsListFactory.nil());
        	else 
      	  		consList = (Demo<Object>) consList.append(result);
      	  	
      	  	return consList;
        }
        else if(lisp.cdr().car().getClass() == String.class) {
        	cons = new Cons<>(isString(lisp.cdr().car()),null);
        	return cons;
        }
        else if(lisp.cdr().car().getClass() == Demo.class) {
        	return cdrIsADemo(lisp,cons,result);
        }
    
        else {
        	cons.setRight(lisp.cdr().car());
        	return cons;
        } 
    }
    
    private Cons<Object,Object>  makeCar(Demo<Object> lisp) throws LispError {
    	Cons<Object,Object> cons;
    	  if(lisp.car().getClass() == Demo.class) {
          	Object res = new MyLispEvaluate(lisp.car()).makeOperands((Demo<Object>) lisp.car()); 	
          	cons = new Cons<>(res,null);
          	
          }
          else if(lisp.car().getClass() == String.class) {
          	cons = new Cons<>(isString(lisp.car()),null);
          }
          else {
          	cons = new Cons<>(lisp.car(),null);
          } 
    	  return cons;
    }
    private Object cdrIsADemo(Demo<Object> lisp,Cons<Object,Object> c,Object result) throws LispError{
    	Demo<Object> consList =(Demo<Object>) ConsListFactory.nil();
    	Cons <Object,Object> cons = c;
    	if(((ConsList<Object>) lisp.cdr().car()).size() == 0) {

    		consList = (Demo<Object>) consList.append(lisp.car());
    		return consList;
    	}
    	Object res = new MyLispEvaluate(lisp.car()).makeOperands((Demo<Object>) lisp.cdr().car());
        if(res.getClass() == Demo.class) {
        	consList = (Demo<Object>) consList.append(result);
        	while(!((ConsList<Object>) res).isEmpty()) {
        		   if(((ConsList<Object>) res).car().getClass() == String.class) {
        			   consList = (Demo<Object>) consList.append(isString(((Demo<Object>) res).car()));
        	        }
        		   else {
        			   consList = (Demo<Object>) consList.append(((Demo<Object>) res).car());
        		   }
        			  
        		res = ((ConsList<Object>) res).cdr();
        	}
        	
        	return consList;
        }
        else if(res.getClass() == String.class) {
        	cons = new Cons<>(isString(res),null);
        	return cons;
        }
    	cons.setRight(res);
    	return cons;
    }
    private Object isString(Object o) {
    	Object ret ;
    	if(o.equals("nil")) {
    		ret = ConsListFactory.nil();
    	}
    	else if( LispFactory.getMyVar().get(o) != null ){
    		ret = LispFactory.getMyVar().get(o);
    	}
    	else {
    		ret = o;
    	}
    	return ret;
    }
}
