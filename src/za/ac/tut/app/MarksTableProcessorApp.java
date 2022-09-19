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
        // TODO code application logic here
        Scanner scanner = new Scanner(System.in);
        
        String[] tableHeaders = {"Student Number", "SW", "FA 1", "FA 2", "SA", "FM", "RESULT"};
        int numStudents;
        do {            
            System.out.print("How many students exist in the institution? Enter a number between 10 and 50: ");
            numStudents = scanner.nextInt();
            
            if (numStudents < 10 || numStudents > 50){
                System.out.println("The number " + numStudents + " is not within range. Try again.\n");
            }
        } while (numStudents < 10 || numStudents > 50);
        
        String[][] marksTable = new String[numStudents][tableHeaders.length];
        
        fillUniqueStudentNumbers(marksTable);
        
        fillAssessmentMarks(marksTable);
        
        displayResults(marksTable, tableHeaders, "Displaying after populating results");
    }
    
    public static int generateRandomNumber(final int MIN, final int MAX){
        Random randomGenerator = new Random();
        int randomNum = randomGenerator.nextInt(MIN, MAX + 1);
        
        return randomNum;
    }
    
    public static String generateStudentNumber(int studentNumberLength){
        String studentNumber = "2";
        
        for (int counter = 0; counter < studentNumberLength; counter++){
            int randomDigit;
            if (counter == 0 || counter == 1){
                randomDigit = generateRandomNumber(0, 2);
            }else {
                randomDigit = generateRandomNumber(0, 9);
            }
            studentNumber = studentNumber.concat(String.valueOf(randomDigit));
        }
        
        return studentNumber;
    }
    
    public static boolean evaluteIfUnique(String studentNum, String[][] marksTable){
        for (int rowCount = 0; rowCount < marksTable.length; rowCount++){
            String studentNumAtIndex = marksTable[rowCount][0];
            if (studentNum.equals(studentNumAtIndex)){
                return false;
            }
        }
        
        return true;
    }
    
    public static void fillUniqueStudentNumbers(String[][] marksTable){
        for (int rowCount = 0; rowCount < marksTable.length; rowCount++){
            String studentNumber;
            boolean isUnique;
            
            do {                
                studentNumber = generateStudentNumber(9);
                
                isUnique = evaluteIfUnique(studentNumber, marksTable);
                
            } while (!isUnique);
            
            marksTable[rowCount][0] = studentNumber;
        }
    }
    
    public static void fillAssessmentMarks(String[][] marksTable){
        for (int rowCount = 0; rowCount < marksTable.length; rowCount++){
            for (int colCount = 1; colCount < marksTable[rowCount].length - 2; colCount++){
                int randomMark = generateRandomNumber(0, 100);
                
                marksTable[rowCount][colCount] = String.valueOf(randomMark);
            }
        }
    }
    
    public static void displayResults(String[][] marksTable, String[] headers, String message){
        System.out.println(message);
        
        for (int rowCounter = -1; rowCounter < marksTable.length; rowCounter++){
            for (int colCounter = 0; colCounter < marksTable[rowCounter].length; colCounter++){
                String data;
                
                if (rowCounter == -1){
                    data = headers[colCounter];
                }else {
                    data = marksTable[rowCounter][colCounter];
                }
                
                if (colCounter == 0){
                        System.out.print(data + "\t\t");
                    }else {
                        System.out.print(data + "\t");
                    }
            }
            
            System.out.println("");
        }
    }
}
