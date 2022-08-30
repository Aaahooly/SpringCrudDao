package aaahooly.crudDao.dao;

import aaahooly.crudDao.models.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

//Но есть способ не писать этот класс вообще,а использовать BeanPropertyRowMapper<>
//Чтобы использовать темплейт необходимо параметризовать класс Моделью.
public class PersonMapper implements RowMapper<Person> {


    // В методе мап ров необходимо отобразить струтуру построения обьекта модели из таблицы
    //Так же поменять тип возращаемого значения на модель, какую параметризовали интерфейс.
    @Override
    public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Person(resultSet.getInt("id"),resultSet.getString("name"),
                resultSet.getInt("age"),resultSet.getString("email"), resultSet.getString("address"));
    }
}
