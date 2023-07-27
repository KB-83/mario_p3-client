package controller.connection;

import controller.LocalController;
import model.response.*;
import util.Saver;
import model.dto.ClientDTO;
import util.Loop;
import view.menu.GamePanel;
import view.menu.MainMenu;
import view.menu.PanelsManagerCard;

public class ResponseHandler implements ResponseVisitor{
    private static ResponseHandler responseHandler;
    private PanelsManagerCard panelsManagerCard;
    private LocalController localController;
    private ResponseHandler(LocalController localController) {
        this.panelsManagerCard = localController.getFrame().getPanelsManagerCard();
        this.localController = localController;
    }
    public static ResponseHandler getInstance(LocalController localController) {
        if (responseHandler == null) {
            responseHandler = new ResponseHandler(localController);
        }
        return responseHandler;
    }

    @Override
    public void visit(SignInLoginResponse response) {
        if (response.isOk()){
            localController.getController().setClient(response.getClient());
            response.getClient().setClientController(localController.getController());
            localController.clientCleared(response.getClient());
            panelsManagerCard.getCardLayout().show(panelsManagerCard, MainMenu.class.getSimpleName());
            panelsManagerCard.getStartPanel().requestFocus();
            //save mikonim
            ClientDTO clientDTO = new ClientDTO(response.getClient().getUsername(),response.getClient().getPassword(),response.getClient().getPrivateChats());
            Saver.getSaver().saveUser(clientDTO);
        }
        else {
            System.out.println(response.getMassage());
        }
    }

    @Override
    public void visit(GameStartResponse response) {

//        user.setCurrentGameState(gameState);
//        panelsManagerCard.getGamePanel().setKeyListener(gameState);
        //test
        Loop loop = new Loop(localController,response.getGameStateDTO(),response.getPlayerDTO(),panelsManagerCard.getGamePanel(),60);
        loop.start();
        panelsManagerCard.getGamePanel().setGameStateDTO(response.getGameStateDTO(),response.getPlayerDTO());
        panelsManagerCard.getGamePanel().setKeyListener(localController);
        panelsManagerCard.getCardLayout().show(panelsManagerCard, GamePanel.class.getSimpleName());
        panelsManagerCard.getGamePanel().requestFocus();
    }

    @Override
    public void visit(GameStateStatusResponse response) {
        //bayad gamestate game panel ro in konim.
        panelsManagerCard.getGamePanel().setGameStateDTO(response.getGameStateDTO(),response.getPlayerDTO());
    }

    @Override
    public void visit(NewPMResponse response) {
        localController.getController().getClient().setPrivateChats(response.getNewPrivateChat());

//        if (ChatPanel baz bood)
        if (response.getSender() != "") {
            localController.getFrame().getNotification().showNotification("NEW PM",response.getMassage().getContext());
            panelsManagerCard.getPrivateChatPanel().setChat(localController.getController().getPrivateChatByOpponentName(response.getSender()).getMassages(), response.getSender());
        }
    }

    @Override
    public void visit(BuyResponse response) {
        if (response.getBill() != null) {
            System.out.println(response.getBill().getCoinCost());
            System.out.println(response.getBill().getDiamondCost());
            System.out.println(response.getBill().getBuyRequest().getHammer());
        }
        else {
            System.out.println(response.getMessage());
        }
        System.out.println("buy response returned");
    }
}
