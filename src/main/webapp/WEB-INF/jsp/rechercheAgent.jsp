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
                    <h2 class="mb-1">Rechercher un agent</h2>
                </div>
               <div class="form-group g-mb-25">
                    <input type="text" class="form-control form-control-md formUA" id="inputName" aria-describedby="inputName" placeholder="Rechercher un agent">
               </div><br>

                <!-- Tableau des agents -->
                <table class="table table-bordered text-center" id="agentsTable">
                    <thead>
                        <tr>
                            <th>Nom </th>
                            <th>Prenom</th>
                            <th>N° Insee</th>
                            <th>Voir</th>
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
<script src="${pageContext.request.contextPath}/JS/getAgentBySearch.js"></script>
<script>
    const path = '${pageContext.request.contextPath}';
</script>

</c:set>
<%@ include file="templatePageAvecMenuV.jsp"%>
