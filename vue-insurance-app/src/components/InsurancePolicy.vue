<!-- src/components/InsurancePolicy.vue -->
<template>
  <div class="entity-crud">
    <h1>Insurance Policy Management</h1>

    <!-- Form to add or edit entity -->
    <div class="form-section">
      <h2>{{ isEdit ? "Edit Insurance Policy" : "Add New Insurance Policy" }}</h2>
      <form @submit.prevent="saveInsurancePolicy">
        <div>
          <label>Name:</label>
          <input type="text" v-model="policy.name" required />
        </div>
        <div>
          <label>Status:</label>
          <select v-model="policy.status">
            <option value="ACTIVE">ACTIVE</option>
            <option value="INACTIVE">INACTIVE</option>
          </select>
        </div>
        <div>
          <label>Start Date:</label>
          <input type="date" v-model="policy.startDate" required />
        </div>
        <div>
          <label>End Date:</label>
          <input type="date" v-model="policy.endDate" required />
        </div>
        <button type="submit">{{ isEdit ? "Update" : "Save" }}</button>
        <button type="button" @click="resetForm">Clear</button>
      </form>
    </div>

    <!-- Entity List -->
    <div class="entity-list">
      <h2>Insurance Policies List</h2>
      <table>
        <thead>
          <tr>
            <th>Name</th>
            <th>Status</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in policies" :key="item.id ?? index">
            <td>{{ item.name }}</td>
            <td>{{ item.status }}</td>
            <td>{{ item.startDate }}</td>
            <td>{{ item.endDate }}</td>
            <td>
              <button @click="editInsurancePolicy(item)">Edit</button>
              <button @click="deleteInsurancePolicy(item.id)">Delete</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from "vue";
import axios from "axios";

interface InsurancePolicy {
  id: number | null;
  name: string;
  status: "ACTIVE" | "INACTIVE";
  startDate: string;
  endDate: string;
}

export default defineComponent({
  name: "InsurancePolicy",
  setup() {
    const policies = ref<InsurancePolicy[]>([]);
    const policy = ref<InsurancePolicy>({
      id: null,
      name: "",
      status: "ACTIVE",
      startDate: "",
      endDate: "",
    });
    const isEdit = ref(false);

    const BASE_URL = "http://localhost:8080";
    const fetchInsurancePolicies = async () => {
      const response = await axios.get(BASE_URL + "/insurancepolicies");
      policies.value = response.data;
    };

    const saveInsurancePolicy = async () => {
      if (isEdit.value) {
        await axios.patch(BASE_URL + `/insurancepolicies/${policy.value.id}`, policy.value);
      } else {
        await axios.post(BASE_URL + `/insurancepolicies`, policy.value);
      }
      fetchInsurancePolicies();
      resetForm();
    };

    const editInsurancePolicy = (item: InsurancePolicy) => {
      policy.value = { ...item };
      isEdit.value = true;
    };

    const deleteInsurancePolicy = async (id: number | null) => {
      if (id !== null) {
        await axios.delete(BASE_URL + `/insurancepolicies/${id}`);
        fetchInsurancePolicies();
      }
    };

    const resetForm = () => {
      policy.value = {
        id: null,
        name: "",
        status: "ACTIVE",
        startDate: "",
        endDate: "",
      };
      isEdit.value = false;
    };

    onMounted(fetchInsurancePolicies);

    return {
      policies,
      policy,
      isEdit,
      fetchInsurancePolicies,
      saveInsurancePolicy,
      editInsurancePolicy,
      deleteInsurancePolicy,
      resetForm,
    };
  },
});
</script>

<style scoped>
/* Add your styles here */
</style>
