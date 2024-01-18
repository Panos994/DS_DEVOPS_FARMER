<template>
  <div class="user-list-scroll-container">
    <div class="cooperatives-list">
      <table>
        <thead>
          <tr class="cooperative-header">
            <th>Name</th>
            <th>VAT</th>
            <th>Details</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="cooperative in cooperatives" :key="cooperative.id" class="cooperative-item">
            <td>{{ cooperative.name }}</td>
            <td>{{ cooperative.vat }}</td>
            <td>
              <router-link :to="{ name: 'CooperativeDetails', params: { id: cooperative.id } }">
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
      cooperatives: [],
    };
  },

  created() {
    this.fetchCooperatives();
  },

  methods: {
    fetchCooperatives() {
      const accessToken = localStorage.getItem('jwt');

      axios.get('http://localhost:9090/api/cooperative', {
        headers: {
          'Authorization': 'Bearer ' + accessToken,
        },
      })
        .then(response => {
          this.cooperatives = response.data;
        })
        .catch(error => {
          console.error('Error fetching cooperatives', error);
        });
    },
  },
};
</script>



<style scoped>
/* Add your styles for the table here */
.user-list-scroll-container {
  overflow-y: auto;
  max-height: 700px;
}

.user-list-container {
  margin: 20px auto;
  max-width: 200px;
}

table {
  width: 90%;
  border-collapse: collapse;
  margin-top: 5px;
  margin: auto;
}

thead {
  background-color: darkgrey;
  color: #fff;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

tr:nth-child(even) {
  background-color: #f2f2f2;
}

.label {
  background-color: #f8f9fa;
  padding: 2px 4px;
  border-radius: 4px;
  margin-right: 4px;
  font-weight: bold;
}

.router-link {
  text-decoration: none;
  color: #007bff;
  cursor: pointer;
}

.router-link:hover {
  text-decoration: underline;
}
</style>