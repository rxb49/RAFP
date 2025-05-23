<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="titrePage"><i class="bi ${titrePage.icone} pe-2"></i>${titrePage.nomPage}</c:set>
<c:set var="cssSpec">
</c:set>
<c:set var="contenuSpec">
    <div class="flex-column">
        <div class="card mb-4" id="cadre2">
            <div class="flex-grow-1 cadre-info my-3 mx-3">
                <h2 class="mb-1"><spring:message code="titre.rafp.calcul" /></h2>
            </div>
            <div class="flex-grow-1 cadre-info my-3 mx-3">
                <h3 class="mb-1">
                    <spring:message code="rafp.calcul.calcul" /> ${lastDateCalcul}
                </h3>
                <h3 class="mb-1">
                    <spring:message code="rafp.calcul.generation" /> ${lastDateGeneration}
                </h3>
            </div>
            <div class="row">
                <div class="col mx-3">
                    <button type="submit" onclick="calculRAFP('${pageContext.request.contextPath}')" class="btn btn-md btn-primary g-mr-10 g-mb-15"><spring:message code="rafp.calcul" /></button>
                    <button type="submit" onclick="generateCSV('${pageContext.request.contextPath}')" class="btn btn-md btn-primary g-mr-10 g-mb-15"><spring:message code="rafp.csv" /></button>
                    <c:if test="${isEtatTExist}">
                        <a href="calculRafp/downloadAll" download="Tous_Les_Fichiers.zip" class="btn btn-md btn-secondary g-mr-10 g-mb-15"><spring:message code="rafp.calcul.telechargement" /></a>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</c:set>
<c:set var="jsSpec">
<script src="${pageContext.request.contextPath}/JS/calculRAFP.js"></script>
<script src="${pageContext.request.contextPath}/JS/generateCSV.js"></script>
</c:set>
<%@ include file="templatePageAvecMenuV.jsp"%>

