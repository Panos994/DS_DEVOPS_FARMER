<template>
  <div class="signup-container">
    <h1>Sign Up</h1>
    <form @submit.prevent="registerUser" class="signup-form">
      <label for="username">Username:</label>
      <input type="text" v-model="username" required class="input-field">
      <label for="email">Email:</label>
      <input type="email" v-model="email" required class="input-field">
      <label for="password">Password:</label>
      <input type="password" v-model="password" required class="input-field">
      <button class="signUpButton" type="submit">Sign Up</button>
    </form>
    <strong v-if="submissionCompleted" class="success-message">
      Signed Up - Go to <router-link to="/login">Login</router-link>
    </strong>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      username: '',
      email: '',
      password: '',
      submissionCompleted: false,
    };
  },
  methods: {
    registerUser() {
      axios.post('http://localhost:9090/api/auth/signup', {
        username: this.username,
        email: this.email,
        password: this.password,
      })
        .then((response) => {
          console.log(response.data);
          this.submissionCompleted = true;
          // Handle successful registration response (e.g., show success message)
        })
        .catch((error) => {
          console.error(error);
          // Handle registration error (e.g., show error message)
        });
    },
  },
};
</script>

<style scoped>
.signup-container {
  max-width: 400px;
  margin: 20px auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
}

.signup-form {
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

.signUpButton {
  padding: 0.5rem 1rem;
  font-size: 1rem;
  color: #fff;
  cursor: pointer;
  background-color: #007bff;
  border: none;
  border-radius: 10px;
  margin-top: 20px;
}

.signUpButton:hover {
  background-color: #0056b3;
}

.success-message {
  margin-top: 15px;
  display: block;
  color: #28a745;
  font-weight: bold;
}
</style>
