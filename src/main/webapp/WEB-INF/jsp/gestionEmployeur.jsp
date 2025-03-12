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

                <div class="form-group g-mb-25 px-3">
                    <label for="nomEmployeur" class="labelRequired"><spring:message code="employeur.nom"/></label>
                    <input type="text" required="required" class="form-control form-control-md formUA" name="nomEmployeur" id="nomEmployeur" aria-describedby="nomEmployeur" placeholder="<spring:message code="employeur.nom"/>">
                </div>
                <br>
                <div class="form-group g-mb-25 px-3">
                    <label for="mailEmployeur" class="labelRequired"><spring:message code="employeur.mail"/></label>
                    <input type="email" required="required" class="form-control form-control-md formUA" name="mailEmployeur" id="mailEmployeur" aria-describedby="mailEmployeur" placeholder="<spring:message code="employeur.mail"/>">
                </div><br>
                <button type="button" class="btn btn-md btn-primary mx-3 mb-3" onclick="insertEmployeur('${pageContext.request.contextPath}')"><spring:message code="btn.ajouter"/></button>
        </div>


        <div class="card mb-4" id="cadre1" >

       </div>
    </div>

</c:set>
<c:set var="jsSpec">
<script src="${pageContext.request.contextPath}/JS/getInfoEmployeur.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
window.addEventListener('load', function(event) {
    appelModifier('${pageContext.request.contextPath}');
});
</script>
</c:set>
<%@ include file="templatePageAvecMenuV.jsp"%>

