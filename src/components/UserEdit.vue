<template>
  <div class="form-container">
    <form @submit.prevent="updateUser" class="user-form">
      <label for="username">Username:</label>
      <input v-model="userDetails.username" type="text" id="username" required>

      <label for="email">Email:</label>
      <input v-model="userDetails.email" type="email" id="email" required>

      <label for="password">Password:</label>
      <input v-model="userDetails.password" type="password" id="password" required>

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
  margin: 20px auto;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  background-color: #f8f9fa;
  /* Light gray background */
  font-family: 'Inter', sans-serif;
  width: 400px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -75%);
  border: 1px solid rgba(0, 0, 0, 0.1);
  font-size: 18px;
}

.user-form {
  display: flex;
  flex-direction: column;
}

.form-group {
  margin-bottom: 16px;
}

.update-button {
  padding: 10px;
  font-size: 1rem;
  color: #fff;
  cursor: pointer;
  background-color: #007bff;
  border: none;
  border-radius: 4px;
  transition: 0.3s
}

.update-button:hover {
  background-color: #0056b3;
}

label {
  margin-bottom: 15px;
  font-weight: 600
}

input {
  padding: 8px;
  margin-bottom: 16px;
  border: 1px solid #007bff;
  /* Blue border */
  border-radius: 4px;
  outline: none;
  font-size: 16px;
}
</style>
