package vvl.util;

/**
 * Factory to create new lists.
 * 
 * The methods take advantage of type inference to simplify the use of the
 * methods in the user code.
 * 
 * The body of the methods must be completed by the students.
 * 
 * @author leberre
 *
 */
public final class ConsListFactory{
	
	protected static Cons<Object,ConsList<Object>> finalCons = new Cons<>(null,null);
	protected static Demo<Object> finalConslist = new Demo<>(finalCons);
	

    private ConsListFactory() {
        // do nothing
    }

    /**
     * Create a new empty list.
     * 
     * @return an empty list
     */
    public static <T> ConsList<T> nil() {
    	return (ConsList<T>) ConsListFactory.finalConslist;
    }

    /**
     * Create a new list containing a single element
     * 
     * @param t
     *            an object
     * @return a list containing only t
     */
    public static <T> ConsList<T> singleton(T t) {
    	Cons<Object,ConsList<Object>> cons = new Cons<>(t,finalConslist);
    	Demo<Object> c = new Demo<>(cons);
    	c.setSize(0);
    	return (ConsList<T>) c;
    }

    /**
     * Create a new list containing the elements given in parameter
     * 
     * @param ts
     *            a variable number of elements
     * @return a list containing those elements
     */
    @SafeVarargs
    public static <T> ConsList<T> asList(T... ts) {
    	ConsList<T> newConsList = ConsListFactory.nil();
    	for(T ts1: ts) {
    		newConsList = newConsList.append(ts1);
    	}
    	return newConsList;

    }




}
