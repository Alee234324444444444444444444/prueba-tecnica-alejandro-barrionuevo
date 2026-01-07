<script setup lang="ts">
import { computed } from "vue";
import type { Product } from "../types/product";

const props = defineProps<{
  products: Product[];
  loading: boolean;
}>();

// Filtrar productos válidos
const validProducts = computed(() => {
  return props.products.filter(p => 
    p.name && 
    p.name.trim() !== '' && 
    !isNaN(Number(p.price)) &&
    !isNaN(Number(p.stock_quantity))
  );
});

// Formatear fecha
function formatCreatedAt(dateString?: string): string {
  if (!dateString) return 'N/A';
  
  try {
    const date = new Date(dateString);
    return date.toLocaleDateString('es-EC', {
      year: 'numeric',
      month: 'short',
      day: 'numeric'
    });
  } catch {
    return 'N/A';
  }
}
</script>

<template>
  <div>
    <!-- Header con contador -->
    <div class="mb-8 flex items-center justify-between">
      <div class="flex items-center gap-4">
        <div class="w-16 h-16 bg-gradient-to-br from-indigo-500 to-purple-600 rounded-2xl flex items-center justify-center shadow-xl">
          <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4"/>
          </svg>
        </div>
        <div>
          <h2 class="text-3xl font-bold text-gray-900">Mis Productos</h2>
          <p class="text-base text-gray-600 mt-1">
            {{ validProducts.length }} {{ validProducts.length === 1 ? 'producto' : 'productos' }} disponibles
          </p>
        </div>
      </div>
    </div>

    <!-- Loading State -->
    <div v-if="loading" class="flex items-center justify-center py-32">
      <div class="text-center">
        <svg class="animate-spin w-16 h-16 text-indigo-600 mx-auto mb-6" fill="none" viewBox="0 0 24 24">
          <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"/>
          <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"/>
        </svg>
        <p class="text-xl text-gray-600 font-medium">Cargando productos...</p>
      </div>
    </div>

    <!-- Empty State -->
    <div v-else-if="validProducts.length === 0" class="bg-white rounded-3xl shadow-2xl p-20 text-center border-2 border-gray-200">
      <div class="w-32 h-32 bg-gradient-to-br from-indigo-100 to-purple-100 rounded-full flex items-center justify-center mx-auto mb-6">
        <svg class="w-16 h-16 text-indigo-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 13V6a2 2 0 00-2-2H6a2 2 0 00-2 2v7m16 0v5a2 2 0 01-2 2H6a2 2 0 01-2-2v-5m16 0h-2.586a1 1 0 00-.707.293l-2.414 2.414a1 1 0 01-.707.293h-3.172a1 1 0 01-.707-.293l-2.414-2.414A1 1 0 006.586 13H4"/>
        </svg>
      </div>
      <p class="text-2xl font-bold text-gray-900 mb-3">No hay productos todavía</p>
      <p class="text-lg text-gray-600">Crea tu primer producto usando el botón "Crear producto"</p>
    </div>

    <!-- Products Grid -->
    <div v-else class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 2xl:grid-cols-4 gap-8">
      <div
        v-for="p in validProducts"
        :key="p.id"
        class="group bg-white rounded-3xl shadow-xl hover:shadow-2xl transition-all duration-300 overflow-hidden border-2 border-gray-200 hover:border-indigo-400 hover:-translate-y-2"
      >
        <!-- Card Header con gradiente -->
        <div class="h-48 bg-gradient-to-br from-indigo-500 via-purple-500 to-pink-500 relative overflow-hidden">
          <div class="absolute inset-0 bg-gradient-to-t from-black/20 to-transparent"></div>
          
          <!-- Badge de stock -->
          <div class="absolute top-5 right-5">
            <span
              class="px-4 py-2 rounded-full text-sm font-bold backdrop-blur-md shadow-lg"
              :class="
                p.stock_quantity === 0
                  ? 'bg-red-500/95 text-white'
                  : p.stock_quantity < 10
                  ? 'bg-yellow-500/95 text-white'
                  : 'bg-green-500/95 text-white'
              "
            >
              <span class="inline-block w-2.5 h-2.5 rounded-full mr-2 bg-white animate-pulse"></span>
              {{ p.stock_quantity }} {{ p.stock_quantity === 1 ? 'unidad' : 'unidades' }}
            </span>
          </div>

          <!-- Icono decorativo -->
          <div class="absolute bottom-5 left-5">
            <div class="w-14 h-14 bg-white/20 backdrop-blur-sm rounded-2xl flex items-center justify-center shadow-lg">
              <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4"/>
              </svg>
            </div>
          </div>
        </div>

        <!-- Card Body -->
        <div class="p-7">
          <!-- Nombre del producto -->
          <h3 class="text-2xl font-bold text-gray-900 mb-4 line-clamp-2 min-h-[64px] leading-tight">
            {{ p.name }}
          </h3>

          <!-- Divider -->
          <div class="w-16 h-1 bg-gradient-to-r from-indigo-500 to-purple-500 rounded-full mb-5"></div>

          <!-- Precio -->
          <div class="mb-6">
            <p class="text-sm text-gray-500 mb-1 font-medium">Precio unitario</p>
            <p class="text-5xl font-black bg-gradient-to-r from-indigo-600 to-purple-600 bg-clip-text text-transparent">
              ${{ Number(p.price).toFixed(2) }}
            </p>
          </div>

          <!-- Info adicional -->
          <div class="flex items-center gap-2 text-sm text-gray-600 bg-gray-50 rounded-xl p-3">
            <svg class="w-5 h-5 text-indigo-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"/>
            </svg>
            <span class="font-medium">{{ formatCreatedAt(p.created_at) }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>