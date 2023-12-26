package gr.hua.agricoop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cooperative")
public class Cooperative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String name;

    @Column
    private String vat;

    private Status estatus;

    @Column
    private String status;

    @Column
    private String notes;
    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;



    //auto eixa
    /*
    @OneToMany(mappedBy = "cooperative", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<User> users;

*/
    @JsonIgnore
    @OneToMany(mappedBy = "cooperative", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<User> users;







    public void setUsers(List<User> users) {
        this.users = users;
    }


    @OneToMany(mappedBy = "cooperative", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Product> products;
    @JsonIgnore
    @OneToMany(mappedBy = "cooperative", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<CultivationLocation> cultivationLocations;

    public Cooperative() {
        this.estatus = Status.PROCESSING;
        this.status = "Processing";
        this.notes = "";
    }

    public Cooperative(String name, String vat) {
        this();
        this.name = name;
        this.vat = vat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public Status getEstatus() {
        return estatus;
    }

    public void setEstatus(Status estatus) {
        this.estatus = estatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }











    public List<User> getUsers() {
        return users;
    }

    public void setFarmers(List<User> users) {
        this.users = users;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<CultivationLocation> getCultivationLocations() {
        return cultivationLocations;
    }

    public void setCultivationLocations(List<CultivationLocation> cultivationLocations) {
        this.cultivationLocations = cultivationLocations;
    }



    //audo eixaaa
    /*
    public void addUser(User user) {
        users.add(user);
    }
*/

    public void addUser(User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
        user.setCooperative(this); // Bidirectional association
    }


    public void removeFarmer(User user) {
        users.remove(user);
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public void addCultivationLocation(CultivationLocation cultivationLocation) {
        cultivationLocations.add(cultivationLocation);
    }

    public void removeCultivationLocation(CultivationLocation cultivationLocation) {
        cultivationLocations.remove(cultivationLocation);
    }

    public String check() {
        if (estatus == Status.APPROVED) {
            return "Application already approved.";
        } else if (estatus == Status.REJECTED) {
            return "Application already rejected.";
        }
        if (!(users.isEmpty() || products.isEmpty() || cultivationLocations.isEmpty()) && vatIsValid()) {
            return "Application is valid.";
        } else {
            String checkResult = "Application has:\n";
            if (users.isEmpty()) {
                checkResult += "\t- Less than 1 member\n";
            }
            if (products.isEmpty()) {
                checkResult += "\t- Less than 1 product\n";
            }
            if (cultivationLocations.isEmpty()) {
                checkResult += "\t- Less than 1 cultivation location\n";
            }
            if (!vatIsValid()) {
                checkResult += "\t- Invalid VAT number";
            }
            return checkResult;
        }
    }

    private boolean vatIsValid() {
        if (vat.length() == 9) {
            try {
                Integer.parseInt(vat);
            } catch (NumberFormatException error) {
                return false;
            }

            int sum = 0;
            int product;
            int lastDigit = Character.getNumericValue(vat.charAt(8));

            for (int i = 0; i < 8; i++) {
                product = Character.getNumericValue(vat.charAt(i));
                product *= (int) Math.pow(2, 8 - i);
                sum += product;
            }

            if ((sum % 11) % 10 == lastDigit && !vat.equals("000000000")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @PreRemove
    private void preRemove() {
        for (User user : users) {
            user.setCooperative(null);
        }
        for (Product product : products) {
            product.setCooperative(null);
        }
        for (CultivationLocation cultivationLocation : cultivationLocations) {
            cultivationLocation.setCooperative(null);
        }
    }




    @Override
    public String toString() {
        return "Cooperative{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", vat='" + vat + '\'' +
                ", status=" + estatus +
                '}';
    }
}
