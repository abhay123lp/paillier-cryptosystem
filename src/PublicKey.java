import java.math.BigInteger;



public class PublicKey extends Key {
	private BigInteger n;
	private BigInteger g;
	
	public PublicKey(BigInteger n, BigInteger g){
		this.n=new BigInteger(n.toString());
		this.g=new BigInteger(g.toString());
	}

	public final BigInteger getN() {
		return n;
	}

	public final BigInteger getG() {
		return g;
	}
	
	
}//end of PublicKey
