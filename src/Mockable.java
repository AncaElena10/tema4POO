/**
 * Interface
 * 
 * @author Moisa Anca-Elena, 321 CA
 */
public interface Mockable<A, B> {
	Unit execute();

	B execute(A arg);
}
