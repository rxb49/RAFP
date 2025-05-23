<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="titrePage"><i class="bi ${titrePage.icone} pe-2"></i>${titrePage.nomPage}</c:set>
<c:set var="cssSpec">
</c:set>
<c:set var="contenuSpec">

    <div class="flex-column">
        <h2 class="mb-1">RAFP de l'année : <span id="anneeActuelle">${anneeActuelle}</span></h2>
        <a href="${pageContext.request.contextPath}/gestionEmployeur" class="card card-custom d-flex flex-row pe-4 mb-3">
            <span class="icon-container me-3 m-2"><i class="bi bi-person"></i></span>
            <div class="flex-grow-1 cadre-info my-3">
                <h2 class="mb-1"><spring:message code="titre.employeur.gestion" /></h2>
                <p class="mb-0 text-muted"><spring:message code="titre.employeur.detail" /></p>
            </div>
            <span class="d-flex justify-content-center">
                <i class="bi bi-chevron-right arrow-icon"></i>
            </span>
        </a>

        <a href="${pageContext.request.contextPath}/importTotal" class="card card-custom d-flex flex-row pe-4 mb-3">
            <span class="icon-container me-3 m-2"><i class="bi bi-plus"></i></span>
            <div class="flex-grow-1 cadre-info my-3">
                <h2 class="mb-1"><spring:message code="saisie.import" /></h2>
                <p class="mb-0 text-muted"><spring:message code="saisie.import.detail" /></p>
            </div>
            <span class="d-flex justify-content-center">
                <i class="bi bi-chevron-right arrow-icon"></i>
            </span>
        </a>

        <a href="${pageContext.request.contextPath}/rechercheEmployeur" class="card card-custom d-flex flex-row pe-4 mb-3">
            <span class="icon-container me-3 m-2"><i class="bi bi-plus"></i></span>
            <div class="flex-grow-1 cadre-info my-3">
                <h2 class="mb-1"><spring:message code="saisie.employeur" /></h2>
                <p class="mb-0 text-muted"><spring:message code="saisie.employeur.detail" /></p>
            </div>
            <span class="d-flex justify-content-center">
                <i class="bi bi-chevron-right arrow-icon"></i>
            </span>
        </a>

        <a href="${pageContext.request.contextPath}/rechercheAgent" class="card card-custom d-flex flex-row pe-4 mb-3">
            <span class="icon-container me-3 m-2"><i class="bi bi-plus"></i></span>
            <div class="flex-grow-1 cadre-info my-3">
                <h2 class="mb-1"><spring:message code="saisie.agent" /></h2>
                <p class="mb-0 text-muted"><spring:message code="saisie.agent.detail" /></p>
            </div>
            <span class="d-flex justify-content-center">
                <i class="bi bi-chevron-right arrow-icon"></i>
            </span>
        </a>

        <a href="${pageContext.request.contextPath}/calculRafp" class="card card-custom d-flex flex-row pe-4 mb-3">
            <span class="icon-container me-3 m-2"><i class="bi bi-send"></i></span>
            <div class="flex-grow-1 cadre-info my-3">
                <h2 class="mb-1"><spring:message code="rafp.calcul" /></h2>
                <p class="mb-0 text-muted"><spring:message code="rafp.calcul.detail" /></p>
            </div>
            <span class="d-flex justify-content-center">
                <i class="bi bi-chevron-right arrow-icon"></i>
            </span>
        </a>
    </div>

</c:set>
<c:set var="jsSpec">
</c:set>
<%@ include file="templatePageSansMenuV.jsp" %>
