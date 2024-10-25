package com.mycompany.aplicativowebintegrador.servicio;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import com.mycompany.aplicativowebintegrador.modelo.Producto;

public class ProductoServicio {
    private LoadingCache<String, List<Producto>> bestSellersCache;

    public ProductoServicio() {
        bestSellersCache = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS)
            .build(new CacheLoader<String, List<Producto>>() {
                @Override
                public List<Producto> load(String key) {
                    // Aquí iría la lógica para obtener los productos más vendidos de la base de datos
                    return obtenerProductosMasVendidosDeBD();
                }
            });
    }

    public void agregarProducto(Producto producto) {
        Preconditions.checkNotNull(producto, "El producto no puede ser nulo");
        // Lógica para agregar el producto
    }

    public List<Producto> obtenerProductos() {
        // Supongamos que obtenemos los productos de alguna fuente
        List<Producto> productos = new ArrayList<>();
        // Aquí iría la lógica para obtener los productos
        return ImmutableList.copyOf(productos);
    }

    public List<Producto> obtenerProductosMasVendidos() {
        try {
            return bestSellersCache.get("bestSellers");
        } catch (Exception e) {
            // Manejar la excepción apropiadamente
            return List.of(); // Retorna una lista vacía en caso de error
        }
    }

    private List<Producto> obtenerProductosMasVendidosDeBD() {
        // Implementa la lógica para obtener los productos más vendidos de la base de datos
        // Este es solo un ejemplo
        return List.of(
            new Producto("Polo OverSize + Diseño", 45.0),
            new Producto("Baggy Jean Street", 99.0),
            new Producto("Polera + Diseño", 99.0)
        );
    }
}
