<script setup lang="ts">
import { reactive, ref } from "vue";
import type { ProductCreateRequest } from "../types/product-create-request";

const props = defineProps<{
  onCreate: (payload: ProductCreateRequest) => Promise<void>;
}>();

const form = reactive<ProductCreateRequest>({
  name: "",
  price: 0,
  stock_quantity: 0,
});

const errors = reactive({
  name: "",
  price: "",
  stock: "",
});

const toastMsg = ref<string | null>(null);
const toastType = ref<"success" | "error">("success");
let toastTimer: number | null = null;

function showToast(message: string, type: "success" | "error") {
  toastMsg.value = message;
  toastType.value = type;
  if (toastTimer) window.clearTimeout(toastTimer);
  toastTimer = window.setTimeout(() => (toastMsg.value = null), 4000);
}

function validate(): boolean {
  errors.name = "";
  errors.price = "";
  errors.stock = "";

  let ok = true;

  if (!form.name.trim()) {
    errors.name = "El nombre es obligatorio";
    ok = false;
  }
  if (form.price <= 0) {
    errors.price = "El precio debe ser mayor a 0";
    ok = false;
  }
  if (form.stock_quantity <= 0) {
    errors.stock = "El stock debe ser mayor a 0";
    ok = false;
  }
  return ok;
}

const isSubmitting = ref(false);

async function submit() {
  if (!validate()) {
    showToast("Revisa los campos en rojo", "error");
    return;
  }

  isSubmitting.value = true;
  try {
    await props.onCreate({
      name: form.name.trim(),
      price: Number(form.price),
      stock_quantity: Number(form.stock_quantity),
    });

    showToast("¡Producto creado exitosamente! ", "success");
    
    // Limpiar formulario
    form.name = "";
    form.price = 0;
    form.stock_quantity = 0;
    
  } catch (error: any) {
    console.error("Error completo:", error);
    console.error("Response data:", error?.response?.data);
    console.error("Message:", error?.response?.data?.message);
    
    // Obtener el mensaje del backend (múltiples formas)
    const backendMessage = error?.response?.data?.message || 
                          error?.response?.data?.error ||
                          error?.message || "";
    
    console.log("🔍 Backend message:", backendMessage);
    
    // Verificar si es error de nombre duplicado
    if (backendMessage.toLowerCase().includes("already exists") || 
        backendMessage.toLowerCase().includes("ya existe") ||
        backendMessage.toLowerCase().includes("exist")) {
      
      const productName = form.name.trim();
      errors.name = `El producto "${productName}" ya existe`;
      showToast(`El producto "${productName}" ya existe`, "error");
    } 
    // Error de precio
    else if (backendMessage.toLowerCase().includes("price") || 
             backendMessage.toLowerCase().includes("precio")) {
      errors.price = "El precio no es válido";
      showToast( backendMessage, "error");
    } 
    // Error de stock
    else if (backendMessage.toLowerCase().includes("stock")) {
      errors.stock = "El stock no es válido";
      showToast(backendMessage, "error");
    } 
    // Error genérico
    else {
      showToast("Error al crear producto: " + (backendMessage || "Error desconocido"), "error");
    }
  } finally {
    isSubmitting.value = false;
  }
}
</script>

<template>
  <div class="w-full">
    <!-- Toast -->
    <transition
      enter-active-class="transition duration-300 ease-out"
      enter-from-class="opacity-0 -translate-y-2"
      enter-to-class="opacity-100 translate-y-0"
      leave-active-class="transition duration-250 ease-in"
      leave-from-class="opacity-100 translate-y-0"
      leave-to-class="opacity-0 -translate-y-2"
    >
      <div
        v-if="toastMsg"
        class="mb-4 px-4 py-3 rounded-xl text-sm font-medium flex items-center gap-2 shadow-lg"
        :class="toastType === 'success' ? 'bg-green-600 text-white' : 'bg-red-600 text-white'"
      >
        <svg v-if="toastType === 'success'" class="w-5 h-5 flex-shrink-0" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/>
        </svg>
        <svg v-else class="w-5 h-5 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
          <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clip-rule="evenodd"/>
        </svg>
        <span class="flex-1">{{ toastMsg }}</span>
      </div>
    </transition>

    <form @submit.prevent="submit" class="space-y-5">
      <!-- Nombre -->
      <div>
        <label class="block text-sm font-semibold text-gray-700 mb-2">Nombre del Producto *</label>
        <input
          v-model="form.name"
          type="text"
          class="w-full border-2 rounded-xl px-4 py-3 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent transition-all"
          :class="errors.name ? 'border-red-500 bg-red-50 ring-2 ring-red-200' : 'border-gray-300'"
          placeholder="Ej: Laptop Dell XPS 15"
          @input="errors.name = ''"
        />
        <transition
          enter-active-class="transition duration-200 ease-out"
          enter-from-class="opacity-0 -translate-y-1"
        >
          <p v-if="errors.name" class="text-sm text-red-600 mt-2 flex items-center gap-1 font-medium">
            <svg class="w-4 h-4 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
              <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z" clip-rule="evenodd"/>
            </svg>
            {{ errors.name }}
          </p>
        </transition>
      </div>

      <!-- Precio y Stock -->
      <div class="grid grid-cols-2 gap-4">
        <div>
          <label class="block text-sm font-semibold text-gray-700 mb-2">Precio (USD) *</label>
          <div class="relative">
            <span class="absolute left-4 top-1/2 -translate-y-1/2 text-gray-500 font-bold">$</span>
            <input
              v-model.number="form.price"
              type="number"
              step="0.01"
              min="0"
              class="w-full border-2 rounded-xl pl-8 pr-4 py-3 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent transition-all"
              :class="errors.price ? 'border-red-500 bg-red-50 ring-2 ring-red-200' : 'border-gray-300'"
              placeholder="0.00"
              @input="errors.price = ''"
            />
          </div>
          <transition
            enter-active-class="transition duration-200 ease-out"
            enter-from-class="opacity-0 -translate-y-1"
          >
            <p v-if="errors.price" class="text-sm text-red-600 mt-2 flex items-center gap-1 font-medium">
              <svg class="w-4 h-4 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z" clip-rule="evenodd"/>
              </svg>
              {{ errors.price }}
            </p>
          </transition>
        </div>

        <div>
          <label class="block text-sm font-semibold text-gray-700 mb-2">Stock *</label>
          <input
            v-model.number="form.stock_quantity"
            type="number"
            min="0"
            class="w-full border-2 rounded-xl px-4 py-3 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent transition-all"
            :class="errors.stock ? 'border-red-500 bg-red-50 ring-2 ring-red-200' : 'border-gray-300'"
            placeholder="0"
            @input="errors.stock = ''"
          />
          <transition
            enter-active-class="transition duration-200 ease-out"
            enter-from-class="opacity-0 -translate-y-1"
          >
            <p v-if="errors.stock" class="text-sm text-red-600 mt-2 flex items-center gap-1 font-medium">
              <svg class="w-4 h-4 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z" clip-rule="evenodd"/>
              </svg>
              {{ errors.stock }}
            </p>
          </transition>
        </div>
      </div>

      <!-- Botón -->
      <button
        type="submit"
        :disabled="isSubmitting"
        class="w-full bg-gradient-to-r from-indigo-600 to-purple-600 text-white rounded-xl py-3 font-semibold hover:from-indigo-700 hover:to-purple-700 transition-all disabled:opacity-60 flex items-center justify-center gap-2 shadow-lg"
      >
        <svg v-if="isSubmitting" class="w-5 h-5 animate-spin" fill="none" viewBox="0 0 24 24">
          <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"/>
          <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"/>
        </svg>
        {{ isSubmitting ? "Guardando..." : "Guardar Producto" }}
      </button>
    </form>
  </div>
</template>