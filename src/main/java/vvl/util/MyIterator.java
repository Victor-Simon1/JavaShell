package vvl.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyIterator<E> implements Iterator<E>{

		private Demo<E> strs;
		MyIterator(Demo<E> strs){
			this.strs = strs;
		}
		
		@Override
		public boolean hasNext() { 
		    return !strs.isEmpty();
		}
		@Override
		public E next() throws NoSuchElementException{
		    if(this.strs.car() == null) {
		        throw new NoSuchElementException();
		    }
			Demo<E> temp = this.strs;
			strs = (Demo<E>) strs.cdr();
			return temp.car();
		}

		

}
