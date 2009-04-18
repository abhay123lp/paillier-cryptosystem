package usingFunctionsOfBigInteger;

import java.math.BigInteger;

public class PublicKeyWithUsage {

		private BigInteger n;
		private BigInteger g;
		
		public PublicKeyWithUsage(BigInteger n, BigInteger g){
			this.n=new BigInteger(n.toString());
			this.g=new BigInteger(g.toString());
		}

		public final BigInteger getN() {
			return n;
		}

		public final BigInteger getG() {
			return g;
		}
		
		public BigInteger encode(BigInteger message){//TODO: Fix this.
			BigInteger nSquare = n.pow(2);
			BigInteger r = MyPallierWithUsage.randomZStar(n);
			//TODO: the following line assume that (g^m)*(r^n) mod n^2 equals ((g^m) mod n^2)*((r^n) mod n^2)) mod n^2
			//TODO: check if it is arithmetically true
				
			BigInteger c = g.modPow(message, nSquare).multiply(r.modPow(n, nSquare)).mod(nSquare);
			return c;
		}//end of encode
		
		
}//end of PublicKey

