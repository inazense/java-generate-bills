package com.programandoapasitos.facturador.participantes;

import java.util.Vector;

public class Cliente {

	// Propiedades
	private String dni;
	private String nombre;
	private String apellidos;
	private int codigoCliente;
	private Direccion direccion;
	private Vector<Telefono> telefonos;
	private Vector<Email> emails;
	
	// Constructores
	public Cliente() {}
	
	public Cliente(String dni, String nombre, String apellidos, int codigoCliente, Direccion direccion, Vector<Telefono> telefonos, Vector<Email> emails) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.codigoCliente = codigoCliente;
		this.direccion = direccion;
		this.telefonos = telefonos;
		this.emails = emails;
	}
	
	// Métodos
	
	public boolean agregarTelefono(Telefono telefono) {
		return telefonos.add(telefono);
	}
	
	public boolean borrarTelefono(Telefono telefono) {
		return telefonos.remove(telefono);
	}
	
	public boolean existeElTelefono(Telefono telefono) {
		return telefonos.contains(telefono);
	}
	
	public boolean agregarEmail(Email email) {
		return emails.add(email);
	}
	
	public boolean borrarEmails(Email email) {
		return emails.remove(email);
	}
	
	public boolean existeElEmail(Email email) {
		return emails.contains(email);
	}
	
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public int getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(int codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public Vector<Telefono> getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(Vector<Telefono> telefonos) {
		this.telefonos = telefonos;
	}

	public Vector<Email> getEmails() {
		return emails;
	}

	public void setEmails(Vector<Email> emails) {
		this.emails = emails;
	}
}
