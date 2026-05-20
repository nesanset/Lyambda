package ru.mephi.vikingdemo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.mephi.vikingdemo.model.Viking;
import ru.mephi.vikingdemo.service.VikingService;

import java.util.List;

@RestController
@RequestMapping("/api/vikings")
@Tag(name = "Vikings", description = "Операции с викингами")
public class VikingController {

    private final VikingService vikingService;
    private VikingListener vikingListener;

    public VikingController(VikingService vikingService, VikingListener vikingListener) {
        this.vikingService = vikingService;
        this.vikingListener = vikingListener;
    }
    
    @GetMapping
    @Operation(summary = "Получить список созданных викингов", 
            operationId = "getAllVikings")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список успешно получен")
    })
    public List<Viking> getAllVikings() {
        System.out.println("GET /api/vikings called");
        return vikingService.findAll();
    }

    @GetMapping("/test")
    @Operation(summary = "Получить список тестовых викингов", 
            operationId = "getTest")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список успешно получен")
    })
    public List<String> test() {
        System.out.println("GET /api/vikings/test called");
        return List.of("Ragnar", "Bjorn");
    }
    
    @PostMapping("/add")
    @Operation(summary = "Добавить случайного викинга",
            description = "Создает одного случайного викинга и добавляет его в список.")
    public void addViking(){
        vikingListener.testAdd();
    }

    @PostMapping("/generate")
    @Operation(summary = "Сгенерировать несколько викингов",
            description = "Создает указанное количество случайных викингов через существующий сервис.")
    public List<Viking> generateRandomVikings(@RequestParam int count){
        return vikingListener.generate(count);
    }

    @PostMapping
    @Operation(summary = "Добавить переданного викинга",
            description = "Добавляет в список викинга, который передан в теле запроса.")
    public void addViking(@RequestBody Viking viking) {
        vikingListener.add(viking);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Удалить викинга",
            description = "Удаляет викинга по индексу в списке.")
    public void deleteViking(@RequestParam int index) {
        vikingListener.delete(index);
    }

    @PutMapping("/update")
    @Operation(summary = "Обновить викинга",
            description = "Заменяет викинга по индексу данными из тела запроса.")
    public void updateViking(@RequestParam int index, @RequestBody Viking viking) {
        vikingListener.update(index, viking);
    }
}
