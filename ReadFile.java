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

//The class that is the heart of the program
//User chooses a PDB prgram that is then read
//and the needed bonds are found
public class ReadFile{
  
  //the frame is going to be application window
  private JFrame frame;
  
  //Used to select the PDB file to be read
  private JFileChooser chooser;
  
  //Used to hold which file has been selected
  private int result;
  
  //Used to iinput the file that has been selected
  private File file;
  
  //Reads the file that has been selected
  private FileReader reader;
  
  //creates a frame to store the output information
  private JFrame outputFrame = new JFrame("Output Information");
  
  //An area to put text as needed
  private JTextArea textBox = new JTextArea("Output");
  
  //Allows the text box to have a scroll pane for easy viewing
  private JScrollPane scrollPane = new JScrollPane(textBox);
  
  //Creates an array to store the Atoms from the protein
  private ArrayList<Atom> atomList = new ArrayList<Atom>(0);
  
  //Creates an array to store the Atoms from the HetAtms (ligands)
  private ArrayList<Atom> hetList = new ArrayList<Atom>(0);
  
  //Creates an array to store the Atoms from the ligand if it is labeled ATOM
  private ArrayList<Atom> ligandATOMList = new ArrayList<Atom>(0);
  
  //Creates an array to store the possible Bonds found from running the program
  public ArrayList<Bond> bondList = new ArrayList<Bond>(0);
  
  //Creates an array to store realistic and porbable bonds
  public ArrayList<Bond> realisticBonds = new ArrayList<Bond>(0);

  
  
     
  //Main method to initialize the program
  //Creates a ReadFile to scan the PDB file, then calculates distances and finds bonds
  //based on the parameters set. As of now, only hydrogen bonds.
  public static ArrayList<Bond> main(String[] args) throws IOException{
    ReadFile read = new ReadFile();
    read.calculateDistances();
    read.findBonds();
    return read.getBondList();
  }
  
  //This creates a ReadFile object which is responisable for reading the PDB files.
  public ReadFile() throws IOException, NullPointerException {
    
    //creates a Jframe window and puts a File Chooser in it for user input
    frame = new JFrame(); 
    frame.setVisible(true);
    chooser = new JFileChooser();
    Font font = new Font("Verdana", Font.BOLD, 14);
    textBox.setFont(font);
    //the value of the user's choice is saved
    result = chooser.showOpenDialog(frame);
    //if a file was chosen, it is saved. If none is chosen, prints error.
    if (result == JFileChooser.APPROVE_OPTION)
      file = chooser.getSelectedFile();
    else
    {
      //If no file is selected, the program terminates
      JOptionPane.showMessageDialog(null, "No file selected");
      System.exit(1);
    }
    
    frame.setVisible(false);
    
    //Creates a text box for the output information to be displayed
    textBox.setText("");
    outputFrame.add(scrollPane, BorderLayout.CENTER);
    outputFrame.setSize(600,1000);
    outputFrame.setVisible(true);
    
    //Creates a reader to pull the text from the file
    //chosen previously
    try{
      reader = new FileReader(file);
    } catch (FileNotFoundException e){
      System.err.println("FileNotFoundException");
    }
  
    //Buffered Reader to use the methods
    BufferedReader bReader = new BufferedReader(reader);
    
    //Keeps track of the line that the reader is on
    String line = "Empty";
    
    //which atom the reader is on
    int i = -1;
    int o = -1;
    
    //Reads until the end of Document by finding the word "End", which notes the end of PDB files
    //
    //This loop reads every line in the PDB file and pulls out
    //the data for ATOMs and HETATMs which signify atoms of large and small molecules, respectively
    while(line.startsWith("END") == false){
      //bReader reads each line. It does not read words or paragraphs
      line = bReader.readLine();
      
      //if the line starts with "ATOM" or "HETATM"
      //it is read since we want this information
      if(line.startsWith("ATOM") || line.startsWith("HETATM")){
        
        //The Scanner reads each block of text in the String line
    	  //Currently this scanner is based on spaces in the text file
    	  //Unfortunately, some PDB files input spaces incorrectly.
    	  //This scanner will be changed to  a different one that can
    	  //account for the archaic nature of PDB files.
        Scanner scan = new Scanner(line);
        //When there is an atom, all the data is imported into an atom
        //and stored with the other atoms
        if(line.startsWith("ATOM")){
          i++;
          atomList.add(new Atom());
          scan.next(); //"Atom"
          atomList.get(i).setMolNumber(Integer.parseInt(scan.next())); //atom number in the molecule chain
          atomList.get(i).setFullSymbol(scan.next()); //full atomic symbol of the atom
          atomList.get(i).setResidue(scan.next()); //amino acid residue that the atom is apart of
          atomList.get(i).setChain(scan.next()); //the chain of the protein that the atom is apart of
          atomList.get(i).setResidueNumber(Integer.parseInt(scan.next())); //the amino acid residue number in the protein
          atomList.get(i).setXCoord(Double.parseDouble(scan.next())); //x coordinate of the atom
          atomList.get(i).setYCoord(Double.parseDouble(scan.next())); //y coordinate of the atom
          atomList.get(i).setZCoord(Double.parseDouble(scan.next())); //z coordinate of the atom
          //these scans will hold the other values
          //but not needed now
          //these two values are skipped and not logged as they are irrelevant.
          //Will be added in the future if needed.
          scan.next(); 
          scan.next();
          atomList.get(i).setSymbol(Character.toString(line.charAt(line.length()-3)));  //simplified atomic symbol
          
          //Prints everything read above. This is to check to see if it matches the PDB file
          //It is extremely ugly and ill formatted, but fits its purpose.
          //Will be cleaned up in the future.
          textBox.append(Integer.toString(atomList.get(i).getMolNumber()) + "   ");
          textBox.append(atomList.get(i).getFullSymbol() + "   ");
          textBox.append(atomList.get(i).getResidue() + "   ");
          textBox.append(atomList.get(i).getChain() + " ");
          textBox.append(Integer.toString(atomList.get(i).getResidueNumber()) + "   ");
          textBox.append(Double.toString(atomList.get(i).getXCoord()) + "   ");
          textBox.append(Double.toString(atomList.get(i).getYCoord()) + "   ");
          textBox.append(Double.toString(atomList.get(i).getZCoord()) + "   ");
          textBox.append(atomList.get(i).getSymbol() + "\n");
          
        }
        
        //When there is a hetatm, all the data is imported into an Atom object
        //and stored with the other hetatms
        //Uses the same procedure as above, but reads the HETATM type as oppsoed to ATOM in PDB files.
        if(line.startsWith("HETATM")){
          o++;
          hetList.add(new Atom());
          scan.skip("HETATM");
          //scan.next();
          hetList.get(o).setMolNumber(Integer.parseInt(scan.next()));
          hetList.get(o).setFullSymbol(scan.next());
          hetList.get(o).setResidue(scan.next());
          hetList.get(o).setChain(scan.next());
          hetList.get(o).setResidueNumber(Integer.parseInt(scan.next()));
          hetList.get(o).setXCoord(Double.parseDouble(scan.next()));
          hetList.get(o).setYCoord(Double.parseDouble(scan.next()));
          hetList.get(o).setZCoord(Double.parseDouble(scan.next()));
          //these scans will hold the other values
          //but not needed now
          scan.next();
          scan.next();
          hetList.get(o).setSymbol(Character.toString(line.charAt(line.length()-3)));
          
          //Appends the pulled information into the textbox to check. Same as above
          textBox.append(Integer.toString(hetList.get(o).getMolNumber()) + "   ");
          textBox.append(hetList.get(o).getFullSymbol() + "   ");
          textBox.append(hetList.get(o).getResidue() + "   ");
          textBox.append(hetList.get(o).getChain() + "   ");
          textBox.append(Integer.toString(hetList.get(o).getResidueNumber()) + "   ");
          textBox.append(Double.toString(hetList.get(o).getXCoord()) + "   ");
          textBox.append(Double.toString(hetList.get(o).getYCoord()) + "   ");
          textBox.append(Double.toString(hetList.get(o).getZCoord()) + "   ");
          textBox.append(hetList.get(o).getSymbol() + "\n");          
        }
        
      
      }
    }
  }
  
  //This method calculates the distance between two input atoms
  public void calculateDistances(){
    
    String ligandName = "VOID";
    String ligandChainName = "VOID";
    
    //the scanner records the inputs from the user
    Scanner userInputScanner = new Scanner(System.in);
    
    //System asks user for which chain of the protein is being tested. This will be known by the user
    System.out.print("Which polymer chain of the protein?");
    String chainName = userInputScanner.nextLine();
    
    //Systems asks the user what the small molecule is defined as in terms of residue name found in the scanner above.
    // Most PDB files use HETATM, but some define them as a separate chain of ATOMS, which is very annoying. 
    //I wish to implement a more elegant solution in the future since PDB files are slightly different.
    System.out.print("Is the ligand an ATOM or HETATM?");
    String ligandType = userInputScanner.nextLine();
    
    //if else statement to handle whether the binding ligand is noted as a HETATM or ATOM in the PDB file
    //When it is labeled as a HETATM, it is given a residue name that the user will know
    if(ligandType.equals("HETATM")){
      System.out.print("Which residue? ");
      ligandName = userInputScanner.nextLine();
    }
    
    //When it is labeled as ATOMs, they will be given a polymer chain, generally A or B, that the user will know.
    else if(ligandType.equals("ATOM")){
      System.out.print("Which polymer chain is the ligand labeled as?");
      ligandChainName = userInputScanner.nextLine();
    }
    
    //When it is noted as neither, the user had an error in inputs.
    //The program ends. In the future, the code will re-ask to identify the error.
    //As of now, I did not have the time nor skill to address this easily.
    else{
      System.out.print("Error: Incompatible inputs. ErrorID 001");
    }
           
    //A huge issue with my code is the lack of finding bonds between water molecules
    //When I implement this part of the program, this input will be required
    
       //System askes the user whether they wish to find bonds with water as well
       //System.out.print("Include waters?");
       //String includeWaters = userInputScanner.nextLine();
       
    
    
    //these values keep track of which atom or bond is being tested during the calculations

    //the atom in the protein
    int j = 0;
    //the hetatom in question (ligand)
    int k = 0;
    //the bond number which has been created
    int p = 0;
    
    //Calculates the distances if the ligand is logged as a HETATM
    if(ligandType.equals("HETATM")){    
      
      //this loop checks each atom in the protein (ATOM) to each ligand atom (HETATOM)
      //and creates a bond when they successfully pass all checks
      while(j < atomList.size()){
        //keeps track of the atom on the ligand
        k = 0;
        
        while(k < hetList.size()){
          
          //is it the right residue? right chain? 
        	//When waters a are tested, the water atoms will also be included for bond distance
          if(hetList.get(k).getResidue().equals(ligandName) //|| (hetList.get(k).getResidue().equals("HOH") && includeWaters.equals("Yes"))) //adds water to be checked on protein
               && atomList.get(j).getChain().equals(chainName) 
               // is it a hydrogen bonding atom?
               && //(((hetList.get(k).getSymbol().equals("H")) 
             
             //this method looks for atoms that are involved in H bonding
             //but not actual Hydrogen atoms since exact hydrogen location is unknown
             //This method suggests where H-bonds would be using O, N, and F atoms
             (hetList.get(k).getSymbol().equals("O") 
                || hetList.get(k).getSymbol().equals("N")
                || hetList.get(k).getSymbol().equals("F"))
               
               &&(atomList.get(j).getSymbol().equals("O") 
                    || atomList.get(j).getSymbol().equals("N") 
                    || atomList.get(j).getSymbol().equals("F")))
          { 
            
            
            
           //This section finds the distance between the two target atoms
        	  //The atoms are pulled form the atom lists.
            Atom atom1 = atomList.get(j);
            Atom atom2 = hetList.get(k);
            
            //Euclidian distance equation
            double xd = atom1.getXCoord()-atom2.getXCoord();
            double yd = atom1.getYCoord()-atom2.getYCoord();
            double zd = atom1.getZCoord()-atom2.getZCoord();
            
            double distance = Math.sqrt(xd*xd + yd*yd + zd*zd);
            
            //Sets the distance to 3 decimal points which reflects the significant figures given by PDB files
            distance = (double)Math.round(distance * 1000) / 1000;
            
            
            //if all the information is correct for a Hydrogen bond
            //a new bond is created with the given atoms
            bondList.add(new Bond(atomList.get(j) , hetList.get(k), distance));
            
            //H bonds exist from 2.4-3.6 angstroms
            if(distance >= 2.4 && distance <= 3.6){
              bondList.get(p).setType("Possible Hydrogen");
            }
            //Above 3.6 angstroms most likely means no interactions in proteins, specifically.
            if(distance > 3.6){
              //bondList.get(p).setType("Possible van der Waals"); //Later versions might print something here if needed
            }
            //Any distance smaller than 2.4 is most likely a van der Waals interaction. These will be accounted for later as well
            if(distance < 2.4){
              bondList.get(p).setType("Too small");
            }
            //this increments to a new spot in the bond list
            p++;
          }
          
          //moves to the next HETATM once the previous one
          //has been successfully checked and added if needed
          k++;
        }
        //moves to the next ATOM to check
        j++;
      }
    }
    
    //This emulates the same process if the ligand is labeled as ATOM
    else if(ligandType.equals("ATOM")){
      
      //this loop checks each atom in the protein (ATOM) to each ligand atom
      //and creates a bond when they successfully pass all checks
      while(j < atomList.size()){
        
        //keeps track of the atom on the ligand
        k = 0;
        
        while(k < atomList.size()){
          
          //is it the right residue? right chain? This will also include waters eventually. They are are more important in the ligand
          if(atomList.get(k).getChain().equals(ligandChainName) //|| (hetList.get(k).getResidue().equals("HOH") && includeWaters.equals("Yes"))) //adds water to be checked on protein
               && atomList.get(j).getChain().equals(chainName) 
               // is it a hydrogen bonding atom?
               && //(((hetList.get(k).getSymbol().equals("H")) 
             
               //this method looks for atoms that are involved in H bonding
               //but not actual Hydrogen atoms since exact hydrogen location is unknown
               //This method suggests where H-bonds would be using O, N, and F atoms
             (atomList.get(k).getSymbol().equals("O") 
                || atomList.get(k).getSymbol().equals("N")
                || atomList.get(k).getSymbol().equals("F"))
               
               &&(atomList.get(j).getSymbol().equals("O") 
                    || atomList.get(j).getSymbol().equals("N") 
                    || atomList.get(j).getSymbol().equals("F")))
          { 
            
            
            
        	   //This section finds the distance between the two target atoms
        	  //The atoms are pulled form the atom lists.
            Atom atom1 = atomList.get(j);
            Atom atom2 = atomList.get(k);
            
            //Euclidian Distance equation
            double xd = atom1.getXCoord()-atom2.getXCoord();
            double yd = atom1.getYCoord()-atom2.getYCoord();
            double zd = atom1.getZCoord()-atom2.getZCoord();
            
            double distance = Math.sqrt(xd*xd + yd*yd + zd*zd);
            
            //Sets the distance to 3 decimal points which reflects the significant figures given by PDB files
            distance = (double)Math.round(distance * 1000) / 1000;
            
            //if all the information is correct for a Hydrogen bond
            //a new bond is created with the given atoms
            bondList.add(new Bond(atomList.get(j) , atomList.get(k), distance));
            
            //H bonds exist from 2.4-3.6 angstroms
            if(distance >= 2.4 && distance <= 3.6){
              bondList.get(p).setType("Possible Hydrogen");
            }
            //Above 3.6 angstroms most likely means no interactions and does nothing
            if(distance > 3.6){
             // bondList.get(p).setType("Possible van der Waals"); //This might print a message depending on future versions
            }
            //below 2.4 angstroms means there will most likely not be a hydrogen bond. WIll be changed to incorporate other interactions later
            if(distance < 2.4){
              bondList.get(p).setType("Too small");
            }
            //this increments to a new spot in the bond list
            p++;
          }
          
          //moves to the next HETATM once the previous one
          //has been successfully checked and added if needed
          k++;
        }
        //moves to the next ATOM to check
        j++;
      }
    }
  }
  
  //This method checks the distance between molecules to see if they are possible for a bond
  //As of now, it is set up for only hydrogen bonds, but will
  //be fixed as need be.
  //The primary atom sorting is in the calculateDistances() method.
  public void findBonds(){
    
    //keeps track of which bond is being checked in the loop
    int i = 0;
    
    //This loop goes through every bond found in the calculateDistance() method
    while(i < bondList.size()){
      
      //if the bond is the appropriate length or smaller. Smaller ones are added as they might have other important information
      if(bondList.get(i).getDistance() <= 3.6){
        
        //if the bond fits the criteria of a reasonable bond, it is added to the list.
        realisticBonds.add(bondList.get(i));
        
        //Prints the full information of each ATOM and the distance between them
        textBox.append(bondList.get(i).getAtom1().getMolNumber() + "   " + 
                       bondList.get(i).getAtom1().getSymbol() + "   "  + 
                       bondList.get(i).getAtom1().getFullSymbol() + "   "  + 
                       bondList.get(i).getAtom1().getResidue() + "   " +
                       bondList.get(i).getAtom1().getResidueNumber() + "   " +
                       bondList.get(i).getAtom2().getMolNumber() + "   " + 
                       bondList.get(i).getAtom2().getSymbol()+ "   " + 
                       bondList.get(i).getAtom2().getFullSymbol() + "   "  + 
                       bondList.get(i).getAtom2().getResidue() + "   " +
                       bondList.get(i).getAtom2().getResidueNumber() + "   " +
                       bondList.get(i).getType() + "   " +
                       bondList.get(i).getDistance() + "\n");
        //goes on to the next bond
        i++;
      }
      else{
        //goes on to the next bond
        i++;
      }
    }
  }
  //the array list of probable bonds is kept if needed elsewhere.
  public ArrayList<Bond> getBondList(){
    return realisticBonds;
}
}
