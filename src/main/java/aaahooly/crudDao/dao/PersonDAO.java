package aaahooly.crudDao.dao;

import aaahooly.crudDao.models.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private static int PEOPLE_COUNT;

    private  static final String DB_URL = "jdbc:mysql://localhost:3306/testbase";
    private  static final String DB_NAME = "root";
    private  static final String DB_PASSWORD = "Aaahooly2005199518796.";

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // <- без явного указания не искал Driver
            connection = DriverManager.getConnection(DB_URL,DB_NAME,DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Person> index() {

        List<Person> people = new ArrayList<>();
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Person");
            while (resultSet.next()) {
                people.add(new Person(resultSet.getInt("id"),
                        resultSet.getString("name"), resultSet.getString("email")
                        , resultSet.getInt("age")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;
    }

    public Person show(int id) {
        try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM  Person where id=?")) {
            ps.setInt(1,id);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return new Person(resultSet.getInt("id"),resultSet.getString("name"),
                    resultSet.getString("email"), resultSet.getInt("age"));

        } catch (SQLException e) {
           e.printStackTrace();
        }
        //return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
        return new Person();
    }

    public void save(Person person) {
        try(PreparedStatement ps = connection.prepareStatement("INSERT INTO Person VALUES(1,?,?,?)")) {
            ps.setString(1, person.getName());
            ps.setInt(2, person.getAge());
            ps.setString(3, person.getEmail());
            ps.executeUpdate();
            System.out.println("Save in DB: \n" + person.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
//      // Использование sql запроса на прямую к базе
//        try {
//            Statement statement = connection.createStatement();
//            String sql = "INSERT INTO Person VALUES(" + 1 + ",'" + person.getName() +
//                    "'," + person.getAge() + ",'" + person.getEmail() + "')";
//            statement.executeUpdate(sql);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    public void update(int id, Person updatePerson) {
        try(PreparedStatement ps = connection.prepareStatement("UPDATE Person SET name=?, age=?," +
                "email=? where id=?" )) { // Обновляем объект в базе
            ps.setString(1, updatePerson.getName());
            ps.setInt(2, updatePerson.getAge());
            ps.setString(3, updatePerson.getEmail());
            ps.setInt(4,id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        Person personToBeUpdated = show(id);
//        personToBeUpdated.setName(updatePerson.getName());
//        personToBeUpdated.setAge(updatePerson.getAge());
//        personToBeUpdated.setEmail(updatePerson.getEmail());
    }

    public void delete(int id) { // DELETE FROM ID
        try(PreparedStatement ps = connection.prepareStatement("DELETE from person where id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //people.removeIf(p -> p.getId() == id);
    }
}
