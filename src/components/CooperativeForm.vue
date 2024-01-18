<template>
  <div>
    <h2>Fill the Form</h2>

    <div class="cooperative-form-container">

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
          <select v-model="cooperative.product_category" id="product_name" required>
            <option value="fruits">Fruits</option>
            <option value="vegetables">Vegetables</option>
            <option value="dairy">Dairy</option>
            <option value="spices">Spices</option>
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
        <div v-if="submissionCompleted">Submission Completed</div>
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
.cooperative-form-container {
  border: 1px solid black;
  border-radius: 10px;
  padding: 20px;
  width: 35%;
  /* Adjust the width as needed */
  margin: 20px auto;
  background-color: #f8f9fa;
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
  padding: 0.5rem 1rem;
  font-size: 1rem;
  color: #333;
  cursor: pointer;
}

.form-group {
  margin-bottom: 15px;
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
}</style>