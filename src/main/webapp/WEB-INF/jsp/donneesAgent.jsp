<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="titrePage"><i class="bi ${titrePage.icone} pe-2"></i>${titrePage.nomPage}</c:set>
<c:set var="cssSpec"></c:set>
<c:set var="contenuSpec">
    <div class="flex-column">
        <div class="card mb-4" id="cadre">
            <div class="container text-center">
                <div class="flex-grow-1 cadre-info my-3 mx-3">
                    <h2 class="mb-1">Données agents</h2>
                </div>

                <table class="table table-bordered text-center" id="agentsTable">
                    <thead>
                        <tr>
                            <th>N° Insee</th>
                            <th>Nom</th>
                            <th>Prenom</th>
                            <th>TBI</th>
                            <th>Indemn</th>
                            <th>Seuil</th>
                            <th>Base Restante</th>
                            <th>Total retour</th>
                            <th>Base retour recalculé</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>${agent.no_insee}</td>
                            <td>${agent.nom_usuel}</td>
                            <td>${agent.prenom}</td>
                            <td>${agent.tbi} €</td>
                            <td>${agent.indemn} €</td>
                            <td>${agent.seuil} €</td>
                            <td>${agent.base_Restante} €</td>
                            <td>${agent.total_Retour} €</td>
                            <td>${agent.base_Retour_Recalculee} €</td>
                        </tr>
                    </tbody>
                </table>
                <a href="${pageContext.request.contextPath}/modifierIndemnTBIAgent/${agent.no_insee}" class="btn btn-secondary">Modifier l'agent</a>
                <div class="flex-grow-1 cadre-info my-3 mx-3">
                    <h2 class="mb-1">Liste des employeurs</h2>
                </div>
                <table class="table table-bordered text-center" id="employeursTable">
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Nom</th>
                            <th>Montant</th>
                            <th>Base retour employeur</th>
                            <th>Modifier</th>
                            <th>Supprimer</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="employeur" items="${employeurs}">
                            <tr>
                                <td>${employeur.id_emp}</td>
                                <td>${employeur.lib_emp}</td>
                                <td>${employeur.mnt_retour}</td>
                                <td>${employeur.base_retour_recalculee_emp}</td>
                                <td><a href="${pageContext.request.contextPath}/modifierEmployeur/${agent.no_insee}/${employeur.id_emp}" class="text-primary">Modifier</a></td>
                                <td><a href="#" onclick="confirmDeleteEmployeur('${pageContext.request.contextPath}', '${employeur.lib_emp}', '${employeur.id_emp}', '${agent.no_insee}')">Supprimer</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <a href="${pageContext.request.contextPath}/ajoutEmployeur/${agent.no_insee}" class="btn btn-secondary">Ajouter un employeur</a>
            </div>
        </div>
    </div>
</c:set>
<c:set var="jsSpec">
<script src="${pageContext.request.contextPath}/JS/confirmDelete.js"></script>

</c:set>
<%@ include file="templatePageAvecMenuV.jsp" %>
