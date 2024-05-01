<template>
  <div class="container">
    <h2>Fill in the Form</h2>
    <div class="cooperative-form">

      <form @submit.prevent="saveCooperative">

        <div class="form-group">
          <label for="name">Name:</label>
          <input v-model="cooperative.name" type="text" id="name" required>
        </div>

        <div class="form-group">
          <label for="vat">VAT:</label>
          <input v-model="cooperative.vat" type="text" id="vat" required>
        </div>


        <div class="form-group">
          <label for="product_name">Product Name:</label>
          <input v-model="cooperative.product_name" type="text" id="product_name" required>
        </div>



        <div class="form-group">
          <label for="product_category">Product Category:</label>
          <select v-model="cooperative.product_category" id="product_category" required>
            <option value="vegetables">Vegetables</option>
            <option value="fruits">Fruits</option>
            <option value="meat">Meat</option>
            <option value="milk">Milk</option>
            <option value="chocolate">Chocolate</option>
            <option value="honey">Honey</option>
            <option value="bread">Bread</option>
            <option value="olive">Olive</option>
          </select>
        </div>

        <div class="form-group">
          <label for="cultivation_city">City:</label>
          <input v-model="cooperative.cultivation_city" type="text" id="cultivation_city" required>
        </div>

        <div class="form-group">
          <label for="cultivation_address">Address:</label>
          <input v-model="cooperative.cultivation_address" type="text" id="cultivation_address" required>
        </div>



        <div class="form-group">
          <label for="cultivation_street_number">Street Number:</label>
          <input v-model="cooperative.cultivation_street_number" type="text" id="cultivation_street_number" required>
        </div>

        <div class="form-group">
          <label for="members">Members:</label>
          <select v-model="cooperative.members" id="product_name" required>
            <option value="1-5">1-5</option>
            <option value="5-10">5-10</option>
            <option value="10-20">10-20</option>
            <option value="20-40">20-40</option>
            <option value="40+">40+</option>
          </select>
        </div>





        <button class="saveButton" type="submit">Save</button>
      </form>

      <strong>
        <div v-if="submissionCompleted" class="success-message">Submission Completed</div>
      </strong>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      cooperative: {
        name: '',
        vat: '',
        product_name: '',
        // Add other properties as needed
      },
      submissionCompleted: false,
    };
  },

  methods: {
    saveCooperative() {
      const accessToken = localStorage.getItem('jwt');

      axios.post('http://localhost:9090/api/cooperative/new', this.cooperative, {
        headers: {
          'Authorization': 'Bearer ' + accessToken,
          'Content-Type': 'application/json',
        },
      })
        .then(response => {
          console.log('Cooperative saved successfully', response.data);
          this.submissionCompleted = true;
        })
        .catch(error => {
          console.error('Error saving cooperative', error);
        });
    },


  },
};
</script>


<style scoped>
* {
  margin: 0;
  padding: 0;
}

.container {
  font-family: 'Inter', sans-serif;
  width: 600px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -55%);
  border: 1px solid #ddd;
  border-radius: 10px;
}

.container>h2 {
  padding: 10px;
  color: #fff;
  background-color: #41b883;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
}

.cooperative-form {
  padding: 20px;
  background-color: #f8f9fa;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
}

.main-header {
  background-color: #f8f9fa;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  padding: 10px 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.saveButton {
  width: 100%;
  padding: 0.5rem 1rem;
  font-size: 20px;
  color: #fff;
  background-color: #007bff;
  border: none;
  border-radius: 5px;
  margin-top: 5px;
  transition: 0.3s;
  cursor: pointer;
}

.saveButton:hover {
  background-color: #0056b3;
}

.form-group {
  margin-bottom: 15px;
}

.success-message {
  display: block;
  font-weight: 600;
  margin-top: 20px;
}

label {
  display: block;
  margin-bottom: 5px;
}

input {
  width: calc(100% - 12px);
  padding: 0.5rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}
select {
  width: calc(100% - 12px);
  padding: 0.5rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

</style>