<template>
  <footer class="footer-bar">
    <div class="footer-content">
      <p>&copy; 2024 Agricultural Cooperatives. All rights reserved.</p>

      <div class="names-box">
        <div class="name-box">
          <p class="name" ref="name1"></p>
        </div>
        <div class="name-box">
          <p class="name" ref="name2"></p>
        </div>
        <div class="name-box">
          <p class="name" ref="name3"></p>
        </div>
      </div>

      <div class="social-icons">

        <a href="#" target="_blank"><i class="fab fa-facebook"></i></a>
        <a href="#" target="_blank"><i class="fab fa-twitter"></i></a>
        <a href="#" target="_blank"><i class="fab fa-linkedin"></i></a>

      </div>
    </div>
  </footer>
</template>

<script>
export default {
  mounted() {
    this.typeText("it2021154 Panagiotis Foteinopoulos", this.$refs.name1);
    this.typeText("it2021113 Giorgos Nicolaides", this.$refs.name2);
    this.typeText("it2021088 Andreas Rousounelos", this.$refs.name3);
  },
  methods: {
    typeText(text, element) {
      let index = 0;

      function type() {
        if (index < text.length) {
          element.innerHTML += text.charAt(index);
          index++;
          setTimeout(type, 70);
        }
      }

      type();
    },
  },
};
</script>

<style scoped>
.footer-bar {
  background-color: #333;
  color: #fff;
  padding: 5px 0;
  text-align: center;
  width: 100%;
  position: fixed;
  bottom: 0;
  left: 0;
}

.footer-content {
  font-family: 'Inter', sans-serif;
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-width: 1300px;
  margin: 0 auto;
}

.names-box {
  display: flex;
  align-items: center;
}

.name-box {
  margin-left: 10px;
  padding: 8px 5px;
  background-color: #555;
  border-radius: 4px;
}

.name {
  margin: 0;
  color: #fff;
  overflow: hidden;
  display: inline-block;
  white-space: nowrap;
}

.social-icons a {
  color: #fff;
  margin-right: 10px;
  font-size: 15px;
}
</style>
