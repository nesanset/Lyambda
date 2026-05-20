/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.mephi.vikingdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mephi.vikingdemo.gui.VikingDesktopFrame;
import ru.mephi.vikingdemo.model.Viking;
import ru.mephi.vikingdemo.service.VikingService;

import java.util.List;

/**
 *
 * @author test2023
 */
@Component
public class VikingListener {
    private VikingService service;
    private VikingDesktopFrame gui;

    @Autowired
    public VikingListener(VikingService service) {
        this.service = service;
    }
    
    public void setGui(VikingDesktopFrame gui){
        this.gui = gui;
    }

    void testAdd() {
        gui.addNewViking(service.generateRandomVikings(1).get(0));
    }

    List<Viking> generate(int count) {
        List<Viking> vikings = service.generateRandomVikings(count);
        vikings.forEach(viking -> gui.addNewViking(viking));
        return vikings;
    }

    void add(Viking viking) {
        gui.addNewViking(service.saveViking(viking));
    }

    void delete(int index) {
        try {
            gui.removeOldViking(service.removeViking(index));
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    void update(int index, Viking viking) {
        try {
            gui.updateOldViking(index, service.updateViking(index, viking));
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }
}
