<template>
  <div class="details">
    <div class="user-details-box">
      <h1>User Details</h1>
      <p>Name: {{ userDetails.username }}</p>
      <p>Encrypted Password: {{ userDetails.password }}</p>
      <p>Email: {{ userDetails.email }}</p>
      <button class="edit-button" @click="editUser">Edit</button>
      <button class="delete-button" @click="deleteUser">Delete</button>
    </div>

    <div class="system-roles-box">
      <h1>System Roles & Authorities</h1>
      <p>Roles: ADMIN (Number: 1)</p>
      <p>Roles: USER (Number: 4)</p>
      <p>Roles: MODERATOR (Number: 5)</p>
      <button class="addRole" @click="addRolePrompt">Add Role</button>
      <button class="deleteRole" @click="deleteRolePrompt">Delete Role</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      userDetails: {},
    };
  },

  created() {
    this.fetchUserDetails();
  },

  methods: {
    fetchUserDetails() {
      const accessToken = localStorage.getItem('jwt');
      const userId = this.$route.params.id;

      axios.get(`http://localhost:9090/api/user/admin/${userId}`, {
        headers: {
          'Authorization': 'Bearer ' + accessToken,
        },
      })
        .then(response => {
          this.userDetails = response.data;
        })
        .catch(error => {
          console.error('Error fetching user details', error);
        });
    },

    addRolePrompt() {
      const userId = this.$route.params.id;
      const roleNumber = prompt('Enter role number to add:');

      if (roleNumber !== null) {
        this.addRole(userId, roleNumber);
      }
    },

    deleteRolePrompt() {
      const userId = this.$route.params.id;
      const roleNumber = prompt('Enter role number to delete:');

      if (roleNumber !== null) {
        this.deleteRole(userId, roleNumber);
      }
    },

    addRole(userId, roleNumber) {
      const accessToken = localStorage.getItem('jwt');

      axios.post(`http://localhost:9090/api/user/admin/role/add/${userId}/${roleNumber}`, null, {
        headers: {
          'Authorization': 'Bearer ' + accessToken,
        },
      })
        .then(response => {
          console.log('Role added successfully', response.data);
          this.fetchUserDetails();
        })
        .catch(error => {
          console.error('Error adding role to user', error);
        });
    },

    deleteRole(userId, roleNumber) {
      const accessToken = localStorage.getItem('jwt');

      axios.post(`http://localhost:9090/api/user/admin/role/delete/${userId}/${roleNumber}`, null, {
        headers: {
          'Authorization': 'Bearer ' + accessToken,
        },
      })
        .then(response => {
          console.log('Role deleted successfully', response.data);
          this.fetchUserDetails();
        })
        .catch(error => {
          console.error('Error deleting role from user', error);
        });
    },

    editUser() {
      const userId = this.$route.params.id;
      this.$router.push(`/users/${userId}/edit`);
    },

    deleteUser() {
      const accessToken = localStorage.getItem('jwt');
      const userId = this.$route.params.id;

      axios.delete(`http://localhost:9090/api/user/admin/${userId}`, {
        headers: {
          'Authorization': 'Bearer ' + accessToken,
        },
      })
        .then(response => {
          console.log('User deleted successfully', response.data);
          this.$router.push('/users');
        })
        .catch(error => {
          console.error('Error deleting user', error);
        });
    },


  },
};
</script>



<style scoped>
.details {
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  background-color: #f8f9fa;
  font-family: 'Inter', sans-serif;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -75%);
  font-size: 18px;
}

.user-details-box,
.system-roles-box {
  background-color: #f8f9fa;
  /* Border around each box */
  border-radius: 10px;
  padding: 20px;
  margin: 10px;
  /*   space between the boxes */
  display: inline-block;
  /*  boxes are inline kanoun wrapping to content */
  /*  margin between the boxes */
}


.edit-button,
.delete-button,
.addRole,
.deleteRole {
  padding: 10px;
  margin-right: 10px;
  font-size: 1rem;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: 0.2s;
}

.edit-button {
  background-color: #28a745;
}

.edit-button:hover {
  background-color: #237e38;
}

.delete-button {
  background-color: #dc3545;
}

.delete-button:hover {
  background-color: #aa2b38;
}

.addRole {
  background-color: #007bff;
}

.addRole:hover {
  background-color: #0056b3;
}

.deleteRole {
  background-color: #ffb507;
}

.deleteRole:hover {
  background-color: #da9c0b;
}
</style>
