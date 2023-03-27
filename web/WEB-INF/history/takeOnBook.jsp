<%-- 
    Document   : listBooks
    Created on : Feb 28, 2023, 11:10:00 AM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

       <h3 class="w-100 d-flex justify-content-center mt-5">Выдать книгу читателю</h3>
       <div class="w-100 p-3 d-flex justify-content-center">
           
            <div class="card border-0 m-2" style="width: 23rem;">
                <form action="createHistory" method="POST">
                    
                    <div class="card-body">
                        <div class="mb-3 row ">
                            <select name="bookId" class="form-select form-select-sm" aria-label=".form-select-sm example">
                                <option value="#" disabled selected>Выберите книгу</option>
                                <c:forEach var="book" items="${listBooks}">
                                    <option value="${book.id}">${book.bookName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="mb-3 row ">
                            <select name="readerId" class="form-select form-select-sm" aria-label=".form-select-sm example">
                                <option value="#" disabled selected>Выберите читателя</option>
                                <c:forEach var="reader" items="${listReaders}">
                                    <option value="${reader.id}">${reader.firstname} ${reader.lastname}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="mb-3 row d-flex justify-content-center">    
                            <button type="submit" class="btn btn-secondary w-50">Выдать книгу</button>
                        </div>
                    </div>
                </form>
             </div>
           
       </div>
  