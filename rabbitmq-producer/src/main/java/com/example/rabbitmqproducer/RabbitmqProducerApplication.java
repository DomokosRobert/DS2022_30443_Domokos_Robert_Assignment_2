package com.example.rabbitmqproducer;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.Local;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Scanner;

@SpringBootApplication
public class RabbitmqProducerApplication {

	@Autowired
	private MessagePublisher message;


	public static void main(String[] args) {

		SpringApplication.run(RabbitmqProducerApplication.class, args);
	}

	public void readFromCsv() throws FileNotFoundException, InterruptedException {
		Random random = new Random();
		Long id = random.nextLong(10)+1;
		Double initial = 0.0;
		Scanner sc = new Scanner(new File("C:\\Users\\robib\\Desktop\\Facultate\\An4\\Sem1\\DS\\A2\\sensor.csv"));
		sc.useDelimiter("\n");
		while(sc.hasNext()){
			Thread.sleep(5000);
			Double value = Double.parseDouble(sc.next());
			Double hourlyValue = value - initial;
			initial = value;
			CustomMessage msg = new CustomMessage(Timestamp.valueOf(LocalDateTime.now()).getTime(),id,hourlyValue);
			message.publishMessage(msg);
			System.out.println("Message sent: " + msg);
		}
	}
	@PostConstruct
	public void init() throws Exception{
		readFromCsv();
	}

}
