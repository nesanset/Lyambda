package ru.mephi.vikingdemo.service;

import org.springframework.stereotype.Service;
import ru.mephi.vikingdemo.model.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;

@Service
public class VikingLyambdaService {
    private final VikingService vikingService;

    public VikingLyambdaService(VikingService vikingService){
        this.vikingService = vikingService;
    }

    public List<Viking> generateRandomVikings(int count){
        return vikingService.generateRandomVikings(count);
    }

    public long countAgeMoreThan(int age){
        Predicate<Viking> condition = viking -> viking.age() > age;
        return count(condition);
    }

    public long countAgeLessThan(int age){
        Predicate<Viking> condition = viking -> viking.age() < age;
        return count(condition);
    }

    public long countAgeInRange(int minAge, int maxAge){
        Predicate<Viking> condition = viking -> viking.age() >= minAge && viking.age() <= maxAge;
        return count(condition);
    }

    public long countAgeOutOfRange(int minAge, int maxAge){
        Predicate<Viking> condition = viking -> viking.age() < minAge || viking.age() > maxAge;
        return count(condition);
    }

    public long countByBeardAndHair(BeardStyle beardStyle, HairColor hairColor){
        Predicate<Viking> condition = viking -> viking.beardStyle() == beardStyle && viking.hairColor() == hairColor && viking.beardStyle() != BeardStyle.CLEAN_SHAVEN;
        return count(condition);
    }

    public long countByOneOrTwoAxes() {
        Predicate<Viking> condition = viking -> countAxes(viking) == 1 || countAxes(viking) == 2;
        return count(condition);
    }

    public String getRandomTallVikingText(){
        Supplier<List<Viking>> tallVikings = () -> vikingService.findAll().stream().filter(viking -> viking.heightCm() > 180).collect(Collectors.toList());
        List<Viking> result = tallVikings.get();
        if (result.isEmpty()){
            return "Нет викингов ростом выше 180";
        }
        return format(result.get((int) (Math.random()*result.size())));
    }

    public String getLegendaryVikingsText(){
        Predicate<Viking> condition = viking -> viking.equipment().stream().anyMatch(item -> item.quality().equals("Legendary"));
        return format(vikingService.findAll().stream().filter(condition).collect(Collectors.toList()));
    }

    public String getRedBeardVikingsSortedByAgeText(){
        Predicate<Viking> condition = viking -> viking.hairColor() == HairColor.Red;
        return format(vikingService.findAll().stream().filter(condition).sorted((first, second) -> Integer.compare(first.age(), second.age())).collect(Collectors.toList()));
    }

    public int getMaxId() {
        List<Integer> idList = vikingService.findAll().stream().map(viking -> viking.id()).collect(Collectors.toList());
        Integer[] ids = idList.toArray(new Integer[0]);
        return Arrays.stream(ids).max((first, second) -> Integer.compare(first, second)).orElse(0);
    }

    public String getEvenIdsText() {
        List<Integer> idList = vikingService.findAll().stream().map(viking -> viking.id()).collect(Collectors.toList());
        Integer[] ids = idList.toArray(new Integer[0]);
        return Arrays.stream(ids).filter(id -> id % 2 == 0).map(id -> String.valueOf(id)).collect(Collectors.joining(", "));
    }

    private long count(Predicate<Viking> condition){
        return vikingService.findAll().stream().filter(condition).count();
    }

    private int countAxes(Viking viking){
        return (int) viking.equipment().stream().filter(item -> item.name().equalsIgnoreCase("Axe")).count();
    }

    private String format(List<Viking> result){
        if (result.isEmpty()){
            return "Ничего не найдено";
        }
        return result.stream().map(viking -> format(viking)).collect(Collectors.joining("\n"));
    }

    private String format(Viking viking){
        Function<Viking, String> formatter = value -> "ID: " + value.id() + ", " + value.name() + ", возраст: " + value.age() + ", рост: " + value.heightCm() + ", цвет волос: " + value.hairColor() + ", форма бороды: " + value.beardStyle() + ", топоров: " + countAxes(value) + ", снаряжение: " + formatEquipment(value.equipment());
        return formatter.apply(viking);
    }

    private String formatEquipment(List<EquipmentItem> equipment){
        return equipment.stream().map(item -> item.name() + " (" + item.quality() + ")").collect(Collectors.joining(", "));
    }
}
