package vvl.lisp;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import vvl.operateur.Operator;
import vvl.operateur.boolean_operator.AndOperator;
import vvl.operateur.boolean_operator.NotOperator;
import vvl.operateur.boolean_operator.OrOperator;
import vvl.operateur.lisp_operator.CarOperator;
import vvl.operateur.lisp_operator.CdrOperator;
import vvl.operateur.lisp_operator.ConsOperator;
import vvl.operateur.lisp_operator.DefineOperator;
import vvl.operateur.lisp_operator.IfOperator;
import vvl.operateur.lisp_operator.LambdaOperator;
import vvl.operateur.lisp_operator.ListOperator;
import vvl.operateur.lisp_operator.MapOperator;
import vvl.operateur.lisp_operator.QuoteOperator;
import vvl.operateur.lisp_operator.SetOperator;
import vvl.operateur.math_operator.AddOperator;
import vvl.operateur.math_operator.DivOperator;
import vvl.operateur.math_operator.EqualsOperator;
import vvl.operateur.math_operator.InfOperator;
import vvl.operateur.math_operator.InfStrictOperator;
import vvl.operateur.math_operator.MultOperator;
import vvl.operateur.math_operator.SousOperator;
import vvl.operateur.math_operator.SupOperator;
import vvl.operateur.math_operator.SupStrictOperator;
import vvl.operateur.not_an_operator.FunctionOperator;
import vvl.operateur.not_an_operator.NilNotOperator;
import vvl.util.ConsList;
import vvl.util.ConsListFactory;
import vvl.util.Demo;

public class MyLispEvaluate {
    
    
    private Object lisp;
    static Map<String,Operator> operateurs = new HashMap<>();
    public MyLispEvaluate(Object pLisp) {
        this.lisp = pLisp;
        
        operateurs.put("and",new AndOperator());
        operateurs.put("or",new OrOperator());
        operateurs.put("not",new NotOperator());
        operateurs.put("+",new AddOperator());
        operateurs.put("-",new SousOperator());
        operateurs.put("/",new DivOperator());
        operateurs.put("*",new MultOperator());
        operateurs.put("<",new InfStrictOperator());
        operateurs.put("<=",new InfOperator());
        operateurs.put(">",new SupStrictOperator());
        operateurs.put(">=",new SupOperator());
        operateurs.put("=",new EqualsOperator());
        operateurs.put("if",new IfOperator());
        operateurs.put("cdr",new CdrOperator());
        operateurs.put("car",new CarOperator());
        operateurs.put("cons",new ConsOperator());
        operateurs.put("list",new ListOperator());
        operateurs.put("quote",new QuoteOperator());
        operateurs.put("nil",new NilNotOperator());
        operateurs.put("define",new DefineOperator());
        operateurs.put("set!",new SetOperator());
        operateurs.put("lambda",new LambdaOperator());
        operateurs.put("map",new MapOperator());
    }
    
    /**
     * choose the operation to do
     * @param lisp
     * @return
     * @throws LispError
     */
    public  Object makeOperands(Demo<Object> lisp) throws LispError {
    	try {
    
    		return operateurs.get(lisp.car().toString()).apply(lisp.cdr());
    	}
    	catch(NullPointerException e) {
    		if(LispFactory.getMyFunc().containsKey(lisp.car())) {
    			return new FunctionOperator().apply(lisp);	
    		}
    		else
    			throw new LispError( lisp.car() + " is undefined");
    	}
    	catch(NoSuchElementException e1) {
    		
    		return ConsListFactory.nil();
    	}
    }
    
    
    public Object evalAlone() throws LispError{
    	if(this.lisp.getClass() == String.class) {
    		 if(LispFactory.getMyVar().containsKey(lisp)) {
     			return LispFactory.getMyVar().get(lisp);
     		}
    		 throw new LispError(lisp + " is undefined",new Throwable());
    	}
    	else
    		return this.lisp;
    	
    	
    }
    /**
     * eval lisp
     * @return
     * @throws LispError
     */
    public Object evalLisp() throws LispError {
    	if(lisp instanceof ConsList) {
    		 Demo<?> lisp2 = (Demo<?>)this.lisp;    
    	        if(lisp2.size() == 0)
    	        	return ConsListFactory.nil();
    	        else {
    	            	return makeOperands((Demo<Object>) lisp2);
    	        }   
    	}
    	else {
    		if(this.lisp.toString().equals("nil"))
    			return "()";
    		else {
    			return this.evalAlone();
    		}
    	}
    }
}
