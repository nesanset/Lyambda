package ru.mephi.vikingdemo.service;

import org.springframework.stereotype.Service;
import ru.mephi.vikingdemo.model.Viking;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;

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
}
