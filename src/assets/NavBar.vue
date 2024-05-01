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
                            <router-link class="nav-link" to="/your-cooperative/:id">My Cooperative</router-link>
                        </li>
                    </ul>
                </div>
                <div v-if="userRoles.length > 0" class="user-roles">
                    <p id="User_roles">User Roles: {{ userRoles.join(', ') }}</p>
                    <button class="logout-button" @click="logout"><span>Logout</span></button>
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
            userRoles: [],
            isNavbarOpen: false,
        };
    },

    created() {
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
            }
        },

        toggleNavbar() {
            this.isNavbarOpen = !this.isNavbarOpen;
        },
    },
};
</script>

<style scoped>
.main-header {
    background-color: #41b883;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    padding: 10px 0;
    width: 100%;
    position: fixed;
    top: 0;
    left: 0;
    z-index: 100;
}

.container {
    font-family: 'Inter', sans-serif;
    font-weight: 400;
    font-size: 18px;
    margin-left: 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.logo {
    font-weight: bold;
    font-size: 1.5rem;
    color: #fff;
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
    color: #fff;
    padding: 0.5rem 1rem;
    transition: color 0.3s;
}

.nav-link:hover {
    color: #34495e;
}

.navbar-open {
    display: block;
}

.user-roles {
    color: #fff;
    margin-left: auto;
    display: flex;
    align-items: center;
}

.logout-button {
    border-radius: 4px;
    background-color: #34495e;
    border: none;
    color: #fff;
    text-align: center;
    font-size: 18px;
    padding: 10px;
    width: 125px;
    transition: all 0.5s;
    cursor: pointer;
    margin-right: 40px;
    margin-left: 40px;
}

.logout-button:hover {
    background-color: #007bff;
}

.logout-button span {
    cursor: pointer;
    position: relative;
    transition: 0.5s;
}

.logout-button span:after {
    content: '\00bb';
    position: absolute;
    opacity: 0;
    top: 0;
    right: 10px;
    transition: 0.5s;
}

.logout-button:hover span {
    padding-right: 25px;
}

.logout-button:hover span:after {
    opacity: 1;
    right: 0;
}

#User_roles {
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
        background-color: #333;
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
