package ourCode;
import java.math.BigInteger;



public class PublicKey extends Key {
	private BigInteger n;
	private BigInteger g;
	
	public PublicKey(BigInteger n, BigInteger g){
		this.n=new BigInteger(n.toString());
		this.g=new BigInteger(g.toString());
	}
	
	/**
	 * @param message
 	 * @return c = (g^m)*(r^n) mod n^2
	 */
	public BigInteger encode(BigInteger message){
		BigInteger nSquare = n.pow(2);
		BigInteger r = MyPaillier.randomZStar(n);
		
		BigInteger c = (MyPaillier.powerMod(g, message, nSquare)).multiply(MyPaillier.powerMod(r, n, nSquare)).mod(nSquare);
		return c;
	}
	
	public String toString(){
		return "Public Key:/n n = "+n+"/n g = "+g;
	}
	
	
}//end of PublicKey
