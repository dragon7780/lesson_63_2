package servise;

import container.ComponentContainer;
import controller.CardController;
import controller.TerminalController;
import controller.TransactionController;
import dto.Profile;
import dto.Terminal;
import repository.ProfileRepository;

public class UserService {
    public void userMenu(){
        System.out.println("Registration is succesfully" );
        boolean isTrue=true;
        while (isTrue){
            menu();
            int action= ComponentContainer.intScanner.nextInt();
            switch (action){
                case 0-> isTrue=false;
                case 1-> addCard();
                case 2-> cardList();
                case 3-> changeStatus();
                case 4-> deleteCard();
                case 5-> refillCard();
                case 6-> transaction();
                case 7-> makePayment();
                default -> {
                    System.err.println("Don't be mazgi: ");
                    return;
                }
            }
        }
    }

    private void makePayment() {
        TerminalController terminalController=new TerminalController();
        terminalController.makePayment();
    }

    private void transaction() {
        TransactionController transactionController=new TransactionController();
        transactionController.createTransaction();
    }

    private void refillCard() {
        CardController cardController=new CardController();
        cardController.refillCard();
    }
    private void deleteCard() {
        CardController cardController=new CardController();
        cardController.deleteCard();
    }
    private void changeStatus() {
        CardController cardController=new CardController();
        cardController.changeCard();
    }
    private void cardList() {
        CardController cardController=new CardController();
        cardController.cardList();
    }
    public void addCard() {
        CardController cardController=new CardController();
        cardController.addCard();
    }
    private void menu() {
        String menu = """
                0 exit
                1 Add Card
                2 Card List
                3 Change Card status
                4 Delete Card
                5 Refill Card
                6 Transaction
                7 Make Payment
                """;
        System.out.println(menu);
    }

    public Boolean changeProfile(String phone) {
        ProfileRepository profileRepository =new ProfileRepository();
        Profile profile = profileRepository.getProfileByPhone(phone);
        if(profile == null){
            return false;
        }else {
            if(profile.getStatus().equals("Active")){
                profile.setStatus("Blocked");
                profileRepository.changeProfileByPhone(profile);
                return true;
            }else {
                profile.setStatus("Active");
                profileRepository.changeProfileByPhone(profile);
                return true;
            }
        }
    }
}
