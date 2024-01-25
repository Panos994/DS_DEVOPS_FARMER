package gr.hua.agricoop.rest;

import gr.hua.agricoop.entity.Cooperative;
import gr.hua.agricoop.entity.User;
import gr.hua.agricoop.service.*;
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



    //OK -- oxi akrivos
    //εξηγηση τι συμβαινει που εψαξα:
    //Οταν μπαινει μετα απο καποιες μεθοδους το cooperative_id στo εξής μόνο table : product
    // τοτε δεν δουλευει ! Ενώ ας πούμε στο cultivation location που το βαζω μετά απο μεθόδους
    //δουλεύειn


    @Secured("ROLE_MODERATOR")
    @GetMapping("")
    public List<Cooperative> showCooperatives() {


        return cooperativeService.getCooperatives();
    }




    //OK
    @Secured("ROLE_MODERATOR")
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
    //@Secured("ROLE_MODERATOR")
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



    //OK ignore-- δεν νομιζω οτι χρειαζεται
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

    //@Secured("ROLE_USER")
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
    @Secured("ROLE_MODERATOR")
    @DeleteMapping("{cooperative_id}")
    public List<Cooperative> deleteCooperative(@PathVariable Integer cooperative_id) {
        cooperativeService.deleteCooperative(cooperative_id);
        return cooperativeService.getCooperatives();
    }





 //-----------------------------------------------------------------












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
    //den xreiazetai ignore paidia
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
    // ignore den xreiazetai paidia
    @GetMapping("{cooperative_id}/users")
    public List<User> getCooperativeUser(@PathVariable Integer cooperative_id) {
        return cooperativeService.getCooperative(cooperative_id).getUsers();
    }





    //θα βγει λογικά και επίσης θα βγει και το getCooperativeProducts και έφτιαξα στον Cooperative
    //entity 2 τιμές product_name, product_category για να πληκτρολογεί απλά ο χρήστης το όνομα
    //προιόντος και κατηγορία προιόντος

    //NOT OK -- eno fainetai na iparxoyn sto table (to cooperative_id μετα απο την χρηση
    //μεθοδου addProduct δεν μου φερνει τα προιοντα του)








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


























    //

    @GetMapping("/user/{user_id}/cooperatives")
    public ResponseEntity<List<Cooperative>> getCooperativesForUser(@PathVariable Long user_id) {
        List<Cooperative> cooperatives = cooperativeService.getCooperativesForUser(user_id);

        if (!cooperatives.isEmpty()) {
            return ResponseEntity.ok(cooperatives);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{user_id}/cooperatives/details")
    public ResponseEntity<List<Cooperative>> getCooperativesDetailsForUser(@PathVariable Long user_id) {
        List<Cooperative> cooperatives = cooperativeService.getCooperativesDetailsForUser(user_id);

        if (!cooperatives.isEmpty()) {
            return ResponseEntity.ok(cooperatives);
        } else {
            return ResponseEntity.notFound().build();
        }
    }











}



