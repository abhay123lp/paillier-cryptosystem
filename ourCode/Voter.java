package ourCode;
import java.math.BigInteger;
import java.util.Random;

public class Voter {
	private static int counter = 1;
	private int vote;
	private int voterID;
	PublicKey publicKey;
	private static Random random = new Random();
	
	/**
	 * Voter Constructor <br>
	 * the constructor do the following things:<br>
	 * 1. sets the public key.<br>
	 * 2. randomly choose a secret vote between 0 and 1.<br>
	 * 3. log his choice in the logger.<br>
	 * @param _publicKey , a public key that the voter use in order to send his vote.
	 */
	public Voter(PublicKey _publicKey){
		this.voterID=counter; // initialization voter id
		counter++;
		this.publicKey = _publicKey; //setting public key
		vote = random.nextInt(2); //generating vote between 0 and 1
		VotingSystem.logger.info("Voter number "+voterID+" has been created. and voted for: "+vote);
	}//end of constructor

	/**
	 * @return the voter choice encrypted with the public key.<br>
	 * The voter vote is discreet and is written in the logger for task evaluation only.
	 */
	public final BigInteger getVote() {
		BigInteger c = publicKey.encode(new BigInteger(Integer.toString(vote)));
		VotingSystem.logger.fine("Voter nubmer "+voterID+" has sent his encrypted vote: "+c.toString());
		return c;
	}//end of getVote
}//end of Voter
