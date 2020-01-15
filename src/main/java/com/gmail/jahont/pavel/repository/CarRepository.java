package com.gmail.jahont.pavel.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gmail.jahont.pavel.repository.model.Car;

public interface CarRepository {

    void deleteAll(Connection connection) throws SQLException;

    Car add(Connection connection, Car car) throws SQLException;

    List<Car> findAll(Connection connection) throws SQLException;

    List<Integer> findCarsWithMinEngineCapacity(Connection connection) throws SQLException;

    void deleteCars(Connection connection, List<Integer> ids) throws SQLException;

    int getCarCountAccordingEngineCapacity(Connection connection, int engineCapacity) throws SQLException;

    void updateNameForCarWithEngineCapacity(Connection connection, int updatedEngineCapacity, String name) throws SQLException;
}