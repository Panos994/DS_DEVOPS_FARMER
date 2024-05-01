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
  font-family: 'Inter', sans-serif;
  width: 400px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -80%);
  margin: 20px auto;
  border: 1px solid #ddd;
  border-radius: 10px;
}

.signup-container>h1 {
  padding: 10px;
  color: #fff;
  background-color: #41b883;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  margin-top: 0;
}

.signup-form {
  display: flex;
  flex-direction: column;
  gap: 15px;
  padding: 20px;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
}

label {
  font-weight: bold;
}

.input-field {
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.signUpButton {
  padding: 0.5rem 1rem;
  margin-top: 15px;
  font-size: 1rem;
  color: #fff;
  cursor: pointer;
  background-color: #007bff;
  border: none;
  border-radius: 4px;
  transition: 0.3s;
}

.signUpButton:hover {
  background-color: #0056b3;
}

.success-message {
  display: block;
  font-weight: 600;
  padding: 10px;
  color: #41b883;
  margin-bottom: 10px;
}
</style>
