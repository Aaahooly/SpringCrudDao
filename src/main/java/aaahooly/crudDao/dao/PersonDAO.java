package aaahooly.crudDao.dao;

import aaahooly.crudDao.models.Person;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {


    private final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //в параметрах бинаПропертиМаппера указывается модель. Этот объект переводит строки таблицы в объект модели, сам.
    public List<Person> index() {
        return jdbcTemplate.query("Select * from Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("Select * from Person where id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public Optional<Person> show(String email) {
        return jdbcTemplate.query("Select * from person where email=?", new Object[]{email},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
    //Использование тимплейта позволяет не использовать prepareStatement, Достаточно написать
    //Sql запрос(1 арг), 2 аргумент  это сами поля модели.
    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name,age,email,address) VALUES(?,?,?,?)", person.getName(), person.getAge(),
                person.getEmail(),person.getAddress());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE Person SET name=?, age=?,email=?,address=? where id=?", person.getName(), person.getAge(),
                person.getEmail(),person.getAddress(), id);
    }

    public void delete(int id) { // DELETE FROM ID
        jdbcTemplate.update("DELETE from person where id=?", id);
    }

    //Тестируем производительность пакетной вставки

    public void testMultipleUpdate() {
        List<Person> people = create1000People();
        long before = System.currentTimeMillis();
        for (Person person : people) {
            jdbcTemplate.update("INSERT INTO Person VALUES(?,?,?,?)", person.getId(), person.getName(), person.getAge(),
                    person.getEmail());
        }
        long after = System.currentTimeMillis();
        System.out.println("Time = " + (after - before));
    }

    public void testBatchUpdate() {
        List<Person> people = create1000People();
        long before = System.currentTimeMillis();
        jdbcTemplate.batchUpdate("INSERT INTO person VALUES (?,?,?,?)", new BatchPreparedStatementSetter() {
            @Override // Необходимо передать все объекты модели еоторые попадут в наш ситсок
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, people.get(i).getId());
                ps.setString(2, people.get(i).getName());
                ps.setInt(3, people.get(i).getAge());
                ps.setString(4, people.get(i).getEmail());
            }

            @Override // Необходимо вернуть размер пакета
            public int getBatchSize() {
                return people.size();
            }
        });
        long after = System.currentTimeMillis();
        System.out.println("Time = " + (after - before));
    }


    private List<Person> create1000People() {
        List<Person> people = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            people.add(new Person(i, "name" + i, 30, "test" + i + "@yandex.ru", "address" ));
        }
        return people;

    }

}

