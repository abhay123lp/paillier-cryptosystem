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
	
	public BigInteger getLambda() {
		return lambda;
	}
	
	public BigInteger getMu() {
		return mu;
	}
	
	public BigInteger decode(BigInteger cipher){//TODO: 
		BigInteger m=(MyPaillier.lFucntion(MyPaillier.powerMod(cipher, lambda, n.pow(2)), n).multiply(mu)).mod(n);
		return m;
	}
	
}
