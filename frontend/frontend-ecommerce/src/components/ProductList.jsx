import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

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
            <Link key={prod.id} to={`/products/${prod.id}`}>
              {prod.name}
            </Link>
          ))}
        </ul>
      )}
    </div>
  );
};

export default ProductList;