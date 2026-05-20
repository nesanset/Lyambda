package ru.mephi.vikingdemo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.mephi.vikingdemo.model.*;
import ru.mephi.vikingdemo.service.VikingLyambdaService;

@RestController
@RequestMapping("/api/lyambda")
@Tag(name = "Lyambda analytics", description = "Аналитика по викингам через лямбда-функции")
public class VikingLyambdaController{
    private final VikingLyambdaService vikingLyambdaService;

    public VikingLyambdaController(VikingLyambdaService vikingLyambdaService){
        this.vikingLyambdaService = vikingLyambdaService;
    }

    @GetMapping("/age/more")
    @Operation(summary = "Посчитать викингов старше возраста",
            description = "Возвращает количество викингов, у которых возраст больше переданного значения.")
    public long countAgeMoreThan(@RequestParam int age){
        return vikingLyambdaService.countAgeMoreThan(age);
    }

    @GetMapping("/age/less")
    @Operation(summary = "Посчитать викингов младше возраста",
            description = "Возвращает количество викингов, у которых возраст меньше переданного значения.")
    public long countAgeLessThan(@RequestParam int age){
        return vikingLyambdaService.countAgeLessThan(age);
    }

    @GetMapping("/age/range")
    @Operation(summary = "Посчитать викингов в диапазоне возраста",
            description = "Возвращает количество викингов, возраст которых находится между minAge и maxAge.")
    public long countAgeInRange(@RequestParam int minAge, @RequestParam int maxAge){
        return vikingLyambdaService.countAgeInRange(minAge, maxAge);
    }

    @GetMapping("/age/outside")
    @Operation(summary = "Посчитать викингов вне диапазона возраста",
            description = "Возвращает количество викингов, возраст которых меньше minAge или больше maxAge.")
    public long countAgeOutOfRange(@RequestParam int minAge, @RequestParam int maxAge){
        return vikingLyambdaService.countAgeOutOfRange(minAge, maxAge);
    }

    @GetMapping("/beard-hair")
    @Operation(summary = "Посчитать по бороде и волосам",
            description = "Возвращает количество викингов с указанной формой бороды и цветом волос одновременно.")
    public long countByBeardAndHair(@RequestParam BeardStyle beardStyle, @RequestParam HairColor hairColor){
        return vikingLyambdaService.countByBeardAndHair(beardStyle, hairColor);
    }

    @GetMapping("/axes")
    @Operation(summary = "Посчитать викингов с одним или двумя топорами",
            description = "Возвращает количество викингов, у которых ровно один или ровно два топора.")
    public long countByOneOrTwoAxes(){
        return vikingLyambdaService.countByOneOrTwoAxes();
    }

    @GetMapping("/random-tall")
    @Operation(summary = "Получить случайного высокого викинга",
            description = "Возвращает случайного викинга ростом выше 180 см в виде текста.")
    public String getRandomTallVikingText(){
        return vikingLyambdaService.getRandomTallVikingText();
    }

    @GetMapping("/legendary")
    @Operation(summary = "Получить викингов с легендарным снаряжением",
            description = "Возвращает текстовый список всех викингов, у которых есть предмет качества Legendary.")
    public String getLegendaryVikingsText(){
        return vikingLyambdaService.getLegendaryVikingsText();
    }

    @GetMapping("/red-sorted")
    @Operation(summary = "Получить рыжебородых по возрасту",
            description = "Возвращает текстовый список рыжих викингов, отсортированный по возрасту.")
    public String getRedBeardVikingsSortedByAgeText(){
        return vikingLyambdaService.getRedBeardVikingsSortedByAgeText();
    }

    @GetMapping("/max-id")
    @Operation(summary = "Найти максимальный ID",
            description = "Возвращает максимальный ID из массива ID викингов.")
    public int getMaxId(){
        return vikingLyambdaService.getMaxId();
    }

    @GetMapping("/even-ids")
    @Operation(summary = "Получить четные ID",
            description = "Возвращает все четные ID из массива ID викингов.")
    public String getEvenIdsText(){
        return vikingLyambdaService.getEvenIdsText();
    }
}
