package data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserStore implements IUserStore, Serializable {

    private List<UserDTO> userList = new ArrayList<UserDTO>();

    public List<UserDTO> getUserList(){
        return this.userList;
    }

    public void setUserList(List<UserDTO> userList){
        this.userList = userList;
    }
}