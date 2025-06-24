import { useState, useEffect } from 'react';

function CartPage() {
    // Estado para guardar el carrito y para saber si está cargando
    const [cart, setCart] = useState(null);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        // Hacemos la llamada a la API del carrito cuando el componente se carga.
        // Usamos el ID 1 del carrito que está hardcodeado por ahora.
        fetch('http://localhost:8081/api/carts/1') // Gracias al proxy, no necesitamos la URL completa
            .then(response => {
                if (!response.ok) {
                    throw new Error('No se pudo obtener el carrito');
                }
                return response.json();
            })
            .then(data => {
                setCart(data); // Guardamos los datos del carrito en el estado
                setIsLoading(false); // Dejamos de cargar
            })
            .catch(error => {
                console.error("Error fetching cart:", error);
                setIsLoading(false); // Dejamos de cargar incluso si hay error
            });
    }, []); // El array vacío [] significa que este efecto se ejecuta solo una vez

    // Mostramos un mensaje mientras se cargan los datos
    if (isLoading) {
        return <div>Cargando carrito...</div>;
    }

    // Mostramos un mensaje si el carrito está vacío o no se pudo cargar
    if (!cart || cart.items.length === 0) {
        return <div>Tu carrito está vacío.</div>;
    }

    // Si todo salió bien, mostramos el contenido del carrito
    return (
        <div>
            <h2>Tu Carrito de Compras</h2>
            <table style={{ width: '100%', textAlign: 'left' }}>
                <thead>
                    <tr>
                        <th>Producto</th>
                        <th>Precio Unitario</th>
                        <th>Cantidad</th>
                        <th>Subtotal</th>
                    </tr>
                </thead>
                <tbody>
                    {cart.items.map(item => (
                        <tr key={item.product.id}>
                            <td>{item.product.name}</td>
                            <td>${item.product.price.toFixed(2)}</td>
                            <td>{item.quantity}</td>
                            <td>${(item.product.price * item.quantity).toFixed(2)}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <hr />
            <h3>Total: ${cart.totalPrice.toFixed(2)}</h3>
        </div>
    );
}

export default CartPage;