<%-- 
    Document   : listBooks
    Created on : Feb 28, 2023, 11:10:00 AM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

       <h3 class="w-100 d-flex justify-content-center mt-5">Список книг</h3>
       <div class="w-100 p-3 d-flex justify-content-center">
           <c:forEach var="book" items="${listBooks}">
            <div class="card m-2" style="width: 13rem;">
                <a class="text-decoration-none" href="book?id=${book.id}"><img src="insertFile/${book.cover.url}" width="200" height="290"/></a>
                
             </div>
           </c:forEach>
       </div>
  