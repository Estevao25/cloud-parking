package one.digitalinnovation.parking.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import one.digitalinnovation.parking.exception.ParkingNotFoundException;
import one.digitalinnovation.parking.model.Parking;
import one.digitalinnovation.parking.repository.ParkingRepository;

@Service
public class ParkingService {

	private final ParkingRepository parkingRepository;

	public ParkingService(ParkingRepository parkingRepository) {
		this.parkingRepository = parkingRepository;
	}

	public List<Parking> findAll() {
		return parkingRepository.findAll();
	}
	
	private static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public Parking findById(String id) {
		return parkingRepository.findById(id).orElseThrow(() -> 
			new ParkingNotFoundException(id));
	}

	public Parking create(Parking parkingCreate) {
		String uuid = getUUID();
		parkingCreate.setId(uuid);
		parkingCreate.setEntryDate(LocalDateTime.now());
		parkingRepository.save(parkingCreate);
		return parkingCreate;
		
	}

	public void delete(String id) {
		findById(id);
		parkingRepository.deleteById(id);;
		
	}

	public Parking update(String id, Parking parkingUpdate) {
		Parking parking = findById(id);
		parking.setColor(parkingUpdate.getColor());
		parking.setState(parkingUpdate.getState());
		parking.setModel(parkingUpdate.getModel());
		parking.setLicense(parkingUpdate.getLicense());
		
		parkingRepository.save(parking);
		return parking;
	}
	
	public Parking exit(String id) {
		Parking parking = findById(id);
		parking.setExitDate(LocalDateTime.now());
		
		Integer horaInicio = parking.getEntryDate().getHour();
		Integer horaFim = parking.getExitDate().getHour();;
		
		Double bill = ((Double.valueOf(horaFim) - Double.valueOf(horaInicio)) + 5) * 4.7;
		
		parking.setBill(bill);
		parkingRepository.save(parking);
		
		return parking;
	}
}
