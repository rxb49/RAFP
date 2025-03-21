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
                    <h2 class="mb-1"><spring:message code="employeur.ajouter" /> pour l'agent ${agent.nom_usuel} ${agent.prenom}</h2>
                </div>

                    <div class="form-group g-mb-25 px-3">
                        <label for="idEmployeurs">Rechercher un employeur</label>
                        <div class="formfield-select--container">
                            <select class="form-control rounded-0 formUA" id="idEmployeurs">
                                <c:forEach var="employeur" items="${employeurs}">
                                    <option value="${employeur.id_emp}" data-name="${employeur.lib_emp}" data-email="${employeur.mail_emp}">
                                    ${employeur.lib_emp} - ${employeur.mail_emp}
                                </c:forEach>
                            </select>

                        </div>
                    </div>
                    <br>
                    <div class="form-group g-mb-25 px-3">
                        <label for="montant" class="labelRequired"><spring:message code="saisie.agent.montant"/></label>
                        <input type="number" required="required" class="form-control form-control-md formUA" name="montant" id="montant" aria-describedby="montant" placeholder="Entrez un montant">
                    </div><br>
                    <div class="button-group">
                        <button type="submit" onclick="insertEmployeurRetour('${pageContext.request.contextPath}')" class="btn btn-md btn-primary">Ajouter</button>
                        <a href="${pageContext.request.contextPath}/donneesAgent?no_insee=${agent.no_insee}" class="btn btn-md btn-secondary">Annuler</a>

                    </div>
            </div>
        </div>
    </div>
</c:set>
<c:set var="jsSpec">
<script src="${pageContext.request.contextPath}/JS/ajoutEmployeurRetour.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

</c:set>
<%@ include file="templatePageAvecMenuV.jsp"%>



