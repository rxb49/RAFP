<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="titrePage"><i class="bi ${titrePage.icone} pe-2"></i>${titrePage.nomPage}</c:set>
<c:set var="cssSpec">
</c:set>
<c:set var="contenuSpec">
<div id="chargement" class="ajax-loading">
    <div id="contentLoader">
        <div id="contentLoaderImg">
            <img src="/images/images/blockLoader.gif" class="img-fluid" alt="Administration des formulaires">
        </div>
    <div id="contentLoaderTxt"><spring:message code="saisie.import"/></div>
</div>
</div>
    <div class="flex-column">
        <div class="card mb-4" id="cadre">
            <div class="container text-center">

                <div class="flex-grow-1 cadre-info my-3 mx-3">
                    <h2 class="mb-1">Importer vos données</h2>
                </div>

                <div id="importForm">
                    <div class="form-group g-mb-25 px-3">
                        <div class="custom-file">
                            <input type="file" class="custom-file-input form-control formUA" id="fileInput" name="fileInput" lang="fr">
                            <div class="invalid-feedback"></div>
                        </div>
                        <br>
                    </div>

                    <div class="button-group">
                        <button type="submit" onclick="importTotal('${pageContext.request.contextPath}')" class="btn btn-md btn-primary">
                            Ajouter
                        </button>
                        <a href="${pageContext.request.contextPath}/donneesEmployeur/${employeurs.id_emp}" class="btn btn-md btn-secondary">
                            Annuler
                        </a>
                    </div>
                </div>

                <hr>

                <h3>Données en attente de validation</h3>
                <div id="tempDataTable" class="table-responsive">

                </div>

                <button id="validateButton" onclick="validateImport('${pageContext.request.contextPath}')" class="btn btn-success" style="display: none;">
                    Valider les données
                </button>
                <button id="cancelButton" onclick="clearTempData('${pageContext.request.contextPath}')" class="btn btn-danger" style="display: none;">
                    Annuler et réinitialiser
                </button>

            </div>
        </div>
    </div>
</c:set>

<c:set var="jsSpec">
    <script src="${pageContext.request.contextPath}/JS/importTotal.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</c:set>

<%@ include file="templatePageAvecMenuV.jsp"%>
