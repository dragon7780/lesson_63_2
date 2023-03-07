package servise;

import dto.Card;
import repository.CardRepository;

public class AdminCardService {
    public boolean updateCard(String number, String exp_date) {
        CardRepository cardRepository=new CardRepository();
        Card adminCardByNumber = cardRepository.getAdminCardByNumber(number, exp_date);
        if(adminCardByNumber != null){
            return true;
        }
        return false;
    }

    public void changeCardStatus(String num) {
        CardRepository cardRepository=new CardRepository();
        Card card = cardRepository.getCardByNumber(num);
        if(card == null){
            return;
        }else {
         if(card.getStatus().equals("Active")){
             card.setStatus("Blocked");
         }else {
             card.setStatus("Active");
         }
        }
        cardRepository.changeCardByNumber(card);
    }

    public boolean deleteCard(String number) {
        CardRepository cardRepository=new CardRepository();
        Card cardByNumber = cardRepository.getCardByNumber(number);
        if(cardByNumber == null){
            System.err.println("this card doesn't exist");
            return false;
        }
        Boolean cardByNum = cardRepository.deleteCardByNum(number);
        if(cardByNum){
            System.err.println("This card succesfully deleted");
        }else {
            return false;
        }
        return true;
    }
}
