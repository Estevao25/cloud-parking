package one.digitalinnovation.parking.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import one.digitalinnovation.parking.exception.ParkingNotFoundException;
import one.digitalinnovation.parking.model.Parking;

@Service
public class ParkingService {

	private static Map<String, Parking> parkingMap = new HashMap<>();
	
	static {
		var id = getUUID();
		var id1 = getUUID();
		Parking parking = new Parking(id, "MSS-1111", "SC", "CELTA", "PRETO");
		parking.setEntryDate(LocalDateTime.now());
		Parking parking1 = new Parking(id1, "WAS-1234", "SP", "VW GOL", "VERMELHO");
		parking1.setEntryDate(LocalDateTime.now());
		parkingMap.put(id, parking);
		parkingMap.put(id1, parking1);
	}
	
	public List<Parking> findAll() {
		return parkingMap.values().stream().collect(Collectors.toList());
	}
	
	private static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public Parking findById(String id) {
		Parking parking = parkingMap.get(id);
		if (parking == null) {
			throw new ParkingNotFoundException(id);
		}
		return parking;
	}

	public Parking create(Parking parkingCreate) {
		String uuid = getUUID();
		parkingCreate.setId(uuid);
		parkingCreate.setEntryDate(LocalDateTime.now());
		parkingMap.put(uuid, parkingCreate);
		return parkingCreate;
		
	}

	public void delete(String id) {
		findById(id);
		parkingMap.remove(id);
		
	}

	public Parking update(String id, Parking parkingUpdate) {
		Parking parking = findById(id);
		parking.setColor(parkingUpdate.getColor());
		parkingMap.replace(id, parking);
		return parking;
	}
	
	public Parking exit(String id) {
		Parking parking = findById(id);
		parking.setExitDate(LocalDateTime.now());
		parkingMap.replace(id, parking);
		
		Integer horaInicio = parking.getEntryDate().getHour();
		Integer horaFim = parking.getExitDate().getHour();;
		
		Double bill = ((Double.valueOf(horaFim) - Double.valueOf(horaInicio)) + 5) * 4.7;
		
		parking.setBill(bill);
		
		return parking;
	}
}
