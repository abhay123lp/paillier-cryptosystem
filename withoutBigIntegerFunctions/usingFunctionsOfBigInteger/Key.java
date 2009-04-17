package usingFunctionsOfBigInteger;

import java.math.BigInteger;

public class Key {
	private BigInteger first;
	private BigInteger second;
	
	public Key(BigInteger first, BigInteger seconed){
		this.first = first;
		this.second = seconed;
	}

	public final String getFirst() {
		return first.toString();
	}

	public final String getSecond() {
		return second.toString();
	}
	
	

}
