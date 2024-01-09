package hr.fer.tel.rassus.server;

import com.opencsv.bean.CsvToBeanBuilder;
import hr.fer.tel.rassus.server.beans.Reading;
import hr.fer.tel.rassus.server.services.ReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileReader;
import java.util.List;


@SpringBootApplication
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}



