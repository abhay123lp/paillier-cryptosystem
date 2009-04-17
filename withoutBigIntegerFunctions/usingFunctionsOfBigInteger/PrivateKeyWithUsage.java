package usingFunctionsOfBigInteger;


import java.math.BigInteger;


public class PrivateKeyWithUsage {
	private BigInteger lambda;
	private BigInteger mu;
	private BigInteger n;
	
	public PrivateKeyWithUsage(BigInteger lambda, BigInteger mu, BigInteger n){
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
//		BigInteger m=(MyPallierWithUsage.lFucntion(MyPallierWithUsage.powerMod(cipher, lambda, n.pow(2)), n).multiply(mu)).mod(n);
//		return m;
		return null;
		
	}
	
}
