package repository;

import db.Database;
import dto.Terminal;
import dto.Transaction;
import enums.TransactionType;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class TransactionRepository {
    public void save(Transaction transaction){
        try {
            Connection con = Database.getConnection();
            Statement statement=con.createStatement();
            String sql=String.format("insert into transaction (card_number,amount,terminal_code,type,created_date) " +
                            "values('%s','%s','%s','%s','%s');",
                   transaction.getCard_number(),transaction.getAmount(),transaction.getTerminal_code(),transaction.getType(),transaction.getCreated_date());
            int update = statement.executeUpdate(sql);
            System.out.println(update);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Transaction> getAll() {
        List<Transaction> transactionList = new LinkedList<>();
        try {
            Connection con = Database.getConnection();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from transaction order by created_date desc ");
            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setCard_number(resultSet.getString("card_number"));
                transaction.setAmount(resultSet.getInt("amount"));
                transaction.setTerminal_code(resultSet.getString("terminal_code"));
                transaction.setType(TransactionType.valueOf(resultSet.getString("type")));
                transaction.setCreated_date(LocalDate.from(resultSet.getTimestamp("created_date").toLocalDateTime()));
                transactionList.add(transaction);
            }
            con.close();
            return transactionList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionList;
    }
}
