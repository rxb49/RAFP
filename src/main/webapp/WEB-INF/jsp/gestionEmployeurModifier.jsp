<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="flex-grow-1 cadre-info my-3 mx-3">
    <h2 class="mb-1"><spring:message code="employeur.modifier" /></h2>
</div>

    <div class="form-group g-mb-25 px-3">
        <label for="idEmployeurs"><spring:message code="saisie.employeur.nomEmployeur" /></label>
        <div class="formfield-select--container">
            <select class="form-control rounded-0 formUA" id="idEmployeurs" name="idEmployeurs" onchange="updateEmployerDetails()">
                <c:forEach var="employeur" items="${employeurs}">
                    <option value="${employeur.id_emp}" data-name="${employeur.lib_emp}" data-email="${employeur.mail_emp}">
                        ${employeur.lib_emp} - ${employeur.mail_emp}
                    </option>
                </c:forEach>
            </select>
        </div>
    </div><br>
    <div class="form-group g-mb-25 px-3">
        <label for="nomEmployeurUpdate" class="labelRequired"><spring:message code="employeur.nom"/></label>
        <input type="text" class="form-control form-control-md formUA" name="nomEmployeurUpdate" id="nomEmployeurUpdate" aria-describedby="nom" placeholder="<spring:message code='employeur.nom'/>">
    </div>
    <br>
    <div class="form-group g-mb-25 px-3">
        <label for="mailEmployeurUpdate" class="labelRequired"><spring:message code="employeur.mail"/></label>
        <input type="email" class="form-control form-control-md formUA" name="mailEmployeurUpdate" id="mailEmployeurUpdate" aria-describedby="emailHelp" placeholder="<spring:message code='employeur.mail'/>">
    </div><br>
    <button type="submit" onclick="updateEmployeur('${pageContext.request.contextPath}')" class="btn btn-md btn-primary mx-3 mb-3"><spring:message code="btn.modifier"/></button>

<script src="${pageContext.request.contextPath}/JS/getInfoEmployeur.js"></script>



