package gruoppo.test.presentation;

import gruoppo.test.Application.ProductInfo;
import gruoppo.test.Application.UserInfo;
import gruoppo.test.service.ProductService;
import gruoppo.test.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/controller")
public class Controller extends HttpServlet {

    private ProductService productService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            redirectToIndex(request, response);
            return;
        }

        try {
            switch (action) {
                case "viewProducts":
                    handleViewProducts(request, response);
                    break;
                case "viewCart":
                    handleViewCart(request, response);
                    break;
                case "logout":
                    handleLogout(request, response);
                    break;
                default:
                    redirectToIndex(request, response);
                    break;
            }
        } catch (SQLException e) {
            handleError(response, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            redirectToIndex(request, response);
            return;
        }

        try {
            switch (action) {
                case "register":
                    handleRegister(request, response);
                    break;
                case "login":
                    handleLogin(request, response);
                    break;
                case "addToCart":
                    handleAddToCart(request, response);
                    break;
                case "removeFromCart":
                    handleRemoveFromCart(request, response);
                    break;
                case "clearCart":
                    handleClearCart(request, response);
                    break;
                default:
                    redirectToIndex(request, response);
                    break;
            }
        } catch (SQLException e) {
            handleError(response, e);
        }
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserInfo user = userService.registerUser(username, password);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            List<ProductInfo> cart = productService.getProductsInCart(user.getUserId());
            session.setAttribute("cart", cart);

            response.sendRedirect("index.jsp");
        } else {
            response.sendRedirect("register.jsp?error=Registration failed, please try again");
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean isLoggedIn = userService.loginUser(username, password);
        if (isLoggedIn) {
            HttpSession session = request.getSession();

            UserInfo user = userService.getUserInfoByUsername(username);
            session.setAttribute("user", user);

            List<ProductInfo> cart = productService.getProductsInCart(user.getUserId());
            session.setAttribute("cart", cart);

            response.sendRedirect("index.jsp");
        } else {
            response.sendRedirect("login.jsp?error=Invalid credentials");
        }
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("index.jsp");
    }

    private void handleViewProducts(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<ProductInfo> products = productService.getAllProducts();
        request.setAttribute("products", products);
        request.getRequestDispatcher("products.jsp").forward(request, response);
    }

    private void handleViewCart(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        UserInfo user = (UserInfo) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<ProductInfo> cart = productService.getProductsInCart(user.getUserId());
        request.setAttribute("cart", cart);
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    private void handleAddToCart(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        HttpSession session = request.getSession();
        UserInfo user = (UserInfo) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp?error=You must be logged in to add items to the cart");
            return;
        }

        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        productService.addProductToCart(user.getUserId(), productId, quantity);

        List<ProductInfo> updatedCart = productService.getProductsInCart(user.getUserId());
        session.setAttribute("cart", updatedCart);

        response.sendRedirect("controller?action=viewCart");
    }

    private void handleRemoveFromCart(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        HttpSession session = request.getSession();
        UserInfo user = (UserInfo) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp?error=You must be logged in to remove items from the cart");
            return;
        }

        int productId = Integer.parseInt(request.getParameter("productId"));

        productService.removeProductFromCart(user.getUserId(), productId);

        List<ProductInfo> updatedCart = productService.getProductsInCart(user.getUserId());
        session.setAttribute("cart", updatedCart);
        response.sendRedirect("controller?action=viewCart");
    }

    private void handleClearCart(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        HttpSession session = request.getSession();
        UserInfo user = (UserInfo) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp?error=You must be logged in to clear your cart");
            return;
        }

        productService.clearCart(user.getUserId());

        session.setAttribute("cart", new ArrayList<>());

        response.sendRedirect("controller?action=viewCart");
    }

    private void handleError(HttpServletResponse response, SQLException e) throws IOException {
        e.printStackTrace();
        response.sendRedirect("error.jsp?error=" + e.getMessage());
    }

    private void redirectToIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<ProductInfo> products = productService.getAllProducts();
            request.setAttribute("products", products);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (SQLException e) {
            handleError(response, e);
        }
    }

    @Override
    public void destroy() {
        try {
            productService = null;
            userService = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
