<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="titrePage"><i class="bi ${titrePage.icone} pe-2"></i>${titrePage.nomPage}</c:set>
<c:set var="cssSpec">
</c:set>
<c:set var="contenuSpec">
    <div class="flex-column">
        <h2 class="mb-1"><spring:message code="titre.rafp"/></h2><br>

        <div class="row">
            <div class="col">
                <button type="button" onclick="getAgents('${pageContext.request.contextPath}')" class="btn btn-md btn-primary g-mr-10 g-mb-15" id="listeAgent"><spring:message code="liste.agent"/></button>
                <button type="button" onclick="getEmployeur('${pageContext.request.contextPath}')" class="btn btn-md btn-primary g-mr-10 g-mb-15" id="listeEmployeur"><spring:message code="liste.employeur"/></button>
            </div>
        </div><br>
            <label for="listeAgentEmployeur"><spring:message code="saisie.agent.rechercher" /></label>
                <div class="formfield-select--container">
                    <select class="form-control rounded-0 formUA" id="listeAgentEmployeur" onchange="getAgentByEmployeur('${pageContext.request.contextPath}')">

                    </select>
                </div><br>

        <div class="container g-z-index-1 g-py-20" >
        <div class="table-responsive">
            <table id="dataTable" class="table">
                <thead>
                    <tr id="nomColonne">

                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${persons}" var ="person">
                    <tr>
                        <td>${person.prenom}</td>
                        <td>${person.nom}</td>
                        <td>
                            <form action="listeRafp/update" method="post" target="_blank">
                                <input type="hidden" value="${person.numero}" name="update"/>
                                <button type="submit" class="btn btn-tab" title="<spring:message code="btn.modifier" />"><i class="bi bi-arrow-clockwise"></i></button>
                            </form>
                        </td>
                        <td>
                            <form action="listeRafp/add" method="post" target="_blank">
                                <input type="hidden" value="${person.numero}" name="add"/>
                                <button type="submit" class="btn btn-tab" title="<spring:message code="btn.ajouter" />"><i class="bi bi-plus"></i></button>
                            </form>
                        </td>
                        <td>
                            <form action="listeRafp/delete" method="post" target="_blank">
                                <input type="hidden" value="${person.numero}" name="delete"/>
                                <button type="submit" class="btn btn-tab" title="<spring:message code="btn.supprimer" />"><i class="bi bi-trash"></i></button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    </div>


</c:set>
<c:set var="jsSpec">
    <script src="${pageContext.request.contextPath}/JS/getInfoEmployeur.js" defer></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11" defer></script>
</c:set>
<%@ include file="templatePageAvecMenuV.jsp"%>