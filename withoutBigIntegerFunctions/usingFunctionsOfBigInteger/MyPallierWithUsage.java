package usingFunctionsOfBigInteger;

import java.math.BigInteger;
import java.util.Random;

/**
 * @author Ika Bar-Menachem, Nir Hemed
 */

public class MyPallierWithUsage {
    public final static int NUM_OF_BITS = 32; 
    public static Random random = new Random();
    public static BigInteger n;

	/**
	 * generation of big prime.
	 * 
	 * @return a random prime p, p=4k+3, p is being represented with 1024 bits. 
	 */
    public static void main(String[] args) {
		//generate Key
    	BigInteger p = BigInteger.probablePrime(NUM_OF_BITS, random);
		BigInteger q = BigInteger.probablePrime(NUM_OF_BITS, random);
		while(q.equals(p)){//preventing from p==q
			System.out.println("a");
			q=BigInteger.probablePrime(NUM_OF_BITS, random);
		}
		n = p.multiply(q); //n=p*q
		
		//calculate lambda =lcm(p-1,q-1) ==> lambda = (p-1*q-1) /gcd(p-1,q-1)
		BigInteger lambda = (p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE))).divide(p.subtract(BigInteger.ONE).gcd(q.subtract(BigInteger.ONE)));
		
		//generate g, where g is a random number from Z*_n^2
    	BigInteger g = randomZStar(n.pow(2));//TODO: find equivalent function in BigInteger
    	
    	//calculate mu = (L(g^lambda mod (n^2)))^-1 mod n
    	BigInteger mu = lFuntion(g.modPow(lambda, n.pow(2))).modInverse(n);
    	
    	//private Key
    	PrivateKeyWithUsage privateKey = new PrivateKeyWithUsage(lambda, mu,n);
    	PublicKeyWithUsage publicKey = new PublicKeyWithUsage(n,g);
    	
    	
    	//testing encryption
    	BigInteger c1 = publicKey.encode(new BigInteger("2"));
    	BigInteger c2 = publicKey.encode(BigInteger.ONE);
    	System.out.println("C1: "+c1);
    	System.out.println("C2: "+c2);
    	BigInteger m1 = privateKey.decode(c1);
    	BigInteger m2 = privateKey.decode(c2);
    	System.out.println("M1: "+m1);
    	System.out.println("M2: "+m2);
    	//m3 = c1*c2 mod nSquare
    	BigInteger m3 = privateKey.decode(c1.multiply(c2));
    	System.out.println("M3: "+m3);
    	
    	
    	
	}



	private static BigInteger lFuntion(BigInteger input) {
		return (input.subtract(BigInteger.ONE)).divide(n);
	}


	//Generating Key
	
	
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
		return ans.gcd(n).equals(BigInteger.ONE)? ans : randomZStar(n);
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



	/**
	 * @param input
	 * @param n
	 * @return (input-1)/n
	 */
	public static BigInteger lFucntion(BigInteger input, BigInteger n) {
		
		return (input.subtract(BigInteger.ONE).divide(n));
	}
	
	//Encoding
	
	//Decoding
}

