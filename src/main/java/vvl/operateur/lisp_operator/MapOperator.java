package vvl.operateur.lisp_operator;

import vvl.lisp.LispError;
import vvl.lisp.LispFactory;
import vvl.lisp.MyLispEvaluate;
import vvl.lisp.MyParser;
import vvl.operateur.Operator;
import vvl.util.ConsList;
import vvl.util.ConsListFactory;
import vvl.util.Demo;

public class MapOperator implements Operator{

	@Override
	public Object apply(ConsList<Object> operandes) throws LispError {

		Demo<Object> lisp = (Demo<Object>)operandes;
		Demo<Object> newLisp = (Demo<Object>) ConsListFactory.nil();
		
		if(lisp.car().getClass() == Demo.class) {
			LispFactory.setTempName("_map_");
			new MyLispEvaluate(lisp).makeOperands((Demo<Object>) lisp.car());
			Object myVar = null;
			if(lisp.cdr().car().getClass() == String.class) {
				myVar = LispFactory.getMyVar().get(lisp.cdr().car());

			}
			if(myVar != null && myVar.getClass() == Demo.class) {
				Object adding = null;
				while(!((ConsList<Object>) myVar).isEmpty()) {
					adding = new MyLispEvaluate(lisp).makeOperands((Demo<Object>) new MyParser().parse("(_map_ " + ((Demo<Object>) myVar).car() +")"));
					newLisp = (Demo<Object>) newLisp.append(adding);
					myVar = ((ConsList<Object>) myVar).cdr();
				}
					
			}
			
		}
		return newLisp;
	}

}
