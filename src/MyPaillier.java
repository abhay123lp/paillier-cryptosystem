import java.math.BigInteger;
/**
 * @author Ika Bar-Menachem, Nir Hemed
 */

public class MyPaillier {
	
	
	
	/**
	 * GCD for BigInteger
	 * @param n1 a bigNubmer
	 * @param n2 a bigNubmer
	 * @return if n1>=0&& n2>=0 the return will be the g.c.d of n1,n2
	 */
	public static BigInteger gcd(BigInteger n1, BigInteger n2){
		if(n2.equals(new BigInteger("0"))) return n1;
		else{
			BigInteger reminder=n1.divideAndRemainder(n2)[1];
			n1=n2;
			n2=reminder;
			return gcd(n1,n2);
		}
	}//end of gcd
	
	/**
	 * GCD for int
	 * @param n1 a bigNubmer
	 * @param n2 a bigNubmer
	 * @return if n1>=0&& n2>=0 the return will be the g.c.d of n1,n2
	 */
	public static int gcd(int n1, int n2){
		if(n2==0) return n1;
		int reminder=n1%n2;
		n1=n2;
		n2=reminder;
		return gcd(n1,n2);
	}
	
	//Generating Key
	
	
	//Encoding
	
	//Decoding
}
