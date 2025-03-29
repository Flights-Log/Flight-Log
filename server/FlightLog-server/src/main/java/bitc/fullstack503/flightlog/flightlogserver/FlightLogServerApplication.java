package bitc.fullstack503.flightlog.flightlogserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("bitc.fullstack503.flightlog.flightlogserver.mapper")
@SpringBootApplication
public class FlightLogServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(FlightLogServerApplication.class, args);
  }

}
