/*******************************************************************************
* File:       LeastSquares.java
* Programmer: Uriel Salazar
* Date:       April 14, 2017
* Course:     CSC-18B
* Purpose:    Text file I/O, implement JFileChooser control and calculate
              regression coefficients
*******************************************************************************/

//package class
package my.leastsquares;

//import packages
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

//declare class
public class LeastSquares extends JPanel implements ActionListener
{
    //declare & initialize variables
    String  data         = null;
    boolean errorLock    = true;
    short   pairs        = 0;
    float   xSum         = 0;
    float   ySum         = 0;
    float   xSumSquared  = 0;
    float   xyProductSum = 0;
    float   xAverage     = 0;
    float   yAverage     = 0;
    float   slope        = 0;
    float   yIntercept   = 0;

    //declare ArrayList objects
    ArrayList<String> xStrings        = new ArrayList<>();
    ArrayList<String> yStrings        = new ArrayList<>();
    ArrayList<Float>  xValues         = new ArrayList<>();
    ArrayList<Float>  yValues         = new ArrayList<>();
    ArrayList<Float>  xValuesSquared  = new ArrayList<>();
    ArrayList<Float>  xyProductValues = new ArrayList<>();

    //declare objects
    JFileChooser fc;
    JButton      openButton;
    JTextArea    area;
    JScrollPane  areaScrollPane;

    public LeastSquares()
    {
        super(new BorderLayout());

        //create and set up JTextArea
        area = new JTextArea(5, 20);
        area.setMargin(new Insets(5, 5, 5, 5));
        area.setEditable(false);
        areaScrollPane = new JScrollPane(area);

        //create and set up file chooser
        fc = new JFileChooser();
        fc.setDialogTitle("Least Squares");
        fc.setCurrentDirectory(new java.io.File("."));
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

        //create open button
        openButton = new JButton("Open a File...");
        openButton.addActionListener(this);
        openButton.setIcon(new javax.swing.ImageIcon(getClass().getResource(
                "OpenFolder.png")));
        
        //place open button on seperate panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(openButton);
        
        //add button and area to panel
        add(buttonPanel, BorderLayout.PAGE_START);
        add(areaScrollPane, BorderLayout.CENTER);
    }
    
    public void actionPerformed(ActionEvent e)
    {
        //handle open button action
        if(e.getSource() == openButton)
        {
            //decide actions for file chooser
            int returnVal = fc.showOpenDialog(LeastSquares.this);
            if(returnVal == JFileChooser.APPROVE_OPTION)
            {
                //assign selected file name to string variable
                String file = fc.getSelectedFile().getName();

                try
                {
                    //declare BufferedReader object
                    BufferedReader reader = new BufferedReader(
                            new FileReader(file));

                    //output confirmation message
                    area.append("Selected file: " + file + "\n\n");

                    //declare StringBuilder object
                    StringBuilder builder = new StringBuilder();
                    String line = reader.readLine();

                    //read in file line by line
                    while (line != null)
                    {
                        builder.append(line);
                        builder.append(System.lineSeparator());

                        //declare bool & temporary string variables
                        Boolean spaceCheck = false;
                        String xTemp = "";
                        String yTemp = "";

                        //grab characters and add them to string arraylists
                        for(int i = 0; i < line.length(); i++)
                        {
                            if(line.charAt(i) == ' ')
                            {
                                spaceCheck = true;
                            }
                            if(spaceCheck == false)
                            {
                                xTemp += line.charAt(i);
                            }
                            else if((spaceCheck == true) &&
                                    (line.charAt(i) != ' '))
                            {
                                yTemp += line.charAt(i);
                            }
                        }
                        //add strings to array list
                        xStrings.add(xTemp);
                        yStrings.add(yTemp);

                        //count each newline as an ordered pair
                        pairs++;

                        //read next line in txt file
                        line = reader.readLine();   
                    }
                    //assign file contents to string variable
                    data = builder.toString();
                    
                    //print output
                    area.append(data + "\n");

                    //close bufferedReader
                    reader.close();
                }
                catch(IOException ioe)
                {
                    //output error message
                    area.append(String.format(
                            "Error reading file: %s\n", file));
                    errorLock = false;
                }
                //convert string arraylists into float arraylists
                for(String stringValue : xStrings)
                {
                    try
                    {
                        xValues.add(Float.parseFloat(stringValue.trim()));
                    }
                    catch(NumberFormatException nfe)
                    {
                        System.err.format("Error parsing value: %s\n",
                                stringValue);
                        errorLock = false;
                    }
                }
                for(String stringValue : yStrings)
                {
                    try
                    {
                        yValues.add(Float.parseFloat(stringValue.trim()));
                    }
                    catch(NumberFormatException nfe)
                    {
                        System.err.format("Error parsing value: %s\n",
                                stringValue);
                        errorLock = false;
                    }
                }
                //square all elements within xValues arraylist & store in new
                //arraylist
                for(float number : xValues)
                {
                    xValuesSquared.add(number * number);
                }
                //multiply the "i'th" elements within each arraylist & strore in
                //new arraylist
                for(int i = 0; i < pairs; i++)
                {
                    xyProductValues.add(xValues.get(i) * yValues.get(i));
                }
                //sum all elements within each float arraylist
                for(int i = 0; i < pairs; i++)
                {
                    xSum += xValues.get(i);
                }
                for(int j = 0; j < pairs; j++)
                {
                    ySum += yValues.get(j);
                }
                for(int c = 0; c < pairs; c++)
                {
                    xSumSquared += xValuesSquared.get(c);
                }
                for(int d = 0; d < pairs; d++)
                {
                    xyProductSum += xyProductValues.get(d);
                }
                //calculate averages for both x and y values
                xAverage = xSum / pairs;
                yAverage = ySum / pairs;

                //calculate the slope of the least squares line equation
                slope = (xyProductSum - (xSum * yAverage)) /
                        (xSumSquared - (xSum * xAverage));

                //calculate the y-intercept of the least squares equation
                yIntercept = yAverage - (slope * xAverage);

                if(errorLock == true)
                {
                   //print equation of least squares line
                    area.append(String.format("Equation of least squares line: "
                        + "y = %.5f%s%.5f%s", slope, "x + ", yIntercept,
                        "\n\n")); 
                }
            }
            else
            {
                area.append("Open command cancelled by user\n\n");
            }
            //reset all variables and array lists
            resetFields();
        }
    }

    //declare and define method
    public void resetFields()
    {
        //declare & initialize variables
        data         = null;
        errorLock    = true;
        pairs = 0;
        xSum = ySum = xSumSquared = xyProductSum = xAverage = yAverage = slope
                    = yIntercept = 0;
        xStrings.clear();   
        yStrings.clear();
        xValues.clear();
        yValues.clear();
        xValuesSquared.clear();
        xyProductValues.clear();
    }
    
    //declare and define method
    private static void createAndShowGUI()
    {
        //create and set up window
        JFrame frame = new JFrame("Least Squares");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //add content to the window
        frame.add(new LeastSquares());
        
        //display window
        frame.setSize(450, 350);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    //declare main program method
    public static void main(String[] args) throws IOException
    {
        //schedule job for event dispatch thread
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                //method call
                createAndShowGUI();
            }
        });
    }
}

