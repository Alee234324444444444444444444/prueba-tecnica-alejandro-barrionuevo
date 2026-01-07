import { http } from "./http";
import type { Product } from "../types/product";
import type { ProductCreateRequest } from "../types/product-create-request";

export async function getProducts(): Promise<Product[]> {
  const res = await http.get<Product[]>("/api/products");
  return res.data;
}

export async function createProduct(payload: ProductCreateRequest): Promise<Product> {
  const res = await http.post<Product>("/api/products", payload);
  return res.data;
}
