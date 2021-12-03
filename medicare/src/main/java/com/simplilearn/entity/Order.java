package com.simplilearn.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	
	@Column(name = "payment_status")
	private boolean paymentStatus;

	public Order() {
 
	}

	public Order(User user, Product product) {
		super();
		this.user = user;
		this.product = product;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setPaymentStatus(boolean status) {
		this.paymentStatus = status;
	}
	
	public boolean getPaymentStatus() {
		return this.paymentStatus;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", user=" + user +", paymentStatus=" + paymentStatus + ", product=" + product + "]";
	}
	
	
	
	

}
