package codeWithBigIntegerFunctionUsage;

import java.math.BigInteger;

public class PublicKeyWithUsage extends KeyWithUsage{

		private BigInteger n;
		private BigInteger g;
		
		public PublicKeyWithUsage(BigInteger n, BigInteger g){
			this.n=new BigInteger(n.toString());
			this.g=new BigInteger(g.toString());
		}

		public BigInteger encode(BigInteger message){
			BigInteger nSquare = n.pow(2);
			BigInteger r = MyPallierWithUsage.randomZStar(n);
				
			BigInteger c = g.modPow(message, nSquare).multiply(r.modPow(n, nSquare)).mod(nSquare);
			return c;
		}//end of encode

		public BigInteger getN() {
			return n;
		}
		
		
}//end of PublicKey

