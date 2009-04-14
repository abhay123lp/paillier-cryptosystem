import java.math.BigInteger;
import java.util.Random;


public class sandBox {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println(MyPaillier.isPrime(new BigInteger(32,5,new Random())));
		System.out.println(MyPaillier.generatePrime().isProbablePrime(5));
		
		//System.out.println(MyPaillier.calculateInverse(new BigInteger("5"), new BigInteger("28")));
	}

}
