<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="titrePage"><i class="bi ${titrePage.icone} pe-2"></i>${titrePage.nomPage}</c:set>
<c:set var="cssSpec">
</c:set>
<c:set var="contenuSpec">
    <div class="flex-column">
        <div class="card mb-4 " id="cadre1">
            <div class="flex-grow-1 cadre-info my-3 mx-3">
                <h2 class="mb-1"><spring:message code="saisie.agent" /></h2>
            </div>

            <form class="g-pa-30--md g-mb-30 mx-3">
                <div class="form-row">
                    <div class="col-12">
                    <%-- fontion search a faire pour recuperer la valeur de l'input pour chercher dans la table  --%>.
                        <div class="input-group input-group-sm mb-3">
                            <input id="searchFormInput" type="text" class="form-control formSearch" placeholder="<spring:message code="saisie.agent.rechercher" />" aria-label="<spring:message code="saisie.agent.rechercher" />" aria-describedby="searchForm" name="searchFormInput">
                        </div>
                    </div>
                </div>
                <label for="exampleSelect2"><spring:message code="saisie.employeur.nomEmployeur" /></label>
                    <div class="formfield-select--container">
                        <select class="form-control rounded-0 formUA" id="exampleSelect2">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                        </select>
                    </div>

                <div class="form-group g-mb-25">
                    <label for="montant" class="labelRequired"><spring:message code="saisie.agent.montant" /></label>
                    <input type="number" class="form-control form-control-md formUA" id="montant" aria-describedby="montant" placeholder="<spring:message code="saisie.agent.montant" />">
                </div><br>
                <button type="submit" class="btn btn-md btn-primary"><spring:message code="btn.confirmer" /></button>
            </form>
        </div>
        </div>
    </div>
</c:set>
<c:set var="jsSpec">
</c:set>
<%@ include file="templatePageAvecMenuV.jsp"%>



