
package com.simplilearn.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.simplilearn.dao.OrderDAO;
import com.simplilearn.dao.ProductDAO;
import com.simplilearn.entity.Order;
import com.simplilearn.entity.Product;
import com.simplilearn.entity.User;

@Controller
@RequestMapping("/homepage")
public class HomePageController {

	// need to inject
	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private OrderDAO orderDAO;
	
	
	@GetMapping("/payment")
	public String payment(Model theModel, HttpServletRequest request) {
		
		return "payment";
				
	}

	@GetMapping("/payment-success")
	public String paymentSuccess(Model theModel, HttpServletRequest request) {
		
		HttpSession session = request.getSession(true);
		User currentUser = (User) session.getAttribute("currentUser");
		

		// get all products from DAO
		List<Order> userOrders = orderDAO.getUserOrders(currentUser.getId());
		List<Product> userProducts = new ArrayList<Product>();

		for (Order order : userOrders) {
			System.out.println(order.getPaymentStatus());
			if(!order.getPaymentStatus()) {
				userProducts.add(order.getProduct());				
			}
		}

		int total_price = 0;

		for (Product product : userProducts) {
			total_price = total_price + product.getPrice();
		}

		theModel.addAttribute("userProducts", userProducts);
		theModel.addAttribute("total_price", total_price);
		theModel.addAttribute("currentUser", currentUser);
		orderDAO.payOrder(currentUser.getId());
		return "successPayment";
				
	}

	
	@GetMapping("/products")
	public String products(Model theModel, HttpServletRequest request,@RequestParam Map<String, String> queryParams) {

		HttpSession session = request.getSession(true);
		User currentUser = (User) session.getAttribute("currentUser");

		// get all products from DAO
		String  temp = queryParams.get("sort");
		List<Product> products;
		if(temp != null && temp.compareToIgnoreCase("true") == 0) {
			products = productDAO.getProducts(true);
		
		}else {
			products = productDAO.getProducts(false);
		}
		System.out.println(queryParams.toString());
		 
		List<Order> userOrders = orderDAO.getUserOrders(currentUser.getId());
		List<Integer> userProducts = new ArrayList<Integer>();

		for (Order order : userOrders) {
			if(!order.getPaymentStatus()) {
				userProducts.add(order.getProduct().getId());				
			}
		}

		// add the products to the model
		theModel.addAttribute("products", products);
		theModel.addAttribute("userProducts", userProducts);
		theModel.addAttribute("currentUser", currentUser);

		return "user-home";
	}

	@GetMapping("/orderProcess")
	public String orderProcess(Model theModel,@RequestParam("productId") int productId,@RequestParam("redirect") String isRedirect, HttpServletRequest request) {

		HttpSession session = request.getSession(true);
		User currentUser = (User) session.getAttribute("currentUser");
		Product product = productDAO.getProduct(productId);

		Order order = new Order(currentUser, product);
		List<Order> userOrders = orderDAO.getUserOrders(currentUser.getId());
		System.out.println(userOrders.toString());
		for(Order o : userOrders) {
			if(o.getUser().getId() == currentUser.getId() && 
					o.getProduct().getId() == productId) {
				orderDAO.deleteOrder(o.getId());
				return "redirect:/homepage/products";
			}
		}
		
		orderDAO.saveOrder(order);
		theModel.addAttribute("currentUser", currentUser);			
		System.out.println("Redirect - " + isRedirect);
		if(isRedirect != null && isRedirect.compareToIgnoreCase("false") == 0) {
			return "mycart";
		}else {
			return "redirect:/homepage/products";			
		}
	}

	@GetMapping("/mycart")
	public String showMyCart(Model theModel, HttpServletRequest request) {

		HttpSession session = request.getSession(true);
		User currentUser = (User) session.getAttribute("currentUser");

		// get all products from DAO
		List<Order> userOrders = orderDAO.getUserOrders(currentUser.getId());
		List<Product> userProducts = new ArrayList<Product>();

		for (Order order : userOrders) {
			System.out.println(order.getPaymentStatus());
			if(!order.getPaymentStatus()) {
				userProducts.add(order.getProduct());				
			}
		}

		int total_price = 0;

		for (Product product : userProducts) {
			total_price = total_price + product.getPrice();
		}

		theModel.addAttribute("userProducts", userProducts);
		theModel.addAttribute("total_price", total_price);
		theModel.addAttribute("currentUser", currentUser);

		return "mycart";
	}

	
	@GetMapping("/myorders")
	public String showMyOrders(Model theModel, HttpServletRequest request) {

		HttpSession session = request.getSession(true);
		User currentUser = (User) session.getAttribute("currentUser");

		// get all products from DAO
		List<Order> userOrders = orderDAO.getUserOrders(currentUser.getId());
		List<Product> userProducts = new ArrayList<Product>();

		for (Order order : userOrders) {
			if(order.getUser().getId() == currentUser.getId() && order.getPaymentStatus()) {
				userProducts.add(order.getProduct());
			}
		}

		int total_price = 0;

		theModel.addAttribute("userProducts", userProducts);
		theModel.addAttribute("total_price", total_price);
		theModel.addAttribute("currentUser", currentUser);

		return "myOrders";
	}
	
	@PostMapping("/searchProducts")
	public String searchProducts(HttpServletRequest request, Model theModel, @RequestParam("keySearch") String key) {

		HttpSession session = request.getSession(true);
		User currentUser = (User) session.getAttribute("currentUser");

		System.out.println(key);
		// get all products from DAO
		List<Product> products = productDAO.searchProducts(key);
		 
		  
		if (currentUser.getType() == 0) {
			// get all products from DAO
			List<Order> userOrders = orderDAO.getUserOrders(currentUser.getId());
			List<Integer> userProducts = new ArrayList<Integer>();

			for (Order order : userOrders) {
				userProducts.add(order.getProduct().getId());
			
		}
		
			theModel.addAttribute("userProducts", userProducts);
			theModel.addAttribute("currentUser", currentUser);
		}

		// add the products to the model
		theModel.addAttribute("products", products);

		
		if (currentUser.getType() == 0) {
			return "user-home";
		}
		
		else {
			return "mange-products";
		}

		
	}

}
