import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.Timer;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class VotingSystem {
	
	public static Logger logger = Logger.getLogger("CryptoLogger");
	public static int numberOfBits;
	
	
	public static void main(String[] args) throws SecurityException, IOException {
		long startTime;
		long endTime;
		BigInteger allVotes;
		Scanner reader = new Scanner(System.in);//IO
		
		//logger
		FileHandler fh = new FileHandler("crypoLoggerOfOurFunction.log",true);
		fh.setFormatter(new SimpleFormatter());
		logger.addHandler(fh);
		logger.setLevel(Level.FINE);
		//end of logger
		
		//Choosing the amount of bits
		System.out.println("Enter the amount of bits the system should have ");
		numberOfBits = reader.nextInt();
		
		logger.info("Stating Voting System");
		startTime = System.currentTimeMillis();
		System.out.println("Please insert the amount of voters you want: ");
		int amountOfVoters = reader.nextInt();
		logger.info("The amount of voters is: "+amountOfVoters);
		
    	//creating keys.
    	logger.info("Generating public and private keys. please wait...");
    	Key [] keys= MyPaillier.generateKey();
    	PrivateKey privateKey = (PrivateKey) keys[0];
    	PublicKey publicKey = (PublicKey) keys[1];
    	
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
    	logger.info("******Voting result******");
    	int result = privateKey.decode(allVotes).intValue();
    	endTime = System.currentTimeMillis();
    	logger.info("The amount of pepole that think that the egg came before the chicken is: "+result);
    	logger.info("The amount of pepole that think that the chicken came before the egg is: "+(amountOfVoters-result));
    	logger.info("The vote started at: "+startTime);
    	logger.info("The vote ended at: "+endTime);
    	logger.info("Total time for vote: "+(endTime-startTime));
		
	}//end of main
}//end of Voting System
