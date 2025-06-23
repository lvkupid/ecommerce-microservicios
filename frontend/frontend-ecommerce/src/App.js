import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import ProductList from './components/ProductList'; // Un componente que ya tenés (renombralo si es necesario)
import ProductDetail from './components/ProductDetail'; // Un componente nuevo que vamos a crear
import CartPage from './components/CartPage'; // Un componente nuevo que vamos a crear

function App() {
  return (
    <Router>
      <div className="App">
        <header>
          <h1>Mi E-Commerce</h1>
          <nav>
            <Link to="/">Productos</Link> | <Link to="/cart">Carrito</Link> {/* Link a futuro */}
          </nav>
        </header>
        <main>
          <Routes>
            <Route path="/" element={<ProductList/>} />
            <Route path="/products/:id" element={<ProductDetail/>} />
            <Route path="/cart" element={<CartPage />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;
