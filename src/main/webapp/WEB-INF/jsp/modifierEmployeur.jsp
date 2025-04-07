<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="titrePage"><i class="bi ${titrePage.icone} pe-2"></i>${titrePage.nomPage}</c:set>
<c:set var="cssSpec">
</c:set>
<c:set var="contenuSpec">

    <div class="flex-column ">
        <div class="card mb-4" id="cadre">
            <div class="container text-center">
                <div class="flex-grow-1 cadre-info my-3 mx-3">
                    <h2 class="mb-1">Modifier un employeur</h2>
                </div>
                <div class="flex-grow-1 cadre-info my-3 mx-3">
                    <h2>Agent : ${agent.nom_usuel} ${agent.prenom}</h2><br>
                </div>
                <div class="flex-grow-1 cadre-info my-3 mx-3">
                    <h2>No Insee : ${agent.no_insee}</h2><br>
                </div>
                <div class="flex-grow-1 cadre-info my-3 mx-3">
                    <h2>Employeur : ${employeurs.lib_emp}</h2><br>
                </div>
                    <br>
                    <div class="form-group g-mb-25 px-3">
                        <label for="montant" class="labelRequired"><spring:message code="employeur.mail"/></label>
                        <input type="number" value="${retour.mnt_retour}" required="required" class="form-control form-control-md formUA" id="montant" aria-describedby="montant" placeholder="Entrez un montant">
                    </div><br>
                    <div class="button-group">
                        <button type="submit" onclick="modifEmployeurRetour('${pageContext.request.contextPath}', '${employeurs.id_emp}', '${agent.no_insee}')" class="btn btn-md btn-primary">Modifier</button>
                        <a href="${pageContext.request.contextPath}/donneesAgent/${agent.no_insee}" class="btn btn-md btn-secondary">Annuler</a>
                    </div>
            </div>
        </div>
    </div>
</c:set>
<c:set var="jsSpec">
<script src="${pageContext.request.contextPath}/JS/modifEmployeurRetour.js"></script>
</c:set>
<%@ include file="templatePageAvecMenuV.jsp"%>



