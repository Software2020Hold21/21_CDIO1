package view;

import dal.IUserDAO;

public class TUI {
    private IUserDAO userDAO;

    public TUI(IUserDAO userDAO){
        this.userDAO = userDAO;
    }

    public void mainMenu(){
        System.out.println("BEGYND");
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



}
