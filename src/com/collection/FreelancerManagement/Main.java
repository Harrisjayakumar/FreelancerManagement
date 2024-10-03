package com.collection.FreelancerManagement;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FreelancerManagement freelancerManagement = new FreelancerManagement();
        boolean loggedIn = false;
        boolean isAdmin = false;

        while (true) {
            if (!loggedIn) {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                isAdmin = Login.isAdmin(username, password);

                if (isAdmin) {
                    System.out.println("Admin access granted.");
                } else {
                    System.out.println("User access granted.");
                }
                loggedIn = true;
            }

            while (loggedIn) {
                try {
                    System.out.println("\nFreelancer Management System");
                    if (isAdmin) {
                        System.out.println("1. Add Freelancer");
                        System.out.println("2. Update Freelancer");
                        System.out.println("3. Delete Freelancer");
                        System.out.println("4. Show Freelancers");
                        System.out.println("5. Search Freelancer");
                        System.out.println("6. Logout");
                        System.out.println("7. Exit");
                    } else {
                        System.out.println("1. Show Freelancers");
                        System.out.println("2. Search Freelancer");
                        System.out.println("3. Logout");
                        System.out.println("4. Exit");
                    }

                    System.out.print("Choose an option: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (choice) {
                        case 1:
                            if (isAdmin) {
                                freelancerManagement.addFreelancer();
                            } else {
                                freelancerManagement.displayFreelancers();
                            }
                            break;
                        case 2:
                            if (isAdmin) {
                                freelancerManagement.updateFreelancer();
                            } else {
                                freelancerManagement.searchFreelancer();
                            }
                            break;
                        case 3:
                            if (isAdmin) {
                                freelancerManagement.deleteFreelancer();
                            } else {
                                System.out.println("Logging out...");
                                loggedIn = false;
                            }
                            break;
                        case 4:
                            if (isAdmin) {
                                freelancerManagement.displayFreelancers();
                            } else {
                                System.out.println("Exiting...");
                                scanner.close();
                                return;
                            }
                            break;
                        case 5:
                            if (isAdmin) {
                                freelancerManagement.searchFreelancer();
                            } else {
                                System.out.println("Invalid option. Please try again.");
                            }
                            break;
                        case 6:
                            if (isAdmin) {
                                System.out.println("Logging out...");
                                loggedIn = false;
                            } else {
                                System.out.println("Invalid option. Please try again.");
                            }
                            break;
                        case 7:
                            System.out.println("Exiting...");
                            scanner.close();
                            return;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter the correct data type.");
                    scanner.nextLine(); // Clear the buffer
                } catch (FreelancerNotFoundException e) {
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    System.out.println("An unexpected error occurred: " + e.getMessage());
                }
            }
        }
    }
}
