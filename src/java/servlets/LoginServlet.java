
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Reader;
import entity.secure.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.AuthorFacade;
import session.BookFacade;
import session.ReaderFacade;
import session.UserFacade;
import tools.PasswordEncrypt;

/**
 *
 * @author user
 */
@WebServlet(name = "LoginServlet", loadOnStartup = 1, urlPatterns = {
    "/showLogin",
    "/login",
    "/logout",
    "/listBooks",
    "/addReader",
    "/createReader",
    "/listAuthors",
    
    
})
public class LoginServlet extends HttpServlet {
    
    @EJB private ReaderFacade readerFacade;
    @EJB private UserFacade userFacade;
    @EJB private BookFacade bookFacade;
    @EJB private AuthorFacade authorFacade;
    private PasswordEncrypt pe = new PasswordEncrypt();

    @Override
    public void init() throws ServletException {
        super.init();
        if(userFacade.count()>0) return;
        Reader reader = new Reader();
                reader.setPhone("56565656");
                reader.setFirstname("Juri");
                reader.setLastname("Melnikov");
                readerFacade.create(reader);
                User user = new User();
                user.setLogin("Administrator");
                user.setSalt(pe.getSalt());
                user.setPassword(pe.getProtectedPassword("12345",user.getSalt()));
                user.setReader(reader);
                List<String> roles = new ArrayList<>();
                roles.add(ReaderServlets.Role.USER.toString());
                roles.add(ReaderServlets.Role.MANAGER.toString());
                roles.add(ReaderServlets.Role.ADMINISTRATOR.toString());
                user.setRoles(roles);
                userFacade.create(user);
    }
    
    
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String path = request.getServletPath();
        switch (path) {
            case "/showLogin":
                request.getRequestDispatcher("/showLogin.jsp").forward(request, response);
                break;
            case "/login":
                String login = request.getParameter("login");
                String password = request.getParameter("password");
                User user = userFacade.findByLogin(login);
                if( user == null){
                    request.setAttribute("info", "Нет такого пользователя или неправильный пароль");
                    request.getRequestDispatcher("/showLogin").forward(request, response);
                    break;
                }
                password = pe.getProtectedPassword(password, user.getSalt());
                if(!user.getPassword().equals(password)){
                    request.setAttribute("info", "Нет такого пользователя или неправильный пароль");
                    request.getRequestDispatcher("/showLogin").forward(request, response);
                    break;
                }
                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);
                request.setAttribute("authUser", user);
                request.setAttribute("info", "Вы авторизованы как: "+user.getLogin());
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                break;
            case "/logout":  
                HttpSession sessionClose = request.getSession(false);
                if(sessionClose != null){
                    sessionClose.invalidate();
                }
                request.setAttribute("authUser", null);
                request.setAttribute("info", "Вы вышли");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                break;
            case "/listBooks":
                request.setAttribute("listBooks", bookFacade.findAll());
                request.getRequestDispatcher("/WEB-INF/books/listBooks.jsp").forward(request, response);
                break;  
            case "/addReader":
                request.getRequestDispatcher("/WEB-INF/readers/addReader.jsp").forward(request, response);
                break;
            case "/createReader":
                String firstname = request.getParameter("firstname");
                String lastname = request.getParameter("lastname");
                String phone = request.getParameter("phone");
                login = request.getParameter("login");
                password = request.getParameter("password");
                Reader reader = new Reader();
                reader.setPhone(phone);
                reader.setFirstname(firstname);
                reader.setLastname(lastname);
                readerFacade.create(reader);
                user = new User();
                user.setLogin(login);
                PasswordEncrypt pe = new PasswordEncrypt();
                user.setSalt(pe.getSalt());
                password = pe.getProtectedPassword(password, user.getSalt());
                user.setPassword(password);
                user.setReader(reader);
                List<String> roles = new ArrayList<>();
                roles.add(ReaderServlets.Role.USER.toString());
                user.setRoles(roles);
                userFacade.create(user);
                request.setAttribute("info","Читатель успешно добавлен");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                break;   
            case "/listAuthors":
                request.setAttribute("listAuthors", authorFacade.findAll());
                request.getRequestDispatcher("/WEB-INF/authors/listAuthors.jsp").forward(request, response);
                break;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
