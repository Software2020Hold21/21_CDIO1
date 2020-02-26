import view.*;
import dal.*;
import data.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
        public static void main(String[] args) throws IUserDAO.DALException {
            IUserStore data = new UserStore();
            IUserDAO userDAO = new UserDAO(data);
            TUI tui = new TUI(userDAO);



            tui.mainMenu();




            /*
            UserDTO Peter = new UserDTO(123,"Peter","PR",new ArrayList<String>());
            List<UserDTO> liste = userDAO.getUserList();
            userDAO.createUser(Peter);
            liste = userDAO.getUserList();
             */




        }



    }
