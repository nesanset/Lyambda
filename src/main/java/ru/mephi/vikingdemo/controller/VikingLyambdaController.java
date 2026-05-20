package ru.mephi.vikingdemo.controller;

import org.springframework.web.bind.annotation.*;
import ru.mephi.vikingdemo.model.*;
import ru.mephi.vikingdemo.service.VikingLyambdaService;

import java.util.List;

@RestController
@RequestMapping("/api/lyambda")
public class VikingLyambdaController{
    private final VikingLyambdaService vikingLyambdaService;

    public VikingLyambdaController(VikingLyambdaService vikingLyambdaService){
        this.vikingLyambdaService = vikingLyambdaService;
    }

    @PostMapping("/generate")
    public List<Viking> generateRandomVikings(@RequestParam int count){
        return vikingLyambdaService.generateRandomVikings(count);
    }

    @GetMapping("/age/more")
    public long countAgeMoreThan(@RequestParam int age){
        return vikingLyambdaService.countAgeMoreThan(age);
    }

    @GetMapping("/age/less")
    public long countAgeLessThan(@RequestParam int age){
        return vikingLyambdaService.countAgeLessThan(age);
    }

    @GetMapping("/age/range")
    public long countAgeInRange(@RequestParam int minAge, @RequestParam int maxAge){
        return vikingLyambdaService.countAgeInRange(minAge, maxAge);
    }

    @GetMapping("/age/outside")
    public long countAgeOutOfRange(@RequestParam int minAge, @RequestParam int maxAge){
        return vikingLyambdaService.countAgeOutOfRange(minAge, maxAge);
    }

    @GetMapping("/beard-hair")
    public long countByBeardAndHair(@RequestParam BeardStyle beardStyle, @RequestParam HairColor hairColor){
        return vikingLyambdaService.countByBeardAndHair(beardStyle, hairColor);
    }

    @GetMapping("/axes")
    public long countByOneOrTwoAxes(){
        return vikingLyambdaService.countByOneOrTwoAxes();
    }

    @GetMapping("/random-tall")
    public String getRandomTallVikingText(){
        return vikingLyambdaService.getRandomTallVikingText();
    }

    @GetMapping("/legendary")
    public String getLegendaryVikingsText(){
        return vikingLyambdaService.getLegendaryVikingsText();
    }

    @GetMapping("/red-sorted")
    public String getRedBeardVikingsSortedByAgeText(){
        return vikingLyambdaService.getRedBeardVikingsSortedByAgeText();
    }

    @GetMapping("/max-id")
    public int getMaxId(){
        return vikingLyambdaService.getMaxId();
    }

    @GetMapping("/even-ids")
    public String getEvenIdsText(){
        return vikingLyambdaService.getEvenIdsText();
    }
}
