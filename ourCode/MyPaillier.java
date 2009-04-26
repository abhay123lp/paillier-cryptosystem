package ourCode;
import java.math.BigInteger;
import java.util.Random;

/**
 * @author Ika Bar-Menachem, Nir Hemed
 * This class implements mathematical function used to create encryption system<br>
 * Based on the Pailler cryptoSystem.
 */

public class MyPaillier {
	public final static int NUM_OF_BITS = VotingSystem.numberOfBits; 
	public static Random random = new Random();
	//Big Integer constants
	public final static BigInteger TWO = new BigInteger("2");
	public final static BigInteger THREE = new BigInteger("3");
	public final static BigInteger FOUR = new BigInteger("4");
	


    /**
     * Generation of a big prime.<br>
	 * This method randomly casts a number with NUM_OF_BITS bits, and uses the algorithm learned in class to determine if it is prime.<br>
	 * <b>Complexity:</b> since we are only checking for a number that is of the form 4k+3, k is positive integer, and taking into consideration
	 * The density of primes, it is likely that this method will return after O(NUM_OF_BITS) casts.
     * @return a random prime p, p=4k+3, p is being represented with NUM_OF_BITS bits.
     */
	public static BigInteger generatePrime(){
		boolean probablyPrime = false;
		BigInteger result = BigInteger.ZERO;
		BigInteger m;
		while (!probablyPrime){
			//m is supposed to be a random integer with NUM_OF_BITS bits:
			m = new BigInteger(NUM_OF_BITS, random);
			if (isPrime(m)) {
				probablyPrime = true;
				result = m;
			}
		}
		return result;
	}//end of generatePrime


    /**
     * Checks if a given number is prime, as learned in class.<br>
	 * <b>Complexity:</b> at most we will perform 1 GCD and 3 powerMod: O(NUM_OF_BITS)<br>
     * @param m - the number to be checked.
     * @return
     *  If m is prime then return true.<br>
     *  If m is not prime return false with probability larger then 0.5
     */
	private static boolean isPrime(BigInteger m) {
		if (!(m.mod(FOUR)).equals(THREE)) return false;
		// the number is of the desired form
		if (powerOfInteger(m)) return false;
		// the number is not a power of integer
		BigInteger a = new BigInteger(NUM_OF_BITS, random);
		//we need a to be from Zm and not ZERO:
		a=a.mod(m);
		while (a.equals(BigInteger.ZERO)){
			a = new BigInteger(NUM_OF_BITS, random);
			a=a.mod(m);
		}
		if (!gcd(a,m).equals(BigInteger.ONE)) return false;
		else{
			BigInteger b = powerMod(a, TWO, m);
			BigInteger c = powerMod(b,(m.add(BigInteger.ONE)).divide(FOUR), m);
		
			if (!b.equals(powerMod(c, TWO, m))) return false;

			if (!c.equals(a.mod(m)) && !c.equals((a.negate()).mod(m))) return false;
		}
		return true;
	}


    /**
     * power a number a by n, modulus m
     * @param a the number to be powered
     * @param n the power
     * @param m modulus
     * @Pre n >=1
	 *
	 * this method uses the general principal of exponention by squaring and uses relatively small multiplications (modulared to m)<br> 
	 * to reduce both memory usage and time complexity.<br>
	 * <b>time complexity:</b> The running time of this algorithm is O(log exponent).<br> 
	 *
     * @return a^n modulus m
     */
	public static BigInteger powerMod(BigInteger a, BigInteger n, BigInteger m) {

		BigInteger result = BigInteger.ONE;
		BigInteger tmp = new BigInteger(a.toString());
		char[] arr = n.toString(2).toCharArray();
		int i = 0;
		while (i<arr.length){
			if (arr[arr.length-1-i]=='1') 
				result = result.multiply(tmp).mod(m);
			i++;
			tmp = tmp.pow(2).mod(m);
		}
		return result;
	}

	/**
	 * checks if a number m is a power of an integer, 
	 * (meaning it is definitely not prime)
	 * @param m is the number to be checked 
	 * @return true if and only if m is a power of an integer.
	 */
	private static boolean powerOfInteger(BigInteger m) {
		//Implementation was not made
		//The probability that a random number is a power of an integer is negligible.
		return false;
	}
	/**
	 * @return m^i, where m and i are both BigIntegers.
	 */
	public static BigInteger power(BigInteger m, BigInteger i) {
		BigInteger result = m;
		while(i.compareTo(BigInteger.ONE)!=0){
			result = result.multiply(m);
			i = i.subtract(BigInteger.ONE);
		}
		return result;
	}

	/**
     * This method is not necessary (and was not used) in the final version of the project.<br>
	 * It uses simple binary search to find an approximation to an i'th root for a number x, and time complexity is of course O(log(x))<br>
	 * when trying to determine whether a given number is a power of an integer (in polylogarithmic time - O((log(x)^2) we can use this method.<br>
	 * @return the i'th root of a number x
     */
	@SuppressWarnings("unused")
	private static double computeRoot(long x, long i) { //only for small numbers
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
     * GCD for BigInteger<br>
     * @param n1 a bigNubmer
     * @param n2 a bigNubmer
     * <b>complexity :</b> O((NUM_OF_BITS)^3)
     * @return if n1>=0&& n2>=0 the return will be the g.c.d of n1,n2<br>
	 * 
     */
	public static BigInteger gcd(BigInteger n1, BigInteger n2){
		if (n1.equals(BigInteger.ZERO)) return n2;
		if (n2.equals(BigInteger.ZERO)) return n1;
		// n1 and n2 even
		if ((n1.and(BigInteger.ONE)).equals(BigInteger.ZERO) && (n2.and(BigInteger.ONE).equals(BigInteger.ZERO))) 
				return gcd(n1.shiftRight(1), n2.shiftRight(1)).shiftLeft(1);
		// n1 is even, n2 is odd
		else if ((n1.and(BigInteger.ONE).equals(BigInteger.ZERO))) return gcd(n1.shiftRight(1), n2);
		// n1 is odd, n2 is even
		else if ((n2.and(BigInteger.ONE).equals(BigInteger.ZERO))) return gcd(n1, n2.shiftRight(1));
		// n1 and n2 odd, n1 >= n2
		else if (n1.compareTo(n2) >= 0) return gcd((n1.subtract(n2)).shiftRight(1), n2);
		// n1 and n2 odd, n1 < n2
		else return gcd(n1, (n2.subtract(n1)).shiftRight(1));
		/*if(n2.equals(BigInteger.ZERO)) return n1;
		else{
			BigInteger reminder=n1.remainder(n2);
			n1=n2;
			n2=reminder;
			return gcd(n1,n2);
		}*/
	}//end of gcd


    /**
     * Using the extended Euclidean algorithm
     * This Function Was Checked with Big Numbers
     * @param number , the number we want to calculate the inverse to
     * @param moduluN , is used to create a number from the Ring of g*_moduluN<br>
     * <b>Complexity:</b> O((NUM_OF_BITS)^3).<br>
     * @return answer , where answer*n== 0 mod g*_n
	 *
     */
	public static BigInteger calculateInverse(BigInteger number,BigInteger moduluN){
		BigInteger ans=moduluN;
		BigInteger x=BigInteger.ZERO;
		BigInteger y=BigInteger.ONE;
		BigInteger lastX=BigInteger.ONE;
		BigInteger lastY=BigInteger.ZERO;
		BigInteger quotient;
		BigInteger temp;
		while(moduluN.equals(BigInteger.ZERO)==false){
			quotient=number.divide(moduluN);
			
			temp=moduluN;
			moduluN=number.mod(moduluN);
			number=temp;
			
			temp=x;
			x=lastX.subtract(quotient.multiply(x));
			lastX=temp;
			
			temp=y;
			y=lastY.subtract(quotient.multiply(y));
			lastY=temp;
			
		}
		return lastX.mod(ans);
		
	}//end of calculateInverse


	//Generating Key
	/**
	 * This function generate a public and private key
	 * @return an array of keys of size 2 <br>
	 * the first cell contains the private key (lambda,mu,n)<br>
	 * the second cell contains the public key (n,g)
	 */
	public static Key[] generateKey(){
		BigInteger p=generatePrime();
		BigInteger q=generatePrime();
		while(q.equals(p)){//preventing from p==q
			q=generatePrime();
		}
		BigInteger n = p.multiply(q);//n=p*q
		//lambda=lcm(p-1,q-1)
		BigInteger lambda=(p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		lambda=lambda.divide(gcd(p.subtract(BigInteger.ONE),q.subtract(BigInteger.ONE)));

		//generate g, where g is a random number from Z*_n^2
		BigInteger nSquare = n.pow(2);
		BigInteger g=randomZStar(nSquare);
		//calculate mu
		BigInteger lInput=powerMod(g, lambda, nSquare);
		
		BigInteger mu=calculateInverse(lFucntion(lInput,n), n);
		
		Key[] ans=new Key[2];
		ans[0]= new PrivateKey(lambda,mu,n);
		ans[1]= new PublicKey(n,g);
		return ans;
	}//end of generateKey
	
	
	/**
	 * @param input
	 * @param n
	 * @return (input-1)/n
	 */
	public static BigInteger lFucntion(BigInteger input, BigInteger n) {
		
		return (input.subtract(BigInteger.ONE).divide(n));
	}
	
	/**
	 * @param n >0
	 * @return a random number g where g is from Z*_n
	 */
	public static  BigInteger randomZStar(final BigInteger n) {
		StringBuilder sb = new StringBuilder();
		String nString=n.toString();
		BigInteger ans;
		int temp, i=0;
		//generating random number from [0,n2)
		while(i<nString.length()){
			temp = random.nextInt(nString.charAt(i)-47);
			sb.append(temp);
			if(temp<((int)nString.charAt(i))-48){//if temp is lexicographically smaller then n^2
				sb.append(generateRandomNubmer(nString.length()-i -1));
				break;
			}
			i++;
			
		}//end of while
		
		
		ans=new BigInteger(sb.toString());
		
		//Repeating the process until gcd(ans,n^2) equals one
		return gcd(ans,n).equals(BigInteger.ONE)? ans : randomZStar(n);
	}//end of randomZStarsqr
	
	/**
	 * @param amountOfDigits, the size of the number to generate
	 * @return a random number of "amountOfDigits" digits
	 */
	private static String generateRandomNubmer(int amountOfDigits){
		StringBuilder sb=new StringBuilder();
		while(amountOfDigits>0){
			sb.append(random.nextInt(10));
			amountOfDigits--;
		}
		return sb.toString();
	}//end of generateRandomNubmer
}//end of MyPaillier
