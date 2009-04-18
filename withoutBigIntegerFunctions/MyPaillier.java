import java.math.BigInteger;
import java.util.Random;

/**
 * @author Ika Bar-Menachem, Nir Hemed
 */

public class MyPaillier {
	public final static int NUM_OF_BITS = VotingSystem.numberOfBits; 
	public static Random random = new Random();



	/**
	 * generation of big prime.
	 * 
	 * @return a random prime p, p=4k+3, p is being represented with NUM_OF_BITS bits. 
	 */
	public static BigInteger generatePrime(){
		boolean probablyPrime = false;
		BigInteger result = BigInteger.ZERO;
		while (!probablyPrime){
			//m is supposed to be a random integer with NUM_OF_BITS bits:
			BigInteger m = new BigInteger(NUM_OF_BITS, random);
			if (isPrime(m)) {
				probablyPrime = true;
				result = m;
			}
		}
		return result;
	}//end of generatePrime

	/**
	 * prime check
	 * 
	 * @param m - the number to be checked.
	 * @return :
	 * 			if m is prime then @return = true;
	 * 			if m is not prime, @return = false with probability larger then 0.5 
	 */
	private static boolean isPrime(BigInteger m) {
		final BigInteger FOUR = new BigInteger("4");
		final BigInteger THREE = new BigInteger("3");
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
		if (!gcd(a,m).equals(new BigInteger("1"))) return false;
		else{
			BigInteger b = powerMod(a, new BigInteger ("2"), m);
			BigInteger c = powerMod(b,(m.add(BigInteger.ONE)).divide(FOUR), m);
		
			if (!b.equals(powerMod(c, new BigInteger("2"), m))) return false;

			if (!c.equals(a.mod(m)) && !c.equals((a.negate()).mod(m))) return false;
		}
		return true;
	}

	/**
	 * power a number a modulus m
	 * @param a the number to be powered
	 * @param n the power
	 * @param m modulus
	 * @Pre n>=1
	 * @return a^n modulus m
	 */
	public static BigInteger powerMod(BigInteger a, BigInteger n, BigInteger m) {

		BigInteger result = BigInteger.ONE;
		BigInteger tmp = new BigInteger(a.toString());
		String binaryRep = n.toString(2);
		char[] arr = binaryRep.toCharArray();
		int i = 0;
		while (i<arr.length){
			if (arr[arr.length-1-i]=='1') result = result.multiply(tmp).mod(m);
			i++;
			tmp = tmp.pow(2).mod(m);
		}
		return result;
	}

	/**
	 * checks if a number m is a power of an integer, 
	 * (meaning it is definitely not prime)
	 * @param m is the number to be checked 
	 * @return true iff m is a power of an integer.
	 */
	private static boolean powerOfInteger(BigInteger m) {
		//Implementation was not made
		//The probability that a random number is a power of an integer is negligible.
		return false;
	}
	/**
	 * returns m^i, where m and i are both BigIntegers.
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
	 * returns the i'th root of a number x
	 */
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
	 * GCD for BigInteger
	 * @param n1 a bigNubmer
	 * @param n2 a bigNubmer
	 * @return if n1>=0&& n2>=0 the return will be the g.c.d of n1,n2
	 */
	public static BigInteger gcd(BigInteger n1, BigInteger n2){
		if(n2.equals(new BigInteger("0"))) return n1;
		else{
			BigInteger reminder=n1.remainder(n2);
			n1=n2;
			n2=reminder;
			return gcd(n1,n2);
		}
	}//end of gcd

	/**
	 * Log for big Integer
	 * @param a the BigInteger
	 * @param base the base
	 * @return Log[base,a], in a double type, which is sufficient to numbers with 1024 bits.
	 */
	public static double log(BigInteger a, double base) {
		int b = a.bitLength() - 1;
		double c = 0;
		double d = 1;
		for (int i = b; i >= 0; --i) {
			if (a.testBit(i))
				c += d;
			d *= 0.5;
		}
		return (Math.log(c) + Math.log(2) * b) / Math.log(base);
	}

	/**
	 * Using the extended Euclidean algorithm
	 * This Function Was Checked with Big Numbers
	 * @param number, the number we want to calculate the inverse to
	 * @param n the Ring of g*_n
	 * @return answer, where answer*n== 0 mod g*_n
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
		
	}


	//Generating Key
	//TODO: Complete
	/**
	 * This function generate a public and private key
	 * @return an array of keys of size 2 <br>
	 * on the first cell there is a private key (lambda,mu,n)<br>
	 * on the second cell there is a public key (n,g)
	 */
	public static Key[] generateKey(){
		BigInteger p=generatePrime();
		BigInteger q=generatePrime();
		while(q.equals(p)){//preventing from p==q
			q=generatePrime();
		}
		BigInteger n=p.multiply(q);//n=p*q
		//lambda=lcm(p-1,q-1)
		BigInteger lambda=(p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		lambda=lambda.divide(gcd(p.subtract(BigInteger.ONE),q.subtract(BigInteger.ONE)));

		//generate g, where g is a random number from Z*_n^2
		BigInteger g=randomZStar(n.pow(2));
		//calculate mu
		BigInteger lInput=powerMod(g, lambda, n.pow(2));
		
		BigInteger mu=calculateInverse(lFucntion(lInput,n), n);//TODO: recheck this line
		
		
		Key[] ans=new Key[2];
		ans[0]= new PrivateKey(lambda,mu,n);
		ans[1]= new PublicKey(n,g);
		return ans;
		
	}
	
	
	/**
	 * @param input
	 * @param n
	 * @return (input-1)/n
	 */
	public static BigInteger lFucntion(BigInteger input, BigInteger n) {
		
		return (input.subtract(BigInteger.ONE).divide(n));
	}
	
	/**
	 * @param n>0
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
	
	//Encoding
	
	//Decoding
}
