package com.jo.drones.global.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jo.drones.global.entity.enums.Model;
import com.jo.drones.global.entity.enums.State;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "drones")
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 100)
    @NotNull(message = "please inter serialNumber")
    private String serialNumber;

    @NotNull(message = "please inter model")
    @Enumerated(EnumType.STRING)
    private Model model;

    @NotNull(message = "please inter weightLimit")
    @Max(value = 500)
    @Min(value = 1)
    private int weightLimit;

    @NotNull(message = "please inter batteryCapacity")
    @Max(value = 100)
    @Min(value = 0)
    private int batteryCapacity;

    @NotNull(message = "please inter state")
    @Enumerated(EnumType.STRING)
    private State state;

    @OneToMany(mappedBy = "drone")
    @JsonIgnore
    private List<Medication> medications;

    public Drone() {
    }

    public Long getId() {
        return id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public int getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(int weightLimit) {
        this.weightLimit = weightLimit;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
