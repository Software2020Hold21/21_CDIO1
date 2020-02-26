package view;

import dal.IUserDAO;
import java.util.Scanner;

public class TUI {
    private IUserDAO userDAO;

    public TUI(IUserDAO userDAO){
        this.userDAO = userDAO;
    }

    public void mainMenu(){

        int exit =0;

        while (exit ==0) {

            System.out.println("Menu");

            //Display options
            System.out.println("1: Create new user.");
            System.out.println("2: View existing users.");
            System.out.println("3: Edit existing account");
            System.out.println("4: Delete account");
            System.out.println("5: Exit program");


            Scanner input = new Scanner(System.in);
            int choice = input.nextInt();

            switch (choice) {
                case 1:
                    // Create. call method for this menu option
                    System.out.println("Choice = " + choice);
                    break;
                case 2:
                    // view.
                    System.out.println("Choice = " + choice);

                    break;
                case 3:
                    // edit
                    System.out.println("Choice = " + choice);

                    break;
                case 4:
                    // delete
                    System.out.println("Choice =" + choice);

                    break;
                case 5:
                    exit = choice;
                    System.out.println("Choice = " + choice);

                    break;


            }
        }



    }
}
