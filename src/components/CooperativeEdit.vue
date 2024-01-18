<template>
  <div>
    <div class="form-container">
      <form @submit.prevent="saveCooperative" class="cooperative-form">
        <label for="name">Name:</label>
        <input v-model="cooperative.name" type="text" id="name" required>

        <label for="vat">VAT:</label>
        <input v-model="cooperative.vat" type="text" id="vat" required>

        <label for="product_name">Product Name:</label>
        <input v-model="cooperative.product_name" type="text" id="product_name" required>

        <label for="product_category">Product Category:</label>
        <input v-model="cooperative.product_category" type="text" id="product_category" required>

        <!-- Add other form fields for cooperative data as needed -->

        <button type="submit">Save</button>
      </form>
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
    };
  },
  created() {
    const cooperativeId = this.$route.params.id;
    this.fetchCooperativeDetails(cooperativeId);
  },
  methods: {
    fetchCooperativeDetails(cooperativeId) {
      const accessToken = localStorage.getItem('jwt');
      axios.get(`http://localhost:9090/api/cooperative/${cooperativeId}`, {
        headers: {
          'Authorization': 'Bearer ' + accessToken,
        },
      })
          .then(response => {
            // Pre-fill the form fields with existing data
            this.cooperative = response.data;
          })
          .catch(error => {
            console.error('Error fetching cooperative details', error);
          });
    },
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
            const cooperativeId = this.$route.params.id;
            this.$router.push(`/cooperatives/${cooperativeId}`);
          })
          .catch(error => {
            console.error('Error saving cooperative', error);
          });
    },
  },
};
</script>

<style scoped>
.form-container {
  max-width: 400px;
  margin: 20px auto;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  background-color: #f8f9fa; /* Light gray background */
}

.cooperative-form {
  display: flex;
  flex-direction: column;
}

label {
  margin-bottom: 8px;
}

input {
  padding: 8px;
  margin-bottom: 16px;
  border: 1px solid #007bff; /* Blue border */
  border-radius: 4px;
  outline: none;
}

button {
  padding: 10px;
  background-color: #007bff; /* Blue button */
  color: #fff; /* White text */
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3; /* Darker blue on hover */
}
</style>
