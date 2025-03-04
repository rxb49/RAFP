<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="titrePage"><spring:message code="error.erreurBDD"/></c:set>
<c:set var="cssSpec">
</c:set>
<c:set var="contenuSpec">
    <div class="container" >
        <div class="row">
            <div class="col text-center g-mb-40">
                <img src="/images/images/habilitation.png" class="img-fluid mb-3" width="250px" />
                <p><spring:message code="error.erreurBDD"/></p>
                <a href="${pageContext.request.contextPath}/" class="btn btn-md u-btn-outline-bluegray g-mt-20"><spring:message code="error.retour"/></a>
            </div>
        </div>
    </div>
</c:set>
<c:set var="jsSpec">
</c:set>
<%@ include file="../templatePageSansMenuV.jsp"%>