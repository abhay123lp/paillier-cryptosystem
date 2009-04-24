package ourCode;
import java.math.BigInteger;

/**
 * @author Ika Bar-Menachem & Nir Hemed<br>
 * this class represent a private key of the Paillier cryptography system<br>
 * The public key holds lambda, mu and n.<br>
 * The private key given a cipher can decipher the message and return the message.
 */
public class PrivateKey extends Key{
	private BigInteger lambda; //lcm of (p-1,q-1)
	private BigInteger mu;
	private BigInteger n;
	
	/**
	 * Constructor<br>
	 * @param lambda= lcm of (p-1,q-1)
	 * @param mu= (L(g^lambda mod n^2)^-1) mod n
	 * @param n = p*q Two Random numbers
	 */
	public PrivateKey(BigInteger lambda, BigInteger mu, BigInteger n){
		this.lambda=new BigInteger(lambda.toString());
		this.mu=new BigInteger(mu.toString());
		this.n=new BigInteger(n.toString());
	}
	
	/**
	 * @param cipher
	 * @return m = L(c^lambda mod n^2)*mu mod n<br>
	 * where L(u) = (u-1)/n<br>
	 * m is the message that was encoded by the public key.
	 */
	public BigInteger decode(BigInteger cipher){
		BigInteger m = (MyPaillier.lFucntion(MyPaillier.powerMod(cipher, lambda, n.pow(2)), n).multiply(mu)).mod(n);
		return m;
	}//end of decode
	
}//end of PrivateKey
