package dal;
import data.*;

import java.io.*;
import java.util.List;

public class UserDAO implements IUserDAO{
    private IUserStore users;
    private String dbLocation = ".\\DataBase.ser";

    public UserDAO(){
        readFromDatabase();
    }


    public UserDTO getUser(int userId) throws DALException{
        UserDTO user=null;
        for (int i = 0; i < users.getUserList().size(); i++) {
            if(users.getUserList().get(i).getUserId()==userId){
                user=users.getUserList().get(i);
            }
        }
        return user;
    }

    public List<UserDTO> getUserList() throws DALException {
        return users.getUserList();
    }

    public void createUser(UserDTO user) throws DALException {
        try{
            //Creates the user
            List<UserDTO> tempraryUserList = users.getUserList();
            tempraryUserList.add(user);
            users.setUserList(tempraryUserList);
            //Tries to write to database
            writeToDatabase();
        } catch (Exception e){
            System.out.println("Couldn't write to database. Creation of user canceled.");
            e.printStackTrace();
        }
    }

    public void updateUser(UserDTO newUser) throws DALException {
        int id = newUser.getUserId();

        for (int i=0; i<users.getUserList().size(); i++){ //Searches for the correct user

            if(id == users.getUserList().get(i).getUserId()){
                try {
                    users.getUserList().remove(i);
                    users.getUserList().add(newUser);
                    writeToDatabase();
                } catch (Exception e){
                    System.out.println("Couldn't write to database. Update canceled.");
                    e.printStackTrace();
                }
            }
        }

    }

    public void deleteUser(int userId) throws DALException {

        for (int i=0; i<users.getUserList().size(); i++){

            if(userId == users.getUserList().get(i).getUserId()){
                try {
                    users.getUserList().remove(i);
                    writeToDatabase();
                } catch (Exception e){
                    System.out.println("Couldn't write to database. Deletion canceled.");
                    e.printStackTrace();
                }
            }
        }

    }

    public void readFromDatabase(){
        File file = new File(".\\DataBase.ser");
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        if (file.exists()){ //If there is a file
            try {
                fis = new FileInputStream(dbLocation);
                ois = new ObjectInputStream(fis);
                users = (IUserStore) ois.readObject();
            } catch (Exception e){
                System.out.println("File exists, but could not be read.");
                e.printStackTrace();
            }

        } else{ //If there is no file
            //Create new UserStore
            users = new UserStore();
            try {
                writeToDatabase();
            } catch (Exception e){
                System.out.println("Couldn't write to database at startup.");
                e.printStackTrace();
            }
        }

    }

    public void writeToDatabase() throws IOException, ClassNotFoundException {
        FileOutputStream fis = new FileOutputStream(dbLocation);
        ObjectOutputStream ois = new ObjectOutputStream(fis);

        try{
            ois.writeObject(users);
        } catch (Exception e){
            System.out.println("Could not write to database.");
            e.printStackTrace();
        }
    }


}
