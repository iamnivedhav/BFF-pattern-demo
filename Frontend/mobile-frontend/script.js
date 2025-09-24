// Define the base URL for your running mobile-bff service
const API_BASE_URL = "http://localhost:8083";


function createStars(rating) {
  const r = Math.round(Number(rating) || 0);
  let stars = "";
  for (let i = 1; i <= 5; i++) stars += i <= r ? "★" : "☆";
  return `<span class="stars">${stars}</span>`;
}
// Detect which page we are on
document.addEventListener("DOMContentLoaded", () => {
  if (document.getElementById("product-list")) loadProducts();
  if (document.getElementById("product-details")) loadProductDetails();
  if (document.getElementById("order-list")) loadOrders();
});

// Load products
function loadProducts() {
  // Fetch from the Mobile BFF endpoint
  fetch(`${API_BASE_URL}/products`)
    .then(res => res.json())
    .then(products => {
      const container = document.getElementById("product-list");
      products.forEach(p => {
        const card = document.createElement("div");
        card.className = "product-card";
        // The mobile DTO provides all these fields, so no changes here.
        // Assuming images are in a local /images folder.
        card.innerHTML = `
          <img src="../images/${p.image}" alt="${p.name}">
          <h3>${p.name}</h3>
          <p>Price: $${p.price}</p>
          <a href="product.html?id=${p.id}">View</a>
        `;
        container.appendChild(card);
      });
    });
}

// Load single product
// Load single product
function loadProductDetails() {
  const urlParams = new URLSearchParams(window.location.search);
  const id = urlParams.get("id");

  fetch(`${API_BASE_URL}/products/${id}`)
    .then(res => res.json())
    .then(product => {
      const container = document.getElementById("product-details");

      if (product) {
        container.innerHTML = `
          <h2>${product.name}</h2>
          <img src="../images/${product.image}" alt="${product.name}" style="max-width:300px;">
          <p><strong>Price:</strong> $${product.price}</p>
          
          <p><strong>Rating:</strong> ${createStars(product.avgRating)}</p>
          <p><strong>Description:</strong> ${product.description}</p> 
          
          <button>Add to Cart</button>
        `;
      } else {
        container.innerHTML = "<p>Product not found.</p>";
      }
    });
}
// Load orders
function loadOrders() {
  // Fetch from the Mobile BFF endpoint
  fetch(`${API_BASE_URL}/orders`)
    .then(res => res.json())
    .then(orders => {
      const container = document.getElementById("order-list");
      orders.forEach(o => {
        const card = document.createElement("div");
        card.className = "order-card";
        // MODIFIED: Changed o.total to o.totalAmount to match the backend DTO
        card.innerHTML = `
          <h3>Order #${o.id}</h3>
          <p>Status: ${o.status}</p>
          <p>Total: $${o.totalAmount}</p>
        `;
        container.appendChild(card);
      });
    });
}