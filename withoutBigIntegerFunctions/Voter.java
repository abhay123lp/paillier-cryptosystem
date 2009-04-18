import java.math.BigInteger;
import java.util.Random;

public class Voter {
	private static int counter = 0;
	private int vote;
	private int voterID;
	PublicKey publicKey;
	
	/**
	 * Voter Constructor <br>
	 * the constructor do the following things:<br>
	 * 1. sets the public key.<br>
	 * 2. randomly choose a secret vote between 0 and 1.<br>
	 * 3. log his choice in the logger.<br>
	 * @param _publicKey, a public key that the voter use in order to send his vote.
	 */
	public Voter(PublicKey _publicKey){
		this.voterID=counter; // initialization voter id
		counter++;
		this.publicKey = _publicKey; //setting public key
		vote = new Random().nextInt(2); //generating vote between 0 and 1
		VotingSystem.logger.info("Voter number "+voterID+" has been created. and voted for: "+vote);
	}

	/**
	 * @return the voter choice encrypted with the public key.
	 */
	public final BigInteger getVote() {
		BigInteger c = publicKey.encode(new BigInteger(Integer.toString(vote)));
		VotingSystem.logger.info("Voter nubmer "+voterID+" has sent his encrypted vote: "+c.toString());
		return c;
	}
	
	
}
