import java.math.BigInteger;


public class PrivateKey extends Key{
	private BigInteger lambda;
	private BigInteger mu;
	
	public PrivateKey(BigInteger lambda, BigInteger mu){
		this.lambda=new BigInteger(lambda.toString());
		this.mu=new BigInteger(mu.toString());
	}
	
	public BigInteger getLambda() {
		return lambda;
	}
	
	public BigInteger getMu() {
		return mu;
	}
	
}
