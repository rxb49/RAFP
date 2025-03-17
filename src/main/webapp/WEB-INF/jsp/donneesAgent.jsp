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
                    <h2 class="mb-1">Données agents</h2>
                </div>
                <div class="form-group g-mb-25">
                    <div class="formfield-select--container">
                        <select class="form-control rounded-0 formUA" id="idAgents" name="idAgents" onchange="getInfoAgent('${pageContext.request.contextPath}')">
                            <c:forEach var="agent" items="${agents}">
                                <option value="${agent.no_insee}" data-nom="${agent.nom_usuel}" data-prenom="${agent.prenom}">
                                    ${agent.no_insee} - ${agent.nom_usuel} - ${agent.prenom}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div><br>

                <!-- Table to display agent data -->
                <table class="table table-bordered text-center" id="agentsTable">
                    <thead>
                        <tr>
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
                        <!-- Dynamic rows will be added here via JavaScript -->
                    </tbody>
                </table>

                <table class="table table-bordered text-center w-75 mx-auto">
                    <tbody>
                        <tr>
                            <td>Employeur 1</td>
                            <td>Montant retour(table rafp_retour)</td>
                            <td>
                                    <a href="${pageContext.request.contextPath}/modifierEmployeur" class="text-primary">Modifier</a>
                            </td>
                            <td>
                                <a href="#" class="text-danger" onclick="confirmDelete('Employeur 1')">Supprimer</a>
                            </td>
                        </tr>
                        <tr>
                            <td>Employeur 2</td>
                            <td>Montant retour(table rafp_retour)</td>
                            <td>
                                    <a href="${pageContext.request.contextPath}/modifierEmployeur" class="text-primary">Modifier</a>
                            </td>
                            <td>
                                <a href="#" class="text-danger" onclick="confirmDelete('Employeur 2')">Supprimer</a>
                            </td>
                        </tr>
                    </tbody>
                </table>



                <a href="${pageContext.request.contextPath}/ajoutEmployeur" class="btn btn-secondary" >Ajouter un employeur</a>
            </div>
        </div>
    </div>
</c:set>
<c:set var="jsSpec">
<script src="${pageContext.request.contextPath}/JS/getEmployeurByAgent.js"></script>
<script>
    function confirmDelete(agent) {
        alert("Voulez-vous vraiment supprimer " + agent + " ?");
    }
</script>
</c:set>
<%@ include file="templatePageAvecMenuV.jsp"%>



