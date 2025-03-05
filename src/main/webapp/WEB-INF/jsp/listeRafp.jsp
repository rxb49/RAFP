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
                <a href="#!" class="btn btn-md btn-primary g-mr-10 g-mb-15"><spring:message code="liste.agent"/></a>
                <a href="#!" class="btn btn-md btn-primary g-mr-10 g-mb-15"><spring:message code="liste.employeur"/></a>
            </div>
        </div><br>
            <label for="exampleSelect2"><spring:message code="saisie.agent.rechercher" /></label>
                <div class="formfield-select--container">
                    <select class="form-control rounded-0 formUA" id="exampleSelect2">
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
                        <option>4</option>
                        <option>5</option>
                    </select>
                </div><br>

        <div class="container g-z-index-1 g-py-20" >
        <div class="table-responsive">
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col"><spring:message code="prenom" /></th>
                        <th scope="col"><spring:message code="nom" /></th>
                        <th scope="col"><spring:message code="btn.modifier" /></th>
                        <th scope="col"><spring:message code="btn.ajouter" /></th>
                        <th scope="col"><spring:message code="btn.supprimer" /></th>

                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${persons}" var ="person">
                    <tr>
                        <td>${person.prenom}</td>
                        <td>${person.nom}</td>
                        <td>
                            <form action="personList/pdf" method="post" target="_blank">
                                <input type="hidden" value="${person.numero}" name="numero"/>
                                <button type="submit" class="btn btn-tab" title="<spring:message code="imprimer" />"><i class="bi bi-arrow-clockwise"></i></button>
                            </form>
                        </td>
                        <td>
                            <form action="personList/pdf" method="post" target="_blank">
                                <input type="hidden" value="${person.numero}" name="numero"/>
                                <button type="submit" class="btn btn-tab" title="<spring:message code="imprimer" />"><i class="bi bi-plus"></i></button>
                            </form>
                        </td>
                        <td>
                            <form action="personList/pdf" method="post" target="_blank">
                                <input type="hidden" value="${person.numero}" name="numero"/>
                                <button type="submit" class="btn btn-tab" title="<spring:message code="imprimer" />"><i class="bi bi-trash"></i></button>
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
</c:set>
<%@ include file="templatePageAvecMenuV.jsp"%>