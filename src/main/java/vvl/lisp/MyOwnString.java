package vvl.lisp;

public class MyOwnString {
    
    private String str;
    private  StringBuilder newString = new StringBuilder();
    private boolean passedParent = false;
    
    public MyOwnString(String pString) {
        str = pString;
    }
    
    private String deleteBefore() throws LispError {
        var cpt = 0;
        var charBeforPar = false;
        
        while(cpt < str.length() && str.charAt(cpt) != '(') {
            if(str.charAt(cpt) != ' ' && str.charAt(cpt) != '\t') 
                charBeforPar = true;
            cpt++;
        }
        var begin = cpt;
        if(charBeforPar && begin != str.length()) 
            throw new LispError("Character before parenthesis");

        return str;
    }
    
    public void addWhenOpenParenthesis(int cpt) {
        passedParent = true;
        newString.append(str.charAt(cpt));
    }
    
    public void addWhenSpace(int cpt) {
        if(cpt +1 < str.length() && !passedParent && str.charAt(cpt+1)!= ' ' && str.charAt(cpt+1)!= ')') {
            	newString.append(str.charAt(cpt));
            
        }
    }
    
    public void addWhenTab(int cpt) {
        if(cpt+1 < str.length() && !passedParent && str.charAt(cpt+1)!= ' ' && str.charAt(cpt+1)!= '\t' && str.charAt(cpt+1)!= ')') {
            	newString.append(' ');
        }
    }
    public  String deleteUselessSpaces() throws LispError{
        var cpt = 0;
        
        str = this.deleteBefore();
        cpt = 0;
       
        while(cpt <str.length()) {
            if(str.charAt(cpt) == '(') {
                addWhenOpenParenthesis(cpt);
            }
            else if(str.charAt(cpt) == ' ' ) {
                addWhenSpace(cpt);
            }
            else if(str.charAt(cpt) == '\t') {
                addWhenTab(cpt);
            }

            else {
                newString.append(str.charAt(cpt));
                passedParent = false;
            }
            cpt++;  
        }
 
        if(newString.length() == 0)
            throw new LispError("Contains only space , tab or no elements");
        return newString.toString();
    }
}
