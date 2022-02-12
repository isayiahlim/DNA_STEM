
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
    	startAnalysisPart2("dna.txt");
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
	    	System.out.println(protein(massPercent, codons));
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
    		else if(sequence.charAt(i)== 'C')
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
    	//prints out the last element
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
    public static String protein(double[] massPercent, String[] codons)
    {
    	//makes a variable equal to the last codon
    	String word = codons[codons.length-1];
    	//makes sure the percentage of the two is right
    	if((massPercent[1] + massPercent[2]) < CG_PERCENTAGE)
    		return "Encodes a protein: no";
    	//makes sure the start codon is right
    	else if(!codons[0].equals("ATG"))
    		return "Encodes a protein: no";
    	//makes sure the nucleotide string is long enough
    	else if(codons.length < MINIMUM_LENGTH)
    		return "Encodes a protein: no";
    	//make sure the end codon is right
    	else if(!word.equals("TAA") && !word.equals("TAG") && !word.equals("TGA"))
    		return "Encodes a protein: no";
    	else
    		return "Encodes a protein: yes";
    }
    
    
    /*
     * This method should serve as the primary method for Part 2 of this project.
     * I call this method directly from my test cases for Part 2 and you therefore should
     * NOT modify this header at all. You will need to replace null with a proper return value
     * to pass all test cases.
     */
    public static String[][] startAnalysisPart2(String filename) throws FileNotFoundException
    {
        //makes a new 2D string Array with the given titles
    	String[][] data = {
    		{"Name", "Nucleotides", "Nucleotide counts", 
    			"Mass percentages", "Codons", "Encodes a protein"}
    	};
    	
    	//reads through the file
    	File file = new File("./input/" + filename);
        Scanner input = new Scanner(file);

    	//welcome message
    	System.out.println("Welcome to the DNA Scanner! Results for "+ filename + ":");
    	System.out.println();
    	
    	/*
    	 * runs through every line of the file, analyzes it, then assigns the 
    	 * statistics to the pre-initialized 2D array, data
    	 */
    	while(input.hasNextLine())
    	{
    		data = scanning(data, input);
    	}
    	
    	//prints out the statistics given the filled out array 'Data'
    	report(data);
    	return data;
    }
    
    //takes the data array and makes it one row longer, filled with correlated stats
    public static String[][] scanning(String [][] data, Scanner input)
    {
    	int rows = data.length;
		//declares and initializes the temp array as one longer than data
    	String[][] tempData = new String[rows + 1][6];
		for(int i = 0; i < rows; i++)
			tempData[i] = data[i];
		
		//puts the name in the name column
		tempData[rows][0] = input.nextLine();
    	
		//finds the sequence of nucleotides and capitalizes it 
		String sequence = input.nextLine().toUpperCase();
    	//finds number of each nucleotide, % of total mass, and fills out the array accordingly
    	int[] nNum = findNCount(sequence);
    	double[] massPercent = findMass(nNum);
    	
    	//nucleotides column
    	tempData[rows][1] = sequence;
    	//nucleotide count column
    	tempData[rows][2] = Arrays.toString(nNum);
    	//mass percentages column
    	tempData[rows][3] = Arrays.toString(massPercent);
    	//codons column
    	tempData[rows][4] = Arrays.toString(findCodons(sequence));
    	//whether it encodes protein column
    	tempData[rows][5] = protein(massPercent, findCodons(sequence));
    	
    	return tempData;
    }
    //prints out all of the results, calling other methods to do so
    public static void report(String[][] data)
    {    	
    	//number of sequences analyzed
    	System.out.println(data.length - 1 + " nucleotide sequences were analyized.");
    	
    	//counts how many proteins are in the 2D array
    	System.out.println(countProteins(data) + " of those sequences were identified as "
    			+ "proteins.");
    	
    	//prints the strand with the most of any nucleotide given the 
    	//nucleotide's position in the array
    	System.out.println(mostNucl(data, 0) + " has the most occurances of Adenine.");
    	System.out.println(mostNucl(data, 1) + " has the most occurances of Cytosine.");
    	System.out.println(mostNucl(data, 2) + " has the most occurances of Guanine.");
    	System.out.println(mostNucl(data, 3) + " has the most occurances of Thymine.");
    	
    	//counts how many sequences begin(false) or end(true) with a given codon in ""
    	System.out.println(countCodon(data, "ATG", false) + " sequences begin with ATG.");
    	System.out.println(countCodon(data, "TAA", true) + " sequences end with TAA.");
    	System.out.println(countCodon(data, "TAG", true) + " sequences end with TAG.");
    	System.out.println(countCodon(data, "TGA", true) + " sequences end with TGA.");
    	
    	//checks if the number of codons is more than 4
    	System.out.println(above4(data) + " sequence contain at least 4 codons.");
    	
    	//counts how many sequences have at least 30% of their mass as C and G
    	System.out.print(percentCG(data) + " ");
    	System.out.println("sequences have at least 30% of their mass from Cytosine and Guanine.");
    }
    
    //counts the number of proteins in an array
    public static int countProteins(String[][] data)
    {
    	int count = 0;
    	//runs through every row in the array- if the answer to encodes a protein is yes,
    	//then it will add one to the count
    	for(int i = 0; i < data.length; i++)
    	{
    		if(data[i][5].equals("Encodes a protein: yes"))
    			count++;
    	}
    	return count;
    }
    
    //finds which protein has the most of the given nucleotide
    public static String mostNucl(String[][] data, int num)
    {
    	int top = 0;
    	String highest = "";
    	for(int i = 1; i < data.length; i++)
    	{
    		//turns the Nucleotide count column in each row into a new String Array
    		String[] newA = data[i][2].substring(1, data[i][2].length() - 1).split(", ");
    		//turns this string into an integer, then if it is greater than the previous highest #
    		//it changes both the highest number of the nucleotide and the correlating name.
    		if(Integer.parseInt(newA[num]) > top)
    		{
    			top = Integer.parseInt(newA[num]);
    			highest = data[i][0];
    		}
    	}
    	return highest;	
    }
    
    //counts how many of a codon begin or end a strand in a given 2D array 
    public static int countCodon(String[][] data, String codon, boolean ends)
    {
    	int num = 0;    	
    	if(ends) 
    	{
    		for(int i = 1; i < data.length; i++)
    			//if the end of the nucleotide sequence equals the codon, num increases
    			if(data[i][1].substring(data[i][1].length()-3).equals(codon))
	    			num++;
    	}
    	else
    	{
    		for(int i = 1; i < data.length; i++)
    			//if the beginning of the sequence equals the codon, num increases
    			if(data[i][1].substring(0,3).equals(codon))
    				num++;
    	}
    	return num;
    }
    
    //sees if the codon count exceeds 4
    public static int above4(String[][] data)
    {
    	int num = 0;
    	for(int i = 0; i < data.length; i++)
    	{
    		//turns the string into an array, then makes sure there are at least 4 codons
    		if(data[i][4].split(", ").length >= 4)
    			num++;
    	}
    	return num;
    }
    
    //counts how many arrays have above 30% cytosine and guanine
    public static int percentCG(String[][] data)
    {
    	int num = 0;
    	for(int i = 1; i < data.length; i++)
    	{
    		//makes a string array equal to the mass percent column in data
    		String[] temp = data[i][3].substring(1, data[i][3].length()-1).split(", ");
    		for(int j = 0; j < temp.length; j++)
    		{
    			//sets each value to a string that can be turned into integer (cuts off decimals)
    			temp[j] = temp[j].substring(0, temp[j].indexOf("."));
    		}
    		//if the c+g > 30, returns one
    		if(Integer.parseInt(temp[1]) + Integer.parseInt(temp[2]) >= 30)
    			num++;
    	}
    	return num;
    }
}
