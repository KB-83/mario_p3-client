package controller;

import view.Frame;
import view.menu.OfflineAskPanel;
import view.menu.StartPanel;

public class LocalController {
    private ClientController controller;
    private Frame frame;
    public LocalController(ClientController controller) {
        this.controller = controller;
        this.frame = new Frame(this);
    }
    public void enableOfflineMode(boolean offline) {
        frame.getPanelsManagerCard().setOffline(offline);
    }
    public void showAskPanel() {
        frame.getPanelsManagerCard().getCardLayout().show
                (frame.getPanelsManagerCard(), OfflineAskPanel.class.getSimpleName());
    }
    public void showLoginPanel(boolean isOnline) {
        enableOfflineMode(!isOnline);
        frame.getPanelsManagerCard().getCardLayout().show
                (frame.getPanelsManagerCard(), StartPanel.class.getSimpleName());


    }

    public ClientController getController() {
        return controller;
    }

    public void setController(ClientController controller) {
        this.controller = controller;
    }
}
