import view.*;
import dal.*;
import data.*;

public class Main {
        public static void main(String[] args) {
            IData data = new Data();
            IUserDAO userDAO = new UserDAO(data);
            TUI tui = new TUI(userDAO);

            tui.mainMenu();






        }



    }
