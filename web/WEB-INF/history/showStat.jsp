<%-- 
    Document   : showStat
    Created on : Apr 3, 2023, 11:03:13 AM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Статистика</title>
    </head>
    <body>
       <h3 class="w-100 d-flex justify-content-center mt-5">Статистика</h3>
       <div class="w-100 p-3 d-flex justify-content-center">
           
            <div class="card border-0 m-2" style="width: 23rem;">
                <form action="createStat" method="POST">
                    
                    <div class="card-body">
                        <div class="mb-3 row ">
                            <select name="selectYear" class="form-select form-select-sm" aria-label=".form-select-sm example">
                                <option value="#" disabled selected>Выберите Год</option>
                                <c:forEach var="year" items="${listYars}">
                                    <option value="${year}">${year}</option>
                                </c:forEach>
                            </select>
                            <select name="selectMonth" class="form-select form-select-sm" aria-label=".form-select-sm example">
                                <option value="#" disabled selected>Выберите Месяц</option>
                                <c:forEach var="month" begin="1" end="12">
                                    <option value="${manth}">${manth}</option>
                                </c:forEach>
                            </select>
                            <select name="selectDay" class="form-select form-select-sm" aria-label=".form-select-sm example">
                                <option value="#" disabled selected>Выберите Деньу</option>
                                <c:forEach var="day" begin="1" end="31">
                                    <option value="${day}">${day}</option>
                                </c:forEach>
                            </select>
                        </div>
                       
                        <div class="mb-3 row d-flex justify-content-center">    
                            <button type="submit" class="btn btn-secondary w-50">Вычислить</button>
                        </div>
                    </div>
                </form>
             </div>
           
       </div>
    </body>
</html>
