<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="titrePage"><i class="bi ${titrePage.icone} pe-2"></i>${titrePage.nomPage}</c:set>
<c:set var="cssSpec">
</c:set>
<c:set var="contenuSpec">
    <div class="container g-z-index-1 g-py-20" >
        <div>
            <table class="table tableUA table-hover">
                <thead>
                    <tr>
                        <th><spring:message code="votre.nom" /></th>
                        <th><spring:message code="votre.prenom" /></th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>${lePersonnel.nom}</td>
                        <td>${lePersonnel.prenom}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</c:set>
<c:set var="jsSpec">
</c:set>
<%@ include file="templatePageAvecMenuV.jsp"%>