package hr.fer.tel.rassus.server.controllers;

import com.opencsv.bean.CsvToBeanBuilder;
import hr.fer.tel.rassus.server.DTO.ReadingDTO;
import hr.fer.tel.rassus.server.beans.Reading;
import hr.fer.tel.rassus.server.beans.Sensor;
import hr.fer.tel.rassus.server.services.ReadingRepository;
import hr.fer.tel.rassus.server.services.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class ReadingController {

    @Autowired
    private ReadingRepository readingRepository;
    private SensorRepository sensorRepository;
    private List<Reading> readings;

    public ReadingController (ReadingRepository readingRepository, SensorRepository sensorRepository) throws FileNotFoundException {
        this.readingRepository = readingRepository;
        this.sensorRepository = sensorRepository;
        readings = new CsvToBeanBuilder<Reading>(new FileReader("readings.csv"))
            .withType(Reading.class).build().parse();
    }

  // TODO 4.3  Spremanje očitanja pojedinog senzora
    @PostMapping("sensor/{id}/reading")
    public ResponseEntity<Reading> addSensorReading (@PathVariable Long id, @RequestBody ReadingDTO readingDTO) {
        Reading reading = new Reading();
        reading.setTemperature(readingDTO.getTemperature());
        reading.setHumidity(readingDTO.getHumidity());
        reading.setPressure(readingDTO.getPressure());
        reading.setCo(readingDTO.getCo());
        reading.setNo2(readingDTO.getNo2());
        reading.setSo2(readingDTO.getSo2());
        Optional<Sensor> optSensor = sensorRepository.findById(id);
        if (optSensor.isPresent()) {
            reading.setSensor(optSensor.get());
            readingRepository.save(reading);
            String locationUrl = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(reading.getReadingId())
                .toUriString();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.LOCATION, locationUrl);
            return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(reading);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

  // TODO 4.5  Popis očitanja pojedinog senzora
    @GetMapping("/sensor/{id}")
    public ResponseEntity<Set<Reading>> getSensorReadings(@PathVariable Long id) {
        Sensor sensor = sensorRepository.findById(id).orElse(null);
        if(sensor != null) {
            Set<Reading> readings = sensor.getReadings();
            for (Reading r : readings) System.out.println(r);
            return ResponseEntity.status(HttpStatus.OK).body(readings);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

    @GetMapping("/readings")
    public List<Reading> getAllReadings() {
        return readings;
    }


}
