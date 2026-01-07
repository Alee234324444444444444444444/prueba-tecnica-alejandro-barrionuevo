<script setup lang="ts">
import { onMounted, ref } from "vue";
import ProductList from "../components/ProductList.vue";
import ProductForm from "../components/ProductForm.vue";
import { useProducts } from "../hooks/useProducts";

const { products, loading, error, loadProducts, addProduct } = useProducts();
const showForm = ref(false);

onMounted(() => loadProducts());

async function handleCreate(payload: any) {
  await addProduct(payload);
  showForm.value = false;
}
</script>

<template>
  <!-- FONDO CAMBIADO A COLORES QUE COMBINAN -->
  <div class="min-h-screen bg-gradient-to-br from-violet-100 via-purple-100 to-fuchsia-100">
    <!-- Header fijo -->
    <div class="sticky top-0 z-40 bg-white/80 backdrop-blur-lg border-b border-purple-200 shadow-lg">
      <div class="w-full px-10 py-6">
        <div class="flex items-center justify-between">
          <div>
            <h1 class="text-4xl font-bold bg-gradient-to-r from-indigo-600 to-purple-600 bg-clip-text text-transparent">
              Gestión de Productos
            </h1>
            <p class="text-gray-700 mt-2 text-base">Administra tu inventario de forma sencilla</p>
          </div>

          <button
            @click="showForm = true"
            class="bg-gradient-to-r from-indigo-600 to-purple-600 text-white px-8 py-4 rounded-xl font-semibold hover:from-indigo-700 hover:to-purple-700 transition-all duration-200 shadow-lg hover:shadow-xl flex items-center gap-3 text-lg"
          >
            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4"/>
            </svg>
            Crear producto
          </button>
        </div>
      </div>
    </div>

    <!-- Contenido principal -->
    <div class="w-full px-10 py-10">
      <!-- Error Alert -->
      <transition
        enter-active-class="transition duration-300 ease-out"
        enter-from-class="opacity-0 -translate-y-2"
        leave-active-class="transition duration-200 ease-in"
        leave-to-class="opacity-0 -translate-y-2"
      >
        <div v-if="error" class="mb-8 bg-red-50 border-l-4 border-red-500 text-red-700 px-6 py-4 rounded-xl flex items-center gap-3 shadow-lg">
          <svg class="w-6 h-6 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
            <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clip-rule="evenodd"/>
          </svg>
          <span class="font-medium text-base">{{ error }}</span>
        </div>
      </transition>

      <!-- Product List -->
      <ProductList :products="products" :loading="loading" />
    </div>

    <!-- Modal -->
    <transition
      enter-active-class="transition duration-300 ease-out"
      enter-from-class="opacity-0"
      enter-to-class="opacity-100"
      leave-active-class="transition duration-200 ease-in"
      leave-from-class="opacity-100"
      leave-to-class="opacity-0"
    >
      <div v-if="showForm" class="fixed inset-0 z-50 overflow-y-auto">
        <div 
          class="fixed inset-0 bg-black/50 backdrop-blur-sm transition-opacity" 
          @click="showForm = false"
        ></div>

        <div class="relative min-h-screen flex items-center justify-center p-4">
          <transition
            enter-active-class="transition duration-300 ease-out"
            enter-from-class="opacity-0 scale-95"
            enter-to-class="opacity-100 scale-100"
            leave-active-class="transition duration-200 ease-in"
            leave-from-class="opacity-100 scale-100"
            leave-to-class="opacity-0 scale-95"
          >
            <div 
              v-if="showForm"
              class="relative w-full max-w-xl bg-white rounded-2xl shadow-2xl p-6"
            >
              <div class="flex items-center justify-between mb-6 pb-4 border-b">
                <div>
                  <h2 class="text-2xl font-bold text-gray-900">Crear Producto</h2>
                  <p class="text-sm text-gray-600 mt-1">Completa los datos del nuevo producto</p>
                </div>
                <button
                  @click="showForm = false"
                  class="text-gray-400 hover:text-gray-600 transition-colors p-2 hover:bg-gray-100 rounded-lg"
                >
                  <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
                  </svg>
                </button>
              </div>

              <ProductForm :on-create="handleCreate" />
            </div>
          </transition>
        </div>
      </div>
    </transition>
  </div>
</template>