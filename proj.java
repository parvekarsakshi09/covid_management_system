import java.io.*;
import java.util.*;

public class proj {

    static final String FILE_NAME = "PatientRecords.txt";
    static final String TEMP_FILE = "TempPatientRecords.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
      
        System.out.println("Welcome to the COVID Management System!");
        System.out.println("");
        System.out.println("");
          displayCovidInstructions();
        System.out.println("Are you an Admin or a User?");
        System.out.print("Enter 'Admin' or 'User': ");
        String role = sc.nextLine();

        if (role.equalsIgnoreCase("Admin")) {
            adminAccess();
        } else if (role.equalsIgnoreCase("User")) {
            userMenu();
        } else {
            System.out.println("Invalid role. Exiting the system.");
        }
    }

    // Admin Access
    public static void adminAccess() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Admin Name: ");
        String adminName = sc.nextLine();
   System.out.print("Enter Admin password: ");
        String pass = sc.nextLine();
        if(pass.equals("sakshi@123")){
        System.out.println("Welcome, " + adminName + "!");
        adminMenu();
        }
        else{
            System.out.println("You are not valid user"); 
        }
    }

    // Admin Menu
    public static void adminMenu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Add Patient Record");
            System.out.println("2. View All Records");
            System.out.println("3. Update Patient Record");
            System.out.println("4. Delete Patient Record");
            System.out.println("5. Generate Backup");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    viewAllRecords();
                    break;
                case 3:
                    updatePatient();
                    break;
                case 4:
                    deletePatient();
                    break;
                case 5:
                    generateBackup();
                    break;
                case 6:
                    System.out.println("Exiting Admin Menu.");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // User Menu (Basic functionality for viewing records)
    public static void userMenu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== User Menu ===");
            System.out.println("1. View All Records");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    viewAllRecords();
                    break;
                case 2:
                    System.out.println("Exiting User Menu.");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Add Patient Record (Admin Only)
    public static void addPatient() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Patient ID: ");
            String id = sc.nextLine();
            System.out.print("Enter Patient Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Age: ");
            int age = sc.nextInt();
            sc.nextLine(); // consume newline
            System.out.print("Enter Address: ");
            String address = sc.nextLine();
            System.out.print("Enter Symptoms: ");
            String symptoms = sc.nextLine();
            System.out.print("Enter Test Result (Positive/Negative): ");
            String result = sc.nextLine();
            System.out.print("Vaccination Status (Yes/No): ");
            String vaccination = sc.nextLine();

            String record = id + "," + name + "," + age + "," + address + "," + symptoms + "," + result + "," + vaccination;
            bw.write(record);
            bw.newLine();
            System.out.println("Patient record added successfully.");
        } catch (IOException e) {
            System.out.println("Error adding patient record: " + e.getMessage());
        }
    }

    // View All Records (Admin and User)
    public static void viewAllRecords() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("\n=== All Patient Records ===");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading patient records: " + e.getMessage());
        }
    }

    // Update Patient Record (Admin Only)
    public static void updatePatient() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Patient ID to update: ");
        String idToUpdate = sc.nextLine();

        boolean recordFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
             BufferedWriter bw = new BufferedWriter(new FileWriter(TEMP_FILE))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(idToUpdate)) {
                    recordFound = true;

                    System.out.println("Existing Record: " + line);
                    System.out.print("Enter updated name (or press Enter to keep current): ");
                    String updatedName = sc.nextLine();
                    if (!updatedName.isEmpty()) data[1] = updatedName;

                    System.out.print("Enter updated age (or press Enter to keep current): ");
                    String updatedAge = sc.nextLine();
                    if (!updatedAge.isEmpty()) data[2] = updatedAge;

                    System.out.print("Enter updated address (or press Enter to keep current): ");
                    String updatedAddress = sc.nextLine();
                    if (!updatedAddress.isEmpty()) data[3] = updatedAddress;

                    System.out.print("Enter updated symptoms (or press Enter to keep current): ");
                    String updatedSymptoms = sc.nextLine();
                    if (!updatedSymptoms.isEmpty()) data[4] = updatedSymptoms;

                    System.out.print("Enter updated test result (Positive/Negative or press Enter to keep current): ");
                    String updatedResult = sc.nextLine();
                    if (!updatedResult.isEmpty()) data[5] = updatedResult;

                    System.out.print("Enter updated vaccination status (Yes/No or press Enter to keep current): ");
                    String updatedVaccination = sc.nextLine();
                    if (!updatedVaccination.isEmpty()) data[6] = updatedVaccination;

                    line = String.join(",", data);
                }
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating patient record: " + e.getMessage());
        }

        if (recordFound) {
            new File(FILE_NAME).delete();
            new File(TEMP_FILE).renameTo(new File(FILE_NAME));
            System.out.println("Patient record updated successfully.");
        } else {
            System.out.println("Patient ID not found. No updates made.");
        }
    }

    // Delete Patient Record (Admin Only)
    public static void deletePatient() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Patient ID to delete: ");
        String idToDelete = sc.nextLine();

        boolean recordFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
             BufferedWriter bw = new BufferedWriter(new FileWriter(TEMP_FILE))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(idToDelete)) {
                    recordFound = true;
                    System.out.println("Deleting Record: " + line);
                    continue; // Skip writing this record to the temp file
                }
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error deleting patient record: " + e.getMessage());
        }

        if (recordFound) {
            new File(FILE_NAME).delete();
            new File(TEMP_FILE).renameTo(new File(FILE_NAME));
            System.out.println("Patient record deleted successfully.");
        } else {
            System.out.println("Patient ID not found. No records deleted.");
        }
    }

    // Backup Patient Data (Admin Only)
    public static void generateBackup() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
             BufferedWriter bw = new BufferedWriter(new FileWriter("PatientBackup.txt"))) {

            String line;
            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine();
            }
            System.out.println("Backup generated successfully.");
        } catch (IOException e) {
            System.out.println("Error generating backup: " + e.getMessage());
        }
    }

public static void displayCovidInstructions() {
        System.out.println("=== COVID Management Instructions ===");
        System.out.println("1. Wash your hands frequently with soap and water or use hand sanitizer.");
        System.out.println("2. Wear a mask properly, covering your nose and mouth.");
        System.out.println("3. Maintain social distancing of at least 6 feet.");
        System.out.println("4. Avoid crowded places and close-contact gatherings.");
        System.out.println("5. Get vaccinated to protect yourself and others.");
        System.out.println("6. If you experience symptoms such as fever, cough, or difficulty breathing,");
        System.out.println("   consult a healthcare provider immediately.");
        System.out.println("7. Use this system to manage patient records and generate statistics effectively.");
        System.out.println("8. Always keep a backup of patient records for safety.");
        System.out.println("\nLet's work together to fight the pandemic effectively!\n");
    }

}