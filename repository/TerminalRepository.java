package repository;

import db.Database;
import dto.Card;
import dto.Terminal;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class TerminalRepository {
    public void save(Terminal terminal){
        try {
            Connection con = Database.getConnection();
            Statement statement=con.createStatement();
            String sql=String.format("insert into terminal (code,adress,status,created_date) " +
                            "values('%s','%s','%s','%s');",
                    terminal.getCode(), terminal.getAddress(),terminal.getStatus(),terminal.getCreated_date());
            int update = statement.executeUpdate(sql);
            System.out.println(update);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Terminal> getAll() {
        List<Terminal> terminalList = new LinkedList<>();
        try {
            Connection con = Database.getConnection();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from terminal order by created_date desc ");
            while (resultSet.next()) {
                Terminal terminal = new Terminal();
                terminal.setCode(resultSet.getString("code"));
                terminal.setAddress(resultSet.getString("adress"));
                terminal.setStatus(resultSet.getString("status"));
                terminal.setCreated_date(LocalDate.from(resultSet.getTimestamp("created_date").toLocalDateTime()));
                terminalList.add(terminal);
            }
            con.close();
            return terminalList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return terminalList;
    }
    public Terminal getTerminalByCode(String code){
        Connection connection = Database.getConnection();
        Terminal terminal=null;
        try {
            PreparedStatement statement = connection.prepareStatement("select*from terminal where code=?");
            statement.setString(1,code);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                terminal=new Terminal();
                terminal.setCode(resultSet.getString("code"));
                terminal.setStatus(resultSet.getString("status"));
                terminal.setCreated_date(LocalDate.from(resultSet.getTimestamp("created_date").toLocalDateTime()));
                terminal.setAddress(resultSet.getString("adress"));
                return terminal;
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return terminal;
    }
    public void changeTerminalByCode(Terminal terminal){
        Connection connection = Database.getConnection();
        int resultSet = 0;
        try {
            PreparedStatement statement = connection.prepareStatement
                    ("update terminal set status=? where code=? ");
            if(terminal.getStatus().equals("Blocked")){
                statement.setString(1,"Blocked");
            }else{
                statement.setString(1,"Active");
            }
            statement.setString(2, terminal.getCode());
            resultSet = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public Boolean deleteTerminal(String code) {
            try {
                Connection con = Database.getConnection();
                PreparedStatement prepareStatement = con.prepareStatement("delete from terminal where code = ?");
                prepareStatement.setString(1, code);
                int n = prepareStatement.executeUpdate();
//            return n == 0 ? false : true;
                return n != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
    }
}
