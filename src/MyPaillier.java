import java.math.BigInteger;
import java.util.Random;
/**
 * @author Ika Bar-Menachem, Nir Hemed
 */

public class MyPaillier {

	/**
	 * power a number a modulus m
	 * @param a the number to be powered
	 * @param n the power
	 * @param m modulus
	 * @Pre n>=1
	 * @return a^n modulus m
	 */
	public static BigInteger powerMod(BigInteger a,BigInteger n, BigInteger m){//n should be a BigInteger?
		BigInteger result = a;
		while (n.compareTo(BigInteger.ONE)>0){
			result = result.multiply(a);
			if (result.compareTo(m)>0) result=result.mod(m);
			n = n.subtract(BigInteger.ONE);
		}
		return result;
	}

	/**
	 * generation of big prime.
	 * 
	 * @return a random prime p, p=4k+3, p is being represented with 1024 bits. 
	 */
	public static BigInteger generatePrime(){
		boolean probablyPrime = false;
		BigInteger result = new BigInteger("0");
		while (!probablyPrime){
			//m is supposed to be a random integer with 1024 bits:
			BigInteger m = new BigInteger(1024, new Random());
			if (isPrime(m)) {
				probablyPrime = true;
				result = m;
			}
		}
		return result;
	}//end of generatePrime

	//TODO: turn back into private!
	public static boolean isPrime(BigInteger m) {
		BigInteger FOUR = new BigInteger("4");
		BigInteger THREE = new BigInteger("3");
		if (!(m.mod(FOUR)).equals(THREE)) return false;
		// the number is of the desired form
		System.out.println("the number is of the desired form");
		if (powerOfInteger(m)) return false;
		// the number is not a power of integer
		System.out.println("the number is not a power of integer");
		BigInteger a = new BigInteger(1024, new Random());
		//we need a to be from Zm and not ZERO:
		a=a.mod(m);
		while (a.equals(BigInteger.ZERO)){
			a = new BigInteger(1024, new Random());
			a=a.mod(m);
		}
		if (!gcd(a,m).equals(new BigInteger("1"))) return false;
		else{
			BigInteger b = powerMod(a, new BigInteger ("2"), m);
			BigInteger c = powerMod(b,(m.add(BigInteger.ONE).divide(FOUR)), m);
			if (!b.equals(powerMod(c, new BigInteger("2"), m))) return false;
			if (!c.equals(a.mod(m)) || !c.equals((a.negate()).mod(m))) return false;
		}
		return true;
	}

	private static boolean powerOfInteger(BigInteger m) {

		return true;
	}

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

	//ika: why do we need this? 
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
