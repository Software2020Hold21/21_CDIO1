package view;

import dal.IUserDAO;

public class TUI {
    private IUserDAO userDAO;

    public TUI(IUserDAO userDAO){
        this.userDAO = userDAO;
    }

    public void mainMenu(){
        System.out.println("BEGYND");
    }
}
