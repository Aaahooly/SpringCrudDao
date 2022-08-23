import aaahooly.crudDao.dao.PersonDAO;
import org.springframework.beans.factory.annotation.Autowired;

public class Main {
    public static void main(String[] args) {
        PersonDAO personDAO = new PersonDAO();
        System.out.println(personDAO.index());
    }
}
