package gr.hua.agricoop.service;

import gr.hua.agricoop.entity.CultivationLocation;
import gr.hua.agricoop.repository.CultivationLocationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class CultivationLocationService {

    @Autowired
    private CultivationLocationRepository cultivationLocationRepository;

    @Transactional
    public List<CultivationLocation> getCultivationLocations() {
        return cultivationLocationRepository.findAll();
    }
    @Transactional
    public CultivationLocation editCultivationLocation(Integer cultivationLocationId, CultivationLocation updatedCultivationLocation) {
        // Check if the cultivation location with the given ID exists
        CultivationLocation existingCultivationLocation = cultivationLocationRepository.findById(cultivationLocationId)
                .orElseThrow(() -> new ResourceNotFoundException("CultivationLocation not found with id: " + cultivationLocationId));

        // Update the existing cultivation location with the new data
        existingCultivationLocation.setAddress(updatedCultivationLocation.getAddress());
        existingCultivationLocation.setArea(updatedCultivationLocation.getArea());
        existingCultivationLocation.setZipCode(updatedCultivationLocation.getZipCode());
        // Add other fields as needed

        // Save and return the updated cultivation location
        return cultivationLocationRepository.save(existingCultivationLocation);
    }






    @Transactional
    public CultivationLocation saveCultivationLocation(CultivationLocation cultivationLocation) {
        cultivationLocationRepository.save(cultivationLocation);
        return cultivationLocation;
    }

    @Transactional
    public void deleteCultivationLocation(Integer cultivationLocationId) {
        cultivationLocationRepository.deleteById(cultivationLocationId);
    }




    @Transactional
    public CultivationLocation getCultivationLocation(Integer cultivationLocationId) {
        return cultivationLocationRepository.findById(cultivationLocationId).get();
    }







    @Transactional
    public List<CultivationLocation> getCultivationLocationsWithoutCooperative() {
        List<CultivationLocation> cultivationLocations = cultivationLocationRepository.findAll();
        cultivationLocations.removeIf(cultivationLocation -> cultivationLocation.getCooperative() != null);
        return cultivationLocations;
    }
}
