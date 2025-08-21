package com.example.playereval.web;

import jakarta.validation.constraints.*;

public class PlayerForm {
    @NotBlank(message = "{NotBlank.playerForm.name}")
    private String name;

    @Pattern(regexp = "\\d+", message = "{Pattern.playerForm.age}")
    private String age;

    @NotNull(message = "{NotNull.playerForm.indexerId}")
    private Integer indexerId;

    @NotNull(message = "{NotNull.playerForm.value}")
    private Float value;

    private Integer playerId; // null when creating

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }
    public Integer getIndexerId() { return indexerId; }
    public void setIndexerId(Integer indexerId) { this.indexerId = indexerId; }
    public Float getValue() { return value; }
    public void setValue(Float value) { this.value = value; }
    public Integer getPlayerId() { return playerId; }
    public void setPlayerId(Integer playerId) { this.playerId = playerId; }
}
