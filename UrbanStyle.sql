-- Creación de la base de datos
CREATE DATABASE UrbanStyleDB;
USE UrbanStyleDB;

-- Tabla Usuarios
CREATE TABLE Usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    contraseña VARCHAR(255),
    rol ENUM('cliente', 'administrador'),
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Tabla Proveedores
CREATE TABLE Proveedores (
    id_proveedor INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    contacto VARCHAR(100),
    telefono VARCHAR(20),
    email VARCHAR(100),
    direccion TEXT,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Tabla Ropa
CREATE TABLE Ropa (
    id_ropa INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    descripcion TEXT,
    precio DECIMAL(10, 2),
    categoria ENUM('hombre', 'mujer', 'unisex'),
    stock INT,
    fecha_caducidad DATE,
    descuento DECIMAL(5, 2) DEFAULT 0,
    id_proveedor INT,
    fecha_agregado DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_proveedor) REFERENCES Proveedores(id_proveedor),
    tipo_producto ENUM('polo', 'pantalon', 'calzado', 'accesorio', 'polera')
);

-- Tabla Pedidos
CREATE TABLE Pedidos (
    id_pedido INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    total DECIMAL(10, 2),
    estado ENUM('pendiente', 'pagado', 'enviado', 'completado', 'cancelado'),
    fecha_pedido DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
);

-- Tabla Detalle_Pedido
CREATE TABLE Detalle_Pedido (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT,
    id_ropa INT,
    cantidad INT,
    precio_unitario DECIMAL(10, 2),
    FOREIGN KEY (id_pedido) REFERENCES Pedidos(id_pedido),
    FOREIGN KEY (id_ropa) REFERENCES Ropa(id_ropa)
);

-- Tabla Carrito
CREATE TABLE Carrito (
    id_carrito INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    id_ropa INT,
    cantidad INT,
    fecha_agregado DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario),
    FOREIGN KEY (id_ropa) REFERENCES Ropa(id_ropa)
);

-- Tabla Pagos
CREATE TABLE Pagos (
    id_pago INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT,
    metodo_pago ENUM('tarjeta_credito', 'tarjeta_debito', 'paypal', 'transferencia_bancaria'),
    estado_pago ENUM('pendiente', 'completado', 'fallido'),
    fecha_pago DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_pedido) REFERENCES Pedidos(id_pedido)
);

-- Tabla Alertas
CREATE TABLE Alertas (
    id_alerta INT AUTO_INCREMENT PRIMARY KEY,
    id_ropa INT,
    mensaje TEXT,
    fecha_alerta DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_ropa) REFERENCES Ropa(id_ropa),
    tipo_alerta ENUM('stock_bajo', 'caducidad_proxima', 'manual') NOT NULL DEFAULT 'manual',
    estado ENUM('activa', 'resuelta') NOT NULL DEFAULT 'activa',
    umbral INT DEFAULT NULL
);



-- Insertar Proveedores reales
INSERT INTO Proveedores (nombre, contacto, telefono, email, direccion) VALUES 
('Gamarra Fashion SAC', 'Roberto Gómez', '951234567', 'rgomez@gamarrafashion.pe', 'Jr. Gamarra 1253, La Victoria, Lima'),
('Urban Textiles Perú', 'Carmen Paredes', '962345678', 'cparedes@urbantextiles.pe', 'Jr. Antonio Bazo 789, La Victoria, Lima'),
('Lima Street Wholesale', 'Miguel Chang', '973456789', 'mchang@limastreet.pe', 'Av. Grau 423, La Victoria, Lima');

-- Insertar Ropa
INSERT INTO `ropa` (`id_ropa`, `nombre`, `descripcion`, `precio`, `categoria`, `tipo_producto`, `stock`, `fecha_caducidad`, `descuento`, `id_proveedor`, `fecha_agregado`) VALUES
(1, 'Sudadera Negra + Diseño ', 'Esta sudadera negra presenta un diseño moderno con un cuello tipo polo y detalles en las mangas. Tiene un estampado en el pecho y en las mangas, que incluye un logo y texto decorativo. La prenda es holgada y se combina con pantalones claros, creando un look casual y estilizado. El fondo neutro resalta la sudadera y el modelo, que tiene un peinado moderno.', 79.00, 'hombre', 'polera', 25, NULL, 0.00, 1, '2024-11-29 00:20:24'),
(2, 'Sudadera con Capucha Verde', ' Esta sudadera con capucha de color verde tiene un diseño urbano y contemporáneo. Presenta un estampado gráfico en el pecho y en las mangas, añadiendo un toque distintivo. El modelo lleva gafas de sol blancas, lo que complementa el estilo desenfadado. La sudadera se combina con pantalones oscuros, creando un look cómodo y moderno, ideal para un día casual. El fondo gris resalta la prenda y el modelo.', 79.00, 'hombre', 'polera', 25, NULL, 0.00, 1, '2024-11-29 00:20:24'),
(3, 'Sudadera Beige + Estampados', 'Esta sudadera de color beige presenta un diseño moderno con múltiples estampados en diferentes colores y estilos. Tiene un cuello tipo polo y un corte holgado, lo que la hace cómoda y estilizada. El modelo la combina con pantalones cargo, creando un look urbano y desenfadado. El fondo neutro resalta la prenda y el modelo, que tiene un peinado moderno.', 65.00, 'hombre', 'polera', 25, NULL, 0.00, 3, '2024-11-29 00:29:21'),
(4, 'Sudadera con Capucha Beige', ' Esta sudadera con capucha de color beige tiene un diseño casual y atractivo. Presenta un estampado gráfico en el pecho que incluye la frase \"FREE STADE\", añadiendo un toque distintivo. El modelo lleva gafas de sol, lo que complementa el estilo relajado. Se combina con pantalones oscuros, creando un look cómodo y moderno, ideal para un día casual.', 49.00, 'hombre', 'polera', 25, NULL, 0.00, 2, '2024-11-29 00:29:21'),
(5, 'Sudadera Basic ', 'Esta sudadera con capucha de color marrón tiene un diseño simple y elegante. Con un corte holgado y un bolsillo frontal, es perfecta para un look casual y cómodo. Su color cálido la convierte en una prenda versátil que se puede combinar fácilmente con diferentes estilos. Ideal para días frescos, esta sudadera es una opción esencial para cualquier guardarropa.', 49.00, 'hombre', 'polera', 25, NULL, 0.00, 1, '2024-11-29 00:29:21'),
(6, ' Sudadera Basic', ' Esta sudadera con capucha de color verde tiene un diseño simple y moderno. Con un corte holgado y un bolsillo frontal, es perfecta para un look casual y cómodo. Su color suave la convierte en una prenda versátil que se puede combinar fácilmente con diferentes estilos, ideal para días frescos.', 49.00, 'hombre', 'polera', 25, NULL, 0.00, 2, '2024-11-29 00:29:21'),
(7, 'Sudadera Negra + Estampado', ' Esta sudadera negra presenta un diseño urbano con un estampado gráfico en el pecho. Combinada con una banda roja en la cabeza, el modelo crea un look atrevido y contemporáneo. La sudadera es holgada y cómoda, ideal para un estilo casual y relajado.', 79.00, 'hombre', 'polera', 25, NULL, 0.00, 3, '2024-11-29 00:29:21'),
(8, 'Sudadera Beige con Capucha', 'Esta sudadera con capucha de color beige tiene un diseño minimalista y elegante. Con un corte holgado y un bolsillo frontal, es perfecta para un look casual. Se puede combinar con pantalones oscuros o claros, lo que la convierte en una opción versátil para cualquier ocasión.', 49.00, 'hombre', 'polera', 25, NULL, 0.00, 1, '2024-11-29 00:29:21'),
(9, 'Sudadera Blanca con Capucha', 'Esta sudadera con capucha de color blanco presenta un diseño limpio y moderno. Con un corte holgado y un estilo sencillo, es ideal para un look casual y fresco. Perfecta para combinar con jeans o pantalones de colores, esta sudadera es una opción esencial para cualquier guardarropa.', 70.00, 'hombre', 'polera', 25, NULL, 0.00, 1, '2024-11-29 00:29:21'),
(10, 'Sudadera Gris Claro con Capucha', 'Esta sudadera con capucha de color gris claro tiene un diseño clásico y cómodo. Con un corte holgado y un bolsillo frontal, es ideal para un look relajado. Su color neutro permite múltiples combinaciones, convirtiéndola en una prenda versátil para cualquier ocasión.', 49.00, 'hombre', 'polera', 25, NULL, 0.00, 3, '2024-11-29 00:29:21')


INSERT INTO `ropa` (`id_ropa`, `nombre`, `descripcion`, `precio`, `categoria`, `tipo_producto`, `stock`, `fecha_caducidad`, `descuento`, `id_proveedor`, `fecha_agregado`) VALUES
(11, 'Sudadera con Capucha Azul Claro', 'Esta sudadera con capucha de color azul claro presenta un diseño fresco y moderno. Con un corte holgado y un bolsillo frontal, es perfecta para un look casual y cómodo. Se combina con pantalones cortos, lo que la hace ideal para días cálidos. Su color suave aporta un toque alegre al outfit.', 49.00, 'hombre', 'polera', 25, NULL, 0.00, 3, '2024-11-29 00:35:41'),
(12, 'Sudadera Gris Oscuro + Detalles', 'Esta sudadera gris oscuro tiene un diseño moderno con detalles en negro y un corte holgado. Presenta un estilo deportivo con un estampado en el pecho y en las mangas. Ideal para un look urbano, se puede combinar con pantalones cargo o jeans, creando un estilo casual y atractivo.', 79.00, 'hombre', 'polera', 25, NULL, 0.00, 3, '2024-11-29 00:35:41'),
(13, 'Sudadera con Capucha Gris', 'Esta sudadera con capucha de color gris es una prenda básica y versátil. Con un diseño simple y un corte holgado, es perfecta para un look relajado. Se combina con pantalones cargo verdes, lo que añade un toque de estilo urbano. Ideal para días frescos y casuales.', 49.00, 'hombre', 'polera', 25, NULL, 0.00, 2, '2024-11-29 00:35:41'),
(14, 'Sudadera con Capucha Azul', 'Esta sudadera con capucha de color azul presenta un diseño sencillo y elegante. Con un bolsillo frontal y un corte holgado, es ideal para un look casual. Su color vibrante la convierte en una prenda llamativa que se puede combinar con jeans o pantalones cortos.', 35.00, 'hombre', 'polera', 25, NULL, 0.00, 2, '2024-11-29 00:35:41'),
(15, 'Sudadera con Capucha Verde Menta', 'Esta sudadera con capucha de color verde menta tiene un diseño moderno y fresco. Presenta un estampado en el pecho que dice \"BEFORE YOU\", añadiendo un toque distintivo. Con un corte holgado y un estilo cómodo, es perfecta para un look casual y relajado, ideal para combinar con pantalones oscuros o claros.', 70.00, 'hombre', 'polera', 25, NULL, 0.00, 2, '2024-11-29 00:35:41'),
(16, 'Sudadera con Capucha Negra', ' Esta sudadera con capucha de color negro presenta un diseño clásico y minimalista. Con un corte holgado y un bolsillo frontal, es perfecta para un look casual y cómodo. El modelo la combina con pantalones cargo de mezclilla, creando un estilo urbano y desenfadado. Ideal para días frescos, esta sudadera es una opción versátil que se adapta a diversas ocasiones.', 49.00, 'hombre', 'polera', 25, NULL, 0.00, 1, '2024-11-29 00:35:41'),
(20, 'Polo Rebelde Worldwide', 'Este polo deportivo de manga corta es una prenda moderna y versátil, ideal para quienes buscan comodidad y estilo. Con un diseño en color negro, presenta detalles en blanco que le añaden un toque distintivo.', 49.00, 'hombre', 'polo', 25, '2025-01-01', 0.00, 3, '2024-11-28 09:32:59'),
(21, 'Camiseta Gráfica Oversize \"Romance\"', 'Esta camiseta oversize de color negro es una prenda llamativa y moderna, ideal para quienes buscan un estilo urbano y desenfadado. Con un diseño gráfico vibrante en la parte frontal, destaca por su mensaje audaz y su estética contemporánea.', 35.00, 'hombre', 'polo', 25, '2025-01-01', 0.00, 1, '2024-11-28 09:32:59'),
(23, 'Polo Oversize + Estampado\r\n', 'Esta camiseta de algodón de alta calidad es perfecta para el uso diario. Con un diseño clásico y cómodo, es ideal para cualquier ocasión, ya sea para salir con amigos, hacer ejercicio o simplemente relajarse en casa.', 35.00, 'hombre', 'polo', 25, '2025-01-01', 0.00, 1, '2024-11-27 20:54:09'),
(24, 'Polo Camisero Deportivo', 'Este polo deportivo combina un diseño moderno con comodidad, ideal para quienes buscan un estilo casual y urbano. Con un color base negro y detalles en blanco, es perfecto para cualquier ocasión, ya sea para hacer ejercicio o para un look diario.', 49.00, 'hombre', 'polo', 25, NULL, 0.00, 2, '2024-11-28 09:59:51'),
(25, 'Camiseta Gráfica \"Dream Car 1985\"', 'La camiseta \"Dream Car 1985\" es una prenda única que combina un estilo retro con un diseño moderno. Con un color beige suave, esta camiseta es perfecta para quienes buscan un look casual y distintivo.', 35.00, 'hombre', 'polo', 25, '2025-01-01', 0.00, 1, '2024-11-28 09:59:51'),
(26, 'Polo Oversize \"Los Angeles\"', 'La camiseta \"Los Angeles\" es una prenda casual que combina un diseño moderno con un toque retro. Con un color verde oscuro, esta camiseta es perfecta para quienes buscan un estilo relajado y urbano.', 35.00, 'hombre', 'polo', 25, NULL, 0.00, 1, '2024-11-28 09:59:51'),
(27, 'Polo Oversize \"Free Spirit\"', '\r\nLa camiseta \"Free Spirit\" es una prenda única que combina un diseño artístico con un estilo moderno. Con un color blanco puro, esta camiseta es perfecta para quienes buscan un look fresco y distintivo.', 35.00, 'hombre', 'polo', 25, NULL, 0.00, 1, '2024-11-28 09:59:51'),
(28, 'Polo Boxyfit \"Magníficas\"', 'La camiseta \"Magníficas\" es una prenda que combina un diseño vibrante con un estilo relajado. Con un color blanco brillante, esta camiseta es perfecta para quienes buscan un look fresco y atractivo, ideal para el verano.', 35.00, 'hombre', 'polo', 25, NULL, 0.00, 2, '2024-11-28 10:38:42'),
(29, 'Polo Oversize \"Hunter\"', 'La camiseta \"Hunter\" es una prenda audaz y moderna que combina un diseño impactante con un estilo urbano. Con un color negro profundo, esta camiseta es perfecta para quienes buscan un look atrevido y distintivo.', 35.00, 'hombre', 'polo', 25, NULL, 0.00, 1, '2024-11-28 10:38:42'),
(30, 'Polo Oversize \"Free Love\"', 'La camiseta \"Free Love\" es una prenda fresca y vibrante que combina un diseño alegre con un estilo relajado. Con un color blanco brillante, esta camiseta es perfecta para quienes buscan un look veraniego y lleno de energía.', 35.00, 'hombre', 'polo', 25, NULL, 0.00, 2, '2024-11-28 10:38:42')



INSERT INTO `ropa` (`id_ropa`, `nombre`, `descripcion`, `precio`, `categoria`, `tipo_producto`, `stock`, `fecha_caducidad`, `descuento`, `id_proveedor`, `fecha_agregado`) VALUES
(31, 'Polo Oversize \"Chasing Dreams\"\r\n', 'La camiseta \"Chasing Dreams\" es una prenda moderna y relajada que combina un diseño atractivo con un estilo casual. Con un color gris suave, esta camiseta es perfecta para quienes buscan un look cómodo y a la moda.', 35.00, 'hombre', 'polo', 25, NULL, 0.00, 1, '2024-11-28 10:46:12'),
(32, 'Polo Cuello Camisero Holgado', 'Esta camisa casual de manga corta en un elegante tono verde oliva es una prenda versátil y moderna, ideal para quienes buscan un look fresco y relajado. Perfecta para cualquier ocasión, desde salidas informales hasta reuniones más relajadas.', 49.00, 'hombre', 'polo', 25, NULL, 0.00, 2, '2024-11-28 10:46:12'),
(33, 'Polo Cuello Camisero Holgado', 'Esta camisa casual de manga corta es una prenda versátil y elegante, ideal para quienes buscan un look moderno y cómodo. Con un color negro clásico, es perfecta para cualquier ocasión, desde salidas informales hasta eventos más relajados.', 49.00, 'hombre', 'polo', 25, NULL, 0.00, 3, '2024-11-28 10:46:12'),
(34, 'Polo Camisero Holgado', 'Esta camisa casual de manga corta en un elegante tono gris es una prenda versátil y moderna, ideal para quienes buscan un look fresco y relajado. Perfecta para cualquier ocasión, desde salidas informales hasta reuniones más relajadas.', 49.00, 'hombre', 'polo', NULL, NULL, 0.00, 3, '2024-11-28 10:49:06'),
(35, 'Polo Camisero Holgado', 'Esta camisa casual de manga corta en un elegante tono blanco es una prenda versátil y moderna, ideal para quienes buscan un look fresco y sofisticado. Perfecta para cualquier ocasión, desde salidas informales hasta eventos más relajados.', 49.00, 'hombre', 'polo', 25, NULL, 0.00, 3, '2024-11-28 10:49:06'),
(40, 'Pantalones Cargo Verde', 'Estos pantalones cargo de color verde presentan un diseño moderno y funcional. Con múltiples bolsillos y un corte holgado, son ideales para un look casual y urbano. Perfectos para combinar con sudaderas o camisetas, estos pantalones son una opción versátil para cualquier ocasión.', 75.00, 'hombre', 'pantalon', 25, NULL, 0.00, 1, '2024-11-29 01:32:21'),
(41, 'Pantalones Cargo Camuflaje', 'Estos pantalones cargo de camuflaje son ideales para un look aventurero. Con un diseño holgado y múltiples bolsillos, ofrecen funcionalidad y estilo. Perfectos para combinar con camisetas o sudaderas, son una opción versátil para cualquier ocasión.', 75.00, 'hombre', 'pantalon', 25, NULL, 0.00, 3, '2024-11-29 01:32:21'),
(42, 'Pantalones Cargo Negro', 'Estos pantalones cargo de color negro son una prenda esencial para un look urbano. Con un diseño holgado y múltiples bolsillos, ofrecen comodidad y estilo. Perfectos para combinar con camisetas o sudaderas, son ideales para cualquier ocasión casual.', 75.00, 'hombre', 'pantalon', 25, NULL, 0.00, 2, '2024-11-29 01:32:21'),
(43, 'Pantalones Cargo Beige', 'Estos pantalones cargo de color beige presentan un diseño moderno y funcional. Con un corte holgado y múltiples bolsillos, son ideales para un look casual y práctico. Perfectos para combinar con sudaderas o camisetas, son una opción versátil para cualquier ocasión.', 75.00, 'hombre', 'pantalon', 3, NULL, 0.00, 3, '2024-11-29 01:32:21'),
(44, 'Pantalones de Chándal Gris', 'Estos pantalones de chándal de color gris son perfectos para un look deportivo. Con un diseño cómodo y un corte holgado, son ideales para actividades al aire libre o días de descanso. Perfectos para combinar con sudaderas o camisetas, son una opción versátil.', 75.00, 'hombre', 'pantalon', 25, NULL, 0.00, 2, '2024-11-29 01:32:21'),
(45, 'Pantalones Jogger Azul', 'Estos pantalones jogger de color azul son cómodos y modernos. Con un diseño holgado y puños en los tobillos, son ideales para un look casual y relajado. Perfectos para combinar con camisetas o sudaderas, son una opción versátil para cualquier ocasión.', 75.00, 'hombre', 'pantalon', 25, NULL, 0.00, 1, '2024-11-29 01:32:21'),
(46, 'Jeans Grises Holgados', 'Estos jeans de color gris presentan un diseño holgado y moderno. Con un corte cómodo y un estilo casual, son ideales para combinar con camisetas o sudaderas. Perfectos para un look relajado y urbano, estos jeans son una opción versátil para cualquier ocasión.', 75.00, 'hombre', 'pantalon', 25, NULL, 0.00, 1, '2024-11-29 01:38:06'),
(47, 'Jeans Holgados de Color Claro', 'Estos jeans de color claro tienen un diseño holgado y cómodo. Con un estilo casual y relajado, son ideales para combinar con camisetas o sudaderas. Perfectos para un look veraniego, estos jeans son una opción versátil para cualquier ocasión.', 75.00, 'hombre', 'pantalon', 25, NULL, 0.00, 3, '2024-11-29 01:38:06'),
(48, 'Jeans de Mezclilla Oscura', 'Estos jeans de mezclilla oscura presentan un diseño elegante y moderno. Con un corte recto y cómodo, son ideales para un look casual o semi-formal. Perfectos para combinar con camisetas o camisas, son una opción versátil para cualquier ocasión.', 75.00, 'hombre', 'pantalon', 25, NULL, 0.00, 2, '2024-11-29 01:38:06'),
(49, 'Jeans Negros Ajustados', 'Estos jeans de color negro tienen un diseño ajustado que realza la figura....(5929 chars omitted)...ión.', 49.00, 'mujer', 'pantalon', 25, NULL, 0.00, 3, '2024-11-29 01:07:03')



INSERT INTO `ropa` (`id_ropa`, `nombre`, `descripcion`, `precio`, `categoria`, `tipo_producto`, `stock`, `fecha_caducidad`, `descuento`, `id_proveedor`, `fecha_agregado`) VALUES
(50, 'Jeans Desgastados', 'Estos jeans de color azul desgastado tienen un diseño moderno y relajado. Con un corte holgado y detalles de desgaste, son ideales para un look casual y desenfadado. Perfectos para combinar con camisetas o sudaderas, son una opción versátil para cualquier ocasión.', 75.00, 'hombre', 'pantalon', 25, NULL, 0.00, 1, '2024-11-29 01:38:06'),
(51, 'Pantalones Deportivos Negros con Rayas', 'Estos pantalones deportivos de color negro presentan un diseño moderno con rayas blancas a los lados. Con un corte holgado y cómodo, son ideales para un look casual y deportivo. Perfectos para combinar con camisetas o sudaderas, son una opción versátil para cualquier ocasión.', 75.00, 'hombre', 'pantalon', 25, NULL, 0.00, 1, '2024-11-29 01:50:06'),
(52, 'Pantalones Deportivos Marrón con Rayas', 'Estos pantalones deportivos de color marrón presentan un diseño moderno con rayas blancas a los lados. Con un corte holgado y cómodo, son ideales para un look casual y deportivo. Perfectos para combinar con camisetas o sudaderas, son una opción versátil para cualquier ocasión.', 75.00, 'hombre', 'pantalon', 25, NULL, 0.00, 1, '2024-11-29 01:50:06'),
(53, 'Pantalones Deportivos Verde con Rayas', 'Estos pantalones deportivos de color verde presentan un diseño moderno con rayas blancas a los lados. Con un corte holgado y cómodo, son ideales para un look casual y deportivo. Perfectos para combinar con camisetas o sudaderas, son una opción versátil para cualquier ocasión.', 75.00, 'hombre', 'pantalon', 25, NULL, 0.00, 1, '2024-11-29 01:50:06'),
(54, 'Pantalones Deportivos Azul con Rayas', 'Estos pantalones deportivos de color azul presentan un diseño moderno con rayas blancas a los lados. Con un corte holgado y cómodo, son ideales para un look casual y deportivo. Perfectos para combinar con camisetas o sudaderas, son una opción versátil para cualquier ocasión.', 75.00, 'hombre', 'pantalon', 25, NULL, 0.00, 1, '2024-11-29 01:50:06'),
(55, 'Pantalones Jogger Beige', 'Estos pantalones jogger de color beige presentan un diseño moderno y cómodo. Con un corte holgado y puños en los tobillos, son ideales para un look casual y relajado. Perfectos para combinar con camisetas o sudaderas, son una opción versátil para cualquier ocasión.', 75.00, 'hombre', 'pantalon', 25, NULL, 0.00, 3, '2024-11-29 01:50:06'),
(60, 'Sudadera con Media Crema Azul', 'Esta sudadera con media cremallera de color azul presenta un diseño moderno y deportivo. Con un corte holgado y un estampado que dice \"NICE LOS ANGELES\", es perfecta para un look casual y cómodo. Ideal para combinar con pantalones cortos o jeans, esta sudadera es una opción versátil para cualquier ocasión.', 49.00, 'mujer', 'polera', 25, NULL, 0.00, 1, '2024-11-29 01:00:18'),
(61, 'Sudadera Oversize Negra con Detalles', 'Esta sudadera oversize de color negro presenta un diseño moderno con detalles en blanco y un estampado que dice \"SMARTTOOTH\". Con un corte holgado, es ideal para un look casual y desenfadado. Perfecta para combinar con pantalones cortos o jeans, esta sudadera es una opción cómoda y estilosa.', 49.00, 'mujer', 'polera', 25, NULL, 0.00, 1, '2024-11-29 01:00:18'),
(62, 'Sudadera con Media Crema Negra y Rosa', 'Esta sudadera con media cremallera de color negro presenta detalles en rosa y un diseño deportivo. Con un corte holgado y un estampado en el pecho, es perfecta para un look casual y moderno. Ideal para combinar con pantalones deportivos o jeans, esta sudadera es una opción versátil y cómoda.', 49.00, 'mujer', 'polera', 25, NULL, 0.00, 1, '2024-11-29 01:00:18'),
(63, 'Sudadera Marrón con Estampado \"Wellness\"', 'Esta sudadera de color marrón presenta un diseño moderno con el texto \"Wellness\" en un estilo llamativo. Con un corte holgado y cómodo, es ideal para un look casual. Perfecta para combinar con jeans o pantalones deportivos, esta sudadera es una opción versátil para cualquier ocasión.', 49.00, 'mujer', 'polera', 25, NULL, 0.00, 1, '2024-11-29 01:07:03')




INSERT INTO `ropa` (`id_ropa`, `nombre`, `descripcion`, `precio`, `categoria`, `tipo_producto`, `stock`, `fecha_caducidad`, `descuento`, `id_proveedor`, `fecha_agregado`) VALUES
(64, 'Sudadera Gris Claro con Estampado \"Wellness\"', 'Esta sudadera de color gris claro presenta un diseño moderno con el texto \"Wellness\" en verde. Con un corte holgado y cómodo, es ideal para un look casual. Perfecta para combinar con jeans o pantalones deportivos, esta sudadera es una opción versátil para cualquier ocasión.', 49.00, 'mujer', 'polera', 25, NULL, 0.00, 2, '2024-11-29 01:07:03'),
(65, 'Sudadera Negra con Estampado \"Wellness\"', 'Esta sudadera de color negro presenta un diseño moderno con el texto \"Wellness\" en blanco. Con un corte holgado y cómodo, es ideal para un look casual. Perfecta para combinar con jeans o pantalones deportivos, esta sudadera es una opción versátil para cualquier ocasión.', 49.00, 'mujer', 'polera', 25, NULL, 0.00, 3, '2024-11-29 01:07:03'),
(66, 'Sudadera con Capucha Gris Claro', 'Esta sudadera con capucha de color gris claro presenta un diseño simple y elegante. Con un corte holgado y un bolsillo frontal, es perfecta para un look casual. Ideal para combinar con pantalones oscuros o claros, esta sudadera es una opción esencial para cualquier guardarropa.', 49.00, 'mujer', 'polera', 25, NULL, 0.00, 1, '2024-11-29 01:07:03'),
(67, 'Sudadera con Capucha Beige', 'Esta sudadera con capucha de color beige tiene un diseño moderno y cómodo. Con un corte holgado y un estilo relajado, es ideal para un look casual. Perfecta para combinar con jeans o pantalones cortos, esta sudadera es una opción versátil para cualquier ocasión.', 49.00, 'mujer', 'polera', 25, NULL, 0.00, 2, '2024-11-29 01:07:03'),
(68, 'Sudadera con Capucha Verde', 'Esta sudadera con capucha de color verde tiene un diseño moderno y fresco. Con un corte holgado y un estilo cómodo, es perfecta para un look casual. Ideal para combinar con pantalones deportivos o jeans, esta sudadera es una opción versátil para cualquier ocasión.', 49.00, 'mujer', 'polera', 25, NULL, 0.00, 3, '2024-11-29 01:07:03'),
(69, 'Sudadera con Media Crema Azul', 'Esta sudadera con media cremallera de color azul presenta un diseño moderno y deportivo. Con un corte holgado y un estampado que dice \"WELLNESS\", es perfecta para un look casual y cómodo. Ideal para combinar con pantalones cortos o jeans, esta sudadera es una opción versátil para cualquier ocasión.', 49.00, 'mujer', 'polera', 25, NULL, 0.00, 1, '2024-11-29 01:07:03'),
(70, 'Sudadera con Media Crema Gris', 'Esta sudadera con media cremallera de color gris presenta un diseño moderno y deportivo. Con un corte holgado y un estampado que dice \"WELLNESS\", es perfecta para un look casual y cómodo. Ideal para combinar con pantalones cortos o jeans, esta sudadera es una opción versátil para cualquier ocasión.', 49.00, 'mujer', 'polera', 25, NULL, 0.00, 2, '2024-11-29 01:07:03'),
(71, 'Sudadera con Media Crema Negra', 'Esta sudadera con media cremallera de color negro presenta un diseño moderno y deportivo. Con un corte holgado y un estampado que dice \"WELLNESS\", es perfecta para un look casual y cómodo. Ideal para combinar con pantalones cortos o jeans, esta sudadera es una opción versátil para cualquier ocasión.', 49.00, 'mujer', 'polera', 25, NULL, 0.00, 3, '2024-11-29 01:07:03'),
(72, 'Sudadera Gris con Estampado \"Save Without Limits\"', 'Esta sudadera de color gris presenta un diseño moderno con el texto \"Save Without Limits\" en la parte posterior. Con un corte holgado y cómodo, es ideal para un look casual y urbano. Perfecta para combinar con jeans o pantalones deportivos, esta sudadera es una opción versátil para cualquier ocasión.', 49.00, 'mujer', 'polera', 25, NULL, 0.00, 1, '2024-11-29 01:12:23'),
(73, 'Sudadera Azul Marino con Estampado \"Goals\"', 'Esta sudadera de color azul marino presenta un diseño moderno con el texto \"Goals\" en un estilo llamativo. Con un corte holgado y cómodo, es ideal para un look casual y deportivo. Perfecta para combinar con pantalones cortos o jeans, esta sudadera es una opción versátil para cualquier ocasión.', 49.00, 'mujer', 'polera', 25, NULL, 0.00, 2, '2024-11-29 01:12:23')



INSERT INTO `ropa` (`id_ropa`, `nombre`, `descripcion`, `precio`, `categoria`, `tipo_producto`, `stock`, `fecha_caducidad`, `descuento`, `id_proveedor`, `fecha_agregado`) VALUES
(74, 'Sudadera Marrón con Estampado \"Goals\"', 'Esta sudadera de color marrón presenta un diseño moderno con el texto \"Goals\" en un estilo llamativo. Con un corte holgado y cómodo, es ideal para un look casual y deportivo. Perfecta para combinar con pantalones cortos o jeans, esta sudadera es una opción versátil para cualquier ocasión.', 49.00, 'unisex', 'polera', 25, NULL, 0.00, 1, '2024-11-29 01:14:04'),
(75, 'Sudadera Verde con Estampado \"Goals\"', 'Esta sudadera de color verde presenta un diseño moderno con el texto \"Goals\" en un estilo llamativo. Con un corte holgado y cómodo, es ideal para un look casual y deportivo. Perfecta para combinar con pantalones cortos o jeans, esta sudadera es una opción versátil para cualquier ocasión.', 49.00, 'unisex', 'polera', 25, NULL, 0.00, 2, '2024-11-29 01:14:42'),
(80, 'Polo Oversize \"Rave\"', 'La camiseta \"Rave\" es una prenda moderna y audaz que combina un diseño artístico con un estilo urbano. Con un color azul marino profundo, esta camiseta es perfecta para quienes buscan un look contemporáneo y lleno de personalidad.', 35.00, 'mujer', 'polo', NULL, NULL, 0.00, 2, '2024-11-28 11:09:50'),
(81, 'Polo Negro \"Mystical Dreams\"', 'La camiseta \"Mystical Dreams\" es una prenda moderna y llamativa que combina un diseño artístico con un estilo casual. Con un color negro profundo, esta camiseta es perfecta para quienes buscan un look fresco y lleno de personalidad.', 35.00, 'mujer', 'polo', 25, NULL, 0.00, 3, '2024-11-28 11:09:50'),
(82, 'Polo Manga Larga Sibylla', 'Esta camiseta de manga larga negra es perfecta para un look moderno y casual. Su diseño ajustado y cropped resalta la figura, mientras que el tejido suave proporciona comodidad durante todo el día. Ideal para combinar con pantalones de tiro alto o faldas, es una prenda versátil que se adapta a diversas ocasiones, desde salidas informales hasta reuniones con amigos.', 25.00, 'mujer', 'polo', 25, NULL, 0.00, 3, '2024-11-28 21:57:34'),
(83, 'Polo Crop Azul con Estampado', 'Este polo crop de color azul presenta un llamativo estampado de esqueletos, lo que le da un toque único y atrevido. Con un diseño de mangas amplias y un corte corto, es ideal para quienes buscan un estilo moderno y desenfadado. Perfecto para combinar con pantalones de tiro alto o faldas, este polo es una opción versátil que añade personalidad a cualquier outfit. Ideal para un look casual y divertido.', 35.00, 'mujer', 'polo', 25, NULL, 0.00, 3, '2024-11-28 22:25:07'),
(84, 'Polo Oversize ', 'Esta camiseta oversize de color lila es una opción fresca y moderna para el verano. Con un diseño de mangas amplias y un corte holgado, ofrece comodidad y estilo. La prenda se complementa con accesorios como gafas de sol blancas y una cadena en el hombro, lo que añade un toque chic. Perfecta para un look casual, se puede combinar con shorts o pantalones ajustados para un estilo desenfadado y atractivo.', 35.00, 'mujer', 'polo', 25, NULL, 0.00, 2, '2024-11-28 21:57:34'),
(85, 'Polo Oversize Beige', 'Este polo oversize de color beige es una prenda versátil y cómoda, ideal para un look casual. Con un diseño de mangas amplias y un corte holgado, se adapta perfectamente a diferentes estilos. Se complementa con accesorios como gafas de sol y una cadena, añadiendo un toque moderno. Perfecto para combinar con shorts o jeans, este polo es ideal para días relajados y salidas informales.', NULL, 'mujer', 'polo', 25, NULL, 0.00, 2, '2024-11-28 22:26:43'),
(86, 'Polo Oversize Negro', 'Este polo oversize de color negro es una prenda esencial para cualquier guardarropa. Con un diseño de mangas amplias y un corte holgado, ofrece comodidad y estilo. Su color neutro permite múltiples combinaciones, y se puede llevar con accesorios llamativos, como gafas de sol. Ideal para un look casual y moderno, este polo es perfecto para días relajados o salidas informales, combinándolo con shorts o pantalones ajustados.', 35.00, 'mujer', 'polo', NULL, NULL, 0.00, 2, '2024-11-28 22:26:43'),
(87, 'Polo Oversize  + Estampado', 'Este polo negro presenta un divertido y llamativo estampado inspirado en la serie \"South Park\", con la famosa frase \"Oh my God, they killed Kenny!\" y gráficos coloridos. Con un diseño de mangas amplias y un corte holgado, es perfecto para quienes buscan un estilo casual y desenfadado. Ideal para combinar con jeans o pantalones cortos, este polo es una excelente opción para los fanáticos de la serie y para quienes desean añadir un toque de humor a su vestuario.', 35.00, 'mujer', 'polo', 25, NULL, 0.00, 3, '2024-11-28 22:32:11')




-- Consulta para obtener el reporte de ventas
SELECT 
    CONCAT('VTA-', LPAD(p.id_pedido, 3, '0')) as id_venta,
    DATE_FORMAT(p.fecha_pedido, '%d/%m/%Y') as fecha,
    u.nombre as cliente,
    GROUP_CONCAT(
        CONCAT(r.nombre, 
        CASE 
            WHEN dp.cantidad > 1 THEN CONCAT(' x', dp.cantidad)
            ELSE ''
        END)
        SEPARATOR ', '
    ) as productos,
    p.total,
    p.estado
FROM 
    Pedidos p
    INNER JOIN Usuarios u ON p.id_usuario = u.id_usuario
    INNER JOIN Detalle_Pedido dp ON p.id_pedido = dp.id_pedido
    INNER JOIN Ropa r ON dp.id_ropa = r.id_ropa
WHERE 
    p.estado IN ('pagado', 'completado')
GROUP BY 
    p.id_pedido, p.fecha_pedido, u.nombre, p.total, p.estado
ORDER BY 
    p.fecha_pedido DESC;



DELIMITER //

CREATE PROCEDURE GenerarAlertasAutomaticas()
BEGIN
    -- Primero, actualizar el estado de las alertas existentes
    UPDATE Alertas a
    INNER JOIN Ropa r ON a.id_ropa = r.id_ropa
    SET a.estado = 'resuelta'
    WHERE a.estado = 'activa' 
    AND (
        (a.tipo_alerta = 'stock_bajo' AND r.stock > 30) OR
        (a.tipo_alerta = 'caducidad_proxima' AND DATEDIFF(r.fecha_caducidad, CURDATE()) > 30)
    );

    -- Luego, generar nuevas alertas por stock bajo
    INSERT INTO Alertas (id_ropa, mensaje, tipo_alerta, estado, umbral)
    SELECT 
        id_ropa,
        CONCAT('Stock bajo: ', nombre, ' (', stock, ' unidades)'),
        'stock_bajo',
        'activa',
        30
    FROM Ropa
    WHERE stock <= 30
    AND id_ropa NOT IN (
        SELECT id_ropa 
        FROM Alertas 
        WHERE tipo_alerta = 'stock_bajo' 
        AND estado = 'activa'
    );

    -- Generar alertas por caducidad próxima
    INSERT INTO Alertas (id_ropa, mensaje, tipo_alerta, estado, umbral)
    SELECT 
        id_ropa,
        CONCAT('¡Producto próximo a caducar! ', nombre, ' caduca el ', DATE_FORMAT(fecha_caducidad, '%d/%m/%Y')),
        'caducidad_proxima',
        'activa',
        30
    FROM Ropa
    WHERE DATEDIFF(fecha_caducidad, CURDATE()) <= 30
    AND id_ropa NOT IN (
        SELECT id_ropa 
        FROM Alertas 
        WHERE tipo_alerta = 'caducidad_proxima' 
        AND estado = 'activa'
    );
END //

DELIMITER ;

-- 4. Crear el nuevo trigger
DELIMITER //

CREATE TRIGGER after_ropa_update 
AFTER UPDATE ON Ropa
FOR EACH ROW
BEGIN
    IF NEW.stock != OLD.stock OR NEW.fecha_caducidad != OLD.fecha_caducidad THEN
        CALL GenerarAlertasAutomaticas();
    END IF;
END //

DELIMITER ;















/* ignorar */

-- Insertar pedidos de prueba usando los usuarios existentes
INSERT INTO Pedidos (id_usuario, total, estado) VALUES 
(33, 458.70, 'completado'),  -- José Martínez
(34, 269.80, 'pagado');      -- Ana López

-- Insertar detalles de pedido
INSERT INTO Detalle_Pedido (id_pedido, id_ropa, cantidad, precio_unitario) VALUES 
(1, 1, 2, 159.90),  -- 2 Urban Hoodie Oversize para José Martínez
(1, 5, 1, 179.90),  -- 1 Cargo Pants Urban para José Martínez
(2, 7, 1, 189.90),  -- 1 Mom Jeans Vintage para Ana López
(2, 10, 1, 79.90);  -- 1 Crop Top Urban Art para Ana López


-- Consulta para ver las fechas actuales
SELECT id_pedido, fecha_pedido FROM Pedidos;

-- Si necesitas actualizar las fechas:
UPDATE Pedidos SET fecha_pedido = '2024-11-13' WHERE id_pedido = 1;
UPDATE Pedidos SET fecha_pedido = '2024-11-14' WHERE id_pedido = 2;


-- Desactivar temporalmente el modo seguro
SET SQL_SAFE_UPDATES = 0;

-- Limpiamos las tablas en orden para evitar problemas con las llaves foráneas
DELETE FROM Alertas;
DELETE FROM Pagos;
DELETE FROM Detalle_Pedido;
DELETE FROM Pedidos;
DELETE FROM Carrito;
DELETE FROM Ropa;
DELETE FROM Proveedores;

-- Reiniciamos los auto_increment
ALTER TABLE Alertas AUTO_INCREMENT = 1;
ALTER TABLE Pagos AUTO_INCREMENT = 1;
ALTER TABLE Detalle_Pedido AUTO_INCREMENT = 1;
ALTER TABLE Pedidos AUTO_INCREMENT = 1;
ALTER TABLE Carrito AUTO_INCREMENT = 1;
ALTER TABLE Ropa AUTO_INCREMENT = 1;
ALTER TABLE Proveedores AUTO_INCREMENT = 1;

-- Volver a activar el modo seguro
SET SQL_SAFE_UPDATES = 1;