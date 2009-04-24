package ourCode;
import java.math.BigInteger;
import java.util.Random;


public class sandBox {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//checking ModInverse:
//		BigInteger b = new BigInteger("23408923713548623870235");
//		BigInteger n = new BigInteger("5897203894561564874984052454240897120956013894");
//		
//		System.out.println(b.modInverse(n));
//		System.out.println(MyPaillier.calculateInverse(b, n));
//		System.out.println("Is the function calculateInverse is currect: "+b.modInverse(n).equals(MyPaillier.calculateInverse(b, n)));
		
		
		//checking the encryption and decryption:
		MyPaillier mp = new MyPaillier();
		Key [] keys = mp.generateKey();
		PrivateKey privateKey = (PrivateKey) keys[0];
		PublicKey publicKey = (PublicKey) keys[1];
		BigInteger c = publicKey.encode(BigInteger.ONE);//encryption of 1
		BigInteger m = privateKey.decode(c);
		System.out.println("c: "+c);
		System.out.println("m: "+m);
		
		
		
//		
//		System.out.println(MyPaillier.isPrime(new BigInteger(32,5,new Random())));
//		System.out.println(MyPaillier.generatePrime().isProbablePrime(5));
//		
		//System.out.println(MyPaillier.calculateInverse(new BigInteger("5"), new BigInteger("28")));
	}

}
