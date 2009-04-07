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
	public static BigInteger powerMod(BigInteger a,BigInteger n, BigInteger m){
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
		System.out.println("a is: "+ a);
		if (!gcd(a,m).equals(new BigInteger("1"))) return false;
		else{
			BigInteger b = powerMod(a, new BigInteger ("2"), m);
			BigInteger c = powerMod(b,(m.add(BigInteger.ONE)).divide(FOUR), m);
			System.out.println("b is: "+ b);
			System.out.println("c is: "+ c);
			if (!b.equals(powerMod(c, new BigInteger("2"), m))) return false;
			System.out.println("test 1");
			if (!c.equals(a.mod(m)) && !c.equals((a.negate()).mod(m))) return false;
			System.out.println("test 2");
		}
		return true;
	}

	public static boolean powerOfInteger(BigInteger m) {
		boolean ans = false;
		long x = m.longValue();
		long i = 2;
		while (i<= Math.log10((double)x)/Math.log10(2.0)){
			double r = computeRoot(x, i);
			if (Math.round(r)==r){ // meaning r is an integer;
				ans = true;
				break;
			}
			else i++;
		}
		return ans;
	}
	public static double computeRoot(long x, long i) { //is double a problem here?
		double tmp = x;
		double high = x;
		double low = 0;
		while (Math.abs(Math.pow(tmp, i)-x)>0.0001){//we need epsilon machine here 
			//simple binary search:
			tmp= (high+low)/2.0;
			if (Math.pow(tmp, i)>x)
				high = tmp;
			else if (Math.pow(tmp,i)<x) 
				low = tmp;
		}
		//this is to fix the error caused by using a fraction, if necessary: 
		if (Math.abs(Math.round(tmp)-tmp)<0.00001) tmp = Math.round(tmp);
		return tmp;
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

	//Generating Key


	//Encoding

	//Decoding
}
