package vvl.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class Demo<E> implements ConsList<E>{
	protected Cons<E,ConsList<E>> cons ;
	protected int sizeCons = 0;
	
	/**
	 * 
	 * @param cons
	 */
	public Demo(Cons<E,ConsList<E>> cons) {
		this.cons = cons;
	}
	
	@SuppressWarnings("unchecked")
    @Override
	public ConsList<E> prepend(Object e) {
		Demo<E> newConsList = (Demo<E>) ConsListFactory.singleton(e);
		newConsList.setSize(this.sizeCons);
		newConsList.cons.setRight(this);
		return newConsList;
	}
	public Cons<E,ConsList<E>> getCons() {
		return cons;
	}
	@SuppressWarnings("unchecked")
    @Override
	public ConsList<E> append(Object e) {

		Demo<E> newCons = (Demo<E>)ConsListFactory.singleton(e);
		Demo<E> newList = (Demo<E>)ConsListFactory.singleton(this.cons.left());// a modif pour car
		Demo<Object> temp = (Demo<Object>) this;
		Demo<E> pointeur= newList;
		Demo<E>debut = newList;
		if(temp.sizeCons != 0) {
			temp = (Demo<Object>) temp.cons.right();
			pointeur.setSize(this.sizeCons);	
			while(temp.getCons() != ConsListFactory.finalCons){
				pointeur.cons.setRight((Demo<E>)ConsListFactory.singleton(temp.cons.left()));
				pointeur = (Demo<E>) pointeur.cons.right();
				pointeur.setSize(temp.sizeCons);
				temp = (Demo<Object>) temp.cons.right();
			}
			pointeur.cons.setRight(newCons); 
		}
		else {
			newCons.setSize(0);
			return newCons;
		}

		return debut;
	}

	@Override
	public boolean isEmpty() {
	   return this == ConsListFactory.finalConslist;
	 
	}

	@Override
	public E car() {
	    if(this == ConsListFactory.finalConslist)
	    	throw new NoSuchElementException();
		return this.cons.left();
	}

	@SuppressWarnings("unchecked")
    @Override
	public ConsList<E> cdr() {
	    if(this.cons.right() == null)
	    	return (ConsList<E>) ConsListFactory.finalConslist;
		return this.cons.right();
	}

	@Override
	public int size() {
		return sizeCons;
	}
	/**
	 * add one to the size of the conslist
	 * @param size
	 */
	public void setSize(int size) {
		this.sizeCons = size +1;

	}
	@SuppressWarnings("unchecked")
    @Override
	public ConsList<E> map(Function f) {
		ConsList<E> newList = ConsListFactory.nil();
		ConsList<E> pointeur = this;
		while(!pointeur.isEmpty()){
			E o = (E) f.apply(pointeur.car()) ;
			newList = newList.append(o);
			pointeur = ((Demo<E>) pointeur).getCons().right();
		}
		return newList;
	}
	@Override
	public Iterator<E> iterator() {
		return new MyIterator<>(this);
	}

    @Override
	public String toString() {
		var stb = new StringBuilder();
		Demo<E> pointeur = this;
		
		if(this.isEmpty()) {
		    return "()";
		}
		stb.append("(");
		while(!pointeur.isEmpty()) {
			stb.append( pointeur.cons.left() );
			stb.append(" ");
			pointeur =  (Demo<E>) pointeur.cons.right();
		}
		
		return  stb.substring(0, stb.length()-1) +")";

	}
	
	@Override
	public int hashCode() {
		return super.hashCode()+1;
	
	}
	
	@SuppressWarnings("unchecked")
    @Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(this.getClass() != obj.getClass())
			return false;
	    Demo<E> other = (Demo<E>) obj;
	    Demo<E> thisObj = this;
	
	   
	    while(true) {
	         if(thisObj.cdr() == ConsListFactory.finalConslist && other.cdr() == ConsListFactory.finalConslist) {
	             return true;
	         }
	         if(thisObj.car() == null && other.car() == null) {
	        	 other = (Demo<E>) other.cdr();
	        	 thisObj = (Demo<E>) thisObj.cdr();
	         }
	         if(thisObj.car().equals(other.car())) {
	        	 other = (Demo<E>) other.cdr();
	        	 thisObj = (Demo<E>) thisObj.cdr();
	         }
	         else {
	             return false;
	         }
	    }
	  
       
      
		
	}
}
