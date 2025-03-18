<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="titrePage"><i class="bi ${titrePage.icone} pe-2"></i>${titrePage.nomPage}</c:set>

<c:set var="contenuSpec">
    <div class="flex-column">
        <div class="card mb-4" id="cadre">
            <div class="container text-center">
                <div class="flex-grow-1 cadre-info my-3 mx-3">
                    <h2 class="mb-1">Données employeur</h2>
                </div>

                <div class="d-flex align-items-center">
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
            </div>
        </div>
    </div>
</c:set>

<c:set var="jsSpec">
<script src="${pageContext.request.contextPath}/JS/getAgentByEmployeur.js"></script>
<script>
    // L'ID d'employeur est maintenant passé dans l'URL, pas besoin de select
    document.addEventListener("DOMContentLoaded", function () {
        var urlParams = new URLSearchParams(window.location.search);
        var idEmployeur = urlParams.get('idEmployeur'); // Récupérer l'ID depuis l'URL
        if (idEmployeur) {
            getAgentByEmployeur('${pageContext.request.contextPath}', idEmployeur);
        } else {
            console.error("❌ L'ID de l'employeur est manquant dans l'URL.");
        }
    });
</script>
</c:set>

<%@ include file="templatePageAvecMenuV.jsp"%>
