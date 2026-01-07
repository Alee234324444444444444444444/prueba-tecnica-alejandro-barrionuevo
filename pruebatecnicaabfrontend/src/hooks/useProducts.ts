import { ref } from "vue";
import type { Product } from "../types/product";
import type { ProductCreateRequest } from "../types/product-create-request";
import { getProducts, createProduct } from "../api/products.api";

export function useProducts() {
  const products = ref<Product[]>([]);
  const loading = ref(false);
  const error = ref<string | null>(null);

  async function loadProducts() {
    loading.value = true;
    error.value = null;
    try {
      products.value = await getProducts();
    } catch (e: any) {
      error.value = e?.response?.data?.message ?? "Error al cargar productos";
    } finally {
      loading.value = false;
    }
  }

  async function addProduct(payload: ProductCreateRequest) {
    loading.value = true;
    error.value = null;
    try {
      await createProduct(payload);
      await loadProducts(); 
    } catch (e: any) {
      error.value = e?.response?.data?.message ?? "Error al crear producto";
      throw e;
    } finally {
      loading.value = false;
    }
  }

  return {
    products,
    loading,
    error,
    loadProducts,
    addProduct,
  };
}
