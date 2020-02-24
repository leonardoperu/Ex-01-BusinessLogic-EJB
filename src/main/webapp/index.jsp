<%@ page session ="true"%>
<%@ page import="java.util.*" %>
<%@ page import="it.distributedsystems.model.dao.*" %>
<%@ page import="it.distributedsystems.model.ejb.Cart" %>
<%@ page import="it.distributedsystems.model.ejb.EJB3Cart" %>


<%!
	String printTableRow(Product product, String url) {
		StringBuffer html = new StringBuffer();
		html
				.append("<tr>")
				.append("<td>")
				.append(product.getName())
				.append("</td>")

				.append("<td>")
				.append(product.getProductNumber())
				.append("</td>")

				.append("<td>")
				.append( (product.getProducer() == null) ? "n.d." : product.getProducer().getName() )
				.append("</td>")

				.append("<td>")
				.append( product.getPrice() + " &euro;" )
				.append("</td>")

				.append("<form>")

				.append("<td>")
				.append( "<input type=\"number\" name=\"quantity\" min=\"1\" max=\"99\" value=\"1\" style=\"alignment: right; width: 50px\" required/>" )
				.append("</td>")

				.append("<td>")
				.append("<input type=\"hidden\" name=\"operation\" value=\"addToCart\"/>")
				.append("<input type=\"hidden\" name=\"productId\" value=\""+product.getId()+"\"/>")
				.append("<input type=\"submit\" name=\"add\" value=\"Add to cart\"/>")
				.append("</td>")

				.append("</form>");

		html
				.append("</tr>");

		return html.toString();
	}

	String printTableRows(List products, String url) {
		StringBuffer html = new StringBuffer();
		Iterator iterator = products.iterator();
		while ( iterator.hasNext() ) {
			html.append( printTableRow( (Product) iterator.next(), url ) );
		}
		return html.toString();
	}

	String printItem(Purchase purchase, String url) {
		StringBuffer html = new StringBuffer();
		html
				.append("<tr>")
				.append("<td>")
				.append(purchase.getProduct().getName())
				.append("</td>")

				.append("<td>")
				.append( (purchase.getProduct().getProducer() == null) ? "n.d." : purchase.getProduct().getProducer().getName() )
				.append("</td>")

				.append("<td>")
				.append( purchase.getProduct().getPrice() + " &euro;" )
				.append("</td>")

				.append("<td>")
				.append( purchase.getQuantity() )
				.append("</td>")

				.append("<td>")
				.append("<form>")
				.append("<input type=\"hidden\" name=\"operation\" value=\"removeFromCart\"/>")
				.append("<input type=\"hidden\" name=\"productId\" value=\""+purchase.getProduct().getId()+"\"/>")
				.append("<input type=\"submit\" name=\"remove\" value=\"Remove\"/>")
				.append("</form>")
				.append("</td>");

		html
				.append("</tr>");

		return html.toString();
	}

	String printCart(Cart cart, String url) {
		StringBuffer html = new StringBuffer();
		Iterator iterator = cart.getPurchases().iterator();
		while ( iterator.hasNext() ) {
			html.append( printItem( (Purchase) iterator.next(), url ) );
		}
		return html.toString();
	}

	String printPreviousOrders(List<Purchase>purchases, String url) {
		StringBuffer html = new StringBuffer();
		for (Purchase purchase : purchases) {
			html
					.append("<tr>")
					.append("<td>")
					.append(purchase.getProduct().getName())
					.append("</td>")

					.append("<td>")
					.append( (purchase.getProduct().getProducer() == null) ? "n.d." : purchase.getProduct().getProducer().getName() )
					.append("</td>")

					.append("<td>")
					.append( purchase.getQuantity() )
					.append("</td>")

					.append("<td>")
					.append( purchase.getPurchaseNumber() )
					.append("</td>")

					.append("</tr>");
		}
		return html.toString();
	}

%>

<html>

	<head>
		<title>HOMEPAGE DISTRIBUTED SYSTEM EJB</title>

		<meta http-equiv="Pragma" content="no-cache"/>
		<meta http-equiv="Expires" content="Mon, 01 Jan 1996 23:59:59 GMT"/>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<meta name="Author" content="you">

		<link rel="StyleSheet" href="styles/default.css" type="text/css" media="all" />

		<style>
			table, th, td {
				padding: 10px;
				border: 1px solid #0000ff;
				border-collapse: collapse;
			}
		</style>
	</head>
	
	<body>

	<jsp:useBean id="cartFactory" class="it.distributedsystems.model.ejb.CartFactory" scope="application"/>
	<%
		if(request.getSession().getAttribute("cart")==null)
			request.getSession().setAttribute("cart",cartFactory.getCart());
		Cart cart= (Cart) request.getSession().getAttribute("cart");

		// can't use builtin object 'application' while in a declaration!
		// must be in a scriptlet or expression!
		DAOFactory daoFactory = DAOFactory.getDAOFactory( application.getInitParameter("dao") );
		CustomerDAO customerDAO = daoFactory.getCustomerDAO();
		PurchaseDAO purchaseDAO = daoFactory.getPurchaseDAO();
		ProductDAO productDAO = daoFactory.getProductDAO();
		ProducerDAO producerDAO = daoFactory.getProducerDAO();

		String operation = request.getParameter("operation");

		if ( operation != null && operation.equals("insertCustomer") ) {
			Customer customer = new Customer();
			customer.setName( request.getParameter("name") );
			try {
				int id = customerDAO.insertCustomer(customer);
				out.println("<!-- inserted customer '" + customer.getName() + "', with id = '" + id + "' -->");
			} catch(Exception e) {
				out.println("Errore inserimento customer.");
			}
		}
		else if ( operation != null && operation.equals("insertProducer") ) {
			Producer producer = new Producer();
			producer.setName( request.getParameter("name") );
			int id = producerDAO.insertProducer( producer );
			out.println("<!-- inserted producer '" + producer.getName() + "', with id = '" + id + "' -->");
		}
		else if ( operation != null && operation.equals("insertProduct") ) {
			Product product = new Product();
			product.setName( request.getParameter("name") );
			product.setPrice(Float.parseFloat(request.getParameter("price")));
			product.setProductNumber(Integer.parseInt(request.getParameter("number")));

			Producer producer = producerDAO.findProducerById(Integer.parseInt(request.getParameter("producer")));
			product.setProducer(producer);
			int id = productDAO.insertProduct(product);
			out.println("<!-- inserted product '" + product.getName() + "' with id = '" + id + "' -->");
		}
		else if ( operation != null && operation.equals("addToCart") ) {
			int quantity = Integer.parseInt( request.getParameter("quantity"));
			Product product = productDAO.findProductById(
					Integer.parseInt( request.getParameter( "productId" ) ) );
			cart.addProduct(product, quantity);
			request.getSession().setAttribute("cart", cart);
			System.out.println(cart.getCustomer().getName() + "'s cart: " + quantity + " " +product.getName() + " added!");
		}
		else if ( operation != null && operation.equals("removeFromCart") ) {
			Product product = productDAO.findProductById(
					Integer.parseInt( request.getParameter( "productId" ) ) );
			cart.removeProduct(product);
			request.getSession().setAttribute("cart", cart);
			System.out.println(cart.getCustomer().getName() + "'s cart: " + product.getName() + " removed!");
		}
		else if ( operation != null && operation.equals("buy") ) {
			cart.submit();
			cart.setCartNumber(cartFactory.generateCartNumber());
			request.getSession().setAttribute("cart", cart);
			System.out.println(cart.getCustomer().getName() + "'s cart: purchase submitted!" );
		}
		else if ( operation != null && operation.equals("clearCart") ) {
			cart.clearCart();
			request.getSession().setAttribute("cart", cart);
			System.out.println(cart.getCustomer().getName() + "'s cart cleared!" );
		}
		else if ( operation != null && operation.equals("chooseCustomer") ) {
			Customer customer = customerDAO.findCustomerById(
					Integer.parseInt( request.getParameter( "customer" ) ) );
			cart.setCustomer(customer);
			request.getSession().setAttribute("customer", customer);
			request.getSession().setAttribute("cart", cart);
			System.out.println("CHOSEN CUSTOMER: " + customer.getName() + "(id: " + customer.getId() + ")");
		}

		//Da aggiungere la possibilità di fare un ordine in sessione e di finalizzarla per creare un purchase.
	%>


	<h1>Customer Manager</h1>

	<div style="display:inline-block; width:33%">
		<h3>Add Customer:</h3>
		<form>
			Name: <input type="text" name="name"/><br/>
			<input type="hidden" name="operation" value="insertCustomer"/>
			<input type="submit" name="submit" value="submit"/>
		</form>
	</div>

	<div style="display: inline-block; width: 33%">
		<h3>Add Producer:</h3>
		<form>
			Name: <input type="text" name="name"/><br/>
			<input type="hidden" name="operation" value="insertProducer"/>
			<input type="submit" name="submit" value="submit"/>
		</form>
	</div>

	<!--<div>
		<p>Add Product:</p>
		<form>
			Name: <input type="text" name="name"/><br/>
			Product Number: <input type="text" name="number"/><br/>
			<input type="hidden" name="operation" value="insertProduct"/>
			<input type="submit" name="submit" value="submit"/>
		</form>
	</div>-->
	<%
		List<Producer> producers = producerDAO.getAllProducers();
		if ( producers.size() > 0 ) {
	%>
	<div style="display: inline-block; width:33%">
		<h3>Add Product:</h3>
		<form>
			Name: <input type="text" name="name"required/><br/>
			Product Number: <input type="text" name="number" required/><br/>
			Price: <input type="number" name="price" step="0.01" required/><br/>
			Producer: <select name="producer" required>
			<%
				Iterator iterator = producers.iterator();
				while ( iterator.hasNext() ) {
					Producer producer = (Producer) iterator.next();
			%>
			<option value="<%= producer.getId() %>"><%= producer.getName()%></option>
			<%
				}// end while
			%>

			<input type="hidden" name="operation" value="insertProduct"/>
			<input type="submit" name="submit" value="submit"/>
		</form>
	</div>
	<%
	}// end if
	else {
	%>
	<div>
		<p>At least one Producer must be present to add a new Product.</p>
	</div>
	<%
		} // end else
	%>

	<br/>
	<hr/>

	<div>
		<h1>Products currently in the database:</h1>
		<table width="60%">
			<tr><th>ProductName</th><th>ProductNumber</th><th>Producer</th><th>Price</th><th>Quantity</th><th><!--</th></tr>-->
			<%= printTableRows( productDAO.getAllProducts(), request.getContextPath() ) %>
		</table>
	</div>

	<div>
		<a href="<%= request.getContextPath() %>">Ricarica lo stato iniziale di questa pagina</a>
	</div>

	<br/>
	<hr/>


	<div>
		<%
			if(request.getSession().getAttribute("customer")==null) {
		%>
		<form>
			<label>Customer</label>
			<select name="customer" required>
				<%
				List<Customer> customers = customerDAO.getAllCustomers();
				for (Customer cust : customers) {
			%>
			<option value="<%= cust.getId() %>"><%= cust.getName()%></option>
				<%
				}// end while
			%>
			<input type="hidden" name="operation" value="chooseCustomer"/>
			<input type="submit" name="submit" value="submit"/>
		</form>
		<%
			} // end if
			else {
				Customer customer = (Customer) request.getSession().getAttribute("customer");
		%>
		<h3>You are: <%= customer.getName() %></h3>

			<h1>Your cart</h1>
			<p><%= cart.toString() %></p>
			<table>
				<tr><th>ProductName</th><th>Producer</th><th>Price</th><th>Quantity</th><th><!--</th></tr>-->
						<%= printCart( cart, request.getContextPath() ) %>
			</table>
			<h3>Totale: <%= cart.getTotal()%> &euro;</h3>
			<br/>
			<form>
				<input type="hidden" name="operation" value="buy">
				<input type="submit" name="buy" value="Buy">
			</form>
			<form>
				<input type="hidden" name="operation" value="clearCart">
				<input type="submit" name="clearCart" value="Clear Cart">
			</form>

			<h2>Your previous purchases</h2>
			<table>
				<tr><th>ProductName</th><th>Producer</th><th>Quantity</th><th>Purchase Number</th><!--</th></tr>-->
						<%= printPreviousOrders( purchaseDAO.findAllPurchasesByCustomer(customer), request.getContextPath() ) %>
			</table>

		<%
			} // end else
		%>
	</div>



	</body>

</html>