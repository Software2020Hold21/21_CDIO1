package view;

import dal.IUserDAO;
import data.UserDTO;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TUI {
    private IUserDAO userDAO;

    public TUI(IUserDAO userDAO){
        this.userDAO = userDAO;
    }

    public void mainMenu() throws IUserDAO.DALException {

        boolean exit = false;
        int menuChoice =0;

        while (!exit) {
            //Initializes scanner
            Scanner input = new Scanner(System.in);

            //Prints main menu
            System.out.println("Main Menu\n" +
                    "1: Create new user.\n" +
                    "2. View existing users.\n" +
                    "3. Update user.\n" +
                    "4. Delete account.\n" +
                    "5. Exit program.");

            try{
                menuChoice = input.nextInt();
            }catch (InputMismatchException e){
                System.out.println("Input must be an integer.");
            }


            switch (menuChoice) {
                case 1:
                    // Create. call method for this menu option
                    System.out.println("Choice = " + menuChoice);
                    createUser(input);
                    break;
                case 2:
                    // view.
                    System.out.println("Choice = " + menuChoice);
                    printUsers();
                    break;
                case 3:
                    // edit
                    System.out.println("Choice = " + menuChoice);
                    editUser(input);

                    break;
                case 4:
                    // delete
                    System.out.println("Choice =" + menuChoice);

                    break;
                case 5:
                    exit = true;
                    System.out.println("Choice = " + menuChoice);
                    break;
            }
        }
    }


    public String generatePassword(){
        //Structure ABC123def
        String ABC="";
        String numbers="";
        String def="";
        int asciiChar;

        for (int i = 0; i < 3; i++) {
            asciiChar = (int) (Math.random()*26)+65;
            ABC += (char) asciiChar;
        }

        for (int i = 0; i < 3; i++) {
            asciiChar = (int) (Math.random()*10)+48;
            numbers += (char) asciiChar;
        }

        for (int i = 0; i < 3; i++) {
            asciiChar = (int) (Math.random()*26)+97;
            def += (char) asciiChar;
        }
        return ABC + numbers + def;
    }

    public String passwordCheck(String password, String name, int id){
        String idString = Integer.toString(id);

        //Checks length of password
        if (password.length()<6){
            return "Password too short.";
        } else if (password.length()>90){
            return "Password too long.";
        }

        //Checks if it contains name or id
        if (password.contains(name)){
            return "Password must not contain your name.";
        } else if (password.contains(idString)){
            return "Password  must not contain your ID";
        }


        //Checks if it contains 3 of the 4 categories
        boolean[] categoriesRepresented = new boolean[4]; //ABC, 123, def, !-_+!?=
        boolean isSpecial;
        for (int i = 0; i < password.length(); i++) {
            isSpecial = password.charAt(i) == 1 || password.charAt(i) == 2 || password.charAt(i) == 3
                    || password.charAt(i) == 4 || password.charAt(i) == 5 || password.charAt(i) == 6 || password.charAt(i) == 7;

            if (password.charAt(i)>=65 && password.charAt(i)<=90){
                categoriesRepresented[0]=true;
            } else if (password.charAt(i)>=48 && password.charAt(i)<57){
                categoriesRepresented[1]=true;
            } else if (password.charAt(i)>=97 && password.charAt(i)<=122){
                categoriesRepresented[2]=true;
            } else if (isSpecial){
                categoriesRepresented[3] = true;
            }
        }

        int sum =0;
        for (int i = 0; i < 4; i++) {
            if (categoriesRepresented[i]){
                sum++;
            }
        }

        if (sum<3){
            return "Password does not contain 3 different types of characters.";
        }

        return "Password OK";
    }

    public void createUser(Scanner input) throws IUserDAO.DALException {
        //User ID
        int id =0;
        boolean valid= false;
        while (!valid){
            try{
                id =0;
                System.out.println("Write ID of the new user");
                id = input.nextInt();
                valid = idValid(id);
                if (!valid){
                    System.out.println("ID must be a number between 11 and 99, and must not be in use already.");
                }
            } catch (InputMismatchException e){
                System.out.println("ID must be a number between 11 and 99, and must not be in use already.");
            }
        }

        //Username
        String name = "";
        while (name.length()<2 || name.length()>20){
            System.out.println("Write the name of the new user");
            name = input.next();
        }

        //Initials
        String initials = "";
        while (initials.length()<2 || initials.length()>4){
            System.out.println("Write the initials of the new user");
            initials = input.next();
        }

        List<String> roles = new ArrayList<String>();
        String[] validRoles = {"Admin","Pharmacist","Foreman","Operator"};
        String userInput;
        for (int i = 0; i < validRoles.length; i++) {
            System.out.println("Write \"OK\" to grant user " + validRoles[i] + " role.  Write something else, to deny.");
            userInput = input.next();
            if (userInput.toLowerCase().equals("ok") ){
                roles.add(validRoles[i]);
            }
        }

        userDAO.createUser(new UserDTO(id,name,initials,roles));
    }

    public void printUsers()throws IUserDAO.DALException{
        List<UserDTO> userList=userDAO.getUserList();
        for (int i = 0; i <userList.size(); i++) {
            System.out.println(userList.get(i).toString());
        }
    }
    public boolean idValid(int id) throws IUserDAO.DALException {
        //Checks that it  is in correct range
        if (id <11 || id>99){
            return false;
        }

        //Checks that id is not already used
        List<UserDTO> users = userDAO.getUserList();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId()==id){
                return false;
            }
        }
        return true;
    }


    public void editUser(Scanner input)throws IUserDAO.DALException{
        System.out.println("Chose the user ID of the user you want to edit");
        printUsers();
        int userID=input.nextInt();
        UserDTO user =userDAO.getUser(userID);
        System.out.println("Chose what to edit:");
        System.out.println("1: Edit user ID");
        System.out.println("2: Edit usernamer");
        System.out.println("3: Edit initials");
        System.out.println("4: Edit role");
        int choice=input.nextInt();

        switch (choice){
            case 1:
                System.out.println("Write new user ID:");
                int id=input.nextInt();
                user.setUserId(id);
                break;
            case 2:
                System.out.println("Write new username:");
                String username=input.nextLine();
                user.setUserName(username);
                break;
            case 3:
                System.out.println("Write new initials:");
                String initials = input.nextLine();
                user.setIni(initials);
                break;
            case 4:
                System.out.println("Ikke implementeret endnu");
                break;
        }
    }

}
