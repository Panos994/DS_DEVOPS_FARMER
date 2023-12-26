package gr.hua.agricoop.rest;

import gr.hua.agricoop.entity.CultivationLocation;
import gr.hua.agricoop.service.CultivationLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cultivation_location")
public class CultivationLocationRestController {

    @Autowired
    private CultivationLocationService cultivationLocationService;

    //ok
    @GetMapping("")
    public List<CultivationLocation> showCultivationLocations() {
        return cultivationLocationService.getCultivationLocations();
    }
    //ok
    @GetMapping("/new")
    public CultivationLocation addCultivationLocation() {
        return new CultivationLocation();
    }

/*
    //not ok
    @PutMapping("{cultivation_location_id}")
    public CultivationLocation editCultivationLocation(@PathVariable Integer cultivation_location_id) {
        return cultivationLocationService.getCultivationLocation(cultivation_location_id);
    }
*/
    //ok
    @PutMapping("/{cultivation_location_id}")
    public ResponseEntity<CultivationLocation> editCultivationLocation(
            @PathVariable Integer cultivation_location_id,
            @RequestBody CultivationLocation updatedCultivationLocation) {
        CultivationLocation result = cultivationLocationService.editCultivationLocation(cultivation_location_id, updatedCultivationLocation);
        return ResponseEntity.ok(result);
    }






    //ok
    @PostMapping("/new")
    public List<CultivationLocation> saveCultivationLocation(@RequestBody CultivationLocation cultivationLocation) {
        cultivationLocationService.saveCultivationLocation(cultivationLocation);
        return cultivationLocationService.getCultivationLocations();
    }


    //ok
    @DeleteMapping("{cultivation_location_id}")
    public List<CultivationLocation> deleteCultivationLocation(@PathVariable Integer cultivation_location_id) {
        cultivationLocationService.deleteCultivationLocation(cultivation_location_id);
        return cultivationLocationService.getCultivationLocations();
    }
}
