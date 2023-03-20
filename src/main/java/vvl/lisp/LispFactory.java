package vvl.lisp;

import java.util.HashMap;
import java.util.Map;

import vvl.util.ConsList;

/**
 * Simple factory to access the interpreter implementation.
 * 
 * @author leberre
 *
 */
public class LispFactory {
	protected static Map<String,Object> myVar = new HashMap<>();
	protected static Map<String,ConsList<Object>> myFunc = new HashMap<>();
	private static String tempName = "";
    private LispFactory() {
        // do nothing
    }
	public static Map<String,Object> getMyVar() {
		return myVar;
	}
	public static Map<String,ConsList<Object>> getMyFunc() {
		return myFunc;
	}

	public static String getTempName() {
		return tempName;
	}

	public static void setTempName(String tempName) {
		LispFactory.tempName = tempName;
	}
	
    /**
     * Create a new instance of the interpreter.
     * 
     * @return a new lisp interpreter.
     */
    public static Lisp makeInterpreter() {
    	myVar.clear();
    	myFunc.clear();
    	return new LispImp();
    }
}
