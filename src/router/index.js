// src/router/index.js
import Vue from 'vue';
import VueRouter from 'vue-router';
import Login from '../views/LoginForm.vue';
import SignUp from '../views/SignUp.vue';
import CooperativeList from '@/components/CooperativeList.vue';
import CooperativeForm from '@/components/CooperativeForm.vue';
import CooperativeDetails from '@/components/CooperativeDetails.vue';

import CooperativeEdit from '@/components/CooperativeEdit.vue';
import userList from "@/components/UserList.vue";
import userDetails from "@/components/userDetails.vue";
import UserEdit from '@/components/UserEdit.vue';


import Dashboard from '@/components/Dashboard.vue';

import YourCooperative from '@/components/YourCooperative.vue';


Vue.use(VueRouter);

const routes = [
    { path: '/', redirect: '/login' },
    { path: '/login', component: Login },
    { path: '/signup', component: SignUp },
    { path: '/cooperatives', name: 'CooperativeList', component: CooperativeList },
    { path: '/cooperatives/new', name: 'CooperativeForm', component: CooperativeForm },
    { path: '/cooperatives/:id', name: 'CooperativeDetails', component: CooperativeDetails },
    { path: '/cooperatives/:id/edit', name: 'CooperativeEdit', component: CooperativeEdit },
    { path: '/users', name: 'userList', component: userList },
    { path: '/users/:id', name: 'userDetails', component: userDetails },

    {path: '/users/:id/edit', name: 'UserEdit', component: UserEdit},
    {path: '/your-cooperative/:id', name: 'YourCooperative', component: YourCooperative},


    {
        path: '/home',
        name: 'Dashboard',
        component: Dashboard,
    },





];

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes,
});


// Navigation guards to check user roles before accessing routes
router.beforeEach((to, from, next) => {
    const userRole = localStorage.getItem('userRole');

    // Define role-based route access
    const roleAccess = {
        ROLE_USER: ['CooperativeForm'],
        ROLE_ADMIN: ['UserDetails', 'UserEdit', 'UserList'],
        ROLE_MODERATOR: ['CooperativeEdit', 'CooperativeList', 'CooperativeDetails'],
    };

    if (to.name && roleAccess[userRole] && !roleAccess[userRole].includes(to.name)) {
        // If the user doesn't have access, redirect to a default route or show an unauthorized message
        next('/');
    } else {
        next();
    }
});












export default router;
