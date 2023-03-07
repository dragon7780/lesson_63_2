package servise;

import dto.Card;
import dto.Transaction;
import enums.TransactionType;
import repository.CardRepository;
import repository.TransactionRepository;

import java.time.LocalDate;

public class TransactionService {
    public boolean getTransaction(Transaction transaction) {
        CardRepository cardRepository=new CardRepository();
        Card card = cardRepository.getCardByNumber(transaction.getCard_number());
        boolean check=checkCard(transaction);
        if(!check) {
            return false;
        }else {
                transaction.setCreated_date(LocalDate.now());
                transaction.setType(TransactionType.PAYMENT);
                transaction.setTerminal_code("not");
                TransactionRepository transactionRepository=new TransactionRepository();
                transactionRepository.save(transaction);
                card.setBalance(card.getBalance()-transaction.getAmount());
                Boolean cardTransaction = cardRepository.makeCardTransaction(card);
                if (!cardTransaction){
                    return false;
                }
                return true;
            }
    }

    private boolean checkCard(Transaction transaction) {
        CardRepository cardRepository=new CardRepository();
        Card card = cardRepository.getCardByNumber(transaction.getCard_number());
        if(card == null){
            System.err.println("This card number doesn't exist");
            return false;
        } else if (card.getStatus().equals("Blocked")) {
            System.out.println("This Card is blocked");
            return false;
        }else if (card.getBalance()<transaction.getAmount()){
                return false;
        }else {
                return true;
        }
    }

    public Boolean makeTransaction(Transaction transaction) {
        CardRepository cardRepository=new CardRepository();
        Card card = cardRepository.getCardByNumber(transaction.getCard_number());
        if(card == null) {
            System.err.println("This card number doesn't exist");
            return false;
        } else if (card.getStatus().equals("Blocked")) {
            System.err.println("This card is Blocked");
            return false;
        } else {
            transaction.setCreated_date(LocalDate.now());
            transaction.setType(TransactionType.REFILL);
            TransactionRepository transactionRepository=new TransactionRepository();
            transactionRepository.save(transaction);
            card.setBalance(card.getBalance()+transaction.getAmount());
            Boolean cardTransaction = cardRepository.makeCardTransaction(card);
            if (!cardTransaction){
                return false;
            }
            return true;
        }
    }
}
