package aaahooly.crudDao.models;

import javax.validation.constraints.*;

public class Person {


    private  int id;

    @NotEmpty(message = "Name not be empty") // <- Говори о том что поле не может быть пустым
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters") // - Задаём диапозон символов от и до
    private String name;
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;
    @Min(value = 0, message = "Age should be greater than 0")
    private int age;

    public Person(String name, String email, int age) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public Person() {}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }
}
