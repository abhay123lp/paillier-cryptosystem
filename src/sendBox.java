import java.math.BigInteger;


public class sendBox {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BigInteger bInt=MyPaillier.gcd(new BigInteger("1071"), new BigInteger("1029"));
		System.out.println(bInt);
	}

}
