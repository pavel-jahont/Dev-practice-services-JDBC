package com.gmail.jahont.pavel.repository.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.gmail.jahont.pavel.repository.CarRepository;
import com.gmail.jahont.pavel.repository.model.Car;
import com.gmail.jahont.pavel.repository.model.CarModelEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CarRepositoryImpl implements CarRepository {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void deleteAll(Connection connection) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM car"
                )
        ) {
            int affectedRows = statement.executeUpdate();
            logger.info("Deleted cars: " + affectedRows);
        }
    }

    @Override
    public Car add(Connection connection, Car car) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO car(name, car_model, engine_capacity) VALUES (?,?,?)",
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            statement.setString(1, car.getName());
            statement.setString(2, car.getCarModel().name());
            statement.setInt(3, car.getEngineCapacity());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating car failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    car.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating car failed, no ID obtained.");
                }
            }
            return car;
        }
    }

    @Override
    public List<Car> findAll(Connection connection) throws SQLException {
        try (
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM car")
        ) {
            List<Car> cars = new ArrayList<>();
            while (rs.next()) {
                Car car = getCar(rs);
                cars.add(car);
            }
            return cars;
        }
    }

    @Override
    public List<Integer> findCarsWithMinEngineCapacity(Connection connection) throws SQLException {
        try (
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT id FROM car WHERE engine_capacity=(SELECT MIN(engine_capacity) FROM car)")
        ) {
            List<Integer> ids = new ArrayList<>();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                ids.add(id);
            }
            return ids;
        }
    }

    @Override
    public void deleteCars(Connection connection, List<Integer> ids) throws SQLException {
        try (
                Statement statement = connection.createStatement()
        ) {
            String collect = ids.stream().map(String::valueOf).collect(Collectors.joining(", "));
            int affectedRows = statement.executeUpdate("DELETE FROM car WHERE id IN (" + collect + ")");
            logger.info("Deleted cars: " + affectedRows);
        }
    }

    @Override
    public int getCarCountAccordingEngineCapacity(Connection connection, int engineCapacity) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT COUNT(*) as count FROM car WHERE engine_capacity=?"
                )
        ) {
            statement.setInt(1, engineCapacity);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count");
                } else {
                    throw new SQLException("Count cars failed");
                }
            }
        }
    }

    @Override
    public void updateNameForCarWithEngineCapacity(Connection connection, int updatedEngineCapacity, String name) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE car SET name = ? WHERE engine_capacity=?"
                )
        ) {
            statement.setString(1, name);
            statement.setInt(2, updatedEngineCapacity);
            int affectedRows = statement.executeUpdate();
            logger.info("Updated cars: " + affectedRows + " with engine capacity: " + updatedEngineCapacity);
        }
    }

    private Car getCar(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String carModel = rs.getString("car_model");
        int engineCapacity = rs.getInt("engine_capacity");
        return Car.newBuilder()
                .id(id)
                .name(name)
                .carModel(CarModelEnum.valueOf(carModel))
                .engineCapacity(engineCapacity)
                .build();
    }
}