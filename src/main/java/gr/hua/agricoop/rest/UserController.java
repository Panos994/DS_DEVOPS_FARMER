package gr.hua.agricoop.rest;


import gr.hua.agricoop.entity.Cooperative;
import gr.hua.agricoop.entity.User;
import gr.hua.agricoop.entity.role;
import gr.hua.agricoop.repository.RoleRepository;
import gr.hua.agricoop.repository.UserRepository;
import gr.hua.agricoop.service.CooperativeService;
import gr.hua.agricoop.service.UserService;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    private CooperativeService cooperativeService;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RoleRepository roleRepository;
    //OK
    @Secured("ROLE_ADMIN")
    @GetMapping("")
    public List<User> showUsers() {
        return userService.getUsers();
    }
    //OK

    @PostMapping("/new/add")
    public User addUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    //OK
    @Secured("ROLE_ADMIN")
    @PutMapping("{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody User user) {
        return userService.updateUser(userId, user);
    }
    //OK
    @PostMapping("/new")
    public List<User> saveUser(@RequestBody User user) {
        userService.saveUser(user);
        List<User> users = userService.getUsers();
        for (User a : users) {
            a.addUser(user);
        }
        return userService.getUsers();
    }

    //OK
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/admin/{user_id}")
    public List<User> deleteUser(@PathVariable Long user_id) {
        userService.deleteUser(user_id);
        return userService.getUsers();
    }



    //OK
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/{userId}")
    public User getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }


























    // OK
    @Secured("ROLE_MODERATOR")
    @GetMapping("/employee/{user_id}/processed_applications")
    public List<Cooperative> getProcessedApplications(@PathVariable Long user_id) {
        User user = userRepository.findById(user_id).orElseThrow();
        return user.getApplications();
    }

    // OK
    @Secured("ROLE_MODERATOR")
    @GetMapping("/employee/{user_id}/unprocessed_applications")
    public List<Cooperative> getUnprocessedApplications(@PathVariable Long user_id) {
        return cooperativeService.getUnprocessedApplications();
    }


    // OK with indicating that the "cooperative is checked"
    @Secured("ROLE_MODERATOR")
    @GetMapping("/employee/{user_id}/{cooperative_id}/check")
    public String checkApplication(@PathVariable Long user_id, @PathVariable Integer cooperative_id) {
        // You can return a message or a specific view for checking applications
        return "Check application for employee_id: " + user_id + ", cooperative_id: " + cooperative_id;
    }

    // OK but I can only approve them
    @Secured("ROLE_MODERATOR")
    @PostMapping("/employee/{user_id}/{cooperative_id}/check/submit")
    public List<Cooperative> submitApplicationCheck(
            @PathVariable Long user_id,
            @PathVariable Integer cooperative_id,
            @RequestBody Map<String, String> requestBody
    ) {
        String notes = requestBody.get("notes");
        cooperativeService.approveApplication(cooperative_id, user_id, notes);
        return cooperativeService.getProcessedApplications(user_id);
    }



 //ok
@Secured("ROLE_ADMIN")
@PostMapping("/admin/role/delete/{user_id}/{role_id}")
    public ResponseEntity<?> deleteRoleFromUser(@PathVariable Long user_id, @PathVariable Integer role_id) {
        User user = userService.getUser(user_id);
        role role = roleRepository.findById(role_id).orElse(null);

        if (user == null || role == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Role not found");
        }

        user.getRoles().remove(role);
        userService.updateUser(user_id, user);

        return ResponseEntity.ok(user);  // Return the updated user as JSON
    }

    //ok
    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/role/add/{user_id}/{role_id}")
    public ResponseEntity<?> addRoleToUser(@PathVariable Long user_id, @PathVariable Integer role_id) {
        User user = userService.getUser(user_id);
        role role = roleRepository.findById(role_id).orElse(null);

        if (user == null || role == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Role not found");
        }

        user.getRoles().add(role);
        userService.updateUser(user_id, user);

        return ResponseEntity.ok(user);  // Return the updated user as JSON
    }
}






















/*

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/register")
    public String register(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user_registration";
    }







    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user, Model model){
        System.out.println("Roles: "+user.getRoles());
        Integer id = userService.saveUser(user);
        String message = "User '"+id+"' saved successfully !";
        model.addAttribute("msg", message);
        return "home";
    }

    @GetMapping("/users")
    public String showUsers(Model model){
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "users";
    }

    @GetMapping("/user/{user_id}")
    public String showUser(@PathVariable Long user_id, Model model){
        model.addAttribute("user", userService.getUser(user_id));
        return "edit_user";
    }

    @PostMapping("/user/{user_id}")
    public String saveFarmer(@PathVariable Long user_id, @ModelAttribute("user") User user, Model model) {
        User the_user = (User) userService.getUser(user_id);
        the_user.setEmail(user.getEmail());
        the_user.setUsername(user.getUsername());
        userService.saveUser(the_user);
        model.addAttribute("users", userService.getUsers());
        return "users";
    }

    @GetMapping("/user/role/delete/{user_id}/{role_id}")
    public String deleteRolefromUser(@PathVariable Long user_id, @PathVariable Integer role_id, Model model){
        User user = (User) userService.getUser(user_id);
        role role = roleRepository.findById(role_id).get();
        user.getRoles().remove(role);
        System.out.println("Roles: "+user.getRoles());
        userService.updateUser(user);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "users";

    }

    @GetMapping("/user/role/add/{user_id}/{role_id}")
    public String addRoletoUser(@PathVariable Long user_id, @PathVariable Integer role_id, Model model){
        User user = (User) userService.getUser(user_id);
        role role = roleRepository.findById(role_id).get();
        user.getRoles().add(role);
        System.out.println("Roles: "+user.getRoles());
        userService.updateUser(user);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "users";

    }
    */