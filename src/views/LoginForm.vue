<template>
  <div class="login-container">
    <h1>Login</h1>
    <form @submit.prevent="loginUser" class="login-form">
      <label for="username">Username:</label>
      <input type="text" v-model="username" required class="input-field">
      <label for="password">Password:</label>
      <input type="password" v-model="password" required class="input-field">
      <button class="loginButton" type="submit">Login</button>
      <div class="signup-link">
        <p>Don't have an account? <router-link to="/signup">Sign Up</router-link></p>
      </div>
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

.login-container>h1 {
  padding: 10px;
  color: #fff;
  background-color: #41b883;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  margin-top: 0;
}


.signup-link {
  text-align: center;
}

.signup-link p a {
  color: #308660;
  text-decoration: none;
  font-weight: 600;
}

.signup-link p a:hover {
  text-decoration: underline;
}

.login-form {
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

.loginButton {
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

.loginButton:hover {
  background-color: #0056b3;
}
</style>
