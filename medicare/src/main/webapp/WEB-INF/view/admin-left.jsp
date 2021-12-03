<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="sidenav col-lg-2 d-flex flex-column h-100">
	<h4 id="logo">
		Medicare<br /><span style="font-size : 12px;">Admin Portal</span>
		
	</h4>

	<nav  class="nav flex-column" >
			 <a class="nav-link active" style="font-size : 14px"
				href="${pageContext.request.contextPath}/admin/products"> <i 
				class="fa fa-shopping-cart fa-lg"></i> Manage Products
			</a> 
			<a class="nav-link" style="font-size : 14px"
				href="${pageContext.request.contextPath}/admin/manageUsers"><i
				class="fa fa-user fa-lg"></i> Manage Users</a>
			<a class="nav-link" style="font-size : 14px"
				href="${pageContext.request.contextPath}/admin/manageOrders"><i
				class="fa fa-shopping-bag fa-lg"></i> Manage Orders</a> 
			<a class="nav-link" style="font-size : 14px"
				href="${pageContext.request.contextPath}/admin/addProdcut"><i
				class="fa fa-plus fa-lg"></i>  Add Product</a>
				
				<a class="nav-link" style="font-size : 14px"
				href="${pageContext.request.contextPath}/login"><i
				class="fa fa-sign-out fa-lg"></i> Log Out</a>

		</nav>
	

		
</div>

