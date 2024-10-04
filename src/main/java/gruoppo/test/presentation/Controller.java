package gruoppo.test.presentation;

import gruoppo.test.Application.Product;
import gruoppo.test.Application.User;
import gruoppo.test.Application.Model;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/controller")
public class Controller extends HttpServlet {

    private Model model;

    @Override
    public void init() throws ServletException {
        model = new Model();
        try {
            model.connect();
        } catch (SQLException e) {
            throw new ServletException("Failed to connect to the database during servlet initialization", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            try {
                List<Product> products = model.getAllProducts();
                request.setAttribute("products", products);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("error.jsp");
                return;
            }
            request.getRequestDispatcher("index.jsp").forward(request, response);
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
                default:
                    response.sendRedirect("index.jsp");
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect("index.jsp");
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
                default:
                    response.sendRedirect("index.jsp");
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = model.registerUser(username, password);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("welcome.jsp");
        } else {
            response.sendRedirect("register.jsp?error=Registration failed");
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean isLoggedIn = model.loginUser(username, password);
        if (isLoggedIn) {
            HttpSession session = request.getSession();
            session.setAttribute("user", new User(username, model.getUserId(username)));
            response.sendRedirect("welcome.jsp");
        } else {
            response.sendRedirect("login.jsp?error=Invalid credentials");
        }
    }

    private void handleViewProducts(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Product> products = model.getAllProducts();
        request.setAttribute("products", products);
        request.getRequestDispatcher("products.jsp").forward(request, response);
    }

    private void handleViewCart(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<Product> cart = model.getProductsInCart(user.getId());
        request.setAttribute("cart", cart);
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    private void handleAddToCart(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        model.addProductToCart(user.getId(), productId, quantity);
        response.sendRedirect("controller?action=viewCart");
    }

    private void handleRemoveFromCart(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int productId = Integer.parseInt(request.getParameter("productId"));

        model.removeProductFromCart(user.getId(), productId);
        response.sendRedirect("controller?action=viewCart");
    }

    @Override
    public void destroy() {
        try {
            model.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

