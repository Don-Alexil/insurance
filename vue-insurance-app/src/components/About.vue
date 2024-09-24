<!-- src/views/About.vue -->
<template>
    <div>
        <h1>About Page</h1>
        <p>This is the about page.</p>
    </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
    name: 'About',
});
</script>

<style scoped>
h1 {
    color: green;
}
</style>
