import java.math.BigInteger;


public class sendBox {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println((new BigInteger("3")).toString(2).length());
		System.out.println(MyPaillier.log(new BigInteger("8"), 2));
		System.out.println(MyPaillier.powerMod(new BigInteger("3"), new BigInteger("2"),new BigInteger("7")));
		
		

//		BigInteger a = MyPaillier.generatePrime();
//		System.out.println(a);
//		System.out.println(a.isProbablePrime(10));
		
		
	}

}
