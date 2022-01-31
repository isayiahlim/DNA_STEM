
/*
 * Isayiah Lim
 * 1/14/2022
 * APCS Period 2
 * DNA Project
 * 
 * Does it make a protein? This program reads different nucleotide chains from a text file and
 * prints out the statistics of it, including numbers of each nucleotide and the percentage of 
 * Cytosine and Guanine. Then it determines whether a protein is made!
 */
import java.io.FileNotFoundException;
//imports files, scanners, and Arrays (for Arrays.toString)
import java.io.File;
import java.util.Scanner;
import java.util.Arrays;
public class DNA
{
    //class constants - may have to add lines of code if changing the num Nucleotides
	public static final int MINIMUM_LENGTH = 4;
	public static final int CG_PERCENTAGE = 30;
	public static final int NUM_NUCLEOTIDES = 4;
	public static final int CODON_LENGTH = 3;
	/* 
     * Your main should call startAnalysis or startAnalysisPart2 with the name of your current
     * test file. You may prompt the user for the filename if you wish for
     * more flexible testing. NOTE: I will not be calling your main method from my test cases at all!
     */
    public static void main(String[] args) throws FileNotFoundException
    {
    	startAnalysis("dna.txt");
    }
    
    /*
     * This method should serve as the primary method for Part 1 of this project.
     * I call this method directly from my test cases for Part 1 and you therefore should
     * NOT modify this header at all.
     */
    public static void startAnalysis(String filename) throws FileNotFoundException
    {
        //reads through the file
    	File file = new File("./input/" + filename);
        Scanner input = new Scanner(file);
        
        //makes two of the three arrays (nucleotide counts and mass)
        int[] nNum = new int[NUM_NUCLEOTIDES];
        double[] massPercent = new double[NUM_NUCLEOTIDES];
        
        //welcome message
    	System.out.println("Welcome to the DNA Scanner! Results for "+ filename + ":");
    	System.out.println();
    	
    	//declares variables that will be used in printing out the stats
    	String name;
    	String sequence;
    	
    	//prints output and runs until there are no more lines to read
    	while(input.hasNextLine())
    	{
    		//initializes the variables to the name and nucleotides
    		name = input.nextLine();
	    	sequence = input.nextLine().toUpperCase();
	    	
	    	//finds the name and the sequence of nucleotides
	    	System.out.println("Name: " + name);
	    	System.out.println("Nucleotides: " + sequence);
	    	
	    	//finds and prints the number of each nucleotide
	    	nNum = findNCount(sequence);
	    	System.out.println("Nucleotide counts: " + Arrays.toString(nNum));
	    	
	    	//finds and prints the percent of the total mass
	    	massPercent = findMass(nNum);
	    	printMass(massPercent);
	    	
	    	//finds and prints the codons in the sequence
	    	String[] codons = findCodons(sequence);
	    	System.out.println("Codons: " + Arrays.toString(codons));
	    	
	    	//determines and prints whether it's a protein
	    	protein(massPercent, codons);
	    	System.out.println();
    	}
    }
    
    //returns an array with the number of each nucleotide
    public static int[] findNCount(String sequence) 
    {
    	//makes an array with length NumNucleotides
    	int[] num = new int[NUM_NUCLEOTIDES];
    	//+s to array with the # of each nucleotide (must add another else if after changed global)
    	for(int i = 0; i < sequence.length(); i++)
    	{
    		if(sequence.charAt(i) == 'A')
    			num[0] ++;
    		else if(sequence.charAt(i) == 'C')
    			num[1] ++;
    		else if(sequence.charAt(i) == 'G')
    			num[2] ++;
    		else if(sequence.charAt(i) == 'T')
    			num[3] ++;
    	}
    	return num;
    }
    
    //returns an array with the masses of each nucleotide
    public static double[] findMass(int[] nucleotides)
    {
    	//makes a duplicate array that can be modified
    	double[] masses = new double[NUM_NUCLEOTIDES];
    	for(int i = 0; i < nucleotides.length; i++)
    		masses[i] = nucleotides[i];
    	
    	//variable used to calculate total mass of nucleotide
    	double total = 0;
    	
    	//calculates the total masses of each- must add more of these if there are more nucleotides
		masses[0] *= 135.128;
   		masses[1] *= 111.103;
    	masses[2] *= 151.128;
   		masses[3] *= 125.107;
    	
    	//calculates the total mass
    	for(double i : masses)
    		total += i;
    	//calculates the percent mass and reassigns it to the mass array
    	for(int i = 0; i < masses.length; i ++)
    	 	masses[i] = masses[i]/total * 100;
    	return masses;
    }
    
    //prints out the masses given the array 
    public static void printMass(double[] mass)
    {
    	System.out.print("Mass percentages: [" );
    	//prints the percentages to two decimal points
    	for(int i = 0; i < mass.length-1; i++)
			System.out.printf("%.2f, ", mass[i]);
    	System.out.printf("%.2f", mass[NUM_NUCLEOTIDES-1]);
    	System.out.println("]");
    }
    
    //returns an array with the codons
    public static String[] findCodons(String sequence)
    {
    	String[] codons = new String[sequence.length()/CODON_LENGTH];
    	//sets each element in the array to a codon of codon length
    	for(int i = 0; i < sequence.length()/CODON_LENGTH; i++)
    		codons[i] = sequence.substring(CODON_LENGTH*i,CODON_LENGTH*i+CODON_LENGTH);
    	return codons;
    }
    
    /*
     * checks if the string has CG of the right percentage, starts with ATG, Ends with TAA, TAG,
     * or TGA, and is over the minimum length, then prints out whether it is a protein
    */
    public static void protein(double[] massPercent, String[] codons)
    {
    	//makes a variable equal to the last codon
    	String word = codons[codons.length-1];
    	//makes sure the percentage of the two is right
    	if((massPercent[1] + massPercent[2]) < CG_PERCENTAGE)
    		System.out.println("Encodes a protein: no");
    	//makes sure the start codon is right
    	else if(!codons[0].equals("ATG"))
    		System.out.println("Encodes a protein: no");
    	//makes sure the nucleotide string is long enough
    	else if(codons.length < MINIMUM_LENGTH)
    		System.out.println("Encodes a protein: no");
    	//make sure the end codon is right
    	else if(!word.equals("TAA") && !word.equals("TAG") && !word.equals("TGA"))
    		System.out.println("Encodes a protein: no");
    	else
    		System.out.println("Encodes a protein: yes");
    }
    
    
    /*
     * This method should serve as the primary method for Part 2 of this project.
     * I call this method directly from my test cases for Part 2 and you therefore should
     * NOT modify this header at all. You will need to replace null with a proper return value
     * to pass all test cases.
     */
    public static String[][] startAnalysisPart2(String filename) throws FileNotFoundException
    {
        return null;
    }
    
}
