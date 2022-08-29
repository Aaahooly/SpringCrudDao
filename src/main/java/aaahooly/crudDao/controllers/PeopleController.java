package aaahooly.crudDao.controllers;

import aaahooly.crudDao.dao.PersonDAO;
import aaahooly.crudDao.models.Person;

import aaahooly.crudDao.util.PersonValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) { // С помощью анотации @PathVariable Мы получим id c в метод
        model.addAttribute("person" ,personDAO.show(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person")  Person person) {
        return "people/new";
    }

    /* @ModelAttribute - конструирует объект из формы запроса
    @Valid - Проверяет валидность создание нового объекта peron
    BindingResult - отвечает за сбор ошибок.
     */
//    @PostMapping
//    public String create(@ModelAttribute("person") @Valid Person person,
//                         BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {//при проверки с помощъю биндингресульт обратно возваращается обЪект
//            //с внедреннымми результатми проверки на валидность
//            return "people/new";
//        }
//        personDAO.save(person);
//        return "redirect:/people";
//    }
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/new";

        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

//    @PatchMapping("/{id}")
//    public String update(@ModelAttribute("person") @Valid Person person, @PathVariable("id") int id,
//                         BindingResult bindingResult) {
//         if(bindingResult.hasErrors()) {
//             return  "people/edit";
//         }
//        personDAO.update(id, person);
//        return "redirect:/people"; //<- работает без указания слеша
//    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/edit";

        personDAO.update(id, person);
        return "redirect:/people";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id, Model model) {
        personDAO.delete(id);
        return "redirect:/people";
    }


}


