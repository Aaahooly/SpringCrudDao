package aaahooly.crudDao.util;



import aaahooly.crudDao.dao.PersonDAO;
import aaahooly.crudDao.models.Person;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
//Сейчас у нас происходит двойная валидация 1- спринг 2-база данных
//но приложение падает с ошибкой из за того что В базе данных не обработан ексепшен
//Для устранения данной ошибки необходимо использовать
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;


    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);//Необходимо подставить класс шде будет использлыаться валидация
    }

    @Override //обработка ошибок валидации с DB
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (personDAO.show(person.getEmail()).isPresent()) {
            errors.rejectValue("email","","This email already taken");
        }
    }
}
