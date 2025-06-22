import React, { useEffect, useState } from 'react';

const ProductList = () => {
  const [products, setProducts] = useState([]);

  useEffect(() => {
  console.log("Se montó el componente ProductList");

  fetch('/api/products')
    .then(response => {
      console.log("Respuesta del backend:", response);
      return response.json();
    })
    .then(data => {
      console.log("Datos recibidos:", data);
      setProducts(data);
    })
    .catch(error => console.error("Error al obtener productos:", error));
}, []);

  return (
    <div>
      {products.length === 0 ? (
        <p>No hay productos.</p>
      ) : (
        <ul>
          {products.map(prod => (
            <li key={prod.id}>
              <strong>{prod.name}</strong>: {prod.description} (${prod.price})
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default ProductList;