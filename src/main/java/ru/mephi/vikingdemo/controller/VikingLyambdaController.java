package ru.mephi.vikingdemo.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import ru.mephi.vikingdemo.model.*;
import ru.mephi.vikingdemo.service.VikingLyambdaService;

@RestController
@RequestMapping("/api/lyambda")
public class VikingLyambdaController{
    private final VikingLyambdaService vikingLyambdaService;

    public VikingLyambdaController(VikingLyambdaService vikingLyambdaService){
        this.vikingLyambdaService = vikingLyambdaService;
    }

    @GetMapping("/age/more")
    @Operation(summary = "Посчитать викингов старше возраста")
    public long countAgeMoreThan(@RequestParam int age){
        return vikingLyambdaService.countAgeMoreThan(age);
    }

    @GetMapping("/age/less")
    @Operation(summary = "Посчитать викингов младше возраста")
    public long countAgeLessThan(@RequestParam int age){
        return vikingLyambdaService.countAgeLessThan(age);
    }

    @GetMapping("/age/range")
    @Operation(summary = "Посчитать викингов в диапазоне возраста")
    public long countAgeInRange(@RequestParam int minAge, @RequestParam int maxAge){
        return vikingLyambdaService.countAgeInRange(minAge, maxAge);
    }

    @GetMapping("/age/outside")
    @Operation(summary = "Посчитать викингов вне диапазона возраста")
    public long countAgeOutOfRange(@RequestParam int minAge, @RequestParam int maxAge){
        return vikingLyambdaService.countAgeOutOfRange(minAge, maxAge);
    }

    @GetMapping("/beard-hair")
    @Operation(summary = "Посчитать по бороде и волосам")
    public long countByBeardAndHair(@RequestParam BeardStyle beardStyle, @RequestParam HairColor hairColor){
        return vikingLyambdaService.countByBeardAndHair(beardStyle, hairColor);
    }

    @GetMapping("/axes")
    @Operation(summary = "Посчитать викингов с одним или двумя топорами")
    public long countByOneOrTwoAxes(){
        return vikingLyambdaService.countByOneOrTwoAxes();
    }

    @GetMapping("/random-tall")
    @Operation(summary = "Получить случайного высокого викинга")
    public String getRandomTallVikingText(){
        return vikingLyambdaService.getRandomTallVikingText();
    }

    @GetMapping("/legendary")
    @Operation(summary = "Получить викингов с легендарным снаряжением")
    public String getLegendaryVikingsText(){
        return vikingLyambdaService.getLegendaryVikingsText();
    }

    @GetMapping("/red-sorted")
    @Operation(summary = "Получить рыжебородых по возрасту")
    public String getRedBeardVikingsSortedByAgeText(){
        return vikingLyambdaService.getRedBeardVikingsSortedByAgeText();
    }

    @GetMapping("/max-id")
    @Operation(summary = "Найти максимальный ID")
    public int getMaxId(){
        return vikingLyambdaService.getMaxId();
    }

    @GetMapping("/even-ids")
    @Operation(summary = "Получить четные ID")
    public String getEvenIdsText(){
        return vikingLyambdaService.getEvenIdsText();
    }
}