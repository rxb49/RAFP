<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="titrePage"><i class="bi ${titrePage.icone} pe-2"></i>${titrePage.nomPage}</c:set>
<c:set var="cssSpec">
</c:set>
<c:set var="contenuSpec">
    <div class="container g-z-index-1 g-py-20" >
        <div class="table-responsive">
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col"><spring:message code="prenom" /></th>
                        <th scope="col"><spring:message code="nom" /></th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${persons}" var ="person">
                    <tr>
                        <td>${person.prenom}</td>
                        <td>${person.nom}</td>
                        <td>
                            <form action="personList/pdf" method="post" target="_blank">
                                <input type="hidden" value="${person.numero}" name="numero"/>
                                <button type="submit" class="btn btn-tab" title="<spring:message code="imprimer" />"><i class="bi bi-printer"></i></button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</c:set>
<c:set var="jsSpec">
</c:set>
<%@ include file="templatePageAvecMenuV.jsp"%>