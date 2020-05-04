package controller;

import dal.IUserDAO;
import data.UserDTO;

import java.util.List;

public class InputLogic {
    IUserDAO userDAO;
    public InputLogic(IUserDAO userDAO){
        this.userDAO = userDAO;
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

}
