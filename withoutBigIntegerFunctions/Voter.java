import java.util.Random;


public class Voter {
	private boolean vote;
	
	public Voter(){
		Random random = new Random();
		vote = random.nextBoolean();
	}

	public final boolean getVote() {
		return vote;
	}
	
	
}
