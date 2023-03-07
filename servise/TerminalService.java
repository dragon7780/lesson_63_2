package servise;

import dto.Card;
import dto.Terminal;
import dto.Transaction;
import repository.CardRepository;
import repository.TerminalRepository;
import repository.TransactionRepository;

import java.time.LocalDate;

public class TerminalService {
    public Boolean addTerminal(Terminal terminal) {
        terminal.setStatus("Active");
        terminal.setCreated_date(LocalDate.now());
        TerminalRepository terminalRepository=new TerminalRepository();
        Terminal terminalByCode = terminalRepository.getTerminalByCode(terminal.getCode());
        if(terminalByCode != null){
            System.err.println("This terminal is exists");
            return false;
        }else {
         terminalRepository.save(terminal);
         return true;
        }
    }

    public Boolean changeStatus(String code) {
        TerminalRepository terminalRepository=new TerminalRepository();
        Terminal terminalByCode = terminalRepository.getTerminalByCode(code);
        if(terminalByCode == null){
            System.err.println("This terminal doesn't exist");
            return false;
        }else {
            if(terminalByCode.getStatus().equals("Active")){
                terminalByCode.setStatus("Blocked");
                terminalRepository.changeTerminalByCode(terminalByCode);
                return true;
            }else {
                terminalByCode.setStatus("Active");
                terminalRepository.changeTerminalByCode(terminalByCode);
                return true;
            }
        }
    }

    public Boolean deleteTerminal(String code) {
        TerminalRepository terminalRepository=new TerminalRepository();
        Terminal terminal = terminalRepository.getTerminalByCode(code);
        if(terminal == null){
            System.err.println("Terminal doesn't exist");
            return false;
        }else{
            Boolean deleteTerminal = terminalRepository.deleteTerminal(code);
            if(!deleteTerminal){
                return false;
            }else{
                return true;
            }
        }
    }

    public boolean makePayment(Terminal terminal, Transaction transaction) {
        TerminalRepository terminalRepository=new TerminalRepository();
        Terminal byCode = terminalRepository.getTerminalByCode(terminal.getCode());
        CardRepository cardRepository=new CardRepository();
        Card card = cardRepository.getCardByNumber(transaction.getCard_number());
        if(byCode==null){
            System.err.println("This terminal doesn't exist");
            return false;
        } else if (card == null) {
            System.err.println("This card doesn't exist");
            return false;
        } else if (card.getStatus().equals("Blocked")) {
            System.out.println("This card is blocked");
            return false;
        } else if (byCode.getStatus().equals("Blocked")){
            System.out.println("This terminal is blocked");
            return false;
        } else if (card.getBalance() < transaction.getAmount()) {
            System.err.println("Card doesn't have enough money");
            return false;
        }else {
            transaction.setTerminal_code(terminal.getCode());
            transaction.setCreated_date(LocalDate.now());
            TransactionRepository transactionRepository=new TransactionRepository();
            transactionRepository.save(transaction);
            card.setBalance(card.getBalance()-transaction.getAmount());
            cardRepository.makeCardTransaction(card);
            Card card1 = cardRepository.getCardByNumber("1111111");
            card1.setBalance(transaction.getAmount()+card1.getBalance());
            cardRepository.save(card1);
            return true;
        }
    }
}
