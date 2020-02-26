import view.*;
import dal.*;
import data.*;

public class Main {
        public static void main(String[] args) {
            IData data = new Data();
            IUserDAO userDAO = new UserDAO(data);
            TUI tui = new TUI(userDAO);

            tui.mainMenu();


            String password;

            for (int i = 0; i < 10; i++) {
                password = tui.generatePassword();
                System.out.println(tui.generatePassword());
                System.out.println(tui.passwordCheck(password),"BO",1234);
            }






        }



    }
