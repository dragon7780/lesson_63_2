package servise;

import container.ComponentContainer;
import dto.Card;
import repository.CardRepository;

import java.time.LocalDate;
import java.util.List;

public class CardService {
    public boolean cardRegister(Card card) {
        card.setPhone(ComponentContainer.currentUser.getPhone());
        card.setBalance(20000);
        card.setStatus("Active");
        card.setCreated_date(LocalDate.now());
        CardRepository cardRepository=new CardRepository();
        Card number = cardRepository.getCardByNumber(card.getNumber());
        if(number != null){
            System.err.println("Change your card number");
            return false;
        }
        cardRepository.save(card);
        return true;
    }
    public void cardList(String phone){
        CardRepository cardRepository =new CardRepository();
        List<Card> cardList = cardRepository.getCardList(phone);
        for (Card card : cardList) {
            System.out.println(card);
        }
    }
    public void changeCard(String number) {
        boolean isTrue = check(number);
        if(!isTrue){
            return;
        }
        CardRepository cardRepository=new CardRepository();
        Card card = cardRepository.getCardByNumber(number);
        if(card.getStatus().equals("Active")){
            card.setStatus("Blocked");
        }else {
            card.setStatus("Active");
        }
        Boolean cardByNumber = cardRepository.changeCardByNumber(card);
        if(!cardByNumber){
            System.err.println("Check card number !!!");
            return;
        }
        UserService userService=new UserService();
        userService.userMenu();
    }
    private boolean check(String number) {
        CardRepository cardRepository=new CardRepository();
        Card card = cardRepository.getCardByNumber(number);
        if(card == null){
            return false;
        }
        return true;
    }
    public void deleteCard(String number) {
        CardRepository cardRepository=new CardRepository();
        Boolean byNum = cardRepository.deleteCardByNum(number);
        if (!byNum){
            System.err.println("Check your card number");
            return;
        }else {
            System.out.println("Card number succesfully deleted");
            UserService service = new UserService();
            service.userMenu();
        }
    }
    public void refillCard(String cardNum, int amount) {
        boolean isCheck=checking(cardNum);
        if (!isCheck){
            return;
        }
        CardRepository cardRepository=new CardRepository();
        cardRepository.refillCard(cardNum,amount);
    }
    private boolean checking(String cardNum) {
        boolean check = check(cardNum);
        if (!check){
            System.err.println("Check your card number ");
            return false;
        }
        CardRepository cardRepository=new CardRepository();
        Card card = cardRepository.getCardByNumber(cardNum);
        Card number = cardRepository.getCardByNumber(cardNum, card.getPhone());
        if(number==null){
            return false;
        }
        return true;
    }
}