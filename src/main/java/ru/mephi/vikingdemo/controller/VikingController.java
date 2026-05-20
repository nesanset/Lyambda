package ru.mephi.vikingdemo.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import ru.mephi.vikingdemo.model.Viking;
import ru.mephi.vikingdemo.service.VikingService;

import java.util.List;

@RestController
@RequestMapping("/api/vikings")
public class VikingController {

    private final VikingService vikingService;
    private VikingListener vikingListener;

    public VikingController(VikingService vikingService, VikingListener vikingListener) {
        this.vikingService = vikingService;
        this.vikingListener = vikingListener;
    }
    
    @GetMapping
    @Operation(summary = "Получить список созданных викингов", operationId = "getAllVikings")
    public List<Viking> getAllVikings() {
        System.out.println("GET /api/vikings called");
        return vikingService.findAll();
    }

    @GetMapping("/test")
    @Operation(summary = "Получить список тестовых викингов", operationId = "getTest")
    public List<String> test() {
        System.out.println("GET /api/vikings/test called");
        return List.of("Ragnar", "Bjorn");
    }
    
    @PostMapping("/add")
    @Operation(summary = "Добавить случайного викинга")
    public void addViking(){
        vikingListener.testAdd();
    }

    @PostMapping("/generate")
    @Operation(summary = "Сгенерировать несколько викингов")
    public List<Viking> generateRandomVikings(@RequestParam int count){
        return vikingListener.generate(count);
    }

    @PostMapping
    @Operation(summary = "Добавить переданного викинга")
    public void addViking(@RequestBody Viking viking) {
        vikingListener.add(viking);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Удалить викинга")
    public void deleteViking(@RequestParam int index) {
        vikingListener.delete(index);
    }

    @PutMapping("/update")
    @Operation(summary = "Обновить викинга")
    public void updateViking(@RequestParam int index, @RequestBody Viking viking) {
        vikingListener.update(index, viking);
    }
}