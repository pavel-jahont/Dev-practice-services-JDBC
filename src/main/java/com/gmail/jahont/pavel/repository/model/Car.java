package com.gmail.jahont.pavel.repository.model;

public class Car {

    private Integer id;
    private String name;
    private CarModelEnum carModel;
    private Integer engineCapacity;

    private Car(Builder builder) {
        id = builder.id;
        name = builder.name;
        carModel = builder.carModel;
        engineCapacity = builder.engineCapacity;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CarModelEnum getCarModel() {
        return carModel;
    }

    public Integer getEngineCapacity() {
        return engineCapacity;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", carModel=" + carModel +
                ", engineCapacity=" + engineCapacity +
                '}';
    }

    public static final class Builder {

        private Integer id;
        private String name;
        private CarModelEnum carModel;
        private Integer engineCapacity;

        private Builder() {}

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder carModel(CarModelEnum val) {
            carModel = val;
            return this;
        }

        public Builder engineCapacity(Integer val) {
            engineCapacity = val;
            return this;
        }

        public Car build() {
            return new Car(this);
        }
    }
}