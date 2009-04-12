import java.math.BigInteger;
import java.util.Random;

/**
 * @author Ika Bar-Menachem, Nir Hemed
 */

public class MyPaillier {
    public final static int NUM_OF_BITS = 32;  
	/**
	 * power a number a modulus m
	 * @param a the number to be powered
	 * @param n the power
	 * @param m modulus
	 * @Pre n>=1
	 * @return a^n modulus m
	 */
	public static BigInteger powerMod(BigInteger ans,BigInteger a, BigInteger n, BigInteger m){
		if (ans.compareTo(m)>0) ans = ans.mod(m);
		if (n.equals(BigInteger.ONE)) return ans;
		if ((n.mod(new BigInteger("2"))).equals(BigInteger.ZERO)){//n is even
			return ((powerMod(ans, a, n.divide(new BigInteger ("2")), m)).multiply(powerMod(ans,a, n.divide(new BigInteger ("2")), m))).mod(m);
		}
		else return a.multiply(powerMod(ans, a, n.subtract(BigInteger.ONE), m));
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
			BigInteger m = new BigInteger(NUM_OF_BITS, new Random());
			if (isPrime(m)) {
				probablyPrime = true;
				result = m;
			}
		}
		return result;
	}//end of generatePrime

	//TODO: turn back into private!
	public static boolean isPrime(BigInteger m) {
		final BigInteger FOUR = new BigInteger("4");
		final BigInteger THREE = new BigInteger("3");
		if (!(m.mod(FOUR)).equals(THREE)) return false;
		// the number is of the desired form
		System.out.println("the number is of the desired form");
		if (powerOfInteger(m)) return false;
		// the number is not a power of integer
		System.out.println("the number is not a power of integer");
		BigInteger a = new BigInteger(NUM_OF_BITS, new Random());
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


	//TODO: nir, please add descripton here, thanks :)
	public static BigInteger powerMod(BigInteger a, BigInteger n, BigInteger m) {
		BigInteger[] arr = new BigInteger[n.toString().length()];
		BigInteger result = a;
		String binaryRep = n.toString(2);
		arr[0]=a;
		for (int i=1; i< arr.length; i++){
			arr[i] = arr[i-1].pow(2).mod(m);
			if (binaryRep.charAt(i)=='1') result = result.multiply(arr[i]).mod(m);
		}
		return result;
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
	 * Log for big Integer
	 * @param a the BigInteger
	 * @param base the base
	 * @return Log[base,a]
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
	 * 
	 * @param number, the nubmer we want to calculate the inverse to
	 * @param n the Ring of g*_n
	 * @return ans, where ans*n== 0 mod g*_n
	 */
	public static BigInteger calculateInverse(BigInteger number,BigInteger n){
		//TODO: complete function
		return null;
	}


	//Generating Key
	//TODO: Complete
	public Key[] generateKey(){
		BigInteger p=generatePrime();
		BigInteger q=generatePrime();
		while(!q.equals(p)){//preventing from p==q
			q=generatePrime();
		}
		BigInteger n=p.multiply(q);//n=p*q
		//lambda=lcm(p-1,q-1)
		BigInteger lambda=(p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		lambda=lambda.divide(gcd(p.subtract(BigInteger.ONE),q.subtract(BigInteger.ONE)));

		//generate g, where g is a random number from Z*_n^2
		BigInteger g=randomZStarNsqr(n);
		//calculate mu
		BigInteger lInput=powerMod(g, lambda, n.pow(2));
		
		BigInteger mu=calculateInverse(lFucntion(lInput,n), n);//TODO: recheck this line
		
		
		Key[] ans=new Key[2];
		ans[0]=(Key) new PrivateKey(lambda,mu);
		ans[1]=(Key) new PublicKey(n,g);
		return ans;
		
	}
	//Encoding
	
	/**
	 * @param input
	 * @param n
	 * @return (input-1)/n
	 */
	private static BigInteger lFucntion(BigInteger input, BigInteger n) {
		
		return (input.subtract(BigInteger.ONE).divide(n));
	}
	
	/**
	 * @param n>0
	 * @return a random number g where g is from Z*_n^2
	 */
	public static  BigInteger randomZStarNsqr(BigInteger n) {//TODO: test function
		String ans="";
		BigInteger randomNumberG;
		BigInteger n2=n.pow(2);
		
		
		//generating random number from [0,n2)
		
		String inputStrint=n2.toString();
		boolean lexicorfeclyBigger=false;//equals true iff n is lexicoraficly bigger then ans
		
		
		final BigInteger TEN=new BigInteger("10");
		int num;
		while (n2.compareTo(BigInteger.ZERO)==1){
			num=(int)(Math.random()*10);
			if(!lexicorfeclyBigger){
				num=(int)(Math.random()*(int)n2.toString().charAt(0));//TODO: FIX THIS LINE
				lexicorfeclyBigger=true;
			}
			ans+=num;
			n2=n2.divide(TEN);
			
		}
		randomNumberG=new BigInteger(ans);
		n2=n.pow(2);
		//checking if the nubmer is valid
		
		//TODO: remvoe following lines.
		System.out.print("randomNumberG.compareTo(n2)<=0: ");
		System.out.println(randomNumberG.compareTo(n2)<=0);
		System.out.print("gcd(randomNumberG,n2).compareTo(BigInteger.ONE)==0");
		System.out.println(gcd(randomNumberG,n2).compareTo(BigInteger.ONE)==0);
		
		if(randomNumberG.compareTo(n2)<=0&&gcd(randomNumberG,n2).compareTo(BigInteger.ONE)==0){
			return randomNumberG;
		}
		System.out.println("again");
		return randomZStarNsqr(n);//repeting the process if the nubmer we gentrated is
								  //bigger then n^2 or if the gcd of n^2 and the random number is not equal to one.
	}

	//Decoding
}
