<!-- src/App.vue -->

<template>
    <div id="app">
        <nav>
            <router-link to="/">Insurances</router-link>
            <router-link to="/about">About</router-link>
        </nav>
        <router-view></router-view>
    </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
    name: 'App',
});
</script>

<style>
nav {
    padding: 20px;
}
nav a {
    margin: 10px;
    text-decoration: none;
}
</style>
