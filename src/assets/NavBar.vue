<template>
    <div>
        <header class="main-header">
            <div class="container">
                
                <button class="navbar-toggler" @click="toggleNavbar">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div :class="{ 'navbar-open': isNavbarOpen }">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <router-link class="nav-link" to="/dashboard" exact>Home</router-link>
                        </li>
                        <li v-if="userRoles.includes(4)" class="nav-item">
                            <router-link class="nav-link" to="/cooperatives/new">Cooperative Form</router-link>
                        </li>
                        <li v-if="userRoles.includes(1)" class="nav-item">
                            <router-link class="nav-link" to="/users">Users</router-link>
                        </li>
                        <li v-if="userRoles.includes(5)" class="nav-item">
                            <router-link class="nav-link" to="/cooperatives">Cooperatives</router-link>
                        </li>

                        <li v-if="userRoles.includes(4)" class="nav-item">
                            <router-link class="nav-link" to="/your-cooperative/:id">Your Cooperative</router-link>
                        </li>






                    </ul>
                </div>
                <div v-if="userRoles.length > 0" class="user-roles">
                    <p id="User_roles">User Roles: {{ userRoles.join(', ') }}</p>
                    <button class="logout-button" @click="logout">Logout</button>
                </div>
            </div>
        </header>
    </div>
</template>
<script>
import axios from "axios";

export default {
    data() {
        return {
            userRoles: [], // Store user roles
            isNavbarOpen: false,

        };
    },

    created() {
        // Fetch user roles when the component is created
        this.fetchUserRole();
    },

    methods: {
        fetchUserRole() {
            const accessToken = localStorage.getItem('jwt');

            axios.get("http://localhost:9090/api/user/roles", {
                headers: {
                    'Authorization': 'Bearer ' + accessToken,
                },
            })
                .then((response) => {
                    this.userRoles = response.data;
                })
                .catch((error) => {
                    console.error('Error fetching user roles:', error);
                });
        },


        async logout() {
            try {
                const confirmed = window.confirm("Are you sure you want to logout?");
                if (confirmed) {
                    localStorage.removeItem('jwt');
                    localStorage.removeItem('user');
                    this.$router.push('/login');
                    window.location.reload();
                }
            } catch (error) {
                console.error('Logout error:', error);
                // Handle the error (e.g., show an error message)
            }
        },

        toggleNavbar() {
            this.isNavbarOpen = !this.isNavbarOpen;
        },
    },
};
</script>


<style scoped>
/* styles  navbar */
.main-header {
    background-color: #f8f9fa;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    padding: 10px 0;
    position: sticky;
    top: 0;
    /* Set the top position to 0 for sticking to the top */
    z-index: 100;
    /* Set a higher z-index to make sure it's above other elements */
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

#User_roles{
    margin-right: 10px;
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

