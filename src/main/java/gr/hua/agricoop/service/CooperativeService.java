package gr.hua.agricoop.service;

import gr.hua.agricoop.entity.Cooperative;
import gr.hua.agricoop.entity.Status;
import gr.hua.agricoop.entity.User;
import gr.hua.agricoop.repository.CooperativeRepository;
import gr.hua.agricoop.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class CooperativeService {

    @Autowired
    private CooperativeRepository cooperativeRepository;

    @Autowired
    UserRepository userRepository;



    @Secured("ROLE_USER")
    @Transactional
    public List<Cooperative> getCooperatives() {
        return cooperativeRepository.findAll();
    }



    @Transactional
    public void saveCooperative(Cooperative cooperative) {
        cooperativeRepository.save(cooperative);

    }







    @Transactional
    public void deleteCooperative(Integer cooperativeId) {
        cooperativeRepository.deleteById(cooperativeId);
    }

    @Transactional
    public Cooperative getCooperative(Integer cooperativeId) {
        return cooperativeRepository.findById(cooperativeId).get();
    }


/*
    @Transactional
    public Cooperative getUserCooperative(Long userId) {
        User user = userRepository.findById(userId).get();
        return user.getCooperative();
    }
*/
    @Transactional
    public Cooperative getUserCooperative(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return user.getCooperative();
    }












    @Transactional
    public List<Cooperative> getProcessedApplications(Long userId) {
        User user = userRepository.findById(userId).get();
        return user.getApplications();
    }

    @Transactional
    public List<Cooperative> getUnprocessedApplications() {
        List<Cooperative> cooperatives = cooperativeRepository.findAll();
        cooperatives.removeIf(cooperative -> cooperative.getEstatus() != Status.PROCESSING);
        return cooperatives;
    }

    @Transactional
    public void approveApplication(Integer cooperativeId, Long userId, String notes) {
        Cooperative cooperative = cooperativeRepository.findById(cooperativeId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        cooperative.setUser(user);
        cooperative.setNotes(notes);
        cooperative.setEstatus(Status.APPROVED);
        cooperativeRepository.save(cooperative);
        user.approveApplication(cooperative);
        userRepository.save(user);
    }
























    @Transactional
    public void rejectApplication(Integer cooperativeId, Long userId, String notes) {
        Cooperative cooperative = cooperativeRepository.findById(cooperativeId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        cooperative.setUser(user);
        cooperative.setNotes(notes);
        cooperative.setEstatus(Status.REJECTED);
        cooperativeRepository.save(cooperative);
        user.rejectApplication(cooperative);
        userRepository.save(user);
    }
}
