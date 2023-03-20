package vvl.operateur.not_an_operator;

import vvl.lisp.LispError;
import vvl.lisp.LispFactory;
import vvl.lisp.MyLispEvaluate;
import vvl.operateur.Operator;
import vvl.util.ConsList;
import vvl.util.ConsListFactory;
import vvl.util.Demo;

public class FunctionOperator implements Operator{

	@Override
	public Object apply(ConsList<Object> operandes) throws LispError {
		Demo<Object> lisp = (Demo<Object>)operandes;
		Demo<Object> function =  (Demo<Object>) LispFactory.getMyFunc().get(lisp.car());
		Demo<Object> copyFunction = (Demo<Object>) ConsListFactory.nil();
		var param = new String[10];
		var paramValue = new Object[10];
		Demo<Object> getParam = (Demo<Object>) function.car();
		var cpt = 0;

		while(! getParam.isEmpty()) {
			param[cpt] = (String) getParam.car();
			cpt++;
			getParam = (Demo<Object>) getParam.cdr();
		}
		cpt = 0;
		lisp = (Demo<Object>) lisp.cdr();
		while(!lisp.isEmpty()) {

			paramValue[cpt] =  lisp.car();
			cpt++;
			lisp = (Demo<Object>) lisp.cdr();
		}

		return arguIsDemo(function,copyFunction,param,paramValue);
	
	}
	
	private Object arguIsDemo(Demo<Object> function,Demo<Object> copyFunction,String [] param, Object [] paramValue) throws LispError {
		if(function.cdr().car().getClass() == Demo.class) {
			function = (Demo<Object>) function.cdr().car();
		
			var notFound = true;
			while(!function.isEmpty()) {
		
				for(var i = 0;i< param.length;i++) {
					if(function.car().equals(param[i])) {
    					copyFunction = (Demo<Object>) copyFunction.append(paramValue[i]);
    					notFound = false;
    				}
    			
				}
				if(notFound)
					copyFunction = (Demo<Object>) copyFunction.append(function.car());
				notFound = true;
				function = (Demo<Object>) function.cdr();
			}

			return new MyLispEvaluate(function).makeOperands(copyFunction);
		}
		else
			return function.cdr().car();
	}

}
