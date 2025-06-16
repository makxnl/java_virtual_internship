package org.javaguru.travel.insurance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

//@SpringBootApplication
public class InsuranceApplication {

    public static void main(String[] args) throws IOException {

//        SpringApplication.run(InsuranceApplication.class, args);
//    }
        JsonFileReader jsonFileReader = new JsonFileReader();
        jsonFileReader.readJsonFromFile("RequestSuccses.json");
    }

}
