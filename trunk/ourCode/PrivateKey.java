package ourCode;
import java.math.BigInteger;


public class PrivateKey extends Key{
	private BigInteger lambda;
	private BigInteger mu;
	private BigInteger n;
	
	public PrivateKey(BigInteger lambda, BigInteger mu, BigInteger n){
		this.lambda=new BigInteger(lambda.toString());
		this.mu=new BigInteger(mu.toString());
		this.n=new BigInteger(n.toString());
	}
	
	/**
	 * @param cipher
	 * @return m = L(c^lambda mod n^2)*mu mod n<br>
	 * where L(u) = (u-1)/n
	 */
	public BigInteger decode(BigInteger cipher){
		BigInteger m = (MyPaillier.lFucntion(MyPaillier.powerMod(cipher, lambda, n.pow(2)), n).multiply(mu)).mod(n);
		return m;
	}
	
}
