package main;

import authentication.Login;
import authentication.Register;
import executor.WriteQuery;

import java.util.Scanner;

public class Main {
    public static void main(String args[]) throws Exception {


//        MetadataGenerator metadataGenerator = new MetadataGenerator();
//        Server1 server1 = new Server1();
//        Server2 server2 = new Server2();
//        metadataGenerator.createMetaData();
//        server1.Server1Data();
//        server2.Server2Data();

        System.out.println("-----------------------------------------------------------------------");
        System.out.println("                          Database Management System                   ");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("1. Registeration");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        while(true){
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    Register r = new Register();
                    r.userRegisteration();
                    break;
                case 2:
                    Login l = new Login();
                    boolean result = l.userLogin();
                    if(result){
                        while (true){
                            System.out.println("--------------------MAIN MENU---------------------");
                            System.out.println("1. Write Queries");
                            System.out.println("2. Export");
                            System.out.println("3. Data Model");
                            System.out.println("4. Analytics");
                            Scanner input = new Scanner(System.in);
                            int choices = input.nextInt();
                            switch (choices) {
                                case 1:
                                    WriteQuery executeQuery = new WriteQuery();
                                    executeQuery.sqlQueries();
                                    break;

                                default:
                                    System.out.println("Invalid Choice!");
                            }

                        }
                    }
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid Choice!");
            }

        }

	}

}
