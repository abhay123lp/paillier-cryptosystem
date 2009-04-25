package ourCode;
import java.math.BigInteger;

/**
 * Public Key
 * @author Ika Bar-Menachem and Nir Hemed
 * this class represent the public key in the pailler cryptoSystem
 * 
 */
public class PublicKey extends Key {
	private BigInteger n; //n = p*q
	private BigInteger g; //a random number from Z*_n^2
	
	/**
	 * @param n = p*q
	 * @param g = a random number from Z*_n^2
	 */
	public PublicKey(BigInteger n, BigInteger g){
		this.n=new BigInteger(n.toString());
		this.g=new BigInteger(g.toString());
	}//end of constructor
	
	/**
	 * @param message
 	 * @return c = (g^m)*(r^n) mod n^2, a cipher of  the message.
	 */
	public BigInteger encode(BigInteger message){
		BigInteger nSquare = n.pow(2);
		BigInteger r = MyPaillier.randomZStar(n);
		
		BigInteger c = (MyPaillier.powerMod(g, message, nSquare)).multiply(MyPaillier.powerMod(r, n, nSquare)).mod(nSquare);
		return c;
	}//end of encode
	
	public String toString(){
		return "Public Key:/n n = "+n+"/n g = "+g;
	}

	public BigInteger getN() {
		
		return n;
	}
	
}//end of PublicKey
