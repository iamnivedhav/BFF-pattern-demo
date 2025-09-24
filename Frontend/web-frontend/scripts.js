// -------- Config --------
const BFF_BASE = "http://localhost:8080";

// -------- Utils --------
function getQueryParam(param) {
  const urlParams = new URLSearchParams(window.location.search);
  return urlParams.get(param);
}

function createStars(rating) {
  const r = Math.round(Number(rating) || 0);
  let stars = "";
  for (let i = 1; i <= 5; i++) stars += i <= r ? "★" : "☆";
  return `<span class="stars">${stars}</span>`;
}

function formatPrice(value) {
  return new Intl.NumberFormat("en-IN", { style: "currency", currency: "INR" }).format(Number(value || 0));
}

async function fetchJson(url) {
  try {
    const res = await fetch(url);
    if (!res.ok) throw new Error("Network error");
    return await res.json();
  } catch (err) {
    console.error("Fetch failed:", err);
    // Return empty array on failure so the app doesn't crash
    return [];
  }
}

// -------- Home --------
async function loadHomePage() {
  const container = document.getElementById("product-list");
  // This check prevents errors if the function is called on the wrong page
  if (!container) return;

  const products = await fetchJson(`${BFF_BASE}/products`);
  container.innerHTML = ""; // Clear existing content

  products.forEach(p => {
    const img = (p.images && p.images[0]) || p.image || "";
    const short = p.shortDescription || p.shortDesc || "";
    const rating = p.avgRating || p.rating || 0;
    const totalReviews = p.totalReviews || p.ratingCount || 0;
    const offer = p.offer ? `<span class="badge">${p.offer}</span>` : "";
    const card = document.createElement("div");
    card.className = "product-card";
    card.innerHTML = `
      <img src="../images/${img}" alt="${p.name}">
      <h3>${p.name}</h3>
      <div class="meta">
        <span class="price">${formatPrice(p.price)}</span>
        ${offer}
      </div>
      <p class="desc">${short}</p>
      <div class="rating">${createStars(rating)} <span>(${totalReviews})</span></div>
      <a class="btn small" href="products.html?id=${p.id}">View Details</a>
    `;
    container.appendChild(card);
  });
}

// -------- Product --------
// -------- Product --------
async function loadProductPage() {
  const id = getQueryParam("id");
  if (!id) return;

  const p = await fetchJson(`${BFF_BASE}/products/${id}`);
  if (!p) return;

  // Set the browser tab title
  document.getElementById("title").textContent = p.name;

  const mainImage = document.getElementById("mainImage");
  const thumbnails = document.getElementById("thumbnails");
  // Assuming images come as a single string "image.jpg" or an array ["image1.jpg", "image2.jpg"]
  const imgs = (p.images && p.images.length) ? p.images : [p.image];
  mainImage.src = `../images/${imgs[0]}` || "images/placeholder.jpg"; // Fallback placeholder image
  thumbnails.innerHTML = "";
  imgs.forEach((src, idx) => {
    const t = document.createElement("img");
    t.src = `images/${src}`;
    if (idx === 0) t.classList.add("active");
    t.onclick = () => {
      mainImage.src = `../images/${src}`;
      thumbnails.querySelectorAll("img").forEach(i => i.classList.remove("active"));
      t.classList.add("active");
    };
    thumbnails.appendChild(t);
  });

  document.getElementById("productTitle").textContent = p.name; // Use new h2 ID
  document.getElementById("brand").textContent = p.brand ? `Brand: ${p.brand}` : "";
  
  // Rating and reviews
  document.getElementById("avgRating").textContent = (p.avgRating || p.rating || 0).toFixed ? (p.avgRating || p.rating || 0).toFixed(1) : (p.avgRating || p.rating || 0);
  document.getElementById("productStars").innerHTML = createStars(p.avgRating || p.rating || 0);
  document.getElementById("totalReviews").textContent = p.totalReviews || p.ratingCount || (p.reviews ? p.reviews.length : 0);


  document.getElementById("price").textContent = formatPrice(p.price);
  const offerElement = document.getElementById("offer");
  if (p.offer) {
      offerElement.textContent = p.offer;
      offerElement.style.display = 'inline-block'; // Show badge
  } else {
      offerElement.style.display = 'none'; // Hide badge if no offer
  }

  document.getElementById("stock").textContent = typeof p.stock === "number" ? (p.stock > 0 ? `In stock (${p.stock})` : "Out of stock") : (p.stock || "");
  document.getElementById("sold").textContent = `Sold: ${p.soldLastWeek || 0} last week • ${p.soldLastMonth || 0} last month`;

  const sizesSelect = document.getElementById("sizes");
  const colorsSelect = document.getElementById("colors");
  sizesSelect.innerHTML = ''; // Clear existing options
  colorsSelect.innerHTML = ''; // Clear existing options

  // Populate sizes (use default if not provided)
  const availableSizes = (p.sizes && p.sizes.length) ? p.sizes : ["S","M","L"];
  availableSizes.forEach(s => { const o=document.createElement("option"); o.textContent=s; sizesSelect.appendChild(o); });

  // Populate colors (use default if not provided)
  const availableColors = (p.colors && p.colors.length) ? p.colors : ["Red","Blue","Green"];
  availableColors.forEach(c => { const o=document.createElement("option"); o.textContent=c; colorsSelect.appendChild(o); });


  // Tab content: Description, Specs, Reviews, Q&A
  document.getElementById("longDesc").textContent = p.description || p.longDesc || p.fullDescription || "";
  
  const specsList = document.getElementById("specsList"); // Changed from 'specs' to 'specsList' for <ul>
  specsList.innerHTML = '';
  (p.specs || []).forEach(s => { const li=document.createElement("li"); li.textContent=s; specsList.appendChild(li); });


  const reviewList = document.getElementById("reviewList");
  reviewList.innerHTML = '';
  (p.reviews || []).forEach(r => {
    const div = document.createElement("div");
    div.className = "review";
    div.innerHTML = `<p><strong>${r.user || "User"}</strong> ${createStars(r.rating)} <small>${r.date || ""}</small></p><p>${r.comment || r.text || ""}</p>`;
    reviewList.appendChild(div);
  });

  const qnaList = document.getElementById("qnaList");
  qnaList.innerHTML = '';
  (p.qna || []).forEach(q => {
    const div = document.createElement("div");
    div.className = "qna-item"; // Add a class for styling Q&A
    div.innerHTML = `<p><strong>${q.user || "User"}</strong>: ${q.question} <br><em>${q.answer || ""}</em> <small>${q.date || ""}</small></p>`;
    qnaList.appendChild(div);
  });
  
  // NOTE: We have not built a /related endpoint, so this will use the local fallback.
  // Assuming the `related` property in your product JSON or fetching related products via a placeholder
  const relatedList = document.getElementById("relatedList");
  relatedList.innerHTML = '';
  const related = p.related || (await fetchJson(`${BFF_BASE}/products/${p.id}/related`)).items || [];
  related.slice(0,8).forEach(rp=>{
    const img = (rp.images && rp.images[0]) || rp.image || "";
    const card = document.createElement("div");
    card.className = "product-card";
    card.innerHTML = `<img src="../images/${img}" alt="${rp.name}"><h3>${rp.name}</h3><div class="meta"><span class="price">${formatPrice(rp.price)}</span></div><a class="btn small" href="products.html?id=${rp.id}">View</a>`;
    relatedList.appendChild(card);
  });
}

// -------- Orders --------
async function loadOrdersPage() {
  const container = document.getElementById("ordersList");
  if (!container) return;

  const orders = await fetchJson(`${BFF_BASE}/orders`);
  container.innerHTML = "";

  orders
    .slice()
    .sort((a,b)=> new Date(b.date) - new Date(a.date))
    .forEach(o=>{
      const itemsHtml = (o.items||[]).map(it=>`<li>${it.name || it.productName || ""} x${it.quantity || it.qty}</li>`).join("");
      const div = document.createElement("div");
      div.className = "order-card";
      div.innerHTML = `
        <div class="order-header">
          <strong>Order ${o.id}</strong>
          <span>${o.status}</span>
        </div>
        <p><small>${o.date}</small></p>
        <ul>${itemsHtml}</ul>
        <p><strong>Total:</strong> ${formatPrice(o.totalAmount)}</p>
        <div class="order-actions">
          <button class="btn small" onclick="alert('Reordered!')">Reorder</button>
        </div>`;
      container.appendChild(div);
    });
}

// -------- Router --------
document.addEventListener("DOMContentLoaded", () => {
  const path = window.location.pathname;
  if (path.endsWith("index.html") || path.endsWith("/")) {
    loadHomePage();
  } else if (path.endsWith("products.html")) {
    loadProductPage();
  } else if (path.endsWith("orders.html")) {
    loadOrdersPage();
  }
});