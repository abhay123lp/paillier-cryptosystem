package ourCode;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
/**
 * 
 * @author Ika Bar-Menachem and Nir Hemed
 * this class demonstrate the use of Paillier cryptoSystem by creating a given amount of voters<br>
 * that vote randomly from {0,1} and submit there vote after encryption with public key.<br>
 * after the vote is complete a voting count is done using the homeomorphic properties of the Paillier cryptoSystem
 */
public class VotingSystem {
	
	public static Logger logger = Logger.getLogger("CryptoLogger");
	public static int numberOfBits;
	
	
	public static void main(String[] args) throws SecurityException, IOException {
		long startTime, endTime;
		BigInteger voteCount;
		Scanner reader = new Scanner(System.in);//IO
		
		//logger
		FileHandler fh = new FileHandler("crypoLoggerOfOurFunction.log",true);
		fh.setFormatter(new SimpleFormatter());
		logger.addHandler(fh);
		logger.setLevel(Level.FINE);
		//end of logger
		
		//Choosing the amount of bits
		System.out.println("Enter the number of bits of represantation for keys in the system: ");
		numberOfBits = reader.nextInt();
		
		System.out.println("Please insert the amount of voters you want: ");
		
		int amountOfVoters = reader.nextInt();
		logger.info("*******Stating Voting System**********");
		startTime = System.currentTimeMillis();
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
    	logger.info("Stating vote count");
    	BigInteger nSquare = publicKey.getN().pow(2);
    	voteCount = kalpi[0].getVote();//assuming there is one voter minimum
    	for (int i = 1; i < kalpi.length; i++) {//voting
    		logger.info("now multipling the "+i+" vote");
			voteCount = (voteCount.multiply(kalpi[i].getVote())).mod(nSquare);
		}
    	
    	//now allVotes have the entire votes encrypted
    	logger.info("******Voting result of Our System******");
    	int result = privateKey.decode(voteCount).intValue();
    	endTime = System.currentTimeMillis();
    	logger.info("The amount of pepole that think that the egg came before the chicken is: "+result);
    	logger.info("The amount of pepole that think that the chicken came before the egg is: "+(amountOfVoters-result));
    	logger.info("Total time for vote: "+((endTime-startTime)/1000.0) + " Seconds.");
		
	}//end of main
}//end of Voting System
