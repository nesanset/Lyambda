package ru.mephi.vikingdemo.service;

import org.springframework.stereotype.Service;
import ru.mephi.vikingdemo.model.Viking;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mephi.vikingdemo.model.*;

@Service
public class VikingService {
    // каждый раз при изменении создаётся новая копия списка 
    private final CopyOnWriteArrayList<Viking> vikings = new CopyOnWriteArrayList<>();
    private final VikingFactory vikingFactory;
    private int nextId = 1;

    @Autowired
    public VikingService(VikingFactory vikingFactory){
        this.vikingFactory = vikingFactory;
    }
    
    public List<Viking> findAll(){
        return List.copyOf(vikings);
    }

    public Viking createRandomViking(){
        Viking viking = vikingFactory.createRandomViking(nextId++);
        vikings.add(viking);
        return viking;
    }

    public List<Viking> generateRandomVikings(int count) {
        List<Viking> generated = IntStream.range(0, count).mapToObj(index -> vikingFactory.createRandomViking(nextId++)).collect(Collectors.toList());
        generated.forEach(viking -> vikings.add(viking));
        return generated;
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
        Predicate<Viking> condition = viking -> viking.beardStyle() == beardStyle && viking.hairColor() == hairColor  && viking.beardStyle() != BeardStyle.CLEAN_SHAVEN;
        return count(condition);
    }

    public long countByAxeCount(int axeCount){
        Predicate<Viking> condition = viking -> countAxes(viking) ==axeCount;
        return count(condition);
    }

    public String getRandomTallVikingText(){
        Supplier<List<Viking>> tallVikings = () -> vikings.stream().filter(viking -> viking.heightCm() > 180).collect(Collectors.toList());
        List<Viking> result = tallVikings.get();
        if (result.isEmpty()){
            return "Нет викингов ростом выше 180";
        }
        return format(result.get((int) (Math.random()*result.size())));
    }

    public String getLegendaryVikingsText(){
        Predicate<Viking> condition = viking -> viking.equipment().stream().anyMatch(item -> item.quality().equals("Legendary"));
        return format(vikings.stream().filter(condition).collect(Collectors.toList()));
    }

    public String getRedBeardVikingsSortedByAgeText(){
        Predicate<Viking> condition = viking -> viking.hairColor() == HairColor.Red;
        return format(vikings.stream().filter(condition).sorted((first, second) -> Integer.compare(first.age(), second.age())).collect(Collectors.toList()));
    }

    public int getMaxId(){
        return vikings.stream().mapToInt(viking -> viking.id()).max().orElse(0);
    }

    public String getEvenIdsText(){
        return vikings.stream().mapToInt(viking -> viking.id()).filter(id -> id % 2 == 0).mapToObj(String::valueOf).collect(Collectors.joining(", "));
    }

    public Viking createViking(Viking viking){
        Viking saved = viking.id() > 0 ? viking : new Viking(
                nextId++,
                viking.name(),
                viking.age(),
                viking.heightCm(),
                viking.hairColor(),
                viking.beardStyle(),
                viking.equipment()
        );

        if (saved.id() >= nextId) {
            nextId = saved.id() + 1;
        }

        vikings.add(saved);
        return saved;
    }

    public int removeViking(int index) throws IndexOutOfBoundsException{
        if (index < 0 || index >= vikings.size()) {
            throw new IndexOutOfBoundsException("Такого викинга не существует");
        }
        vikings.remove(index);
        return index;
    }

    public Viking updateViking(int index, Viking viking) throws IndexOutOfBoundsException{
        if (index < 0 || index >= vikings.size()) {
            throw new IndexOutOfBoundsException("Такого викинга не существует");
        }
        vikings.set(index, viking);
        return viking;
    }

    private long count(Predicate<Viking> condition){
        return vikings.stream().filter(condition).count();
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
        Function<Viking, String> formatter = value -> "ID: " + value.id()+ ", " + value.name() + ", возраст: " + value.age() + ", рост: " + value.heightCm() + ", цвет волос: " + value.hairColor() + ", форма бороды: " + value.beardStyle() + ", топоров: " + countAxes(value) + ", снаряжение: " + formatEquipment(value.equipment());
        return formatter.apply(viking);
    }

    private String formatEquipment(List<EquipmentItem> equipment){
        return equipment.stream().map(item -> item.name() + " (" + item.quality() + ")").collect(Collectors.joining(", "));
    }
}