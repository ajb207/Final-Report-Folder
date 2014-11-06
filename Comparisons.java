package dealwisCode;


import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;
import java.text.Format;

//This is the over arching class of my whole code since the development name for my idea was "Comparisons".

public class Comparisons{
  
  //the list of all BondLists from ReadFile input
  //private ArrayList<ArrayList<Bond>> comparisonList;// = new ArrayList<ArrayList<Bond>>(0);
  
	
	//Viewing window. Moved elsewhere, but might be put back here in later versions
  //a JFrame window to display the information gathered.
 // private JFrame outputFrame = new JFrame("Comparison Output Information");
  //An area to put text as needed
  //private JTextArea textBox = new JTextArea("Output");
  
  //main method that starts the whole program
  public static void main(String[] args) throws IOException{
    
    //asks the user for the number of files to be read.
    Scanner userInputScanner = new Scanner(System.in);
    System.out.print("How many files?");
    int numberOfFiles = Integer.parseInt(userInputScanner.nextLine());
    
   //Arrays that store the data for the comparison
    //An array of lists is created containing a list for each PDB file that will be run
    ArrayList<ArrayList<Bond>> comparisonList = new ArrayList<ArrayList<Bond>>(numberOfFiles);
    //an Array of lists for the Atoms that are found. They are added for easy viewing purposes.
    ArrayList<Atom> commonBondedAtoms = new ArrayList<Atom>(0);
    
    //JFrame window to view the information
    JFrame outputFrame = new JFrame("Comparison Output Information");
    //An area to put text as needed
    JTextArea textBox = new JTextArea("Output");
    JScrollPane scrollPane = new JScrollPane(textBox);
    textBox.setText("");
    outputFrame.add(scrollPane, BorderLayout.CENTER);
    outputFrame.setSize(600,1000);
    outputFrame.setVisible(true);
    
    //Loop to find and print each atom from the comparisonList.
    int j = 0;
    while(j < numberOfFiles){
      //initializes a ReadFile to mine the PDB file
      String[] arguments = new String[] {};
      //the realisticBonds are imported to the comparisonList
      comparisonList.add(ReadFile.main(arguments));
      //This loop adds visible text about the bonds to the output textbox
      int i = 0;
      while(i < comparisonList.get(j).size()){
        textBox.append(comparisonList.get(j).get(i).getAtom1().getMolNumber() + "   " + 
                       comparisonList.get(j).get(i).getAtom1().getSymbol() + "   "  + 
                       comparisonList.get(j).get(i).getAtom1().getFullSymbol() + "   "  + 
                       comparisonList.get(j).get(i).getAtom1().getResidue() + "   " +
                       comparisonList.get(j).get(i).getAtom1().getResidueNumber() + "   " +
                       comparisonList.get(j).get(i).getAtom2().getMolNumber() + "   " + 
                       comparisonList.get(j).get(i).getAtom2().getSymbol()+ "   " + 
                       comparisonList.get(j).get(i).getAtom2().getFullSymbol() + "   "  + 
                       comparisonList.get(j).get(i).getAtom2().getResidue() + "   " +
                       comparisonList.get(j).get(i).getAtom2().getResidueNumber() + "   " +
                       comparisonList.get(j).get(i).getType() + "   " +
                       comparisonList.get(j).get(i).getDistance() + "\n");
        i++;
      }
      j++;
    }
    //keeps track of which ligand bond list is being tested
    int m = 0;
    //keeps track of which bond is being tested
    int n = 0;
    //keeps track of the atoms in commonBondedAtoms
    int h = 0;
    
    //this loop pulls the residue from the protein that is being bonded
    //then tracks how many times it pops up among the other ligands
    //This is similar to the original loop above, but used for cleaning up the outputs
    while (m < comparisonList.size()){
      n = 0;
      while(n < comparisonList.get(m).size()){
        //pulls the identification information from the Atom in the protein and stores it in 
        //comparison list and also prints the information
        commonBondedAtoms.add(comparisonList.get(m).get(n).getAtom1());
        textBox.append(commonBondedAtoms.get(h).getResidue() + " " +
                       Integer.toString(commonBondedAtoms.get(h).getResidueNumber()) + " " +
                       commonBondedAtoms.get(h).getFullSymbol() + " " +
                       "\n");
        h++;
        n++;
      }
      m++;
    }
  }
}