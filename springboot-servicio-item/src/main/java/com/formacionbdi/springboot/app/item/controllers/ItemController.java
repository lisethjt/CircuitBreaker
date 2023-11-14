package com.formacionbdi.springboot.app.item.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.item.models.Producto;
import com.formacionbdi.springboot.app.item.models.service.ItemService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
public class ItemController {

	@Autowired
	@Qualifier("serviceFeign")
	private ItemService itemService;

	private final Logger logger = LoggerFactory.getLogger(ItemController.class);

	@CircuitBreaker(name = "items", fallbackMethod = "metodoAlternativo")
	@GetMapping("/ver/{id}/cantidad/{cantidad}")
	public Item detalle2(@PathVariable Long id, @PathVariable Integer cantidad) {
		return itemService.findById(id, cantidad);
	}

	public Item metodoAlternativo(Long id, Integer cantidad, Throwable e) {
		logger.info(e.getMessage());
		Item item = new Item();
		Producto producto = new Producto();

		item.setCantidad(cantidad);
		producto.setId(id);
		producto.setNombre("Camara Sony");
		producto.setPrecio(500.00);
		item.setProducto(producto);
		return item;
	}
}