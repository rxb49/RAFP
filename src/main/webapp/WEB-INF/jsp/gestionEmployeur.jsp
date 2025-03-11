<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="titrePage"><i class="bi ${titrePage.icone} pe-2"></i>${titrePage.nomPage}</c:set>
<c:set var="cssSpec">
</c:set>
<c:set var="contenuSpec">
    <div class="flex-column ">

        <div class="card mb-4" id="cadre">
            <div class="flex-grow-1 cadre-info my-3 mx-3">
                <h2 class="mb-1"><spring:message code="employeur.ajouter" /></h2>
            </div>

            <form class="g-pa-30--md g-mb-30 mx-3" method="post" action="${pageContext.request.contextPath}/gestionEmployeur/add">
                <div class="form-group g-mb-25">
                    <label for="nomEmployeur" class="labelRequired"><spring:message code="employeur.nom"/></label>
                    <input type="text" required="required" class="form-control form-control-md formUA" name="nomEmployeur" id="nomEmployeur" aria-describedby="nomEmployeur" placeholder="<spring:message code="employeur.nom"/>">
                </div>
                <br>
                <div class="form-group g-mb-25">
                    <label for="mailEmployeur" class="labelRequired"><spring:message code="employeur.mail"/></label>
                    <input type="email" required="required" class="form-control form-control-md formUA" name="mailEmployeur" id="mailEmployeur" aria-describedby="mailEmployeur" placeholder="<spring:message code="employeur.mail"/>">
                </div><br>
                <button type="submit" class="btn btn-md btn-primary"><spring:message code="btn.ajouter"/></button>
            </form>
            <c:if test="${not empty message}">
                <div style="color: green;">${message}</div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div style="color: red;">${errorMessage}</div>
            </c:if>
        </div>


        <div class="card mb-4" id="cadre1">
            <div class="flex-grow-1 cadre-info my-3 mx-3">
                <h2 class="mb-1"><spring:message code="employeur.modifier" /></h2>
            </div>

            <form class="g-pa-30--md g-mb-30 mx-3" method="post" action="${pageContext.request.contextPath}/gestionEmployeur/update">
                <div class="form-group g-mb-25">
                    <label for="employeurs"><spring:message code="saisie.employeur.nomEmployeur" /></label>
                    <div class="formfield-select--container">
                        <select class="form-control rounded-0 formUA" id="employeurs" name="employeurs" onchange="updateEmployerDetails()">
                            <c:forEach var="employeur" items="${employeurs}">
                                <option value="${employeur.id_emp}" data-name="${employeur.lib_emp}" data-email="${employeur.mail_emp}">
                                    ${employeur.lib_emp} - ${employeur.mail_emp}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div><br>
                <div class="form-group g-mb-25">
                    <label for="nameEmployeur" class="labelRequired"><spring:message code="employeur.nom"/></label>
                    <input type="text" class="form-control form-control-md formUA" name="nomEmployeurUpdate" id="nameEmployeur" aria-describedby="nom" placeholder="<spring:message code='employeur.nom'/>">
                </div>
                <br>
                <div class="form-group g-mb-25">
                    <label for="emailEmployeur" class="labelRequired"><spring:message code="employeur.mail"/></label>
                    <input type="email" class="form-control form-control-md formUA" name="mailEmployeurUpdate" id="emailEmployeur" aria-describedby="emailHelp" placeholder="<spring:message code='employeur.mail'/>">
                </div><br>
                <button type="submit" class="btn btn-md btn-primary"><spring:message code="btn.modifier"/></button>
            </form>
            <c:if test="${not empty messageUpdate}">
                <div style="color: green;">${messageUpdate}</div>
            </c:if>
            <c:if test="${not empty errorMessageUpdate}">
                <div style="color: red;">${errorMessageUpdate}</div>
            </c:if>
       </div>
    </div>
<script type="text/javascript">
    function updateEmployerDetails() {
        var select = document.getElementById("employeurs");
        var selectedOption = select.options[select.selectedIndex];
        var name = selectedOption.getAttribute("data-name");
        var email = selectedOption.getAttribute("data-email");

        document.getElementById("nameEmployeur").value = name;
        document.getElementById("emailEmployeur").value = email;
    }
 </script>


</c:set>
<c:set var="jsSpec">
</c:set>
<%@ include file="templatePageAvecMenuV.jsp"%>
