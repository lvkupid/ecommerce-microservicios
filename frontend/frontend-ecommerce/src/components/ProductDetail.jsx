import React, {useState, useEffect} from "react";
import {useParams} from "react-router-dom";

function ProductDetail(){
    const {id} = useParams();
    const [product, setProduct] = useState(null);

    useEffect(() => {
        console.log("Se montó el componente ProductDetailPage");

        fetch(`/api/products/${id}`)
            .then(response => {
                console.log("Respuesta del backend:", response);
                return response.json();
            })
            .then(data => {
                console.log("Datos recibidos:", data);
                setProduct(data);
            })
            .catch(error => console.error("Error al obtener el producto:", error));
    }, [id]);

    if(!product) {
        return <p>Cargando producto...</p>;
    }

    const handleAddToCart = () => {
        const cartItem = {
            productId: product.id,
            quantity: 1 // Por ahora, agregamos una unidad
        };
        fetch('http://localhost:8081/api/carts/1/items', { // Usamos el carrito con ID 1 por ahora
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(cartItem)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al agregar al carrito');
            }
            return response.json();
        })
        .then(data => {
            console.log("Respuesta del servidor:", data);
            alert(`¡${product.name} fue agregado al carrito!`);
        })
        .catch(error => {   
            console.error('Error en la petición POST:', error);
            alert("Hubo un problema al agregar el producto.");
        });
    }

    return (
        <div>
            <h2>{product.name}</h2>
            <p>Precio: ${product.price}</p>
            <p>Descripción: {product.description}</p>
            <button onClick={handleAddToCart}>Agregar al carrito</button>
        </div>
    );

}

export default ProductDetail;