<template>
    <footer class="footer-bar">
      <div class="footer-content">
        <p>&copy; 2024 Your Company. All rights reserved.</p>
        <div class="names-box">
          <div class="name-box">
            <p class="name">it2021113 Giorgos Nicolaides</p>
          </div>
          <div class="name-box">
            <p class="name">it2021154 Panagiotis Foteinopoulos</p>
          </div>
          <div class="name-box">
            <p class="name">it2021088 Andreas Rousounelos</p>
          </div>
        </div>
        <div class="social-icons">
          <!-- Add your social icons or links here -->
          <a href="#" target="_blank"><i class="fab fa-facebook"></i></a>
          <a href="#" target="_blank"><i class="fab fa-twitter"></i></a>
          <a href="#" target="_blank"><i class="fab fa-linkedin"></i></a>
          <!-- Add more social icons as needed -->
        </div>
      </div>
    </footer>
  </template>
  
  <script>
  export default {
    // Add component logic if needed
  };
  </script>
  
  <style scoped>
  .footer-bar {
    background-color: darkgrey;
    color: #fff;
    padding: 5px 0;
    text-align: center;
    width: 100%;
    position: fixed;
    bottom: 0;
  }
  
  .footer-content {
    display: flex;
    justify-content: space-between;
    align-items: center;
    max-width: 1200px;
    margin: 0 auto;
  }
  
  .names-box {
    display: flex;
    align-items: center;
  }
  
  .name-box {
    margin-left: 10px;
    padding: 5px 10px;
    background-color: grey; /* Set your preferred background color */
    border-radius: 5px;
  }
  
  .name {
    margin: 0;
    color: #fff; /* Set your preferred text color */
  }
  
  .social-icons a {
    color: #fff;
    margin-right: 10px;
    font-size: 20px;
  }
  
  /* Add additional styles for social icons or other elements */
  </style>
  