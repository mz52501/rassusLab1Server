package hr.fer.tel.rassus.server.beans;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Sensor {
  //  TODO
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long sensorId;
  private Double latitude, longitude;
  private String ip;
  private Integer port;

  @OneToOne
  @JoinColumn(name = "closest_sensor_id") // Name of the column that holds the ID of the closest sensor
  @JsonManagedReference
  private Sensor closestSensor;

  public Sensor() {
  }

  @OneToMany(mappedBy = "sensor")
  @JsonManagedReference
  private Set<Reading> readings;

  public Long getSensorId() {
    return sensorId;
  }

  public void setSensorId(Long sensorId) {
    this.sensorId = sensorId;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public Set<Reading> getReadings() {
    return readings;
  }

  public void setReadings(Set<Reading> readings) {
    this.readings = readings;
  }

  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public Sensor getClosestSensor() {
    return closestSensor;
  }

  public void setClosestSensor(Sensor closestSensor) {
    this.closestSensor = closestSensor;
  }
}


