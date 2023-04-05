
       <h3 class="w-100 d-flex justify-content-center mt-5">Статистика</h3>
       <div class="w-50 p-3 container">
            <div class="row">
                <div class="m-2 w-100">
                    <form action="calcStat" method="POST">
                        <div class="">
                            <div class="m-3 row w-100">
                                <div class="col">
                                    <select name="selectYear" class="form-select form-select-sm" aria-label=".form-select-sm example">
                                        <option value="" >Выберите Год</option>
                                        <c:forEach var="year" begin="${year-3}" end="${year}">
                                            <option value="${year}" <c:if test="${selectYear==year}">selected</c:if>>${year}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col">
                                    <select name="selectMonth" class="form-select form-select-sm" aria-label=".form-select-sm example">
                                        <option value=""  >Выберите Месяц</option>
                                        <c:forEach var="month" begin="1" end="12">
                                            <option value="${month}" <c:if test="${selectMonth==month}">selected</c:if>>${month}</option>
                                        </c:forEach>
                                    </select>
                                </div>    
                                <div class="col">
                                    <select name="selectDay" class="form-select form-select-sm" aria-label=".form-select-sm example">
                                        <option value=""  selected>Выберите Деньу</option>
                                        <c:forEach var="day" begin="1" end="31">
                                            <option value="${day}"<c:if test="${selectDay==day}">selected</c:if>>${day}</option>
                                        </c:forEach>
                                    </select>
                                </div>    
                            </div>

                            <div class="mb-3 row d-flex justify-content-center">    
                                <button type="submit" class="btn btn-secondary w-50">Вычислить</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="row">
                <table class="table">
                    <tr>
                        <th scope="col">№</th>
                        <th scope="col" class="text-left">Книга читалась</th>
                        <th scope="col" class="text-center">${period}</th>
                    </tr>
                    <c:forEach var="entry" items="${mapStat}" varStatus="status">
                        <tr>
                            <th scope="row">${status.index+1}</th>
                            <td class="text-left">${entry.key.bookName}</td>
                            <td class="text-center">${entry.value}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
       </div>
                
    
