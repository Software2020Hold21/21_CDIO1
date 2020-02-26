package data;

import java.util.List;

public interface IUserStore {

    List<UserDTO> getUserList();

    public void setUserList(List<UserDTO> userList);


}
