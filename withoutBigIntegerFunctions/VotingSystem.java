import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class VotingSystem {
	
	public static Logger logger = Logger.getLogger("CryptoLogger");
	
	
	
	public static void main(String[] args) throws SecurityException, IOException {
		BigInteger allVotes; 
		
//		logger.addHandler(new FileHandler("unreal.xml"));
    	logger.setLevel(Level.FINE);	
    	
    	//creating keys.
    	Key [] keys= MyPaillier.generateKey();
    	PrivateKey privateKey = (PrivateKey) keys[0];
    	PublicKey publicKey = (PublicKey) keys[1];
    	
    	logger.info("Stating Voting System");
    	Scanner reader = new Scanner(System.in);
    	System.out.println("Please insert the amount of voters you want: ");
    	int amountOfVoters = reader.nextInt();
    	logger.info("The amount of voters is: "+amountOfVoters);
    	Voter [] kalpi = new Voter[amountOfVoters];
    	for (int i = 0; i < kalpi.length; i++) {//voting
			kalpi[i] = new Voter(publicKey);
		}
    	
    	//submitting the votes (encrypted of course)
    	allVotes = kalpi[0].getVote();//assuming there is one voter minimum
    	for (int i = 1; i < kalpi.length; i++) {//voting
			allVotes = allVotes.multiply(kalpi[i].getVote());
		}
    	
    	//now allVotes have the entire votes encrypted
    	
    	System.out.println("Voting result");
    	BigInteger result = privateKey.decode(allVotes);
    	System.out.println("The amount of pepole that think that the egg came before the chicken is: "+result.toString());
    	System.out.println("The amount of pepole that think that the chicken came before the egg is: "+(amountOfVoters-result.intValue()));
    	
    	
    	
		
//		Voter [] kalpi = new Voter();
		
	}

}
