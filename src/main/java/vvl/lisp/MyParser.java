package vvl.lisp;

import java.math.BigInteger;

import vvl.util.ConsList;
import vvl.util.ConsListFactory;

public class MyParser {
    
    
    private int cptListCourante;
    private int cptListNew ;
    private int cptParOpen ;
    private int cptParClose ;
    private boolean endListe;
    int cpt = 0;
    ConsList<Object>[] in = new ConsList[20];
    /**
     * Constructor
     * Initialize attribute
     */
    public MyParser() {
        cptListCourante = 0;
        cptListNew = 0;
        cptParOpen =0;
        cptParClose = 0;
        endListe = false;
        
    }
    /**
     * Count the number of time who c is in str
     * @param str
     * @param c
     * @return number of apparition of c in str
     */
    public static int countOccurence(String str,String c) {
    
    	return str.length() - str.replace(c,"" ).length();
    }
    
    /**
     * Take a string to transform it into the good object
     * @param str
     * @return a specific object
     */
    private static Object stringToSpecifyObj(String str) {
        
        
        if(str.chars().allMatch( Character::isDigit)){
            return new BigInteger(str);
        }
        else if(str.equals("#t")) {
            return LispBoolean.TRUE;
        }else if(str.equals("#f")) {
            return LispBoolean.FALSE;
        }
        try {
      
            return Double.valueOf(str);
        }
        catch(NumberFormatException e) {
            return str;
        }
    }
    
    
    /**
     * parse a string to transform it into a conslist
     * @param expr
     * @return conslist or an object
     * @throws LispError
     */
    
    public Object parse(String expr) throws LispError {
        var myStr = new MyOwnString(expr);
        String exprWithoutSpace = myStr.deleteUselessSpaces();
        String[] tab = exprWithoutSpace.split(" ");
        
        if(countOccurence(exprWithoutSpace,"(") != countOccurence(exprWithoutSpace,")"))
            throw new LispError("Miss one or many parenthasis");
        else if(!exprWithoutSpace.contains("(")) {
            if(tab.length>1 )
                throw new LispError("More than one element");
            return stringToSpecifyObj(tab[0]);
        }
        else {
           
            var newObj = new StringBuilder() ;
            
            
            while(cpt < exprWithoutSpace.length() && endListe == false ) {

                switch(exprWithoutSpace.charAt(cpt) ) {
                case '(':
                    makeOpenParenthesis();
                    break;
                case ')':
                    newObj = makeCloseParenthesis(newObj);
                    break;  
                case ' ':
                    newObj = makeSpace(newObj);
                    break;
                default:
                    newObj = newObj.append(exprWithoutSpace.charAt(cpt));
                    cpt++;
                    break;
                }   
            }
            return verifyEndList(exprWithoutSpace);
        }   
        
    }
    /**
     * Make the operation when a gave a parenthesis
     */
    
    private void makeOpenParenthesis() {
        cptListCourante = cptListNew;
        in[cptListCourante] = ConsListFactory.nil();
        cptListNew++;
        cptParOpen++;
        cpt++;
    }
    /**
     * make operation when we encounter a parenthesis
     * @param newObj
     * @return a stringbuilder
     */
    private StringBuilder makeCloseParenthesis(StringBuilder newObj) {
        cptParClose++;
        if(newObj.length() != 0) {
            in[cptListCourante] =  in[cptListCourante].append(stringToSpecifyObj(newObj.toString()));
            newObj = new StringBuilder();
        }
        if(cptListCourante != 0) {
              in[cptListCourante-1]=  in[cptListCourante-1].append(in[cptListCourante]);
              cptListCourante--;
              cptListNew--;
              cpt++;
        }
        else{
            if(cptParClose == cptParOpen)
                endListe = true;
            cpt++;
        } 
        
        return newObj;
    }
    /**
     * make operation when we encounter a space
     * @param newObj
     * @return stringbuilder
     */
    private StringBuilder makeSpace(StringBuilder newObj) {
        if(newObj.length() !=0)
            in[cptListCourante] = in[cptListCourante].append(stringToSpecifyObj(newObj.toString()));
        newObj = new StringBuilder();
        cpt++;
        
        return newObj;
    }
    
    /**
     *  verify if we are at the ende of the list
     * @param exprWithoutSpace
     * @return
     * @throws LispError
     */
    private Object verifyEndList(String exprWithoutSpace) throws LispError{
        if(endListe) {
            if(cpt == exprWithoutSpace.length())
                return in[0];
            while(cpt < exprWithoutSpace.length() && exprWithoutSpace.charAt(cpt) == ' ') {
                cpt++;
            }
            cpt++;
            if(cpt != exprWithoutSpace.length())
                throw new LispError("Element aprés Liste");


        }
        
        return in[0];
    }
}
