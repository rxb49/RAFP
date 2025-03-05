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

            <form class="g-pa-30--md g-mb-30 mx-3">
                <div class="form-group g-mb-25">
                    <label for="nomEmployeur" class="labelRequired"><spring:message code="employeur.nom"/></label>
                    <input type="text" class="form-control form-control-md formUA" id="nomEmployeur" aria-describedby="nom" placeholder="<spring:message code="employeur.nom"/>">
                </div>
                <br>
                <div class="form-group g-mb-25">
                    <label for="email" class="labelRequired"><spring:message code="employeur.mail"/></label>
                    <input type="email" class="form-control form-control-md formUA" id="email" aria-describedby="emailHelp" placeholder="<spring:message code="employeur.mail"/>">
                </div><br>
                <button type="submit" class="btn btn-md btn-primary"><spring:message code="btn.ajouter"/></button>
            </form>
        </div>

        <div class="card mb-4" id="cadre1">
            <div class="flex-grow-1 cadre-info my-3 mx-3">
                <h2 class="mb-1"><spring:message code="employeur.modifier" /></h2>
            </div>

            <form class="g-pa-30--md g-mb-30 mx-3">
                <div class="form-group g-mb-25">
                    <label for="exampleSelect1"><spring:message code="saisie.employeur.nomEmployeur" /></label>
                    <div class="formfield-select--container">
                        <select class="form-control rounded-0 formUA" id="exampleSelect1">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                        </select>
                    </div>
                </div><br>
                <div class="form-group g-mb-25">
                    <label for="nomEmployeur" class="labelRequired"><spring:message code="employeur.nom"/></label>
                    <input type="text" class="form-control form-control-md formUA" id="nameEmployeur" aria-describedby="nom" placeholder="<spring:message code="employeur.nom"/>">
                </div>
                <br>
                <div class="form-group g-mb-25">
                    <label for="email" class="labelRequired"><spring:message code="employeur.mail"/></label>
                    <input type="email" class="form-control form-control-md formUA" id="emailEmployeur" aria-describedby="emailHelp" placeholder="<spring:message code="employeur.mail"/>">
                </div><br>
                <button type="submit" class="btn btn-md btn-primary"><spring:message code="btn.modifier"/></button>
            </form>
        </div>
    </div>
</c:set>
<c:set var="jsSpec">
</c:set>
<%@ include file="templatePageAvecMenuV.jsp"%>