import view.*;
import dal.*;
import data.*;

import java.io.*;

public class Main {
        public static void main(String[] args) throws IUserDAO.DALException, IOException, ClassNotFoundException {

            IUserDAO userDAO = new UserDAO();
            TUI tui = new TUI(userDAO);
            tui.mainMenu();




        }

    }
