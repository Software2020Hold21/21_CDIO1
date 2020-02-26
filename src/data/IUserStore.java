package data;

import java.util.List;

public interface IUserStore {

    List<UserDTO> getUserList();

    void setUserList(List<UserDTO> userList);


}
