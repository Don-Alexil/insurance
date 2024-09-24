// src/router/index.ts
import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import InsurancePolicy from "../components/InsurancePolicy.vue";
import About from "../components/About.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "InsuranceCrud",
    component: InsurancePolicy,
  },
  {
    path: "/about",
    name: "About",
    component: About,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;