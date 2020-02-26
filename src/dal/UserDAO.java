package dal;
import data.*;

import java.util.List;

public class UserDAO implements IUserDAO{
    private IUserStore users;

    public UserDAO(IUserStore users){
        this.users = users;
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
        List<UserDTO> tempraryUserList = users.getUserList();
        tempraryUserList.add(user);
        users.setUserList(tempraryUserList);
    }

    public void updateUser(UserDTO user) throws DALException {

    }

    public void deleteUser(int userId) throws DALException {

        for (int i=0; i<users.getUserList().size(); i++){

            if(userId == users.getUserList().get(i).getUserId())
                users.getUserList().remove(i);

        }

    }
}
