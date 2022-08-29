package aaahooly.crudDao.controllers;

import aaahooly.crudDao.dao.PersonDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// Пакетное обновление Если нужно вставить 1000 записей одной операцией а не
// для кадждой записи делать одну операцию вставки
@Controller
@RequestMapping("/test-batch-update")
public class BatchController {

    private  final PersonDAO personDAO;

    public BatchController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index() {
        return "batch/index";
    }

    //Реализация метода без пакетной вставки
    @GetMapping("/without")
    public String withoutBatch() {
        personDAO.testMultipleUpdate();
        return "redirect:/people";
    }
    //Реализация метода с пакетной вставкой
    @GetMapping("/with")
    public String withBatch() {
        personDAO.testBatchUpdate();
        return "redirect:/people";
    }
}
