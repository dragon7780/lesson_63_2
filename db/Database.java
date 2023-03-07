package db;

import dto.Card;
import dto.Profile;
import enums.ProfileRole;
import repository.CardRepository;
import repository.ProfileRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class Database {
    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/Atto", "postgres", "azizbek");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
    public static void createTable() {
        try {
            String studentTable = "create table if not exists profile( " +
                    "                name varchar(25) not null, " +
                    "                surname varchar(25) not null, " +
                    "                phone varchar(9), " +
                    "                password varchar, "+
                    "                created_date timestamp, " +
                    "                status varchar, "+
                    "                role varchar"+
                    "         );";
            Connection con = getConnection();
            Statement statement = con.createStatement();
            statement.executeUpdate(studentTable);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void createCardTable() {
        try {
            String studentTable = "create table if not exists card( " +
                    "                number varchar(25) not null, " +
                    "                exp_date varchar(25) not null, " +
                    "                balance integer, " +
                    "                status varchar, "+
                    "                phone varchar, "+
                    "                created_date timestamp " +
                    "         );";
            Connection con = getConnection();
            Statement statement = con.createStatement();
            statement.executeUpdate(studentTable);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void createTerminalTable() {
        try {
            String studentTable = "create table if not exists terminal( " +
                    "                code varchar, " +
                    "                adress varchar not null, " +
                    "                status varchar, " +
                    "                created_date timestamp " +
                    "         );";
            Connection con = getConnection();
            Statement statement = con.createStatement();
            statement.executeUpdate(studentTable);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void createTransactionTable() {
        try {
            String studentTable = "create table if not exists transaction( " +
                    "                card_number varchar, " +
                    "                amount integer not null, " +
                    "                terminal_code varchar, " +
                    "                type varchar,"+
                    "                created_date timestamp " +
                    "         );";
            Connection con = getConnection();
            Statement statement = con.createStatement();
            statement.executeUpdate(studentTable);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    static {
        Profile profile=new Profile();
        profile.setName("admin");
        profile.setSurname("admin");
        profile.setPassword("7777");
        profile.setPhone("707777777");
        profile.setStatus("Active");
        profile.setRole(ProfileRole.ADMIN);
        profile.setCreated_date(LocalDate.now());
        ProfileRepository profileRepository=new ProfileRepository();
        Profile profile1 = profileRepository.getProfileByPhone(profile.getPhone());
        if(profile1 == null){
        profileRepository.save(profile);
        }
    }
    static {
        Card card=new Card();
        card.setNumber("1111111");
        card.setBalance(0);
        card.setExp_date("1211");
        card.setStatus("Company");
        card.setCreated_date(LocalDate.now());
        card.setPhone("000000000");
        CardRepository cardRepository=new CardRepository();
        Card number = cardRepository.getCardByNumber(card.getNumber());
        if (number == null){
            cardRepository.save(card);
        }
    }
}