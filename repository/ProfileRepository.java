package repository;

import db.Database;
import dto.Card;
import dto.Profile;
import enums.ProfileRole;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class ProfileRepository {
        public void save(Profile profile) {
            try {
                Connection con = Database.getConnection();
                Statement statement=con.createStatement();
                String sql=String.format("insert into profile (name,surname,phone,password,created_date,status,role) " +
                                "values('%s','%s','%s','%s','%s','%s','%s');",
                        profile.getName(), profile.getSurname(),profile.getPhone(),
                        profile.getPassword(),profile.getCreated_date(),profile.getStatus(),profile.getRole());
                statement.executeUpdate(sql);
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        public Profile getProfileByPhone(String phone) {
            try {
                Profile profile = null;
                Connection con = Database.getConnection();
                PreparedStatement prepareStatement = con.prepareStatement("select * from profile where phone = ?");
                prepareStatement.setString(1, phone);
                ResultSet resultSet = prepareStatement.executeQuery();
                if (resultSet.next()) {
                    profile = new Profile();
                    profile.setName(resultSet.getString("name"));
                    profile.setSurname(resultSet.getString("surname"));
                    profile.setPhone(resultSet.getString("phone"));
                    profile.setPhone(resultSet.getString("password"));
                    profile.setCreated_date(LocalDate.from(resultSet.getTimestamp("created_date").toLocalDateTime()));
                }
                con.close();
                return profile;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        public List<Profile> getAll() {
            List<Profile> profileList = new LinkedList<>();
            try {
                Connection con = Database.getConnection();
                Statement statement = con.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from profile");
                while (resultSet.next()) {
                    Profile profile = new Profile();
                    profile.setName(resultSet.getString("name"));
                    profile.setSurname(resultSet.getString("surname"));
                    profile.setPhone(resultSet.getString("phone"));
                    profile.setPassword(resultSet.getString("password"));
                    profile.setCreated_date(LocalDate.from(resultSet.getTimestamp("created_date").toLocalDateTime()));
                    profile.setStatus(resultSet.getString("status"));
                    profile.setRole(ProfileRole.valueOf(resultSet.getString("role")));
                    profileList.add(profile);
                }
                con.close();
                return profileList;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return profileList;
        }
        public boolean deleteById(String phone) {
            try {
                Connection con = Database.getConnection();
                PreparedStatement prepareStatement = con.prepareStatement("delete from profile where phone = ?");
                prepareStatement.setString(1, phone);
                int n = prepareStatement.executeUpdate();
//            return n == 0 ? false : true;
                return n != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
        public Boolean changeProfileByPhone(Profile profile){
        Connection connection = Database.getConnection();
        int resultSet = 0;
        try {
            PreparedStatement statement = connection.prepareStatement
                    ("update profile set status=? where phone=? ");
            if(profile.getStatus().equals("Blocked")){
                statement.setString(1,"Blocked");
            }else{
                statement.setString(1,"Active");
            }
            statement.setString(2, profile.getPhone());
            resultSet = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (resultSet == 1){
            System.out.println("Profile succesfully changed!!!");
            return true;
        }
        return false;
    }
}


