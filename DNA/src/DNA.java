
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
//imports files and scanners
import java.io.File;
import java.util.Scanner;
public class DNA
{
    //class constants
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
    	startAnalysis("dna_Expected.txt");
    }
    
    /*
     * This method should serve as the primary method for Part 1 of this project.
     * I call this method directly from my test cases for Part 1 and you therefore should
     * NOT modify this header at all.
     */
    public static void startAnalysis(String filename) throws FileNotFoundException
    {
        //scans the file
    	File file = new File(filename);
        Scanner input = new Scanner(file);
        
        //makes the three types of arrays 
        int[] nucleotideNum = new int[NUM_NUCLEOTIDES];
        double[] massPercent = new double[NUM_NUCLEOTIDES];
        String[] codons = findCodons();
        
        //finds the different parts of the input and prints them out in the output
        output(input, nucleotideNum, massPercent, codons);
        
    }
    
    //prints out all the outputs 
    public static void output(Scanner input, int[] nNum, double[] massPercent, String[] codons)
    {
    	//welcome message
    	System.out.println("Welcome to the DNA Scanner! Results for dna.txt: ");
    	System.out.println();
    	
    	//declares variables that will be used in printing out the stats
    	String name;
    	String sequence;
    	boolean isProtein;
    	
    	//runs until there are no more lines to read
    	while(input.hasNextLine())
    	{
    		//initializes the variables to the name and nucleotides
    		name = input.nextLine();
	    	sequence = input.nextLine().toUpperCase();
	    	
	    	//gives the name and the sequence of nucleotides
	    	System.out.println("Name: " + name);
	    	System.out.println("Nucleotides: " + sequence);
	    	
	    	//finds the number of each codon
	    	nNum = findNCount(sequence);
	    	System.out.println("Nucleotide Counts: " + nNum.toString());
	    	
	    	massPercent = findMass(nNum);
	    	System.out.println("Mass Percentages: " + massPercent.toString());
	    	
	    	codons = findCodons(sequence);
	    	System.out.println("Codons: " + codons.toString());
	    	
	    	isProtein = protein();
	    	System.out.println("Encodes a protein: ");
	    	System.out.println();
    	}	
    }
    
    public static int[] findNCount(String sequence)
    {
    	int[] num = new int[]
    	for(int i = 0; i < sequence.length(); i++)
    	{
    		
    			
    	}
    }
    
    //returns an array with the codons
    public static String[] findCodons(String sequence)
    {
    	
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
