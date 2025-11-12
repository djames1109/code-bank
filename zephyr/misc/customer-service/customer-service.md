## 👤 Customer Service

### 🎯 Purpose
The **Customer Service** manages customer identity and verification workflows.  
It handles registration, profile updates, and identity validation via an external KYC (Know Your Customer) API.  
It also serves as the source of truth for customer information used by other Zephyr services.

---

### 🧩 Use Cases

| # | Use Case | Description |
|---|-----------|-------------|
| 1 | **Customer Registration (with KYC Verification)** | Register a new customer and verify their identity using an external KYC API before saving to the database. |
| 2 | **Get Customer Details** | Retrieve a customer profile, using Redis for caching to improve performance. |
| 3 | **Update Customer Information** | Update existing customer data (e.g., contact info) and refresh the Redis cache. |
| 4 | **Validate Customer (for internal use)** | Allow internal services (e.g., Account Service) to validate that a customer exists and is KYC-verified. |

---

### ⚙️ API Endpoints

| # | Endpoint | Method | Description | Request Body | Response Example |
|---|-----------|---------|-------------|---------------|------------------|
| 1 | `/api/customers` | `POST` | Register a new customer and perform KYC verification. | ```json { "firstName": "Alice", "lastName": "Tan", "nationalId": "AB1234567", "birthDate": "1990-01-01", "email": "alice@email.com" } ``` | ```json { "id": 1, "status": "VERIFIED", "kycStatus": "VERIFIED" } ``` |
| 2 | `/api/customers/{id}` | `GET` | Retrieve a customer's details (cached in Redis). | — | ```json { "id": 1, "firstName": "Alice", "lastName": "Tan", "kycStatus": "VERIFIED" } ``` |
| 3 | `/api/customers/{id}` | `PUT` | Update customer information and refresh cache. | ```json { "email": "new@email.com", "phone": "+63917..." } ``` | ```json { "id": 1, "status": "UPDATED" } ``` |
| 4 | `/api/customers/{id}/validate` | `GET` | Internal endpoint for validating customer existence and KYC status. | — | ```json { "id": 1, "exists": true, "kycStatus": "VERIFIED" } ``` |

---

### 🧠 Integration Notes

- **External KYC API** – Invoked during registration to verify identity and extract only relevant fields (`status`, `issued`, `expiry`).
- **Redis Cache** – Used for quick lookups of frequently accessed customer profiles.
- **PostgreSQL** – Stores all customer profiles and KYC verification results.
- **Security** – All endpoints require a valid JWT except the internal `/validate`, which uses a service-to-service token.
