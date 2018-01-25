package smartlab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import smartlab.model.SensorData;
import smartlab.repository.SensorDataRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
public class SensorController {

    @Autowired
    SensorDataRepository sensorDataRepository;

    @GetMapping("/internalTemperature")
    public Double internalTemperature(){
        SensorData sensor = sensorDataRepository.findTopByIdSensorOrderByTimeDesc("tmp1");
        return Double.valueOf(sensor.getValue());
    }

    @GetMapping("/externalTemperature")
    public Double externalTemperature(){
        SensorData sensor = sensorDataRepository.findTopByIdSensorOrderByTimeDesc("tmp2");
        return Double.valueOf(sensor.getValue());
    }

    @GetMapping("/sensor/{id}")
    public SensorData getActualSensorValue(@PathVariable("id") String id){
        return sensorDataRepository.findTopByIdSensorOrderByTimeDesc(id);
    }

    @GetMapping("/sensor/{id}/values")
    public List<SensorData> getAllSensorValue(String id){
        return sensorDataRepository.findByIdSensorOrderByTimeDesc(id);
    }
    @GetMapping("/sensor/{id}/values/date")
    public List<SensorData> getAllSensorValue(@RequestParam("startDate")  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dataInicial,
                                              @RequestParam("endDate")  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dataFinal,
                                              @PathVariable("id") String id){
        return sensorDataRepository.findByIdSensorAndTimeBetween(id, dataInicial, dataFinal);
    }

}
