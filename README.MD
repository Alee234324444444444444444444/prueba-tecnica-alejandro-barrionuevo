# 🛒 Prueba Técnica Alejandro Barrionuevo – Aplicación FullStack de Gestión de Productos

**Prueba Técnica AB** es una aplicación FullStack desarrollada para la gestión básica de productos, permitiendo **listar** y **crear productos**, manejar stock y visualizar información relevante de cada producto.

El proyecto está compuesto por:
- 🔙 **Backend** en **Kotlin + Spring Boot**
- 🎨 **Frontend** en **Vue 3 + TypeScript + Tailwind CSS**
- 🐘 **Base de datos PostgreSQL**
- 🐳 **Docker** para contenerización

La aplicación sigue una **arquitectura por capas**, asegurando una correcta separación de responsabilidades y facilidad de mantenimiento.

---

## 🧱 Arquitectura General

- **Backend**: API REST que gestiona la lógica de negocio y persistencia siguiendo el patrón **CONTROLLER → SERVICES → REPOSITORY**
- **Frontend**: Interfaz gráfica desarrollada como SPA que consume la API para mostrar y crear productos

---

## 📂 Estructura del Proyecto

### 🔙 Backend – Spring Boot + Kotlin

```plaintext
com/example/pruebatecnicaab
├── config/                # Configuraciones globales (CORS)
│   └── CorsConfig.kt
├── constants/             # Constantes del sistema
│   └── Routes.kt
├── controllers/           # Controladores REST (endpoints)
│   └── ProductController.kt
├── exceptions/            # Excepciones personalizadas
│   ├── handlers/
│   │   └── GlobalExceptionHandler.kt
│   ├── InvalidProductPriceException.kt
│   ├── ProductAlreadyExistsException.kt
│   └── StockNotAvailableException.kt
├── mappers/               # Conversión entre entidades y DTOs
│   ├── BaseMapper.kt
│   └── ProductMapper.kt
├── models/
│   ├── entities/          # Entidades JPA
│   │   ├── BaseEntity.kt
│   │   └── Product.kt
│   ├── requests/          # DTOs de entrada
│   │   └── CreateProductRequest.kt
│   └── responses/         # DTOs de salida
│       └── ProductResponse.kt
├── repositories/          # Acceso a base de datos
│   └── ProductRepository.kt
├── services/              # Lógica de negocio
│   └── ProductService.kt
└── PruebatecnicaabApplication.kt
```

### 🎨 Frontend – Vue 3 + TypeScript + Tailwind CSS

```plaintext
pruebatecnicaabfrontend/
├── public/                  # Archivos públicos
├── src/
│   ├── api/                 # Configuración de consumo de la API (Axios)
│   ├── assets/              # Recursos estáticos (imágenes, íconos)
│   ├── components/          # Componentes reutilizables
│   │   ├── ProductForm.vue  # Formulario para crear productos
│   │   └── ProductList.vue  # Listado visual de productos
│   ├── hooks/               # Lógica reutilizable (Composition API)
│   │   └── useProducts.ts   # Obtención y creación de productos
│   ├── types/               # Interfaces TypeScript (DTOs)
│   │   ├── product.ts
│   │   └── product-create-request.ts
│   ├── views/               # Vistas principales
│   │   └── ProductsView.vue
│   ├── App.vue              # Componente raíz de la aplicación
│   ├── main.ts              # Punto de entrada
│   └── style.css            # Estilos globales y Tailwind CSS
├── Dockerfile               # Build del frontend con Nginx
├── docker-compose.yml       # Contenedor del frontend
├── nginx.conf               # Configuración de Nginx (SPA)
├── index.html               # HTML base
├── package.json             # Dependencias y scripts
├── postcss.config.js        # Configuración de PostCSS
├── tailwind.config.js       # Configuración de Tailwind
├── tsconfig*.json           # Configuración de TypeScript
└── vite.config.ts           # Configuración de Vite
```

---

## 🚀 Características Principales

- ✅ Listado de productos con información detallada
- ✅ Creación de nuevos productos
- ✅ Gestión de stock
- ✅ Validación de datos en frontend y backend
- ✅ Manejo de errores centralizado
- ✅ Arquitectura escalable y mantenible
- ✅ Contenedorización con Docker

---



## 📋 Requisitos Previos

- Docker y Docker Compose
- Node.js (para desarrollo local del frontend)
- Java 17+ (para desarrollo local del backend)

---


## 🚀 Instalación y Ejecución

### Con Docker

```bash
# Clonar el repositorio
git clone https://github.com/Alee234324444444444444444444/prueba-tecnica-alejandro-barrionuevo.git

# En la raiz del proyecto se abre una terminal para el backend (pruebatécnicaab):
cd pruebatécnicaab

# Y estando en la ruta del pruebatécnicaab (backend ) ejecutamos:
docker compose up

#Lo mismo hacemos en el frontend abrimos otra terminal y ejecutamos:
cd pruebatecnicaabfrontend

# Y luego ejecutamos estando en la ruta pruebatecnicaabfrontend:
docker compose up


# La aplicación estará disponible en:
# Frontend: 
http://localhost:5173
# Backend:
 http://localhost:8080
```

## 🗄️ Configuración de Base de Datos

### Credenciales de PostgreSQL

La aplicación utiliza PostgreSQL como base de datos. Las credenciales configuradas son:

### Acceso al Gestor de Base de Datos

Puedes conectarte a la base de datos usando cualquier cliente de PostgreSQL (pgAdmin, DBeaver, DataGrip, etc.) con las siguientes credenciales:

**Configuración de Conexión:**
- **Host:** `localhost`
- **Puerto:** `6969`
- **Base de datos:** `pruebaab_db`
- **Usuario:** `admin`
- **Contraseña:** `admin`


---


## 📝 API Endpoints

### Productos

- **GET** `/api/products` - Listar todos los productos
- **POST** `/api/products` - Crear un nuevo producto


