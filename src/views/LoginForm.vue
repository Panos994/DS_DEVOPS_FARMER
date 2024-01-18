<template>
  <div class="login-container">
    <h1>Agricultural Forms</h1>
    <router-link to="/signup" class="signup-link">Sign Up</router-link>
    <form @submit.prevent="loginUser" class="login-form">
      <label for="username">Username:</label>
      <input type="text" v-model="username" required class="input-field">
      <label for="password">Password:</label>
      <input type="password" v-model="password" required class="input-field">
      <button class="loginButton" type="submit">Login</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      username: '',
      password: '',
    };
  },
  methods: {
    loginUser() {
      axios.post('http://localhost:9090/api/auth/signin', {
        username: this.username,
        password: this.password,
      })
        .then((response) => {
          console.log(response.data);
          // Handle successful login response (e.g., store token, redirect)
          this.updateUserDetails(response.data);

          // Extract the token from the response
          const accessToken = response.data.accessToken;

          // Store the token in localStorage
          localStorage.setItem('jwt', accessToken);

          this.$router.push('/dashboard');
          window.location.reload();
        })
        .catch((error) => {
          console.error(error);
          // Handle login error (e.g., show error message)
        });
    },

    updateUserDetails(userDetails) {
      // Store the user object in local storage
      localStorage.setItem('user', JSON.stringify(userDetails));
    },
  },
};
</script>

<style scoped>
.login-container {
  max-width: 400px;
  margin: 20px auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  text-align: center;
}


.signup-link {
  display: inline-block;
  margin-top: 1px;
  margin-bottom: 15px;
  color: white;
  text-decoration: none;
  font-weight: bold;
  padding: 5px; /* Adjust padding as needed */
  border: 1px solid black;
  border-radius: 10px; /* Adjust border-radius as needed */
  border-color: black;
  background-color: #007bff;
}

.signup-link:hover {
  background-color: green; /* Add a background color on hover for better visibility */
}

.login-form {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

label {
  font-weight: bold;
}

.input-field {
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  width: 100%;
}

.loginButton {
  padding: 0.5rem 1rem;
  font-size: 1rem;
  color: #fff;
  cursor: pointer;
  background-color: #007bff;
  border: none;
  border-radius: 4px;
}

.loginButton:hover {
  background-color: #0056b3;
}
</style>
