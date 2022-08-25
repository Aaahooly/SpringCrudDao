package aaahooly.crudDao.dao;

import aaahooly.crudDao.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {


    private  final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //в параметрах бинаПропертиМаппера указывается модель. Этот объект переводит строки схемы в объект модели, сам.
    public List<Person> index() {
        return jdbcTemplate.query("Select * from Person",new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("Select * from Person where id=?",new Object[]{id},new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    //Использование тимплейта позволяет не использовать prepareStatement, Достаточно написать
    //Sql запрос(1 арг), 2 аргумент  это сами поля модели.
    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person VALUES(?,?,?)",person.getName(),person.getAge(),
                person.getEmail());
    }

    public void update(int id, Person person) {
       jdbcTemplate.update("UPDATE Person SET name=?, age=?,email=? where id=?",person.getName(),person.getAge(),
               person.getEmail(),id);
    }

    public void delete(int id) { // DELETE FROM ID
        jdbcTemplate.update("DELETE from person where id=?",id);
    }
}
