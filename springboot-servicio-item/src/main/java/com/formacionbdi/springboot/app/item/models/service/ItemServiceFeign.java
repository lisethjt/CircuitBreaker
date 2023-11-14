package com.formacionbdi.springboot.app.item.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formacionbdi.springboot.app.item.clientes.ProductoClienteRest;
import com.formacionbdi.springboot.app.item.models.Item;

@Service("serviceFeign")
public class ItemServiceFeign implements ItemService {

	@Autowired
	private ProductoClienteRest clienteFeign;

	@Override
	public Item findById(Long id, Integer cantidad) {
		return new Item(clienteFeign.detalle(id), cantidad);
	}

}
