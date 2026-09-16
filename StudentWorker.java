package com.mycompany.studentworker;

import java.util.Scanner;
import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;
/**
 * A object StudentWorker that contains the name, major, and grades of a student
 * @author jevongilliam
 */
public class StudentWorker {
    
    private String firstName;
    private String lastName;
    private String major;
    private float[] grades = new float[3];
    /**
     * A constructor for a StudentWorker Object
     * @param fn Represents first name of a student
     * @param ln Represents last name of a student
     * @param m Represents major of a student 
     * @param g Represents grades as an Array of floats
     */
    StudentWorker(String fn, String ln, String m, float[] g)
    {
        firstName = fn;
        lastName = ln;
        major = m;
        for(int i = 0; i < g.length; i++)
        {
            grades[i] = g[i];
        }
    }
    /**
     * Gets the first name of a student
     * @return String: The first name of a student
     */
    public String getFirstName() {return firstName;}
    /**
     * Gets the last name of a student 
     * @return String: The last name of a student
     */
    public String getLastName() {return lastName;}
    /**
     * Gets the major of a student
     * @return String: the major of a student
     */
    public String getMajor() {return major;}
    /**
     * Gets the grades of a student
     * @return Array of floats: grades of student
     */
    public float[] getGrades() {return grades;}
    /**
     * Uploads Student data from a file
     * @return An ArrayList of StudentWorker objects
     * @throws IOException if file not found
     */
    public static ArrayList<StudentWorker> uploadStudents() throws IOException
    {
        Scanner us = new Scanner(System.in);
        
        System.out.println("What is the name of the file you want to upload data from?");
        String inFileName = us.nextLine();
        
        File f = new File(inFileName);
        Scanner inFile = new Scanner(f);
        ArrayList<StudentWorker> myStudentWorkers = new ArrayList<>();
        while(inFile.hasNext())
        {
           String fn = inFile.next();
           String ln = inFile.next();
           String m = inFile.next();
           float g1 = inFile.nextFloat();
           float g2 = inFile.nextFloat();
           float g3 = inFile.nextFloat();
           
           float[] grades = new float[3];
           grades[0] = g1;
           grades[1] = g2;
           grades[2] = g3;

           StudentWorker sw = new StudentWorker(fn,ln,m,grades);
           
           myStudentWorkers.add(sw);
        }
        
        System.out.println("***Data uploaded successfully.");
        
        System.out.println("");
            
        return myStudentWorkers;
    }
    /**
     * Prints students data from an ArrayList of StudentWorker objects
     * @param z Represents an ArrayList of StudentWorker objects
     */
    public static void printStudentData(ArrayList<StudentWorker> z)
    {
        
        for(StudentWorker x : z)
        {
            float[] g = x.getGrades();

            System.out.println("Student: " + x.getFirstName() + " " + x.getLastName()
            + ", Major: " + x.getMajor() + ", Exam Grades: "
            + g[0] + ", " + g[1]+ ", " + g[2]);   
        }
        System.out.println("");
    }
    /**
     * Prints the name of a student with the requested major -
     * - from an ArrayList of StudentWorker objects 
     * @param s Represents an ArrayList of StudentWorker objects
     */
    public static void printMajor(ArrayList<StudentWorker> s)
    {
        Scanner pm = new Scanner(System.in);
        System.out.println("What major would you like to search for?");
        String findMajor = pm.nextLine();
        
        String notFound = "";
        String inMajor = "";
        
        for(StudentWorker x : s)
        {
            inMajor = x.getMajor();
            if(inMajor.equalsIgnoreCase(findMajor))
            {
                notFound = x.getMajor();
                System.out.println(x.getFirstName() +" "+ x.getLastName());
            }
        }
        if(notFound.equalsIgnoreCase(""))
        {
            System.out.println("No matching students found");
        }
        
    }
    /**
     * Makes a file with the stats of a StudentWorker object
     * In File: Average Grade, Sorted list of first names, highest and lowest grades
     * @param s Represents a ArrayList of StudentWorker Objects
     * @throws IOException if file not found
     */
    public static void generateStatsFile(ArrayList<StudentWorker> s) throws IOException
    {
        String outFile = "stats.txt";
        FileWriter fw = new FileWriter(outFile, false);
        PrintWriter pw = new PrintWriter(fw);
        
        pw.println("Student Grades:");
            
        for(StudentWorker x : s)
        {
            float[] g = x.getGrades();
            float totalGrade = 0f; 
            float avg = 0f;

            for(int i = 0; i < g.length; i++) 
            {
                totalGrade += g[i];
            }
            
            avg = totalGrade/3;
            
            if(avg < 60)
                pw.printf("Student Name: %s %s, Average: %,.1f, Letter Grade: F\n", x.firstName, x.lastName, avg);
            else if(avg < 70)
                pw.printf("Student Name: %s %s, Average: %,.1f, Letter Grade: D\n", x.firstName, x.lastName, avg);
            else if(avg < 80)
                pw.printf("Student Name: %s %s, Average: %,.1f, Letter Grade: C\n", x.firstName, x.lastName, avg);
            else if(avg < 90)
                pw.printf("Student Name: %s %s, Average: %,.1f, Letter Grade: B\n", x.firstName, x.lastName, avg);
            else if(avg <= 100)
                pw.printf("Student Name: %s %s, Average: %,.1f, Letter Grade: A\n", x.firstName, x.lastName, avg);
        }
        
        pw.println("");
        
        String[] names = new String[s.size()];
        
        for(int i = 0; i < s.size(); i++)
        {
            StudentWorker x = s.get(i);
            names[i] = x.getFirstName();
        }    
        
        Arrays.sort(names);
        
        pw.println("Student names sorted alphabetically by first name:");
        
        pw.print("[");
        for(int i = 0; i < names.length; i++)
        {
            pw.print(names[i]);
            
            if(i != names.length - 1)
                pw.print(", "); 
        }
        pw.print("]\n");
        
        pw.println("");
        
        String highName = "";
        String lowName = "";
        
        float high = s.get(0).getGrades()[0];
        float low = s.get(0).getGrades()[0];
        
        for(int i = 0; i < s.size(); i++)
            {
                StudentWorker x = s.get(i);
                
                float[] g = x.getGrades();
                
                for(int n = 0; n < 3; n++)
                {
                    if(g[n] > high)
                    {
                        high = g[n];
                        highName = x.getFirstName() + " " + x.getLastName();
                    }

                    if(g[n] < low)
                    {
                        low = g[n];
                        lowName = x.getFirstName() + " " + x.getLastName();
                    }
                }  
            }
        
        pw.printf("Student %s received the highest grade %,.1f\n",highName, high);
        pw.printf("Student %s received the lowest grade %,.1f\n",lowName, low);
        
        pw.close();
        
        System.out.println("Created statistics file named stats.txt.\n");
    }
    /**
     * Prints the contents of file "stats.txt"
     * @throws IOException if file not found
     */
    public static void printStatsFile() throws IOException
    {
        File f = new File("stats.txt");
        Scanner inFile = new Scanner(f);
        
        while(inFile.hasNext())
        {
            System.out.println(inFile.nextLine());
        }
        System.out.println("");
    }
    /**
     * 
     * @param args
     * @throws IOException if file not found
     */
    public static void main(String[] args) throws IOException {
        
        Scanner kb = new Scanner(System.in);
        
        ArrayList<StudentWorker> myStudentWorkers = new ArrayList<>();
        
        while(true)
        {
            System.out.println("Welcome to the Student Analyzer program." +
            " Please choose from the following options:\n" + "1. Upload Data\n" +
            "2. View Data\n" + "3. Find Major\n" + "4. Create Statistics File\n" +
            "5. Print Statistics File\n" + "6. Exit the program\n");                   
                           
            int opt = kb.nextInt();
            
            kb.nextLine();
            
            if(opt == 1)
            {
                myStudentWorkers = uploadStudents();
            }
            else if(opt == 2)
            {
               printStudentData(myStudentWorkers);
            }
            else if(opt == 3)
            {
                printMajor(myStudentWorkers);
            }
            else if(opt == 4)
            {
                generateStatsFile(myStudentWorkers);
            }
            else if(opt == 5)
            {
                printStatsFile();
            }
            else if(opt == 6)
            {
                break;
            }
        }
    }
}
