package vvl.operateur;

public enum OperatorEnum {
	
	ADD("+"),SOUS("-"),MULT("*"),DIV("/"),AND("and"),NOT("not"),OR("or"),CAR("car"),
	CDR("cdr"),CONS("cons"),DEFINE("define"),IF("if"),LIST("list"),QUOTE("quote"),LAMBDA("lambda"),NIL("nil"),SET("set!");
	
	private String op;

	OperatorEnum(String op) {
		this.op = op;
	}
	
	public static boolean contains(String value) {
		for(OperatorEnum o :OperatorEnum.values()) {
			if(o.op.equals(value)) {
				return true;
			}
		}
		
		return false;
	}
}
