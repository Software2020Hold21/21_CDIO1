package dal;
import data.*;

import java.util.List;

public class UserDAO implements IUserDAO{
    private IData data;

    public UserDAO(IData data){
        this.data = data;
    }


    public UserDTO getUser(int userId) throws DALException {
        return null;
    }

    public List<UserDTO> getUserList() throws DALException {
        return null;
    }

    public void createUser(UserDTO user) throws DALException {

    }

    public void updateUser(UserDTO user) throws DALException {

    }

    public void deleteUser(int userId) throws DALException {

    }
}
