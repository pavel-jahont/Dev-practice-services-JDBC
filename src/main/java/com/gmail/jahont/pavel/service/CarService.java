package com.gmail.jahont.pavel.service;

import java.util.List;

import com.gmail.jahont.pavel.repository.model.Car;

public interface CarService {

    void deleteAll();

    void add(List<Car> cars);

    List<Car> findAll();

    void deleteCarsWithMinEngineCapacity();

    int getCarCountAccordingEngineCapacity(int engineCapacity);

    void updateNameForCarWithEngineCapacity(int updatedEngineCapacity, String name);

}