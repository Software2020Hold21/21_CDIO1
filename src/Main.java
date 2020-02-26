import view.*;
import dal.*;
import data.*;

public class Main {
        public static void main(String[] args) {
            IUserStore data = new UserStore();
            IUserDAO userDAO = new UserDAO(data);
            TUI tui = new TUI(userDAO);






            tui.mainMenu();






        }



    }
