<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div></div>
</template>

<script>
// Trigger a page refresh
</script>

<style scoped>
/* styles of our navbar */
.main-header {
  font-family: 'Inter', sans-serif;
  background-color: #f8f9fa;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  padding: 10px 0;
  position: relative;
}

.container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.logo {
  font-weight: bold;
  font-size: 1.5rem;
  color: #333;
  text-decoration: none;
}

.navbar-toggler {
  display: none;
}

.navbar-nav {
  list-style: none;
  display: flex;
  margin: 0;
  padding: 0;
}

.nav-item {
  margin-right: 15px;
}

.nav-link {
  text-decoration: none;
  color: #333;
  font-size: 1rem;
  padding: 0.5rem 1rem;
  transition: color 0.3s;
}

.nav-link:hover {
  color: #007bff;
}

.navbar-open {
  display: block;
}

.user-roles {
  margin-left: auto;
  display: flex;
  align-items: center;
}

.logout-button {
  padding: 0.5rem 1rem;
  font-size: 1rem;
  color: #333;
  cursor: pointer;
}

@media (max-width: 768px) {
  .logo {
    margin-right: auto;
  }

  .navbar-toggler {
    display: block;
    cursor: pointer;
  }

  .navbar-nav {
    flex-direction: column;
    position: absolute;
    top: 60px;
    left: 0;
    background-color: #fff;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    display: none;
  }

  .navbar-open .navbar-nav {
    display: flex;
  }

  .nav-item {
    margin-right: 0;
    margin-bottom: 10px;
  }
}
</style>



