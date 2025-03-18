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
                    <h2 class="mb-1">Données employeur</h2>
                </div>
               <div class="form-group g-mb-25">
                    <div class="formfield-select--container">
                        <select class="form-control rounded-0 formUA" id="idEmployeurs" name="idEmployeurs" onchange="getAgentByEmployeur('${pageContext.request.contextPath}')">
                            <c:forEach var="employeur" items="${employeurs}">
                                <option value="${employeur.id_emp}" data-name="${employeur.lib_emp}" data-email="${employeur.mail_emp}">
                                    ${employeur.lib_emp} - ${employeur.mail_emp}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div><br>
                <div class="d-flex align-items-center">
                    <h2 class="fw-bold my-2 me-5" id="selectedEmployeur"></h2>
                    <h2 class="fw-bold my-2" id="totalMontantRetour">Montant retour total: </h2>
                </div>

                <!-- Tableau des agents -->
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
                    </tbody>
                </table>


                <!-- Bouton Ajouter un agent -->
                <a href="${pageContext.request.contextPath}/ajoutAgent" class="btn btn-secondary">Ajouter un agent</a>

            </div>
        </div>
    </div>
</c:set>
<c:set var="jsSpec">
<script src="${pageContext.request.contextPath}/JS/getAgentByEmployeur.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        var selectEmployeur = document.getElementById("idEmployeurs");
        var selectedEmployeurDisplay = document.getElementById("selectedEmployeur");


        function updateEmployeurLabel() {
            var selectedOption = selectEmployeur.options[selectEmployeur.selectedIndex];
            selectedEmployeurDisplay.textContent = selectedOption.dataset.name;
        }

        selectEmployeur.addEventListener("change", function() {
            updateEmployeurLabel();
            getAgentByEmployeur('${pageContext.request.contextPath}');
        });
        updateEmployeurLabel();
        getAgentByEmployeur('${pageContext.request.contextPath}');

    });
</script>
<script>
    function confirmDelete(agent) {
        alert("Voulez-vous vraiment supprimer " + agent + " ?");
    }
</script>
</c:set>
<%@ include file="templatePageAvecMenuV.jsp"%>
