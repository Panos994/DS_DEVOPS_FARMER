<template>
  <div>
    <p>Name: {{ cooperativeDetails.name }}</p>
    <p>VAT: {{ cooperativeDetails.vat }}</p>
    <p>Product Name: {{ cooperativeDetails.product_name }}</p>
    <p>Status: {{ cooperativeDetails.status }}</p>
    <p>Notes: {{ cooperativeDetails.notes }}</p>

    <!--button class="editB" @click="editCooperative">Edit</button-->
    <button class="deleteB" @click="deleteCooperative">Delete</button>
    <button class="approveB" @click="approveCooperative(cooperativeDetails.id)">Approve</button>
    <button class="rejectB" @click="rejectCooperative(cooperativeDetails.id)">Reject</button>

    <p>Your User ID as Moderator is: {{ userDetails.id }}</p>
  </div>
</template>

<script>
import axios from 'axios';


export default {
  data() {
    return {
      cooperativeDetails: {},
      userDetails: {},
    };
  },

  created() {
    this.fetchCooperativeDetails();
    this.fetchUserDetails();
  },


  methods: {
    fetchUserDetails() {
      // Fetch user details from local storage
      const storedUser = localStorage.getItem('user');
      if (storedUser) {
        this.userDetails = JSON.parse(storedUser);
      } else {
        return null;
      }
    },

    fetchCooperativeDetails() {
      const accessToken = localStorage.getItem('jwt');
      const cooperativeId = this.$route.params.id;

      axios.get(`http://localhost:9090/api/cooperative/${cooperativeId}`, {
        headers: {
          'Authorization': 'Bearer ' + accessToken,
        },
      })
        .then(response => {
          this.cooperativeDetails = response.data;
        })
        .catch(error => {
          console.error('Error fetching cooperative details', error);
        });
    },

    approveCooperative(cooperativeId) {
      const notes = prompt('Enter notes for approval:');
      if (notes !== null) {
        const accessToken = localStorage.getItem('jwt');
        const userId = this.userDetails.id;



        axios.post(`http://localhost:9090/api/user/employee/${userId}/${cooperativeId}/check/submit`, {
          notes: notes,

        }, {
          headers: {
            'Authorization': 'Bearer ' + accessToken,
            'Content-Type': 'application/json',
          },
        })
          .then(response => {
            console.log('Cooperative approved successfully', response.data);

            this.fetchCooperativeDetails();



          })
          .catch(error => {
            console.error('Error approving cooperative', error);
          });
      }

    },



    rejectCooperative(cooperativeId) {
      const notes = prompt('Enter notes for rejection:');
      if (notes !== null) {
        const accessToken = localStorage.getItem('jwt');
        const userId = this.userDetails.id;

        axios.post(`http://localhost:9090/api/user/employee/${userId}/${cooperativeId}/check/submit-reject`, {
          notes: notes,

        }, {
          headers: {
            'Authorization': 'Bearer ' + accessToken,
            'Content-Type': 'application/json',
          },
        })
          .then(response => {
            console.log('Cooperative rejected successfully', response.data);

            this.fetchCooperativeDetails();

          })
          .catch(error => {
            console.error('Error rejecting cooperative', error);
          });
      }

    },













    editCooperative() {
      const cooperativeId = this.$route.params.id;
      this.$router.push(`/cooperatives/${cooperativeId}/edit`);
    },

    deleteCooperative() {
      const accessToken = localStorage.getItem('jwt');
      const cooperativeId = this.$route.params.id;

      axios.delete(`http://localhost:9090/api/cooperative/${cooperativeId}`, {
        headers: {
          'Authorization': 'Bearer ' + accessToken,
        },
      })
        .then(response => {
          console.log('Cooperative deleted successfully', response.data);
          this.$router.push('/cooperatives');
        })
        .catch(error => {
          console.error('Error deleting cooperative', error);
        });
    },

    logout() {
      localStorage.removeItem('jwt');
      localStorage.removeItem('user');
      this.$router.push('/login');
    },
  },
};
</script>



<style scoped>
div {
  max-width: 400px;
  margin: 20px auto;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  background-color: #f8f9fa;
  font-family: 'Inter', sans-serif;
  width: 400px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -75%);
  border: 1px solid rgba(0, 0, 0, 0.1);
}

.editB,
.deleteB,
.approveB,
.rejectB {
  padding: 10px;
  margin-right: 10px;
  font-size: 1rem;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: 0.2s;
}

.editB {
  background-color: #28a745;
  /* Green for Edit */
}

.editB:hover {
  background-color: #237e38;
}

.deleteB {
  background-color: #dc3545;
  /* Red for Delete */
}

.deleteB:hover {
  background-color: #aa2b38;
}

.approveB {
  background-color: #007bff;
  /* Blue for Approve */
}

.approveB:hover {
  background-color: #0056b3;
}

.rejectB {
  background-color: #ffb507;
  /* Yellow for Reject */
}

.rejectB:hover {
  background-color: #da9c0b;
}
</style>