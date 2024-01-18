<template>
  <div class="user-list-scroll-container">
    <div class="user-list-container">
      <table>
        <thead>
          <tr>
            <th>Username</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="user in users" :key="user.id">
            <td>
              <span class="label">Username:</span> {{ user.username }}
            </td>

            <!-- Add other fields as needed -->
            <td>
              <router-link :to="{ name: 'userDetails', params: { id: user.id } }">
                View Details
              </router-link>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      users: [],
    };
  },

  created() {
    this.fetchUsers();
  },

  methods: {
    fetchUsers() {
      const accessToken = localStorage.getItem('jwt');
      axios
        .get('http://localhost:9090/api/user', {
          headers: {
            Authorization: 'Bearer ' + accessToken,
          },
        })
        .then((response) => {
          this.users = response.data;
        })
        .catch((error) => {
          console.error('Error fetching users', error);
        });
    },
  },
};
</script>

<style scoped>
/* Add your styles for the table here */
.user-list-scroll-container {
  overflow-y: auto; /* Enable vertical scrolling */
  max-height: 700px; /* Set a maximum height for the scroll container */
}

.user-list-container {
  margin: 20px auto; /* Center the table */
  max-width: 600px; /* Set a maximum width for the table */
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 10px;
}


thead {
  background-color: darkgrey;
  /* Blue background for header */
  color: #fff;
  /* White text for header */
}

th,
td {
  border: 1px solid #ddd;
  /* Add border for cells */
  padding: 8px;
  text-align: left;
}

tr:nth-child(even) {
  background-color: #f2f2f2;
  /* Alternate row color for better readability */
}

.label {
  background-color: #f8f9fa;
  /* Light grey background for labels */
  padding: 2px 4px;
  border-radius: 4px;
  margin-right: 4px;
  font-weight: bold;
}

.router-link {
  text-decoration: none;
  color: #007bff;
  /* Blue color for link text */
  cursor: pointer;
}

.router-link:hover {
  text-decoration: underline;
}
</style>
