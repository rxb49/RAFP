<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="titrePage"><spring:message code="label.welcome"/></c:set>
<c:set var="cssSpec">
</c:set>
<c:set var="contenuSpec">
    <div class="flex-column">

        <a href="${pageContext.request.contextPath}/gestionEmployeur" class="card card-custom d-flex flex-row pe-4 mb-3">
            <span class="icon-container me-3 m-2"><i class="bi bi-person"></i></span>
            <div class="flex-grow-1 cadre-info my-3">
                <h2 class="mb-1"><spring:message code="titre.dev"/></h2>
                <p class="mb-0 text-muted"><spring:message code="titre.dev.detail"/></p>
            </div>
            <span class="d-flex justify-content-center">
                <i class="bi bi-chevron-right arrow-icon"></i>
            </span>
        </a>

        <a href="${pageContext.request.contextPath}/hello" class="card card-custom d-flex flex-row pe-4 mb-3">
            <span class="icon-container me-3 m-2"><i class="bi bi-briefcase"></i></span>
            <div class="flex-grow-1 cadre-info my-3">
                <h2 class="mb-1"><spring:message code="titre.3"/></h2>
                <p class="mb-0 text-muted"><spring:message code="titre.3.detail"/></p>
            </div>
            <span class="d-flex justify-content-center">
                <i class="bi bi-chevron-right arrow-icon"></i>
            </span>
        </a>


        <div class="card mb-4 " id="cadre">
            <div class="flex-grow-1 cadre-info my-3 mx-3">
                <h2 class="mb-1"><spring:message code="titre.4" /></h2>
            </div>

            <form class="g-pa-30--md g-mb-30 mx-3">
                <div class="form-group g-mb-25">
                    <label for="exampleSelect1"><spring:message code="titre.4.employeur" /></label>
                    <div class="formfield-select--container">
                        <select class="form-control rounded-0 formUA" id="exampleSelect1">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                        </select>
                    </div>
                </div>
                <br>
                <div class="form-group g-mb-25">
                    <label><spring:message code="titre.4.csv" /></label>
                    <div class="custom-file">
                        <input type="file" class="custom-file-input form-control formUA" id="Q10" lang="fr">
                        <div class="invalid-feedback"></div>
                    </div>
                </div><br>
                <button type="submit" class="btn btn-md btn-primary">Confirmer</button>
            </form>
        </div>

        <div class="card mb-4 " id="cadre1">
            <div class="flex-grow-1 cadre-info my-3 mx-3">
                <h2 class="mb-1"><spring:message code="titre.5" /></h2>
            </div>

            <form class="g-pa-30--md g-mb-30 mx-3">
                <div class="form-row">
                    <div class="col-12">
                        <div class="input-group input-group-sm mb-3">
                            <input id="searchFormInput" type="text" class="form-control formSearch" placeholder="Rechercher un agent" aria-label="Rechercher un agent" aria-describedby="searchForm" name="searchFormInput">
                        </div>
                    </div>
                </div>
                <label for="exampleSelect1"><spring:message code="titre.4.employeur" /></label>
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
                    <label for="exampleInputEmail1" class="labelRequired"><spring:message code="titre.5.montant" /></label>
                    <input type="number" class="form-control form-control-md formUA" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Entrez un montant">
                </div><br>
                <button type="submit" class="btn btn-md btn-primary">Confirmer</button>
            </form>
        </div>

        <div class="card mb-4" id="cadre2">
            <div class="flex-grow-1 cadre-info my-3 mx-3">
                <h2 class="mb-1"><spring:message code="titre.6" /></h2>
            </div>
        <div class="row">
            <div class="col mx-3">
                <a href="#!" class="btn btn-md btn-primary g-mr-10 g-mb-15"><spring:message code="titre.6.rafp" /></a>
                <a href="#!" class="btn btn-md btn-secondary g-mr-10 g-mb-15"><spring:message code="titre.6.mail" /></a>
            </div>
        </div>


        </div>
    </div>

</c:set>
<c:set var="jsSpec">
</c:set>
<%@ include file="templatePageSansMenuV.jsp" %>