package com.gmail.jahont.pavel.service.impl;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.gmail.jahont.pavel.repository.model.Car;
import com.gmail.jahont.pavel.repository.model.CarModelEnum;
import com.gmail.jahont.pavel.service.CarService;
import com.gmail.jahont.pavel.service.HomeWorkService;
import com.gmail.jahont.pavel.util.RandomUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HomeWorkServiceImpl implements HomeWorkService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static final int MAX_ENGINE_CAPACITY = 10;
    private static final int MIN_ENGINE_CAPACITY = 1;
    private static final String TASK_FILE_NAME = "/task1.sql";
    private CarService carService = new CarServiceImpl();

    @Override
    public void runFirstTask() {
        logger.info("-------------First task-------------");
        try {
            URI fileLocation = getClass().getResource(TASK_FILE_NAME).toURI();
            Path sqlFile = Paths.get(fileLocation);
            try (Stream<String> stream = Files.lines(sqlFile)) {
                stream.forEach(logger::info);
            }
        } catch (URISyntaxException | IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Not valid file location", e);
        }
    }

    @Override
    public void runSecondTask() {
        logger.info("-------------Second task-------------");
        carService.deleteAll();
        List<Car> carList = new ArrayList<>();
        int countOfEntities = 10;
        for (int i = 0; i < countOfEntities; i++) {
            Car car = Car.newBuilder()
                    .name("test" + i)
                    .carModel(generateCarModel())
                    .engineCapacity(RandomUtil.getElement(MIN_ENGINE_CAPACITY, MAX_ENGINE_CAPACITY))
                    .build();
            carList.add(car);
        }
        carService.add(carList);

        List<Car> allCars = carService.findAll();
        allCars.forEach(logger::info);

        carService.deleteCarsWithMinEngineCapacity();

        int searchableEngineCapacity = RandomUtil.getElement(MIN_ENGINE_CAPACITY, MAX_ENGINE_CAPACITY);
        int carCountAccordingEngineCapacity = carService.getCarCountAccordingEngineCapacity(searchableEngineCapacity);
        logger.info("Car count with engine capacity: " + searchableEngineCapacity + " are " + carCountAccordingEngineCapacity);

        int updatedEngineCapacity = RandomUtil.getElement(MIN_ENGINE_CAPACITY, MAX_ENGINE_CAPACITY);
        carService.updateNameForCarWithEngineCapacity(updatedEngineCapacity, "Good capacity");

    }

    private CarModelEnum generateCarModel() {
        int index = RandomUtil.getElement(0, CarModelEnum.values().length - 1);
        return CarModelEnum.values()[index];
    }
}