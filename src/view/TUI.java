package view;

import dal.IUserDAO;
import data.UserDTO;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
//import data.UserDTO;

public class TUI {
    private IUserDAO userDAO;


    public TUI(IUserDAO userDAO){
        this.userDAO = userDAO;
    }

    public void mainMenu() throws IUserDAO.DALException {

        boolean exit = false;
        String menuChoiceString;
        int menuChoice =0;

        //Initializes scanner
        Scanner input = new Scanner(System.in);

        while (!exit) {
            //resets menuchoice
            menuChoice =0;

            //Prints main menu
            System.out.println("Main Menu\n" +
                    "1: Create new user.\n" +
                    "2. View existing users.\n" +
                    "3. Update user.\n" +
                    "4. Delete account.\n" +
                    "5. Exit program.");

            try{
                menuChoiceString = input.nextLine();
                menuChoice = Integer.parseInt(menuChoiceString);
            }catch (Exception e){
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
                    int id=0;


                    while (true){
                        System.out.println("Pick the userID that you want to delete: ");
                        String userIdString = input.nextLine();
                        try{
                            id = Integer.parseInt(userIdString);

                            System.out.println("Are you sure, you want to delete user "+ id +
                                    "\nIf you are sure, type \"OK\".");
                            String inputOK = input.nextLine();
                            if (inputOK.toLowerCase().equals("ok")){
                                userDAO.deleteUser(id);
                                System.out.println("User "+ id+ " has now been deleted");
                            } else{
                                System.out.println("Deletion of user canceled.");
                            }
                            break;
                        } catch (IUserDAO.DALException e){
                            System.out.println(e.getMessage());
                        } catch (Exception e){
                            System.out.println("Input must be a number corresponding to a userId.");
                        }
                    }


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

    public void createUser(Scanner input){
        //User ID
        int id = 0;
        String idString;
        boolean idValid= false;
        while (!idValid){
            try{
                System.out.println("Write ID of the new user");
                idString = input.nextLine();
                id = Integer.parseInt(idString);
                idValid = idValid(id);
                if (!idValid){
                    System.out.println("ID must be a number between 11 and 99, and must not be in use already.");
                }
            } catch (Exception e) {
                System.out.println("ID must be a number between 11 and 99, and must not be in use already.");
            }
        }

        //Username
        String name = "";
        boolean userNameValid = false;
        while (!userNameValid){
            System.out.println("Write the name of the new user");
            name = input.nextLine();
            if (name.length()>=2 && name.length()<=20){
                userNameValid = true;
            } else{
                System.out.println("Username must be between 2 and 20 characters.");
            }
        }

        //Initials
        String initials = "";
        boolean initialsValid = false;
        while (!initialsValid){
            System.out.println("Write the initials of the user.");
            initials = input.nextLine();
            if (initials.length()>=2 && initials.length()<=4){
                initialsValid = true;
            } else{
                System.out.println("Initials must be between 2 and 4 characters.");
            }
        }

        //Roles
        List<String> roles = new ArrayList<String>();
        String[] validRoles = {"Admin","Pharmacist","Foreman","Operator"};
        String userInput;
        for (int i = 0; i < validRoles.length; i++) {
            System.out.println("Write \"OK\" to grant user " + validRoles[i] + " role.  Write something else, to deny.");
            userInput = input.nextLine();
            if (userInput.toLowerCase().equals("ok") ){
                roles.add(validRoles[i]);
            }
        }

        //Password
        String password;
        do {
            password = generatePassword();
        } while (!passwordCheck(password,name,id).equals("Password OK"));

        //CPR
        String cprString = "";
        boolean cprValid= false;
        while (!cprValid){
            try{
                System.out.println("Write CPR of the new user");
                cprString = input.nextLine();
                //Checks if it only contains numbers, by trying to parse to long
                Long.parseLong(cprString);

                //Maybe, CPR needs some more requirements to be valid
                cprValid = cprString.length() == 10;
                if (!cprValid){
                    System.out.println("CPR must be a 10 digit number.");
                }
            } catch (InputMismatchException e){
                System.out.println("CPR must be a 10 digit number.");
            } catch (NumberFormatException e){
                System.out.println("CPR must be a 10 digit number.");
            } catch (Exception e){
                System.out.println("CPR must be a 10 digit number.");
                e.printStackTrace();
            }

        }

        try{
            userDAO.createUser(new UserDTO(id,name,initials,roles, password, cprString));
        } catch (IUserDAO.DALException e){
            System.out.println("User not created. Problem with data access.");
            e.printStackTrace();
        }

    }

    public void printUsers(){

        try{
            List<UserDTO> userList=userDAO.getUserList();
            for (int i = 0; i <userList.size(); i++) {
                System.out.println(userList.get(i).toString());
            }
        } catch (IUserDAO.DALException e){
            e.printStackTrace();
        }


    }
    public boolean idValid(int id){
        //Checks that it  is in correct range
        if (id <11 || id>99){
            return false;
        }

        //Checks that id is not already used
        try{
            List<UserDTO> userList=userDAO.getUserList();
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getUserId()==id){
                    return false;
                }
            }
        } catch (IUserDAO.DALException e){
            e.printStackTrace();
        }

        return true;
    }

    public void editUser(Scanner input){
        int userID=0;
        int choice =0;
        //Dette er linjen der tilføjet
        UserDTO user;

        printUsers();
        System.out.println("Choose the user ID of the user you want to edit");

        while (true) {
            try {
                userID=Integer.parseInt(input.nextLine());
                user =userDAO.getUser(userID);
                break;
            } catch (IUserDAO.DALException e){
                System.out.println(e.getMessage());
            }
            catch (Exception e){
                System.out.println("Input must a number corresponding to an existing user ID.");
            }
        }


        System.out.println("Choose what to edit:");
        System.out.println("1: Edit user ID");
        System.out.println("2: Edit usernamer");
        System.out.println("3: Edit initials");
        System.out.println("4: Edit role");
        System.out.println("5: Exit");

        while (true){
            try {
                choice=Integer.parseInt(input.nextLine());
                break;
            } catch (Exception e){
                System.out.println("Input must be an existing user ID.");
            }
        }

        try{
            switch (choice){
                case 1:

                    boolean idValid=false;
                    while (!idValid){
                        try{
                            System.out.println("Write new user ID:");
                            String idString = input.nextLine();
                            int id = Integer.parseInt(idString);
                            idValid = idValid(id);
                            if (!idValid){
                                System.out.println("ID must be a number between 11 and 99, must not be in use already and must be a new user id");
                            }else{
                                user.setUserId(id);
                                System.out.println(user.getUserName()+" has now a new user id: "+id);
                            }
                        } catch (Exception e) {
                            System.out.println("ID must be a number between 11 and 99, and must not be in use already.");
                        }
                    }


                    break;
                case 2:
                    System.out.println("Write new username:");
                    String username=input.next();
                    user.setUserName(username);
                    System.out.println("User id: "+userID+" has now a new username: "+username);
                    break;
                case 3:
                    System.out.println("Write new initials:");
                    String initials = input.next();
                    user.setIni(initials);
                    System.out.println("UserID: "+userID+" has gotten his initials changed too: "+initials);
                    break;
                case 4:
                    //Starts with saving all the old rolls
                    List<String> roles = user.getRoles();
                    System.out.println("Select which role to edit:");
                    String[] validRoles = {"Admin","Pharmacist","Foreman","Operator"};
                    //A boolean that is used to to determine if the user already has the role the admin wishes to edit
                    boolean userHasRole=false;
                    //Prints all the the roles that the user has
                    for (int i = 0; i < validRoles.length ; i++) {
                        System.out.println(i+1+": " +validRoles[i]);
                    }
                    //Saves which role the admin wish to edit
                    int roleNumber = input.nextInt()-1;
                    //Loops through all the old roles roles to see if the user already has the role assigned to them
                    for (int i = 0; i <roles.size() ; i++) {
                        if(user.getRoles().get(i).equals(validRoles[roleNumber])){
                           userHasRole = true;
                        }
                    }
                    //If the user has the role the admin get the choice to remove it.
                    if(userHasRole){
                        System.out.println("Do you want to remove the role "+validRoles[roleNumber]+" from the user?");
                        System.out.println("1: Yes");
                        System.out.println("2: No");
                        int deleteRole = input.nextInt();
                        if(deleteRole==1){
                            for (int i = 0; i <roles.size(); i++) {
                                if(roles.get(i).equals(validRoles[roleNumber])){
                                    roles.remove(i);
                                    System.out.println(validRoles[roleNumber] + " has been removed from "+user.getUserName());
                                }
                            }

                        }
                    }else{
                        //If the user don't have the role it will be possible to add it.
                        System.out.println("Do you want to assign the role "+validRoles[roleNumber]+" to the user?");
                        System.out.println("1: Yes");
                        System.out.println("2: No");
                        int assignRole =input.nextInt();
                        if(assignRole==1){
                            roles.add(validRoles[roleNumber]);
                            System.out.println(validRoles[roleNumber] + " has been added from "+user.getUserName());
                        }
                }
                //Updates the roles with the new ones
                user.setRoles(roles);
                break;
            }
            userDAO.updateUser(user);
        }catch (IUserDAO.DALException e){
            System.out.println("Problem with writing to database. Update canceled.");
        }
        catch (Exception e){
            System.out.println("Couldn't complete update. Update canceled.");
        }
    }

}
