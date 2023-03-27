<%-- 
    Document   : listBooks
    Created on : Feb 28, 2023, 11:10:00 AM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
   <h3 class="w-100 d-flex justify-content-center mt-5">Список читателей</h3>
   <div class="w-100 p-3 d-flex justify-content-center">
        <div class="card m-2" style="width: 45rem;">
            <div class="card-body">
                <div class="container text-center">
                    <c:forEach var="entry" items="${mapReaders}" varStatus="status">
                        <div class="row mb-5">
                            <div class="col d-flex justify-content-start">
                                <h5 class="">${status.index+1}. ${entry.key.firstname} ${entry.key.lastname}. ${entry.key.phone} </h5>
                            </div>
                            <div class="col d-flex justify-content-start mb-5">
                               Читаемые книги 
                            </div>
                        </div>
                        <c:forEach var="book" items="${entry.value}" varStatus="status">
                            <div class="row  ">
                                <div class="col ">
                                </div>
                                <div class="col d-flex justify-content-start">
                                    <a  href="book?id=${book.id}" class="">
                                       ${status.index + 1}. ${book.bookName}
                                    </a>
                                </div>
                            </div>
                        </c:forEach>
                             
                    </c:forEach>
                </div>
            </div>
         </div>
   </div>
