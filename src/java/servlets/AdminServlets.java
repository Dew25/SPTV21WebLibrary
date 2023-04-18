
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Book;
import entity.Reader;
import entity.secure.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.HistoryFacade;
import session.ReaderFacade;
import session.UserFacade;
import tools.PasswordEncrypt;

/**
 *
 * @author user
 */
@WebServlet(name = "AdminServlet", urlPatterns = {
    "/admin",
    "/changeRole",
    
    

})
public class AdminServlets extends HttpServlet {
    
    @EJB private ReaderFacade readerFacade;
    @EJB private HistoryFacade historyFacade;
    @EJB private UserFacade userFacade;
    
    static enum Role {USER,MANAGER,ADMINISTRATOR};
    
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
        HttpSession session = request.getSession(false);
        if(session == null){
            request.setAttribute("info", "У вас нет прав, авторизуйтесь!");
            request.getRequestDispatcher("/showLogin").forward(request, response);
            return;
        }
        User authUser = (User) session.getAttribute("user");
        if(authUser == null){
            request.setAttribute("info", "У вас нет прав, авторизуйтесь!");
            request.getRequestDispatcher("/showLogin").forward(request, response);
            return;
        }
        request.setAttribute("authUser", authUser);
        String path = request.getServletPath();
        switch (path) {
            case "/admin":
                request.setAttribute("users", userFacade.findAll());
                request.setAttribute("roles", ReaderServlets.Role.values());
                request.getRequestDispatcher("/WEB-INF/readers/admin.jsp").forward(request, response);
                break;
            case "/changeRole":
                String addRole = request.getParameter("addRole");
                String removeRole = request.getParameter("removeRole");
                String selectedRole = request.getParameter("selectedRole");
                String userId = request.getParameter("userId");
                if(selectedRole == null || selectedRole.isEmpty()){
                    request.setAttribute("info","Не выбрана роль");
                    request.getRequestDispatcher("/admin").forward(request, response);
                    break;
                }
                if(userId == null || userId.isEmpty()){
                    request.setAttribute("info","Не выбран пользователь");
                    request.getRequestDispatcher("/admin").forward(request, response);
                    break;
                }
                User user = userFacade.find(Long.parseLong(userId));
                if(user.getLogin().equals("Administrator")){
                    request.setAttribute("info","Этому пользователю изменить роль невозможно");
                    request.getRequestDispatcher("/admin").forward(request, response);
                    break;
                }
                if((addRole == null || addRole.isEmpty()) && (removeRole != null)){
                    //удаляем роль у пользователя
                    for (int i = 0; i < user.getRoles().size(); i++) {
                        String role = user.getRoles().get(i);
                        if(role.equals(selectedRole)){
                            user.getRoles().remove(role);
                            userFacade.edit(user);
                        }
                    }
                }else if((removeRole == null || removeRole.isEmpty()) && (addRole != null)){
                    //добавляем роль пользователю
                    if(!user.getRoles().contains(selectedRole)){
                        user.getRoles().add(selectedRole);
                        userFacade.edit(user);
                    }
                }else{
                    request.setAttribute("info","Действие не выполнено");
                    request.getRequestDispatcher("/admin").forward(request, response);
                    break;
                }
                request.setAttribute("info","Роль успешно изменена");
                request.getRequestDispatcher("/admin").forward(request, response);
                break;
            case "/listReaders":
                Map<Reader, List<Book>> mapReaders = new HashMap();
                List<Reader> listReaders = readerFacade.findAll();
                for (int i = 0; i < listReaders.size(); i++) {
                    Reader r = listReaders.get(i);
                    mapReaders.put(r, historyFacade.getReadingBook(r)); 
                }
                request.setAttribute("mapReaders", mapReaders);
                request.getRequestDispatcher("/WEB-INF/readers/listReaders.jsp").forward(request, response);
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
