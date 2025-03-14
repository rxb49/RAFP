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
                    <h2 class="mb-1">Modifier un agent pour l'employeur 1</h2>
                </div>
                <div class="flex-grow-1 cadre-info my-3 mx-3">
                    <h2>Employeur : Employeur 1</h2><br>
                </div>
               <div class="flex-grow-1 cadre-info my-3 mx-3">
                    <h2>Agent : Agent 1</h2><br>
                </div>
                <div class="form-group g-mb-25 px-3">
                        <input type="number" required="required" class="form-control form-control-md formUA" name="montant" id="montant" aria-describedby="montant" placeholder="Entrez un montant">
                </div><br>

                <div class="button-group">
                    <button type="submit" class="btn btn-md btn-primary">Modifier</button>
                    <a href="${pageContext.request.contextPath}/donneesEmployeur" class="btn btn-md btn-secondary">Annuler</a>
                </div>
            </div>
        </div>
    </div>
</c:set>
<c:set var="jsSpec">
<script>
    function confirmDelete(agent) {
        alert("Voulez-vous vraiment supprimer " + agent + " ?");
    }
</script>
</c:set>
<%@ include file="templatePageAvecMenuV.jsp"%>



