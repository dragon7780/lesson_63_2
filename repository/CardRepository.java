package repository;

import db.Database;
import dto.Card;


import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class CardRepository {
    public void save(Card card){
        try {
            Connection con = Database.getConnection();
            Statement statement=con.createStatement();
            String sql=String.format("insert into card (number,exp_date,balance,status,phone,created_date) " +
                            "values('%s','%s','%s','%s','%s','%s');",
                    card.getNumber(), card.getExp_date(),card.getBalance(),
                    card.getStatus(),card.getPhone(),card.getCreated_date());
            int update = statement.executeUpdate(sql);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Card> getCardList(String phone){
        List<Card> cardList=new LinkedList<>();
        try{
            Card card=null;
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("select *from card where phone = ? order by created_date desc ");
            statement.setString(1,phone);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                card=new Card();
                card.setNumber(resultSet.getString("number"));
                card.setStatus(resultSet.getString("status"));
                card.setCreated_date(LocalDate.from(resultSet.getTimestamp("created_date").toLocalDateTime()));
                card.setPhone(resultSet.getString("phone"));
                card.setBalance(resultSet.getInt("balance"));
                card.setExp_date(resultSet.getString("exp_date"));
                cardList.add(card);
            }
            connection.close();
            return cardList;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return cardList;
    }
    public List<Card> getAll() {
        List<Card> cardList = new LinkedList<>();
        try {
            Connection con = Database.getConnection();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from card order by created_date desc");
            while (resultSet.next()) {
                Card card = new Card();
                card.setNumber(resultSet.getString("number"));
                card.setExp_date(resultSet.getString("exp_date"));
                card.setBalance(resultSet.getInt("balance"));
                card.setStatus(resultSet.getString("status"));
                card.setPhone(resultSet.getString("phone"));
                card.setCreated_date(LocalDate.from(resultSet.getTimestamp("created_date").toLocalDateTime()));
                cardList.add(card);
            }
            con.close();
            return cardList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cardList;
    }
    public Card getCardByNumber(String number){
        Connection connection = Database.getConnection();
        Card card=null;
        try {
            PreparedStatement statement = connection.prepareStatement("select*from card where number=?");
            statement.setString(1,number);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                card=new Card();
                card.setNumber(resultSet.getString("number"));
                card.setStatus(resultSet.getString("status"));
                card.setCreated_date(LocalDate.from(resultSet.getTimestamp("created_date").toLocalDateTime()));
                card.setPhone(resultSet.getString("phone"));
                card.setBalance(resultSet.getInt("balance"));
                card.setExp_date(resultSet.getString("exp_date"));
                return card;
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return card;
    }
    public Card getCardByNumber(String number,String phone){
        Connection connection = Database.getConnection();
        Card card=null;
        try {
            PreparedStatement statement = connection.prepareStatement("select*from card where number=? and phone=?");
            statement.setString(1,number);
            statement.setString(2,phone);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                card=new Card();
                card.setNumber(resultSet.getString("number"));
                card.setStatus(resultSet.getString("status"));
                card.setCreated_date(LocalDate.from(resultSet.getTimestamp("created_date").toLocalDateTime()));
                card.setPhone(resultSet.getString("phone"));
                card.setBalance(resultSet.getInt("balance"));
                card.setExp_date(resultSet.getString("exp_date"));
                return card;
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return card;
    }
    public Card getAdminCardByNumber(String number,String exp_date){
        Connection connection = Database.getConnection();
        Card card=null;
        try {
            PreparedStatement statement = connection.prepareStatement("select*from card where number=? and exp_date=?");
            statement.setString(1,number);
            statement.setString(2,exp_date);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                card=new Card();
                card.setNumber(resultSet.getString("number"));
                card.setStatus(resultSet.getString("status"));
                card.setCreated_date(LocalDate.from(resultSet.getTimestamp("created_date").toLocalDateTime()));
                card.setPhone(resultSet.getString("phone"));
                card.setBalance(resultSet.getInt("balance"));
                card.setExp_date(resultSet.getString("exp_date"));
                return card;
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return card;
    }
    public Boolean changeCardByNumber(Card card){
        Connection connection = Database.getConnection();
        int resultSet = 0;
        try {
            PreparedStatement statement = connection.prepareStatement
                    ("update card set status=? where number=? ");
            if(card.getStatus().equals("Blocked")){
                statement.setString(1,"Blocked");
            }else{
                statement.setString(1,"Active");
            }
            statement.setString(2, card.getNumber());
            resultSet = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (resultSet == 1){
            System.out.println("Card succesfully changed!!!");
            return true;
        }
        return false;
    }
    public Boolean refillCard(String number,int amount){
        Connection connection = Database.getConnection();
        int resultSet = 0;
        try {
            PreparedStatement statement = connection.prepareStatement("update card set balance=balance+? where number=?");
            statement.setInt(1,amount);
            statement.setString(2,number);
            resultSet = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (resultSet == 1){
            System.out.println("Card succesfully refilled!!!");
            return true;
        }
        return false;
    }
    public Boolean makeCardTransaction(Card card){
        Connection connection = Database.getConnection();
        int resultSet = 0;
        try {
            PreparedStatement statement = connection.prepareStatement("update card set balance=? where number=? ");
            statement.setInt(1,card.getBalance());
            statement.setString(2, card.getNumber());
            resultSet = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (resultSet == 1){
            System.out.println("Card succesfully changed balance!!!");
            return true;
        }
        return false;
    }
    public Boolean deleteCardByNum(String number) {
            try {
                Connection con = Database.getConnection();
                PreparedStatement prepareStatement = con.prepareStatement("delete from card where number = ?");
                prepareStatement.setString(1, number);
                int n = prepareStatement.executeUpdate();
//            return n == 0 ? false : true;
                return n != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
    }
}
