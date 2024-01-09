package hr.fer.tel.rassus.server.beans;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;


@Entity
public class Reading {
  //  TODO

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long readingId;
    private String temperature, humidity, pressure, co, no2, so2;

    @ManyToOne
    @JoinColumn(name = "sensorId")
    @JsonBackReference
    private Sensor sensor;

    public Reading() {
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public Long getReadingId() {
        return readingId;
    }

    public void setReadingId(Long readingId) {
        this.readingId = readingId;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getNo2() {
        return no2;
    }

    public void setNo2(String no2) {
        this.no2 = no2;
    }

    public String getSo2() {
        return so2;
    }

    public void setSo2(String so2) {
        this.so2 = so2;
    }

    @Override
    public String toString() {
        return "SensorReading{" +
            "id=" + readingId +
            ", temperature='" + temperature + '\'' +
            ", humidity='" + humidity + '\'' +
            ", pressure='" + pressure + '\'' +
            ", co='" + co + '\'' +
            ", no2='" + no2 + '\'' +
            ", so2='" + so2 + '\'' +
            '}';
    }
}

