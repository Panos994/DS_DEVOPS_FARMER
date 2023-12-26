package gr.hua.agricoop.rest;

import gr.hua.agricoop.entity.Cooperative;
import gr.hua.agricoop.entity.CultivationLocation;
import gr.hua.agricoop.entity.Product;
import gr.hua.agricoop.entity.User;
import gr.hua.agricoop.service.*;
import jakarta.transaction.Transactional;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cooperative")
public class CooperativeRestController {

    @Autowired
    private CooperativeService cooperativeService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CultivationLocationService cultivationLocationService;

    //OK -- Vasika an gemizei to user_id gia kapoion logo sto Table cooperative me addUser as poume
    //den mou tous emfanizei

    //Οταν μπαινει μετα απο καποιες μεθοδους το cooperative_id στα εξής μόνο tables : product και
    //users τοτε δεν δουλευει ! Ενώ ας πούμε στο cultivation location που το βαζω μετά απο μεθόδους
    //δουλεύει
    @Secured("ROLE_MODERATOR")
    @GetMapping("")
    public List<Cooperative> showCooperatives() {


        return cooperativeService.getCooperatives();
    }




    //OK
    @Secured("ROLE_USER")
    @GetMapping("{cooperative_id}")
    public Cooperative getCooperative(@PathVariable Integer cooperative_id) {

        return cooperativeService.getCooperative(cooperative_id);
    }



    //OK
    @Secured("ROLE_USER")
    @GetMapping("/new")
    public Cooperative addCooperative() {
        return new Cooperative();
    }


    /*
    @PostMapping("{cooperative_id}")
    public Cooperative editCooperative(@PathVariable Integer cooperative_id) {
        return cooperativeService.getCooperative(cooperative_id);
    }

     */
    //OK
    @Secured("ROLE_USER")
    @PutMapping("{cooperative_id}")
    public Cooperative editCooperative(@PathVariable Integer cooperative_id, @RequestBody Cooperative updatedCooperative) {
        Cooperative existingCooperative = cooperativeService.getCooperative(cooperative_id);

        if (existingCooperative != null) {
            // Update the properties of the existing cooperative with the new values
            existingCooperative.setName(updatedCooperative.getName());
            existingCooperative.setVat(updatedCooperative.getVat());
            // Update other properties as needed

            cooperativeService.saveCooperative(existingCooperative);
            return existingCooperative;
        } else {
            // Handle the case where the cooperative with the given ID is not found
            return null; // or throw an exception, return a response with a 404 status, etc.
        }
    }



    //OK -- δεν νομιζω οτι χρειαζεται
    @PostMapping("/temp")
    public String saveCooperative(@RequestBody Cooperative cooperative)  {
        cooperativeService.saveCooperative(cooperative);
        return "temp";
    }

    /*
//edo
    //OK (edo mallon that valoume ROLE_USER)
    @PostMapping("/new")
    public List<Cooperative> saveCooperative(@RequestBody Cooperative cooperative, Model model) {
        cooperativeService.saveCooperative(cooperative);
        return cooperativeService.getCooperatives();
    }

*/

    @Secured("ROLE_USER")
    @PostMapping("/new")
    public List<Cooperative> saveCooperative(@RequestBody Cooperative cooperative, Model model) {
        // Retrieve the current user from the security context
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Retrieve the user by username using the new method
        User currentUser = userService.getUserByUsername(userDetails.getUsername());

        // Set the user for the cooperative
        cooperative.setUser(currentUser);

        // Save the cooperative
        cooperativeService.saveCooperative(cooperative);

        // Return the list of cooperatives
        return cooperativeService.getCooperatives();
    }
























    //OK
    @Secured("ROLE_USER")
    @DeleteMapping("{cooperative_id}")
    public List<Cooperative> deleteCooperative(@PathVariable Integer cooperative_id) {
        cooperativeService.deleteCooperative(cooperative_id);
        return cooperativeService.getCooperatives();
    }



/* αυτο ειχα πριν και αυτο λογικα δουλευει
    //OK
    @GetMapping("/user/{user_id}")
    public Cooperative getUserCooperative(@PathVariable Long user_id) {
        return cooperativeService.getUserCooperative(user_id);
    }
*/


    //OK -- πρωτα χρησιμοποιεις την add_user πιο κατω restController method ή επισης εφτιαξα
    //αυτοματα αν κανει ενας με ROLE_USER sign in και κανει saveCooperative καταχωρειτε το user_id
    //στο table του cooperative
    @GetMapping("/user/{user_id}")
    public ResponseEntity<Cooperative> getUserCooperative(@PathVariable Long user_id) {
        Cooperative cooperative = cooperativeService.getUserCooperative(user_id);

        if (cooperative != null) {
            return ResponseEntity.ok(cooperative);
        } else {
            return ResponseEntity.notFound().build();
        }
    }






    //ΝΟΤ ΟΚ -- να την βγαλω ? Την χρειαζόμαστε αφού θα το βλέπουμε μέσω του User που εχει κανει αιτηση (με ROLE_USER δικαιωμα εν ολιγοις ο farmer εννοειται)
    // δηλαδη απο τη ακριβως πιο πανω μεθοδο
    //δεν ειμαι 100% σιγουρος θελει δουλεια --ειναι 200 αλλα επιστρεφει κενο αποτελεσμα
    @GetMapping("{cooperative_id}/users")
    public List<User> getCooperativeUser(@PathVariable Integer cooperative_id) {
        return cooperativeService.getCooperative(cooperative_id).getUsers();
    }







    //NOT OK -- eno fainetai na iparxoyn sto table (to cooperative_id μετα απο την χρηση
    //μεθοδου addProduct δεν μου φερνει τα προιοντα του)
    @GetMapping("{cooperative_id}/products")
    public List<Product> getCooperativeProducts(@PathVariable Integer cooperative_id) {
        return cooperativeService.getCooperative(cooperative_id).getProducts();
    }




    //OK
    @GetMapping("{cooperative_id}/cultivation_locations")
    public List<CultivationLocation> getCooperativeCultivationLocations(@PathVariable Integer cooperative_id) {
        return cooperativeService.getCooperative(cooperative_id).getCultivationLocations();
    }







    //OK
    @PostMapping("/users/{cooperative_id}/{user_id}")
    public List<User> addUser(@PathVariable Integer cooperative_id, @PathVariable Long user_id,Model model) {
        Cooperative cooperative = cooperativeService.getCooperative(cooperative_id);
        User user = userService.getUser(user_id);
        cooperative.addUser(user);
        cooperativeService.saveCooperative(cooperative);
        user.setCooperative(cooperative);
        userService.saveUser(user);
        model.addAttribute((cooperative));
        model.addAttribute((userService.getUserWithoutCooperative()));
        return cooperative.getUsers();
    }




    //OK MALLON -- stin database apothikeuetai kanonika to cooperative_id sto table Products
    //apla sto postman βγαζει αυτο :
    //POST http://localhost:9090/api/cooperative/api/products/8/5
    //Error: aborted
    //Request Headers
    //Content-Type: application/json
    //Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJleGFtcGwxMSIsImlhdCI6MTcwMzU5Nzc3OSwiZXhwIjoxNzAzNjg0MTc5fQ.VPEnajIBFY-xJ8bF2ftTlBGPwktY9mfAzxxNsskPEJX5uKUf7zJN6gjySxqRhlDek1mP839YEjVMUzjRR620iw
    //User-Agent: PostmanRuntime/7.36.0
    //Accept: */*
    //Postman-Token: eb2e3ae6-14f4-482f-890f-764f2fe1d4a3
    //Host: localhost:9090
    //Accept-Encoding: gzip, deflate, br
    //Connection: keep-alive
    //Content-Length: 30
    //Request Body

    @PostMapping("/api/products/{cooperative_id}/{product_id}")
    public List<Product> addProduct(@PathVariable Integer cooperative_id, @PathVariable Integer product_id, Model model) {
        Cooperative cooperative = cooperativeService.getCooperative(cooperative_id);
        Product product = productService.getProduct(product_id);
        cooperative.addProduct(product);
        cooperativeService.saveCooperative(cooperative);
        product.setCooperative(cooperative);
        productService.saveProduct(product);
        model.addAttribute(cooperative);
        model.addAttribute(productService.getProductsWithoutCooperative());
        return cooperative.getProducts();
    }






    //ok

    @PostMapping("/api/cultivation_locations/{cooperative_id}/{cultivation_location_id}")
    public List<CultivationLocation> addCultivationLocation(@PathVariable Integer cooperative_id, @PathVariable Integer cultivation_location_id,Model model) {
        Cooperative cooperative = cooperativeService.getCooperative(cooperative_id);
        CultivationLocation cultivationLocation = cultivationLocationService.getCultivationLocation(cultivation_location_id);
        cooperative.addCultivationLocation(cultivationLocation);
        cooperativeService.saveCooperative(cooperative);
        cultivationLocation.setCooperative(cooperative);
        cultivationLocationService.saveCultivationLocation(cultivationLocation);
        model.addAttribute(cooperative);
        model.addAttribute(cultivationLocationService.getCultivationLocationsWithoutCooperative());
        return cooperative.getCultivationLocations();
    }
}



