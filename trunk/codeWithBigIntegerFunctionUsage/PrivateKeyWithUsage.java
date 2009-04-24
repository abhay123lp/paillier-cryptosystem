package codeWithBigIntegerFunctionUsage;


import java.math.BigInteger;


public class PrivateKeyWithUsage extends KeyWithUsage {
	private BigInteger lambda;
	private BigInteger mu;
	private BigInteger n;
	
	public PrivateKeyWithUsage(BigInteger lambda, BigInteger mu, BigInteger n){
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
		BigInteger m=(MyPallierWithUsage.lFucntion(cipher.modPow(lambda, n.pow(2)), n).multiply(mu)).mod(n);
		return m;
	}
	
}
