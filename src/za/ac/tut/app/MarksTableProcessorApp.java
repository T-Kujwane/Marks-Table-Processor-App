/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package za.ac.tut.app;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Thato Keith Kujwane
 */
public class MarksTableProcessorApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here: Build an application that processes a table of student marks
        //Create Scanner instance
        Scanner scanner = new Scanner(System.in);
        
        //Create table headers (columns) array
        String[] tableHeaders = {"Student Number", "SW", "FA 1", "FA 2", "SA", "FM", "RESULT"};
        
        //Declare a varible that will hold the number of students that exist in an organization
        int numStudents;
        
        do {
            //Prompt user for number of students
            System.out.print("How many students exist in the institution? Enter a number between 10 and 50: ");
            numStudents = scanner.nextInt();
            
            //Evaluate number of students
            if (numStudents < 10 || numStudents > 50) {
                //Display error message
                System.out.println("The number " + numStudents + " is not within range. Try again.\n");
            }
        } while (numStudents < 10 || numStudents > 50);
        
        //Initialize 2D array table with the number of rows being the number of students since each row represents the
        //marks for a student, the column size is equal to our headers
        String[][] marksTable = new String[numStudents][tableHeaders.length];
        
        //Fill student unique numbers
        fillUniqueStudentNumbers(marksTable);
        
        //Fill assessment marks
        fillAssessmentMarks(marksTable);
        
        //Determine final marks for each student
        determineFinalMarks(marksTable);
        
        //Determine results
        determineResults(marksTable);
        
        //Sort in descending order of final marks
        sortByFinalMarks(marksTable);
        
        //Display 2D array values by invoking the method responsible
        displayResults(marksTable, tableHeaders, "**************Printing Student Marks Table******************");
    }
    
    //This method generates a random number between MIN and MAX (both values inclusive)
    public static int generateRandomNumber(final int MIN, final int MAX) {
        //Instantiate random Object
        Random randomGenerator = new Random();
        //Generate random number by calling the nextInt() method of Random
        int randomNum = randomGenerator.nextInt(MIN, MAX + 1);
        
        //Return random number
        return randomNum;
    }
    
    //This method generates a student number of a specific length
    public static String generateStudentNumber(int studentNumberLength) {
        //Since student numbers begin with 2, we have our first digit as 2 in our student number
        String studentNumber = "2";
        
        //Define loop to generate the remaining digits of the student number,
        //we subtract 1 because we already have the first digit as 2
        for (int counter = 0; counter < studentNumberLength - 1; counter++) {
            int randomDigit;
            
            //Since the maximum number of the first three digits should be 222, we want to restrict the range of generating 
            //random numbers
            //If we are generating the second or third digit of the student number, the digits generated should be between 0 and 2
            if (counter == 0 || counter == 1) {
                randomDigit = generateRandomNumber(0, 2);
            } else {
                //Otherwise generate any single digit
                randomDigit = generateRandomNumber(0, 9);
            }
            //Concatenate (add) generated digit to student number
            studentNumber = studentNumber.concat(String.valueOf(randomDigit));
        }
        
        //Return student number
        return studentNumber;
    }

    //This method checks if a given student number does not exist in a given table
    public static boolean evaluteIfUnique(String studentNum, String[][] marksTable) {
        //When dealing with 2D arrays, it important that we know the structure of the table.
        //Our table is structured as follows: Stud_Num SW FA1 FA2 SA FM Result
        //Since (in this method) we are only interested in student numbers, wit means we will be focussing on the first column only
        for (int rowCount = 0; rowCount < marksTable.length; rowCount++) {
            //For each row, get the student number from the first column
            String studentNumAtIndex = marksTable[rowCount][0];
            //If we find a matching student number it means that our student number is no longer unique
            if (studentNum.equals(studentNumAtIndex)) {
                //Return false
                return false;
            }
        }
        //Otherwise return true
        return true;
    }

    //This method fill unique student numbers in our array
    public static void fillUniqueStudentNumbers(String[][] marksTable) {
        //Loop through all rows
        for (int rowCount = 0; rowCount < marksTable.length; rowCount++) {
            String studentNumber;
            boolean isUnique;
            
            //For each row
            do {
                //Generate 9 digit student number
                studentNumber = generateStudentNumber(9);
                
                //Check if it's unique
                isUnique = evaluteIfUnique(studentNumber, marksTable);
                
                //Repeat the process while the student number is not unique
            } while (!isUnique);
            
            //Once it's unique, add it to the first column of the current row
            marksTable[rowCount][0] = studentNumber;
        }
    }
    
    //This method simply fills the assessment marks for all the students
    public static void fillAssessmentMarks(String[][] marksTable) {
        //Loop through all rows since we're doing this for all students
        for (int rowCount = 0; rowCount < marksTable.length; rowCount++) {
            //Loop through columns 1 to 5, because those are the only columns that contain assessment marks
            for (int colCount = 1; colCount < marksTable[rowCount].length - 2; colCount++) {
                //For each column generate random mark
                int randomMark = generateRandomNumber(0, 100);
                
                //Store the mark
                marksTable[rowCount][colCount] = String.valueOf(randomMark);
            }
        }
    }
    
    //This method simply determines the final for the students
    public static void determineFinalMarks(String[][] marksTable) {
        //Loop through all rows
        for (int rowCounter = 0; rowCounter < marksTable.length; rowCounter++) {
            //Determine the last column that has marks. Again, knowing the structure of the table is important
            int lastMarkIndex = marksTable[rowCounter].length - 3;
            int finalMark = 0;
            
            //Loop through columns 1 to 5
            for (int colCounter = 1; colCounter <= lastMarkIndex; colCounter++) {
                //For each column, get assessment mark
                String assessmentMarkStr = marksTable[rowCounter][colCounter];
                
                //Convert mark to integer
                int assmentMark = Integer.parseInt(assessmentMarkStr);
                double weightedMark;
                
                //We know that the last column containing assessment marks is the summative assessment column
                //Therefore, if the current mark is not the SA mark, the weight is 20
                //We determine the weighted mark
                if (colCounter != lastMarkIndex) {
                    weightedMark = (0.2 * assmentMark);
                } else {
                    weightedMark = (0.4 * assmentMark);
                }
                
                //Add weighted mark to final mark
                finalMark += Math.round(weightedMark);
            }
            
            //Add final mark to table, next to the SA mark
            marksTable[rowCounter][lastMarkIndex + 1] = String.valueOf(finalMark);
        }
    }
    
    //This method simpl determines the results of the students
    public static void determineResults(String[][] marksTable) {
        //Loop through all rows since we're doing this for all students
        for (int rowCounter = 0; rowCounter < marksTable.length; rowCounter++) {
            //We know that the last index is the Result column and it's found at row length - 1
            int lastIndex = marksTable[rowCounter].length - 1;
            
            //Therefore, the final mark is just before the results column i.e at last index - 1
            //Get final mark
            final String FINAL_MARK_STR = marksTable[rowCounter][lastIndex - 1];
            //Type cast mark to Integer
            final int FINAL_MARK = Integer.parseInt(FINAL_MARK_STR);
            
            //Get SA mark
            final String SA_MARK_STR = marksTable[rowCounter][lastIndex - 2];
            //Type cast mark to Integer
            final int SA = Integer.parseInt(SA_MARK_STR);
            
            //Declare variable that will hold the result
            String result;
            
            //If the SA mark is less than 40, the result is FAIL SUBMINIMUM
            if (SA < 40) {
                result = "FAIL SUBMINIMUM";
            } else {
                //Otherwise, if the final mark is less than 50, the result is a FAIL
                if (FINAL_MARK < 50) {
                    result = "FAIL";
                } else if (FINAL_MARK < 75) {
                    //Else, if the final mark is less than 75, the result is PASS
                    result = "PASS";
                } else {
                    //Else, the result is a PASS DISTINCTION
                    result = "PASS DISTINCTION";
                }
            }
            //After determining the result, we simply store it at it's column in the table
            marksTable[rowCounter][lastIndex] = result;
        }
    }

    //This method simply sorts the table in descending order of final marks
    public static void sortByFinalMarks(String[][] marksTable) {
        for (int i = 0; i < marksTable.length; i++) {
            for (int j = 0; j < marksTable.length - 1; j++) {
                String[] lowerIndexArray = marksTable[j];
                String[] upperIndexArray = marksTable[j + 1];

                int finalMarkIndex = marksTable[j].length - 2;

                if (Integer.parseInt(lowerIndexArray[finalMarkIndex]) < Integer.parseInt(upperIndexArray[finalMarkIndex])) {
                    marksTable[j] = upperIndexArray;
                    marksTable[j + 1] = lowerIndexArray;
                }
            }
        }
    }

    //This method simply displays the results found in the 2D array table, with table headers and a customized message
    public static void displayResults(String[][] marksTable, String[] tableHeaders, String message) {
        System.out.println(message);
        
        for (int rowCounter = -1; rowCounter < marksTable.length; rowCounter++) {
            for (int colCounter = 0; colCounter < marksTable[0].length; colCounter++) {
                String data;
                
                //We want to print the column headers, hence we started the counter at row -1
                //During the iteration of row - 1, we print the table headers
                if (rowCounter == -1) {
                    data = tableHeaders[colCounter];
                } else {
                    data = marksTable[rowCounter][colCounter];
                }

                if (colCounter == 0) {
                    System.out.print(data + "\t\t");
                } else {
                    System.out.print(data + "\t");
                }
            }

            System.out.println("");
        }
    }
}
