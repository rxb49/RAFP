<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="titrePage"><i class="bi ${titrePage.icone} pe-2"></i>${titrePage.nomPage}</c:set>
<c:set var="cssSpec">
</c:set>
<c:set var="contenuSpec">
    <div class="flex-column">
        <div class="card mb-4" id="cadre">
            <div class="container text-center">
                <div class="flex-grow-1 cadre-info my-3 mx-3">
                    <h2 class="mb-1">Liste des agents pour ${employeur.lib_emp}</h2>
                </div>
                <div class="flex-grow-1 cadre-info my-3 mx-3">
                    <h3 class="mb-1" id="mntTotal">Montant Total: ${totalMontantRetour} €</h3>
                </div>
                <div class="flex-grow-1 cadre-info my-3 mx-3">
                    <h3 class="mb-1" >Liste des agents</h3>
                </div>

            </div>
            <table class="table table-bordered text-center" id="agentsTable">
                <thead>
                    <tr>
                        <th>N° Insee</th>
                        <th>Nom</th>
                        <th>Prenom</th>
                        <th>Montant retour total</th>
                        <th>Modifier</th>
                        <th>Supprimer</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="agent" items="${agents}">
                        <tr>
                            <td>${agent.insee}</td>
                            <td>${agent.nom_usuel}</td>
                            <td>${agent.prenom}</td>
                            <td>${agent.mnt_retour} €</td>
                            <td><a href="${pageContext.request.contextPath}/modifierAgent/${employeur.id_emp}/${agent.insee}" class="text-primary">Modifier</a></td>
                            <td><a href="#" onclick="confirmDeleteEmployeur('${pageContext.request.contextPath}', '${employeur.lib_emp}', '${employeur.id_emp}', '${agent.insee}')">Supprimer</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
                <a href="${pageContext.request.contextPath}/ajoutAgent/${employeur.id_emp}" class="btn btn-secondary">Ajouter un employeur</a>
        </div>
    </div>
</c:set>

<c:set var="jsSpec">
<script src="${pageContext.request.contextPath}/JS/confirmDelete.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</c:set>

<%@ include file="templatePageAvecMenuV.jsp"%>
