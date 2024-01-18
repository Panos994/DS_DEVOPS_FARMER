<template>
  <div class="form-container">
    <form @submit.prevent="updateUser" class="user-form">
      <div class="form-group">
        <label for="username">Username:</label>
        <input v-model="userDetails.username" type="text" id="username" required>
      </div>

      <div class="form-group">
        <label for="email">Email:</label>
        <input v-model="userDetails.email" type="email" id="email" required>
      </div>

      <div class="form-group">
        <label for="password">Password:</label>
        <input v-model="userDetails.password" type="password" id="password" required>
      </div>

      <button class="update-button" type="submit">Update</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      userDetails: {
        username: '',
        email: '',
        password: '',
        // Add other properties as needed
      },
    };
  },
  created() {
    const userId = this.$route.params.id;
    this.fetchUserDetails(userId);
  },
  methods: {
    fetchUserDetails(userId) {
      const accessToken = localStorage.getItem('jwt');
      axios.get(`http://localhost:9090/api/user/admin/${userId}`, {
        headers: {
          'Authorization': 'Bearer ' + accessToken,
        },
      })
          .then(response => {
            // Pre-fill the form fields with existing data
            this.userDetails = response.data;
          })
          .catch(error => {
            console.error('Error fetching user details', error);
          });
    },
    updateUser() {
      const userId = this.$route.params.id;
      const accessToken = localStorage.getItem('jwt');

      axios.post(`http://localhost:9090/api/user/new`, this.userDetails, {
        headers: {
          'Authorization': 'Bearer ' + accessToken,
          'Content-Type': 'application/json',
        },
      })
          .then(response => {
            console.log('User updated successfully', response.data);
            // Redirect to the user details page or perform other actions
            this.$router.push(`/users/${userId}`);
          })
          .catch(error => {
            console.error('Error updating user', error);
          });
    },
  },
};
</script>

<style scoped>
.form-container {
  max-width: 400px;
  margin: 0 auto;
  text-align: center;
}

.user-form {
  padding: 16px;
  background-color: #f8f9fa;
  border: 1px solid #dee2e6;
  border-radius: 4px;
}

.form-group {
  margin-bottom: 16px;
}

/* Add your styles for the header here */
.main-header {
  background-color: #f8f9fa;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  padding: 10px 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.update-button {
  padding: 10px;
  font-size: 1rem;
  color: #fff;
  cursor: pointer;
  background-color: #007bff;
  border: none;
  border-radius: 4px;
}

.update-button:hover {
  background-color: #0056b3;
}
</style>
