package hr.fer.tel.rassus.server.controllers;


import com.opencsv.bean.CsvToBeanBuilder;
import hr.fer.tel.rassus.server.DTO.SensorDTO;
import hr.fer.tel.rassus.server.beans.Reading;
import hr.fer.tel.rassus.server.beans.Sensor;
import hr.fer.tel.rassus.server.services.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@RestController
public class SensorsController {

    @Autowired
    private SensorRepository sensorRepository;

    public SensorsController(SensorRepository sensorRepository) throws FileNotFoundException {
        this.sensorRepository = sensorRepository;
    }


    //  TODO 4.1  Registracija
    @PostMapping("/sensors")
    public ResponseEntity<Sensor> sensorRegistration(@RequestBody SensorDTO sensorDTO) {
        Sensor sensor = new Sensor();
        sensor.setLatitude(sensorDTO.getLatitude());
        sensor.setLongitude(sensorDTO.getLongitude());
        sensor.setIp(sensorDTO.getIp());
        sensor.setPort(sensorDTO.getPort());
        sensorRepository.save(sensor);
        String locationUrl = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(sensor.getSensorId())
            .toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, locationUrl);
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(sensor);
    }

    //  TODO 4.4  Popis senzora
    @GetMapping("/sensors")
    public List<Sensor> getSensors() {
        return sensorRepository.findAll();
    }

    //  TODO 4.2  Najbliži susjed
    @GetMapping("/neighbour/{id}")
    public SensorDTO getClosestNeighbour(@PathVariable Long id) {
        List<Sensor> sensors = getSensors();
        Sensor sensor = sensorRepository.findById(id).orElse(null);
        double mindist = 100000;
        long sensorid = 0;
        int r = 6371;
        SensorDTO dto = new SensorDTO();
        if (sensor.getClosestSensor() == null) {
            for (Sensor s : sensors) {
                if (sensor.getSensorId() != s.getSensorId()) {
                    double dlat = s.getLatitude() - sensor.getLatitude();
                    double dlon = s.getLongitude() - sensor.getLongitude();
                    double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(s.getLatitude()) * Math.cos(sensor.getLatitude()) * Math.pow(Math.sin(dlon / 2), 2);
                    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                    double d = r * c;
                    if(d < mindist) {
                        mindist = d;
                        sensorid = s.getSensorId();
                    }
                }
            }
            if(sensorid != 0) {
                Sensor sToReturn = sensorRepository.findById(sensorid).orElse(null);
                dto.setLatitude(sToReturn.getLatitude());
                dto.setLongitude(sToReturn.getLongitude());
                dto.setIp(sToReturn.getIp());
                dto.setPort(sToReturn.getPort());
                sensor.setClosestSensor(sToReturn);
                sensorRepository.save(sensor);
            } else return null;
        } else {
            Sensor sToReturn = sensor.getClosestSensor();
            dto.setLatitude(sToReturn.getLatitude());
            dto.setLongitude(sToReturn.getLongitude());
            dto.setIp(sToReturn.getIp());
            dto.setPort(sToReturn.getPort());
        }
        return dto;
    }
}
